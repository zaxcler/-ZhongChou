package com.lianhai.zhongchou.mypage;

import com.lianhai.zhongchou.R;
import com.lianhai.zhongchou.bean.UserInfo;
import com.lianhai.zhongchou.config.BaseInfo;
import com.lianhai.zhongchou.config.MyApplication;
import com.lianhai.zhongchou.utils.JsonUtils;
import com.lianhai.zhongchou.utils.NetWorkHepler1;
import com.lianhai.zhongchou.utils.NetWorkUtils;
import com.lianhai.zhongchou.utils.NetworkHepler;

import com.lianhai.zhongchou.utils.RegularUtils;
import com.lianhai.zhongchou.utils.ZXUtils;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginActivity extends Activity {
	private EditText account_et;
	private EditText password_et;
	private Button confirm;
	private LinearLayout background;
	private TextView forget_password;
	private TextView register;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login_activity);
		ZXUtils.initTitle(this, "登录", false);
		findView();
		init();
	}

	private void findView() {
		account_et = (EditText) findViewById(R.id.account);
		password_et = (EditText) findViewById(R.id.password);
		confirm = (Button) findViewById(R.id.confirm);
		background = (LinearLayout) findViewById(R.id.background);
		forget_password = (TextView) findViewById(R.id.forget_password);
		register = (TextView) findViewById(R.id.register);
	}

	private void init() {

		forget_password.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ZXUtils.goActivity(LoginActivity.this, ForgetPasswordActivity.class);
			}
		});
		register.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ZXUtils.goActivity(LoginActivity.this, RegisterActivity.class);
			}
		});

		background.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.e("------", "--------");
				v.setFocusable(true);
				v.setFocusableInTouchMode(true);
				v.requestFocus();
				// account_et.clearFocus();
				// password_et.clearFocus();
			}
		});
		// 点击其他地方输入法消失
		account_et.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (!hasFocus) {
					ZXUtils.closeInputMethod(LoginActivity.this);
					Log.e("account_et____失去焦点", "失去焦点");
				}
			}
		});
		password_et.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (!hasFocus) {
					ZXUtils.closeInputMethod(LoginActivity.this);
					Log.e("password_et——————失去焦点", "失去焦点");
				}
			}
		});
		confirm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String account = account_et.getText().toString().trim();
				String password = password_et.getText().toString().trim();
				if (account == null || "".equals(account)) {
					Toast.makeText(LoginActivity.this, "账号不能为空！", Toast.LENGTH_SHORT).show();
					return;
				}
				if (password == null || "".equals(password)) {
					Toast.makeText(LoginActivity.this, "密码不能为空！", Toast.LENGTH_SHORT).show();
					return;
				}
//				if (!RegularUtils.isPhoneNumber(account)) {
//					Toast.makeText(LoginActivity.this, "请输入正确手机号！", Toast.LENGTH_SHORT).show();
//					return;
//				}

				// ------下面进行网络请求------

				RequestParams params=new RequestParams();
				NetWorkHepler1 hepler1=new NetWorkHepler1();
				hepler1.put("m","user");
				hepler1.put("a","login");
				params.put("username",account);
				params.put("password",ZXUtils.MD5(password));


				NetWorkUtils.doPost(BaseInfo.BaseUrl_jin,params,hepler1,new JsonHttpResponseHandler(){
					@Override
					public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
					super.onSuccess(statusCode, headers, response);
						Log.e("statusCode", statusCode + "");
						Log.e("response", response.toString());

						if (response.optString("result")!=null){
							Toast.makeText(LoginActivity.this,response.optString("result"),Toast.LENGTH_SHORT).show();
						}

						if (response.optInt("code")==1){
							if (response.optJSONObject("body")!=null){
								UserInfo info= JsonUtils.getResult(response.optJSONObject("body"),UserInfo.class);
								MyApplication.saveUserInfo(info);
								LoadUserInfo(info);
							}
							LoginActivity.this.finish();
						}

				}

					@Override
					public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
					super.onFailure(statusCode, headers, responseString, throwable);
						Log.e("statusCode", statusCode+"");
					for (Header header: headers ){
						Log.e("onFailure", header.toString());
					}
				}
				});


			}
		});

	}

	/**
	 * 加载用户信息
	 */
	private void LoadUserInfo(UserInfo info) {
		NetWorkHepler1 hepler1=new NetWorkHepler1();
		hepler1.put("m","user");
		hepler1.put("a","information");
		hepler1.put("id",info.getId());
		hepler1.put("app",1);//标志，是从APP传过去的


		NetWorkUtils.doGet(BaseInfo.BaseUrl_jin,hepler1,new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				super.onSuccess(statusCode, headers, response);
				if (response.optString("result")!=null){
					Toast.makeText(LoginActivity.this,response.optString("result"),Toast.LENGTH_SHORT).show();
				}
				if (response.optInt("code")==1){
					if (response.opt("body")!=null){
						UserInfo info=JsonUtils.getResult(response.optJSONObject("body"),UserInfo.class);
						MyApplication.saveUserInfo(info);
					}

					Log.e("response",response.toString());
				}

			}

			@Override
			public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
				super.onFailure(statusCode, headers, responseString, throwable);
			}
		});

	}


}
