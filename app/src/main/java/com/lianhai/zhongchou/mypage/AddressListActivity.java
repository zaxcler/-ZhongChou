package com.lianhai.zhongchou.mypage;

import java.util.ArrayList;
import java.util.List;

import com.lianhai.zhongchou.R;
import com.lianhai.zhongchou.adapter.AddressAdapter;
import com.lianhai.zhongchou.bean.AddressBean;
import com.lianhai.zhongchou.bean.TestBean;
import com.lianhai.zhongchou.config.BaseInfo;
import com.lianhai.zhongchou.config.MyApplication;
import com.lianhai.zhongchou.utils.JsonUtils;
import com.lianhai.zhongchou.utils.NetWorkHepler1;
import com.lianhai.zhongchou.utils.NetWorkUtils;
import com.lianhai.zhongchou.utils.ZXUtils;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.HeaderViewListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.Header;
import org.json.JSONObject;

public class AddressListActivity extends Activity {
	private ListView listview;
	private View footview;
	private AddressAdapter adapter;

	private boolean choose_address=false;//是否是选择收货地址
	private Intent intent;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.address_or_card_list_activity);
		ZXUtils.initTitle(this, "地址", false);
		intent=getIntent();
		choose_address=intent.getBooleanExtra("chooseaddress",false);
		findView();
		init();
	}

	private void findView() {
		listview = (ListView) findViewById(R.id.listview);
		footview=LayoutInflater.from(this).inflate(R.layout.address_list_footview, null);
		
	}

	private void init() {

		loadData();


		
	}

	/**
	 * 加载数据
	 */
	private void loadData() {
		NetWorkHepler1 hepler1=new NetWorkHepler1();
		hepler1.put("m","address");
		hepler1.put("a", "lists");

		RequestParams params=new RequestParams();
		int id= MyApplication.preferences.getInt("UserId",0);
		params.put("uid", id);
		NetWorkUtils.doPost(BaseInfo.BaseUrl_jin, params, hepler1, new JsonHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				super.onSuccess(statusCode, headers, response);
				if (response.optInt("code") == 1) {
					if (response.optJSONArray("body") != null) {
						List<AddressBean> addressList = JsonUtils.getResultList(response.optJSONArray("body"), AddressBean.class);
						bindData(addressList);
					}else {
						/**
						 * {"body":null,"result":"ok","code":1}
						 * 当body==null的时候，为了能给listview添加footview，创建空的集合
						 */
						List<AddressBean> addressList=new ArrayList<AddressBean>();
						bindData(addressList);
					}
					Log.e("response", response.toString());
				} else {
					if (response.optString("result")!=null)
					Toast.makeText(AddressListActivity.this,response.optString("result"),Toast.LENGTH_SHORT).show();
				}
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
				super.onFailure(statusCode, headers, responseString, throwable);
				Log.e("responseString", responseString.toString());
			}
		});
	}

	/**
	 * 绑定数据
	 * @param addressList
	 */
	private void bindData(final List<AddressBean> addressList) {


		if (adapter==null){
			adapter=new AddressAdapter(this, addressList, R.layout.address_item);
		}else {
			adapter.update(addressList);
			adapter.notifyDataSetChanged();
		}

		/**
		 * 没有footview才添加
		 */
		if (listview.getFooterViewsCount()==0){
			listview.addFooterView(footview);
			Log.e("listview.g", "listview.getFooterViewsCount()==0");
		}

		listview.setAdapter(adapter);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				HeaderViewListAdapter addressAdapter = (HeaderViewListAdapter) arg0.getAdapter();


				if (addressAdapter.getCount() - 1 == arg2) {
//					Toast.makeText(AddressListActivity.this, "点击", Toast.LENGTH_SHORT).show();
					ZXUtils.goActivity(AddressListActivity.this, AddressAddActivity.class);
					return;
				}else {

					intent.setClass(AddressListActivity.this, AddressAddActivity.class);
					intent.putExtra("addressBean", addressList.get(arg2));
					Log.e("choose_address","   "+choose_address);
					if (choose_address){
						setResult(RESULT_OK, intent);
						AddressListActivity.this.finish();
						return;
					}
					startActivity(intent);
				}

			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		loadData();
	}
}
