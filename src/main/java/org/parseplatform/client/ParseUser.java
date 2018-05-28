package org.parseplatform.client;

/*
File:  ParseUser.java
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

import com.dotweblabs.shape.client.HttpRequestException;
import com.dotweblabs.shape.client.Shape;
import com.google.gwt.http.client.URL;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.storage.client.Storage;
import com.google.gwt.user.client.rpc.AsyncCallback;
import elemental.client.Browser;
import org.parseplatform.client.util.UUID;
import java.util.Iterator;

/**
 * @author Kerby Martino
 * @since 0-SNAPSHOT
 * @version 0-SNAPSHOT
 */
public class ParseUser extends ParseObject {

    public static String sessionToken = null;

    public ParseUser() {
        setClassName("_User");
    }

    public ParseUser(String usename, String password) {
        setClassName("_User");
        setUsername(usename);
        setPassword(password);
    }

    public ParseUser(String json) {
        super(ParseConstants.CLASSNAME_USER, json);
    }

    public void setUsername(String username) {
        putString("username", username);
    }

    public void setPassword(String password) {
        putString("password", password);
    }


    public void signup(final ParseAsyncCallback<ParseResponse> callback) {

        ParseObject user = this;

        Shape.post(Parse.SERVER_URL + "users")
                .header(ParseConstants.FIELD_REST_APP_ID, Parse.X_Parse_Application_Id)
                .header(ParseConstants.FIELD_REST_REST_API_KEY, Parse.X_Parse_REST_API_Key)
                .header(ParseConstants.FIELD_REST_MASTER_KEY, Parse.X_Parse_Master_Key)
                .body(user.toString())
                .asJson(new AsyncCallback<String>() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        callback.onFailure(new ParseError(throwable));
                    }
                    @Override
                    public void onSuccess(String s) {
                        try {
                            ParseResponse response = new ParseResponse(s);
                            if(response.get("sessionToken") != null) {
                                //createdAt, objectId, sessionToken
                                sessionToken = response.get("sessionToken").isString().stringValue();
                                callback.onSuccess(response);
                            } else {
                                HttpRequestException ex
                                        = new HttpRequestException(response.getErrorMessage(), response.getErrorCode());
                                callback.onFailure(new ParseError(ex));
                            }
                        } catch (Exception e) {
                            callback.onFailure(new ParseError(e));
                        }
                    }
                });
    }

    public void login(boolean remember, final ParseAsyncCallback<ParseResponse> callback) {
        String username = getString("username");
        String password = getString("password");
        String param = "username=" + username + "&" + "password=" + password;
        Shape.get(Parse.SERVER_URL + "login?" + URL.encode(param))
                .header(ParseConstants.FIELD_REST_APP_ID, Parse.X_Parse_Application_Id)
                .header(ParseConstants.FIELD_REST_REST_API_KEY, Parse.X_Parse_REST_API_Key)
                .header(ParseConstants.FIELD_REST_MASTER_KEY, Parse.X_Parse_Master_Key)
                .header("X-Parse-Revocable-Session", "1")
                .asJson(new AsyncCallback<String>() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        Browser.getWindow().getConsole().log("Parse login exception " + throwable.getMessage());
                        callback.onFailure(new ParseError(throwable));
                    }
                    @Override
                    public void onSuccess(String s) {
                        if(s != null && !s.isEmpty()) {
                            ParseResponse response = new ParseResponse(s);
                            if(response.isError()) {
                                callback.onFailure(new ParseError(response.getErrorCode(), response.getErrorMessage()));
                            } else {
                                if(response.get("sessionToken") != null) {
                                    //createdAt, objectId, sessionToken
                                    sessionToken = response.get("sessionToken").isString().stringValue();
                                    Parse.X_Parse_Session_Token = sessionToken;
                                    Storage storage = Storage.getLocalStorageIfSupported();
                                    if(storage != null && remember) {
                                        String key = "Parse/" + Parse.X_Parse_Application_Id + "/currentUser";
                                        storage.setItem(key, response.toString());
                                    }
                                }
                                callback.onSuccess(response);
                            }
                        }
                    }
                });
    }

    public void login(String username, String password, final ParseAsyncCallback<ParseResponse> callback) {
        putString(ParseConstants.FIELD_USERNAME, username);
        putString(ParseConstants.FIELD_PASSWORD, password);
        login(true, new ParseAsyncCallback<ParseResponse>() {
            @Override
            public void onFailure(ParseError error) {
                callback.onFailure(error);
            }
            @Override
            public void onSuccess(ParseResponse var1) {
                callback.onSuccess(var1);
            }
        });
    }

    public static void anonymousLogin(final ParseAsyncCallback<ParseResponse> callback) {
        JSONObject payload = new JSONObject();
        JSONObject authData = new JSONObject();
        JSONObject id = new JSONObject();
        id.put("id", new JSONString(UUID.uuid().replace("-","")));
        authData.put("anonymous", id);
        payload.put("authData", authData);
        Shape.post(Parse.SERVER_URL + "/users")
                .header(ParseConstants.FIELD_REST_APP_ID, Parse.X_Parse_Application_Id)
                .header(ParseConstants.FIELD_REST_REST_API_KEY, Parse.X_Parse_REST_API_Key)
                .header(ParseConstants.FIELD_REST_MASTER_KEY, Parse.X_Parse_Master_Key)
                .header("X-Parse-Revocable-Session", "1")
                .body(payload.toString())
                .asJson(new AsyncCallback<String>() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        callback.onFailure(new ParseError(throwable));
                    }
                    @Override
                    public void onSuccess(String s) {
                        if(s != null && !s.isEmpty()) {
                            ParseResponse response = new ParseResponse(s);
                            if(response.isError()) {
                                callback.onFailure(new ParseError(response.getErrorCode(), response.getErrorMessage()));
                            } else {
                                if(response.get("sessionToken") != null) {
                                    //createdAt, objectId, sessionToken
                                    sessionToken = response.get("sessionToken").isString().stringValue();
                                    Parse.X_Parse_Session_Token = sessionToken;
                                    Storage storage = Storage.getLocalStorageIfSupported();
                                    if(storage != null) {
                                        String key = "Parse/" + Parse.X_Parse_Application_Id + "/currentUser";
                                        storage.setItem(key, response.toString());
                                    }
                                }
                                callback.onSuccess(response);
                            }
                        }
                    }
                });
    }

    public static void logout(final ParseAsyncCallback<ParseResponse> callback) {
        Shape.post(Parse.SERVER_URL + "logout")
                .header(ParseConstants.FIELD_REST_APP_ID, Parse.X_Parse_Application_Id)
                .header(ParseConstants.FIELD_REST_REST_API_KEY, Parse.X_Parse_REST_API_Key)
                .header(ParseConstants.FIELD_REST_MASTER_KEY, Parse.X_Parse_Master_Key)
                .header(ParseConstants.FIELD_REST_SESSION_TOKEN, Parse.X_Parse_Session_Token)
                .asJson(new AsyncCallback<String>() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        callback.onFailure(new ParseError(throwable));
                    }
                    @Override
                    public void onSuccess(String s) {
                        try {
                            ParseResponse response = new ParseResponse(s);
                            Parse.X_Parse_Session_Token = null;
                            sessionToken = null;
                            String userKey = "Parse/" + Parse.X_Parse_Application_Id + "/currentUser";
                            Storage storage = Storage.getLocalStorageIfSupported();
                            if(storage != null && storage.getItem(userKey) != null) {
                                storage.removeItem(userKey);
                            }
                            callback.onSuccess(response);
                        } catch (Exception e) {
                            callback.onFailure(new ParseError(e));
                        }
                    }
                });
    }

    public static void become(String sessionToken, final ParseAsyncCallback<ParseObject> callback){
        Shape.get(Parse.SERVER_URL + "/users/me")
                .header(ParseConstants.FIELD_REST_APP_ID, Parse.X_Parse_Application_Id)
                .header(ParseConstants.FIELD_REST_REST_API_KEY, Parse.X_Parse_REST_API_Key)
                .header(ParseConstants.FIELD_REST_MASTER_KEY, Parse.X_Parse_Master_Key)
                .header(ParseConstants.FIELD_REST_SESSION_TOKEN, Parse.X_Parse_Session_Token)
                .asJson(new AsyncCallback<String>() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        callback.onFailure(new ParseError(throwable));
                    }
                    @Override
                    public void onSuccess(String s) {
                        ParseResponse response = new ParseResponse(s);
                        if(response.get("sessionToken") != null) {
                            String sessionToken = response.get("sessionToken").isString().stringValue();
                            Parse.X_Parse_Session_Token = sessionToken;
                            callback.onSuccess(response.asParseObject("_User"));
                        } else {
                            HttpRequestException ex
                                    = new HttpRequestException(response.getErrorMessage(), response.getErrorCode());
                            callback.onFailure(new ParseError(ex));
                        }
                    }
                });
    }

    public static void requestPasswordReset(final ParseAsyncCallback<ParseResponse> callback) {
        Shape.post(Parse.SERVER_URL + "requestPasswordReset")
                .header(ParseConstants.FIELD_REST_APP_ID, Parse.X_Parse_Application_Id)
                .header(ParseConstants.FIELD_REST_REST_API_KEY, Parse.X_Parse_REST_API_Key)
                .header(ParseConstants.FIELD_REST_MASTER_KEY, Parse.X_Parse_Master_Key)
                .header(ParseConstants.FIELD_REST_SESSION_TOKEN, Parse.X_Parse_Session_Token)
                .asJson(new AsyncCallback<String>() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        callback.onFailure(new ParseError(throwable));
                    }
                    @Override
                    public void onSuccess(String s) {
                        try {
                            ParseResponse response = new ParseResponse(s);
                            callback.onSuccess(response);
                        } catch (Exception e) {
                            callback.onFailure(new ParseError(e));
                        }
                    }
                });
    }

    public static void requestPasswordReset(final String email, final ParseAsyncCallback<ParseResponse> callback) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("email", new JSONString(email));
        Shape.post(Parse.SERVER_URL + "requestPasswordReset")
                .header(ParseConstants.FIELD_REST_APP_ID, Parse.X_Parse_Application_Id)
                .header(ParseConstants.FIELD_REST_REST_API_KEY, Parse.X_Parse_REST_API_Key)
                .header(ParseConstants.FIELD_REST_MASTER_KEY, Parse.X_Parse_Master_Key)
                .header(ParseConstants.FIELD_REST_SESSION_TOKEN, Parse.X_Parse_Session_Token)
                .body(jsonObject.toString())
                .asJson(new AsyncCallback<String>() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        callback.onFailure(new ParseError(throwable));
                    }
                    @Override
                    public void onSuccess(String s) {
                        try {
                            ParseResponse response = new ParseResponse(s);
                            callback.onSuccess(response);
                        } catch (Exception e) {
                            callback.onFailure(new ParseError(e));
                        }
                    }
                });
    }

    public void retrieveUser(String objectId ){}

    public static ParseUser retrieveCurrentUser() {
        Storage storage = Storage.getLocalStorageIfSupported();
        if(storage != null) {
            String key = "Parse/" + Parse.X_Parse_Application_Id + "/currentUser";
            String user = storage.getItem(key);
            if(user != null){
                ParseUser parseUser = new ParseUser(user);
                if(parseUser != null) {
                    Parse.X_Parse_Session_Token = parseUser.getString("sessionToken");
                }
                return parseUser;
            }
        }
        return null;
    }

    public void updateUser(ParseObject ref, final ParseAsyncCallback<ParseResponse> callback) {
        String objectId = ref.getObjectId();
        final String className = ref.getClassName();
        final String path = Parse.SERVER_URL + "/users/" + objectId;
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
                .header(ParseConstants.FIELD_REST_APP_ID, Parse.X_Parse_Application_Id)
                .header(ParseConstants.FIELD_REST_REST_API_KEY, Parse.X_Parse_REST_API_Key)
                .header(ParseConstants.FIELD_REST_MASTER_KEY, Parse.X_Parse_Master_Key)
                .header(ParseConstants.FIELD_REST_SESSION_TOKEN, Parse.X_Parse_Session_Token)
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

    public static void deleteUser(String objectId) {}

}
