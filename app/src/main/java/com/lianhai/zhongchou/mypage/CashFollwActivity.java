package com.lianhai.zhongchou.mypage;

import com.lianhai.zhongchou.R;
import com.lianhai.zhongchou.adapter.CardAdapter;
import com.lianhai.zhongchou.adapter.CashfollowAdapter;
import com.lianhai.zhongchou.bean.CashFollowBean;
import com.lianhai.zhongchou.bean.TestBean;
import com.lianhai.zhongchou.config.BaseInfo;
import com.lianhai.zhongchou.utils.JsonUtils;
import com.lianhai.zhongchou.utils.NetWorkUtils;
import com.lianhai.zhongchou.utils.NetworkHepler;
import com.lianhai.zhongchou.utils.ZXUtils;
import com.loopj.android.http.JsonHttpResponseHandler;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.ListView;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CashFollwActivity extends Activity {
	private ListView listview;
	private int offset=0;//偏移量
	private int limit=10;//页限制
	private CashfollowAdapter adapter;//
	private List<CashFollowBean> cashFollowBeans;//
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.address_or_card_list_activity);
		ZXUtils.initTitle(this, "现金流水", false);
		findView();
		init();
		
	}

	private void findView() {
		listview = (ListView) findViewById(R.id.listview);
	}

	private void init() {

		loadData();
//		listview.setOnScrollListener(new AbsListView.OnScrollListener() {
//			@Override
//			public void onScrollStateChanged(AbsListView absListView, int i) {
//
//			}
//
//			@Override
//			public void onScroll(AbsListView absListView, int i, int i1, int i2) {
//				if (i+i1==i2){
//					offset++;
//					loadMore(offset*limit);
//				}
//			}
//		});

	}

	/**
	 * 加载数据
	 */
	private void loadData() {
		NetWorkUtils.doGet(BaseInfo.swift, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				super.onSuccess(statusCode, headers, response);
				Log.e("response", response.toString());
				if (response.optInt("code") == 1) {
					if (response.optJSONArray("body") != null) {
						List<CashFollowBean> cashFollowBeans = JsonUtils.getResultList(response.optJSONArray("body"), CashFollowBean.class);
						if (cashFollowBeans != null) {
							bindData(cashFollowBeans);
						}
					}
				}
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
				super.onFailure(statusCode, headers, responseString, throwable);
				Log.e("responseString", responseString.toString());
			}
		});
	}

	/**
	 * 绑定数据
	 * @param cashFollowBeans
	 */
	private void bindData(List<CashFollowBean> cashFollowBeans) {

		adapter=new CashfollowAdapter(this,cashFollowBeans,R.layout.cashfollow_item);
		listview.setAdapter(adapter);
	}

	/**
	 * 加载更多
	 */
	public void loadMore(int offset){
		NetworkHepler hepler=new NetworkHepler();
		hepler.put("offset", offset);
		NetWorkUtils.doGet(BaseInfo.swift,null,hepler, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				super.onSuccess(statusCode, headers, response);
				Log.e("response", response.toString());
				if (response.optInt("code") == 1) {
					if (response.optJSONArray("body") != null) {
						List<CashFollowBean> beans= JsonUtils.getResultList(response.optJSONArray("body"), CashFollowBean.class);
						if (cashFollowBeans != null) {
							cashFollowBeans.addAll(beans);
							adapter.notifyDataSetChanged();
						}
					}
				}
			}
			@Override
			public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
				super.onFailure(statusCode, headers, responseString, throwable);
				Log.e("responseString", responseString.toString());
			}
		});

	}



}
