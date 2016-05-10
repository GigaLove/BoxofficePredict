/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weiresearch.film.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author GigaLiu
 */
public class DateUtil {

    public static String date2Str(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        if (date == null) {
            return null;
        }
        return sdf.format(date);
    }

    public static Date str2Date(String dateStr) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            return sdf.parse(dateStr);
        } catch (ParseException | NullPointerException ex) {
            Logger.getLogger(DateUtil.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public static Date movieReleaseTime2Date(String dateStr) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm");
            return sdf.parse(dateStr);
        } catch (ParseException | NullPointerException ex) {
            Logger.getLogger(DateUtil.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
