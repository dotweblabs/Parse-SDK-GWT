package org.parseplatform.client;

import com.dotweblabs.shape.client.HttpRequestException;
import com.dotweblabs.shape.client.Shape;
import com.google.gwt.http.client.URL;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.storage.client.Storage;
import com.google.gwt.user.client.rpc.AsyncCallback;
import elemental.client.Browser;

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
    public void login(final boolean remember, final ParseAsyncCallback<ParseResponse> callback) {
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

    public void requestPasswordReset(final ParseAsyncCallback<ParseResponse> callback) {
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
    public void requestPasswordReset(final String email, final ParseAsyncCallback<ParseResponse> callback) {
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
