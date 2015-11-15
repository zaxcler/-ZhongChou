package com.lianhai.zhongchou.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ListView;

/**
 * Created by zaxcler on 15/11/14.
 * 处理与swiplereflashlayout下拉刷新的冲突
 */
public class MyListview1 extends ListView {

    private int mLastY;//最新的Y值
    private int mY;//y值
    private int mScaled;//系统滑动的默认值


    public MyListview1(Context context) {
        super(context);
        mScaled = ViewConfiguration.get(context).getScaledDoubleTapSlop();
    }

    public MyListview1(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScaled = ViewConfiguration.get(context).getScaledDoubleTapSlop();
    }

    public MyListview1(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mScaled = ViewConfiguration.get(context).getScaledDoubleTapSlop();
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        /**
         * 当滑动到listview的顶部时才让外部得到滑动事件
         */

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.e("ACTION_DOWN", mY + "");
                mY = getScrollYValue();
                break;
            case MotionEvent.ACTION_MOVE:
                mLastY = getScrollYValue();
                Log.e("ACTION_MOVE", mLastY + "");
                break;
            case MotionEvent.ACTION_UP:
                Log.e("ACTION_UP", mLastY + "");
                mLastY = getScrollYValue();
                break;

        }

        Log.e("getFirstVisiblePosition",getFirstVisiblePosition()+"");

        if (getFirstVisiblePosition()== 0) {
            if (mLastY - mY > mScaled+60) {
                Log.e("mLastY-mY> mScaled","+++"+ (mLastY - mY ) + "");
                return true;
            } else {
                Log.e("mLastY-mY> mScaled","---"+(mLastY - mY ) + "");
                return false;
            }

        } else {

            return super.onTouchEvent(ev);
        }

    }

    /**
     * listview没有办法利用getscrolly获取滑动高度
     * @return
     */
    public int getScrollYValue() {
        View c = this.getChildAt(0);
        if (c == null) {
        return 0;
        }
        int firstVisiblePosition = this.getFirstVisiblePosition();
        int top = c.getTop();
        return -top + firstVisiblePosition * c.getHeight();
    }

//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        /**
//         * 当滑动到listview的顶部时才让外部得到滑动事件
//         */
//
//        switch (ev.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                Log.e("ACTION_DOWN", mY + "");
//                mY = getScrollYValue();
//                break;
//            case MotionEvent.ACTION_MOVE:
//                mLastY = getScrollYValue();
//                Log.e("ACTION_MOVE", mLastY + "");
//                break;
//            case MotionEvent.ACTION_UP:
//                Log.e("ACTION_UP", mLastY + "");
//                mLastY = getScrollYValue();
//                break;
//
//        }
//
//
//        if (getScrollYValue() == 0) {
//            if (mLastY - mY > mScaled) {
//                Log.e("mLastY-mY> mScaled", (mLastY - mY > mScaled) + "");
//                return true;
//            } else {
//                Log.e("mLastY-mY> mScaled", (mLastY - mY > mScaled) + "");
//                return false;
//            }
//
//        } else {
//
//            return super.onTouchEvent(ev);
//        }
//    }


}
