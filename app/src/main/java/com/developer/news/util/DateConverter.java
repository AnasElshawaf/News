package com.developer.news.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter {

    public static String parseDateToddMMyyyy(String time) {
        String temp = time.replace("Z", " UTC");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        Date date = null;
        try {
            date = sdf.parse(temp);
        } catch (ParseException e) {
            e.printStackTrace();
        }
//        SimpleDateFormat newDate = new SimpleDateFormat("M-dd-yyyy");
//        String finalDate = newDate.format(date);

        String finalDate=  DateFormat.getDateInstance(DateFormat.MEDIUM).format(date);


        return finalDate;
    }

    }
