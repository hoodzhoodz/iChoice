package com.choicemmed.common;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class LocalLizationUtil {

	/**
	 * 获取当前小数点分隔符
	 * 
	 * @return
	 */
	public static String getDecimalSeparator() {
		NumberFormat format = DecimalFormat.getNumberInstance(Locale
				.getDefault());// 获取当前区域数字格式
		DecimalFormatSymbols dfs = ((DecimalFormat) format)
				.getDecimalFormatSymbols();
		char decimalSeparator = dfs.getDecimalSeparator();
		return decimalSeparator + "";
	}

	/**
	 * 获取本地显示的小数转化为double处理
	 * 
	 * @param str
	 * @return
	 * @throws ParseException
	 */
	public static double getDoubleWithLocal(String str) {
		if (str.contains(".")) {
			return Double.valueOf(str);
		}
		NumberFormat format = NumberFormat.getNumberInstance(Locale
				.getDefault());// 获取当前区域数字格式
		try {
			return format.parse(str).doubleValue();
		} catch (ParseException e) {
			e.printStackTrace();
			return Double.NaN;
		}
	}
	
	/**
	 * 格式化 日期 
	 * 默认 yyyy-mm-dd
	 * @param date
	 * @return
	 */
	public String formartDate(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		date = date.substring(6, date.length() - 2);
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(Long.valueOf(date));
		return sdf.format(calendar.getTime());

	}
	

}
