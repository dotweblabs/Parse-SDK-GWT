package com.dotweblabs.gwt.client;

import com.dotweblabs.gwt.client.js.JsonConverter;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by paperspace on 6/29/2016.
 */
public class ParseObject extends JSONObject {
    private String className;
    public ParseObject(String className){
        this.className = className;
    }
    public ParseObject() {
    }
    public String getClassName() {
        return className;
    }
    public void setClassName(String classsName) {
        this.className = classsName;
    }
    public static ParseObject parse(String className, String json) {
        JSONObject jsonObject = (JSONObject) JSONParser.parseStrict(json);
        ParseObject parseObject = ((ParseObject) jsonObject);
        return parseObject;
    }
}
