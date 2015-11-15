package com.lianhai.zhongchou.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ScrollView;

public class MyScrollView extends ScrollView {
	private int mTouchSlop;
	// 上一次触摸时的X坐标
	private float mPrevX;
	// 上一次触摸时的y坐标
	private float mPrevY;
	public MyScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		 // 触发移动事件的最短距离，如果小于这个距离就不触发移动控件
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
	}

	public MyScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		 // 触发移动事件的最短距离，如果小于这个距离就不触发移动控件
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
	}

	public MyScrollView(Context context) {
		super(context);
		 // 触发移动事件的最短距离，如果小于这个距离就不触发移动控件
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
	}
	
	
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent event) {
		
		  switch (event.getAction()) {
          case MotionEvent.ACTION_DOWN:
              mPrevX = event.getX();
              break;

          case MotionEvent.ACTION_MOVE:
              final float eventX = event.getX();
              float xDiff = Math.abs(eventX - mPrevX);
              // Log.d("refresh" ,"move----" + eventX + "   " + mPrevX + "   " + mTouchSlop);
              // 增加60的容差，让下拉刷新在竖直滑动时就可以触发
              if (xDiff > mTouchSlop + 60) {
                  return false;
              }
      }
		return super.onInterceptTouchEvent(event);
	}
	

}
