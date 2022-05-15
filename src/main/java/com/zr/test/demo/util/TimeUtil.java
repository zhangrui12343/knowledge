package com.zr.test.demo.util;

import com.zr.test.demo.component.exception.CustomException;
import com.zr.test.demo.config.enums.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * 时间工具
 *
 * @author huang_kangjie
 * @date 2020/3/20 0020 17:46
 */
@Slf4j
public class TimeUtil {

     private static final Logger logger = LoggerFactory.getLogger(TimeUtil.class);

     /** 默认时间长度为20 */
     public static final int TIME_LENGTH = 19;

     /**
      * 时间格式 HHmmss
      */
     public static final String HHMMSS = "HHmmss";
     /**
      * 时间格式 YYYY-MM-DD
      */
     public static final String YYYY_MM_DD = "yyyy-MM-dd";

     /**
      * 时间格式 yyyyMMddHHmmss
      */
     public static final String yyyyMMddHHmmss = "yyyy-MM-dd HH:mm:ss";
     /**
      * 获取当前时间 yyyy-MM-dd HH:mm:ss
      *
      * @return yyyy-MM-dd HH:mm:ss
      */
     public static String getTime() {
          SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          return sdf.format(new Date());
     }

     /**
      * 获取 x 天 之前的时间
      * @param days 多少天
      * @return yyyy-MM-dd HH:mm:ss
      */
     public static String getTimeBefore(int days){
          LocalDateTime now = LocalDateTime.now();
          now = now.minus(days, ChronoUnit.DAYS);
          SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          Date date = Date.from( now.atZone( ZoneId.systemDefault()).toInstant());
          return sdf.format(date);
     }

     /**
      * 获取当前时间 date
      *
      * @return yyyy-MM-dd HH:mm:ss
      */
     public static String getTime(Date date) {
          if(date==null){
               return null;
          }
          SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          return sdf.format(date);
     }

     /**
      * 获取当前时间 date
      *
      * @return yyyy-MM-dd
      */
     public static String getDateStr(Date date) {
          SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
          return sdf.format(date);
     }

     /**
      * 根据字符串获取日期
      * @param time yyyy-MM-dd HH:mm:ss
      * @return Date
      */
     public static Date getDate(String time) {
         try {
              SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
              return sdf.parse(time);
         } catch (Exception e) {
              log.error("getDate(String time) error! time = {}", time);
              log.error("getDate(String time) error : " + e.getMessage(), e);
              throw new CustomException(ErrorCode.SYS_PARAM_INNER_ERR, "时间格式出错");
         }
     }
     /**
      * 根据字符串获取日期
      * @param time
      * @return Date
      */
     public static Date getShortDateToDate(Date time) {
          try {
               SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
               return sdf.parse(sdf.format(time));
          } catch (Exception e) {
               log.error("getShortDateToDate(Date time) error! time = {}", time);
               log.error("getShortDateToDate(Date time) error : " + e.getMessage(), e);
               throw new CustomException(ErrorCode.SYS_PARAM_INNER_ERR, "时间格式出错");
          }
     }
     /**
      * 根据字符串获取日期
      * @param time yyyy-MM-dd HH:mm:ss
      * @return Date
      */
     public static Date getDate(String time,String format) {
          try {
               SimpleDateFormat sdf = new SimpleDateFormat(format);
               return sdf.parse(time);
          } catch (Exception e) {
               log.error("getDate(String time,String format) error! time = {},format = {}", time,format);
               log.error("getDate(String time,String format) error : " + e.getMessage(), e);
               throw new CustomException(ErrorCode.SYS_PARAM_INNER_ERR, "时间格式出错");
          }
     }
     /**
      * 传入时间，获取该时间的 开始时间
      * @param time yyyy-MM-dd 或者 yyyy-MM-dd HH:mm:ss
      * @return yyyy-MM-dd 00:00:00
      */
     public static String getDayStart(String time){
          return getDateStrShort(time) + " 00:00:00";
     }
     /**
      * 传入时间，获取该时间的 开始时间
      * @param date yyyy-MM-dd 或者 yyyy-MM-dd HH:mm:ss
      * @return yyyy-MM-dd 23:59:59
      */
     public static String getDayStart(Date date){
          return getDateStr(date) + " 00:00:00";
     }
     /**
      * 传入时间，获取该时间的 开始时间
      * @param time yyyy-MM-dd 或者 yyyy-MM-dd HH:mm:ss
      * @return yyyy-MM-dd 23:59:59
      */
     public static String getDayEnd(String time){
          return getDateStrShort(time) + " 23:59:59";
     }

