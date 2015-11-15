package com.lianhai.zhongchou.mypage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

import com.lianhai.zhongchou.R;
import com.lianhai.zhongchou.config.BaseInfo;
import com.lianhai.zhongchou.utils.ZXUtils;

/**
 * Created by zaxcler on 15/11/11.
 */
public class AboutActivvity extends Activity implements View.OnClickListener{
    private LinearLayout LinearLayout1;
    private LinearLayout LinearLayout2;
    private LinearLayout LinearLayout3;
    private LinearLayout LinearLayout4;
    private LinearLayout LinearLayout5;
    private LinearLayout LinearLayout6;
    private LinearLayout LinearLayout7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.aboutus_activity);
        ZXUtils.initTitle(this, "关于鼎及", false);
        findView();
        init();


    }

    private void findView() {

        LinearLayout1 = (LinearLayout) findViewById(R.id.LinearLayout1);
        LinearLayout2 = (LinearLayout) findViewById(R.id.LinearLayout2);
        LinearLayout3 = (LinearLayout) findViewById(R.id.LinearLayout3);
        LinearLayout4 = (LinearLayout) findViewById(R.id.LinearLayout4);
        LinearLayout5 = (LinearLayout) findViewById(R.id.LinearLayout5);
        LinearLayout6 = (LinearLayout) findViewById(R.id.LinearLayout6);
        LinearLayout7 = (LinearLayout) findViewById(R.id.LinearLayout7);
    }

    private void init() {
        LinearLayout1.setOnClickListener(this);
        LinearLayout2.setOnClickListener(this);
        LinearLayout3.setOnClickListener(this);
        LinearLayout4.setOnClickListener(this);
        LinearLayout5.setOnClickListener(this);
        LinearLayout6.setOnClickListener(this);
        LinearLayout7.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent=new Intent();
        intent.setClass(this,WebViewAcitity.class);
        switch (view.getId()){
            case R.id.LinearLayout1:
                intent.putExtra("url",BaseInfo.BaseUrl_xu+"?m=mobile&a=pagec&id=1&type=1");
                intent.putExtra("name","关于我们");
                break;
            case R.id.LinearLayout2:
                intent.putExtra("url",BaseInfo.BaseUrl_xu+"?m=mobile&a=pagec&id=2&type=1");
                intent.putExtra("name","联系我们");
                break;
            case R.id.LinearLayout3:
                intent.putExtra("url",BaseInfo.BaseUrl_xu+"?m=mobile&a=pagec&id=3&type=1");
                intent.putExtra("name","新手指南");
                break;
            case R.id.LinearLayout4:
                intent.putExtra("url",BaseInfo.BaseUrl_xu+"?m=mobile&a=pagec&id=4&type=1");
                intent.putExtra("name","风险提示");
                break;
            case R.id.LinearLayout5:
                intent.putExtra("url",BaseInfo.BaseUrl_xu+"?m=mobile&a=pagec&id=5&type=1");
                intent.putExtra("name","鼎及规则");
                break;
            case R.id.LinearLayout6:
                intent.putExtra("url",BaseInfo.BaseUrl_xu+"?m=mobile&a=pagec&id=6&type=1");
                intent.putExtra("name","政策法规");
                break;
            case R.id.LinearLayout7:
                intent.putExtra("url",BaseInfo.BaseUrl_xu+"?m=mobile&a=pagec&id=7&type=1");
                intent.putExtra("name","用户协议");
                break;
        }
        startActivity(intent);

    }
}
