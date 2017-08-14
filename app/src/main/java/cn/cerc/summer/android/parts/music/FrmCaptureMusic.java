package cn.cerc.summer.android.parts.music;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mimrc.vine.R;

public class FrmCaptureMusic extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_capture_music);
    }

    public static void startForm(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, FrmCaptureMusic.class);
        context.startActivity(intent);
    }
}
