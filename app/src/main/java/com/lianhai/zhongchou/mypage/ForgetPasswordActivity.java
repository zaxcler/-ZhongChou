package com.lianhai.zhongchou.mypage;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.lianhai.zhongchou.R;
import com.lianhai.zhongchou.config.BaseInfo;
import com.lianhai.zhongchou.utils.NetWorkHepler1;
import com.lianhai.zhongchou.utils.NetWorkUtils;
import com.lianhai.zhongchou.utils.ZXUtils;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

public class ForgetPasswordActivity extends Activity {
	private EditText account_et;
	private EditText password_et;
	private EditText identify_code_et;
	private Button confirm;
	private Button identify_code_button;
	private LinearLayout background;
	
	private TimerTask mTimerTask;
	private Timer mTimer;
	private int time=60;
	private final int TIMER=1;//计时标记
	private boolean isTimeing=false;//是否在计时
	private Handler hander=new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case TIMER:
				if (time>0) {
					identify_code_button.setBackgroundDrawable(getResources().getDrawable(R.drawable.corners_half_right_grey));
					identify_code_button.setText(time+"秒后重新获取");
				}else {
					identify_code_button.setText("获取验证码");
					identify_code_button.setBackgroundDrawable(getResources().getDrawable(R.drawable.corners_half_right_midyellow));
					time=60;
					isTimeing=false;
					mTimer.cancel();
				}
				break;

			default:
				break;
			}
		};
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.forget_password_activity);
		ZXUtils.initTitle(this, "忘记密码", false);
		findView();
		init();
	}

	private void findView() {
		account_et = (EditText) findViewById(R.id.account);
		password_et = (EditText) findViewById(R.id.password);
		identify_code_et = (EditText) findViewById(R.id.identify_code_et);
		confirm = (Button) findViewById(R.id.confirm);
		identify_code_button = (Button) findViewById(R.id.identify_code_button);
		background = (LinearLayout) findViewById(R.id.background);
	}

	private void init() {
		
		background.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				account_et.clearFocus();
				password_et.clearFocus();
			}
		});
		// 点击其他地方输入法消失
//		account_et.setOnFocusChangeListener(new OnFocusChangeListener() {
//
//			@Override
//			public void onFocusChange(View v, boolean hasFocus) {
//				if (!hasFocus) {
//					ZXUtils.closeInputMethod(ForgetPasswordActivity.this);
//				}
//			}
//		});
//		password_et.setOnFocusChangeListener(new OnFocusChangeListener() {
//
//			@Override
//			public void onFocusChange(View v, boolean hasFocus) {
//				if (!hasFocus) {
//					ZXUtils.closeInputMethod(ForgetPasswordActivity.this);
//				}
//			}
//		});
//		identify_code_et.setOnFocusChangeListener(new OnFocusChangeListener() {
//
//			@Override
//			public void onFocusChange(View v, boolean hasFocus) {
//				if (!hasFocus) {
//					ZXUtils.closeInputMethod(ForgetPasswordActivity.this);
//				}
//			}
//		});
		//验证码倒计时
		identify_code_button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				/*
				 * 如果在计时状态则不可点击
				 */
				if (isTimeing) {
					return;
				}
//				if (!RegularUtils.isPhoneNumber(account_et.getText().toString().trim())) {
//					Toast.makeText(ForgetPasswordActivity.this, "请输入正确手机号！", Toast.LENGTH_SHORT).show();
//					return;
//				}
				NetWorkHepler1 hepler1=new NetWorkHepler1();
				hepler1.put("m","index");
				hepler1.put("a","sms");
				String phone = account_et.getText().toString().trim();
				RequestParams params=new RequestParams();
				params.put("telephone",phone);

				NetWorkUtils.doPost(BaseInfo.BaseUrl_jin, params, hepler1, new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
						super.onSuccess(statusCode, headers, response);
						if (response.optInt("code") == 1) {
							isTimeing = true;
							mTimerTask = new TimerTask() {

								@Override
								public void run() {
									time--;
									Message message = Message.obtain();
									message.arg1 = time;
									message.what = TIMER;
									hander.sendMessage(message);
								}
							};
							mTimer = new Timer();
							mTimer.schedule(mTimerTask, 0, 1000);
						}else {
							isTimeing = false;
							if (response.optString("result")!=null&&!"".equals(response.optString("result"))){
								Toast.makeText(ForgetPasswordActivity.this,response.optString("result"),Toast.LENGTH_SHORT).show();
							}
						}
					}
					@Override
					public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
						super.onFailure(statusCode, headers, responseString, throwable);
						isTimeing=false;
						Log.e("responseString", responseString);
					}
				});

				
			}
		});
		confirm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String account = account_et.getText().toString().trim();
				String password = password_et.getText().toString().trim();
				String identify_code = identify_code_et.getText().toString().trim();
				if (account == null || "".equals(account)) {
					Toast.makeText(ForgetPasswordActivity.this, "账号不能为空！", Toast.LENGTH_SHORT).show();
					return;
				}
				if (identify_code == null || "".equals(identify_code)) {
					Toast.makeText(ForgetPasswordActivity.this, "请输入验证码！", Toast.LENGTH_SHORT).show();
					return;
				}
				if (password.length()<6 || password.length()>20) {
					Toast.makeText(ForgetPasswordActivity.this, "账号长度6-20位", Toast.LENGTH_SHORT).show();
					return;
				}
				if (password == null || "".equals(password)) {
					Toast.makeText(ForgetPasswordActivity.this, "密码不能为空！", Toast.LENGTH_SHORT).show();
					return;
				}
//				if (!RegularUtils.isPhoneNumber(account)) {
//					Toast.makeText(ForgetPasswordActivity.this, "请输入正确手机号！", Toast.LENGTH_SHORT).show();
//					return;
//				}

				// ------下面进行网络请求------

				NetWorkHepler1 hepler1=new NetWorkHepler1();
				hepler1.put("m","user");
				hepler1.put("a","forgetpwd");

				RequestParams params=new RequestParams();
				params.put("telephone",account);
				params.put("code",identify_code);
				params.put("password",ZXUtils.MD5(password));

				NetWorkUtils.doPost(BaseInfo.BaseUrl_jin,params,hepler1,new JsonHttpResponseHandler(){
					@Override
					public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
						super.onSuccess(statusCode, headers, response);
						if (response.optInt("code")==1){
							Toast.makeText(ForgetPasswordActivity.this,"密码修改成功",Toast.LENGTH_SHORT).show();
							ForgetPasswordActivity.this.finish();
						}else {
							if (response.optString("result")!=null)
							Toast.makeText(ForgetPasswordActivity.this,"密码修改失败",Toast.LENGTH_SHORT).show();
						}
					}

					@Override
					public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
						super.onFailure(statusCode, headers, responseString, throwable);
						Log.e("responseString",responseString);
					}
				});


			}
		});

	}

}
