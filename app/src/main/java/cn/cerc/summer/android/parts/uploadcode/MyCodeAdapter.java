package cn.cerc.summer.android.parts.uploadcode;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mimrc.vine.R;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.HashMap;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by 黄冲<hc92android@163.com> on 2017/8/16.
 */

public class MyCodeAdapter extends RecyclerView.Adapter {

    private List<String> mStringList;
    private WebView mWebView;
    private Context mContext;
    public MyCodeAdapter(List<String> mStringList, Context context, WebView url) {
        this.mStringList = mStringList;
        this.mContext = context;
        this.mWebView = url;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_code_view, parent, false);
        CodeViewHodler mCodeViewHodler = new CodeViewHodler(inflate,parent.getContext());
        return mCodeViewHodler;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CodeViewHodler mCodeViewHodler = (CodeViewHodler) holder;
        String mCode = mStringList.get(position);
        mCodeViewHodler.setData(mCode,position);
    }

    @Override
    public int getItemCount() {
        return mStringList.size()==0?0:mStringList.size();
    }

     class CodeViewHodler extends RecyclerView.ViewHolder{

        private Context viewHoderContext;
        private TextView hCode;
        private Button mButton;
        private HashMap<String,String> map = new HashMap<>();
        public CodeViewHodler(View itemView, Context context) {
            super(itemView);
            viewHoderContext = context;
            hCode = (TextView) itemView.findViewById(R.id.my_code);
            mButton = (Button) itemView.findViewById(R.id.my_post_net);
        }

        public void setData(final String mCode, final int position) {
            hCode.setText(mCode);

            mButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Log.e("MyCodeAdapter", "onClick: 条目"+position+"被点击了__code1="+mCode );
                    map.put(position+"",mCode);
                    Toast.makeText(viewHoderContext, "条目"+position+"上传服务器"+mCode, Toast.LENGTH_SHORT).show();
                    RequestParams params = new RequestParams("https://www.baidu.com");
                    params.addParameter("条目"+position,mCode);
                    x.http().post(params, new Callback.CommonCallback<Object>() {
                        @Override
                        public void onSuccess(Object result) {
                            Log.e(TAG, "onSuccess: 上传成功" );
                            //TODO:上传成功后返回url加载到webview
                        }
                        @Override
                        public void onError(Throwable ex, boolean isOnCallback) {
                        }
                        @Override
                        public void onCancelled(CancelledException cex) {
                        }

                        @Override
                        public void onFinished() {

                        }
                    });
                }

            });
        }
    }
}
