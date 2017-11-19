package org.parseplatform.client.util;

import elemental.client.Browser;

public class LogUtil {
    public static void log(String log) {
        Browser.getWindow().getConsole().log(log);
    }
}
