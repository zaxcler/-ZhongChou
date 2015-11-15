package com.lianhai.zhongchou.adapter;

import android.content.Context;

import com.lianhai.zhongchou.bean.TestBean;

import java.util.List;

/**
 * Created by zaxcler on 15/11/4.
 */
public class CardAdapter extends CommonAdapter<TestBean>{

    public CardAdapter(Context context, List<TestBean> list, int resource) {
        super(context, list, resource);
    }

    @Override
    public void setDataToItem(ViewHolder holder, TestBean testBean) {

    }
}
