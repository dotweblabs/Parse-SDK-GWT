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

import com.dotweblabs.shape.client.Shape;
import com.google.common.base.Joiner;
import com.google.gwt.json.client.*;
import com.google.gwt.user.client.rpc.AsyncCallback;
import org.parseplatform.client.util.JSON;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;

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
    
    /*
    Methods
     */

    public void create(final AsyncCallback<ParseResponse> callback) {
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
                        callback.onFailure(throwable);
                    }
                    @Override
                    public void onSuccess(String s) {
                        try {
                            JSONObject jsonObject = JSONParser.parseStrict(s).isObject();
                            ParseResponse response = new ParseResponse();
                            Iterator<String> it = jsonObject.keySet().iterator();
                            while (it.hasNext()) {
                                String key = it.next();
                                JSONValue jsonValue = jsonObject.get(key);
                                response.put(key, jsonValue);
                            }
                            callback.onSuccess(response);
                        } catch (Exception e) {
                            callback.onFailure(e);
                        }
                    }
                });
    }
    public void retrieve(final AsyncCallback<ParseObject> callback) {
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
                        callback.onFailure(throwable);
                    }
                    @Override
                    public void onSuccess(String s) {
                        callback.onSuccess(ParseObject.parse(className, s));
                    }
                });
    }
    public static void retrieve(final ParseObject ref, final List<String> includes,
                                final AsyncCallback<ParseObject> callback) {
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
                        callback.onFailure(throwable);
                    }
                    @Override
                    public void onSuccess(String s) {
                        callback.onSuccess(ParseObject.parse(className, s));
                    }
                });
    }
    public void delete(final AsyncCallback<ParseResponse> callback) {
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
                        callback.onFailure(throwable);
                    }
                    @Override
                    public void onSuccess(String s) {
                        callback.onSuccess(ParseResponse.parse(s));
                    }
                });
    }
    public void update(final AsyncCallback<ParseResponse> callback) {
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
                            callback.onFailure(throwable);
                        }
                        @Override
                        public void onSuccess(String s) {
                            //logger.log(Level.INFO, "Parse Update Response: " + s);
                            try {
                                ParseResponse parseResponse = ParseResponse.parse(s);
                                callback.onSuccess(parseResponse);
                            } catch (Exception e) {
                                callback.onFailure(e);
                            }
                        }
                    });

        } catch (Exception e) {
            callback.onFailure(e);
        }
    }

    public void getRelation(String referenceKey, ParseObject referencee,
                                   final AsyncCallback<ParseResponse> callback) {
        ParseObject reference = this;
        ParseQuery query = new ParseQuery(referencee);
        JSONObject jsonObject = new JSONObject();
        ParsePointer pointer = ParsePointer.parse(reference.getClassName(), reference.getObjectId());
        jsonObject.put("object", pointer);
        jsonObject.put("key", new JSONString(referenceKey));
        Where where = new Where("$relatedTo", jsonObject);
        query.where(where);
        query.find(new AsyncCallback<ParseResponse>() {
            @Override
            public void onFailure(Throwable throwable) {
                callback.onFailure(throwable);
            }
            @Override
            public void onSuccess(ParseResponse parseResponse) {
                callback.onSuccess(parseResponse);
            }
        });
    }

    public void getRelation(String referenceKey, ParseObject referencee,
                                   String filterField, String filterValue, final AsyncCallback<ParseResponse> callback) {
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
        ParsePointer pointer = ParsePointer.parse(reference.getClassName(), reference.getObjectId());
        jsonObject.put("object", pointer);
        jsonObject.put("key", new JSONString(referenceKey));
        Where where = new Where("$relatedTo", jsonObject).where(filterField).regex(jsonValueRegex);
        query.where(where);
        query.find(new AsyncCallback<ParseResponse>() {
            @Override
            public void onFailure(Throwable throwable) {
                callback.onFailure(throwable);
            }
            @Override
            public void onSuccess(ParseResponse parseResponse) {
                callback.onSuccess(parseResponse);
            }
        });
    }

    public void getRelationOrderAscending(String referenceKey, ParseObject referencee,
                                                 final AsyncCallback<ParseResponse> callback) {
        ParseObject reference = this;
        ParseQuery query = new ParseQuery(referencee);
        JSONObject jsonObject = new JSONObject();
        ParsePointer pointer = ParsePointer.parse(reference.getClassName(), reference.getObjectId());
        jsonObject.put("object", pointer);
        jsonObject.put("key", new JSONString(referenceKey));
        Where where = new Where("$relatedTo", jsonObject);
        query.where(where);

        query.find(new AsyncCallback<ParseResponse>() {
            @Override
            public void onFailure(Throwable throwable) {
                callback.onFailure(throwable);
            }
            @Override
            public void onSuccess(ParseResponse parseResponse) {
                callback.onSuccess(parseResponse);
            }
        });
    }


    public void createRelation(String key, ParsePointer target,
                                      final AsyncCallback<ParseResponse> callback) {
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
                        callback.onFailure(throwable);
                    }
                    @Override
                    public void onSuccess(String s) {
                        try{
                            ParseResponse parseResponse = ParseResponse.parse(s);
                            callback.onSuccess(parseResponse);
                        } catch (Exception e) {
                            callback.onFailure(e);
                        }
                    }
                });


    }

    public void createRelation(String key, ParsePointer[] targets,
                                      final AsyncCallback<ParseResponse> callback) {
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
                        callback.onFailure(throwable);
                    }
                    @Override
                    public void onSuccess(String s) {
                        callback.onSuccess(ParseResponse.parse(s));
                    }
                });
    }

}
