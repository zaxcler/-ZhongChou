package com.lianhai.zhongchou.mypage;

import com.lianhai.zhongchou.R;
import com.lianhai.zhongchou.adapter.SpinnerAdapter;
import com.lianhai.zhongchou.bean.AddressBean;
import com.lianhai.zhongchou.bean.CityOrPrivance;
import com.lianhai.zhongchou.config.BaseInfo;
import com.lianhai.zhongchou.customview.MySpinner;
import com.lianhai.zhongchou.utils.JsonUtils;
import com.lianhai.zhongchou.utils.NetWorkHepler1;
import com.lianhai.zhongchou.utils.NetWorkUtils;
import com.lianhai.zhongchou.utils.RegularUtils;
import com.lianhai.zhongchou.utils.ZXUtils;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AddressAddActivity extends Activity implements View.OnClickListener {
//	private Spinner province;
	private MySpinner province;
	private MySpinner city;
	private EditText name;
	private EditText phone;
	private EditText address;
	private EditText code;
	private Button save;
	private Button delete;

	private int provinceId;
	private int cityId;
	private int id;

	private List<CityOrPrivance> provinces;
	private List<CityOrPrivance> citys;





	private Intent intent;
	private AddressBean addressBean;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.address_add_activity);
		ZXUtils.initTitle(this, "添加地址", false);
		intent=getIntent();
		addressBean=intent.getParcelableExtra("addressBean");
		if (addressBean!=null){
			provinceId=addressBean.getProvince();
			cityId=addressBean.getCity();
		}

		findView();
		init();
		
	}

	private void findView() {
		province = (MySpinner) findViewById(R.id.province);
		city = (MySpinner) findViewById(R.id.city);
		name = (EditText) findViewById(R.id.name);
		phone = (EditText) findViewById(R.id.phone);
		address = (EditText) findViewById(R.id.address);
		code = (EditText) findViewById(R.id.code);
		save = (Button) findViewById(R.id.save);
		delete = (Button) findViewById(R.id.delete);

	}

	private void init() {
		if (addressBean!=null){
			bindData(addressBean);
		}

		/**
		 * 获取城市信息
		 */
		NetWorkHepler1 hepler1=new NetWorkHepler1();
		hepler1.put("m","index");
		hepler1.put("a","address");
		hepler1.put("type","province");
		NetWorkUtils.doGet(BaseInfo.BaseUrl_jin, hepler1, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
				super.onSuccess(statusCode, headers, response);
				Log.e("JSONArray", response.toString());
				provinces = JsonUtils.getResultList(response, CityOrPrivance.class);
				if (provinces != null) {
					/**
					 * 设置点击后的回调函数
					 */
					province.setList(provinces, new MySpinner.OnClickLisener() {
						@Override
						public void onClick(CityOrPrivance cityOrPrivance) {
							provinceId = cityOrPrivance.getId();

							NetWorkHepler1 hepler1 = new NetWorkHepler1();
							hepler1.put("m", "index");
							hepler1.put("a", "address");
							hepler1.put("type", "city");
							hepler1.put("pid", provinceId);
							NetWorkUtils.doGet(BaseInfo.BaseUrl_jin, hepler1, new JsonHttpResponseHandler() {
								@Override
								public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
									super.onSuccess(statusCode, headers, response);
									citys = JsonUtils.getResultList(response, CityOrPrivance.class);
									if (citys != null)

									/**
									 * 设置点击后的回调函数
									 */
										city.setList(citys, new MySpinner.OnClickLisener() {
											@Override
											public void onClick(CityOrPrivance cityOrPrivance) {
												cityId = cityOrPrivance.getId();
											}
										});
								}

								@Override
								public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
									super.onFailure(statusCode, headers, responseString, throwable);
									Log.e("responseString", responseString);
								}

							});
						}
					});
				}

			}

			@Override
			public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
				super.onFailure(statusCode, headers, responseString, throwable);
				Log.e("responseString", responseString);
			}
		});

		province.setOnClickListener(this);
		city.setOnClickListener(this);
		save.setOnClickListener(this);
		delete.setOnClickListener(this);


