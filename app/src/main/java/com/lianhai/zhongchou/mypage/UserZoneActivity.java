package com.lianhai.zhongchou.mypage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lianhai.zhongchou.R;
import com.lianhai.zhongchou.adapter.FragmentAdapter;
import com.lianhai.zhongchou.bean.UserInfo;
import com.lianhai.zhongchou.config.BaseInfo;
import com.lianhai.zhongchou.config.MyApplication;
import com.lianhai.zhongchou.utils.JsonUtils;
import com.lianhai.zhongchou.utils.NetWorkHepler1;
import com.lianhai.zhongchou.utils.NetWorkUtils;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.apache.http.Header;
import org.json.JSONObject;

public class UserZoneActivity extends FragmentActivity  implements OnClickListener{
	
	private ViewPager viewpager;
//	private MyViewPager viewpager;
	
	private ImageView sanjiao1;
	private ImageView sanjiao2;
	private ImageView sanjiao3;
	
	private LinearLayout item1;
	private LinearLayout item2;
	private LinearLayout item3;
	private LinearLayout linearLayout1;//关注的linearLayout

	private ImageView user_photo;//用户头像
	private ImageView add;//添加
	private ImageView back;//返回
	private TextView user_name;//用户昵称
	private TextView follow;//关注

	private int sid;//被浏览用户sid
	private UserInfo userInfo;//被关注用户的信息
	private Intent intent;//

