package com.lianhai.zhongchou.adapter;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class FragmentAdapter extends FragmentPagerAdapter{
	public FragmentManager manager;
	public List<Fragment> fragments;

	public FragmentAdapter(FragmentManager fm) {
		super(fm);
		fragments=new ArrayList<Fragment>();
	}

	@Override
	public Fragment getItem(int arg0) {
		return fragments.get(arg0);
	}

	@Override
	public int getCount() {
		return fragments.size();
	}
	

}