     /**
      * 传入时间，获取该时间的 开始时间
      * @param date yyyy-MM-dd 或者 yyyy-MM-dd HH:mm:ss
      * @return yyyy-MM-dd 23:59:59
      */
     public static String getDayEnd(Date date){
          return getDateStr(date) + " 23:59:59";
     }

     /**
      * 根据字符串获取日期
      * 解析带T的时间，主要是C++为了兼容C#的时间解析，每次在传递时间的时候都带了T
      * @param time yyyy-MM-ddTHH:mm:ss
      * @return Date
      */
     public static Date getDateT(String time) {
         try {
              SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
              return sdf.parse(time);
         } catch (Exception e) {
              log.error("getDateT(String time) error! time = {}", time);
              log.error("getDateT(String time) error : " + e.getMessage(), e);
              return null;
         }
     }

     /**
      * 根据字符串获取日期
      * @param time yyyy-MM-dd
      * @return Date
      */
     public static Date getDateShort(String time) {
         try {
              SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
              return sdf.parse(time);
         } catch (Exception e) {
              log.error("getDateShort(String time) error! time = {}", time);
              log.error("getDateShort(String time) error : " + e.getMessage(), e);
              return new Date();
         }
     }

     /**
      * 根据字符串获取日期
      * @return today yyyy-MM-dd
      */
     public static String getToday() {
         try {
              SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
              return sdf.format(new Date());
         } catch (Exception e) {
              log.error("getToday() error : " + e.getMessage(), e);
              return "";
         }
     }


     /**
      * 获取月份
      *
      * @return 月份
      */
     public static int getMonth() {
          Calendar cal = Calendar.getInstance();
          return cal.get(Calendar.MONTH) + 1;
     }
     /**
      * 获取年月202202
      *
      * @return 年月
      */
     public static String getYearMonth() {
          Calendar cal = Calendar.getInstance();
          return String.valueOf(cal.get(Calendar.YEAR))+(cal.get(Calendar.MONTH) + 1);
     }
     /**
      * 获取年份
      *
      * @return 年
      */
     public static int getYear() {
          Calendar cal = Calendar.getInstance();
          return cal.get(Calendar.YEAR);
     }

     /**
      * 本地时间转utc时间字符串
      *
      * @param localTime 本地时间 yyyy-MM-dd HH:mm:ss
      * @return 返回utc时间字符串yyyy-MM-dd HH:mm:ss
      */
     public static String localToUtcStr(String localTime) {
          SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          return sdf.format(localToUtc(localTime));
     }

     /**
      * 本地时间转utc时间字符串
      *
      * @param utcTime 本地时间 yyyy-MM-dd HH:mm:ss
      * @return 返回utc时间字符串yyyy-MM-dd HH:mm:ss
      */
     public static String utcToLocalStr(String utcTime) {
          SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          return sdf.format(utcToLocal(utcTime));
     }

     /**
      * local时间转换成UTC时间
      *
      * @param localTime 本地时间
      * @return utc时间
      */
     public static Date localToUtc(String localTime) {
          SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          Date localDate;
          try {
               localDate = sdf.parse(localTime);
          } catch (ParseException e) {
               logger.error("本地时间转utc出错: " + e.getMessage(), e);
               throw new CustomException(ErrorCode.SYS_LOCAL_TO_UTC_ERR);
          }
          long localTimeInMillis = localDate.getTime();
          //long时间转换成Calendar
          Calendar calendar = Calendar.getInstance();
          calendar.setTimeInMillis(localTimeInMillis);
          //取得时间偏移量
          int zoneOffset = calendar.get(Calendar.ZONE_OFFSET);
          //取得夏令时差
          int dstOffset = calendar.get(Calendar.DST_OFFSET);
          //从本地时间里扣除这些差量，即可以取得UTC时间
          calendar.add(Calendar.MILLISECOND, -(zoneOffset + dstOffset));
          //取得的时间就是UTC标准时间
          return new Date(calendar.getTimeInMillis());
     }

