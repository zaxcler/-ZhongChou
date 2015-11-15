package com.lianhai.zhongchou.mypage;

import com.lianhai.zhongchou.R;
import com.lianhai.zhongchou.adapter.FragmentAdapter;
import com.lianhai.zhongchou.config.BaseInfo;
import com.lianhai.zhongchou.config.MyApplication;
import com.lianhai.zhongchou.utils.ZXUtils;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MyProjectListActivity extends FragmentActivity implements OnClickListener {
	private TextView support;
	private TextView collection;
	private TextView sponsor;// 发起
	private View scrollbar;
	private ViewPager view_content;
	
	private int lastPosition = MyApplication.getScreen_width() / 9 * 3;// scorllbar上次滑动到的位置
	
	private int type;//标志是从哪个地方进来的

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.myproject_list_activity);
		ZXUtils.initTitle(this, "用户中心", false);
		type=getIntent().getIntExtra("type", 0);
		findView();
		init();

	}

	private void findView() {
		support = (TextView) findViewById(R.id.support);
		collection = (TextView) findViewById(R.id.collection);
		sponsor = (TextView) findViewById(R.id.sponsor);
//		scrollbar = (View) findViewById(R.id.scrollbar);
		view_content = (ViewPager) findViewById(R.id.view_content);

	}

	private void init() {
		support.setOnClickListener(this);
		collection.setOnClickListener(this);
		sponsor.setOnClickListener(this);


		
		

//		// 初始化话水平的蓝色滑动条
//		LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) scrollbar.getLayoutParams();
//		int scrollbarLength = MyApplication.getScreen_width() / 9;
//		params.width = scrollbarLength;
//		scrollbar.setLayoutParams(params);
		
		
		FragmentAdapter adapter=new FragmentAdapter(getSupportFragmentManager());


		ProjectListFragment fragment1=new ProjectListFragment();
		Bundle bundle1=new Bundle();
		bundle1.putInt("where",0);
		fragment1.setArguments(bundle1);
		adapter.fragments.add(fragment1);

		ProjectListFragment fragment2=new ProjectListFragment();
		Bundle bundle2=new Bundle();
		bundle2.putInt("where", 1);
		fragment2.setArguments(bundle2);
		adapter.fragments.add(fragment2);

		ProjectListFragment fragment3=new ProjectListFragment();
		Bundle bundle3=new Bundle();
		bundle3.putInt("where",2);
		fragment3.setArguments(bundle3);
		adapter.fragments.add(fragment3);

		view_content.setAdapter(adapter);
		view_content.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int i, float v, int i1) {

			}

			@Override
			public void onPageSelected(int i) {
				view_content.setCurrentItem(i);
//				scrollbarMove(i);
				chooseTab(i);
			}

			@Override
			public void onPageScrollStateChanged(int i) {

			}
		});
		
		/*
		 * 根据不同的type初始化
		 */
		switch (type) {
		case 0:
			chooseTab(0);
//			support.setTextColor(getResources().getColor(R.color.blue));
//			scrollbarMove(0);
//			view_content.setCurrentItem(0);
			break;
		case 1:
			chooseTab(1);
//			collection.setTextColor(getResources().getColor(R.color.blue));
//			scrollbarMove(1);
//			view_content.setCurrentItem(1);
			break;
		case 2:
			chooseTab(2);
//			sponsor.setTextColor(getResources().getColor(R.color.blue));
//			scrollbarMove(2);
//			view_content.setCurrentItem(2);
			break;

		}
		

	}

	@Override
	public void onClick(View v) {
		clearState();
		switch (v.getId()) {
		case R.id.support:
			chooseTab(0);
//			support.setTextColor(getResources().getColor(R.color.blue));
//			scrollbarMove(0);
//			view_content.setCurrentItem(0);
			break;
		case R.id.collection:
			chooseTab(1);
//			collection.setTextColor(getResources().getColor(R.color.blue));
//			scrollbarMove(1);
//			view_content.setCurrentItem(1);
			break;
		case R.id.sponsor:
			chooseTab(2);
//			sponsor.setTextColor(getResources().getColor(R.color.blue));
//			scrollbarMove(2);
//			view_content.setCurrentItem(2);
			break;

		}

	}

//	/**
//	 * 滑动条
//	 *
//	 * @param postion
//	 */
//	private void scrollbarMove(int postion) {
//		int fromPostion = lastPosition;
//		int newPostion = 0;
//		Animation animation;
//		switch (postion) {
//		case 0:
//			newPostion = MyApplication.getScreen_width() / 9 * 2;
//			break;
//		case 1:
//			newPostion = MyApplication.getScreen_width() / 9 * 4;
//			break;
//		case 2:
//			newPostion = MyApplication.getScreen_width() / 9 * 6;
//
//			break;
//		}
//		animation = new TranslateAnimation(fromPostion, newPostion, 0, 0);
//		animation.setFillAfter(true);
//		animation.setDuration(500);
//		animation.setInterpolator(new DecelerateInterpolator());
//		scrollbar.setAnimation(animation);
//		scrollbar.startAnimation(animation);
//		lastPosition = newPostion;
//	}

	/**
	 * 选择某个table
	 * @param index
	 */
	private void chooseTab(int index){
		clearState();
		view_content.setCurrentItem(index);
		switch (index){
			case 0:
				support.setTextColor(getResources().getColor(R.color.white));
				/**
				 * 兼容低版本
				 */
				support.setBackgroundDrawable(getResources().getDrawable(R.drawable.corners_half_left_midyellow));
				break;
			case 1:
				collection.setTextColor(getResources().getColor(R.color.white));
				collection.setBackgroundColor(getResources().getColor(R.color.midyellow));
				break;
			case 2:
				sponsor.setBackgroundDrawable(getResources().getDrawable(R.drawable.corners_half_right_midyellow));
				sponsor.setTextColor(getResources().getColor(R.color.white));
				break;
		}

	}
	/**
	 * 清楚选中状态
	 */
	public void clearState(){
		support.setTextColor(getResources().getColor(R.color.midyellow));
		collection.setTextColor(getResources().getColor(R.color.midyellow));
		sponsor.setTextColor(getResources().getColor(R.color.midyellow));
		/**
		 * 兼容低版本
		 */
		support.setBackgroundDrawable(getResources().getDrawable(R.drawable.corners_half_left_white));
		collection.setBackgroundColor(getResources().getColor(R.color.white));
		sponsor.setBackgroundDrawable(getResources().getDrawable(R.drawable.corners_half_right_white));

	}
	
	

}
