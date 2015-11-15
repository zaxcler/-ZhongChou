package com.lianhai.zhongchou.mypage;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.lianhai.zhongchou.R;
import com.lianhai.zhongchou.adapter.ImageAdapter;
import com.lianhai.zhongchou.config.BaseInfo;
import com.lianhai.zhongchou.config.MyApplication;
import com.lianhai.zhongchou.utils.ImageUpload;
import com.lianhai.zhongchou.utils.NetWorkHepler1;
import com.lianhai.zhongchou.utils.NetWorkUtils;
import com.lianhai.zhongchou.utils.RegularUtils;
import com.lianhai.zhongchou.utils.ZXUtils;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.Header;
import org.json.JSONObject;


public class VerifiedAcitvity extends Activity implements OnClickListener{
	private TextView name;
	private TextView card_id;
	private TextView uploadphoto;
	private Button submit;
	
	private List<Bitmap> photos;
	private List<Uri> photos_uri;
	private ImageAdapter mImageAdapter;
	private GridView photo_gridview;
	private RequestParams mParams;//网络请求参数

	private final int CHOOSE_PHOTO=1;
	private final int CORP_PHOTO=2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.verified_activity);
		ZXUtils.initTitle(this, "实名认证", false);
		findView();
		init();
		
	}

	private void findView() {
		uploadphoto = (TextView) findViewById(R.id.uploadphoto);
		name = (TextView) findViewById(R.id.name);
		card_id = (TextView) findViewById(R.id.card_id);
		photo_gridview = (GridView) findViewById(R.id.photo_gridview);
		submit = (Button) findViewById(R.id.submit);
	}

	private void init() {
		photos=new ArrayList<Bitmap>();
		photos_uri= new ArrayList<Uri>();
		mImageAdapter=new ImageAdapter(this, photos, R.layout.image_view);
		uploadphoto.setOnClickListener(this);
		submit.setOnClickListener(this);
		photo_gridview.setAdapter(mImageAdapter);
		photo_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, final int gird_i, long l) {
				AlertDialog.Builder builder=new AlertDialog.Builder(VerifiedAcitvity.this,AlertDialog.THEME_HOLO_LIGHT);
				builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						dialogInterface.dismiss();
					}
				});
				builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						photos.remove(gird_i);
						photos_uri.remove(gird_i);
//						mImageAdapter.removeBitmap(i);
						mImageAdapter.notifyDataSetChanged();
					}
				});
						AlertDialog dialog=builder.create();
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				dialog.setMessage("是否删除");
				dialog.show();

			}
		});
		
		
		
		
	}
//
//	private Handler handler=new Handler(){
//		@Override
//		public void handleMessage(Message msg) {
//			super.handleMessage(msg);
//			NetWorkHepler1 mhelper=new NetWorkHepler1();
//			mhelper.put("m","user");
//			mhelper.put("a","auth");
//			mhelper.put("type", "identify");
//			NetWorkUtils.doPost(BaseInfo.BaseUrl_jin, mParams, mhelper, new JsonHttpResponseHandler() {
//				@Override
//				public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//					super.onSuccess(statusCode, headers, response);
//					if (response.optInt("code") == 1) {
//						Toast.makeText(VerifiedAcitvity.this, "申请成功等待审核", Toast.LENGTH_SHORT).show();
//						MyApplication.editor.putInt("Auth_status", 2).commit();
//						ImageUpload.photoId.clear();
//						VerifiedAcitvity.this.finish();
//					} else {
//						ImageUpload.photoId.clear();
//						if (response.optString("result") != null)
//							Toast.makeText(VerifiedAcitvity.this, response.optString("result"), Toast.LENGTH_SHORT).show();
//					}
//
//
//				}
//
//				@Override
//				public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//					super.onFailure(statusCode, headers, responseString, throwable);
//					Log.e("responseString", responseString);
//					ImageUpload.photoId.clear();
//				}
//			});
//
//		}
//
//	};

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.uploadphoto:
			if (photos.size()==2) {
				Toast.makeText(this, "最多上传两张图片哟！", Toast.LENGTH_SHORT).show();
				return;
			}
			ZXUtils.chosePhoto(this, CHOOSE_PHOTO);
			break;

			case R.id.submit:



				mParams=new RequestParams();
				final String realname=name.getText().toString().trim();

				if (realname==null || "".equals(realname)){
					Toast.makeText(this,"名字不能为空",Toast.LENGTH_SHORT).show();
					return;
				}
				final String idCard=card_id.getText().toString().trim();
				if (idCard==null || "".equals(idCard)){
					Toast.makeText(this,"身份证号码不能为空",Toast.LENGTH_SHORT).show();
					return;
				}
				if (!RegularUtils.isIDCard(idCard)){
					Toast.makeText(this,"身份证号码有误",Toast.LENGTH_SHORT).show();
					return;
				}
