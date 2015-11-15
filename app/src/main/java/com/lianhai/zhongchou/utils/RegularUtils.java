package com.lianhai.zhongchou.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegularUtils {
	/**
	 * 判断是否是电话号码
	 * @return
	 */
	public static boolean isPhoneNumber(String phoneString){
		//电话号码格式
//		Pattern p=Pattern.compile("/^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$/");
		Pattern p=Pattern.compile("/^1(3|4|5|7|8)\\d{9}$/");
		Matcher m=p.matcher(phoneString);
		
		return m.matches();
		
	}
	/**
	 * 判断是否是身份证
	 * @return
	 */
	public static boolean isIDCard(String phoneString){
		//
		Pattern p=Pattern.compile("(^\\d{15}$)|(^\\d{18}$)|(^\\d{17}(X|x)$)");
		Matcher m=p.matcher(phoneString);
		return m.matches();

	}
}
