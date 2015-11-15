package com.lianhai.zhongchou.mypage;

import java.util.ArrayList;

import com.lianhai.zhongchou.R;
import com.lianhai.zhongchou.adapter.AddressAdapter;
import com.lianhai.zhongchou.adapter.CardAdapter;
import com.lianhai.zhongchou.bean.AddressBean;
import com.lianhai.zhongchou.bean.BandAndCardInfo;
import com.lianhai.zhongchou.bean.TestBean;
import com.lianhai.zhongchou.config.BaseInfo;
import com.lianhai.zhongchou.customview.DialogManager;
import com.lianhai.zhongchou.customview.MyListview;
import com.lianhai.zhongchou.homepage.BinderCardActivity;
import com.lianhai.zhongchou.homepage.LianLianPayActivity;
import com.lianhai.zhongchou.utils.JsonUtils;
import com.lianhai.zhongchou.utils.NetWorkUtils;
import com.lianhai.zhongchou.utils.NetworkHepler;
import com.lianhai.zhongchou.utils.ZXUtils;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HeaderViewListAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import org.apache.http.Header;
import org.json.JSONObject;

public class WithdrawActivity extends Activity {

    private MyListview listview;
    private View footview;
    private TextView notice;//提示
    private TextView money;//余额
    private TextView bank_name;//银行名称
    private TextView card_no;//银行卡号
    private EditText withdraw_money;//提现
    private Button submit;//提交

    private BandAndCardInfo bandAndCardInfo;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.withdraw_activity);
        ZXUtils.initTitle(this, "提现", false);
        intent = getIntent();
        bandAndCardInfo = intent.getParcelableExtra("bandAndCardInfo");
        findView();
        init();
    }

    private void findView() {
        footview = LayoutInflater.from(this).inflate(R.layout.card_list_footview, null);
        notice = (TextView) findViewById(R.id.notice);
        money = (TextView) findViewById(R.id.money);
        bank_name = (TextView) findViewById(R.id.bank_name);
        card_no = (TextView) findViewById(R.id.card_no);
        withdraw_money = (EditText) findViewById(R.id.withdraw_money);
        submit = (Button) findViewById(R.id.submit);
    }

    private void init() {

        bindData();
        String noticeString = notice.getText().toString();
        SpannableStringBuilder builder = new SpannableStringBuilder(noticeString);
        //ForegroundColorSpan 为文字前景色，BackgroundColorSpan为文字背景色
        //此处无法引用自定义颜色
        ForegroundColorSpan liangyellow = new ForegroundColorSpan(new Color().rgb(251, 180, 45));
        builder.setSpan(liangyellow, 13, 17, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        notice.setText(builder);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestParams params = new RequestParams();
                params.put("money", withdraw_money.getText().toString().trim());
                params.put("acct_name", bandAndCardInfo.getBrabank_name());
                params.put("card_no", bandAndCardInfo.getCard_no());
                params.put("terminal", 1);//标志，从APP传的参数
                NetWorkUtils.doPost(BaseInfo.withdraw, params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        if (response.optInt("code") == 1) {
                            /**
                             * 提现成功，服务器回调
                             */

                        NetWorkUtils.doGet(BaseInfo.ReturnUrlExtract, new JsonHttpResponseHandler() {
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                super.onSuccess(statusCode, headers, response);
                                Log.e("回调", response.toString());
                                if (response.optInt("code")==1){
                                    WithdrawActivity.this.finish();
                                }
                                if (response.optString("result")!=null){
                                    DialogManager.showNotice(WithdrawActivity.this,response.optString("result"));
                                }

                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                                super.onFailure(statusCode, headers, responseString, throwable);
                            }
                        });


                        }
                        if (response.optString("result") != null) {
                            DialogManager.showNotice(WithdrawActivity.this, response.optString("result"));
                        }

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        super.onFailure(statusCode, headers, responseString, throwable);
                        Log.e("responseString", responseString);
                    }
                });
            }
        });

    }

    /**
     * 加载数据
     */
    private void bindData() {
        if (bandAndCardInfo != null) {
            if (bandAndCardInfo.getPrepaid() != null) {
                money.setText(bandAndCardInfo.getPrepaid());
            }

            if (bandAndCardInfo.getBrabank_name() != null) {
                bank_name.setText(bandAndCardInfo.getBrabank_name());
            } else {
                bank_name.setText("");
            }
            if (bandAndCardInfo.getCard_no() != null) {
                card_no.setText(bandAndCardInfo.getCard_no());
            } else {
                card_no.setText("");
            }
        }else {

        }
    }


}
