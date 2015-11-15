package com.lianhai.zhongchou;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.lianhai.zhongchou.adapter.RecommendAdapter;
import com.lianhai.zhongchou.bean.ProjectBean;
import com.lianhai.zhongchou.config.BaseInfo;
import com.lianhai.zhongchou.customview.MySpinner1;
import com.lianhai.zhongchou.customview.SearchView;
import com.lianhai.zhongchou.utils.JsonUtils;
import com.lianhai.zhongchou.utils.NetWorkUtils;
import com.lianhai.zhongchou.utils.NetworkHepler;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProjectListFragment1 extends Fragment implements View.OnClickListener{
	private ListView listView;
	private MySpinner1 sort1;
	private MySpinner1 sort2;
	private MySpinner1 sort3;
	private RecommendAdapter adapter;
	private SearchView searchview;
	private ImageView search;

	private SwipeRefreshLayout swipleReflashLayout;

	private View notice;//提示信息

	private boolean canload =false;//是否可以加载

	private int c=0;
	private int s=0;
	private int h=0;
	private int p=1;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.fragment_project_list_1, null);
		findView(view);
		init();
		return view;
	}

	private void findView(View view) {

		listView = (ListView) view.findViewById(R.id.listView);
		sort1 = (MySpinner1) view.findViewById(R.id.sort1);
		sort2 = (MySpinner1) view.findViewById(R.id.sort2);
		sort3 = (MySpinner1) view.findViewById(R.id.sort3);
		searchview = (SearchView) view.findViewById(R.id.searchview);
		search = (ImageView) view.findViewById(R.id.search);
		notice = (View) view.findViewById(R.id.notice);
		swipleReflashLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipleReflashLayout);
	}

	private void init() {

		loadData(c, s, h, p, true);


		ArrayList<String> sort1_list=new ArrayList<String>();
		sort1_list.add("分类");
		sort1_list.add("股权");
		sort1_list.add("消费");
		sort1_list.add("公益");
		sort1.setList(sort1_list);
		ArrayList<String> sort2_list=new ArrayList<String>();
		sort2_list.add("阶段");
		sort2_list.add("预热中");
		sort2_list.add("众筹中");
		sort2_list.add("已成功");

		sort2.setList(sort2_list);
		ArrayList<String> sort3_list=new ArrayList<String>();
		sort3_list.add("排序");
		sort3_list.add("最新上线");
		sort3_list.add("支持最多");
		sort3_list.add("金额最高");
		sort3.setList(sort3_list);

		sort1.setOnClickListener(this);
		sort2.setOnClickListener(this);
		sort3.setOnClickListener(this);

		sort1.setClickListener(new MySpinner1.onSpinnerClickListener() {
			@Override
			public void onClick(int position) {
				c = position;
				p = 1;
				loadData(c, s, h, p, true);
			}
		});
		sort2.setClickListener(new MySpinner1.onSpinnerClickListener() {
			@Override
			public void onClick(int position) {
				s=position;
				p=1;
				loadData(c,s,h,p,true);
			}
		});
		sort3.setClickListener(new MySpinner1.onSpinnerClickListener() {
			@Override
			public void onClick(int position) {
				h=position;
				p=1;
				loadData(c,s,h,p,true);
			}
		});

		search.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				searchview.setVisibility(View.VISIBLE);
			}
		});

		swipleReflashLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				loadData(c,s,h,p,false);
			}
		});

		/**
		 * 点击搜索消失，监听器
		 */
		searchview.setSearchLisenter(new SearchView.SearchLisenter() {
			@Override
			public void doSearch(View view, CharSequence currentText) {

				NetworkHepler hepler=new NetworkHepler();
				hepler.put("kw",currentText);
				NetWorkUtils.doGet(BaseInfo.Search, null, hepler, new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
						super.onSuccess(statusCode, headers, response);
						Log.e("response", response.toString());
						if (response.optInt("code") == 1) {
							if (response.optJSONArray("body") != null) {
								ArrayList<ProjectBean> projects = (ArrayList<ProjectBean>) JsonUtils.getResultList(response.optJSONArray("body"), ProjectBean.class);
								if (projects != null) {
									bindDate(projects, true);
								} else {
									p = 1;
								}

							} else {
								p = 1;
							}

						} else {
							/**
							 * 没有数据，就回到第一页
							 */
							p = 1;
						}
					}

					@Override
					public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
						super.onFailure(statusCode, headers, responseString, throwable);
						/**
						 * 失败，就回到第一页
						 */
						p=1;
					}
				});
				searchview.setVisibility(View.GONE);
			}
		});

		/**
		 * 实时搜索
		 */
		searchview.setTextChangeLisenter(new SearchView.TextChangeLisenter() {
			@Override
			public void onTextChange(CharSequence result) {

			}
		});

		listView.setOnScrollListener(new AbsListView.OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView absListView, int i) {
				Log.e("state","onScrollStateChanged");
				//在滑动,并且手指在屏幕上
				if (i==SCROLL_STATE_TOUCH_SCROLL){
					Log.e("state","SCROLL_STATE_TOUCH_SCROLL");
				}
				//停止滚动
				if (i==SCROLL_STATE_IDLE){
					Log.e("state", "SCROLL_STATE_IDLE");
					if (canload){
						loadData(c, s, h,++p, false);
					}

				}
				//惯性滚动，手指离开屏幕
				if (i==SCROLL_STATE_FLING){
					Log.e("state","SCROLL_STATE_FLING");
				}
			}

			@Override
			public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

				Log.e("result", (firstVisibleItem + visibleItemCount == totalItemCount ) + "");
				Log.e("log", "firstVisibleItem" + firstVisibleItem + " visibleItemCount " + visibleItemCount + "totalItemCount" + totalItemCount);

				if (firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount>0) {
					canload=true;
					Log.e("state","canload"+ canload);
				}

				/**
				 * 动态控制swiplereflashlayout是否下拉加载
				 */
				View view= absListView.getChildAt(0);
				// 当firstVisibleItem是第0位。如果view==null说明列表为空，需要刷新;或者top==0说明已经到达列表顶部, 也需要刷新

				if (firstVisibleItem == 0 && (view==null || view.getTop()==0 )) {
					swipleReflashLayout.setEnabled(true);
				}else{
					swipleReflashLayout.setEnabled(false);
			}
		}
		});


	}

	public void loadData(int c,int s,int h,int p, final boolean reflash) {
//		RequestParams params=new RequestParams();
//		params.put("c",c);//分类
//		params.put("s", s);//阶段
//		params.put("h", h);//排序
//		params.put("p", p);//页数
		NetworkHepler params=new NetworkHepler();
		params.put("c",c);//分类
		params.put("s", s);//阶段
		params.put("h", h);//排序
		params.put("p", p);//页数
		NetWorkUtils.doGet(BaseInfo.Project_list, null,params, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				super.onSuccess(statusCode, headers, response);

				Log.e("response",response.toString());
				if (response != null) {
					if (response.optInt("code") == 1) {
						ArrayList<ProjectBean> projects = (ArrayList<ProjectBean>) JsonUtils.getResultList(response.optJSONArray("body"), ProjectBean.class);
						if (projects != null){
							bindDate(projects,reflash);
						}else {
							/**
							 * 没有数据，就回到第一页
							 */
							ProjectListFragment1.this.p=1;
						}


					} else {
						/**
						 * 没有数据，就回到第一页
						 */
						ProjectListFragment1.this.p=1;
						Toast.makeText(getActivity(), response.optString("result"), Toast.LENGTH_SHORT).show();
					}
				}

			}

			@Override
			public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
				super.onFailure(statusCode, headers, responseString, throwable);
				/**
                 * 失败，就回到第一页
				 */
				ProjectListFragment1.this.p=1;
				Toast.makeText(getActivity(),responseString,Toast.LENGTH_SHORT).show();
				Log.e("responseString",responseString);
			}
		});

	}

	private void bindDate(final ArrayList<ProjectBean> projects,boolean reflash) {
		swipleReflashLayout.setRefreshing(false);
		if (adapter==null) {
			adapter = new RecommendAdapter(getActivity(), projects, R.layout.project_item);
			if (projects.size()!=0){
				notice.setVisibility(View.GONE);
			}
			listView.setAdapter(adapter);
		}else {
			/**
			 * 是刷新或者下拉加载 true刷新 false下拉加载
			 */
			if (reflash){
				/**
				 *没有项目就加载推荐项目
				 */
				if (projects.size()==0){
					/**
					 * 没有搜到项目就添加hearview
					 */
					notice.setVisibility(View.VISIBLE);
					loadData(0,0,0,1,false);
				}else {
					/**
					 * 有搜到项目就去掉headview
					 */
					notice.setVisibility(View.GONE);

				}
				adapter.replaceList(projects);
			}else {

				adapter.addList(projects);
			}
			adapter.notifyDataSetChanged();
		}


	}

	@Override
	public void onClick(View v) {



	}
}
