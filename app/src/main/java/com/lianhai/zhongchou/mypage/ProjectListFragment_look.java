package com.lianhai.zhongchou.mypage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.lianhai.zhongchou.R;
import com.lianhai.zhongchou.adapter.MyProjectAdapter1;
import com.lianhai.zhongchou.bean.ProjectBean;
import com.lianhai.zhongchou.config.BaseInfo;
import com.lianhai.zhongchou.utils.JsonUtils;
import com.lianhai.zhongchou.utils.NetWorkUtils;
import com.lianhai.zhongchou.utils.NetworkHepler;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.List;

public class ProjectListFragment_look extends Fragment {
	private ListView listView;


	private String URL="";//请求数据的url
	private int where;
	private int id;


	public ProjectListFragment_look() {

	}


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle bundle=getArguments();
		id=bundle.getInt("id",0);
		if (bundle!=null){
			where=bundle.getInt("where");
			switch (where){
				case 0:
					URL= BaseInfo.Support_list;
					break;
				case 1:
					URL= BaseInfo.Sponsor_list;
					break;
		}

		}

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.fragment_project_list_2, null);
		findView(view);
		init();
		return view;
	}

	private void findView(View view) {
		listView = (ListView) view.findViewById(R.id.listView);
	}

	private void init() {
		loadData();

	}

	private void loadData() {

		NetworkHepler hepler=new NetworkHepler();
		hepler.put("isMy",0);
		hepler.put("id",id);

		NetWorkUtils.doGet(URL,null,hepler, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				super.onSuccess(statusCode, headers, response);
				Log.e("response", response.toString());
				if (response.optInt("code") == 1) {
					if (response.optJSONArray("body") != null) {
						List<ProjectBean> beans = JsonUtils.getResultList(response.optJSONArray("body"), ProjectBean.class);
						if (beans!=null)
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

		MyProjectAdapter1 adapter=new MyProjectAdapter1(getActivity(), beans, R.layout.project_item_look);
		listView.setAdapter(adapter);


	}

}
