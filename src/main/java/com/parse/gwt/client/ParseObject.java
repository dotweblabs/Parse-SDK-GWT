package com.parse.gwt.client;

import com.google.gwt.json.client.*;

import java.util.Iterator;

/**
 *
 *
 * @author Kerby Martino
 * @since 0-SNAPSHOT
 * @version 0-SNAPSHOT
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
        return ParseObject.clone(className, jsonObject);
    }
    public static ParseObject clone(String className, JSONObject jsonObject) {
        ParseObject response = null;
        Iterator<String> it = jsonObject.keySet().iterator();
        while(it.hasNext()) {
            if(response == null) {
                response = new ParseObject(className);
            }
            String key = it.next();
            JSONValue value = jsonObject.get(key);
            response.put(key, value);
        }
        return response;
    }
    public void putNull(String key) {
        put(key, JSONNull.getInstance());
    }
    public void putBoolean(String key, Boolean value) {
        if(value != null) {
            put(key, JSONBoolean.getInstance(value));
        }
    }
    public void putString(String key, String value) {
        if(value != null) {
            put(key, new JSONString(value));
        }
    }
    public void putNumber(String key, Integer value) {
        if(value != null) {
            put(key, new JSONNumber(value));
        }
    }
    public void putNumber(String key, Long value) {
        if(value != null) {
            put(key, new JSONNumber(value));
        }
    }
    public void putNumber(String key, Double value) {
        if(value != null) {
            put(key, new JSONNumber(value));
        }
    }
    public void setObjectId(String objectId) {
        if(objectId != null) {
            put("objectId", new JSONString(objectId));
        }
    }
    public String getObjectId() {
        if(get("objectId") != null && get("objectId").isString() != null) {
            return get("objectId").isString().stringValue();
        }
        return null;
    }
    public String getCreatedAt() {
        if(get("createdAt") != null && get("createdAt").isString() != null) {
            return get("createdAt").isString().stringValue();
        }
        return null;
    }
    public String getUpdatedAt() {
        if(get("updatedAt") != null && get("updatedAt").isString() != null) {
            return get("updatedAt").isString().stringValue();
        }
        return null;
    }
}
