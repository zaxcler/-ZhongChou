package com.lianhai.zhongchou.customview;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.lianhai.zhongchou.R;

public class MyLoadingProgress  {
	private static Dialog dialog;

	public MyLoadingProgress() {
		
	}

	/*
	 * 显示dialog
	 */
	public static void showLoadingDialog(Context context){
		dialog=new Dialog(context, AlertDialog.THEME_HOLO_DARK);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		View group=LayoutInflater.from(context).inflate(R.layout.dialog_loading, null);
		dialog.setContentView(group);
		dialog.getWindow().setLayout(250, 250);
		dialog.getWindow().setBackgroundDrawable(context.getResources().getDrawable(R.drawable.halfapha_solid));
		ImageView loading=(ImageView) group.findViewById(R.id.loading);
		Animation animation=AnimationUtils.loadAnimation(context, R.anim.rotation);
//		WindowManager.LayoutParams lp=dialog.getWindow().getAttributes();
//		lp.height=72;
//		lp.width=72;
//		dialog.getWindow().setAttributes(lp);
		
//		Animation animation=new RotateAnimation(0, 359);
//		animation.setRepeatCount(-1);//设置不断重复
//		animation.setDuration(500);
//		animation.setZAdjustment(50);
		animation.setInterpolator(new LinearInterpolator());//设置匀速
//		dialog.getWindow().setLayout(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		loading.setAnimation(animation);
		loading.startAnimation(animation);
		
		dialog.setCancelable(true);
		dialog.show();
	}
	/*
	 * 关闭dialog
	 */
	public static void closeLoadingDialog(){
		dialog.dismiss();
	}
	

}
