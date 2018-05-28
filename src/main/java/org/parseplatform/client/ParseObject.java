package org.parseplatform.client;

/*
File:  ParseObject.java
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

import com.dotweblabs.shape.client.Shape;
import com.google.common.base.Joiner;
import com.google.gwt.json.client.*;
import com.google.gwt.user.client.rpc.AsyncCallback;
import org.parseplatform.client.js.base.JSON;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * @author Kerby Martino
 * @since 0-SNAPSHOT
 * @version 0-SNAPSHOT
 */
public class ParseObject extends JSONObject {

    private String className;

    public ParseObject() {
    }

    public ParseObject(JSONObject jsonObject) {
        if(jsonObject != null) {
            Iterator<String> it = jsonObject.keySet().iterator();
            while (it.hasNext()) {
                String key = it.next();
                JSONValue value = jsonObject.get(key);
                this.put(key, value);
            }
        }
    }

    public ParseObject(String className, String json) {
        JSONValue jsonValue = JSONParser.parseStrict(json);
        if(jsonValue != null && jsonValue.isObject() != null) {
            JSONObject jsonObject = jsonValue.isObject();
            Iterator<String> it = jsonObject.keySet().iterator();
            while(it.hasNext()) {
                String key = it.next();
                JSONValue value = jsonObject.get(key);
                put(key, value);
            }
        }
        setClassName(className);
    }

    public ParseObject(String className, JSONObject jsonObject) {
        this(jsonObject);
        setClassName(className);
    }

    public ParseObject(String className){
        this.className = className;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String classsName) {
        this.className = classsName;
    }

    public static ParseObject parse(String json) {
        JSONValue jsonValue = JSONParser.parseStrict(json);
        if(jsonValue != null && jsonValue.isObject() != null) {
            JSONObject jsonObject = (JSONObject) JSONParser.parseStrict(json);
            return new ParseObject(jsonObject);
        }
        return null;
    }

