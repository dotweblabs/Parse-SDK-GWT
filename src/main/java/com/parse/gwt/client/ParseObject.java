package com.parse.gwt.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.json.client.*;
import com.google.web.bindery.autobean.shared.AutoBeanFactory;
import com.parse.gwt.client.js.base.JSON;

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
    public ParsePointer getPointer() {
        ParsePointer pointer = new ParsePointer(this);
        return pointer;
    }

    public Parse.Query relation(String key) {
        Parse.Query query = null;
        try {
            JSONObject jsonRelation = get(key) != null ? get(key).isObject() : null;
            if(jsonRelation != null && jsonRelation.isObject() != null) {
                JSONObject jsonObject = get(key).isObject();
                ParseRelation relation = ParseRelation.clone(jsonObject);
                String className = relation.getClassName();
                if(className != null) {
                    query = Parse.Query.extend(new ParseObject(className));
                    JSONObject relatedTo = new JSONObject();
                    ParsePointer pointer = ParsePointer.parse(this.getClassName(), this.getObjectId());
                    relatedTo.put("object", pointer);
                    relatedTo.put("key", new JSONString(key));
                    Where where = new Where("$relatedTo", relatedTo);
                    query.where(where);
                }
            } else {
                throw new RuntimeException("ParseRelation is missing for " + key);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return query;
    }

    public void setACL(ParseACL acl) {
        if(acl != null) {
            put("ACL", acl);
        }
    }

    /**
     * Creates a new {@ParseObject} from a {@JsType} object
     *
     * @param className Parse className
     * @param object Source object
     * @param <T>
     * @return a new new {@ParseObject}
     */
    public static <T> ParseObject from(String className, T object) {
        String json = JSON.stringify(object);
        ParseObject parseObject = ParseObject.parse(className, json);
        return parseObject;
    }

    /**
     * Marshall this {@ParseObject} into a target object.
     *
     * @param clazz Target object class
     * @param <T> Target object type
     * @return Target object
     */
    public <T> T as(Class<T> clazz) {
        T as = JSON.parse(this.toString());
        return as;
    }

}
