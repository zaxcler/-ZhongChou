package com.lianhai.zhongchou.mypage;

import com.lianhai.zhongchou.R;
import com.lianhai.zhongchou.bean.BandAndCardInfo;
import com.lianhai.zhongchou.config.BaseInfo;
import com.lianhai.zhongchou.config.MyApplication;
import com.lianhai.zhongchou.customview.DialogManager;
import com.lianhai.zhongchou.homepage.BinderCardActivity;
import com.lianhai.zhongchou.homepage.LianLianPayActivity;
import com.lianhai.zhongchou.utils.JsonUtils;
import com.lianhai.zhongchou.utils.NetWorkUtils;
import com.lianhai.zhongchou.utils.ZXUtils;
import com.loopj.android.http.JsonHttpResponseHandler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.apache.http.Header;
import org.json.JSONObject;

public class WalletActivity extends Activity {
	
	private TextView balance;//余额
	private LinearLayout withdraw;//余额提现
	private LinearLayout add_card;//添加银行卡

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.wallet_activity);
		ZXUtils.initTitle(this, "我的钱包", false);
		findView();
		init();
	}

	private void findView() {
		balance = (TextView) findViewById(R.id.Balance);
		withdraw = (LinearLayout) findViewById(R.id.withdraw);
		add_card = (LinearLayout) findViewById(R.id.add_card);
		
	}

	private void init() {

		balance.setText(MyApplication.preferences.getString("money","0.00")+"元");

		withdraw.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				NetWorkUtils.doGet(BaseInfo.IsFirst, new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
						super.onSuccess(statusCode, headers, response);
						Log.e("response", response.toString());
						if (response.optInt("code") == 28) {
							DialogManager.showNotice(WalletActivity.this,"你还未绑定过银行卡");
						} else {
							Intent intent=new Intent();
							if (response.optJSONObject("body") != null) {
								BandAndCardInfo bandAndCardInfo = JsonUtils.getResult(response.optJSONObject("body"), BandAndCardInfo.class);
								intent.setClass(WalletActivity.this, WithdrawActivity.class);
								intent.putExtra("bandAndCardInfo", bandAndCardInfo);
								startActivity(intent);
							}


						}
					}

					@Override
					public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
						super.onFailure(statusCode, headers, responseString, throwable);
						Log.e("responseString", responseString);
					}
				});
			}
		});
		add_card.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ZXUtils.goActivity(WalletActivity.this, CardListActivity.class);
			}
		});
	}

}
