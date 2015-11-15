package com.lianhai.zhongchou.homepage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.lianhai.zhongchou.R;
import com.lianhai.zhongchou.config.BaseInfo;
import com.lianhai.zhongchou.config.MyApplication;
import com.lianhai.zhongchou.customview.DialogManager;
import com.lianhai.zhongchou.utils.NetWorkUtils;
import com.lianhai.zhongchou.utils.NetworkHepler;
import com.lianhai.zhongchou.utils.ZXUtils;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;

/**
 * Created by zaxcler on 15/10/21.
 */
public class ProtocolActivity extends Activity {
    private WebView webView;
    private Button confirm;
    private Intent intent;
    private int id;
    private CheckBox isArgee;

    private TextView content;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.protocol_activity);
        MyApplication.addActivityToMap(this, "buygq");
        ZXUtils.initTitle(this, "协议", false);
        intent=getIntent();
        id=intent.getIntExtra("id",0);
        findView();
        init();

    }



    private void findView() {
//        webView = (WebView) findViewById(R.id.webView);
        confirm = (Button) findViewById(R.id.confirm);
        isArgee = (CheckBox) findViewById(R.id.isArgee);

        content = (TextView) findViewById(R.id.content);

    }

    private void init() {
        loadData();
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isArgee.isChecked()){
                    DialogManager.showNotice(ProtocolActivity.this,"请同意该协议");
                    return;
                }
                NetworkHepler hepler=new NetworkHepler();
                hepler.put("id",id);
                hepler.put("agree",1);

                NetWorkUtils.doGet(BaseInfo.Agree_Contract, null, hepler, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        if (response.optInt("code") == 1) {
                            /**
                             * 生成订单
                             */
                            orderStock();
                        }else {
                            if (response.optString("result")!=null)
                            DialogManager.showNotice(ProtocolActivity.this,response.optString("result"));
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        super.onFailure(statusCode, headers, responseString, throwable);
                        Log.e("responseString",responseString);
                    }
                });



            }
        });
    }

    private void loadData() {

        NetworkHepler hepler=new NetworkHepler();
        hepler.put("id",id);

        NetWorkUtils.doGet(BaseInfo.Contract, null, hepler, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                if (response.optInt("code") == 1) {
                    if (response.optString("body") != null) {
                        content.setText(Html.fromHtml(response.optString("body")));
//                        webView.loadDataWithBaseURL(null,response.optString("body"),"text/html","utf-8",null);
                    }
                } else {
                    if (response.optString("result") != null)
                        DialogManager.showNotice(ProtocolActivity.this, response.optString("result"));
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.e("responseString", responseString);
            }
        });

    }

    /**
     * 下股权订单
     */
    private void orderStock(){
        RequestParams params=new RequestParams();
        params.put("id",id);

        NetWorkUtils.doPost(BaseInfo.Order_Stock,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.e("response", response.toString());
                if (response.optInt("code")==1){
                    intent.setClass(ProtocolActivity.this, PayActivity.class);
                    startActivity(intent);
                    ProtocolActivity.this.finish();
                }else {
                    if (response.optString("result") != null)
                        DialogManager.showNotice(ProtocolActivity.this, response.optString("result"));
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.e("responseString", responseString);
            }
        });

    }

}