//		SpinnerAdapter adapter= new SpinnerAdapter(this,R.layout.spinner_checked_text,list,province);
//		province.setAdapter(adapter);

		
		
	}

	/**
	 * 绑定数据
	 * @param addressBean
	 */
	private void bindData(AddressBean addressBean) {

		delete.setVisibility(View.VISIBLE);
		if (addressBean.getProvince_name()!=null){
			province.setText(addressBean.getProvince_name());
		}
		if (addressBean.getCity_name()!=null){
			city.setText(addressBean.getCity_name());
		}
		if (addressBean.getName()!=null){
			name.setText(addressBean.getName());
		}
		if (addressBean.getTelephone()!=null){
			phone.setText(addressBean.getTelephone());
		}
		if (addressBean.getAddress()!=null){
			address.setText(addressBean.getAddress());
		}
		if (addressBean.getZipcode()!=null){
			code.setText(addressBean.getZipcode());
		}

	}

	@Override
	public void onClick(View view) {

		switch (view.getId()){
			case R.id.save:

				int province_id=provinceId;
				int city_id=cityId;
				String name_string=name.getText().toString().trim();
				String phone_string=phone.getText().toString().trim();
				String address_string=address.getText().toString().trim();
				String code_string=code.getText().toString().trim();

				if (name_string==null||"".equals(name_string)){
					Toast.makeText(this,"姓名不能为空",Toast.LENGTH_SHORT).show();
					return;
				}
				if (phone_string==null||"".equals(phone_string)){
					Toast.makeText(this,"电话不能为空",Toast.LENGTH_SHORT).show();
					return;
				}
				if (address_string==null||"".equals(address_string)){
					Toast.makeText(this,"地址不能为空",Toast.LENGTH_SHORT).show();
					return;
				}
				if (RegularUtils.isPhoneNumber(phone_string)){
					Toast.makeText(this,"请输入正确的电话号码",Toast.LENGTH_SHORT).show();
					return;
				}

//				if (code_string==null||"".equals(code_string)){
//					Toast.makeText(this,"邮政编码不能为空",Toast.LENGTH_SHORT).show();
//					return;
//				}
				NetWorkHepler1 hepler1=new NetWorkHepler1();
				hepler1.put("m","address");

				RequestParams params=new RequestParams();
				if (addressBean==null){
					hepler1.put("a","create");
				}else {
					hepler1.put("a","save");
					params.put("id",addressBean.getId());
				}
				params.put("province",province_id);
				params.put("city",city_id);
				params.put("name",name_string);
				params.put("address",address_string);
				params.put("telephone",phone_string);
				params.put("zipcode",code_string);

				NetWorkUtils.doPost(BaseInfo.BaseUrl_jin, params, hepler1, new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
						super.onSuccess(statusCode, headers, response);
						Log.e("response", response.toString());
						if (response.optInt("code") == 1) {
							id=response.optInt("body");
							Toast.makeText(AddressAddActivity.this, "地址添加成功", Toast.LENGTH_SHORT).show();
							AddressAddActivity.this.finish();
						} else {
							Toast.makeText(AddressAddActivity.this, response.optString("result"), Toast.LENGTH_SHORT).show();
						}
					}

					@Override
					public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
						super.onFailure(statusCode, headers, responseString, throwable);
						Log.e("responseString",responseString);
					}
				});
				break;
			case R.id.delete:
				NetWorkHepler1 h=new NetWorkHepler1();
				h.put("m","address");
				h.put("a","remove");

				RequestParams params1=new RequestParams();
				params1.put("id",addressBean.getId());

				NetWorkUtils.doPost(BaseInfo.BaseUrl_jin, params1, h, new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
						super.onSuccess(statusCode, headers, response);
						if (response.optInt("code") == 1) {
							Toast.makeText(AddressAddActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
							AddressAddActivity.this.finish();
						} else {
							if (response.optString("result")!=null)
							Toast.makeText(AddressAddActivity.this, response.optString("result"), Toast.LENGTH_SHORT).show();
						}
					}

					@Override
					public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
						super.onFailure(statusCode, headers, responseString, throwable);
						Log.e("responseString",responseString.toString());
					}
				});

				break;
		}

	}
}
