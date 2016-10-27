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

import com.dotweblabs.shape.client.HttpRequestException;
import com.dotweblabs.shape.client.Shape;
import com.google.gwt.http.client.URL;
import com.google.gwt.json.client.*;
import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * Simple Parse Client for GWT
 *
 * @author Kerby Martino
 * @since 0-SNAPSHOT
 * @version 0-SNAPSHOT
 */
public class Parse {

    public static class Cloud {
        public static void run(String function, ParseObject object, final AsyncCallback<ParseResponse> callback) {
            Shape.post(Parse.SERVER_URL + "functions/" + function)
                    .header("X-Parse-Application-Id", X_Parse_Application_Id)
                    .header("X-Parse-REST-API-Key", X_Parse_REST_API_Key)
                    .header("X-Parse-Master-Key", X_Parse_Master_Key)
                    .body(object.toString())
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

    public static class Users {
        public static String sessionToken = null;
        public static void signup(ParseObject user, final AsyncCallback<ParseResponse> callback) {
            Shape.post(Parse.SERVER_URL + "users")
                    .header("X-Parse-Application-Id", X_Parse_Application_Id)
                    .header("X-Parse-REST-API-Key", X_Parse_REST_API_Key)
                    .header("X-Parse-Master-Key", X_Parse_Master_Key)
                    .body(user.toString())
                    .asJson(new AsyncCallback<String>() {
                        @Override
                        public void onFailure(Throwable throwable) {
                            HttpRequestException ex = (HttpRequestException) throwable;
                            callback.onFailure(ex);
                        }
                        @Override
                        public void onSuccess(String s) {
                            ParseResponse response = ParseResponse.parse(s);
                            if(response.get("sessionToken") != null) {
                                //createdAt, objectId, sessionToken
                                sessionToken = response.get("sessionToken").isString().stringValue();
                                callback.onSuccess(response);
                            } else {
                                HttpRequestException ex
                                        = new HttpRequestException(response.getErrorMessage(), response.getErrorCode());
                                callback.onFailure(ex);
                            }
                        }
                    });
    }
        public static void login(String username, String password, final AsyncCallback<ParseResponse> callback) {
            String param = "username=" + username + "&" + "password=" + password;
            Shape.get(Parse.SERVER_URL + "login?" + URL.encode(param))
                    .header("X-Parse-Application-Id", X_Parse_Application_Id)
                    .header("X-Parse-REST-API-Key", X_Parse_REST_API_Key)
                    .header("X-Parse-Master-Key", X_Parse_Master_Key)
                    .header("X-Parse-Revocable-Session", "1")
                    .asJson(new AsyncCallback<String>() {
                        @Override
                        public void onFailure(Throwable throwable) {
                            HttpRequestException ex = (HttpRequestException) throwable;
                            callback.onFailure(ex);
                        }
                        @Override
                        public void onSuccess(String s) {
                            ParseResponse response = ParseResponse.parse(s);
                            if(response.get("sessionToken") != null) {
                                //createdAt, objectId, sessionToken
                                sessionToken = response.get("sessionToken").isString().stringValue();
                                Parse.X_Parse_Session_Token = sessionToken;
                                callback.onSuccess(response);
                            } else {
                                HttpRequestException ex
                                        = new HttpRequestException(response.getErrorMessage(), response.getErrorCode());
                                callback.onFailure(ex);
                            }
                        }
                    });
        }
        public static void logout(final AsyncCallback<ParseResponse> callback) {
            Shape.post(Parse.SERVER_URL + "logout")
                    .header("X-Parse-Application-Id", X_Parse_Application_Id)
                    .header("X-Parse-REST-API-Key", X_Parse_REST_API_Key)
                    .header("X-Parse-Master-Key", X_Parse_Master_Key)
                    .header("X-Parse-Session-Token", sessionToken)
                    .asJson(new AsyncCallback<String>() {
                        @Override
                        public void onFailure(Throwable throwable) {
                            callback.onFailure(throwable);
                        }
                        @Override
                        public void onSuccess(String s) {
                            try {
                                ParseResponse response = ParseResponse.parse(s);
                                sessionToken = null;
                                callback.onSuccess(response);
                            } catch (Exception e) {
                                callback.onFailure(e);
                            }
                        }
                    });
        }

        public static void become(String sessionToken, final AsyncCallback<ParseObject> callback){
            Shape.post(Parse.SERVER_URL + "sessions" + "/" + sessionToken)
                    .header("X-Parse-Application-Id", X_Parse_Application_Id)
                    .header("X-Parse-REST-API-Key", X_Parse_REST_API_Key)
                    .header("X-Parse-Master-Key", X_Parse_Master_Key)
                    .header("X-Parse-Session-Token", sessionToken)
                    .asJson(new AsyncCallback<String>() {
                        @Override
                        public void onFailure(Throwable throwable) {
                            callback.onFailure(throwable);
                        }
                        @Override
                        public void onSuccess(String s) {
                            System.out.println("Response: " + s);
                        }
                    });
        }

        public static void requestPasswordReset() {}
        public static void retrieveUser(String objectId ){}
        public static void retrieveCurrentUser() {}
        public static void updateUser(ParseObject update) {}
        public static void deleteUser(String objectId) {}
    }

    public static class Objects {
        public static ParseObject extend(String className) {
            return new ParseObject(className);
        }
        public static void create(final ParseObject object, final AsyncCallback<ParseResponse> callback) {
            Shape.post(Parse.SERVER_URL + Parse.CLASSES_URI + object.getClassName())
                    .header("X-Parse-Application-Id", X_Parse_Application_Id)
                    .header("X-Parse-REST-API-Key", X_Parse_REST_API_Key)
                    .header("X-Parse-Master-Key", X_Parse_Master_Key)
                    .header("X-Parse-Session-Token", X_Parse_Session_Token)
                    .body(object.toString())
                    .asJson(new AsyncCallback<String>() {
                        @Override
                        public void onFailure(Throwable throwable) {
                            HttpRequestException ex = (HttpRequestException) throwable;
                            callback.onFailure(ex);
                        }
                        @Override
                        public void onSuccess(String s) {
                            callback.onSuccess(ParseResponse.parse(s));
                        }
                    });
        }
        public static void retrieve(final ParseObject ref, final AsyncCallback<ParseObject> callback) {
            String objectId = ref.getObjectId();
            final String className = ref.getClassName();
            final String path = Parse.SERVER_URL + Parse.CLASSES_URI + className + "/" + objectId;
            Shape.get(path)
                    .header("X-Parse-Application-Id", X_Parse_Application_Id)
                    .header("X-Parse-REST-API-Key", X_Parse_REST_API_Key)
                    .header("X-Parse-Master-Key", X_Parse_Master_Key)
                    .header("X-Parse-Session-Token", X_Parse_Session_Token)
                    .asJson(new AsyncCallback<String>() {
                        @Override
                        public void onFailure(Throwable throwable) {
                            HttpRequestException ex = (HttpRequestException) throwable;
                            callback.onFailure(ex);
                        }
                        @Override
                        public void onSuccess(String s) {
                            callback.onSuccess(ParseObject.parse(className, s));
                        }
                    });
        }
        public static void delete(final ParseObject ref, final AsyncCallback<ParseResponse> callback) {
            String objectId = ref.getObjectId();
            final String className = ref.getClassName();
            final String path = Parse.SERVER_URL + Parse.CLASSES_URI + className + "/" + objectId;
            JSONObject payload = new JSONObject();
            JSONObject opponents = new JSONObject();
            opponents.put("__op", new JSONString("Delete"));
            payload.put("opponents", opponents);
            Shape.delete(path)
                    .header("X-Parse-Application-Id", X_Parse_Application_Id)
                    .header("X-Parse-REST-API-Key", X_Parse_REST_API_Key)
                    .header("X-Parse-Master-Key", X_Parse_Master_Key)
                    .header("X-Parse-Session-Token", X_Parse_Session_Token)
                    .body(payload.toString())
                    .asJson(new AsyncCallback<String>() {
                        @Override
                        public void onFailure(Throwable throwable) {
                            HttpRequestException ex = (HttpRequestException) throwable;
                            callback.onFailure(ex);
                        }
                        @Override
                        public void onSuccess(String s) {
                            callback.onSuccess(ParseResponse.parse(s));
                        }
                    });
        }
        public static void update(final ParseObject ref, final AsyncCallback<ParseResponse> callback) {
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
                    payload.put(key, value);
                }
            }
            Shape.put(path)
                    .header("X-Parse-Application-Id", X_Parse_Application_Id)
                    .header("X-Parse-REST-API-Key", X_Parse_REST_API_Key)
                    .header("X-Parse-Master-Key", X_Parse_Master_Key)
                    .header("X-Parse-Session-Token", X_Parse_Session_Token)
                    .body(payload.toString())
                    .asJson(new AsyncCallback<String>() {
                        @Override
                        public void onFailure(Throwable throwable) {
                            HttpRequestException ex = (HttpRequestException) throwable;
                            callback.onFailure(ex);
                        }
                        @Override
                        public void onSuccess(String s) {
                            callback.onSuccess(ParseResponse.parse(s));
                        }
                    });
        }
        public static void query(final Query query, final AsyncCallback<ParseResponse> callback) {
            String className = query.get("className").isString().stringValue();

        }

