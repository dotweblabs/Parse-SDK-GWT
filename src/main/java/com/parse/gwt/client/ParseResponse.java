/**
 *
 * Copyright (c) 2016 Dotweblabs Web Technologies and others. All rights reserved.
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
package com.parse.gwt.client;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.parse.gwt.client.js.base.JSON;

import java.util.Iterator;

/**
 *
 *
 * @author Kerby Martino
 * @since 0-SNAPSHOT
 * @version 0-SNAPSHOT
 */
public class ParseResponse extends JSONObject {
    public static ParseResponse parse(String json) {
        JSONObject jsonObject = (JSONObject) JSONParser.parseStrict(json);
        return ParseResponse.clone(jsonObject);
    }
    public static ParseResponse clone(JSONObject jsonObject) {
        ParseResponse response = new ParseResponse();
        Iterator<String> it = jsonObject.keySet().iterator();
        while(it.hasNext()) {
            if(response == null) {
                response = new ParseResponse();
            }
            String key = it.next();
            JSONValue value = jsonObject.get(key);
            response.put(key, value);
        }
        return response;
    }
    public ParseObject asParseObject(String className) {
        return ParseObject.clone(className, this);
    }
    public Boolean isEmpty() {
        if(getResults() != null) {
            return getResults().size() == 0 ? true : false;
        } else if (getResult() != null && (getResult().isArray() != null)) {
            return getResult().isArray().size() == 0 ? true : false;
        }
        return true;
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
    public JSONArray getResults() {
        JSONArray results =  get("results") != null ? get("results").isArray() : null;
        return results;
    }
    public JSONValue getResult() {
        JSONValue result = get("result") != null ? get("result") : null;
        return result;
    }
    public String getErrorMessage() {
        String message = get("error") != null ? get("error").isString().stringValue() : null;
        return message;
    }
    public int getErrorCode() {
        double code = get("code") != null ? get("code").isNumber().doubleValue() : null;
        return (int) code;
    }

    /**
     * Marshall this {@ParseResponse} into a target object.
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
        if(get("code") != null
            && get("error") != null) {
            return true;
        }
        return false;
    }

}
