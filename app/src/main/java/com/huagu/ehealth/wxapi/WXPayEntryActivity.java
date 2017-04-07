package com.huagu.ehealth.wxapi;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.huagu.ehealth.R;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import cn.cerc.summer.android.Activity.MainActivity;
import cn.cerc.summer.android.MyConfig;


public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
	
	private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";
	
    private IWXAPI api;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result);
        
    	api = WXAPIFactory.createWXAPI(this, MyConfig.WX_appId);
        api.handleIntent(getIntent(), this);
    }

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
        api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
	}

	@Override
	public void onResp(BaseResp resp) {
		Log.e("resp", resp.getType() + "");
		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
			String str = "";
			if (resp.errCode == 0) str = "支付成功";
			else if (resp.errCode == -2) str = "用户取消";
			else str = "支付失败";
			MainActivity.getInstance().webview.loadUrl("javascript:ReturnForApp('"+str+"')");

//			AlertDialog.Builder builder = new AlertDialog.Builder(this);
//			builder.setTitle(R.string.app_tip);
//			builder.setMessage(getString(R.string.pay_result_callback_msg, str));
//			builder.setNegativeButton("返回我的页面", null);
//			builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
//				@Override
//				public void onDismiss(DialogInterface dialog) {
//
//				}
//			});
//			builder.show();
			WXPayEntryActivity.this.finish();
		}
		switch (resp.errCode){
			case BaseResp.ErrCode.ERR_OK:
				Log.i("ERR_OK","success");
				break;
		}
	}
}