package com.lianhai.zhongchou.adapter;

import java.util.List;

import com.lianhai.zhongchou.R;
import com.lianhai.zhongchou.config.MyApplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class ImageAdapter extends CommonAdapter<Bitmap> {
	

	public ImageAdapter(Context context, List<Bitmap> list, int resource) {
		super(context, list, resource);
	}

	@Override
	public void setDataToItem(ViewHolder holder, Bitmap t) {
		ImageView photo=holder.getView(R.id.photo);
		LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,MyApplication.getScreen_width()/3-10);
		photo.setLayoutParams(params);
		photo.setImageBitmap(t);
		
		
	}
	/**
	 * 添加图片集合
	 * @param
	 */
	public void addList(List<Bitmap> bitmaps){
		this.list.clear();
		this.list.addAll(bitmaps);
	}
	/**
	 * 添加图片
	 * @param
	 */
	public void addBitmap(Bitmap bitmap){
		this.list.add(bitmap);
	}
	/**
	 * 删除图片
	 * @param
	 */
	public void removeBitmap(int i ){
		this.list.remove(i);
	}


}
