package com.lianhai.zhongchou.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;


/**
 * 解决在类似scrollview的控件中只显示一条记录的情况
 */

public class MyListview extends ListView {


	public MyListview(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public MyListview(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public MyListview(Context context) {
		super(context);
	}
	/*
	 * 使listview在scrollview中也能显示多条  不写listview的高度为1 能滚动
	 * (non-Javadoc)
	 * @see android.widget.ListView#onMeasure(int, int)
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		 int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
	                MeasureSpec.AT_MOST);

		super.onMeasure(widthMeasureSpec, expandSpec);
	}
}
