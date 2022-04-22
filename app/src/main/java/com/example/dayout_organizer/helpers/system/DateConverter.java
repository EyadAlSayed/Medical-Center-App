package com.example.dayout_organizer.helpers.system;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateConverter {


    public static String dateToString(Date date) {
        DateFormat dateFormat;
        dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.ENGLISH);
        return dateFormat.format(date);

    }

    public static Date stringToDate(String sDate) {
        Date date = new Date();
        try {
            date = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.ENGLISH).parse(sDate);
        } catch (Exception ignored) {
        }
        return date;
    }


    public static String findDifference(Date dateStart, Date dateEnd) {
        long difference_In_Time = dateEnd.getTime() - dateStart.getTime();
        long difference_In_Days = (difference_In_Time / (1000 * 60 * 60 * 24)) % 365;
        long difference_In_Hours = (difference_In_Time / (1000 * 60 * 60)) % 24;
        return "" + difference_In_Days + "Day " + difference_In_Hours + "Hour";
    }


    public static String now() {
        return dateToString(Calendar.getInstance().getTime());
    }


    public static String formatDate(String date){
        DateFormat dateFormat;
        dateFormat = new SimpleDateFormat("HH:mm:ss    dd-MM-yyyy", Locale.ENGLISH);
        return dateFormat.format(stringToDate(date));
    }

    public static String formatUTCDate(String date){
        DateFormat dateFormat;
        dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return dateFormat.format(stringToDate(date));
    }
}
