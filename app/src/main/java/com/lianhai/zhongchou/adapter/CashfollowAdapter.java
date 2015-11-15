package com.lianhai.zhongchou.adapter;

import android.content.Context;
import android.widget.TextView;

import com.lianhai.zhongchou.R;
import com.lianhai.zhongchou.bean.CashFollowBean;
import com.lianhai.zhongchou.bean.TestBean;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by zaxcler on 15/10/16.
 */
public class CashfollowAdapter  extends  CommonAdapter<CashFollowBean>{

    public  CashfollowAdapter(Context context, List<CashFollowBean> list, int resource) {
        super(context, list, resource);
    }

    @Override
    public void setDataToItem(ViewHolder holder, CashFollowBean t) {
        TextView name = holder.getView(R.id.name);

        if (t.getNote()!=null){
            name.setText(t.getNote());
        }else {
            name.setText("");
        }
        TextView money = holder.getView(R.id.money);
        if (t.getType()==0){
            money.setText("+"+t.getMoney());
        }else {
            money.setText("-"+t.getMoney());
        }

        TextView type = holder.getView(R.id.type);
        String paytype="";
        switch (t.getPaytype()){
            case 0:
                paytype="投资";
                break;
            case 1:
                paytype="线下充值";
                break;
            case 2:
                paytype="线上充值";
                break;
            case 3:
                paytype="提现";
                break;
            case 4:
                paytype="其他";
                break;
            default:
                paytype="其他";
                break;
        }
        type.setText(paytype);

        TextView time = holder.getView(R.id.time);
        long time_date = Long.valueOf(t.getTime())*1000;
        Date date=new Date(time_date);
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        if (format.format(date)!=null) {
            time.setText(format.format(date));
        }else {
            time.setText("");
        }
    }
}
