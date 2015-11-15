package com.lianhai.zhongchou.mypage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.lianhai.zhongchou.R;
import com.lianhai.zhongchou.adapter.FansFollowAdapter;
import com.lianhai.zhongchou.bean.UserInfo;
import com.lianhai.zhongchou.config.BaseInfo;
import com.lianhai.zhongchou.utils.JsonUtils;
import com.lianhai.zhongchou.utils.NetWorkHepler1;
import com.lianhai.zhongchou.utils.NetWorkUtils;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SupportFollowFragment extends Fragment implements View.OnClickListener{
	private ListView listView;
	private int id;//userzoneactivity传过来的id
	private FansFollowAdapter fansadapter;
	private FansFollowAdapter followadapter;


	private TextView follow;
	private TextView fans;
	private View scrollbar;

	private List<UserInfo> followlist;
	private List<UserInfo> fanslist;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle bundle=getArguments();
		id=bundle.getInt("id",0);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View view=inflater.inflate(R.layout.fragment_support_follow, null);
		findView(view);
		init();
		return view;
	}

	private void findView(View view) {
		listView = (ListView) view.findViewById(R.id.listView);
		follow = (TextView) view.findViewById(R.id.follow);
		fans = (TextView) view.findViewById(R.id.fans);
	}

	private void init() {
		followlist=new ArrayList<UserInfo>();
		fanslist=new ArrayList<UserInfo>();

		follow.setOnClickListener(this);
		fans.setOnClickListener(this);
		follow.setTextColor(getResources().getColor(R.color.blue));
//		loadData("lookme", "sid");
		loadData("mylook", "uid");


	}

	private void loadData(String a, final String param_name) {

		NetWorkHepler1 hepler1=new NetWorkHepler1();
		hepler1.put("m", "UserLook");
		hepler1.put("a", a);
		hepler1.put("" + param_name, id);

		NetWorkUtils.doGet(BaseInfo.BaseUrl_jin, hepler1, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				super.onSuccess(statusCode, headers, response);
				if (response.optInt("code") == 1) {
					if (response.optString("body") != null) {
						List<UserInfo> list = JsonUtils.getResultList(response.optString("body"), UserInfo.class);

						if (list != null) {
							bindData(list, param_name);
						}else {
							/**
							 * 如果没有数据，创建一个空的数据
							 */
							List<UserInfo> null_list=new ArrayList<UserInfo>();
							bindData(null_list, param_name);
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
	private void bindData(final List<UserInfo> list,String param_name) {
		/**
		 * 获取到数据，第一次显示关注好友，其他的保存起来，在点击的时候调用
		 */
		if ("sid".equals(param_name)) {
			if (followadapter==null){
				followadapter=new FansFollowAdapter(getActivity(), list, R.layout.fans_follow_item);
			}
			listView.setAdapter(followadapter);
			fans.setText("粉丝（" + list.size() + ")");

		} else if ("uid".equals(param_name)) {
			if(fansadapter==null){
				fansadapter=new FansFollowAdapter(getActivity(), list, R.layout.fans_follow_item);
			}
			listView.setAdapter(fansadapter);
			follow.setText("关注好友（" + list.size() + ")");
		}
		Log.e("list", list.toString());


		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				Intent intent = new Intent();
				intent.setClass(getActivity(), UserZoneActivity.class);
				intent.putExtra("sid", list.get(arg2).getId());
				startActivity(intent);
			}
		});
	}


	@Override
	public void onClick(View view) {
		clearState();
		switch (view.getId()){
			case R.id.fans:
				fans.setTextColor(getResources().getColor(R.color.blue));
				loadData("lookme", "sid");
				break;
			case R.id.follow:
				follow.setTextColor(getResources().getColor(R.color.blue));
				loadData("mylook", "uid");
				break;
		}
	}

	/**
	 * 清楚选中状态
	 */
	public void clearState(){
		fans.setTextColor(getResources().getColor(R.color.blacktext));
		follow.setTextColor(getResources().getColor(R.color.blacktext));
	}
}
