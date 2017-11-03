package org.parseplatform.client;

import com.dotweblabs.shape.client.Shape;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class ParseConfig {
    public static void get(final ParseAsyncCallback<Config> callback) {
        Shape.get(Parse.SERVER_URL + "/config")
                .header("X-Parse-Application-Id", Parse.X_Parse_Application_Id)
                .header("X-Parse-REST-API-Key", Parse.X_Parse_REST_API_Key)
                .header("X-Parse-Master-Key", Parse.X_Parse_Master_Key)
                .asJson(new AsyncCallback<String>() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        callback.onFailure(new ParseError(throwable));
                    }
                    @Override
                    public void onSuccess(String s) {
                        try {
                            org.parseplatform.client.Config config = org.parseplatform.client.Config.fromJSON(s);
                            callback.onSuccess(config);
                        } catch (Exception e){
                            callback.onFailure(new ParseError(e));
                        }
                    }
                });
    }
    public static void update(String parameter, JSONValue value, final ParseAsyncCallback<org.parseplatform.client.Config> callback) {
        JSONObject params = new JSONObject();
        params.put(parameter, value);
        JSONObject body = new JSONObject();
        body.put("params", params);
        Shape.put(Parse.SERVER_URL + "/config")
                .header("X-Parse-Application-Id", Parse.X_Parse_Application_Id)
                .header("X-Parse-REST-API-Key", Parse.X_Parse_REST_API_Key)
                .header("X-Parse-Master-Key", Parse.X_Parse_Master_Key)
                .body(body.toString())
                .asJson(new AsyncCallback<String>() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        callback.onFailure(new ParseError(throwable));
                    }
                    @Override
                    public void onSuccess(String s) {
                        try {
                            org.parseplatform.client.Config config = org.parseplatform.client.Config.fromJSON(s);
                            callback.onSuccess(config);
                        } catch (Exception e){
                            callback.onFailure(new ParseError(e));
                        }
                    }
                });
    }
}
