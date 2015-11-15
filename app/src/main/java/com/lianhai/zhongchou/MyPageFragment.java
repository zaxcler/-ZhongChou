package com.lianhai.zhongchou;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lianhai.zhongchou.config.BaseInfo;
import com.lianhai.zhongchou.config.MyApplication;
import com.lianhai.zhongchou.mypage.CashFollwActivity;
import com.lianhai.zhongchou.mypage.FansOrFollowListActivity;
import com.lianhai.zhongchou.mypage.LoginActivity;
import com.lianhai.zhongchou.mypage.MyProjectListActivity;
import com.lianhai.zhongchou.mypage.UserInfomationActivity;
import com.lianhai.zhongchou.mypage.WalletActivity;
import com.lianhai.zhongchou.utils.NetWorkUtils;
import com.lianhai.zhongchou.utils.ZXUtils;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.apache.http.Header;
import org.json.JSONObject;

public class MyPageFragment extends Fragment implements OnClickListener{
	private LinearLayout login_or_info;
	private LinearLayout LinearLayout1;
	private LinearLayout LinearLayout2;
	private LinearLayout LinearLayout3;
	private LinearLayout LinearLayout4;
	private LinearLayout LinearLayout5;
	private LinearLayout LinearLayout6;
	private LinearLayout LinearLayout7;

	private TextView yuan1;
	private TextView yuan2;
	private TextView yuan3;
	private TextView yuan4;
	private TextView yuan5;

	private TextView name;
	private TextView phone;
	private ImageView user_photo;
	private TextView money;

	private final int LOGIN=1;

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.fragment_mypage, null);
		findView(view);
		init();
		return view;
	}

	private void findView(View view) {
		login_or_info = (LinearLayout) view.findViewById(R.id.login_or_info);
		LinearLayout1 = (LinearLayout) view.findViewById(R.id.LinearLayout1);
		LinearLayout2 = (LinearLayout) view.findViewById(R.id.LinearLayout2);
		LinearLayout3 = (LinearLayout) view.findViewById(R.id.LinearLayout3);
		LinearLayout4 = (LinearLayout) view.findViewById(R.id.LinearLayout4);
		LinearLayout5 = (LinearLayout) view.findViewById(R.id.LinearLayout5);
		LinearLayout6 = (LinearLayout) view.findViewById(R.id.LinearLayout6);
		LinearLayout7 = (LinearLayout) view.findViewById(R.id.LinearLayout7);
		
		yuan1 = (TextView) view.findViewById(R.id.yuan1);
		yuan2 = (TextView) view.findViewById(R.id.yuan2);
		yuan3 = (TextView) view.findViewById(R.id.yuan3);
		yuan4 = (TextView) view.findViewById(R.id.yuan4);
		yuan5 = (TextView) view.findViewById(R.id.yuan5);

		name = (TextView) view.findViewById(R.id.name);
		phone = (TextView) view.findViewById(R.id.phone);
		user_photo = (ImageView) view.findViewById(R.id.user_photo);
		money = (TextView) view.findViewById(R.id.money);

	}

	private void init() {

		bindData();
		
		login_or_info.setOnClickListener(this);
		
		LinearLayout1.setOnClickListener(this);
		LinearLayout2.setOnClickListener(this);
		LinearLayout3.setOnClickListener(this);
		LinearLayout4.setOnClickListener(this);
		LinearLayout5.setOnClickListener(this);
		LinearLayout6.setOnClickListener(this);
		LinearLayout7.setOnClickListener(this);
		
		yuan1.setOnClickListener(this);
		yuan2.setOnClickListener(this);
		yuan3.setOnClickListener(this);
		yuan4.setOnClickListener(this);
		yuan5.setOnClickListener(this);
		
		
	}

	private void bindData() {
		String photo_url= MyApplication.preferences.getString("Gravatar","");
		ImageLoader.getInstance().displayImage(BaseInfo.BaseUrl_xu + photo_url, user_photo, MyApplication.options_image);
		Log.e("photo_url",photo_url);

		String phone_num=MyApplication.preferences.getString("Telephone","");
		phone.setText(phone_num);

		String user_name=MyApplication.preferences.getString("UserName","请登录");
		name.setText(user_name);

		String money_string=MyApplication.preferences.getString("money","0.00");
		money.setText(money_string+"元");

	}

	@Override
	public void onClick(View v) {
		final Intent intent=new Intent();
		
		switch (v.getId()) {
		case R.id.login_or_info:
			NetWorkUtils.doGet(BaseInfo.IsLogin, new JsonHttpResponseHandler() {
				@Override
				public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
					super.onSuccess(statusCode, headers, response);
					if (response.optInt("code") == 1) {
						ZXUtils.goActivity(getActivity(), UserInfomationActivity.class);
					} else {
						intent.setClass(getActivity(), LoginActivity.class);
						startActivityForResult(intent, LOGIN);
					}
				}

				@Override
				public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
					super.onFailure(statusCode, headers, responseString, throwable);
					Log.e("responseString",responseString);
				}
			});
