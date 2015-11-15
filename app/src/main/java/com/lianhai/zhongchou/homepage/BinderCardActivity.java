package com.lianhai.zhongchou.homepage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lianhai.zhongchou.R;
import com.lianhai.zhongchou.bean.CardInfo;
import com.lianhai.zhongchou.bean.OrderInfo;
import com.lianhai.zhongchou.config.BaseInfo;
import com.lianhai.zhongchou.customview.DialogManager;
import com.lianhai.zhongchou.pay.lianlianpay.LianLianPay;
import com.lianhai.zhongchou.utils.JsonUtils;
import com.lianhai.zhongchou.utils.NetWorkUtils;
import com.lianhai.zhongchou.utils.ZXUtils;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;

/**
 * Created by zaxlcer on 15/11/7.
 * 绑定银行卡页面
 */
public class BinderCardActivity extends Activity{


    private EditText card_no_et;//银行卡
    private TextView band_name;//银行名称
    private TextView money;//支付金额
    private Button confirm;//确认支付
    private int id;
    private Intent intent;
    private OrderInfo info;
    private boolean cardExist=false;//是否存在卡


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.bindercard_activity);
        ZXUtils.initTitle(this, "支付", false);
        intent=getIntent();
        id=intent.getIntExtra("id", 0);
        info=intent.getParcelableExtra("orderinfo");
        findView();
        init();

    }

    private void findView() {
        card_no_et = (EditText) findViewById(R.id.card_no_et);
        band_name = (TextView) findViewById(R.id.band_name);
        money = (TextView) findViewById(R.id.money);
        confirm = (Button) findViewById(R.id.confirm);

    }

    private void init() {

        bindData();

        card_no_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                RequestParams params = new RequestParams();
                params.put("card_no", charSequence);
                Log.e("","-------");
                NetWorkUtils.doPost(BaseInfo.Card_Info, params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        Log.e("response", response.toString());
                        if (response.optInt("code") == 1) {
                            //成功 则改卡是存在的
                            cardExist=true;
                            if (response.optJSONObject("body")!=null){
                                CardInfo cardInfo= JsonUtils.getResult(response.optJSONObject("body"),CardInfo.class);
                                Log.e("cardInfo.toString()",cardInfo.toString());
                                if (cardInfo!=null){
                                    if(cardInfo.getBank_name()!=null)
                                        band_name.setText(cardInfo.getBank_name());
                                }
                            }

                        }else {
                            band_name.setText("");
                            cardExist=false;
                        }
                    }
                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        super.onFailure(statusCode, headers, responseString, throwable);
                        Log.e("responseString", responseString);
                    }
                });
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!cardExist){
                    DialogManager.showNotice(BinderCardActivity.this,"银行卡验证失败");
                    return;
                }
                info.setCardId(card_no_et.getText().toString().trim());
                LianLianPay lianLianPay=new LianLianPay(BinderCardActivity.this);
                lianLianPay.pay(info);


            }
        });
    }

    private void bindData() {
        if (info!=null) {
            Log.e("info",info.toString());
            if (info.getMoney() != null) {
                money.setText("金额：" + info.getMoney() + "元");
            } else {
                money.setText("金额：0.0元");
            }
        }
    }
}
