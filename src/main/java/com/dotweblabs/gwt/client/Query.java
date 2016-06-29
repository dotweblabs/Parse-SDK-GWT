package com.dotweblabs.gwt.client;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONBoolean;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;

/**
 *
 * Simple Parse Client for GWT
 *
 * @author Kerby Martino
 * @since 0-SNAPSHOT
 * @version 0-SNAPSHOT
 */
public class Query extends JSONObject {

    public Query() {}

    public Query(ParseObject object) {
        setClassName(object.getClassName());
    }

    private String getClassName() {
        return get("className").isString().stringValue();
    }

    private void setClassName(String className) {
        put("className", new JSONString(className));
    }

    public void where(Where where){
        put("where", where);
    }

}
