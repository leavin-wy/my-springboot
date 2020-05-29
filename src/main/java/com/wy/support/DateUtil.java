/**
 * Copyright (c) 2015-2016, Chill Zhuang 庄骞 (smallchill@163.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wy.support;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateUtil {
	/**
	 * 获取YYYY格式
	 *
	 * @return
	 */
	public static String getYear() {
		return formatDate(new Date(), "yyyy");
	}

	/**
	 * 获取YYYY格式
	 *
	 * @return
	 */
	public static String getYear(Date date) {
		return formatDate(date, "yyyy");
	}

	/**
	 * 获取YYYY-MM-DD格式
	 *
	 * @return
	 */
	public static String getDay() {
		return formatDate(new Date(), "yyyy-MM-dd");
	}

	/**
	 * 获取YYYY-MM-DD格式
	 *
	 * @return
	 */
	public static String getDay(Date date) {
		return formatDate(date, "yyyy-MM-dd");
	}

	/**
	 * 获取YYYYMMDD格式
	 *
	 * @return
	 */
	public static String getDays() {
		return formatDate(new Date(), "yyyyMMdd");
	}

	/**
	 * 获取YYYYMMDD格式
	 *
	 * @return
	 */
	public static String getDays(Date date) {
		return formatDate(date, "yyyyMMdd");
	}

	/**
	 * 获取YYYY-MM-DD HH:mm:ss格式
	 *
	 * @return
	 */
	public static String getTime() {
		return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 获取YYYY-MM-DD HH:mm:ss.SSS格式
	 *
	 * @return
	 */
	public static String getMsTime() {
		return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss.SSS");
	}

	/**
	 * 获取YYYYMMDDHHmmss格式
	 *
	 * @return
	 */
	public static String getAllTime() {
		return formatDate(new Date(), "yyyyMMddHHmmss");
	}

	/**
	 * 获取YYYY-MM-DD HH:mm:ss格式
	 *
	 * @return
	 */
	public static String getTime(Date date) {
		return formatDate(date, "yyyy-MM-dd HH:mm:ss");
	}

	public static String formatDate(Date date, String pattern) {
		String formatDate = null;
		if (StringUtils.isNotBlank(pattern)) {
			formatDate = DateFormatUtils.format(date, pattern);
		} else {
			formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
		}
		return formatDate;
	}

	/**
	 * @Title: compareDate
	 * @Description:(日期比较，如果s>=e 返回true 否则返回false)
	 * @param s
	 * @param e
	 * @return boolean
	 * @throws
	 * @author luguosui
	 */
	public static boolean compareDate(String s, String e) {
		if (parseDate(s) == null || parseDate(e) == null) {
			return false;
		}
		return parseDate(s).getTime() >= parseDate(e).getTime();
	}

	/**
	 * 格式化日期
	 *
	 * @return
	 */
	public static Date parseDate(String date) {
		return parse(date,"yyyy-MM-dd");
	}

	/**
	 * 格式化日期
	 *
	 * @return
	 */
	public static Date parseTime(String date) {
		return parse(date,"yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 格式化日期
	 *
	 * @return
	 */
	public static Date parse(String date, String pattern) {
		try {
			SimpleDateFormat sdfd = new SimpleDateFormat(pattern);
			Date dateStr = sdfd.parse(date);
			return dateStr;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 格式化日期
	 *
	 * @return
	 */
	public static String format(Date date, String pattern) {
		return DateFormatUtils.format(date, pattern);
	}

	/**
	 * 把日期转换为Timestamp
	 *
	 * @param date
	 * @return
	 */
	public static Timestamp format(Date date) {
		return new Timestamp(date.getTime());
	}

	/**
	 * 校验日期是否合法
	 *
	 * @return
	 */
	public static boolean isValidDate(String s) {
		return parse(s, "yyyy-MM-dd HH:mm:ss") != null;
	}

	/**
	 * 校验日期是否合法
	 *
	 * @return
	 */
	public static boolean isValidDate(String s, String pattern) {
		return parse(s, pattern) != null;
	}

	public static int getDiffYear(String startTime, String endTime) {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		try {
			int years = (int) (((fmt.parse(endTime).getTime() - fmt.parse(
					startTime).getTime()) / (1000 * 60 * 60 * 24)) / 365);
			return years;
		} catch (Exception e) {
			// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
			return 0;
		}
	}

	/**
	 * <li>功能描述：时间相减得到天数
	 *
	 * @param beginDateStr
	 * @param endDateStr
	 * @return long
	 * @author Administrator
	 */
	public static long getDaySub(String beginDateStr, String endDateStr) {
		long day = 0;
		SimpleDateFormat format = new SimpleDateFormat(
				"yyyy-MM-dd");
		Date beginDate = null;
		Date endDate = null;

		try {
			beginDate = format.parse(beginDateStr);
			endDate = format.parse(endDateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		day = (endDate.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000);
		// System.out.println("相隔的天数="+day);

		return day;
	}

	/**
	 * 得到n天之后的日期
	 *
	 * @param days
	 * @return
	 */
	public static String getAfterDayDate(String days) {
		int daysInt = Integer.parseInt(days);

		Calendar canlendar = Calendar.getInstance(); // java.util包
		canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
		Date date = canlendar.getTime();

		SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateStr = sdfd.format(date);

		return dateStr;
	}

	public static Date getAfterMonthDate(Date date1,int month) {
		Calendar canlendar = Calendar.getInstance(); // java.util包
		canlendar.setTime(date1);
		canlendar.add(Calendar.DAY_OF_MONTH, month); // 日期减 如果不够减会将月变动
		Date date = canlendar.getTime();
		return parse(formatDate(date,"yyyy/MM/dd"),"yyyy/MM/dd");
	}

	/**
	 * 得到n天之后是周几
	 *
	 * @param days
	 * @return
	 */
	public static String getAfterDayWeek(String days) {
		int daysInt = Integer.parseInt(days);

		Calendar canlendar = Calendar.getInstance(); // java.util包
		canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
		Date date = canlendar.getTime();

		SimpleDateFormat sdf = new SimpleDateFormat("E");
		String dateStr = sdf.format(date);

		return dateStr;
	}

//	public static String getYear(Date date) {
//		Calendar canlendar = Calendar.getInstance(); // java.util包
//		canlendar.setTime(date);
//		canlendar.get(Calendar.YEAR)
//
//		return dateStr;
//	}

	/**
	 * 把yyyyMMdd字符格式转换为yyyy-mm-dd格式
	 * @param date
	 * @return
	 */
	public static String getDateByPattern(String date){
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyymmdd");
			sdf.setLenient(false);//不进行进位处理，设置宽容度为false
			Date newDate = sdf.parse(date);

			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/mm/dd");
			String dateStr = sdf1.format(newDate);

//			System.out.println(dateStr);
//			System.out.println(newDate);

			return dateStr;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public static String getYearMonth(Date date){
		Calendar cal = Calendar.getInstance();
//使用给定的 Date 设置此 Calendar 的时间
		cal.setTime(date);
		String a="";
		int year=cal.get(Calendar.YEAR);
		int month=cal.get(Calendar.MONTH)+1;
		if(month<10){
			a=year+"0"+month;
		}else{
			a=year+""+month;
		}
return a;
	}

	public static List<String> getYMDatesBetweenTwoDate(Date beginDate, Date endDate) {
		List<String> lDate = new ArrayList<String>();
		lDate.add(getYearMonth(beginDate));//把开始时间加入集合
		Calendar cal = Calendar.getInstance();
//使用给定的 Date 设置此 Calendar 的时间
		cal.setTime(beginDate);
		boolean bContinue = true;
		while (bContinue) {
//根据日历的规则，为给定的日历字段添加或减去指定的时间量
			cal.add(Calendar.MONTH, 1);
// 测试此日期是否在指定日期之后
			if (endDate.after(cal.getTime())) {
				lDate.add(getYearMonth(cal.getTime()));
			} else {
				break;
			}
		}
		lDate.add(getYearMonth(endDate));//把结束时间加入集合
		return lDate;
	}

	public static List getDatesBetweenTwoDate(Date beginDate, Date endDate) {
		List lDate = new ArrayList();
		lDate.add(beginDate);//把开始时间加入集合
		Calendar cal = Calendar.getInstance();
//使用给定的 Date 设置此 Calendar 的时间
		cal.setTime(beginDate);
		boolean bContinue = true;
		while (bContinue) {
//根据日历的规则，为给定的日历字段添加或减去指定的时间量
			cal.add(Calendar.DAY_OF_MONTH, 1);
// 测试此日期是否在指定日期之后
			if (endDate.after(cal.getTime())) {
				lDate.add(cal.getTime());
			} else {
				break;
			}
		}
		lDate.add(endDate);//把结束时间加入集合
		return lDate;
	}

	public static String getFirstDayDateOfMonth(String date) {
		String a= getYearMonth(parse(date,"yyyy/mm/dd"));
		System.out.println(a+"/01");
		return a+"/01";

	}

	public static List<String> getYmDatesByStartEndDate(String startDate, String endDate){
		try {
			List<String> ymDates=new ArrayList<String>();
			String firtStart=getFirstDayDateOfMonth(startDate);
			String firtSEnd=getFirstDayDateOfMonth(endDate);
//			 ymDates=getYMDatesBetweenTwoDate(firtStart,firtSEnd);
			return ymDates;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		String date="2017/12/09";
		String enddate="2018/03/11";
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-mm-dd");
		String dateStr = sdf1.format(new Date());
		System.out.println(dateStr);
		getYmDatesByStartEndDate(date,enddate);




	}

}
