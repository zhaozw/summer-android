package cn.cerc.summer.android.parts.image;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mimrc.vine.R;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import cn.cerc.summer.android.parts.permission.Constant;
import cn.cerc.summer.android.parts.permission.EasyPermissions;
import okhttp3.Call;

public class FrmZoomImage extends AppCompatActivity implements View.OnClickListener,
        EasyPermissions.PermissionCallbacks {
    private ImageView IvBack;
    private ImageView IvAdd;
    private ScaleView IvBig;
    private ImageView IvJian;
    private TextView TvTitle;
    String url = "http://img.taopic.com/uploads/allimg/140313/235026-1403130Z43831.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_zoom_image);

        //        RemoteServer service = new RemoteServer("SvrLogin.check");
        //        service.getDataIn().setString("account", "user01");
        //        service.getDataIn().setString("password", "password");
        //        if(service.exec()){
        //            String state = service.getDataOut().getHead().getString("state");
        //        }else{
        //            Toast.makeText(getApplicationContext(), service.getMessage()).show();
        //        }
        //
        //        String ver = new SampleService("SvrServer.getVersion").exec().getString("state");

        init();//初始化

                String[] perms = Constant.STORAGE_PERMISSIONS;
                if (EasyPermissions.hasPermissions(this, perms)) {
                    Toast.makeText(this, "手机内存卡权限", Toast.LENGTH_SHORT).show();
//                    savePicture(bitmapBig);//保存图片到SD卡
                } else
                    EasyPermissions.requestPermissions(this, "应用需要读取手机内存权限",
                            Constant.SD_CARD_STATE, perms);

        OkHttpUtils
                .get()
                .url(url)
                .tag(this)
                .build()
                .execute(new BitmapCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(Bitmap response, int id) {
                        IvBig.setImageBitmap(response);
                        savePicture(response);//保存图片到SD卡
                    }
                });

    }


    private final class TouchListener implements OnTouchListener {

        /**
         * 记录是拖拉照片模式还是放大缩小照片模式
         */
        private int mode = 0;// 初始状态
        /**
         * 拖拉照片模式
         */
        private static final int MODE_DRAG = 1;
        /**
         * 放大缩小照片模式
         */
        private static final int MODE_ZOOM = 2;

        /**
         * 用于记录开始时候的坐标位置
         */
        private PointF startPoint = new PointF();
        /**
         * 用于记录拖拉图片移动的坐标位置
         */
        private Matrix matrix = new Matrix();
        /**
         * 用于记录图片要进行拖拉时候的坐标位置
         */
        private Matrix currentMatrix = new Matrix();

        /**
         * 两个手指的开始距离
         */
        private float startDis;
        /**
         * 两个手指的中间点
         */
        private PointF midPoint;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            /** 通过与运算保留最后八位 MotionEvent.ACTION_MASK = 255 */
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                // 手指压下屏幕
                case MotionEvent.ACTION_DOWN:
                    mode = MODE_DRAG;
                    // 记录ImageView当前的移动位置
                    currentMatrix.set(IvBig.getImageMatrix());
                    startPoint.set(event.getX(), event.getY());
                    break;
                // 手指在屏幕上移动，改事件会被不断触发
                case MotionEvent.ACTION_MOVE:
                    // 拖拉图片
                    if (mode == MODE_DRAG) {
                        float dx = event.getX() - startPoint.x; // 得到x轴的移动距离
                        float dy = event.getY() - startPoint.y; // 得到x轴的移动距离
                        // 在没有移动之前的位置上进行移动
                        matrix.set(currentMatrix);
                        matrix.postTranslate(dx, dy);
                    }
                    // 放大缩小图片
                    else if (mode == MODE_ZOOM) {
                        float endDis = distance(event);// 结束距离
                        if (endDis > 10f) { // 两个手指并拢在一起的时候像素大于10
                            float scale = endDis / startDis;// 得到缩放倍数
                            matrix.set(currentMatrix);
                            matrix.postScale(scale, scale, midPoint.x, midPoint.y);
                        }
                    }
                    break;
                // 手指离开屏幕
                case MotionEvent.ACTION_UP:
                    // 当触点离开屏幕，但是屏幕上还有触点(手指)
                case MotionEvent.ACTION_POINTER_UP:
                    mode = 0;
                    break;
                // 当屏幕上已经有触点(手指)，再有一个触点压下屏幕
                case MotionEvent.ACTION_POINTER_DOWN:
                    mode = MODE_ZOOM;
                    /** 计算两个手指间的距离 */
                    startDis = distance(event);
                    /** 计算两个手指间的中间点 */
                    if (startDis > 10f) { // 两个手指并拢在一起的时候像素大于10
                        midPoint = mid(event);
                        //记录当前ImageView的缩放倍数
                        currentMatrix.set(IvBig.getImageMatrix());
                    }
                    break;
            }
            IvBig.setImageMatrix(matrix);
            return true;
        }

        /**
         * 计算两个手指间的距离
         */
        private float distance(MotionEvent event) {
            float dx = event.getX(1) - event.getX(0);
            float dy = event.getY(1) - event.getY(0);
            /** 使用勾股定理返回两点之间的距离 */
            return (float) Math.sqrt(dx * dx + dy * dy);
        }

        /**
         * 计算两个手指间的中间点
         */
        private PointF mid(MotionEvent event) {
            float midX = (event.getX(1) + event.getX(0)) / 2;
            float midY = (event.getY(1) + event.getY(0)) / 2;
            return new PointF(midX, midY);
        }

    }

    //loadBitmapFromLocal(url);
    private Bitmap loadBitmapFromLocal(String fileName) {
        // 去找文件，将文件转换为bitmap
        String name;
        try {
            name = fileName;

            File file = new File(getCache(), name);
            if (file.exists()) {
                return BitmapFactory.decodeFile(file.getAbsolutePath());
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    public void savePicture(Bitmap bitmap) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

            FileOutputStream fos = null;
            try {
                String name = "testImage";
                File file = new File(getCache(), name);
                fos = new FileOutputStream(file);

                // 将图像写到流中
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (fos != null) {
                    try {
                        fos.flush();
                        fos.close();
                        fos = null;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private String getCache() {
        String state = Environment.getExternalStorageState();
        File dir = null;
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            // 有sd卡
            dir = new File(Environment.getExternalStorageDirectory(),
                    "/Android/data/" + this.getPackageName() + "/icon");
        } else {
            // 没有sd卡
            dir = new File(this.getCacheDir(), "/icon");
        }

        if (!dir.exists()) {
            dir.mkdirs();
        }

        return dir.getAbsolutePath();
    }

//    private static final int maxWidth = 720;
//    private static final int maxHeigh = 1280;

    int imageWitch, maxWidth;
    int imageHeigh, maxHeigh;
    // 设置每次增减值
    int addWitch;
    int addHeigh;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_add:
                Log.e("[][][][]", IvBig.mMaxScale + "");
                                if (imageWitch >= maxWidth || imageHeigh >= maxHeigh) {
                                    Toast.makeText(this, "已经放大到了最大值", Toast.LENGTH_SHORT).show();
                                } else {
                                    imageWitch += addWitch;
                                    imageHeigh += addHeigh;
                                    IvBig.setLayoutParams(new LinearLayout.LayoutParams(imageWitch, imageHeigh));
                                }
                break;
            case R.id.image_jian:
                if (imageWitch <= maxWidth / 8 || imageHeigh <= maxHeigh / 8) {
                    Toast.makeText(this, "已经缩小到了最小值", Toast.LENGTH_SHORT).show();
                } else {
                    imageWitch -= addWitch;
                    imageHeigh -= addHeigh;
                    IvBig.setLayoutParams(new LinearLayout.LayoutParams(imageWitch, imageHeigh));
                }
                break;
        }
    }

    /**
     * 获取view宽高值
     */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
//        imageWitch=IvBig.getDrawable().getBounds().width();
//        imageHeigh=IvBig.getDrawable().getBounds().height();
        //        // 获取宽高值
        //        imageWitch = IvBig.getWidth();
        //        imageHeigh = IvBig.getHeight();
//                maxWidth = 1440;
//                maxHeigh = 720;
//                // 设置每次增减值
//                addWitch = imageWitch / 10;
//                addHeigh = imageHeigh / 10;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Constant.READ_WRITE_SD_CARD
                && EasyPermissions.hasPermissions(this, Constant.STORAGE_PERMISSIONS)) {
            Toast.makeText(this,"手机权限",Toast.LENGTH_SHORT).show();
            //savePicture(bitmapBig);//保存图片到SD卡
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (requestCode == Constant.READ_WRITE_SD_CARD) {
            //Constant.showMessage(this, "此功能需要读取手机内存卡权限");
            Toast.makeText(this, "此功能需要读取手机内存卡权限", Toast.LENGTH_SHORT).show();
        }
    }

    public static void startForm(Context context, String urlImage) {
        Intent intent = new Intent();
        intent.setClass(context, FrmZoomImage.class);
        intent.putExtra("url", urlImage);
        context.startActivity(intent);
    }

    private void init() {
        IvBack = (ImageView) findViewById(R.id.imageView_back);
        IvAdd = (ImageView) findViewById(R.id.image_add);
        IvAdd.setOnClickListener(this);
        IvJian = (ImageView) findViewById(R.id.image_jian);
        IvJian.setOnClickListener(this);
        IvBig = (ScaleView) findViewById(R.id.image_big);
        //IvBig.setOnTouchListener(new TouchListener());
        TvTitle = (TextView) findViewById(R.id.textView_title);
    }
}
