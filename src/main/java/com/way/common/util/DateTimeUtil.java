package com.way.common.util;

import org.apache.commons.lang3.StringUtils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @ClassName: DateTimeUtil
 * @Description: TODO
 * @author hf
 * @date 2016年10月28日 下午2:00:03
 * 
 */
public class DateTimeUtil {
	private static SimpleDateFormat normalDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM月dd日 HH:mm");
	private static DateFormat commonDateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private static DateFormat emdhmsyDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);

	/**
	 * @功能描述:将日期转换为字符串
	 * @param date
	 * @return
	 * @创建时间 2008-7-13
	 * @author Administrator
	 */
	public static String parseDateFormat(Date date, String formate) {
		try {
			SimpleDateFormat sdf = getSimpleDateFormat(formate);
			return sdf.format(date);
		} catch (Exception ex) {
			return "";
		}
	}

	/**
	 * @功能描述:將字符串轉換為日期
	 * @param dateStr
	 * @return
	 * @创建时间 2008-7-13
	 * @author Administrator
	 */
	public static Date parseStringToDate(String dateStr, String formate) throws Exception {
		try {
			SimpleDateFormat sdf = getSimpleDateFormat(formate);
			return sdf.parse(dateStr);
		} catch (Exception ex) {
			return null;
		}
	}
	
	/**
     * 将字符串转换为日期（不包括时间）
     *
     * @param dateString "yyyy-MM-dd"格式的日期字符串
     * @return 日期
     */
    public static Date convertToDate(String dateString) {
        try {
        	SimpleDateFormat sdf = getSimpleDateFormat("yyyy-MM-dd");
			return sdf.parse(dateString);
        } catch (Exception e) {
            return null;
        }
    }

	/**
	 * @功能描述: 计算两个日期之间的时间差 传入的对象为两个日期,字符串和时间对象即可
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 * @创建时间 2008-7-12
	 * @author Administrator
	 */
	public static String dateDiff(Object dateFrom, Object dateTo) throws Exception {
		try {

			Date date1 = getDateByObject(dateFrom);
			Date date2 = getDateByObject(dateTo);
			Calendar cal1 = Calendar.getInstance();
			Calendar cal2 = Calendar.getInstance();
			cal1.setTime(date2);
			cal2.setTime(date1);
			float a = ((float) (cal1.getTimeInMillis() - cal2.getTimeInMillis()) / 86400) / 1000;
			int b = (int) a;
			return "" + Math.abs(((b == a) ? b : b + 1));
		} catch (Exception ex) {
			System.out.println(ex);
		}
		return "";
	}

	/**
	 * 日期相减
	 * 
	 * @param date
	 *            日期
	 * @param date1
	 *            日期
	 * @return 返回相减后的日期
	 */
	public static int getDiffDate(Date date, Date date1) {
		return (int) ((date.getTime() - date1.getTime()) / (24 * 3600 * 1000));
	}

	/**
	 * @功能描述:根据传入参数产生日期对象
	 * @param obj
	 * @return
	 * @throws Exception
	 * @创建时间 Oct 20, 2008
	 * @author Administrator
	 */
	public static Date getDateByObject(Object obj) throws Exception {
		if (null == obj) {
			return normalDateFormat.parse(getCurrentDate(null));
		}

		if (obj instanceof Date) {
			return normalDateFormat.parse(normalDateFormat.format(obj));
		}
		if (obj instanceof String) {
			return normalDateFormat.parse((String) obj);
		}
		return null;
	}

	/**
	 *
	 * @功能描述：将分钟转换成小时
	 * @param minute
	 *            分钟数
	 * @return
	 * @throws @创建时间
	 * @author zhh
	 */
	public static String minuteConvertHour(long minute) {
		int minuteInt = new Long(minute).intValue();
		int h = new Double(Math.floor(minute / 60)).intValue();
		int m = minuteInt - h * 60;
		StringBuffer sb = new StringBuffer();
		if (h > 0) {
			sb.append(h);
			sb.append("小时");
		}
		if (m > 0) {
			sb.append(m);
			sb.append("分钟");
		}
		return sb.toString();
	}

	/**
	 * @功能描述:获取当前时间
	 * @return
	 * @创建时间 2008-7-26
	 * @author beedoor
	 */
	public static String getCurrentDate(String format) throws Exception {
		Date impTime = new Date();
		SimpleDateFormat sdf = getSimpleDateFormat(format);
		return sdf.format(impTime);
	}

	/**
	 * @功能描述:获取指定格式的字符串
	 * @return
	 * @创建时间 2008-7-26
	 * @author beedoor
	 */
	public static String getFormatDateStr(Object obj, String format) throws Exception {
		Date d = null;
		SimpleDateFormat sdf = getSimpleDateFormat(format);
		if (obj instanceof String) {
			d = sdf.parse(obj.toString());
		} else if (obj instanceof Date) {
			d = (Date) obj;

		}
		if (null != d) {
			return sdf.format(d);
		}

		return "";
	}

	/**
	 * 功能描述:返回日期格式化对象
	 *
	 * @param format
	 * @return
	 * @创建时间 Jul 28, 2008
	 * @author Administrator
	 */
	public static SimpleDateFormat getSimpleDateFormat(String format) throws Exception {
		try {
			if(StringUtils.isEmpty(format)) {
				return new SimpleDateFormat("yyyy-MM-dd");
			}
			return new SimpleDateFormat(format);
		} catch (Exception ex) {
			return normalDateFormat;
		}
	}

	public static final String YEAR = "YYYY";
	public static final String MM = "MM";
	public static final String DD = "DD";
	public static final String HH = "HH";
	public static final String MI = "MI";
	public static final String SS = "SS";

	/**
	 * @功能描述:返回日期中的指定部分--YYYY 年份，MM 月份，DD 天，HH 小时 MI 分 SS 秒
	 * @param date
	 * @param part
	 * @return
	 * @创建时间 Jul 28, 2008
	 * @author Administrator
	 */
	public static int getDatePart(Date date, String part) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		if (part.equals(YEAR)) {
			return cal.get(Calendar.YEAR);
		} else if (part.equals(MM)) {
			return cal.get(Calendar.MONTH);
		} else if (part.equals(DD)) {
			return cal.get(Calendar.DAY_OF_MONTH);
		} else if (part.equals(HH)) {
			return cal.get(Calendar.HOUR_OF_DAY);
		} else if (part.equals(MI)) {
			return cal.get(Calendar.MINUTE);
		} else if (part.equals(SS)) {
			return cal.get(Calendar.SECOND);
		}

		return 0;
	}

	public static String getSimpleDateString(String date) {
		try {
			Date date22 = getDateByObject(date);
			return simpleDateFormat.format(date22);
		} catch (Exception ex) {

		}
		return "";
	}

	public static Timestamp transferDateTimeStamp(Date date) {
		return new Timestamp(date.getTime());
	}

	// 日期转化为大小写
	public static String dataToUpper(Date date) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		int year = ca.get(Calendar.YEAR);
		int month = ca.get(Calendar.MONTH) + 1;
		int day = ca.get(Calendar.DAY_OF_MONTH);
		return numToUpper(year) + "年" + monthToUppder(month) + "月" + dayToUppder(day) + "日";
	}

	// 将数字转化为大写
	public static String numToUpper(int num) {
		// String u[] = {"零","壹","贰","叁","肆","伍","陆","柒","捌","玖"};
		String u[] = { "零", "一", "二", "三", "四", "五", "六", "七", "八", "九" };
		char[] str = String.valueOf(num).toCharArray();
		String rstr = "";
		for (int i = 0; i < str.length; i++) {
			rstr = rstr + u[Integer.parseInt(str[i] + "")];
		}
		return rstr;
	}

	// 月转化为大写
	public static String monthToUppder(int month) {
		if (month < 10) {
			return numToUpper(month);
		} else if (month == 10) {
			return "十";
		} else {
			return "十" + numToUpper(month - 10);
		}
	}

	// 日转化为大写
	public static String dayToUppder(int day) {
		if (day < 20) {
			return monthToUppder(day);
		} else {
			char[] str = String.valueOf(day).toCharArray();
			if (str[1] == '0') {
				return numToUpper(Integer.parseInt(str[0] + "")) + "十";
			} else {
				return numToUpper(Integer.parseInt(str[0] + "")) + "十" + numToUpper(Integer.parseInt(str[1] + ""));
			}
		}
	}

	// yyyyMM 返回相隔月数
	public static int getBetweenMonth(Date start, Date end) {
		if (start.after(end)) {
			Date t = start;
			start = end;
			end = t;
		}
		Calendar startCalendar = Calendar.getInstance();
		startCalendar.setTime(start);
		Calendar endCalendar = Calendar.getInstance();
		endCalendar.setTime(end);
		Calendar temp = Calendar.getInstance();
		temp.setTime(end);
		temp.add(Calendar.DATE, 1);

		int year = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
		int month = endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);

		if ((startCalendar.get(Calendar.DATE) == 1) && (temp.get(Calendar.DATE) == 1)) {
			return year * 12 + month + 1;
		} else if ((startCalendar.get(Calendar.DATE) != 1) && (temp.get(Calendar.DATE) == 1)) {
			return year * 12 + month;
		} else if ((startCalendar.get(Calendar.DATE) == 1) && (temp.get(Calendar.DATE) != 1)) {
			return year * 12 + month;
		} else {
			return (year * 12 + month - 1) < 0 ? 0 : (year * 12 + month);
		}
	}

	// 获取当前日期的前一天日期
	public static String getLastDate(String format) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1); // 得到前一天
		SimpleDateFormat lformat = new SimpleDateFormat(format);
		return lformat.format(calendar.getTime());
	}

	// 比较2个日期的大小
	public static int compare_date(Date from, Date to) {
		if (from.getTime() > to.getTime()) {
			return 1;
		} else if (from.getTime() < to.getTime()) {
			return -1;
		} else {
			return 0;
		}
	}

	public static int getYear(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.YEAR);
	}

	public static int getMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.MONTH) + 1;
	}

	public static int getDay(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.DAY_OF_MONTH);
	}

	public static Date convertStringToDate(String dateStr) {
		return convertStringToDate(dateStr, "yyyy-MM-dd");
	}

	public static Date convertStringToDate(String dateStr, String formatStr) {
		SimpleDateFormat format = new SimpleDateFormat(formatStr);
		try {
			return format.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String getAddMonth(String dateStr, int offset) {
		String calcRslt = "";
		try {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Calendar cal = Calendar.getInstance();
			cal.setTime(df.parse(dateStr));
			cal.add(Calendar.MONTH, offset);// 月份加1

			calcRslt = commonDateFormat.format(cal.getTime());

		} catch (Exception e) {
			System.out.println("getAddMonth error==" + e.getMessage());
		}
		return calcRslt;
	}

	public static String[] getMonthAry(String beginDate, String endDate) {
		String[] monthAry = null;
		try {
			int count = getMonthCount(beginDate + "-01", endDate + "-01");
			count = count + 1;
			monthAry = new String[count];
			for (int i = 0; i < count; i++) {
				String monthStr = getAddMonth(beginDate + "-01", i);
				monthAry[i] = monthStr.substring(0, monthStr.length() - 3);
			}

		} catch (Exception e) {
			System.out.println("getMonthAry error==" + e.getMessage());
		}
		return monthAry;
	}

	public static String getReduceOneMonth(String dateStr) {
		String calcRslt = "";
		try {
			int year = getYear(convertStringToDate(dateStr));
			int month = getMonth(convertStringToDate(dateStr));
			int day = getDay(convertStringToDate(dateStr));
			String dayStr = "";
			if (day < 10) {
				dayStr = "0" + day;
			} else {
				dayStr = day + "";
			}
			if (month == 1) {
				month = 12;
				calcRslt = (year - 1) + "-" + month + "-" + dayStr;
			} else {
				calcRslt = year + "-" + (month - 1) + "-" + dayStr;
				if ((month - 1) < 10) {
					calcRslt = year + "-0" + (month - 1) + "-" + dayStr;
				} else {
					calcRslt = year + "-" + (month - 1) + "-" + dayStr;
				}
			}
		} catch (Exception e) {
			System.out.println("getReduceOneMonth error==" + e.getMessage());
		}
		return calcRslt;
	}

	public static String getReduceMonth(String dateStr, int offset) {
		String calcRslt = "";
		try {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Calendar cal = Calendar.getInstance();
			cal.setTime(df.parse(dateStr));
			cal.add(Calendar.MONTH, offset);// 月份减1

			calcRslt = commonDateFormat.format(cal.getTime());
		} catch (Exception e) {
			System.out.println("getReduceMonth error==" + e.getMessage());
		}
		return calcRslt;
	}

	// 计算当月最后一天,返回字符串
	public static int getLastDay(String dateStr) {
		int day = 0;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = sdf.parse(dateStr);
			Calendar lastDate = Calendar.getInstance();
			lastDate.setTime(date);
			lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
			lastDate.add(Calendar.MONTH, 1);// 加一个月，变为下月的1号
			lastDate.add(Calendar.DATE, -1);// 减去一天，变为当月最后一天

			String str = sdf.format(lastDate.getTime());
			String dayStr = str.substring(8);
			day = new Integer(dayStr).intValue();
		} catch (Exception e) {
			System.out.println("error:getLastDay()==" + e.getMessage());
		}
		return day;
	}

	// 计算当月第一天,返回字符串
	public static String getFirstDate(String dateStr) {
		String str = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = sdf.parse(dateStr);
			Calendar firstDate = Calendar.getInstance();
			firstDate.setTime(date);
			firstDate.set(Calendar.DATE, 1);// 设为当前月的1号

			str = sdf.format(firstDate.getTime());
		} catch (Exception e) {
			System.out.println("error:getLastDay()==" + e.getMessage());
		}
		return str;
	}

	public static String getAllYearMonthDay(String dateStr) {
		String calcRslt = "";
		try {
			int year = getYear(convertStringToDate(dateStr));
			int month = getMonth(convertStringToDate(dateStr));
			int day = getDay(convertStringToDate(dateStr));
			String monthStr = "";
			if (month < 10) {
				monthStr = "0" + month;
			} else {
				monthStr = month + "";
			}
			String dayStr = "";
			if (day < 10) {
				dayStr = "0" + day;
			} else {
				dayStr = day + "";
			}
			calcRslt = year + "-" + monthStr + "-" + dayStr;
		} catch (Exception e) {
			System.out.println("getAllYearMonthDay error==" + e.getMessage());
		}
		return calcRslt;
	}

	public static String changeDay(String datestr, int offset) {
		SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String changedatestr = bartDateFormat.format(changeDay(convertStringToDate(datestr, "yyyy-MM-dd"), offset))
				.toString();
		return changedatestr;
	}

	public static Date changeDay(Date date, int offset) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_YEAR, (calendar.get(Calendar.DAY_OF_YEAR) + offset));
		return calendar.getTime();
	}

	public static String getChineseDate(String dateStr) {
		Date date = convertStringToDate(dateStr);
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		int year = ca.get(Calendar.YEAR);
		int month = ca.get(Calendar.MONTH) + 1;
		int day = ca.get(Calendar.DAY_OF_MONTH);
		return year + "年" + month + "月" + day + "日";
	}

	/**
	 * 取得上个月的最后一天
	 *
	 * @param
	 * @return String
	 */
	public static String getOnOneMonthEnd() {
		String result = "";
		int year = getYear();
		int month = getMonth();
		if (month == 1) {
			month = 12;
			year = year - 1;
		} else {
			month = month - 1;
		}
		String ymd = year + "-" + month + "-" + "01";
		Date date = convertStringToDate(ymd);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, 1);
		calendar.add(Calendar.DAY_OF_YEAR, -1);
		result = parseDateFormat(calendar.getTime(), "yyyy-MM-dd");
		return result;
	}

	/**
	 * 取得上个月份的第一天
	 *
	 * @param
	 * @return String
	 */
	public static String getOnOneMonthBegin() {
		String result = "";
		int year = getYear();
		int month = getMonth();
		if (month == 1) {
			month = 12;
			year = year - 1;
		} else {
			month = month - 1;
		}
		if (month < 10) {
			result = year + "-0" + month + "-" + "01";
		} else {
			result = year + "-" + month + "-" + "01";
		}
		return result;
	}

	/**
	 * 返回整型的年份
	 *
	 * @return 当前的年份
	 */
	public static int getYear() {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		return year;
	}

	/**
	 * 返回整型的月份
	 *
	 * @return 当前的月份
	 */
	public static int getMonth() {
		Calendar cal = Calendar.getInstance();
		String monthstr = (cal.get(Calendar.MONTH) > 8) ? "" + (cal.get(Calendar.MONTH) + 1)
				: "0" + (cal.get(Calendar.MONTH) + 1);
		int month = new Integer(monthstr).intValue();
		return month;
	}

	/**
	 * 根据一定格式转换字符串的日期格式
	 *
	 * @return 当前的月份
	 */
	public static String getEmdhmsyDate(String dateStr) {
		String newDate = "";
		try {
			Date d = emdhmsyDateFormat.parse(dateStr);
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			newDate = formatter.format(d);
		} catch (Exception e) {
			System.out.println("转换错误==" + e.toString());
		}
		return newDate;
	}

	/**
	 * 将日期向后延后几天
	 *
	 * @param dateStr
	 * @param day
	 * @return
	 * @author CWW
	 */
	public static String dateAddDay(String dateStr, int day) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String newDate = "";
		try {
			Date d = new Date(sdf.parse(dateStr).getTime() + 24 * 3600 * 1000 * day);
			newDate = sdf.format(d);
		} catch (Exception e) {
			System.out.println("转换格式错误==" + e.toString());
		}
		return newDate;
	}

	/**
	 * 将日期提前几天
	 *
	 * @param dateStr
	 * @param day
	 * @return
	 * @author CWW
	 */
	public static String dateSubDay(String dateStr, int day) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String newDate = "";
		try {
			Date d = new Date(sdf.parse(dateStr).getTime() - 24 * 3600 * 1000 * day);
			newDate = sdf.format(d);
		} catch (Exception e) {
			System.out.println("转换格式错误==" + e.toString());
		}
		return newDate;
	}

	/**
	 * 判断指定日期是否为当月
	 *
	 * @param date
	 * @return
	 */
	public static boolean isOnMonth(String date) {
		try {
			String toDate = getCurrentDate("yyyy-MM");
			toDate = toDate.substring(toDate.lastIndexOf("-") + 1);
			if (date == null)
				return false;
			if (date.substring(5, 7).equals(toDate))
				return true;
		} catch (Exception e) {
			System.out.println("判断日期错误!");
		}
		return false;

	}

	/**
	 * 获取两日期相差几个月
	 *
	 * @param date
	 * @param date1
	 * @return
	 * @author cww
	 */
	public static int getMonthCount(String startDate, String endDate) {
		int monthCount = 0;
		int flag = 0;
		try {
			Calendar calendarDate1 = Calendar.getInstance();
			calendarDate1.setTime(DateFormat.getDateInstance().parse(startDate));

			Calendar calendarDate2 = Calendar.getInstance();
			calendarDate2.setTime(DateFormat.getDateInstance().parse(endDate));

			if (calendarDate2.equals(calendarDate1)) {
				return 0;
			}
			if (calendarDate1.after(calendarDate2)) {
				Calendar temp = calendarDate1;
				calendarDate1 = calendarDate2;
				calendarDate2 = temp;
			}
			if (calendarDate2.get(Calendar.DAY_OF_MONTH) < calendarDate1.get(Calendar.DAY_OF_MONTH)) {
				flag = 1;
			}
			if (calendarDate2.get(Calendar.YEAR) > calendarDate1.get(Calendar.YEAR)) {
				monthCount = ((calendarDate2.get(Calendar.YEAR) - calendarDate1.get(Calendar.YEAR)) * 12
						+ calendarDate2.get(Calendar.MONTH) - flag) - calendarDate1.get(Calendar.MONTH);
			} else {
				monthCount = calendarDate2.get(Calendar.MONTH) - calendarDate1.get(Calendar.MONTH) - flag;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return monthCount;
	}

	public static void main(String args[]) throws Exception {
		System.out.println(
				DateTimeUtil.getDiffDate(convertStringToDate("2012-03-25"), convertStringToDate("2012-02-25")));
		// System.out.println(DateUtil.getDiffDate(new
		// Date(),DateUtil.parseStringToDate("2011-01-25".replace("-",
		// "/"),"yyyy/MM/dd")));
		// System.out.println(getDiffDate(new
		// Date(),DateUtil.parseStringToDate("2011-01-25".replace("-",
		// "/"),"yyyy/MM/dd")));
		// System.out.println(getDiffDate(new Date(),new Date("2011/01/03")));
		// String c="000001|0306|6225683521000382909|卞莉|15|333|444||0000|成功";
		// List<String> list=parseStringToList(c,"|");
		// for(int i=0;i<list.size();i++){
		// System.out.println("list==["+i+"]="+list.get(i));
		// }
		// System.out.println("a=="+getAddMonth("2011-12-01",1).substring(0,7));
		// System.out.println("b=="+getDiffDate(new
		// Date(),DateUtil.convertStringToDate("2011-07-21")));
		String a = "2012-02-02";
		// System.out.println("c=="+changeDay("2011-2-27",4));
		// String[] ary=getMonthAry("2012-11","2012-11");
		// for(int i=0;i<ary.length;i++){
		// System.out.println("XXXX==" + ary[i]);

		// }
		int flag = 0;
		// System.out.println("count++=="+flag++);
		String b = "'aaa','vvv'";
		Date xx = new Date();
		System.out.println("count++==" + "2013-07-19 15:40:00.0".substring(0, 10));

		System.out.println(DateTimeUtil.compare_date(parseStringToDate("2014-12-26", "yyyy-MM-dd"),
				parseStringToDate("2014-12-25", "yyyy-MM-dd")));
		System.out.println("2012-08-22".substring(0, 7));

	}

	private static List<String> parseStringToList(String target, String flag) {
		StringBuilder sb = new StringBuilder(target);
		int st = 0;
		int ed = sb.indexOf(flag);
		List<String> list = new LinkedList<String>();
		while (st >= 0 && ed >= 0) {
			list.add(sb.substring(st, ed));
			st = ed + 1;
			ed = sb.indexOf(flag, st);
		}
		if (list.size() > 0) {
			list.add(sb.substring(st, sb.length()));
		}
		return list;
	}

	/**
	 * 比较指定两日期如果str1晚于str2则return true;
	 *
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static boolean secondCompare(String str1, String str2) {
		boolean bea = false;
		SimpleDateFormat sdf_d = new SimpleDateFormat("yyyy-MM-dd");
		Date date1;
		Date date0;
		int flag = 0;
		try {
			date1 = sdf_d.parse(str1);
			date0 = sdf_d.parse(str2);
			flag = date0.compareTo(date1);
			if (flag == 0 || flag == -1) {
				bea = true;
			}
		} catch (ParseException e) {
			bea = false;
		}
		return bea;
	}

	/**
	 * 获得自然月的最后一天日期
	 * 
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public static String getLastDayOfMonth(Date date) throws Exception {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.roll(Calendar.DAY_OF_MONTH, -1);
		return getFormatDateStr(cal.getTime(), "yyyy-MM-dd");
	}

	/**
	 * 指定日期向后推几天 zmt
	 * 
	 * @param dateStr
	 * @param num
	 * @return
	 */
	public static String getDateAddDay(String dateStr, int num) {
		Date date = DateTimeUtil.convertStringToDate(dateStr);
		long time = date.getTime() + (1000L * 60 * 60 * 24 * num);
		if (time > 0) {
			date.setTime(time);
		}
		return commonDateFormat.format(date);
	}
	
	/* 获取月份的第一天跟最后一天 */
	@SuppressWarnings("unused")
	public static String[] getDate(int month) {
		// month不能小于1，此次应该写个排错处理，我就不写了
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date())));
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.DATE, 1);
		int maxDay = calendar.getActualMaximum(Calendar.DATE);
		int minDay = calendar.getActualMinimum(Calendar.DATE);
		String minDayOfMonth = dateFormat.format(calendar.getTime());
		calendar.set(calendar.DATE, maxDay);
		String maxDayOfMonth = dateFormat.format(calendar.getTime());
		String[] dataArray = new String[] { minDayOfMonth, maxDayOfMonth };// 把该月的第一天跟最后一天扔到数组里
		return dataArray;
	}
}