	private boolean flag=false;//是否已关注

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.userzone_activity);

		intent=getIntent();
		sid=intent.getIntExtra("sid",0);
		findView();
		init();
	}

	private void findView() {
//		viewpager = (MyViewPager) findViewById(R.id.viewpager);
		viewpager = (ViewPager) findViewById(R.id.viewpager);

		sanjiao1 = (ImageView) findViewById(R.id.sanjiao1);
		sanjiao2 = (ImageView) findViewById(R.id.sanjiao2);
		sanjiao3 = (ImageView) findViewById(R.id.sanjiao3);
		
		item1 = (LinearLayout) findViewById(R.id.item1);
		item2 = (LinearLayout) findViewById(R.id.item2);
		item3 = (LinearLayout) findViewById(R.id.item3);
		linearLayout1 = (LinearLayout) findViewById(R.id.linearLayout1);

		follow = (TextView) findViewById(R.id.follow);
		add = (ImageView) findViewById(R.id.add);
		back = (ImageView) findViewById(R.id.back);
		user_photo = (ImageView) findViewById(R.id.user_photo);
		user_name = (TextView) findViewById(R.id.user_name);

	}

	private void init() {
		
		item1.setOnClickListener(this);
		item2.setOnClickListener(this);
		item3.setOnClickListener(this);
		follow.setOnClickListener(this);
		back.setOnClickListener(this);
		linearLayout1.setOnClickListener(this);
		Log.e("sid",sid+" -------");

		LoadUserInfo();



		NetWorkHepler1 hepler1=new NetWorkHepler1();
		hepler1.put("m","UserLook");
		hepler1.put("a","isLook");
		hepler1.put("sid",sid);

		NetWorkUtils.doGet(BaseInfo.BaseUrl_jin,hepler1,new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				super.onSuccess(statusCode, headers, response);
				if (response.optInt("code")==1){
					follow.setText("已关注");
					add.setVisibility(View.GONE);
					flag=true;
				}else {
					follow.setText("关注");
					add.setVisibility(View.VISIBLE);
					flag=false;
				}
				Log.e("flag",flag+"");
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
				super.onFailure(statusCode, headers, responseString, throwable);
				Log.e("responseString",responseString.toString());
			}
		});

		
		FragmentAdapter adapter =new FragmentAdapter(getSupportFragmentManager());
		ProjectListFragment_look fragment1=new ProjectListFragment_look();
		Bundle bundle1=new Bundle();
		bundle1.putInt("where", 0);
		bundle1.putInt("id",sid);
		fragment1.setArguments(bundle1);
		adapter.fragments.add(fragment1);

		ProjectListFragment_look fragment2=new ProjectListFragment_look();
		Bundle bundle2=new Bundle();
		bundle2.putInt("where", 1);
		bundle2.putInt("id", sid);
		fragment2.setArguments(bundle2);
		adapter.fragments.add(fragment2);

		SupportFollowFragment fragment3=new SupportFollowFragment();
		Bundle bundle3=new Bundle();
		bundle3.putInt("id", sid);
		fragment3.setArguments(bundle3);
		adapter.fragments.add(fragment3);

		viewpager.setAdapter(adapter);
		viewpager.setOffscreenPageLimit(3);
		viewpager.addOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				clearState();
				switch (arg0) {
				case 0:
					sanjiao1.setVisibility(View.VISIBLE);
					viewpager.setCurrentItem(0);
					break;
				case 1:
					sanjiao2.setVisibility(View.VISIBLE);
					viewpager.setCurrentItem(1);
					break;
				case 2:
					sanjiao3.setVisibility(View.VISIBLE);
					viewpager.setCurrentItem(2);
					break;
				}
				
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				
			}
		});
		
	}



	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.item1:
			clearState();
			sanjiao1.setVisibility(View.VISIBLE);
			viewpager.setCurrentItem(0);
			break;
		case R.id.item2:
			clearState();
			sanjiao2.setVisibility(View.VISIBLE);
			viewpager.setCurrentItem(1);
			break;
		case R.id.item3:
			clearState();
			sanjiao3.setVisibility(View.VISIBLE);
			viewpager.setCurrentItem(2);
			break;
		case R.id.linearLayout1:
			if (!flag){
				NetWorkHepler1 hepler1=new NetWorkHepler1();
				hepler1.put("m","UserLook");
				hepler1.put("a", "create");
				RequestParams params=new RequestParams();
				params.put("sid",sid);
				NetWorkUtils.doPost(BaseInfo.BaseUrl_jin,params,hepler1, new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
						super.onSuccess(statusCode, headers, response);
						Log.e("response",response.toString());
						if (response.optInt("code") == 1) {
							add.setVisibility(View.GONE);
							follow.setText("已关注");
						}
						if (response.optString("result")!=null)
						Toast.makeText(UserZoneActivity.this,response.optString("result"),Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
						super.onFailure(statusCode, headers, responseString, throwable);
						Log.e("responseString", responseString.toString());
					}
				});

			}
			break;
			case R.id.back:
				this.finish();
				break;

		}
		follow.setFocusable(true);
		follow.requestFocus();
	}
	
	/**
	 * 清除选中状态
	 */
	private void clearState(){
		sanjiao1.setVisibility(View.GONE);
		sanjiao2.setVisibility(View.GONE);
		sanjiao3.setVisibility(View.GONE);
	}

	/**
	 * 加载被关注用户信息
	 */
	private void LoadUserInfo() {
		NetWorkHepler1 hepler1=new NetWorkHepler1();
		hepler1.put("m","user");
		hepler1.put("a","information");
		hepler1.put("id", sid);
		hepler1.put("app", 1);//标志，是从APP传过去的
		NetWorkUtils.doGet(BaseInfo.BaseUrl_jin, hepler1, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				super.onSuccess(statusCode, headers, response);

				if (response.optInt("code") == 1) {
					if (response.opt("body") != null) {
						userInfo = JsonUtils.getResult(response.optJSONObject("body"), UserInfo.class);
						if (userInfo != null) {
							bindData(userInfo);
						}
					}
					Log.e("userinfo", response.toString());
				}

			}

			@Override
			public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
				super.onFailure(statusCode, headers, responseString, throwable);
			}
		});

	}

	/**
	 * 绑定数据
	 * @param userInfo
	 */
	private void bindData(UserInfo userInfo) {
		if (userInfo.getUsername() != null) {
			user_name.setText(userInfo.getUsername());
		} else {
			user_name.setText("");
		}
		ImageLoader.getInstance().displayImage(BaseInfo.BaseUrl_xu + userInfo.getGravatar(), user_photo, MyApplication.options_image);


	}

}
