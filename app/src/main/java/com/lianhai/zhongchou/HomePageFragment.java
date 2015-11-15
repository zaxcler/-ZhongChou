package com.lianhai.zhongchou;

import java.util.ArrayList;
import java.util.List;

import com.lianhai.zhongchou.adapter.RecommendAdapter;
import com.lianhai.zhongchou.bean.HomePageBean;
import com.lianhai.zhongchou.bean.ProjectBean;
import com.lianhai.zhongchou.bean.TestBean;
import com.lianhai.zhongchou.config.BaseInfo;
import com.lianhai.zhongchou.customview.ImageCycleView;
import com.lianhai.zhongchou.customview.ImageCycleView.ImageCycleViewListener;
import com.lianhai.zhongchou.customview.MyListview;
import com.lianhai.zhongchou.customview.MyScrollView;
import com.lianhai.zhongchou.customview.MySwipleReflashLayout;
import com.lianhai.zhongchou.homepage.ProjectDetailActivity;
import com.lianhai.zhongchou.utils.JsonUtils;
import com.lianhai.zhongchou.utils.NetWorkUtils;
import com.lianhai.zhongchou.utils.NetworkHepler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

public class HomePageFragment extends Fragment implements View.OnClickListener{
	private ImageCycleView ad_view;
	private ImageView search;
	private LinearLayout project1;
	private LinearLayout project2;
	private LinearLayout project3;

	private MyListview listview;
	
	private MyScrollView scrollview;
	private MySwipleReflashLayout swipleReflashLayout;
	private LinearLayout childview;
	private ArrayList<String> bannerUrlList;
	private List<ProjectBean> projects;

	private int pageNo=1;
	private boolean isReflash=false;

	private RecommendAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.fragment_homepage, null);
		
		findView(view);
		init();
		return view;
	}

	private void findView(View view) {
		scrollview = (MyScrollView) view.findViewById(R.id.scrollview);
		swipleReflashLayout = (MySwipleReflashLayout) view.findViewById(R.id.swipleReflashLayout);
		childview = (LinearLayout) view.findViewById(R.id.childview);
		ad_view = (ImageCycleView) view.findViewById(R.id.ad_view);
		search = (ImageView) view.findViewById(R.id.search);
		project1 = (LinearLayout) view.findViewById(R.id.project1);
		project2 = (LinearLayout) view.findViewById(R.id.project2);
		project3 = (LinearLayout) view.findViewById(R.id.project3);
		listview = (MyListview) view.findViewById(R.id.listview);
	}

	private void init() {
		scrollview.requestChildFocus(childview, ad_view);
		swipleReflashLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				isReflash=true;
				loadData();

			}
		});
		loadData();
		project1.setOnClickListener(this);
		project2.setOnClickListener(this);
		project3.setOnClickListener(this);
	}

	private void loadData() {

		NetWorkUtils.doGet(BaseInfo.Home, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				super.onSuccess(statusCode, headers, response);
				if (response == null) {
					Log.e("访问主页", "获取数据失败！");
					return;
				}
				try {
					//code=1表示成功 0表示失败
					int code = response.getInt("code");
					if (code == 1) {
						/**
						 * 如果是刷新页面，成功则设置刷新状态为false
						 */
						if (isReflash){
							swipleReflashLayout.setRefreshing(false);
						}
						HomePageBean homePageBean = null;
						try {
							homePageBean = JsonUtils.getResult(response.getJSONObject("body"), HomePageBean.class);
						} catch (JSONException e) {
							e.printStackTrace();
						}
						Log.e("homePageBean", "homePageBean" + homePageBean.toString());
						bindData(homePageBean);
					} else {
						Toast.makeText(getActivity(), response.getString("result"), Toast.LENGTH_SHORT).show();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}

			}

			@Override
			public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
				super.onFailure(statusCode, headers, responseString, throwable);
				Log.e("访问主页", "连接不到服务器");
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
				super.onFailure(statusCode, headers, throwable, errorResponse);
			}
		});
	}

	/**
	 * 绑定数据
	 * @param homePageBean
	 */
	private void bindData(HomePageBean homePageBean) {
		bannerUrlList=new ArrayList<String>();
		if (homePageBean.getFocus()!=null){
			for (int i=0;i<homePageBean.getFocus().size();i++){
				bannerUrlList.add(BaseInfo.BaseUrl_xu +homePageBean.getFocus().get(i).getPath());
			}
		}
		ad_view.setImageResources(bannerUrlList, new ImageCycleViewListener() {
			@Override
			public void onImageClick(int position, View imageView) {

			}
		});

		if (homePageBean.getProject()!=null){
			projects=homePageBean.getProject();
			adapter=new RecommendAdapter(getActivity(), projects, R.layout.project_item);
			listview.setAdapter(adapter);

		}


	}


	@Override
	public void onClick(View view) {
		MainActivity mainActivity= (MainActivity) getActivity();
		mainActivity.main_content.setCurrentItem(1);
		switch (view.getId()){
			case R.id.project1:
				mainActivity.projectListFragment1.loadData(3,0,0,1,true);
				break;
			case R.id.project2:
				mainActivity.projectListFragment1.loadData(1,0,0,1,true);
				break;
			case R.id.project3:
				mainActivity.projectListFragment1.loadData(2,0,0,1,true);
				break;
		}
	}
}
