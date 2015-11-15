package com.lianhai.zhongchou.mypage;

import java.util.ArrayList;

import com.lianhai.zhongchou.R;
import com.lianhai.zhongchou.adapter.AddressAdapter;
import com.lianhai.zhongchou.adapter.CardAdapter;
import com.lianhai.zhongchou.bean.AddressBean;
import com.lianhai.zhongchou.bean.TestBean;
import com.lianhai.zhongchou.utils.ZXUtils;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.HeaderViewListAdapter;
import android.widget.ListView;

public class CardListActivity extends Activity {
	private ListView listview;
	private View footview;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.address_or_card_list_activity);
		ZXUtils.initTitle(this, "银行卡", false);
		findView();
		init();
	}

	private void findView() {
		listview = (ListView) findViewById(R.id.listview);
		footview=LayoutInflater.from(this).inflate(R.layout.card_list_footview, null);
		
	}

	private void init() {
		ArrayList<TestBean> list=new ArrayList<TestBean>();
		list.add(new TestBean());
		CardAdapter adapter=new CardAdapter(this, list, R.layout.card_item);
		listview.addFooterView(footview);
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				HeaderViewListAdapter addressAdapter=(HeaderViewListAdapter) arg0.getAdapter();
				
				
				if (addressAdapter.getCount()-1==arg2) {
//					Toast.makeText(AddressListActivity.this, "点击", Toast.LENGTH_SHORT).show();
					ZXUtils.goActivity(CardListActivity.this, CardAddActivity.class);
					return;
				}
				
			}
		});
		
	}
}
