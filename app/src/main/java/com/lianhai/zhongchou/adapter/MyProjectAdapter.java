package com.lianhai.zhongchou.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lianhai.zhongchou.R;
import com.lianhai.zhongchou.bean.ProjectBean;
import com.lianhai.zhongchou.config.BaseInfo;
import com.lianhai.zhongchou.config.MyApplication;
import com.lianhai.zhongchou.customview.DialogManager;
import com.lianhai.zhongchou.homepage.PayActivity;
import com.lianhai.zhongchou.homepage.ProjectDetailActivity;
import com.lianhai.zhongchou.homepage.ProtocolActivity;
import com.lianhai.zhongchou.utils.NetWorkUtils;
import com.lianhai.zhongchou.utils.NetworkHepler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.List;

public class MyProjectAdapter extends CommonAdapter<ProjectBean> {


	private int where=0;
	public MyProjectAdapter(Context context, List<ProjectBean> list, int resource) {
		super(context, list, resource);
	}

	public void setWhere(int where){
		this.where=where;
	}
	@Override
	public void setDataToItem(ViewHolder holder, final ProjectBean t) {
		if (t==null){
			return;
		}

		final Intent intent=new Intent();

		ImageView imageView1 = holder.getView(R.id.imageView1);
		ImageLoader.getInstance().displayImage(BaseInfo.BaseUrl_xu+t.getLogo(),imageView1, MyApplication.options_image);

		final TextView content = holder.getView(R.id.content);
		if (t.getName()!=null){
			content.setText(t.getName());
		}else {
			content.setText("");
		}
		ImageView success=holder.getView(R.id.success);

		TextView support = holder.getView(R.id.support);
		TextView delete = holder.getView(R.id.delete);
		success.setVisibility(View.INVISIBLE);
		switch (where){
			case 0:
				support.setVisibility(View.VISIBLE);
				switch (t.getStatus()){
					case 0:
						support.setText("签协议");
						break;
					case 1:
						support.setText("支付");
						break;
					case 2:
						delete.setVisibility(View.GONE);
						support.setVisibility(View.GONE);
						success.setVisibility(View.VISIBLE);
						break;
				}
				break;
			case 1:
				support.setVisibility(View.GONE);
				break;
			case 2:
				support.setVisibility(View.GONE);
				delete.setVisibility(View.GONE);
				break;
		}

		support.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				intent.putExtra("id", t.getId());
				switch (t.getStatus()){
					case 0:
						intent.setClass(context, ProtocolActivity.class);
						context.startActivity(intent);
						break;
					case 1:
						intent.setClass(context, PayActivity.class);
						context.startActivity(intent);
						break;
					case 2:
						break;
				}
			}
		});
		switch (where){
			case 0:
				delete.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						NetworkHepler hepler=new NetworkHepler();
						hepler.put("id",t.getId());
						NetWorkUtils.doGet(BaseInfo.Cancel_Stock, null, hepler, new JsonHttpResponseHandler() {
							@Override
							public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
								if (response.optInt("code") == 1) {
									list.remove(t);
									MyProjectAdapter.this.notifyDataSetChanged();
								}
								if (response.optString("result") != null) {
									DialogManager.showNotice(context, response.optString("result"));
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
				break;
			case 1:
				delete.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						NetworkHepler hepler = new NetworkHepler();
						hepler.put("id", t.getId());
						NetWorkUtils.doGet(BaseInfo.collection, null, hepler, new JsonHttpResponseHandler() {
							@Override
							public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
								super.onSuccess(statusCode, headers, response);
								if (response.optInt("code") == 1) {
									list.remove(t);
									MyProjectAdapter.this.notifyDataSetChanged();
								}
								if (response.optString("result") != null) {
									DialogManager.showNotice(context, response.optString("result"));
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

				break;
		}

		holder.getConvertView().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				if (t.getCheck_at()==0){
					DialogManager.showNotice(context,"该项目正在审核中...");
					return;
				}
				intent.putExtra("id", t.getId());
				intent.putExtra("type", t.getType());
				intent.setClass(context, ProjectDetailActivity.class);
				context.startActivity(intent);
			}
		});



	}





}
