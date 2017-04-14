/**
 *
 * Copyright (c) 2017 Dotweblabs Web Technologies and others. All rights reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package org.parseplatform.client.util;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.TimeZone;
import com.google.gwt.i18n.client.TimeZoneInfo;
import com.google.gwt.i18n.client.constants.TimeZoneConstants;
import com.google.gwt.i18n.shared.DateTimeFormat;
import elemental.client.Browser;

import java.util.Date;

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
