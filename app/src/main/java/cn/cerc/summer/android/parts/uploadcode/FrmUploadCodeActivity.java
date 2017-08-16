package cn.cerc.summer.android.parts.uploadcode;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mimrc.vine.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/16.
 */

public class FrmUploadCodeActivity extends AppCompatActivity implements View.OnClickListener {
    private WebView mWebView;
    private EditText viewById;
    private RecyclerView mRecyclerView;
    private List<String > mCods = new ArrayList<>();
    private Button mCmmitCode;
    private MyCodeAdapter myCodeAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upoad_code);

        initView();
        lisenner();
        initData();
    }

    private void lisenner() {
        mCmmitCode.setOnClickListener(this);
    }

    private void initView() {
        mWebView = (WebView) findViewById(R.id.my_webview);
        viewById = (EditText) findViewById(R.id.bar_code);
        mRecyclerView = (RecyclerView)findViewById(R.id.code_listview);
        mCmmitCode = (Button) findViewById(R.id.commit);


    }

    private void initData() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        myCodeAdapter = new MyCodeAdapter(mCods,this,mWebView);
        mRecyclerView.setAdapter(myCodeAdapter);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.commit :
                initEdData();
                break;
            default:
                return;
        }
    }

    private void initEdData() {
        String trim = viewById.getText().toString().trim();
        if(TextUtils.isEmpty(trim)) {
            Toast.makeText(this,"没有内容无法提交",Toast.LENGTH_SHORT).show();
            return;
        }
        if(!mCods.contains(trim)){
            mCods.add(trim);
            myCodeAdapter.notifyDataSetChanged();
        }else{
            Toast.makeText(this,"数据重复了,别重复提交了",Toast.LENGTH_SHORT).show();
            return;
        }

    }
}
