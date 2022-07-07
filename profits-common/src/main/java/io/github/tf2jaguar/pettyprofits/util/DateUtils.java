package io.github.tf2jaguar.pettyprofits.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class DateUtils {

    public static final String PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String PATTERN_FORMATE = "yyyy/MM/dd";
    public static final String PATTERN_NO_SECOND = "yyyy-MM-dd HH:mm";
    public static final String PATTERN_NO_MINUTE = "yyyy-MM-dd HH";
    public static final String PATTERN_NO_HOUR = "yyyy-MM-dd";
    public static final String PATTERN_NO_DAY = "yyyy-MM";
    public static final String PATTERN_HOUR_MINUTE = "HH:mm";
    public static final String PATTERN_HOUR = "HH";
    public static final String PATTERN_HOUR_MINUTE_SECOND = "HH:mm:ss";
    public static final String PATTERN_BEGIN_OF_DAY = "yyyy-MM-dd 00:00:00";
    public static final String PATTERN_END_OF_DAY = "yyyy-MM-dd 23:59:59";
    public static final String PATTERN_NO_HOUR_NO_MINUS = "yyyyMMdd";
    public static final String PATTERN_NO_YEAR = "MM-dd";
    public static final String SUFFIX_BEGIN_OF_DAY = " 00:00:00";
    public static final String SUFFIX_END_OF_DAY = " 23:59:59";
    public static final String PATTERN_CHINESE = "MM月dd日 HH:mm";
    public static final String PATTERN_WITH_HOURS = "yyyyMMddHH";
    public static final String PATTERN_WITH_MINUTES = "yyyyMMddHHmm";
    public static final String PATTERN_WITH_SECONDS = "yyyyMMddHHmmss";
    public static final String PATTERN_CHINESE_NO_MIN = "yyyy年MM月dd日";
    public static final String PATTERN_CHINESE_HOUR_MIN = "yyyy年MM月dd日 HH:mm";
    public static final String PATTERN_CHINESE_HOUR_MIN_SECOND = "yyyy年MM月dd日 HH:mm:ss";
    public static final String PATTERN_CHINESE_MONTH_DAY = "MM月dd日";

    public static final String PATTERN_CHINESE_NO_BLANK = "yyyy年MM月dd日HH:mm";


    public static final String PATTERN_OF_MINUS = "HH:mm";

    public static final Long ONE_DAY = 24 * 60 * 60 * 1000L;

    public static final Long FIFTEEN_MIN = 15 * 60 * 1000L;
    public static final Long ONE_MIN = 1 * 60 * 1000L;
    public static final Long ONE_HOUR = 60 * 60 * 1000L;

    public static Date previousHour(Date date, Integer hour) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, -hour);
        return calendar.getTime();
    }

    /**
     * 往前面推hour个小时
     */
    public static Date previousHour(Integer hour) {
        return previousHour(new Date(), hour);
    }

    /**
     * @author:
     * @desc: 往前面推min分钟
     * @date: 2020/10/15 10:47 上午
     * @params: [min]
     * @return: java.util.Date
     */
    public static Date previousMin(Integer min) {
        return previousMin(new Date(), min);
    }

    public static Date previousMin(Date date, Integer min) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, -min);
        return calendar.getTime();
    }

    public static Date previousSeconds(Integer seconds) {
        return previousSeconds(new Date(), seconds);
    }

    public static Date previousSeconds(Date date, Integer seconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, -seconds);
        return calendar.getTime();
    }

    /**
     * 当前时间倒退months月
     *
     * @param months months
     * @return date
     */
    public static Date previousMonthOf(final int months) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -months);
        return calendar.getTime();
    }

    /**
     * date时间倒退months月是哪一天
     *
     * @param date   date
     * @param months months
     * @return date
     */
    public static Date previousMonthOf(final Date date, final int months) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -months);
        return calendar.getTime();
    }

    /**
     * 当前时间倒退days天
     *
     * @param days days
     * @return date
     */
    public static Date previousDayOf(final int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -days);
        return calendar.getTime();
    }

    /**
     * date时间倒退days天是哪一天
     *
     * @param date date
     * @param days days
     * @return date
     */
    public static Date previousDayOf(final Date date, final int days) {
        if (days == 0) {
            return date;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -days);
        return calendar.getTime();
    }

    /**
     * date时间倒退days天是哪一天
     * <p>
     * 返回pattern格式
     *
     * @param date    date
     * @param days    days
     * @param pattern pattern
     * @return date
     */
    public static String previousDayOf(final Date date, final int days, final String pattern) {
        return date2String(pattern, previousDayOf(date, days));
    }

    public static String beginOfTheDay(String dateString, String arg1Pattern) {
        Date date = string2date(arg1Pattern, dateString);
        return date2String(PATTERN_BEGIN_OF_DAY, date);
    }

    public static String beginOfTheDay(Date date) {
        return date2String(PATTERN_BEGIN_OF_DAY, date);
    }

    public static String endOfTheDay(String dateString, String arg1Pattern) {
        Date date = string2date(arg1Pattern, dateString);
        return date2String(PATTERN_END_OF_DAY, date);
    }

    public static String endOfTheDay(Date date) {
        return date2String(PATTERN_END_OF_DAY, date);
    }

    public static Date beginDateOfTheDay(Date date) {
        return getTimeOfDate(date, 0, 0, 0);
    }

    public static Date endDateOfTheDay(Date date) {
        return getTimeOfDate(date, 23, 59, 59);
    }

    public static Date string2date(String pattern, String dateTime) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            return sdf.parse(dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date string2date(String dateTime) {
        SimpleDateFormat sdf = new SimpleDateFormat(PATTERN);
        try {
            return sdf.parse(dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 返回当前格式化后的时间字符串。yyyy-MM-dd hh:mm:ss
     *
     * @return yyyy-MM-dd hh:mm:ss
     */
    public static String nowDateTimeString() {
        return date2String(new Date());
    }

    public static String date2String(String pattern, Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    public static String date2String(Date date) {
        return date2String(PATTERN, date);
    }

    /**
     * 单位分钟
     * d2表示时间-d1表示时间
     *
     * @param arg1 date1
     * @param arg2 date2
     * @return interval
     */
    public static long minutesOfArg2SubArg1(Date arg1, Date arg2) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(arg1);
        long d1Mills = calendar.getTimeInMillis();
        calendar.setTime(arg2);
        return (calendar.getTimeInMillis() - d1Mills) / (60L * 1000L);
    }

    public static long minutesOfArg2SubArg1(String arg1, String arg2) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(PATTERN);
        return minutesOfArg2SubArg1(sdf.parse(arg1), sdf.parse(arg2));
    }

    /**
     * date1 is earlier?，时间在前？
     *
     * @param arg1 date1
     * @param arg2 date2
     * @return true, date1 earlier than date2 or equal to date2;
     */
    public static boolean arg1IsEarlierOrEqualed(Date arg1, Date arg2) {
        return minutesOfArg2SubArg1(arg1, arg2) >= 0;
    }

    public static boolean arg1IsEarlierOrEqualed(String arg1, String arg2) throws ParseException {
        return minutesOfArg2SubArg1(arg1, arg2) >= 0;
    }

    /**
     * 获取两个时间差
     *
     * @param start 开始时间
     * @param end   结束时间
     * @return 单位（精确两位小数）
     */
    public static double getDistance(Date start, Date end, Long timeUnit) {
        double days;
        double interval = timeUnit * 1.00d;
        long time1 = start.getTime();
        long time2 = end.getTime();

        long diff;
        if (time1 < time2) {
            diff = time2 - time1;
        } else {
            diff = time1 - time2;
        }
        days = diff / interval;
        return (double) (Math.round(days * 100)) / 100;
    }

    /**
     * 获取两个时间相差的天数
     *
     * @param start 开始时间
     * @param end   结束时间
     * @return 天数 （精确两位小数）
     */
    public static double getDistanceDay(Date start, Date end) {
        return getDistance(start, end, ONE_DAY);
    }

    /**
     * 获取两个时间相差的分钟数
     *
     * @param start 开始时间
     * @param end   结束时间
     * @return 分钟数 （精确两位小数）
     */
    public static double getDistanceMinutes(Date start, Date end) {
        return getDistance(start, end, ONE_MIN);
    }

    /**
     * 获取两个时间段内的全部天
     *
     * @param dStart 开始时间
     * @param dEnd   结束时间
     * @return 区间内的所有时间，按照秒计算
     */
    public static List<Date> daysBetween(Date dStart, Date dEnd) {
        Calendar cStart = Calendar.getInstance();
        cStart.setTime(dStart);
        List<Date> dateList = new LinkedList<>();
        dateList.add(dStart);
        // 此日期是否在指定日期之后
        while (dEnd.after(cStart.getTime())) {
            cStart.add(Calendar.DAY_OF_MONTH, 1);
            dateList.add(cStart.getTime());
        }
        return dateList;
    }

    /**
     * 获取当天具体时间点的时间戳
     *
     * @return
     */
    public static Date getTodaySpecificTimePointTime(int specificHour, int specificMinute, int specificSecond) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, specificHour);
        cal.set(Calendar.MINUTE, specificMinute);
        cal.set(Calendar.SECOND, specificSecond);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 返回当前时间所在月份的第一天0:00:00 和最后一天23:59:59，精确到豪秒
     */
    public static Date[] monthStartAndEndDay() {
        Calendar firstDay = Calendar.getInstance();
        firstDay.add(Calendar.MONTH, 0);
        firstDay.set(Calendar.DAY_OF_MONTH, 1);
        firstDay.set(Calendar.HOUR_OF_DAY, 0);
        firstDay.set(Calendar.MINUTE, 0);
        firstDay.set(Calendar.SECOND, 0);
        firstDay.set(Calendar.MILLISECOND, 0);

        Calendar lastDay = Calendar.getInstance();
        lastDay.set(Calendar.DAY_OF_MONTH, lastDay.getActualMaximum(Calendar.DAY_OF_MONTH));
        lastDay.set(Calendar.HOUR_OF_DAY, 23);
        lastDay.set(Calendar.MINUTE, 59);
        lastDay.set(Calendar.SECOND, 59);
        lastDay.set(Calendar.MILLISECOND, 59);
        return new Date[]{firstDay.getTime(), lastDay.getTime()};
    }

    /**
     * 获取当天指定时间点的秒值
     *
     * @param hour
     * @param minute
     * @param second
     * @return
     */
    public static Long getSecondsOfCurrentDay(Integer hour, Integer minute, Integer second) {
        return getTimeOfCurrentDay(hour, minute, second).getTime() / 1000L;
    }

    /**
     * 获取当天的某一点的时间点
     *
     * @return
     */
    public static Date getTimeOfCurrentDay(Integer hour, Integer minute, Integer second) {
        return getTimeOfDate(new Date(), hour, minute, second);
    }

    public static Date getTimeOfDate(Date date, Integer hour, Integer minute, Integer second) {
        Calendar todayEnd = Calendar.getInstance();
        todayEnd.setTime(date);
        todayEnd.set(Calendar.HOUR_OF_DAY, hour);
        todayEnd.set(Calendar.MINUTE, minute);
        todayEnd.set(Calendar.SECOND, second);
        todayEnd.set(Calendar.MILLISECOND, 0);
        return todayEnd.getTime();
    }

    public static Date getTimeOfDate(Date date, Integer minute, Integer second) {
        Calendar todayEnd = Calendar.getInstance();
        todayEnd.setTime(date);
        todayEnd.set(Calendar.MINUTE, minute);
        todayEnd.set(Calendar.SECOND, second);
        todayEnd.set(Calendar.MILLISECOND, 0);
        return todayEnd.getTime();
    }

    public static String getTimeStringOfDate(Long time) {
        Calendar todayEnd = Calendar.getInstance();
        todayEnd.setTime(new Date(time));
        return todayEnd.get(Calendar.YEAR) + "-" + (todayEnd.get(Calendar.MONTH) + 1) + "-" + todayEnd.get(Calendar.DAY_OF_MONTH);
    }


    public static String getTimeStringOfDateNoYear(Long time) {
        Calendar todayEnd = Calendar.getInstance();
        todayEnd.setTime(new Date(time));
        return (todayEnd.get(Calendar.MONTH) + 1) + "月" + todayEnd.get(Calendar.DAY_OF_MONTH) + "日";
    }

    /**
     * 获取当前时间小时转换为数字
     *
     * @return
     */
    public static Long getCurrentTimeToNumber() {
        SimpleDateFormat format = new SimpleDateFormat(PATTERN_WITH_HOURS);
        return Long.valueOf(format.format(new Date()));
    }


    public static LocalDateTime toLocalDateTime(Date date) {
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        return localDateTime;
    }

    public static Date toDate(LocalDateTime date) {
        ZoneId zone = ZoneId.systemDefault();
        ZonedDateTime zonedDateTime = date.atZone(zone);
        return Date.from(zonedDateTime.toInstant());
    }

    /**
     * 获取给定时间所在天的截止时间转换为数字：格式为 2021070623
     *
     * @param localDateTime
     * @return
     */
    public static Long getEndTimeToNumber(LocalDateTime localDateTime) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(PATTERN_WITH_HOURS);
        String dateStr = dateTimeFormatter.format(localDateTime.withHour(23).withMinute(59).withSecond(59));
        return Long.valueOf(dateStr);
    }

    /**
     * 获取给定时间所在天的开始时间转换为数字：格式为 2021070600
     *
     * @param localDateTime
     * @return
     */
    public static Long getStartTimeToNumber(LocalDateTime localDateTime) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(PATTERN_WITH_HOURS);
        String dateStr = dateTimeFormatter.format(localDateTime.withHour(0).withMinute(0).withSecond(0));
        return Long.valueOf(dateStr);
    }


    /**
     * 获取给定时间所在天的截止时间转换为数字：格式为 2021070623
     *
     * @param localDateTime
     * @return
     */
    public static String formatDate(LocalDateTime localDateTime, String pattern) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        return dateTimeFormatter.format(localDateTime);
    }

    /**
     * 获取当年第一天0点时间戳
     *
     * @Author: Bi Xiang Ting
     * @Date: 2021/6/5 2:40 下午
     * @Description:
     * @Param: []
     * @return: java.lang.Long
     */

    public static Long yearTimeInMillis() {
        Calendar calendar = Calendar.getInstance();// 获取当前日期
        calendar.add(Calendar.YEAR, 0);
        calendar.add(Calendar.DATE, 0);
        calendar.add(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_YEAR, 1);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Long time = calendar.getTimeInMillis() / 1000;
        return time;
    }

    public static void main(String[] args) throws ParseException {
        Date current = new Date();
        Date date = plusTimeMinute(current, -700);
        System.out.println(date);
        System.out.println(date.before(current));
        System.out.println(getCurrentYear());

        Date date1 = string2date("2022-06-28 14:12:00");
        Date date2 = plusTimeDay(date1, 4);
        System.out.println(date2String(date2));
    }

    public static long getLastSecond() {
        LocalDateTime midnight = LocalDateTime.now().plusDays(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
        long seconds = ChronoUnit.SECONDS.between(LocalDateTime.now(), midnight);
        return seconds;
    }

    public static Date dateTimeToDate(LocalDateTime dateTime) {
        Date date = Date.from(dateTime.toInstant(ZoneOffset.of("+8")));
        return date;
    }


    public static LocalDateTime dateToDateTime(Date date) {
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zone);
    }

    public static Date getStartTimeOfDate(Date date) {
        LocalDateTime localDateTime = dateToDateTime(date).withNano(0).withHour(0).withMinute(0).withSecond(0);
        return dateTimeToDate(localDateTime);
    }

    public static Date getEndTimeOfDate(Date date) {
        LocalDateTime localDateTime = dateToDateTime(date).withNano(999_999_999).withHour(23).withMinute(59).withSecond(59);
        return dateTimeToDate(localDateTime);
    }

    public static LocalDateTime getOneDayStartTimeOf(LocalDateTime localDateTime) {
        return localDateTime.withNano(0).withHour(0).withMinute(0).withSecond(0);
    }

    public static LocalDateTime getOneDayEndTimeOf(LocalDateTime localDateTime) {
        return localDateTime.withNano(9999).withHour(23).withMinute(59).withSecond(59);
    }

    public static int getHour(Date date) {
        if (date == null) {
            throw new IllegalArgumentException();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    public static long getHourTime(Date date) {
        if (date == null) {
            date = new Date();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTimeInMillis();
    }

    public static Date plusTimeMinute(Date time, int minutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        calendar.add(Calendar.MINUTE, minutes);
        return calendar.getTime();
    }

    public static Date plusTimeHour(Date time, int hour) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        calendar.add(Calendar.HOUR_OF_DAY, hour);
        return calendar.getTime();
    }

    public static Date plusTimeDay(Date time, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        calendar.add(Calendar.DAY_OF_MONTH, day);
        return calendar.getTime();
    }

    public static String dayName(Date time) {
        String timeStr = date2String(PATTERN_NO_HOUR, time);
        Date nowDate = new Date();
        String nowDateStr = date2String(PATTERN_NO_HOUR, nowDate);
        String tomorrowDateStr = date2String(PATTERN_NO_HOUR, previousDayOf(nowDate, -1));
        String theDayAfterTomorrowDateStr = date2String(PATTERN_NO_HOUR, previousDayOf(nowDate, -2));
        if (nowDateStr.equals(timeStr)) {
            return "今天";
        } else if (tomorrowDateStr.equals(timeStr)) {
            return "明天";
        } else if (theDayAfterTomorrowDateStr.equals(timeStr)) {
            return "后天";
        }
        return "";
    }

    public static int getCurrentYear() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR);
    }

    public static boolean dateIsToday(Date date) {
        String today = date2String(PATTERN_NO_HOUR_NO_MINUS, new Date());
        String data = date2String(PATTERN_NO_HOUR_NO_MINUS, date);
        return today.equals(data);
    }
}
