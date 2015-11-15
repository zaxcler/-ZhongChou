package com.lianhai.zhongchou.homepage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.lianhai.zhongchou.R;
import com.lianhai.zhongchou.config.BaseInfo;
import com.lianhai.zhongchou.config.MyApplication;
import com.lianhai.zhongchou.customview.DialogManager;
import com.lianhai.zhongchou.utils.NetWorkUtils;
import com.lianhai.zhongchou.utils.NetworkHepler;
import com.lianhai.zhongchou.utils.ZXUtils;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

/**
 * Created by zaxcler on 15/10/21.
 */
public class AboutGQActivity extends Activity {
    private CheckBox checkbox1;
    private CheckBox checkbox2;
    private TextView protocol1;
    private TextView protocol2;
    private Button confirm;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.about_gq_activity);
        MyApplication.addActivityToMap(this, "buygq");
        ZXUtils.initTitle(this, "投资风险", false);
        intent=getIntent();
        findView();
        init();
    }

    private void findView() {
        checkbox1 = (CheckBox) findViewById(R.id.checkbox1);
        checkbox2 = (CheckBox) findViewById(R.id.checkbox2);
        protocol1 = (TextView) findViewById(R.id.protocol1);
        protocol2 = (TextView) findViewById(R.id.protocol2);
        confirm = (Button) findViewById(R.id.confirm);

    }

    private void init() {
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkbox1.isChecked()&&checkbox2.isChecked()){
                    intent.setClass(AboutGQActivity.this,ProtocolActivity.class);
                    startActivity(intent);
                    AboutGQActivity.this.finish();
                }else {
                    Toast.makeText(AboutGQActivity.this,"您必须同意风险协议",Toast.LENGTH_SHORT).show();
                }
            }
        });

        protocol1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NetworkHepler hepler=new NetworkHepler();
                hepler.put("id",8);
                NetWorkUtils.doGet(BaseInfo.getCont,null,hepler,new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        if (response.optJSONObject("body") != null && response.optJSONObject("body").optString("agreement")!=null) {
                            DialogManager.showFWBDialog(AboutGQActivity.this, response.optJSONObject("body").optString("agreement"));
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        super.onFailure(statusCode, headers, responseString, throwable);
                        Log.e("responseString,",responseString);
                    }
                });
            }
        });



        protocol2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NetworkHepler hepler=new NetworkHepler();
                hepler.put("id",9);
                NetWorkUtils.doGet(BaseInfo.getCont, null, hepler, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        if (response.optJSONObject("body") != null && response.optJSONObject("body").optString("agreement")!=null) {
                            DialogManager.showFWBDialog(AboutGQActivity.this, response.optJSONObject("body").optString("agreement"));
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        super.onFailure(statusCode, headers, responseString, throwable);
                        Log.e("responseString,", responseString);
                    }
                });
            }
        });

    }
}
