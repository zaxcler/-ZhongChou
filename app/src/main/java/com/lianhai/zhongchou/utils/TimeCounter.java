package com.lianhai.zhongchou.utils;

import android.util.Log;

import java.util.Date;


public class TimeCounter {
	
	public TimeCounter(){
		
	}
	/**
	 * 返回的时间格式是多少分钟前
	 * @param begaindate
	 * @param endDate
	 * @return
	 */
	public static String CountTime(Date begaindate,Date endDate){
		long begaintime=begaindate.getTime();
		long endtime=endDate.getTime();
		String time = null;
		long result=(endtime-begaintime)/1000;
		if (result<=0) {
			time="刚刚";
		}else if (result<60) {
			time=result+"秒前";
			
		}else if (result>=60 && result<3600) {
			time=(result/60)+"分钟前";
		}else if (result>=3600 && result<3600*24) {
			time=(result/3600)+"小时前";
		}else if (result>=3600*24 && result<3600*24*365) {
			time=(result/(3600*24))+"天前";
		}else if (result>=3600*24*365 ) {
			time=(result/(3600*24*365))+"年前";
		}
		return time;
		
	}
	/**
	 * 换算成天
	 * @param begaindate
	 * @param endDate
	 * @return
	 */
	public static int countTimeOfDay(Date begaindate,Date endDate){
		long a=begaindate.getTime();
		long b=endDate.getTime();
		long countday=(endDate.getTime()-begaindate.getTime());
//		int day=new Long(countday/1000/3600/24).intValue();

		/**
		 * 向上取正
		 */
		double countday_double=Double.parseDouble(countday+"");


		double s=Double.parseDouble(String.valueOf(countday_double / 1000 / 3600 / 24));
		double day_double=Math.ceil(Double.parseDouble(String.valueOf(countday_double / 1000 / 3600 / 24)));
		Log.e("countday",countday+"");
		Log.e("day_double",day_double+"");
		int day= (int) day_double;
		if (day<0)
			day=0;
		return day;
		
	}
	
	/**
	 * 比较两个时间的大小
	 * @param begaindate
	 * @param endDate
	 * @return
	 */
	public static int compareDate(Date begaindate,Date endDate){
		
		long countday=(endDate.getTime()-begaindate.getTime());
		if (countday<0) {
			return -1;
		}else if (countday==0) {
			return 0;
		}else {
			return 1;
		} 
		
	}
	
	
}