     /**
      * utc时间转成local时间
      *
      * @param utcTime utc时间
      * @return 本地时间
      */
     public static Date utcToLocal(String utcTime) {
          SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
          Date utcDate;
          try {
               utcDate = sdf.parse(utcTime);
          } catch (ParseException e) {
               logger.error("utc转本地时间出错: " + e.getMessage(), e);
               throw new CustomException(ErrorCode.SYS_UTC_TO_LOCAL_ERR);
          }
          sdf.setTimeZone(TimeZone.getDefault());
          Date locatlDate;
          String localTime = sdf.format(utcDate.getTime());
          try {
               locatlDate = sdf.parse(localTime);
          } catch (ParseException e) {
               logger.error("utc转本地时间出错: " + e.getMessage(), e);
               throw new CustomException(ErrorCode.SYS_UTC_TO_LOCAL_ERR);
          }
          return locatlDate;
     }

     /**
      * 比较两个时间的大小
      *
      * 如果后者结束时间大于等于开始时间返回成功
      * 时间格式 yyyy-MM-dd HH:mm:ss
      *
      * @param startTime 开始时间 yyyy-MM-dd HH:mm:ss
      * @param endTime   结束时间 yyyy-MM-dd HH:mm:ss
      * @return true 结束时间大于开始时间返回成功
      */
     public static boolean compareTo(String startTime, String endTime) {
          try {
               DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
               Date start = format.parse(startTime);
               Date end = format.parse(endTime);
               int compare = start.compareTo(end);
               //-1 大于 0 等于 1小于
               return compare <= 0;
          } catch (Exception e) {
               log.error("比较两个时间大小出错：" + e.getMessage(), e);
          }
          return false;
     }

     /**
      * 比较两个时间的大小 - 后者 大于等于 前者
      * 如果后者结束时间大于开始时间返回成功
      * 时间格式 yyyy-MM-dd
      *
      * @param startTime 开始时间 yyyy-MM-dd
      * @param endTime   结束时间 yyyy-MM-dd
      * @return true 结束时间 大于等于 开始时间返回成功
      */
     public static boolean compareDateGte(String startTime, String endTime) {
          try {
               DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
               Date start = format.parse(startTime);
               Date end = format.parse(endTime);
               int compare = start.compareTo(end);
               //-1 大于 0 等于 1小于
               return compare <= 0;
          } catch (Exception e) {
               log.error("比较两个时间大小出错：" + e.getMessage(), e);
          }
          return false;
     }

     /**
      * 比较两个时间的大小 - 后者 大于 前者
      * 如果后者结束时间大于开始时间返回成功
      * 时间格式 yyyy-MM-dd
      *
      * @param startTime 开始时间 yyyy-MM-dd
      * @param endTime   结束时间 yyyy-MM-dd
      * @return true 结束时间 大于 开始时间返回成功
      */
     public static boolean compareDateGt(String startTime, String endTime) {
          try {
               DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
               Date start = format.parse(startTime);
               Date end = format.parse(endTime);
               int compare = start.compareTo(end);
               //-1 大于 0 等于 1小于
               return compare < 0;
          } catch (Exception e) {
               log.error("比较两个时间大小出错：" + e.getMessage(), e);
          }
          return false;
     }

     /**
      * 比较两个时间的大小 - 后者 小于等于 前者
      * 如果后者结束时间大于开始时间返回成功
      * 时间格式 yyyy-MM-dd
      *
      * @param startTime 开始时间 yyyy-MM-dd
      * @param endTime   结束时间 yyyy-MM-dd
      * @return true 结束时间 小于等于 开始时间返回成功
      */
     public static boolean compareDateLte(String startTime, String endTime) {
          try {
               DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
               Date start = format.parse(startTime);
               Date end = format.parse(endTime);
               int compare = start.compareTo(end);
               //-1 大于 0 等于 1小于
               return compare >= 0;
          } catch (Exception e) {
               log.error("比较两个时间大小出错：" + e.getMessage(), e);
          }
          return false;
     }

