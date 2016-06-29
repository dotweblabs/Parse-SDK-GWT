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
package com.dotweblabs.gwt.client;

import com.dotweblabs.gwt.client.js.JsonConverter;
import com.dotweblabs.shape.client.HttpRequestException;
import com.dotweblabs.shape.client.Shape;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.Iterator;

/**
 *
 * Simple Parse Client for GWT
 *
 * @author Kerby Martino
 * @since 0-SNAPSHOT
 * @version 0-SNAPSHOT
 */
public class Parse {

    public static class Objects {
        public static void create(final ParseObject object, final AsyncCallback<ParseResponse> callback) {
            Shape.post(Parse.SERVER_URL + Parse.CLASSES_URI + object.getClassName())
                    .header("X-Parse-Application-Id", X_Parse_Application_Id)
                    .header("X-Parse-REST-API-Key", X_Parse_REST_API_Key)
                    .header("X-Parse-Master-Key", X_Parse_Master_Key)
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
            Shape.put(path)
                    .header("X-Parse-Application-Id", X_Parse_Application_Id)
                    .header("X-Parse-REST-API-Key", X_Parse_REST_API_Key)
                    .header("X-Parse-Master-Key", X_Parse_Master_Key)
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

        }
    }

    public static String SERVER_URL = "https://parseapi.back4app.com/";
    public static String CLASSES_URI = "classes/";

    /*
    Headers
     */
    public static String X_Parse_Application_Id = null;
    public static String X_Parse_REST_API_Key = null;
    public static String X_Parse_Master_Key = null;

    public static String _appId = null;;
    public static String _javascriptKey = null;;
    public static String _restApiKey = null;;
    public static String _masterKey = null;;

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


}
