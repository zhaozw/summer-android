package cn.cerc.summer.android.basis.utils;

import android.content.Context;

import cn.cerc.summer.android.basis.forms.JavaScriptService;
import cn.cerc.summer.android.parts.music.FrmCaptureMusic;

/**
 * Created by Administrator on 2017/8/14.
 */

public class CaptureMusic implements JavaScriptService {
    @Override
    public String execute(Context context, String dataIn) {
        FrmCaptureMusic.startForm(context);
        return "true";
    }
}
