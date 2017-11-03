package org.parseplatform.client;

import com.dotweblabs.shape.client.HttpRequestException;
import com.dotweblabs.shape.client.Shape;
import com.google.gwt.http.client.URL;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.storage.client.Storage;
import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.Iterator;

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

    public void setUsername(String username) {
        putString("username", username);
    }

    public void setPassword(String password) {
        putString("password", password);
    }


    public void signup(final ParseAsyncCallback<ParseResponse> callback) {

        ParseObject user = this;

        Shape.post(Parse.SERVER_URL + "users")
                .header("X-Parse-Application-Id", Parse.X_Parse_Application_Id)
                .header("X-Parse-REST-API-Key", Parse.X_Parse_REST_API_Key)
                .header("X-Parse-Master-Key", Parse.X_Parse_Master_Key)
                .body(user.toString())
                .asJson(new AsyncCallback<String>() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        callback.onFailure(new ParseError(throwable));
                    }
                    @Override
                    public void onSuccess(String s) {
                        try {
                            ParseResponse response = ParseResponse.parse(s);
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
                .header("X-Parse-Application-Id", Parse.X_Parse_Application_Id)
                .header("X-Parse-REST-API-Key", Parse.X_Parse_REST_API_Key)
                .header("X-Parse-Master-Key", Parse.X_Parse_Master_Key)
                .header("X-Parse-Revocable-Session", "1")
                .asJson(new AsyncCallback<String>() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        callback.onFailure(new ParseError(throwable));
                    }
                    @Override
                    public void onSuccess(String s) {
                        try {
                            ParseResponse response = ParseResponse.parse(s);
                            if(response.get("sessionToken") != null) {
                                //createdAt, objectId, sessionToken
                                sessionToken = response.get("sessionToken").isString().stringValue();
                                Parse.X_Parse_Session_Token = sessionToken;
                                Storage storage = Storage.getLocalStorageIfSupported();
                                if(storage != null && remember) {
                                    String key = "Parse/" + Parse.X_Parse_Application_Id + "/currentUser";
                                    storage.setItem(key, response.toString());
                                }
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
    public static void login(String username, String password, final ParseAsyncCallback<ParseResponse> callback) {
        String param = "username=" + username + "&" + "password=" + password;
        Shape.get(Parse.SERVER_URL + "login?" + URL.encode(param))
                .header("X-Parse-Application-Id", Parse.X_Parse_Application_Id)
                .header("X-Parse-REST-API-Key", Parse.X_Parse_REST_API_Key)
                .header("X-Parse-Master-Key", Parse.X_Parse_Master_Key)
                .header("X-Parse-Revocable-Session", "1")
                .asJson(new AsyncCallback<String>() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        callback.onFailure(new ParseError(throwable));
                    }
                    @Override
                    public void onSuccess(String s) {
                        try {
                            ParseResponse response = ParseResponse.parse(s);
                            if(response.get("sessionToken") != null) {
                                //createdAt, objectId, sessionToken
                                sessionToken = response.get("sessionToken").isString().stringValue();
                                Parse.X_Parse_Session_Token = sessionToken;
                                Storage storage = Storage.getLocalStorageIfSupported();
                                if(storage != null) {
                                    String key = "Parse/" + Parse.X_Parse_Application_Id + "/currentUser";
                                    storage.setItem(key, response.toString());
                                }
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
    public void logout(final ParseAsyncCallback<ParseResponse> callback) {
        Shape.post(Parse.SERVER_URL + "logout")
                .header("X-Parse-Application-Id", Parse.X_Parse_Application_Id)
                .header("X-Parse-REST-API-Key", Parse.X_Parse_REST_API_Key)
                .header("X-Parse-Master-Key", Parse.X_Parse_Master_Key)
                .header("X-Parse-Session-Token", sessionToken)
                .asJson(new AsyncCallback<String>() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        callback.onFailure(new ParseError(throwable));
                    }
                    @Override
                    public void onSuccess(String s) {
                        try {
                            ParseResponse response = ParseResponse.parse(s);
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
                .header("X-Parse-Application-Id", Parse.X_Parse_Application_Id)
                .header("X-Parse-REST-API-Key", Parse.X_Parse_REST_API_Key)
                .header("X-Parse-Master-Key", Parse.X_Parse_Master_Key)
                .header("X-Parse-Session-Token", sessionToken)
                .asJson(new AsyncCallback<String>() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        callback.onFailure(new ParseError(throwable));
                    }
                    @Override
                    public void onSuccess(String s) {
                        ParseResponse response = ParseResponse.parse(s);
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

    public void requestPasswordReset(final ParseAsyncCallback<ParseResponse> callback) {
        Shape.post(Parse.SERVER_URL + "requestPasswordReset")
                .header("X-Parse-Application-Id", Parse.X_Parse_Application_Id)
                .header("X-Parse-REST-API-Key", Parse.X_Parse_REST_API_Key)
                .header("X-Parse-Master-Key", Parse.X_Parse_Master_Key)
                .header("X-Parse-Session-Token", sessionToken)
                .asJson(new AsyncCallback<String>() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        callback.onFailure(new ParseError(throwable));
                    }
                    @Override
                    public void onSuccess(String s) {
                        try {
                            ParseResponse response = ParseResponse.parse(s);
                            callback.onSuccess(response);
                        } catch (Exception e) {
                            callback.onFailure(new ParseError(e));
                        }
                    }
                });
    }
    public void requestPasswordReset(final String email, final ParseAsyncCallback<ParseResponse> callback) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("email", new JSONString(email));
        Shape.post(Parse.SERVER_URL + "requestPasswordReset")
                .header("X-Parse-Application-Id", Parse.X_Parse_Application_Id)
                .header("X-Parse-REST-API-Key", Parse.X_Parse_REST_API_Key)
                .header("X-Parse-Master-Key", Parse.X_Parse_Master_Key)
                .header("X-Parse-Session-Token", sessionToken)
                .body(jsonObject.toString())
                .asJson(new AsyncCallback<String>() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        callback.onFailure(new ParseError(throwable));
                    }
                    @Override
                    public void onSuccess(String s) {
                        try {
                            ParseResponse response = ParseResponse.parse(s);
                            callback.onSuccess(response);
                        } catch (Exception e) {
                            callback.onFailure(new ParseError(e));
                        }
                    }
                });
    }
    public void retrieveUser(String objectId ){}
    public ParseObject retrieveCurrentUser() {
        Storage storage = Storage.getLocalStorageIfSupported();
        if(storage != null) {
            String key = "Parse/" + Parse.X_Parse_Application_Id + "/currentUser";
            String user = storage.getItem(key);
            if(user != null){
                ParseObject parseObject = ParseObject.parse("_User", user);
                if(parseObject != null) {
                    Parse.X_Parse_Session_Token = parseObject.get("sessionToken").isString().stringValue();
                }
                return parseObject;
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
                        callback.onSuccess(ParseResponse.parse(s));
                    }
                });
    }
    public static void deleteUser(String objectId) {}
}