    public static ParseObject parse(String className, String json) {
        ParseObject parseObject = parse(json);
        if(parseObject != null) {
            parseObject.setClassName(className);
        }
        return parseObject;
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
    public void putArray(String key, Double value) {
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
                ParsePointer parsePointer = new ParsePointer(className, objectId);
                return parsePointer;
            }
        }
        return null;
    }

    public ParseACL getACL() {
        if(get("ACL") != null && get("ACL").isObject() != null) {
            JSONObject acl = get("ACL").isObject();
            ParseACL parseACL = new ParseACL(acl);
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
        if(this.get(key) != null && this.get(key).isNumber() != null) {
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

    public Short getShort(String key) {
        if(this.get(key) != null && this.get(key).isNumber() != null) {
            Short db = new Double(this.get(key).isNumber().doubleValue()).shortValue();
            return db;
        }
        return null;
    }

    public Float getFloat(String key) {
        if(this.get(key) != null && this.get(key).isNumber() != null) {
            Float db = new Double(this.get(key).isNumber().doubleValue()).floatValue();
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

    public ParseQuery relation(String key) {
        ParseQuery query = null;
        try {
            JSONObject jsonRelation = get(key) != null ? get(key).isObject() : null;
            if(jsonRelation != null && jsonRelation.isObject() != null) {
                JSONObject jsonObject = get(key).isObject();
                ParseRelation relation = ParseRelation.clone(jsonObject);
                String className = relation.getClassName();
                if(className != null) {
                    query = new ParseQuery(new ParseObject(className));
                    JSONObject relatedTo = new JSONObject();
                    ParsePointer pointer = new ParsePointer(this.getClassName(), this.getObjectId());
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
    
    /*
    Methods
     */

    public void create(final ParseAsyncCallback<ParseResponse> callback) {
        final ParseObject object = this;
        Shape.post(Parse.SERVER_URL + Parse.CLASSES_URI + object.getClassName())
                .header("X-Parse-Application-Id", Parse.X_Parse_Application_Id)
                .header("X-Parse-REST-API-Key", Parse.X_Parse_REST_API_Key)
                .header("X-Parse-Master-Key", Parse.X_Parse_Master_Key)
                .header("X-Parse-Session-Token", Parse.X_Parse_Session_Token)
                .body(object.toString())
                .asJson(new AsyncCallback<String>() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        callback.onFailure(new ParseError(throwable));
                    }
                    @Override
                    public void onSuccess(String s) {
                        JSONObject jsonObject = JSONParser.parseStrict(s).isObject();
                        ParseResponse response = new ParseResponse();
                        Iterator<String> it = jsonObject.keySet().iterator();
                        while (it.hasNext()) {
                            String key = it.next();
                            JSONValue jsonValue = jsonObject.get(key);
                            response.put(key, jsonValue);
                        }
                        callback.onSuccess(response);
                    }
                });
    }
    public void retrieve(final ParseAsyncCallback<ParseObject> callback) {
        final ParseObject ref = this;
        String objectId = ref.getObjectId();
        final String className = ref.getClassName();
        final String path = Parse.SERVER_URL + Parse.CLASSES_URI + className + "/" + objectId;
        Shape.get(path)
                .header("X-Parse-Application-Id", Parse.X_Parse_Application_Id)
                .header("X-Parse-REST-API-Key", Parse.X_Parse_REST_API_Key)
                .header("X-Parse-Master-Key", Parse.X_Parse_Master_Key)
                .header("X-Parse-Session-Token", Parse.X_Parse_Session_Token)
                .asJson(new AsyncCallback<String>() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        callback.onFailure(new ParseError(throwable));
                    }
                    @Override
                    public void onSuccess(String s) {
                        callback.onSuccess(new ParseObject(className, s));
                    }
                });
    }
    public static void retrieve(final ParseObject ref, final List<String> includes,
                                final ParseAsyncCallback<ParseObject> callback) {
        String objectId = ref.getObjectId();
        String className = ref.getClassName();
        String path = Parse.SERVER_URL + Parse.CLASSES_URI + className + "/" + objectId;

        String stringIncludes = Joiner.on(",").join(includes);
        if(stringIncludes != null && !stringIncludes.isEmpty()) {
            path = path + "?include=" + stringIncludes;
        }

        Shape.get(path)
                .header("X-Parse-Application-Id", Parse.X_Parse_Application_Id)
                .header("X-Parse-REST-API-Key", Parse.X_Parse_REST_API_Key)
                .header("X-Parse-Master-Key", Parse.X_Parse_Master_Key)
                .header("X-Parse-Session-Token", Parse.X_Parse_Session_Token)
                .asJson(new AsyncCallback<String>() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        callback.onFailure(new ParseError(throwable));
                    }
                    @Override
                    public void onSuccess(String s) {
                        callback.onSuccess(new ParseObject(className, s));
                    }
                });
    }
    public void delete(final ParseAsyncCallback<ParseResponse> callback) {
        final ParseObject ref = this;
        String objectId = ref.getObjectId();
        final String className = ref.getClassName();
        final String path = Parse.SERVER_URL + Parse.CLASSES_URI + className + "/" + objectId;
        JSONObject payload = new JSONObject();
        JSONObject opponents = new JSONObject();
        opponents.put("__op", new JSONString("Delete"));
        payload.put("opponents", opponents);
        Shape.delete(path)
                .header("X-Parse-Application-Id", Parse.X_Parse_Application_Id)
                .header("X-Parse-REST-API-Key", Parse.X_Parse_REST_API_Key)
                .header("X-Parse-Master-Key", Parse.X_Parse_Master_Key)
                .header("X-Parse-Session-Token", Parse.X_Parse_Session_Token)
                .body(payload.toString())
                .asJson(new AsyncCallback<String>() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        callback.onFailure(new ParseError(throwable));
                    }
                    @Override
                    public void onSuccess(String s) {
                        callback.onSuccess(new ParseResponse(s));
                    }
                });
    }
    public void update(final ParseAsyncCallback<ParseResponse> callback) {
        try {
            final ParseObject ref = this;
            String objectId = ref.getObjectId();
            final String className = ref.getClassName();
            final String path = Parse.SERVER_URL + Parse.CLASSES_URI + className + "/" + objectId;
            JSONObject payload = new JSONObject();
            Iterator<String> it = ref.keySet().iterator();
            while(it.hasNext()) {
                String key = it.next();
                JSONValue value = ref.get(key);
                // Do not copy objectId
                if(!key.equals("objectId")){
                    if(key.equals("createdAt") || key.equals("updatedAt")) {
                        // skip
                    } else {
                        payload.put(key, value);
                    }
                }
            }
            Shape.put(path)
                    .header("X-Parse-Application-Id", Parse.X_Parse_Application_Id)
                    .header("X-Parse-REST-API-Key", Parse.X_Parse_REST_API_Key)
                    .header("X-Parse-Master-Key", Parse.X_Parse_Master_Key)
                    .header("X-Parse-Session-Token", Parse.X_Parse_Session_Token)
                    .body(payload.toString())
                    .asJson(new AsyncCallback<String>() {
                        @Override
                        public void onFailure(Throwable throwable) {
                            callback.onFailure(new ParseError(throwable));
                        }
                        @Override
                        public void onSuccess(String s) {
                            //logger.log(Level.INFO, "Parse Update Response: " + s);
                            try {
                                ParseResponse parseResponse = new ParseResponse(s);
                                callback.onSuccess(parseResponse);
                            } catch (Exception e) {
                                callback.onFailure(new ParseError(e));
                            }
                        }
                    });

        } catch (Exception e) {
            callback.onFailure(new ParseError(e));
        }
    }

    public ParseQuery query() {
        return new ParseQuery(this);
    }

    public void increment(String field, Long amount, final ParseAsyncCallback<Long> callback) {
        try {
            final ParseObject ref = this;
            String objectId = ref.getObjectId();
            final String className = ref.getClassName();
            final String path = Parse.SERVER_URL + Parse.CLASSES_URI + className + "/" + objectId;
            JSONObject payload = new JSONObject();
            JSONObject op = new JSONObject();
            op.put("__op", new JSONString("Increment"));
            op.put("amount", new JSONNumber(amount));
            payload.put(field, op);
            Shape.put(path)
                    .header("X-Parse-Application-Id", Parse.X_Parse_Application_Id)
                    .header("X-Parse-REST-API-Key", Parse.X_Parse_REST_API_Key)
                    .header("X-Parse-Master-Key", Parse.X_Parse_Master_Key)
                    .header("X-Parse-Session-Token", Parse.X_Parse_Session_Token)
                    .body(payload.toString())
                    .asJson(new AsyncCallback<String>() {
                        @Override
                        public void onFailure(Throwable throwable) {
                            callback.onFailure(new ParseError(throwable));
                        }
                        @Override
                        public void onSuccess(String s) {
                            try {
                                ParseResponse parseResponse = new ParseResponse(s);
                                Double value = parseResponse.get(field).isNumber().doubleValue();
                                callback.onSuccess(value.longValue());
                            } catch (Exception e) {
                                callback.onFailure(new ParseError(e));
                            }
                        }
                    });
        } catch (Exception e) {
            callback.onFailure(new ParseError(e));
        }
    }

    public void getRelation(String referenceKey, ParseObject referencee,
                            final ParseAsyncCallback<ParseResponse> callback) {
        ParseObject reference = this;
        ParseQuery query = new ParseQuery(referencee);
        JSONObject jsonObject = new JSONObject();
        ParsePointer pointer = new ParsePointer(reference.getClassName(), reference.getObjectId());
        jsonObject.put("object", pointer);
        jsonObject.put("key", new JSONString(referenceKey));
        Where where = new Where("$relatedTo", jsonObject);
        query.where(where);
        query.find(new ParseAsyncCallback<ParseResponse>() {
            @Override
            public void onFailure(ParseError error) {
                callback.onFailure(error);
            }
            @Override
            public void onSuccess(ParseResponse parseResponse) {
                callback.onSuccess(parseResponse);
            }
        });
    }

    public void getRelation(String referenceKey, ParseObject referencee,
                            String filterField, String filterValue, final ParseAsyncCallback<ParseResponse> callback) {
        ParseObject reference = this;
        String regex = "(?i)(?:.*";

        String [] words = filterValue.split(" ");
        for (int i = 0; i < words.length; i++) {
            regex += words[i];
            if (i == words.length - 1) {
                regex += ")";
            } else {
                regex += "|.*";
            }
        }
        JSONValue jsonValueRegex = new JSONString(regex);
        ParseQuery query = new ParseQuery(referencee);
        JSONObject jsonObject = new JSONObject();
        ParsePointer pointer = new ParsePointer(reference.getClassName(), reference.getObjectId());
        jsonObject.put("object", pointer);
        jsonObject.put("key", new JSONString(referenceKey));
        Where where = new Where("$relatedTo", jsonObject).where(filterField).regex(jsonValueRegex);
        query.where(where);
        query.find(new ParseAsyncCallback<ParseResponse>() {
            @Override
            public void onFailure(ParseError e) {
                callback.onFailure(e);
            }
            @Override
            public void onSuccess(ParseResponse parseResponse) {
                callback.onSuccess(parseResponse);
            }
        });
    }

    public void getRelationOrderAscending(String referenceKey, ParseObject referencee,
                                          final ParseAsyncCallback<ParseResponse> callback) {
        ParseObject reference = this;
        ParseQuery query = new ParseQuery(referencee);
        JSONObject jsonObject = new JSONObject();
        ParsePointer pointer = new ParsePointer(reference.getClassName(), reference.getObjectId());
        jsonObject.put("object", pointer);
        jsonObject.put("key", new JSONString(referenceKey));
        Where where = new Where("$relatedTo", jsonObject);
        query.where(where);

        query.find(new ParseAsyncCallback<ParseResponse>() {
            @Override
            public void onFailure(ParseError error) {
                callback.onFailure(error);
            }
            @Override
            public void onSuccess(ParseResponse parseResponse) {
                callback.onSuccess(parseResponse);
            }
        });
    }


    public void createRelation(String key, ParsePointer target,
                               final ParseAsyncCallback<ParseResponse> callback) {
        ParseObject object = this;
        String objectId = object.getObjectId();
        final String className = object.getClassName();
        final String path = Parse.SERVER_URL + Parse.CLASSES_URI + className + "/" + objectId;
        JSONArray objects = new JSONArray();
        objects.set(0, target);
        JSONObject payload = new JSONObject();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("__op", new JSONString("AddRelation"));
        jsonObject.put("objects", objects);
        payload.put(key, jsonObject);
        Shape.put(path)
                .header("X-Parse-Application-Id", Parse.X_Parse_Application_Id)
                .header("X-Parse-REST-API-Key", Parse.X_Parse_REST_API_Key)
                .header("X-Parse-Master-Key", Parse.X_Parse_Master_Key)
                .header("X-Parse-Session-Token", Parse.X_Parse_Session_Token)
                .body(payload.toString())
                .asJson(new AsyncCallback<String>() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        callback.onFailure(new ParseError(throwable));
                    }
                    @Override
                    public void onSuccess(String s) {
                        try{
                            ParseResponse parseResponse = new ParseResponse(s);
                            callback.onSuccess(parseResponse);
                        } catch (Exception e) {
                            callback.onFailure(new ParseError(e));
                        }
                    }
                });
    }

    public void createRelation(String key, ParsePointer[] targets,
                               final ParseAsyncCallback<ParseResponse> callback) {
        final ParseObject object = this;
        String objectId = object.getObjectId();
        final String className = object.getClassName();
        final String path = Parse.SERVER_URL + Parse.CLASSES_URI + className + "/" + objectId;
        JSONObject payload = new JSONObject();
        JSONArray objects = new JSONArray();
        int i = 0;
        for(ParsePointer p : Arrays.asList(targets)) {
            objects.set(i, p);
            i++;
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("__op", new JSONString("AddRelation"));
        jsonObject.put("objects", objects);
        payload.put(key, jsonObject);
        Shape.put(path)
                .header("X-Parse-Application-Id", Parse.X_Parse_Application_Id)
                .header("X-Parse-REST-API-Key", Parse.X_Parse_REST_API_Key)
                .header("X-Parse-Master-Key", Parse.X_Parse_Master_Key)
                .header("X-Parse-Session-Token", Parse.X_Parse_Session_Token)
                .body(payload.toString())
                .asJson(new AsyncCallback<String>() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        callback.onFailure(new ParseError(throwable));
                    }
                    @Override
                    public void onSuccess(String s) {
                        callback.onSuccess(new ParseResponse(s));
                    }
                });
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
