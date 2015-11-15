package com.lianhai.zhongchou.mypage;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.lianhai.zhongchou.R;
import com.lianhai.zhongchou.config.BaseInfo;
import com.lianhai.zhongchou.config.MyApplication;
import com.lianhai.zhongchou.utils.ImageUpload;
import com.lianhai.zhongchou.utils.NetWorkHepler1;
import com.lianhai.zhongchou.utils.NetWorkUtils;
import com.lianhai.zhongchou.utils.ZXUtils;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.Header;
import org.json.JSONObject;

public class UserInfomationActivity extends Activity implements OnClickListener{
	private LinearLayout change_photo;
	private LinearLayout change_password;
	private LinearLayout adrress;
	private LinearLayout verified;//认证
	private LinearLayout about;//关于
	private TextView go_verified;//去认证

	private ImageView user_photo;
	private TextView account_name;
	private TextView phone;
	private TextView exit;
	
	private final int CHOOSE_PHOTO=1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.user_info_activity);
		ZXUtils.initTitle(this, "设置", false);
		findView();
		init();
	}

	private void findView() {
		change_photo = (LinearLayout) findViewById(R.id.change_photo);
		change_password = (LinearLayout) findViewById(R.id.change_password);
		adrress = (LinearLayout) findViewById(R.id.adrress);
		verified = (LinearLayout) findViewById(R.id.verified);
		about = (LinearLayout) findViewById(R.id.about);

		user_photo = (ImageView) findViewById(R.id.user_photo);
		account_name = (TextView) findViewById(R.id.account_name);
		phone = (TextView) findViewById(R.id.phone);
		go_verified = (TextView) findViewById(R.id.go_verified);
		exit = (TextView) findViewById(R.id.exit);
		
		
	}

	private void init() {
		change_photo.setOnClickListener(this);
		change_password.setOnClickListener(this);
		adrress.setOnClickListener(this);
		user_photo.setOnClickListener(this);
		account_name.setOnClickListener(this);
		phone.setOnClickListener(this);
		exit.setOnClickListener(this);
		about.setOnClickListener(this);
		bindData();

		
		
	}

	/**
	 * 绑定数据
	 */
	private void bindData() {
		String photo_path= MyApplication.preferences.getString("Gravatar","");
		ImageLoader.getInstance().displayImage(BaseInfo.BaseUrl_xu+photo_path, user_photo, MyApplication.options_image);
		String nickname=MyApplication.preferences.getString("UserName","");
		account_name.setText(nickname);
		String mobile=MyApplication.preferences.getString("Telephone","");
		phone.setText(mobile);
		int Auth_status=MyApplication.preferences.getInt("Auth_status",0);
		switch (Auth_status){
			case 0:
				go_verified.setText("去认证");
				verified.setOnClickListener(this);
				break;
			case 2:
				go_verified.setText("审核中");
				break;
			case 1:
				go_verified.setText("已认证");
				break;
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.change_photo:
			ZXUtils.chosePhoto(this, CHOOSE_PHOTO);
			break;
		case R.id.change_password:
			ZXUtils.goActivity(this, ChangePasswordActivity.class);
			break;
		case R.id.adrress:
			ZXUtils.goActivity(this, AddressListActivity.class);
			break;
		case R.id.verified:
			ZXUtils.goActivity(this, VerifiedAcitvity.class);
			break;
		case R.id.exit:
			NetWorkHepler1 hepler1=new NetWorkHepler1();
			hepler1.put("m", "user");
			hepler1.put("a", "logout");
			NetWorkUtils.doGet(BaseInfo.BaseUrl_jin, hepler1, new JsonHttpResponseHandler());
			MyApplication.editor.clear().commit();
			this.finish();
			break;
		case R.id.about:
			ZXUtils.goActivity(this,AboutActivvity.class);
			break;

		
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode==RESULT_OK) {
			switch (requestCode) {
			case CHOOSE_PHOTO:
				Uri uri=data.getData();
				try {
					InputStream inputStream=getContentResolver().openInputStream(uri);
					BitmapFactory.Options options=new BitmapFactory.Options();
					options.inJustDecodeBounds=true;
					options.inSampleSize=4;
					options.inJustDecodeBounds=false;
					Bitmap bitmap=BitmapFactory.decodeStream(inputStream, null, options);
					user_photo.setImageBitmap(bitmap);
					//----更新服务器数据-----
					List<Uri> uri_list=new ArrayList<Uri>();
					uri_list.add(uri);

					ImageUpload.showUploadDialog(this);
					ImageUpload.setUploadListener(new ImageUpload.UploadListener() {
						@Override
						public void onFailed(Object error) {
							ImageUpload.photoId.clear();
						}

						@Override
						public void onSuccess(Object result) {
							JSONObject jsonObject= (JSONObject) result;
							/**
							 * 保存图片名字
							 */
							if (jsonObject!=null){
								if (jsonObject.optJSONObject("body")!=null&&jsonObject.optJSONObject("body").optString("file")!=null){
									Log.e("Gravatar", jsonObject.optJSONObject("body").optString("file"));
									MyApplication.editor.putString("Gravatar",jsonObject.optJSONObject("body").optString("file")).commit();
								}
							}

							NetWorkHepler1 hepler1=new NetWorkHepler1();
							hepler1.put("m","user");
							hepler1.put("a","save");
							RequestParams params=new RequestParams();
							params.put("data[gravatar]",ImageUpload.photoId.get(0));
							NetWorkUtils.doPost(BaseInfo.BaseUrl_jin,params,hepler1,new JsonHttpResponseHandler(){
								@Override
								public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
									super.onSuccess(statusCode, headers, response);

									Log.e("response", response.toString());

									if (response.optInt("code")==1){
										Toast.makeText(UserInfomationActivity.this,"头像修改成功",Toast.LENGTH_SHORT).show();
										bindData();
									}else {
										Toast.makeText(UserInfomationActivity.this,"头像修改失败",Toast.LENGTH_SHORT).show();
										MyApplication.editor.clear().commit();
									}
									ImageUpload.photoId.clear();
								}

								@Override
								public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
									super.onFailure(statusCode, headers, responseString, throwable);
									Log.e("response", responseString.toString());
									ImageUpload.photoId.clear();
								}
							});
						}
					});
					ImageUpload.upLoadImage(uri_list);
					
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;

			default:
				break;
			}
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		bindData();
	}
}
