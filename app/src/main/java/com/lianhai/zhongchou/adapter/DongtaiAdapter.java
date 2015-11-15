package com.lianhai.zhongchou.adapter;

import android.content.Context;

import com.lianhai.zhongchou.bean.TestBean;

import java.util.List;

/**
 * Created by zaxlcer on 15/11/12.
 */
public class DongtaiAdapter extends CommonAdapter<TestBean>{

    protected DongtaiAdapter(Context context, List<TestBean> list, int resource) {
        super(context, list, resource);
    }

    @Override
    public void setDataToItem(ViewHolder holder, TestBean testBean) {

    }
}
