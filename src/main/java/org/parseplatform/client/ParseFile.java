package org.parseplatform.client;

import com.dotweblabs.shape.client.Shape;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.Iterator;

public class ParseFile extends JSONObject {
    public static final String IMAGE_JPEG = "image/jpeg";
    public static final String IMAGE_PNG = "image/png";

    public static void upload(String base64file, String fileName, String mimeType, final AsyncCallback<ParseResponse> callback) {
//            String data = "{__ContentType : \"" + mimeType + "\", base64 : " + base64file + "}";
        JSONObject data = new JSONObject();
        data.put("__ContentType", new JSONString(mimeType));
        data.put("base64", new JSONString(base64file));
        data.put("_ApplicationId", new JSONString(Parse.X_Parse_Application_Id));
        if(Parse.X_Parse_Javascript_Key != null && !Parse.X_Parse_Javascript_Key.isEmpty()) {
            data.put("_JavaScriptKey", new JSONString(Parse.X_Parse_Javascript_Key));
        }
        if(Parse.X_Parse_Session_Token != null && !Parse.X_Parse_Session_Token.isEmpty()) {
            data.put("_SessionToken", new JSONString(Parse.X_Parse_Session_Token));
        }
        Shape.post(Parse.SERVER_URL + Parse.FILES_URI + fileName)
                .header("Content-Type", "plain-text")
                .body(data.toString())
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

}
