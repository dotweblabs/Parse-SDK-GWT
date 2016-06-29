package com.dotweblabs.gwt.client;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONBoolean;
import com.google.gwt.json.client.JSONObject;

/**
 *
 * Simple Parse Client for GWT
 *
 * @author Kerby Martino
 * @since 0-SNAPSHOT
 * @version 0-SNAPSHOT
 */
public class Query extends JSONObject {

    private String className;
    private Where where = null;
    private JSONArray and;
    private JSONArray or;
    public Query() {}
    public Query(ParseObject object) {
        setClassName(object.getClassName());
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Where where(String key) {
        this.where = new Where(key);
        return this.where;
    }

    public void and(Where... where){
        if(and == null){
            and = new JSONArray();
        }
    }

    public void or(Where... where){
        if(or == null){
            or = new JSONArray();
        }
    }
}