     /**
      * 比较两个时间的大小 - 后者 小于 前者
      * 如果后者结束时间大于开始时间返回成功
      * 时间格式 yyyy-MM-dd
      *
      * @param startTime 开始时间 yyyy-MM-dd
      * @param endTime   结束时间 yyyy-MM-dd
      * @return true 结束时间 小于 开始时间返回成功
      */
     public static boolean compareDateLt(String startTime, String endTime) {
          try {
               DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
               Date start = format.parse(startTime);
               Date end = format.parse(endTime);
               int compare = start.compareTo(end);
               //-1 大于 0 等于 1小于
               return compare > 0;
          } catch (Exception e) {
               log.error("比较两个时间大小出错：" + e.getMessage(), e);
          }
          return false;
     }

     /**
      * @param start 开始时间 yyyy-MM
      * @param end   结束时间 yyyy-MM
      * @return 返回所有的月
      */
     public static List<String> getAllMonths(String start, String end) {
          List<String> list = new ArrayList<>();
          String splitSign = "-";
          start = start.substring(0, 7);
          end = end.substring(0, 7);
          //判断YYYY-MM时间格式的正则表达式
          String regex = "\\d{4}" + splitSign + "(([0][1-9])|([1][012]))";
          if (!start.matches(regex) || !end.matches(regex)) {
               return list;
          }
          if (start.compareTo(end) > 0) {
               //start大于end日期时，互换
               String temp = start;
               start = end;
               end = temp;
          }
          //从最小月份开始
          String temp = start;
          while (temp.compareTo(start) >= 0 && temp.compareTo(end) <= 0) {
               //首先加上最小月份,接着计算下一个月份
               list.add(temp);
               String[] arr = temp.split(splitSign);
               int year = Integer.parseInt(arr[0]);
               int month = Integer.parseInt(arr[1]) + 1;
               if (month > 12) {
                    month = 1;
                    year++;
               }
               //补0操作
               if (month < 10) {
                    temp = year + splitSign + "0" + month;
               } else {
                    temp = year + splitSign + month;
               }
          }
          return list;
     }

     /**
      * 获取两个时间之间的天
      *
      * @param start 开始时间格式 yyyy-MM-dd
      * @param ends  结束时间格式 yyyy-MM-dd
      * @return 所有的时间
      */
     public static List<String> getAllDays(String start, String ends) {
          List<String> result = new ArrayList<>();
          Calendar tempStart = Calendar.getInstance();
          SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
          Date begin;
          Date end;
          try {
               begin = fmt.parse(start);
               end = fmt.parse(ends);
          } catch (ParseException e) {
               log.error("获取两个时间之间的天数出错：" + e.getMessage(), e);
               return result;
          }
          tempStart.setTime(begin);
          while (begin.getTime() <= end.getTime()) {
               result.add(fmt.format(tempStart.getTime()));
               tempStart.add(Calendar.DAY_OF_YEAR, 1);
               begin = tempStart.getTime();
          }
          return result;
     }

     /**
      * 获取相同月数的天
      * @param days 天的集合
      * @param like 某一天
      * @return 相同的天
      */
     public static List<String> getAllDaysLike(List<String> days, String like) {
          List<String> rs = new ArrayList<>();
          days.forEach( day -> {
               if(day.startsWith(like)) {
                    rs.add(day);
               }
          });
          return rs;
     }

     /**
      * 获取月的天
      *
      * @param year  某年
      * @param month 某月
      * @return 所有的天
      */
     public static List<String> getMonthDay(String year, String month) {
          List<String> result = new ArrayList<>();
          Calendar tempStart = Calendar.getInstance();
          SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
          Date begin;
          Date end;
          try {
               if (null == year || "".equals(year) || null == month || "".equals(month)) {
                    return result;
               }
               begin = fmt.parse(year + "-" + month + "-" + "01");
               end = fmt.parse(year + "-" + (Integer.parseInt(month) + 1) + "-" + "01");
          } catch (ParseException e) {
               log.error("获取月份的天出错：" + e.getMessage(), e);
               return result;
          }
          tempStart.setTime(begin);
          while (begin.getTime() < end.getTime()) {
               result.add(fmt.format(tempStart.getTime()));
               tempStart.add(Calendar.DAY_OF_YEAR, 1);
               begin = tempStart.getTime();
          }
          return result;

     }
     /*
      *
      *
      */

