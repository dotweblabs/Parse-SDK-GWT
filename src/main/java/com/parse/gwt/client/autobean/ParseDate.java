package com.parse.gwt.client.autobean;

/**
 * Created by Kerby on 4/13/2017.
 */
public interface ParseDate {
    String get__type(); // should default to "Date"
    String getIso();
    void set__type(String __type);
    void setIso(String url);
}
