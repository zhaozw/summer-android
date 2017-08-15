package cn.cerc.summer.android.parts.image;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.mimrc.vine.R;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.Call;

public class FrmZoomImage extends AppCompatActivity {
       private ImageView IvBack;
        private ImageView IvAdd;
    private ImageView IvBig;
    private ImageView IvJian;
    private TextView TvTitle;
    Bitmap bitmapBig=null;
    String url="http://img.taopic.com/uploads/allimg/140313/235026-1403130Z43831.jpg";

//    Handler handler=  new Handler(new Handler.Callback() {
//        @Override
//        public boolean handleMessage(Message msg) {
//            msg.what=0;
//            bitmapBig= (Bitmap) msg.obj;
//            IvBig.setImageBitmap(bitmapBig);
//            Log.e("图片","运行到这里");
//            return true;
//        }
//    });

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

        OkHttpUtils.get().url(url).tag(this).build().connTimeOut(20000).readTimeOut(20000).writeTimeOut(20000) .execute(new BitmapCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(Bitmap response, int id) {
                bitmapBig =response;
                IvBig.setImageBitmap(response);
            }
        });


        savePicture(bitmapBig);//保存图片到SD卡

    }

    public void savePicture(Bitmap bitmap)
    {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
        {
            try
            {
                File sdcardDir = Environment
                        .getExternalStorageDirectory();
                String filename = sdcardDir.getCanonicalPath()
                        +  "car"+".jpg";
                File file = new File(filename);
                FileOutputStream out = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                out.flush();
                out.close();
            } catch (FileNotFoundException e)
            {
                e.printStackTrace();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }


    private void init() {
        IvBack= (ImageView) findViewById(R.id.imageView_back);
        IvAdd= (ImageView) findViewById(R.id.image_add);
        IvJian= (ImageView) findViewById(R.id.image_jian);
        IvBig=(ImageView) findViewById(R.id.image_big);
        TvTitle=(TextView)findViewById(R.id.textView_title);
    }



    public static void startForm(Context context, String urlImage) {
        Intent intent = new Intent();
        intent.setClass(context, FrmZoomImage.class);
        intent.putExtra("url", urlImage);
        context.startActivity(intent);
    }
}
