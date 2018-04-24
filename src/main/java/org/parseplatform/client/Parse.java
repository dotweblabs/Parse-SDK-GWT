package org.parseplatform.client;

/*
File:  Parse.java
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

import com.google.gwt.storage.client.Storage;
import elemental.client.Browser;
import java.util.logging.Logger;

/**
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
    public static String BATCH_URI = "batch";
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
        initialize(SERVER_URL, appId, restApiKey, null, masterKey);
    }

    public static void initialize(String path, String appId, String restApiKey, String masterKey) {
        initialize(path, appId, restApiKey, null, masterKey);
    }

    public static void initialize(String path, String appId, String restApiKey, String javascriptKey, String masterKey) {
        X_Parse_Application_Id = appId;
        X_Parse_REST_API_Key = restApiKey;
        if(masterKey != null) {
            X_Parse_Master_Key = masterKey;
        }
        X_Parse_Javascript_Key = javascriptKey;
        SERVER_URL = path;
        if(!SERVER_URL.endsWith("/")) {
            SERVER_URL = SERVER_URL + "/";
        }
        Storage storage = Storage.getLocalStorageIfSupported();
        if(storage != null) {
            storage.setItem("X-Parse-Application-Id", appId);
            storage.setItem("X-Parse-REST-API-Key", restApiKey);
            if(masterKey != null) {
                storage.setItem("X-Parse-Master-Key", masterKey);
            }
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
