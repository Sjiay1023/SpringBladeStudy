package org.sjiay.demo.controller;

import org.apache.commons.lang3.StringUtils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * {@code DateUtil}主要用于处理日期相关的操作：获取日期，日期格式转换，日期格式校验等工作。
 *
 * <p>时间转换及获取主要用到了{@link SimpleDateFormat}的{@code format}和{@code parse}方法、{@link Calendar}的{@code getTime}方法、
 * {@link Timestamp}的{@code valueOf}方法。</p>
 *
 * <p>时间校验主要是比较原字符串的内容跟原字符串按指定格式转换后的内容是否相等，相等即合法。</p>
 *
 * @author luoxl
 * @since util 0.0.1
 */
@SuppressWarnings("all")
public class DateUtils {

    public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    public static final String YYYYMMDD = "yyyyMMdd";

    public static final String YYYYMM = "yyyyMM";

    /** 时间格式化pattern，日期横杠拆分，时间冒号拆分 */
    public static final String DAY_CROSS_TIME_COLON = "yyyy-MM-dd HH:mm:ss";

    /**
     * description: 比较当前时间和指定时间相差的天数 <br>
     * author: yangshanjun <br>
     * @param userBeginDate
     * @return int
     */
    public static int getDifDays(Date userBeginDate) {
        if(userBeginDate == null){
            return 0;
        }
        Calendar current = Calendar.getInstance();
        current.set(Calendar.HOUR_OF_DAY, 23);
        current.set(Calendar.MINUTE, 59);
        current.set(Calendar.SECOND, 59);
        Calendar begin = Calendar.getInstance();
        begin.setTime(userBeginDate);
        begin.set(Calendar.HOUR_OF_DAY, 0);
        begin.set(Calendar.MINUTE, 0);
        begin.set(Calendar.SECOND, 0);
        Long diffMillSeconds = current.getTime().getTime() - begin.getTime().getTime();
        return  (int) Math.ceil(diffMillSeconds * 1.0 /(1000*60*60*24));
    }


    /**
     * 时间转换,将时间转换为参数pattern指定的格式
     * @param date 时间
     * @param pattern 时间格式，如：yyyy-MM-dd HH:mm:ss
     * @return 转换后的时间字符串
     */
    public static String formatDate(Date date, String pattern) {
        if (date == null) {
            return "";
        }
        if(StringUtils.isBlank(pattern)){
            pattern=YYYYMMDDHHMMSS;
        }
        SimpleDateFormat myFormat = new SimpleDateFormat(pattern);
        return myFormat.format(date);
    }

    /**
     * 时间转换,将时间转换为参数pattern指定的格式
     * @param time 时间
     * @param pattern 时间格式，如：yyyy-MM-dd HH:mm:ss
     * @return 转换后的时间字符串
     */
    public static String formatDate(Timestamp time, String pattern) {
        if (time == null) {
            return "";
        }
        SimpleDateFormat myFormat = new SimpleDateFormat(pattern);
        return myFormat.format(time);
    }

    /**
     * 以"yyyy-MM-dd HH:mm:ss.0"格式获取系统当前时间（秒）
     *
     * @return <code>Timestamp</code>当前系统时间
     */
    public static Timestamp getTime() {
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        String mystrdate = myFormat.format(calendar.getTime());
        return Timestamp.valueOf(mystrdate);
    }

    /**
     * 获取当天最早的时间(时间yyyy-MM-dd 00:00:00)
     *
     * @return <code>Timestamp</code>当天最早的时间(时间yyyy-MM-dd 00:00:00)
     */
    public static Timestamp getDateFirst() {
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        Calendar calendar = Calendar.getInstance();
        String mystrdate = myFormat.format(calendar.getTime());
        return Timestamp.valueOf(mystrdate);
    }

    /**
     * 获取当天最晚时间(时间23:59:59)
     *
     * @return <code>Timestamp</code>当天最晚时间 (时间yyyy-MM-dd 23:59:59)
     */
    public static Timestamp getDateLast() {
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
        Calendar calendar = Calendar.getInstance();
        String mystrdate = myFormat.format(calendar.getTime());
        return Timestamp.valueOf(mystrdate);
    }