     /**
      * 获取两个时间之间的天数
      *
      * @param start 开始时间时间格式 yyyy-MM-dd
      * @param ends  结束时间时间格式 yyyy-MM-dd
      * @return 总数
      */
     public static Integer getDaySum(String start, String ends) {
          int result = 0;
          Calendar tempStart = Calendar.getInstance();
          SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
          Date begin;
          Date end;
          try {
               begin = fmt.parse(start);
               end = fmt.parse(ends);
          } catch (ParseException e) {
               log.error("获取两个时间之间的天数出错：" + e.getMessage(), e);
               e.printStackTrace();
               return 0;
          }
          tempStart.setTime(begin);
          while (begin.getTime() < end.getTime()) {
               result += 1;
               tempStart.add(Calendar.DAY_OF_YEAR, 1);
               begin = tempStart.getTime();
          }
          return result;

     }

     /**
      *
      * 计算两个日期相差的月份数
      *
      * @param date1 日期1
      * @param date2 日期2
      * @param pattern 日期1和日期2的日期格式 默认 yyyy-MM-dd HH:mm:ss
      * @return 相差的月份数
      * @throws ParseException 解析异常
      */
     public static int getMonthSpace(String date1, String date2, String pattern) throws ParseException{
          if(StringUtils.isEmpty(pattern)) {
               pattern = "yyyy-MM-dd HH:mm:ss";
          }
          SimpleDateFormat sdf=new SimpleDateFormat(pattern);
          Calendar c1=Calendar.getInstance();
          Calendar c2=Calendar.getInstance();
          c1.setTime(sdf.parse(date1));
          c2.setTime(sdf.parse(date2));
          int year =c2.get(Calendar.YEAR)-c1.get(Calendar.YEAR);
          //开始日期若小月结束日期
          if(year<0){
               year=-year;
               return year*12+c1.get(Calendar.MONTH)-c2.get(Calendar.MONTH);
          }
          return year*12+c2.get(Calendar.MONTH)-c1.get(Calendar.MONTH);
     }

     /**
      * 根据字符串时间获取时间戳
      * @param times yyyy-MM-dd HH:mm:ss
      * @return 精确到毫秒
      */
     public static long getTimestap(String times){
          return TimeUtil.getDate(times).getTime();
     }

     /**
      * 时间戳转时间 yyyy-MM-dd HH:mm:ss
      * @param timestamp 精确到毫秒
      * @return yyyy-MM-dd HH:mm:ss
      */
     public static String timestampToDateStr(Long timestamp) {
          Date date = new Date(timestamp);
          DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          return dateFormat.format(date);
     }

     /**
      * 获取当前时间 与 格林治时间的差
      * @return 小时
      */
     public static int getDiff(){
          String time = TimeZone.getDefault().getID();
          TimeZone myTimeZone =TimeZone.getTimeZone(time);
          return myTimeZone.getRawOffset()/(1000*60*60)+myTimeZone.getDSTSavings()/(1000*60*60);
     }

     /**
      * 根据时间获取格林纸时间
      * @param time yyyy-MM-dd HH:mm:ss
      * @return 2020-04-02T15:59:59Z
      */
     public static String getIsoDateStr(String time){
          String isoDateStr = localToUtcStr(time);
          return isoDateStr.substring(0,10) + "T" + isoDateStr.substring(11) + "Z";
     }

     /**
      * 根据时间获取格林纸时间, 包含了ISO
      * @param time yyyy-MM-dd HH:mm:ss
      * @return 2020-04-02T15:59:59Z
      */
     public static String getISODateStr2(String time){
          String isoDateStr = localToUtcStr(time);
          return "ISODate(" + isoDateStr.substring(0,10) + "T" + isoDateStr.substring(11) + "Z)";
     }

     /**
      * 增加小时
      * @param date 日期
      * @param hour 增加秒数
      * @return Date 日期
      */
     public static Date addHour(Date date, int hour) {
          SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          Calendar cal = Calendar.getInstance();
          cal.setTime(date);
          // 24小时制
          cal.add(Calendar.HOUR, hour);
          date = cal.getTime();
          return date;
     }

     /**
      * 增加分钟
      * @param date 日期
      * @param minute 增加的分钟
      * @return Date 日期
      */
     public static Date addMinute(Date date, int minute) {
          SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          if (date == null) {
               return date;
          }
          //显示输入的日期
          System.out.println("front:" + format.format(date));
          Calendar cal = Calendar.getInstance();
          cal.setTime(date);
          // 24小时制
          cal.add(Calendar.MINUTE, minute);
          date = cal.getTime();
          return date;
     }

