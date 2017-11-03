package org.parseplatform.client;

import com.dotweblabs.shape.client.Shape;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class ParseCloud {
    public static void run(String function, ParseObject object, final AsyncCallback<ParseResponse> callback) {
        Shape.post(Parse.SERVER_URL + "functions/" + function)
                .header("X-Parse-Application-Id", Parse.X_Parse_Application_Id)
                .header("X-Parse-REST-API-Key", Parse.X_Parse_REST_API_Key)
                .header("X-Parse-Master-Key", Parse.X_Parse_Master_Key)
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

    public static void run(String function, final AsyncCallback<ParseResponse> callback) {
        Shape.post(Parse.SERVER_URL + "functions/" + function)
                .header("X-Parse-Application-Id", Parse.X_Parse_Application_Id)
                .header("X-Parse-REST-API-Key", Parse.X_Parse_REST_API_Key)
                .header("X-Parse-Master-Key", Parse.X_Parse_Master_Key)
                .body(new JSONObject().toString())
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