package com.lianhai.zhongchou.mypage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import com.lianhai.zhongchou.R;
import com.lianhai.zhongchou.adapter.MyProjectAdapter;
import com.lianhai.zhongchou.bean.ProjectBean;
import com.lianhai.zhongchou.config.BaseInfo;
import com.lianhai.zhongchou.customview.DialogManager;
import com.lianhai.zhongchou.utils.JsonUtils;
import com.lianhai.zhongchou.utils.NetWorkUtils;
import com.lianhai.zhongchou.utils.NetworkHepler;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.List;

public class ProjectListFragment extends Fragment {
	private ListView listView;
	private MyProjectAdapter adapter;


	private String URL="";//请求数据的url
	private int where;

	private int offset=0;
	private int limit=10;
	private List<ProjectBean> beans;
	private boolean canLoad=false;

	private View notice;//提示信息

	public ProjectListFragment() {

	}


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle bundle=getArguments();
		if (bundle!=null){
			where=bundle.getInt("where");
			switch (where){
				case 0:
					URL= BaseInfo.Support_list;
					break;
				case 1:
					URL= BaseInfo.Collection_list;
					break;
				case 2:
					URL= BaseInfo.Sponsor_list;
					break;
		}

		}

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.fragment_project_list, null);
		findView(view);
		init();
		return view;
	}

	private void findView(View view) {
		listView = (ListView) view.findViewById(R.id.listView);
		notice = (View) view.findViewById(R.id.notice);
	}

	private void init() {
		loadData();
		listView.setOnScrollListener(new AbsListView.OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView absListView, int i) {

			}

			@Override
			public void onScroll(AbsListView absListView, int i, int i1, int i2) {
				if (i + i1 == i2 && canLoad) {
					loadMore(offset * limit);
				}
			}
		});

	}

	private void loadData() {

		NetWorkUtils.doGet(URL, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				super.onSuccess(statusCode, headers, response);
				Log.e("response", response.toString());
				if (response.optInt("code") == 1) {
					if (response.optJSONArray("body") != null) {
						beans = JsonUtils.getResultList(response.optJSONArray("body"), ProjectBean.class);
						bindData(beans);

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
	 * @param beans
	 */
	private void bindData(List<ProjectBean> beans) {
		if (beans==null || (beans!=null&&beans.size()==0)){
			notice.setVisibility(View.VISIBLE);
		}else {
			adapter=new MyProjectAdapter(getActivity(), beans, R.layout.project_item_my);
			adapter.setWhere(where);
			listView.setAdapter(adapter);
		}


	}

	/**
	 * 加载更多
	 */
	public void loadMore(int offset){
		NetworkHepler hepler=new NetworkHepler();
		hepler.put("offset",offset);
		hepler.put("limit",limit);
		NetWorkUtils.doGet(BaseInfo.BaseUrl_xu,null,hepler,new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				super.onSuccess(statusCode, headers, response);
				if (response.optInt("code")==1){
					List<ProjectBean> list = JsonUtils.getResultList(response.optJSONArray("body"), ProjectBean.class);
					if (list!=null) {
						beans.addAll(list);
						notice.setVisibility(View.GONE);
						canLoad=true;
					}else {
						canLoad=false;
						DialogManager.showNotice(getActivity(),"没有更多咯！");
					}
					adapter.notifyDataSetChanged();
				}
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
				super.onFailure(statusCode, headers, responseString, throwable);
			}
		});
	}



}
