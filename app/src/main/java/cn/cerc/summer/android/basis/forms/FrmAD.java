package cn.cerc.summer.android.basis.forms;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mimrc.vine.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import cn.cerc.summer.android.basis.core.MyApp;
import cn.cerc.summer.android.basis.core.WebConfig;

public class FrmAD extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private ViewPager viewPager;
    private LinearLayout contan;
    private TextView skip;
    private boolean is_skip;//是否跳转
    private Animation animation;//渐变动画
    private List<ImageView> imageViews;
    private List<String> list;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        FrmMain.getInstance().finish();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {//?device=android&clientId=44444444
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_ad);

        viewPager = (ViewPager) this.findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(2);
        contan = (LinearLayout) this.findViewById(R.id.contan);
        skip = (TextView) this.findViewById(R.id.skip);

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        list = WebConfig.getInstance().getAdImages();

        imageViews = new ArrayList<ImageView>();
        for (int i = 0; i < list.size(); i++) {
            ImageView imageView = new ImageView(this);
            ImageLoader.getInstance().displayImage(list.get(i), imageView, MyApp.getInstance().getImageOptions());
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageViews.add(imageView);
            if (i == (list.size() - 1))
                imageView.setOnClickListener(this);

            View view = new View(this);
            view.setBackgroundResource(R.drawable.point_white);
            if (i == 0)
                view.setBackgroundResource(R.drawable.point_color);
            LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            llp.width = 15;
            llp.height = 15;
            llp.leftMargin = 20;
            llp.rightMargin = 20;
            contan.addView(view, llp);
        }

        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return imageViews.size();
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(imageViews.get(position));
                return imageViews.get(position);
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(imageViews.get(position));
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }
        });

        viewPager.addOnPageChangeListener(this);
    }

    @Override
    public void onClick(View v) {
        finish();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        for (int i = 0; i < contan.getChildCount(); i++) {
            if (position == i) contan.getChildAt(i).setBackgroundResource(R.drawable.point_white);
            else contan.getChildAt(i).setBackgroundResource(R.drawable.point_color);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }
}
