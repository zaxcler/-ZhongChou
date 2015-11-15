package com.lianhai.zhongchou.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lianhai.zhongchou.R;
import com.lianhai.zhongchou.bean.ProjectBean;
import com.lianhai.zhongchou.config.BaseInfo;
import com.lianhai.zhongchou.config.MyApplication;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class MyProjectAdapter1 extends CommonAdapter<ProjectBean> {


	public MyProjectAdapter1(Context context, List<ProjectBean> list, int resource) {
		super(context, list, resource);
	}

	@Override
	public void setDataToItem(ViewHolder holder, final ProjectBean t) {
		ImageView imageView1 = holder.getView(R.id.imageView1);
		ImageLoader.getInstance().displayImage(BaseInfo.BaseUrl_xu+t.getLogo(),imageView1, MyApplication.options_image);

		TextView content = holder.getView(R.id.content);
		if (t.getName()!=null){
			content.setText(t.getName());
		}else {
			content.setText("");
		}

	}

}
