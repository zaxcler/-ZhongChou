package com.lianhai.zhongchou.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.lianhai.zhongchou.R;
import com.lianhai.zhongchou.bean.InvestsItemInfo;
import com.lianhai.zhongchou.config.BaseInfo;
import com.lianhai.zhongchou.config.MyApplication;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by zaxcler on 15/11/5.
 */
public class InvestsItemAdapter extends CommonAdapter<InvestsItemInfo>{

    public  InvestsItemAdapter(Context context, List<InvestsItemInfo> list, int resource) {
        super(context, list, resource);
    }

    @Override
    public void setDataToItem(ViewHolder holder, InvestsItemInfo investsItemInfo) {

        ImageView touxiang=holder.getView(R.id.touxiang);
        ImageLoader.getInstance().displayImage(BaseInfo.BaseUrl_xu+investsItemInfo.getGravatar(),touxiang, MyApplication.options_image);

        TextView name=holder.getView(R.id.name);
        if (investsItemInfo.getName()!=null){
            name.setText(investsItemInfo.getName());
        }else {
            name.setText("");
        }

        TextView money = holder.getView(R.id.money);
        if (investsItemInfo.getPremoney()!=null){
            money.setText(investsItemInfo.getPremoney());
        }else {
            money.setText("0.00");
        }

    }
}