//			ZXUtils.goActivity(getActivity(), UserInfomationActivity.class);
			break;
		case R.id.LinearLayout1:
			NetWorkUtils.doGet(BaseInfo.IsLogin, new JsonHttpResponseHandler() {
				@Override
				public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
					super.onSuccess(statusCode, headers, response);
					if (response.optInt("code") == 1) {
						intent.setClass(getActivity(), MyProjectListActivity.class);
						intent.putExtra("type", 0);
						getActivity().startActivity(intent);
					} else {
						Toast.makeText(getActivity(),"未登录！",Toast.LENGTH_SHORT).show();
					}
				}

				@Override
				public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
					super.onFailure(statusCode, headers, responseString, throwable);
					Log.e("responseString", responseString);
				}
			});

			break;
		case R.id.LinearLayout2:
			NetWorkUtils.doGet(BaseInfo.IsLogin, new JsonHttpResponseHandler() {
				@Override
				public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
					super.onSuccess(statusCode, headers, response);
					if (response.optInt("code") == 1) {
						intent.setClass(getActivity(), MyProjectListActivity.class);
						intent.putExtra("type", 1);
						getActivity().startActivity(intent);
					} else {
						Toast.makeText(getActivity(), "未登录！", Toast.LENGTH_SHORT).show();
					}
				}

				@Override
				public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
					super.onFailure(statusCode, headers, responseString, throwable);
					Log.e("responseString", responseString);
				}
			});

			break;
		case R.id.LinearLayout3:
			NetWorkUtils.doGet(BaseInfo.IsLogin, new JsonHttpResponseHandler() {
				@Override
				public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
					super.onSuccess(statusCode, headers, response);
					if (response.optInt("code") == 1) {
						intent.setClass(getActivity(), MyProjectListActivity.class);
						intent.putExtra("type", 2);
						getActivity().startActivity(intent);
					} else {
						Toast.makeText(getActivity(), "未登录！", Toast.LENGTH_SHORT).show();
					}
				}

				@Override
				public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
					super.onFailure(statusCode, headers, responseString, throwable);
					Log.e("responseString", responseString);
				}
			});

			break;

		case R.id.LinearLayout4:
			NetWorkUtils.doGet(BaseInfo.IsLogin, new JsonHttpResponseHandler() {
				@Override
				public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
					super.onSuccess(statusCode, headers, response);
					if (response.optInt("code") == 1) {
						intent.setClass(getActivity(), FansOrFollowListActivity.class);
						intent.putExtra("type", 0);
						getActivity().startActivity(intent);
					} else {
						Toast.makeText(getActivity(), "未登录！", Toast.LENGTH_SHORT).show();
					}
				}

				@Override
				public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
					super.onFailure(statusCode, headers, responseString, throwable);
					Log.e("responseString", responseString);
				}
			});


			break;
		case R.id.LinearLayout5:
			NetWorkUtils.doGet(BaseInfo.IsLogin, new JsonHttpResponseHandler() {
				@Override
				public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
					super.onSuccess(statusCode, headers, response);
					if (response.optInt("code") == 1) {
						intent.setClass(getActivity(), FansOrFollowListActivity.class);
						intent.putExtra("type", 1);
						getActivity().startActivity(intent);
					} else {
						Toast.makeText(getActivity(), "未登录！", Toast.LENGTH_SHORT).show();
					}
				}

				@Override
				public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
					super.onFailure(statusCode, headers, responseString, throwable);
					Log.e("responseString", responseString);
				}
			});

			break;
		case R.id.LinearLayout6:
			NetWorkUtils.doGet(BaseInfo.IsLogin, new JsonHttpResponseHandler() {
				@Override
				public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
					super.onSuccess(statusCode, headers, response);
					if (response.optInt("code") == 1) {
						ZXUtils.goActivity(getActivity(), WalletActivity.class);
					} else {
						Toast.makeText(getActivity(), "未登录！", Toast.LENGTH_SHORT).show();
					}
				}

				@Override
				public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
					super.onFailure(statusCode, headers, responseString, throwable);
					Log.e("responseString", responseString);
				}
			});

			break;
		case R.id.LinearLayout7:
			NetWorkUtils.doGet(BaseInfo.IsLogin, new JsonHttpResponseHandler() {
				@Override
				public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
					super.onSuccess(statusCode, headers, response);
					if (response.optInt("code") == 1) {
						ZXUtils.goActivity(getActivity(), CashFollwActivity.class);
					} else {
						Toast.makeText(getActivity(), "未登录！", Toast.LENGTH_SHORT).show();
					}
				}

				@Override
				public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
					super.onFailure(statusCode, headers, responseString, throwable);
					Log.e("responseString", responseString);
				}
			});


			break;

		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Log.e("---","+++");
		if (resultCode== Activity.RESULT_OK){
			switch (requestCode){
				case LOGIN:

					bindData();
					break;
			}
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		/**
		 * 获取用户余额
		 */
		NetWorkUtils.doGet(BaseInfo.MyBalance, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				super.onSuccess(statusCode, headers, response);
				Log.e("response", response.toString());
				if (response.optInt("code") == 1) {
					if (response.optString("body") != null)
						MyApplication.editor.putString("money", response.optString("body")).commit();

				}
				bindData();
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
				super.onFailure(statusCode, headers, responseString, throwable);
				bindData();
			}
		});


	}
}
