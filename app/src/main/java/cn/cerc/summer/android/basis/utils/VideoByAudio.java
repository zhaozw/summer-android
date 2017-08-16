package cn.cerc.summer.android.basis.utils;


import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import cn.cerc.summer.android.basis.forms.JavaScriptService;
import cn.cerc.summer.android.parts.videobyvoice.component.TextVideoActivity;

/**
 * Created by Administrator on 2017/8/15.
 */

public class VideoByAudio implements JavaScriptService{
    @Override
    public String execute(Context context, String dataIn) {
        try {
            JSONObject json = new JSONObject(dataIn);
            String phoneNo = json.getString("phoneNo");
            TextVideoActivity.startForm(context, phoneNo);
            return "进入视频语音通话界面吧";
        } catch (JSONException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}

/**
 * var browser = getBrowser();
 * if(browser == null){
 *     alter("this is not android or iphone");
 *     return
 *  }
 *  broser.req = {"phoneNo": "13812345678"}
 *  if(!browser.send("VideoByAudio")
 *     alter(browser.getMessage());
 *
 *
 *
 *
 *
 *
 * browser.req = {"targetId", "hy", "finishFunction", "returnFunct"};
 * browser.send("xxx")
 *
 * function returnFunct(msgId, msgText){
 *     if(msgId == "hy"){
 *         $("#hy").text(msgText);
 *     }
 * }
 */