        public static void getRelation(ParseObject reference, String referenceKey, ParseObject referencee,
                                        final AsyncCallback<ParseResponse> callback) {


            Parse.Query query = Parse.Query.extend(referencee);
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

        public static void getRelation(ParseObject reference, String referenceKey, ParseObject referencee,
                       String filterField, String filterValue, final AsyncCallback<ParseResponse> callback) {
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
            Parse.Query query = Parse.Query.extend(referencee);
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

        public static void getRelationOrderAscending(ParseObject reference, String referenceKey, ParseObject referencee,
                                       final AsyncCallback<ParseResponse> callback) {
            Parse.Query query = Parse.Query.extend(referencee);
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


        public static void createRelation(ParseObject object, String key, ParsePointer target,
                                          final AsyncCallback<ParseResponse> callback) {
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
                    .header("X-Parse-Application-Id", X_Parse_Application_Id)
                    .header("X-Parse-REST-API-Key", X_Parse_REST_API_Key)
                    .header("X-Parse-Master-Key", X_Parse_Master_Key)
                    .header("X-Parse-Session-Token", X_Parse_Session_Token)
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

        public static void createRelation(ParseObject object, String key, ParsePointer[] targets,
                                          final AsyncCallback<ParseResponse> callback) {
            String objectId = object.getObjectId();
            final String className = object.getClassName();
            final String path = Parse.SERVER_URL + Parse.CLASSES_URI + className + "/" + objectId;
            JSONObject payload = new JSONObject();
            JSONArray objects = new JSONArray();
            int i = 0;
            for(ParsePointer p :Arrays.asList(targets)) {
                objects.set(i, p);
                i++;
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("__op", new JSONString("AddRelation"));
            jsonObject.put("objects", objects);
            payload.put(key, jsonObject);
            Shape.put(path)
                    .header("X-Parse-Application-Id", X_Parse_Application_Id)
                    .header("X-Parse-REST-API-Key", X_Parse_REST_API_Key)
                    .header("X-Parse-Master-Key", X_Parse_Master_Key)
                    .header("X-Parse-Session-Token", X_Parse_Session_Token)
                    .body(payload.toString())
                    .asJson(new AsyncCallback<String>() {
                        @Override
                        public void onFailure(Throwable throwable) {
                            HttpRequestException ex = (HttpRequestException) throwable;
                            callback.onFailure(ex);
                        }
                        @Override
                        public void onSuccess(String s) {
                            callback.onSuccess(ParseResponse.parse(s));
                        }
                    });


        }
    }

    public static class Query extends JSONObject {

        private Where where;
        private Order order;
        private int limit;
        private int skip;


        public Query() {

        }

        public Query(ParseObject object) {
            setClassName(object.getClassName());
        }

        public static Query extend(ParseObject object) {
            return new Query(object);
        }

        private String getClassName() {
            return get("className").isString().stringValue();
        }

        private void setClassName(String className) {
            put("className", new JSONString(className));
        }

        public Query order(Order order) {
            this.order = order;
            put("order", order);
            return this;
        }

        public Query where(Where where){
            this.where = where;
            put("where", where);
            return this;
        }

        public Query limit(int limit){
            this.limit = limit;
            put("limit", new JSONNumber(limit));
            return this;
        }

        public Query skip(int skip){
            this.skip = skip;
            put("skip", new JSONNumber(skip));
            return this;
        }

        public void get(String objectId, final AsyncCallback<ParseObject> response) {
            ParseObject ref = new ParseObject(getClassName());
            ref.setObjectId(objectId);
            Parse.Objects.retrieve(ref, new AsyncCallback<ParseObject>() {
                @Override
                public void onFailure(Throwable throwable) {
                    response.onFailure(throwable);
                }

                @Override
                public void onSuccess(ParseObject parseObject) {
                    response.onSuccess(parseObject);
                }
            });
        }

        public void find(final AsyncCallback<ParseResponse> callback){
            String className = getClassName();
            String where = "";
            String order = "";
            String limit = "";
            String skip = "";
            if(get("where") != null) {
                JSONObject jsonWhere = get("where").isObject();
                where = jsonWhere.toString();
                assert where != null;
                where = "?where=" + URL.encode(where);
            }
            if(get("order") != null) {
                JSONArray jsonOrder = get("order").isArray();
                assert order != null;
                order = where.isEmpty() ? "?order=" : "&order=";
                for(int i=0;i<jsonOrder.size();i++){
                    order = order + jsonOrder.get(i).isString().stringValue() + ",";
                }
                order = order.substring(0, order.lastIndexOf(","));
            }
            if(get("limit") != null) {
                int l = (int) get("limit").isNumber().doubleValue();
                if(order.isEmpty() && !where.isEmpty()) {
                    limit = "&limit=" + l;
                } else if(!order.isEmpty() && where.isEmpty()) {
                    limit = "&limit=" + l;
                } else if(order.isEmpty() && where.isEmpty()) {
                    limit = "?limit=" + l;
                }
            }
            if(get("skip") != null) {
                int s = (int) get("skip").isNumber().doubleValue();
                if(!where.isEmpty() && order.isEmpty() && limit.isEmpty())  {
                    skip = "&skip=" + s;
                } else if(where.isEmpty() && order.isEmpty() && !limit.isEmpty()) {
                    skip = "&skip=" + s;
                } else if(where.isEmpty() && !order.isEmpty() && limit.isEmpty()) {
                    skip = "&skip=" + s;
                } else if(where.isEmpty() && order.isEmpty() && limit.isEmpty()) {
                    skip = "?skip=" + s;
                } else {
                    skip = "&skip=" + s;
                }
            }
            Shape.get(Parse.SERVER_URL + Parse.CLASSES_URI + className + where + order + limit + skip)
                    .header("X-Parse-Application-Id", X_Parse_Application_Id)
                    .header("X-Parse-REST-API-Key", X_Parse_REST_API_Key)
                    .header("X-Parse-Master-Key", X_Parse_Master_Key)
                    .header("X-Parse-Session-Token", X_Parse_Session_Token)
                    .asJson(new AsyncCallback<String>() {
                        @Override
                        public void onFailure(Throwable throwable) {
                            callback.onFailure(throwable);
                        }
                        @Override
                        public void onSuccess(String s) {
                            try {
                                ParseResponse resp = ParseResponse.parse(s);
                                callback.onSuccess(resp);
                            } catch (Exception e) {
                                callback.onFailure(e);
                            }
                        }
                    });
        }

        public Subscription subscribe() {
            Subscription subscription = new Subscription(getClassName(), where);
            return subscription;
        }

    }

    public static String SERVER_URL = "http://localhost:1337/parse";
    public static String CLASSES_URI = "classes/";

    /*
    Headers
     */
    public static String X_Parse_Application_Id = null;
    public static String X_Parse_REST_API_Key = null;
    public static String X_Parse_Master_Key = null;
    public static String X_Parse_Session_Token = null;

    public static String _appId = null;;
    public static String _javascriptKey = null;;
    public static String _restApiKey = null;;
    public static String _masterKey = null;;
    public static String _sessionToken = null;

    public static void initialize(String appId, String restApiKey, String masterKey) {
        _appId = appId;
        _restApiKey = restApiKey;
        _masterKey = masterKey;
        X_Parse_Application_Id = appId;
        X_Parse_REST_API_Key = restApiKey;
        X_Parse_Master_Key = masterKey;
        if(!SERVER_URL.endsWith("/")) {
            SERVER_URL = SERVER_URL + "/";
        }
    }

    public static void initialize(String appId, String restApiKey) {
        _appId = appId;
        _restApiKey = restApiKey;
        X_Parse_Application_Id = appId;
        X_Parse_REST_API_Key = restApiKey;
        if(!SERVER_URL.endsWith("/")) {
            SERVER_URL = SERVER_URL + "/";
        }
    }

    public static void initializeSession(String appId, String restApiKey, String sessionToken) {
        _appId = appId;
        _restApiKey = restApiKey;
        _sessionToken = sessionToken;
        X_Parse_Application_Id = appId;
        X_Parse_REST_API_Key = restApiKey;
        X_Parse_Session_Token = sessionToken;
        if (!SERVER_URL.endsWith("/")) {
            SERVER_URL = SERVER_URL + "/";
        }
    }

    public static String getUri() {
        String s = SERVER_URL.replaceFirst("^(http://|https://)","");
        return s;
    }

}
