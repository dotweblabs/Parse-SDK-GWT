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

import com.google.gwt.storage.client.Storage;
import elemental.client.Browser;

import java.util.logging.Logger;

/**
 *
 * Simple Parse Client for GWT
 *
 * @author Kerby Martino
 * @since 0-SNAPSHOT
 * @version 0-SNAPSHOT
 */
public class Parse {

    {
        initializeFromLocalStorage();
    }

    static final Logger logger = Logger.getLogger(Parse.class.getName());

    public static String SERVER_URL = "http://localhost:1337/parse";
    public static String CLASSES_URI = "classes/";
    public static String FILES_URI = "files/";

    /*
    Headers
     */
    public static String X_Parse_Application_Id = null;
    public static String X_Parse_REST_API_Key = null;
    public static String X_Parse_Javascript_Key = null;
    public static String X_Parse_Master_Key = null;
    public static String X_Parse_Session_Token = null;
    public static String X_Parse_Server_URL = null;

    public static void initializeFromLocalStorage() {
        Browser.getWindow().getConsole().log("Initializing Parse from Local Storage");
        if(X_Parse_Application_Id == null) {
            Storage storage = Storage.getLocalStorageIfSupported();
            if(storage != null) {
                X_Parse_Application_Id = storage.getItem("X-Parse-Application-Id");
            }
        }
        if(X_Parse_REST_API_Key == null) {
            Storage storage = Storage.getLocalStorageIfSupported();
            if(storage != null) {
                X_Parse_Application_Id = storage.getItem("X-Parse-REST-API-Key");
            }
        }
        if(X_Parse_Master_Key == null) {
            Storage storage = Storage.getLocalStorageIfSupported();
            if(storage != null) {
                X_Parse_Application_Id = storage.getItem("X-Parse-Master-Key");
            }
        }
        if(X_Parse_Session_Token == null || X_Parse_Session_Token == null) {
            Storage storage = Storage.getLocalStorageIfSupported();
            if(storage != null) {
                X_Parse_Application_Id = storage.getItem("X-Parse-Session-Token");
            }
        }
        if(X_Parse_Session_Token == null && X_Parse_Application_Id != null) {
            String key = "Parse/" + X_Parse_Application_Id + "/currentUser";
            Storage storage = Storage.getLocalStorageIfSupported();
            if(storage != null && storage.getItem(key) != null) {
                ParseObject user = ParseObject.parse("_User", storage.getItem(key));
            }
        }
    }

    public static void initialize(String appId, String restApiKey, String masterKey) {
        initialize(appId, restApiKey, null, masterKey);
    }

    public static void initialize(String appId, String restApiKey, String javascriptKey, String masterKey) {
        X_Parse_Application_Id = appId;
        X_Parse_REST_API_Key = restApiKey;
        X_Parse_Master_Key = masterKey;
        X_Parse_Javascript_Key = javascriptKey;
        if(!SERVER_URL.endsWith("/")) {
            SERVER_URL = SERVER_URL + "/";
        }
        Storage storage = Storage.getLocalStorageIfSupported();
        if(storage != null) {
            storage.setItem("X-Parse-Application-Id", appId);
            storage.setItem("X-Parse-REST-API-Key", restApiKey);
            storage.setItem("X-Parse-Master-Key", masterKey);
            if(storage.getItem("X-Parse-Session-Token") != null) {
                X_Parse_Session_Token = storage.getItem("X-Parse-Session-Token");
            }
            String key = "Parse/" + X_Parse_Application_Id + "/currentUser";
            if(storage.getItem(key) != null){
                ParseObject user = ParseObject.parse("_User", storage.getItem(key));
                String sessionToken = user.get("sessionToken").isString().stringValue();
                X_Parse_Session_Token = sessionToken;
            }
        }
    }

    public static void initialize(String appId, String restApiKey) {
        X_Parse_Application_Id = appId;
        X_Parse_REST_API_Key = restApiKey;
        if(!SERVER_URL.endsWith("/")) {
            SERVER_URL = SERVER_URL + "/";
        }
        Storage storage = Storage.getLocalStorageIfSupported();
        if(storage != null) {
            storage.setItem("X-Parse-Application-Id", appId);
            storage.setItem("X-Parse-REST-API-Key", restApiKey);
            if(storage.getItem("X-Parse-Session-Token") != null) {
                X_Parse_Session_Token = storage.getItem("X-Parse-Session-Token");
            }
            String key = "Parse/" + X_Parse_Application_Id + "/currentUser";
            if(storage.getItem(key) != null){
                ParseObject user = ParseObject.parse("_User", storage.getItem(key));
                String sessionToken = user.get("sessionToken").isString().stringValue();
                X_Parse_Session_Token = sessionToken;
            }
        }
    }

    public static void initializeSession(String appId, String restApiKey, String sessionToken) {
        X_Parse_Application_Id = appId;
        X_Parse_REST_API_Key = restApiKey;
        X_Parse_Session_Token = sessionToken;
        if (!SERVER_URL.endsWith("/")) {
            SERVER_URL = SERVER_URL + "/";
        }
        Storage storage = Storage.getLocalStorageIfSupported();
        if(storage != null) {
            storage.setItem("X-Parse-Application-Id", appId);
            storage.setItem("X-Parse-REST-API-Key", restApiKey);
            storage.setItem("X-Parse-Session-Token", sessionToken);
        }
    }

    public static void setServerUrl(String serverUrl) {
        SERVER_URL = serverUrl;
    }

    /**
     * Setup to connect to a different parse-server for authentication
     * and different parse-server for storing classes and functions
     *
     * @param authProxyServerUrl
     * @param serverUrl
     */
    public static void setServerUrl(String authProxyServerUrl, String serverUrl) {
        SERVER_URL = authProxyServerUrl;
        X_Parse_Server_URL  = serverUrl;
    }

    public static String getUri() {
        String s = SERVER_URL.replaceFirst("^(http://|https://)","");
        return s;
    }

}
