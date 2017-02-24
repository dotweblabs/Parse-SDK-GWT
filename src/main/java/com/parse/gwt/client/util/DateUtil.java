package com.parse.gwt.client.util;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.TimeZone;
import com.google.gwt.i18n.client.TimeZoneInfo;
import com.google.gwt.i18n.client.constants.TimeZoneConstants;
import com.google.gwt.i18n.shared.DateTimeFormat;
import elemental.client.Browser;

import java.util.Date;

/**
 * Created by Kerby on 2/23/2017.
 */
public class DateUtil {
    public static String getStringFormat(Date inputDate){
        final TimeZoneConstants timeZoneConstants = GWT.create(TimeZoneConstants.class);
        //final TimeZone latinAmerica = TimeZone.createTimeZone(TimeZoneInfo.buildTimeZoneData(timeZoneConstants.americaSantoDomingo()));
        String strFormat = null;
        try{
            DateTimeFormat dateTimeFormat = DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.ISO_8601);
//            strFormat = dateTimeFormat.format(inputDate);
            strFormat = dateTimeFormat.format(inputDate/*, latinAmerica*/);
            Date d = new Date(strFormat);
            strFormat = dateTimeFormat.format(d, TimeZone.createTimeZone(0));
            String[] s = strFormat.split("\\+");
            strFormat = s[0];
        }catch(Exception e){
            e.printStackTrace();
        }
        return strFormat;
    }
    public static Date iso8601String(String iso) {
        try {
            Date date = new Date(iso);
            return date;
        } catch (Exception e) {
            Browser.getWindow().getConsole().log("Date format error for string: " + iso);
            e.printStackTrace();
        }
        return null;
    }
}