     /**
      * 增加秒
      * @param date 日期
      * @param seconds 增加的秒
      * @return Date 日期
      */
     public static Date addSeconds(Date date, int seconds) {
          Calendar cal = Calendar.getInstance();
          cal.setTime(date);
          // 24小时制
          cal.add(Calendar.SECOND, seconds);
          date = cal.getTime();
          return date;
     }

     /**
      * 增加、或者减少月份后的日期
      * @param date 日期 yyyy-MM-dd HH:mm:ss
      * @param months 加减的月份
      * @return 日期
      */
     public static Date addMonths(Date date, int months) {
          SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          Calendar cal = Calendar.getInstance();
          cal.setTime(date);
          cal.add(Calendar.MONTH, months);
          date = cal.getTime();
          return date;
     }


     /**
      * 根据时间戳获取时间
      * @param timestamp 时间戳 精确到毫秒
      * @return yyyy-MM-dd HH:mm:ss
      */
     public static String getDate(long timestamp){
          Date date = new Date(timestamp);
          DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          return dateFormat.format(date);
     }

     /**
      * 根据时间戳获取时间
      * @param timestamp 时间戳 精确到毫秒
      * @return  Date yyyy-MM-dd HH:mm:ss
      */
     public static Date getDateByTimestamp(long timestamp){
          return new Date(timestamp);
     }

     /**
      * 根据完整时间获取日期
      * @param time yyyy-MM-dd HH:mm:ss
      * @return yyyy-MM-dd
      */
     public static String getDateStrShort(String time){
          if(!StringUtils.isEmpty(time) && time.length() > 10) {
               return time.substring(0, 10);
          }
          return time;
     }

     /**
      * 时间格式化 yyyyMMddHHmmss to yyyy-MM-dd HH:mm:ss
      * @param date 时间 200911120000
      * @return 返回 2009-11-12 00:00:
      */
     public static String fomart(String date){
          try {
               DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
               Date dDate = format.parse(date);
               DateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
               return format2.format(dDate);
          } catch (Exception e){
               log.error("格式化时间出错：" +e.getMessage(), e);
          }
          return "";
     }
   public static boolean isDate(String date,String patten){
        DateFormat format = new SimpleDateFormat(patten);
        try {
             format.setLenient(false);
             format.parse(date);
             return true;
        } catch (ParseException e) {
             return false;
        }
   }

     /**
      * 根据格式 把date转成String
      *
      * @param date Date类型数据
      * @param patten 格式
      * @return String
      */
     public static String format(Date date, String patten) {
          SimpleDateFormat sdf = new SimpleDateFormat(patten);
          return sdf.format(date);
     }

     /**
      * 替换时间中的 - ：
      * @param time 时间 yyyy-MM-dd HH:mm:ss
      * @return yyyyMMddHHmmss
      */
     public static String replaceAll(String time){
          return time.replace(":", "")
                  .replace(" ", "")
                  .replace("-", "");
     }

     /**
      * 返回一周的第几天
      * @param date 日期
      * @return 星期天  = 1； 星期1 = 2 。。。。
      */
     public static int dayOfWeek(Date date){
          Calendar calendar = Calendar.getInstance();
          calendar.setTime(date);
          int w = calendar.get(Calendar.DAY_OF_WEEK) - 1;
          if(w < 0){
               w = 0;
          }
          return w;
     }

     public static String zero = "0";

     /**
      * 返回日的索引
      *
      * 如果是前9天则去掉0
      *
      * 日期格式为yyyy-MM-dd 或者 yyyy-MM-dd HH:mm:ss
      *
      * @param day 日期字符串
      * @return 索引
      */
     public static int dayOfMonth(String day){
          day = day.substring(8, 10);
          if(zero.equals(day.substring(0,1))) {
               day = day.substring(1);
          }
          return Integer.parseInt(day);
     }

     /**
      * 获取年
      * @param time yyyy-MM-dd 或者 yyyy-MM-dd HH:mm:ss
      * @return yyyy
      */
     public static String getYearStr(String time){
          return time.substring(0, 4);
     }