//				if (photos.size()<2){
//					Toast.makeText(this,"必须上传证件照正反面",Toast.LENGTH_SHORT).show();
//					return;
//				}
				NetWorkHepler1 mhelper=new NetWorkHepler1();
				mParams.put("name", realname);
				mParams.put("idno", idCard);
				mhelper.put("m","auth");
				mhelper.put("a","auth");
//				mhelper.put("type", "identify");
				NetWorkUtils.doPost(BaseInfo.BaseUrl_jin, mParams, mhelper, new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
						super.onSuccess(statusCode, headers, response);
						if (response.optInt("code") == 1) {
							Toast.makeText(VerifiedAcitvity.this, "申请成功等待审核", Toast.LENGTH_SHORT).show();
							MyApplication.editor.putInt("Auth_status", 2).commit();
//							ImageUpload.photoId.clear();
							VerifiedAcitvity.this.finish();
						} else {
//							ImageUpload.photoId.clear();
							if (response.optString("result") != null)
								Toast.makeText(VerifiedAcitvity.this, response.optString("result"), Toast.LENGTH_SHORT).show();
						}


					}

					@Override
					public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
						super.onFailure(statusCode, headers, responseString, throwable);
						Log.e("responseString", responseString);
						ImageUpload.photoId.clear();
					}
				});
//				ImageUpload.showUploadDialog(this);
//				ImageUpload.setUploadListener(new ImageUpload.UploadListener() {
//					@Override
//					public void onFailed(Object error) {
//						ImageUpload.photoId.clear();
//					}
//
//					@Override
//					public void onSuccess(Object result) {
//						mParams.put("realname", realname);
//						mParams.put("identify", idCard);
//						mParams.put("identify_zheng", ImageUpload.photoId.get(0));
//						mParams.put("identify_fan", ImageUpload.photoId.get(1));
//						handler.sendEmptyMessage(1);
//					}
//				});
//				ImageUpload.upLoadImage(photos_uri);

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
				Log.e("uri",uri.toString());
				try {
					//根据uri打开图片的流
					InputStream inputStream=getContentResolver().openInputStream(uri);
					BitmapFactory.Options options=new BitmapFactory.Options();
					options.inJustDecodeBounds = true;
//					BitmapFactory.decodeStream(inputStream, null, options);
					options.inSampleSize=4;
					//按照宽高之中小的进行压缩
//					if (options.outWidth>MyApplication.getScreen_width()/3 || options.outHeight>MyApplication.getScreen_width()/3) {
//						options.inSampleSize=Math.min(options.outWidth/(MyApplication.getScreen_width()/3) , options.outHeight/(MyApplication.getScreen_width()/3));
//					}
					options.inJustDecodeBounds=false;
					Bitmap bitmap=BitmapFactory.decodeStream(inputStream, null, options);
					photos.add(bitmap);
					photos_uri.add(uri);
//					mImageAdapter.addBitmap(bitmap);
					mImageAdapter.notifyDataSetChanged();
					
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;

			
			}
		}
	}

}
