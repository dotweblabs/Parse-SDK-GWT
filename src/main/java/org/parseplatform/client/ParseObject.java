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
package org.parseplatform.client;

import com.google.gwt.json.client.*;
import org.parseplatform.client.js.base.JSON;

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
    public static ParseObject parse( String json) {
        JSONObject jsonObject = (JSONObject) JSONParser.parseStrict(json);
        return ParseObject.clone(jsonObject);
    }

    public static ParseObject parse(String className, String json) {
        JSONObject jsonObject = (JSONObject) JSONParser.parseStrict(json);
        return ParseObject.clone(className, jsonObject);
    }

    public static ParseObject clone(String className, JSONObject jsonObject) {
        ParseObject response = null;
        Iterator<String> it = jsonObject.keySet().iterator();
        while (it.hasNext()) {
            if (response == null) {
                response = new ParseObject(className);
            }
            String key = it.next();
            JSONValue value = jsonObject.get(key);
            response.put(key, value);
        }
        return response;
    }

    public static ParseObject clone(JSONObject jsonObject) {
        ParseObject response = null;
        Iterator<String> it = jsonObject.keySet().iterator();
        while(it.hasNext()) {
            if(response == null) {
                response = new ParseObject();
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

    public ParsePointer getPointer(String key) {
        if(this.get(key) != null && this.get(key).isObject() != null) {
            JSONObject jso = this.get(key).isObject();
            String type = jso.get("__type").isString() != null ? jso.get("__type").isString().stringValue() : null;
            if(type != null && type.equals("Pointer")){
                String className = jso.get("className").isString() != null ? jso.get("className").isString().stringValue() : null;
                String objectId = jso.get("objectId").isString() != null ? jso.get("objectId").isString().stringValue() : null;
                ParsePointer parsePointer = ParsePointer.parse(className, objectId);
                return parsePointer;
            }
        }
        return null;
    }

    public ParseACL getACL() {
        if(get("ACL") != null && get("ACL").isObject() != null) {
            JSONObject acl = get("ACL").isObject();
            ParseACL parseACL = (ParseACL) acl;
            return parseACL;
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

    public String getString(String key) {
        if(this.get(key) != null && this.get(key).isString() != null) {
            return this.get(key).isString().stringValue();
        }
        return null;
    }

    public Long getLong(String key) {
        if(this.get(key) != null && this.get(key).isString() != null) {
            Double db = this.get(key).isNumber().doubleValue();
            return Long.valueOf(db + "");
        }
        return null;
    }

    public Double getDouble(String key) {
        if(this.get(key) != null && this.get(key).isNumber() != null) {
            Double db = this.get(key).isNumber().doubleValue();
            return db;
        }
        return null;
    }

    public Boolean getBoolean(String key) {
        if(this.get(key) != null && this.get(key).isBoolean() != null) {
            boolean booleanValue = this.get(key).isBoolean().booleanValue();
            return booleanValue;
        }
        return null;
    }

    public JSONArray getJSONArray(String key) {
        if(this.get(key) != null && this.get(key).isArray() != null) {
            JSONArray array = this.get(key).isArray();
            return array;
        }
        return null;
    }

    public JSONObject getJSONObject(String key) {
        if(this.get(key) != null && this.get(key).isObject() != null) {
            JSONObject jso = this.get(key).isObject();
            return jso;
        }
        return null;
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

    public Integer getErrorCode() {
        return get("code") != null ? (int) get("code").isNumber().doubleValue() : null;
    }

    public String getErrorMessage() {
        return get("error").isString() != null ? get("error").isString().stringValue() : null;
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

    /**
     * Check if this object is an error.
     *
     * @return true is this object is error, false if not
     */
    public boolean isError() {
        if(getErrorCode() != null && getErrorMessage() != null) {
            return  true;
        }
        return false;
    }

}
