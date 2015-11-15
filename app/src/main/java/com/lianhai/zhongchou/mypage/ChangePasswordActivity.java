package com.lianhai.zhongchou.mypage;

import android.app.Activity;
import android.os.Bundle;
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

public class ChangePasswordActivity extends Activity {
	
	private EditText old_password;
	private EditText new_password;
	private EditText confirm_password;
	private Button confirm;
	private LinearLayout background;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.change_password_activity);
		ZXUtils.initTitle(this, "修改密码", false);
		findView();
		init();
	}
	
	

	private void findView() {
		old_password = (EditText) findViewById(R.id.old_password);
		new_password = (EditText) findViewById(R.id.new_password);
		confirm_password = (EditText) findViewById(R.id.confirm_password);
		confirm = (Button) findViewById(R.id.confirm);
		background = (LinearLayout) findViewById(R.id.background);
	}

	private void init() {
		
     background.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.e("------", "--------");
				v.setFocusable(true);
				v.setFocusableInTouchMode(true);
				v.requestFocus();
//				account_et.clearFocus();
//				password_et.clearFocus();
			}
		});
		// 点击其他地方输入法消失
//     old_password.setOnFocusChangeListener(new OnFocusChangeListener() {
//
//			@Override
//			public void onFocusChange(View v, boolean hasFocus) {
//				if (!hasFocus) {
//					ZXUtils.closeInputMethod(ChangePasswordActivity.this);
//					Log.e("account_et____失去焦点", "失去焦点");
//				}
//			}
//		});
//     new_password.setOnFocusChangeListener(new OnFocusChangeListener() {
//
//			@Override
//			public void onFocusChange(View v, boolean hasFocus) {
//				if (!hasFocus) {
//					ZXUtils.closeInputMethod(ChangePasswordActivity.this);
//					Log.e("password_et——————失去焦点", "失去焦点");
//				}
//			}
//		});
//     confirm_password.setOnFocusChangeListener(new OnFocusChangeListener() {
//
//    	 @Override
//    	 public void onFocusChange(View v, boolean hasFocus) {
//    		 if (!hasFocus) {
//    			 ZXUtils.closeInputMethod(ChangePasswordActivity.this);
//    			 Log.e("password_et——————失去焦点", "失去焦点");
//    		 }
//    	 }
//     });
		confirm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String oldpassword = old_password.getText().toString().trim();
				String newpassword = new_password.getText().toString().trim();
				String confirmpassword = confirm_password.getText().toString().trim();
				if (oldpassword == null || "".equals(oldpassword)) {
					Toast.makeText(ChangePasswordActivity.this, "原密码不能为空！", Toast.LENGTH_SHORT).show();
					return;
				}
				if (new_password.length()<6 || new_password.length()>20) {
					Toast.makeText(ChangePasswordActivity.this, "账号长度6-20位", Toast.LENGTH_SHORT).show();
					return;
				}
				if (newpassword == null || "".equals(newpassword)) {
					Toast.makeText(ChangePasswordActivity.this, "新密码不能为空！", Toast.LENGTH_SHORT).show();
					return;
				}
				if (confirmpassword == null || "".equals(confirmpassword)) {
					Toast.makeText(ChangePasswordActivity.this, "请输入确认密码！", Toast.LENGTH_SHORT).show();
					return;
				}

				if (!newpassword.equals(confirmpassword)) {
					Toast.makeText(ChangePasswordActivity.this, "两次输入密码不一致！", Toast.LENGTH_SHORT).show();
					return;
				}

				// ------下面进行网络请求------
				NetWorkHepler1 hepler1=new NetWorkHepler1();
				hepler1.put("m","user");
				hepler1.put("a", "changepwd");

				RequestParams params=new RequestParams();
				params.put("password",ZXUtils.MD5(oldpassword));
				params.put("new_password",ZXUtils.MD5(newpassword));
				NetWorkUtils.doPost(BaseInfo.BaseUrl_jin,params,hepler1,new JsonHttpResponseHandler(){
					@Override
					public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
						super.onSuccess(statusCode, headers, response);
						if (response.optInt("code")==1){
							Toast.makeText(ChangePasswordActivity.this,"密码修改成功",Toast.LENGTH_SHORT).show();
							ChangePasswordActivity.this.finish();
						}else {
							if (response.optString("result")!=null)
							Toast.makeText(ChangePasswordActivity.this,response.optString("result"),Toast.LENGTH_SHORT).show();
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