    /**
     * 获取当前时间,格式如Tue Sep 13 17:31:44 CST 2016
     *
     * @return <code>Date</code>获取当前时间
     */
    public static Date getDate() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime();
    }

    /**
     * 取得当前时间的年月日("yyyyMMdd")
     *
     * @return 当前时间的年月日("yyyyMMdd")字符串
     */
    public static String getCurrDate() {
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance();
        String mystrdate = myFormat.format(calendar.getTime());
        return mystrdate;
    }

    /**
     * 格式必须为yyyy-[m]m-[d]d hh:mm:ss[.f...]时间字符串转换成Timestamp
     *
     * @param timeString
     * @return <code>Timestamp</code>时间戳
     */
    public static Timestamp getTime(String timeString) {
        return Timestamp.valueOf(timeString);
    }

    /**
     * 自定义格式的时间字符串转换成格式为（yyyy-MM-dd HH:mm:ss）的时间戳
     *
     * @param timeString
     *            时间字符串
     * @param fmt
     *            与时间字符串相匹配的格式
     * @return <code>Timestamp</code>时间戳
     * @throws ParseException
     */
    public static Timestamp getTime(String timeString, String fmt) throws ParseException {
        SimpleDateFormat myFormat = new SimpleDateFormat(fmt);
        Date date = myFormat.parse(timeString);
        myFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return getTime(myFormat.format(date));
    }

    /**
     * 输入指定格式(yyyy-MM-dd HH:mm:ss或者yyyy-MM-dd)时间字符串,返回时间戳
     * <p>
     * 如果输入格式为yyyy-MM-dd的时间字符串,则返回格式yyyy-MM-dd 00:00:00的时间戳
     *
     * @param timeString
     * @return <code>Timestamp</code>时间戳
     * @throws ParseException
     */
    public static Timestamp getDateFirst(String timeString) throws ParseException {
        if(StringUtils.isBlank(timeString)){
            return null;
        }
        if (timeString.length() > 10) {
            return getTime(timeString, "yyyy-MM-dd HH:mm:ss");
        } else {
            return getTime(timeString, "yyyy-MM-dd");
        }
    }

    /**
     * 输入指定格式(yyyy-MM-dd HH:mm:ss或者yyyy-MM-dd)时间字符串,返回时间戳
     * <p>
     * 如果输入格式为yyyy-MM-dd的时间字符串,则返回格式yyyy-MM-dd 23:59:59的时间戳
     *
     * @param timeString
     *            格式为(yyyy-MM-dd HH:mm:ss或者yyyy-MM-dd)时间字符串
     * @return Timestamp
     * @throws ParseException
     */
    public static Timestamp getDateLast(String timeString) throws ParseException {
        if(StringUtils.isBlank(timeString)){
            return null;
        }
        if (timeString.length() > 10) {
            return getTime(timeString, "yyyy-MM-dd HH:mm:ss");
        } else {
            return getTime(timeString + " 23:59:59", "yyyy-MM-dd HH:mm:ss");
        }
    }

    /**
     * 周日为一周的起始日
     * <p>
     * 获取本周周一时间，返回 格式yyyy-MM-dd 00:00:00
     *
     * @return Timestamp
     */
    public static Timestamp getMonday() {
        Calendar calendar = Calendar.getInstance();
        int dayofweek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        calendar.add(Calendar.DATE, -dayofweek + 1);
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        String mystrdate = myFormat.format(calendar.getTime());
        return Timestamp.valueOf(mystrdate);
    }

    /**
     * 周日为一周的起始日
     * <p>
     * 获取本周 周日 时间，返回格式yyyy-MM-dd 23:59:59
     *
     * @author cmt
     * @return Timestamp
     */
    public static Timestamp getSunday() {
        Calendar calendar = Calendar.getInstance();
        int dayofweek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        calendar.add(Calendar.DATE, -dayofweek);
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
        String mystrdate = myFormat.format(calendar.getTime());
        return Timestamp.valueOf(mystrdate);
    }

    /**
     * 字符串时间格式转换
     * @param date
     * @param fromFormat
     * @param toFormat
     * @param handleException 是否捕获转换异常
     * @return
     */
    public static String convertFormat(String  date,String fromFormat, String toFormat,boolean handleException) {
        try {
            SimpleDateFormat fdf = new SimpleDateFormat(fromFormat);
            Date d = fdf.parse(date);
            SimpleDateFormat tdf = new SimpleDateFormat(toFormat);
            return tdf.format(d);
        } catch (ParseException e) {
            if (handleException) {
                return null;
            }
            throw new RuntimeException(e);
        }
    }

    /**
     * 给指定的时间增加指定的天数
     *
     * @author cmt
     * @param time
     * @param day
     * @return
     */
    public static Timestamp addDay(Timestamp time, Integer day) {
        Timestamp time2 = new Timestamp(time.getTime() + day * 1000L * 60 * 60 * 24L);
        return time2;
    }

    /**
     * 2个时间的相差天数
     *
     * @author cmt
     * @param time1
     * @param time2
     * @return 相差天数
     */
    public static Integer getDay(Timestamp time1, Timestamp time2) {
        Long dayTime = (time1.getTime() - time2.getTime()) / (1000 * 60 * 60 * 24);
        return dayTime.intValue();
    }

    /**
     * 两个日期相减 格式 yyyyMMdd
     *
     * @param oldDate
     * @param newDate
     * @return 相差的天数
     */
    public static long getsubDate(String oldDate, String newDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date d1 = null;
        Date d2 = null;
        try {
            d1 = sdf.parse(oldDate);
            d2 = sdf.parse(newDate);
        } catch (ParseException e) {
            throw new RuntimeException("日期处理出错");
        }

        return (d1.getTime() - d2.getTime()) / (3600L * 1000 * 24);
    }

    /**
     * 两个日期相减 格式 yyyyMMdd
     *
     * @param oldDate
     * @param newDate
     * @return 相差的天数
     */
    public static long getsubDate(Date oldDate, Date newDate) {
        return (oldDate.getTime() - newDate.getTime()) / (3600L * 1000 * 24);
    }

    /**
     * 获取系统当前时间（分）
     *
     * @author cmt
     * @return
     */
    public static String getMinute() {
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyyMMddHHmm");
        return myFormat.format(new Date());
    }

    /**
     * 转换成时间 字符串格式必须为 yyyy-MM-dd HH:mm:ss 或 yyyy-MM-dd
     *
     * @author cmt
     * @return Date
     * @throws ParseException
     */
    public static Date parseToDateyyyyMMddHHmmss(String val) throws ParseException {
        Date date = null;
        Locale.setDefault(Locale.SIMPLIFIED_CHINESE);
        if (null != val && val.trim().length() != 0 && !"null".equals(val.trim().toLowerCase())) {
            val = val.trim();
            if (val.length() > 10) {
                SimpleDateFormat sdf = new SimpleDateFormat(YYYYMMDDHHMMSS);
                date = sdf.parse(val);
            }
            if (val.length() <= 10) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                date = sdf.parse(val);
            }
        }
        return date;
    }

    public static Date str2DateyyyyMMddHHmmss(String val)  {
        Date date = null;
        Locale.setDefault(Locale.SIMPLIFIED_CHINESE);
        if (null != val && val.trim().length() != 0 && !"null".equals(val.trim().toLowerCase())) {
            val = val.trim();
            if (val.length() > 10) {
                SimpleDateFormat sdf = new SimpleDateFormat(YYYYMMDDHHMMSS);
                try {
                    date = sdf.parse(val);
                } catch (ParseException e) {
                    date = new Date();
                }
            }
            if (val.length() <= 10) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    date = sdf.parse(val);
                } catch (ParseException e) {
                    date= new Date();
                }
            }
        }
        return date;
    }

    /**
     * 转换成时间 字符串格式必须为 yyyy-MM-dd HH:mm:ss 或 yyyy-MM-dd
     *
     * @author cmt
     * @return Date
     * @throws ParseException
     */
    public static Date parseToDate(String val) throws ParseException {
        Date date = null;
        if (null != val && val.trim().length() != 0 && !"null".equals(val.trim().toLowerCase())) {
            val = val.trim();
            if (val.length() > 10) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                date = sdf.parse(val);
            }
            if (val.length() <= 10) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                date = sdf.parse(val);
            }
        }
        return date;
    }

    /**
     * 将时间字符串转换成指定格式的Date
     *
     * @param dateStr
     *
     * @param format
     *
     * @return Date
     * @throws ParseException
     */
    public static Date parseToDate(String dateStr, String format) throws ParseException {
        Date date = null;
        if (StringUtils.isNotBlank(dateStr) && !"null".equals(dateStr.trim().toLowerCase())) {
            dateStr = dateStr.trim();
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            date = sdf.parse(dateStr);
        }
        return date;
    }

    public static Date parseToDateWithOutThrow(String dateStr, String format) {
        Date date = null;
        if (null != dateStr && dateStr.trim().length() != 0 && !"null".equals(dateStr.trim().toLowerCase())) {
            dateStr = dateStr.trim();
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            try {
                date = sdf.parse(dateStr);
            } catch (ParseException e) {
                date = new Date();
            }
        }
        return date;
    }

    /**
     * 获取上月的第一天yyyy-MM-dd 00:00:00和最后一天yyyy-MM-dd 23:59:59
     *
     * @author cmt
     * @return
     */
    @SuppressWarnings("static-access")
    public static Map<String, String> getPreMonth() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        Calendar cal = Calendar.getInstance();
        GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        calendar.add(Calendar.MONTH, -1);
        Date theDate = calendar.getTime();
        gcLast.setTime(theDate);
        gcLast.set(Calendar.DAY_OF_MONTH, 1);
        String day_first_prevM = df.format(gcLast.getTime());
        StringBuilder str = new StringBuilder().append(day_first_prevM).append(" 00:00:00");
        day_first_prevM = str.toString(); // 上月第一天

        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DATE, 1);
        calendar.add(Calendar.DATE, -1);
        String day_end_prevM = df.format(calendar.getTime());
        StringBuilder endStr = new StringBuilder().append(day_end_prevM).append(" 23:59:59");
        day_end_prevM = endStr.toString(); // 上月最后一天

        Map<String, String> map = new HashMap<String, String>();
        map.put("prevMonthFD", day_first_prevM);
        map.put("prevMonthPD", day_end_prevM);
        return map;
    }

    /**
     * 获取上周 周一时间，返回 格式yyyy-MM-dd 00:00:00
     *
     * @author cmt
     * @return
     */
    @SuppressWarnings("static-access")
    public static String getPreMonthStr() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);

        SimpleDateFormat myFormat = new SimpleDateFormat(YYYYMM);
        return myFormat.format(calendar.getTime());
    }

    /**
     * 获取上周 周一时间，返回 格式yyyy-MM-dd 00:00:00
     *
     * @author cmt
     * @return
     */
    @SuppressWarnings("static-access")
    public static Timestamp getPreMonday() {
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        calendar.add(Calendar.WEEK_OF_MONTH, -1);

        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        String mystrdate = myFormat.format(calendar.getTime());
        return Timestamp.valueOf(mystrdate);
    }

    /**
     * 获取上周 周日时间，返回 格式yyyy-MM-dd 23:59:59
     *
     * @author cmt
     * @return
     */
    @SuppressWarnings("static-access")
    public static Timestamp getPreSunday() {
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        calendar.add(Calendar.WEEK_OF_MONTH, -1);

        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
        String mystrdate = myFormat.format(calendar.getTime());
        return Timestamp.valueOf(mystrdate);
    }

    /**
     * 获取每月的开始时间，如：2020/07/01 00:00:00
     */
    public static Timestamp getMonthStart() {
        Calendar cale = Calendar.getInstance();
        cale.add(Calendar.MONTH, 0);
        cale.set(Calendar.DAY_OF_MONTH, 1);

        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        String mystrdate = myFormat.format(cale.getTime());
        return Timestamp.valueOf(mystrdate);
    }

    /**
     * 获取每月的最后时间 如 2020/07/31 23:59:59
     */
    public static Timestamp getMonthEnd() {
        Calendar cale = Calendar.getInstance();
        cale.set(Calendar.DAY_OF_MONTH, cale.getActualMaximum(Calendar.DAY_OF_MONTH));

        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
        String mystrdate = myFormat.format(cale.getTime());
        return Timestamp.valueOf(mystrdate);
    }

    /**
     * 获得格式为yyyyMMddHHmmssSSS时间字符串
     *
     * @return String
     */
    public static String getDateyyyyMMddHHmmssSSSStr() {
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        Calendar calendar = Calendar.getInstance();
        return myFormat.format(calendar.getTime());
    }

    /**
     * 获得格式为yyyyMMddHHmmss时间字符串
     *
     * @return String
     */
    public static String getDateyyyyMMddHHmmss() {
        SimpleDateFormat myFormat = new SimpleDateFormat(YYYYMMDDHHMMSS);
        Calendar calendar = Calendar.getInstance();
        return myFormat.format(calendar.getTime());
    }

    /**
     * 获得格式为yyyyMM时间字符串
     *
     * @return String
     */
    public static String getDateyyyyMM() {
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyyMM");
        Calendar calendar = Calendar.getInstance();
        return myFormat.format(calendar.getTime());
    }

    /**
     * 获得格式为yyyyMMdd时间字符串
     *
     * @return String
     */
    public static String getDateyyyyMMdd() {
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance();
        return myFormat.format(calendar.getTime());
    }

    /**
     * 获取t天前日期，格式yyyyMM
     *
     * @return
     */
    public static String getDateyyyyMMdd(int t) {
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_YEAR);
        calendar.set(Calendar.DAY_OF_YEAR, day - t);
        return myFormat.format(calendar.getTime());
    }

    /**
     * 获取当前月份，格式yyyyMM
     *
     * @return
     */
    public static String getMonth() {
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyyMM");
        return myFormat.format(getDate());
    }

    /**
     * 取当前时间 格式为 yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String getDateNow() {
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        return myFormat.format(calendar.getTime());
    }

    /**
     * 指定格式进行时间校验
     *
     * @param pattern 格式
     * @param date 具体时间
     * @return boolean true表示具体的时间与时间格式相符,false则相反
     */
    public static boolean validateDate(String pattern, String date) {
        if (StringUtils.isBlank(date)) {
            return false;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            Date time = sdf.parse(date);
            String newValue = sdf.format(time);
            return date.equals(newValue);
        } catch (ParseException e) {
            return false;
        }
    }

    /**
     * 校验给定的时间字符串与格式yyyyMMddHHmmss是否相符
     *
     * @param date
     *
     * @return boolean
     */
    public static boolean validateyMdHms(String date) {
        return validateDate(YYYYMMDDHHMMSS, date);
    }

    /**
     * 校验给定的时间字符串与格式yyyy-MM-dd HH:mm:ss是否相符
     *
     * @param date
     * @return boolean
     */
    public static boolean validateyMdHmsWithSymbol(String date) {
        return validateDate("yyyy-MM-dd HH:mm:ss", date);
    }

    /**
     * 校验给定的时间字符串与格式yyyy-MM-dd是否相符
     *
     * @param date
     * @return boolean
     */
    public static boolean validateyMdWithSymbol(String date) {
        return validateDate("yyyy-MM-dd", date);
    }

    /**
     *校验给定的时间字符串与格式yyyyMMdd是否相符
     *
     * @param date
     * @return boolean
     */
    public static boolean validateyMd(String date) {
        return validateDate("yyyyMMdd", date);
    }

    public static boolean validateyyyyMM(String date) {
        return validateDate("yyyyMM", date);
    }

    /**
     * 获得格式为yyyyMMddHH时间字符串
     * @return
     */
    public static String getDateyyyyMMddHH() {
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyyMMddHH");
        Calendar calendar = Calendar.getInstance();
        return myFormat.format(calendar.getTime());
    }

    /**
     * 获得格式为yyyyMMddHHmm时间字符串
     * @return
     */
    public static String getDateyyyyMMddHHmm() {
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyyMMddHHmm");
        Calendar calendar = Calendar.getInstance();
        return myFormat.format(calendar.getTime());
    }

    /**
     * description: 按照特定格式转换指定字符串 <br>
     * author: yangshanjun <br>
     * @param dateStr
     * @param format
     * @return java.util.Date
     */
    public static Date parseStr(String dateStr, String format) {
        try{
            SimpleDateFormat sdf = new SimpleDateFormat();
            sdf.applyPattern(format);
            return sdf.parse(dateStr);
        } catch (Exception e){
            e.printStackTrace();
            throw new IllegalArgumentException(String.format("时间戳格式应为%s", format));
        }
    }

    /**
     * description: 获取yyyyMMdd日期格式的开始时间 <br>
     * author: yangshanjun <br>
     * @param dateStr
     * @return java.util.Date
     */
    public static Date getBeginTime(String dateStr) {
        Date date = null;
        try{
            SimpleDateFormat sdf = new SimpleDateFormat();
            sdf.applyPattern("yyyyMMdd");
            date = sdf.parse(dateStr);
        } catch (Exception e){
            e.printStackTrace();
            throw new IllegalArgumentException("时间戳格式应为yyyyMMdd");
        }
        return date;
    }

    /**
     * description: 获取yyyyMMdd日期格式的结束时间 <br>
     * author: yangshanjun <br>
     * @param dateStr
     * @return java.util.Date
     */
    public static Date getEndTime(String dateStr)  {
        Date date = null;
        try{
            SimpleDateFormat sdf = new SimpleDateFormat();
            sdf.applyPattern("yyyyMMdd");
            Date tmp = sdf.parse(dateStr);
            date = new Date((tmp.getTime() + 24 * 60 * 60 * 1000) -1);
        } catch (Exception e){
            e.printStackTrace();
            throw new IllegalArgumentException("时间戳格式应为yyyyMMdd");
        }
        return date;
    }

    /**
     * description: 对于指定日期，增加或减少指定天数<br>
     * author: yangshanjun <br>
     * @param dateStr yyyyMMdd日期格式
     * @param increment 天数，正数加，负数减
     * @return java.lang.String
     */
    public static String addDate(String dateStr, int increment) {
        String ret = null;
        try{
            SimpleDateFormat sdf = new SimpleDateFormat();
            sdf.applyPattern("yyyyMMdd");
            Date date = sdf.parse(dateStr);
            Date yesterday = new Date(date.getTime() + increment * 24 * 60 * 60 * 1000);
            ret = sdf.format(yesterday);
        } catch (Exception e){
            e.printStackTrace();
            throw new IllegalArgumentException("时间戳格式应为yyyyMMdd");
        }
        return ret;
    }

    /**
     * 使用DateTimeFormatter转换Date为String
     *
     * @param date {@link Date}
     * @param pattern 日期格式
     * @return 格式化的日期
     */
    public static String formatDateByDTF(Date date, String pattern)
    {

        DateTimeFormatter df = DateTimeFormatter.ofPattern(pattern);
        LocalDateTime ldt = date.toInstant()
                                .atZone(ZoneId.systemDefault())
                                .toLocalDateTime();
        return df.format(ldt);
    }

    public static Date addSecs(Date date, int delta){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int cur = calendar.get(Calendar.SECOND);
        calendar.set(Calendar.SECOND, cur + delta);
        return calendar.getTime();
    }

    public static String getYyyymmdd(Integer year, Integer month, Integer date){
        StringBuilder builder = new StringBuilder();
        builder.append(year);
        if(month < 10){
            builder.append("0").append(month);
        } else{
            builder.append(month);
        }
        if(date != null){
            if(date < 10){
                builder.append("0");
            }
            builder.append(date);
        } else{
            return builder.toString();
        }
        String dateStr = builder.toString();
        boolean valid = validateDate(DateUtils.YYYYMMDD, dateStr);
        return valid?dateStr:DateUtils.getCurrDate();
    }
    /**
     *
     * @param nowTime   当前时间
     * @param startTime	开始时间
     * @param endTime   结束时间
     * @return
     * @author sunran   判断当前时间在时间区间内
     */
    public static boolean isEffectiveDate(Date nowTime, Date startTime, Date endTime) {
        if (nowTime.getTime() == startTime.getTime()
                || nowTime.getTime() == endTime.getTime()) {
            return true;
        }

        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(startTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        if (date.after(begin) && date.before(end)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取当月最后i天
     * @param days
     * @return
     */
    public static List<String> getLastDays(int days) {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance();
        List<String> list=new ArrayList<String>();
        for(int i=1;i<=days;i++){
            calendar.add(Calendar.MONTH, 1);
            calendar.set(Calendar.DATE, 1);
            calendar.add(Calendar.DATE, -i);
            Date theDate = calendar.getTime();
            String s = df.format(theDate);
            list.add(s);
        }
        return list;
    }

    /**
     * 计算两个日期相差的秒数
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static int calLastedTime(Date startDate, Date endDate) {
        long a = endDate.getTime();
        long b = startDate.getTime();
        int c = (int) ((a - b) / 1000);
        return c;
    }
}
