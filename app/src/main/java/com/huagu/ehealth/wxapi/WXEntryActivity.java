package com.huagu.ehealth.wxapi;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.huagu.ehealth.R;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.json.JSONObject;

import cn.cerc.summer.android.Activity.MainActivity;
import cn.cerc.summer.android.Interface.RequestCallback;
import cn.cerc.summer.android.MyApplication;
import cn.cerc.summer.android.Utils.XHttpRequest;


public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
	private Context context = WXEntryActivity.this;

	private void handleIntent(Intent paramIntent) {
		MyApplication.getInstance().api.handleIntent(paramIntent, this);
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		handleIntent(getIntent());
	}
	@Override
	protected void onNewIntent(Intent intent) {
// TODO Auto-generated method stub
		super.onNewIntent(intent);
		setIntent(intent);
		handleIntent(intent);
	}
	@Override
	public void onReq(BaseReq arg0) {
// TODO Auto-generated method stub
		finish();
	}

	@Override
	public void onResp(BaseResp resp) {
// TODO Auto-generated method stub
		switch (resp.errCode) {
			case BaseResp.ErrCode.ERR_OK:
				if (ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX == resp.getType()) {
					Toast.makeText(context, "分享成功", Toast.LENGTH_LONG).show();
					break;
				}
				String code = ((SendAuth.Resp) resp).code;
				if(code!=null){
					Log.e("WXEntryActivity",code);
					MainActivity.getInstance().Action(code,"LoginWx");
				}

				break;
			case BaseResp.ErrCode.ERR_USER_CANCEL:
				Toast.makeText(context, "用户取消", Toast.LENGTH_LONG).show();
				break;
			case BaseResp.ErrCode.ERR_AUTH_DENIED:
				Toast.makeText(context, "用户拒绝授权", Toast.LENGTH_LONG).show();
				break;
			default:
				break;
		}
		finish();
	}
}