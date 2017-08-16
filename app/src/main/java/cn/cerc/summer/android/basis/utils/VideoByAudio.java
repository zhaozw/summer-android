package cn.cerc.summer.android.basis.utils;


import android.content.Context;
import android.content.Intent;

import cn.cerc.summer.android.basis.forms.JavaScriptService;
import cn.cerc.summer.android.parts.videobyvoice.component.TextVideoActivity;

/**
 * Created by Administrator on 2017/8/15.
 */

public class VideoByAudio implements JavaScriptService{
    @Override
    public String execute(Context context, String dataIn) {
        Intent intent = new Intent(context, TextVideoActivity.class);
        context.startActivity(intent);
        return "进入视频语音通话界面吧";
    }
}
