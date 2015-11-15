package com.lianhai.zhongchou.mypage;

import com.lianhai.zhongchou.R;
import com.lianhai.zhongchou.customview.MySpinner;
import com.lianhai.zhongchou.utils.ZXUtils;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

public class CardAddActivity extends Activity implements View.OnClickListener{
	private MySpinner account_type;
	private MySpinner province;
	private MySpinner city;
	private EditText name;
	private EditText phone;
	private EditText address;
	private EditText code;
	private Button save;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.card_add_activity);
		ZXUtils.initTitle(this, "添加地址", false);
		findView();
		init();
		
	}

	private void findView() {
		account_type = (MySpinner) findViewById(R.id.account_type);
	}

	private void init() {

//		ArrayList <String> type=new ArrayList<String>();
//		type.add("个人账户");
//		type.add("公司账户");
// 		account_type.setList(type);
//		account_type.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {

	}
}
