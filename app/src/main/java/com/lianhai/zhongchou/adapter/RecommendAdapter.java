package com.lianhai.zhongchou.adapter;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

import com.lianhai.zhongchou.R;

/**
 * 首页推荐列表的adapter
 */

import com.lianhai.zhongchou.bean.ProjectBean;
import com.lianhai.zhongchou.config.BaseInfo;
import com.lianhai.zhongchou.config.MyApplication;
import com.lianhai.zhongchou.homepage.ProjectDetailActivity;
import com.lianhai.zhongchou.utils.TimeCounter;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class RecommendAdapter extends CommonAdapter<ProjectBean>{



	public RecommendAdapter(Context context, List<ProjectBean> list, int resource) {
		super(context, list, resource);
		// TODO Auto-generated constructor stub
	}

	public void replaceList( List<ProjectBean> list){
		this.list.clear();
		this.list.addAll(list);
	}
	public void addList( List<ProjectBean> list){
		this.list.addAll(list);
	}


	@Override
	public void setDataToItem(ViewHolder holder, final ProjectBean t) {
		//取消虚线的硬件加速，否则虚线显示为实线
		holder.getView(R.id.dashed_line).setLayerType(View.LAYER_TYPE_SOFTWARE, null);

		ImageView imageView = holder.getView(R.id.imageView1);

		ImageLoader.getInstance().displayImage(BaseInfo.BaseUrl_xu +t.getLogo(), imageView,MyApplication.options_image);

			TextView description = holder.getView(R.id.description);
		if (t.getName()!=null){
			description.setText(t.getName());
		}

		TextView Sponsor = holder.getView(R.id.Sponsor);
		if (t.getUsername()!=null){
			Sponsor.setText(t.getUsername());
		}

		TextView state = holder.getView(R.id.state);
		getState(t.getCheck_at(),t.getStart_at(),t.getEnd_at(),state,t.getPre_value(),t.getRealMoney());


		TextView percent = holder.getView(R.id.percent);
		ProgressBar progressBar=holder.getView(R.id.progressbar);
		ImageView chaomu = holder.getView(R.id.chaomu);
		chaomu.setVisibility(View.GONE);
		if (t.getPre_value()!=0){
			DecimalFormat format=new DecimalFormat("##.00");
			String p;
			/**
			 * 防止出现  .93%  这种数据
			 */
			if (t.getTotalMoney() / t.getPre_value() * 100<1){
				p=0+"";
			}else {
				p=format.format(t.getTotalMoney() / t.getPre_value() * 100);
			}

			percent.setText(p+"%");
			if (t.getTotalMoney()==0){
				percent.setText(0+"%");
			}
			if(t.getTotalMoney()/t.getPre_value()>1){
				progressBar.setProgress(100);
				chaomu.setVisibility(View.VISIBLE);
			}else {
				double pg=  t.getTotalMoney()/t.getPre_value()*100;
				progressBar.setProgress((int) pg);
			}
		}


		TextView totle_money = holder.getView(R.id.totle_money);


		DecimalFormat doubleformat=new DecimalFormat("0");
		double totlemoney=new Double(t.getTotalMoney()+"");
		if (totlemoney>10000){
			totlemoney=totlemoney/10000;
			totle_money.setText("￥"+doubleformat.format(totlemoney)+"万元");
		}else {
			totle_money.setText("￥"+doubleformat.format(totlemoney)+"元");
		}



		TextView left_day = holder.getView(R.id.left_day);
		long end_day=Long.valueOf(t.getEnd_at())*1000;
		Date end_date=new Date(end_day);
		Date currentdate=new Date(System.currentTimeMillis());
		int leftday=TimeCounter.countTimeOfDay(currentdate,end_date);
		left_day.setText(leftday+"天");

		holder.getConvertView().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent=new Intent(context, ProjectDetailActivity.class);
				intent.putExtra("type",t.getType());
				intent.putExtra("id",t.getId());
				context.startActivity(intent);

			}
		});









	}

	/**
	 * 计算项目状态
	 * @param check_at
	 * @param start_at
	 * @param end_at
	 * @return
	 */
	public String getState(int check_at,int start_at,int end_at,TextView state_tv,double preMoney,double realmoney){
		state_tv.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.solid_orange));
		long long_check_at = Long.valueOf(check_at)*1000;
		long long_start_at = Long.valueOf(start_at)*1000;
		long long_end_at   = Long.valueOf(end_at)*1000;

		String state="";
		Date check_at_date = new Date(long_check_at);
		Date start_at_date = new Date(long_start_at);
		Date end_at_date   = new Date(long_end_at);
		Date currentdate   = new Date(System.currentTimeMillis());
		long d=System.currentTimeMillis();
		if (TimeCounter.compareDate(end_at_date,currentdate)>0){
			if (realmoney>=preMoney){
				state="已成功";
				state_tv.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.corners_green));
			}else {
				state="失败";
				state_tv.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.solid_orange));
			}


		}else if (TimeCounter.compareDate(start_at_date,currentdate)>0&& TimeCounter.compareDate(end_at_date,currentdate)<0){
			state="众筹中";
		}else if (TimeCounter.compareDate(check_at_date,currentdate)>0&& TimeCounter.compareDate(start_at_date,currentdate)<0){
			state="预热中";
		}
		state_tv.setText(state);
		return state;
	}

}
