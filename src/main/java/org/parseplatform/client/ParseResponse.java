package org.parseplatform.client;

/*
File:  ParseResponse.java
Version: 0-SNAPSHOT
Contact: hello@dotweblabs.com
----
Copyright (c) 2018, Dotweblabs Web Technologies
All rights reserved.
Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
    * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
    * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
    * Neither the name of Dotweblabs Web Technologies nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.
THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES,
INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Kerby Martino
 * @since 0-SNAPSHOT
 * @version 0-SNAPSHOT
 */
public class ParseResponse extends JSONObject {

    public ParseResponse() {}
    public ParseResponse(String json) {
        if(json != null && !json.isEmpty()) {
            JSONValue value = JSONParser.parseStrict(json);
            if(value != null) {
                if(value.isObject() != null) {
                    JSONObject jsonObject = value.isObject();
                    if(jsonObject != null) {
                        Iterator<String> it = jsonObject.keySet().iterator();
                        while(it.hasNext()) {
                            String key = it.next();
                            JSONValue jsonValue = jsonObject.get(key);
                            put(key, jsonValue);
                        }
                    }
                }
            }
        }
    }
    @Deprecated
    public static ParseResponse parse(String json) {
        JSONObject jsonObject = (JSONObject) JSONParser.parseStrict(json);
        return ParseResponse.clone(jsonObject);
    }
    @Deprecated
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
    public Long getCount() {
        if(get("count") != null && get("count").isNumber() != null) {
            Double count = get("count").isNumber().doubleValue();
            return count.longValue();
        }
        return null;
    }
    public JSONValue getFirstResult() {
        JSONArray results = getResults();
        for(int i=0;i<results.size();i++) {
            return results.get(i);
        }
        return null;
    }
    public ParseObject getFirstResultAsParseObject() {
        JSONArray results = getResults();
        for(int i=0;i<results.size();i++) {
            if(results.get(i).isObject() != null) {
                ParseObject parseObject = new ParseObject(results.get(i).isObject());
                return parseObject;
            }
        }
        return null;
    }
    public List<ParseObject> getResultsAsParseObject() {
        List<ParseObject> resultList = new LinkedList<ParseObject>();
        JSONArray results = getResults();
        for(int i=0;i<results.size();i++) {
            if(results.get(i).isObject() != null) {
                ParseObject parseObject = new ParseObject(results.get(i).isObject());
                resultList.add(parseObject);
            }
        }
        return resultList;
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
//    public <T> T as(Class<T> clazz) {
//        T as = JSON.parse(this.toString());
//        return as;
//    }

    public ParseObject asParseObject(String className) {
        return new ParseObject(className, this);
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
