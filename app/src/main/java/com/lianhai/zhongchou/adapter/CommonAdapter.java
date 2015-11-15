package com.lianhai.zhongchou.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class CommonAdapter<T> extends BaseAdapter {

	protected Context context;
	protected List<T> list ;
	protected int resource;
	protected int position;


	protected CommonAdapter(Context context, List<T> list, int resource) {
		this.context = context;
		this.list = list;
		this.resource = resource;
	}

	@Override
	public int getCount() {

		return list.size();
	}

	@Override
	public T getItem(int position) {

		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		this.position=position;
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = ViewHolder.getInstance(context, position, convertView, parent, resource);
		setDataToItem(holder, getItem(position));
		convertView = holder.getConvertView();

		return convertView;
	}

	public abstract void setDataToItem(ViewHolder holder, T t);

}
