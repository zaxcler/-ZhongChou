package com.lianhai.zhongchou.homepage;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.lianhai.zhongchou.R;
import com.lianhai.zhongchou.bean.BandAndCardInfo;
import com.lianhai.zhongchou.bean.OrderInfo;
import com.lianhai.zhongchou.pay.lianlianpay.LianLianPay;
import com.lianhai.zhongchou.utils.ZXUtils;

/**
 * Created by zaxlcer on 15/11/7.
 * 绑定银行卡页面
 */
public class LianLianPayActivity extends Activity{


    private TextView bank_name;//银行名称
    private TextView money;//支付金额
    private TextView card_no;//银行卡号
    private TextView notice;//提示
    private Button confirm;//确认支付

    private BandAndCardInfo bandAndCardInfo;//银行卡信息
    private OrderInfo info;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.lianlianpay_activity);
        ZXUtils.initTitle(this, "支付", false);
        intent=getIntent();
        bandAndCardInfo=intent.getParcelableExtra("bandAndCardInfo");
        info=intent.getParcelableExtra("orderinfo");
        findView();
        init();

    }

    private void findView() {
        money = (TextView) findViewById(R.id.money);
        bank_name = (TextView) findViewById(R.id.bank_name);
        card_no = (TextView) findViewById(R.id.card_no);
        notice = (TextView) findViewById(R.id.notice);
        confirm = (Button) findViewById(R.id.confirm);


    }

    private void init() {
        bindData();

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bandAndCardInfo==null){
                    return;
                }
                info.setCardId(bandAndCardInfo.getCard_no());
                LianLianPay lianLianPay=new LianLianPay(LianLianPayActivity.this);
                lianLianPay.pay(info);
            }
        });

    }

    private void bindData() {
        if (info!=null){
            if (info.getMoney()!=null){
                money.setText("金额："+info.getMoney()+"元");
            }else {
                money.setText("金额："+0.00+"元");
            }
        }
        if (bandAndCardInfo!=null){
            if (bandAndCardInfo.getPrepaid()!=null)


            if (bandAndCardInfo.getBrabank_name()!=null){
                bank_name.setText(bandAndCardInfo.getBrabank_name());
            }else {
                bank_name.setText("");
            }
            if (bandAndCardInfo.getCard_no()!=null){
                card_no.setText(bandAndCardInfo.getCard_no());
            }else {
                card_no.setText("");
            }
            if (bandAndCardInfo.getAcct_name()!=null){
                String name="*"+bandAndCardInfo.getAcct_name().substring(1,bandAndCardInfo.getAcct_name().length());
                Spannable sp=new SpannableString("只能选择，"+name+"的所有银行卡支付");
                sp.setSpan(new ForegroundColorSpan(Color.RED),5,5+name.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                notice.setText(sp);
            }else {
                String name="*"+bandAndCardInfo.getAcct_name();
                Spannable sp=new SpannableString("只能选择，"+name+"的所有银行卡支付");
                sp.setSpan(new ForegroundColorSpan(Color.RED),5,5+name.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                card_no.setText(sp);
            }

        }
    }
}
