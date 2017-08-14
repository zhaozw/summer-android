package cn.cerc.summer.android.basis.utils;

import android.content.Context;

import cn.cerc.summer.android.basis.forms.FrmScan;
import cn.cerc.summer.android.basis.forms.JavaScriptService;

/**
 * Created by Jason<sz9214e@qq.com> on 2017/8/9.
 * 扫一扫功能，扫描成功后上传到指定网址
 */

public class ScanBarcode implements JavaScriptService {
    @Override
    public String execute(Context context, String dataIn) {
        FrmScan.startForm(context,dataIn);
       return "";
    }
}
