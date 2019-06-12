package com.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Description 根据年龄返回年代
 * @Author wangliqiang
 * @Date 2019/6/3 10:58
 */
public class DateUtils {

    public static String getYearBaseByAge(String age) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        calendar.add(Calendar.YEAR, -Integer.valueOf(age));
        Date newDate = calendar.getTime();

        DateFormat dateFormat = new SimpleDateFormat("yyyy");
        String newDateString = dateFormat.format(newDate);
        Integer newDateInteger = Integer.valueOf(newDateString);
        String yearBaseType = "未知";
        if (newDateInteger >= 1940 && newDateInteger < 1950) {
            yearBaseType = "40后";
        } else if (newDateInteger >= 1950 && newDateInteger < 1960) {
            yearBaseType = "50后";
        } else if (newDateInteger >= 1960 && newDateInteger < 1970) {
            yearBaseType = "60后";
        } else if (newDateInteger >= 1970 && newDateInteger < 1980) {
            yearBaseType = "70后";
        } else if (newDateInteger >= 1980 && newDateInteger < 1990) {
            yearBaseType = "80后";
        } else if (newDateInteger >= 1990 && newDateInteger < 2000) {
            yearBaseType = "90后";
        } else if (newDateInteger >= 2000 && newDateInteger < 2010) {
            yearBaseType = "00后";
        } else if (newDateInteger >= 2010) {
            yearBaseType = "10后";
        }

        return yearBaseType;
    }


    public static int getDaysBetweenStartAndEnd(String startTime,String endTime,String dateFormateString) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat(dateFormateString);
        Date start = dateFormat.parse(startTime);
        Date end = dateFormat.parse(endTime);

        Calendar startCalendar = Calendar.getInstance();
        Calendar endCalendar = Calendar.getInstance();
        startCalendar.setTime(start);
        endCalendar.setTime(end);
        int days = 0;
        while(startCalendar.before(endCalendar)){
            startCalendar.add(Calendar.DAY_OF_YEAR,1);
            days +=1;
        }

        return days;
    }


    public static String getHoursByDay(String timeValue) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd hhmmss");
        Date time = dateFormat.parse(timeValue);
        dateFormat = new SimpleDateFormat("hh");
        String resultHour = dateFormat.format(time);

        return resultHour;
    }

}
