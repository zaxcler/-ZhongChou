package com.lianhai.zhongchou.homepage;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lianhai.zhongchou.R;
import com.lianhai.zhongchou.bean.BandAndCardInfo;
import com.lianhai.zhongchou.bean.OrderInfo;
import com.lianhai.zhongchou.config.BaseInfo;
import com.lianhai.zhongchou.config.MyApplication;
import com.lianhai.zhongchou.customview.DialogManager;
import com.lianhai.zhongchou.utils.JsonUtils;
import com.lianhai.zhongchou.utils.NetWorkUtils;
import com.lianhai.zhongchou.utils.NetworkHepler;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

/**
 * Created by zaxcler on 15/10/21.
 */
public class PayActivity  extends Activity implements View.OnClickListener{
    private LinearLayout pay_alipay;
    private LinearLayout pay_lianlian;
    private LinearLayout pay_yinlian;
    private TextView money;
    private ImageView back;

    private Intent intent;
    private int id;
    private OrderInfo info;
    private double paymoney;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.pay_activity);
        MyApplication.addActivityToMap(this, "buygq");
        intent=getIntent();
        id=intent.getIntExtra("id",0);
        findView();
        init();
    }

    private void findView() {
        pay_alipay = (LinearLayout) findViewById(R.id.pay_alipay);
        pay_lianlian = (LinearLayout) findViewById(R.id.pay_lianlian);
        pay_yinlian = (LinearLayout) findViewById(R.id.pay_yinlian);
        back = (ImageView) findViewById(R.id.back);
        money = (TextView) findViewById(R.id.money);



    }

    private void init() {

       loadData();

        pay_alipay.setOnClickListener(this);
        pay_lianlian.setOnClickListener(this);
        pay_yinlian.setOnClickListener(this);
        back.setOnClickListener(this);

    }

    /**
     * 加载数据
     */
    private void loadData() {
        NetworkHepler hepler=new NetworkHepler();
        hepler.put("id", id);

        NetWorkUtils.doGet(BaseInfo.Get_Order, null, hepler, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.e("response", response.toString());
                if (response.optInt("code") == 1) {
                    if (response.optJSONObject("body") != null) {
                        info = JsonUtils.getResult(response.optJSONObject("body"), OrderInfo.class);
                        if (info != null)
                            bindData(info);
                    }
                } else {
                    if (response.optString("result") != null)
                        DialogManager.showNotice(PayActivity.this, response.optString("result"));
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
     * 绑定数据
     */
    private void bindData(OrderInfo info) {
        if(info.getMoney()!=null){
            money.setText("￥"+info.getMoney()+"元");
            paymoney=Double.valueOf(info.getMoney());
        }


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.pay_lianlian:

                NetWorkUtils.doGet(BaseInfo.IsFirst, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        Log.e("response",response.toString());
                        /**
                         * code=28是首次支付
                         */
                        if (response.optInt("code") == 28) {
                            intent.setClass(PayActivity.this, BinderCardActivity.class);
                            intent.putExtra("orderinfo", info);
                            startActivity(intent);
                            PayActivity.this.finish();
                        } else {

                            if (response.optJSONObject("body")!=null){
                                BandAndCardInfo bandAndCardInfo=JsonUtils.getResult(response.optJSONObject("body"),BandAndCardInfo.class);
                                intent.setClass(PayActivity.this, LianLianPayActivity.class);
                                intent.putExtra("orderinfo", info);
                                intent.putExtra("bandAndCardInfo",bandAndCardInfo);
                                startActivity(intent);
                                PayActivity.this.finish();
                            }



                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        super.onFailure(statusCode, headers, responseString, throwable);
                        Log.e("responseString", responseString);
                    }
                });
                break;
            case R.id.pay_alipay:
               showPayDialog(R.layout.alipay_notice_activity, paymoney);
                break;
            case R.id.pay_yinlian:
                showPayDialog(R.layout.lianlianpay_notice_activity, paymoney);
                break;
            case R.id.back:
                this.finish();
                break;
        }
    }

    /**
     * 显示支付窗口
     * @param resourceId
     */
    private void showPayDialog(int resourceId,double money ){
        final Dialog dialog=new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view= LayoutInflater.from(this).inflate(resourceId,null);
        TextView money_tv= (TextView) view.findViewById(R.id.money);
        money_tv.setText(money+"");
        Button confirm= (Button) view.findViewById(R.id.confirm);
        dialog.setContentView(view);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                PayActivity.this.finish();
            }
        });

        dialog.show();

    }
}
