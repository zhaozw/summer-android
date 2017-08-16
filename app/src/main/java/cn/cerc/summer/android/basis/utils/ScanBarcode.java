package cn.cerc.summer.android.basis.utils;

import android.content.Context;
import android.content.Intent;

import cn.cerc.summer.android.basis.forms.JavaScriptService;
import cn.cerc.summer.android.parts.uploadcode.FrmUploadCodeActivity;

/**
 * Created by Jason<sz9214e@qq.com> on 2017/8/9.
 */

public class ScanBarcode implements JavaScriptService {
    @Override
    public String execute(Context context, String dataIn) {
        Intent intent = new Intent(context, FrmUploadCodeActivity.class);
        context.startActivity(intent);
        //TODO: 此功能还未准备好
        throw new RuntimeException("此功能还未准备好");
    }
}
