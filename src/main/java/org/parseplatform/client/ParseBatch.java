package org.parseplatform.client;

import com.dotweblabs.shape.client.Shape;
import com.google.gwt.json.client.*;
import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class ParseBatch extends JSONObject {

    private ParseObject[] objects;

    public ParseBatch(ParseObject[] objects) {
        this.objects = objects;
    }

    public void create(final ParseAsyncCallback<ParseResponse> callback) {
        List<ParseObject> parseObjectList = Arrays.asList(objects);
        JSONArray requests = new JSONArray();
        int i = 0;
        for(ParseObject parseObject : parseObjectList) {
            JSONObject requestObject = new JSONObject();
            requestObject.put("method", new JSONString("POST"));
            requestObject.put("path", new JSONString("/parse/classes/" + parseObject.getClassName()));
            requestObject.put("body", parseObject);
            requests.set(i, requestObject);
            i++;
        }
        JSONObject payload = new JSONObject();
        payload.put("requests", requests);
        Shape.post(Parse.SERVER_URL + Parse.BATCH_URI)
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
                            callback.onFailure(new ParseError(e));
                        }
                    }
                });
    }
}
