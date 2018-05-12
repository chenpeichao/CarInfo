package org.zc.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Description:	时间转化工具类
 * Create by @author cpc
 * 2018年4月15日 下午2:27:02
 */
public class DateUtils {
	/**全格式的时间格式字符串*/
	public static String DATE_STR_WHOLE= "yyyy-MM-dd HH:mm:ss";

	/**
	 * 通过秒级时间整数得到指定格式的时间
	 * @param secondRankLongNumber		秒级别整数
	 * @param pattern					指定日期格式
	 * @return
	 */
	public static String getDateFromSecondRankLongNumber(Long secondRankLongNumber, String pattern) {
		return new SimpleDateFormat(pattern).format(new Date(secondRankLongNumber * 1000));
	}
	
	/**
	 * 通过指定格式的时间得到秒级时间整数
	 * @param dateStr					时间字符串
	 * @param pattern					指定日期格式
	 * @return
	 * @throws ParseException 
	 */
	public static Long getSecondRankLongNumberFromDateStr(String dateStr, String pattern) {
		try {
			Long time = new SimpleDateFormat(pattern).parse(dateStr).getTime();
			return time / 1000;
		} catch(ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 通过得到秒级时间整数
	 * @param date						时间
	 * @return
	 * @throws ParseException 
	 */
	public static Long getSecondRankLongNumberFromDate(Date date) {
		Long time = date.getTime();
		return time / 1000;
	}
	
	/**
	 * 测试时间工具类
	 */
	public static void main1(String[] args) {
		System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(getSecondRankLongNumberFromDate(new Date()) * 1000)));;
		System.out.println(DateUtils.getSecondRankLongNumberFromDate(new Date()));
	}

	/**
	 * 得到指定格式的时间字符串--当date为null，默认为当前时间
	 * @param pattern		时间格式
	 * @param date			日期
	 * @return
	 */
	public static String getPatternDateTime(String pattern, Date date) {
		return new SimpleDateFormat(pattern).format(date == null ? new Date() : date);
	}
}
