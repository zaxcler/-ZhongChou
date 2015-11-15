package com.lianhai.zhongchou.mypage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ListView;

import com.lianhai.zhongchou.R;
import com.lianhai.zhongchou.adapter.FansFollowAdapter;
import com.lianhai.zhongchou.bean.UserInfo;
import com.lianhai.zhongchou.config.BaseInfo;
import com.lianhai.zhongchou.config.MyApplication;
import com.lianhai.zhongchou.utils.JsonUtils;
import com.lianhai.zhongchou.utils.NetWorkHepler1;
import com.lianhai.zhongchou.utils.NetWorkUtils;
import com.lianhai.zhongchou.utils.ZXUtils;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.List;

public class FansOrFollowListActivity extends Activity {
	private ListView listView;
	private int type;//0:关注我的人 1：我关注的人
	private Intent intent;
	private String a;//方法名
	private String param_name;//参数名
	private View notice;//提示

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.common_list);
		intent=getIntent();
		type=intent.getIntExtra("type",0);
		switch (type){
			case 0:
				ZXUtils.initTitle(this, "关注我的人", false);
				a="lookme";
				param_name="sid";
				break;
			case 1:
				ZXUtils.initTitle(this, "我关注的人", false);
				a="mylook";
				param_name="uid";
				break;
		}

		findView();
		init();
	}

	private void findView() {
		listView = (ListView) findViewById(R.id.listView);
		notice = (View) findViewById(R.id.notice);

	}

	private void init() {
		loadData();
	}

	private void loadData() {
		NetWorkHepler1 hepler1=new NetWorkHepler1();
		hepler1.put("m","UserLook");
		hepler1.put("a",a);

		hepler1.put("" + param_name, MyApplication.preferences.getInt("UserId", 0));
		NetWorkUtils.doGet(BaseInfo.BaseUrl_jin, hepler1, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				super.onSuccess(statusCode, headers, response);
				if (response.optInt("code") == 1) {
					if (response.optString("body") != null) {
						List<UserInfo> list = JsonUtils.getResultList(response.optString("body"), UserInfo.class);
						bindData(list);
					}
				}
			}
			@Override
			public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
				super.onFailure(statusCode, headers, responseString, throwable);
				Log.e("responseString",responseString.toString());
			}
		});
	}

	private void bindData(final List<UserInfo> list) {
		if (list==null){
			notice.setVisibility(View.VISIBLE);
		}else {
			FansFollowAdapter adapter=new FansFollowAdapter(this, list, R.layout.fans_follow_item);
			listView.setAdapter(adapter);
		}

	}

	@Override
	protected void onResume() {
		super.onResume();
		loadData();
	}
}