     /**
      * 获取月
      * @param time yyyy-MM-dd 或者 yyyy-MM-dd HH:mm:ss
      * @return MM
      */
     public static String getMonthStr(String time){
          return time.substring(5, 7);
     }

     /**
      * 获取天
      * @param time yyyy-MM-dd 或者 yyyy-MM-dd HH:mm:ss
      * @return dd
      */
     public static String getDayStr(String time){
          return time.substring(8, 10);
     }


     /**
      * 计算两个日期之间的秒
      *
      * 后者 - 前者
      * @param date1 日期1
      * @param date2 日期2
      * @return 秒
      */
     public static int diff(Date date1, Date date2){
          return (int)((date2.getTime() - date1.getTime()) / 1000);
     }


     /**
      * 把秒 格式化成 00:00:00
      * @param secondsTime 秒 比如：61
      * @return 00:01:01
      */
     public static String secondsToHourMinuteSeconds(long secondsTime) {
          StringBuilder sb = new StringBuilder();
          long days = secondsTime / (60 * 60 * 24);
          long hours = (secondsTime % (60 * 60 * 24)) / (60 * 60);
          long minutes = (secondsTime % (60 * 60)) / 60;
          long seconds = secondsTime % 60;
          DecimalFormat format = new DecimalFormat("00");
          sb.append(format.format(hours)).append(":").append(format.format(minutes)).append(":").append(format.format(seconds));
          return sb.toString();
     }

     /**
      * 判断两个日期是否超过x个月
      *
      * end 日期需要大于 start
      *
      * @param months 月份
      * @return ture:是  false:否
      */
     public static boolean diffMonths(Date start, Date end, int months) {
          try {
               //先获取start 开始日期，指定月份后的日期
               Date calculateDate =  addMonths(start, months);
               log.info("calculateDate = {}", calculateDate);
               return end.after(calculateDate);
          } catch (Exception e) {
               throw new CustomException(ErrorCode.SYS_PARAM_INNER_ERR, "get diff month error");
          }
     }

     /**
      * 获取一个指定时间范围内的随机时间
      * @param beginDate 开始日期 yyyy-MM-dd
      * @param endDate 结束日期 yyyy-MM-dd
      * @return date
      */
     public static Date randomDate(String beginDate, String endDate) {
          try {
               SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
               Date start = format.parse(beginDate);
               Date end = format.parse(endDate);

               if(start.getTime() >= end.getTime()){
                    return null;
               }
               long date = random(start.getTime(),end.getTime());
               return new Date(date);
          } catch (Exception e) {
               log.error("create random date error" + e.getMessage(), e);
          }
          return null;
     }

     private static long random(long begin,long end){
          long rtn = begin + (long)(Math.random() * (end - begin));
          if(rtn == begin || rtn == end){
               return random(begin,end);
          }
          return rtn;
     }

     /**
      * 获取某个月的最后一天
      * @param year 指定年
      * @param month 指定月
      * @return 当月最后一天
      */
     public static String getLastDayOfMonth(int year, int month) {
          Calendar cal = Calendar.getInstance();
          cal.set(Calendar.YEAR, year);
          cal.set(Calendar.MONTH, month - 1);
          cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DATE));
          return new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
     }

     /**
      * 获取某个月的第一天
      * @param year 指定年
      * @param month 指定月
      * @return 当月第一天
      */
     public static String getFirstDayOfMonth(int year, int month) {
          Calendar cal = Calendar.getInstance();
          cal.set(Calendar.YEAR, year);
          cal.set(Calendar.MONTH, month - 1);
          cal.set(Calendar.DAY_OF_MONTH, cal.getMinimum(Calendar.DATE));
          return new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
     }

     /**
      * 获取时间 HH:mm:ss
      * @param date 日期，yyyy-MM-dd HH:mm:ss
      * @return HH:mm:ss
      */
     public static String getTimeHHmmss(Date date){
          SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
          return sdf.format(date);
     }

     /**
      * 格式化日期为字符串
      * @param date 日期
      * @param format 格式，如yyyy-MM-dd HH:mm:ss，完全根据自定义的格式获取
      * @return 字符串日期
      */
     public static String toString(Date date, String format){
          SimpleDateFormat sdf = new SimpleDateFormat(format);
          return sdf.format(date);
     }

}

