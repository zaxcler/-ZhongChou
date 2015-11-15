package com.lianhai.zhongchou.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lianhai.zhongchou.R;
import com.lianhai.zhongchou.bean.Repay;
import com.lianhai.zhongchou.bean.TestBean;
import com.lianhai.zhongchou.homepage.ProjectDetailActivity;
import com.lianhai.zhongchou.homepage.SupportBuyGYXFActivity;
import com.lianhai.zhongchou.utils.ZXUtils;

import java.util.List;

public class GYSupportAdapter extends CommonAdapter<Repay> {

	public GYSupportAdapter(Context context, List<Repay> list, int resource) {
		super(context, list, resource);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setDataToItem(ViewHolder holder, final Repay t) {

		TextView money=holder.getView(R.id.money);
		money.setText("￥" + t.getMoney() + "元");

		TextView support_num=holder.getView(R.id.support_num);
		support_num.setText(list.size()+"人支持");

		TextView limit_num=holder.getView(R.id.limit_num);
		limit_num.setText("（限购" + t.getNum() + "人)");

		TextView support_desc=holder.getView(R.id.support_desc);
		support_desc.setText(t.getContent());

		Button support=holder.getView(R.id.support);
		support.setText("支持￥："+t.getMoney());

		support.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ProjectDetailActivity activity=(ProjectDetailActivity) context;
				Intent intent=new Intent();
				intent.setClass(activity, SupportBuyGYXFActivity.class);
				intent.putExtra("id", activity.id);//项目id
				intent.putExtra("repay",t);
				activity.startActivity(intent);
			}
		});
	}

}
