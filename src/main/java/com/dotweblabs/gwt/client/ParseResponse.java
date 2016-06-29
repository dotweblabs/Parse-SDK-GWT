package com.dotweblabs.gwt.client;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by paperspace on 6/29/2016.
 */
public class ParseResponse extends JSONObject {
    public static ParseResponse parse(String json) {
        JSONObject jsonObject = (JSONObject) JSONParser.parseStrict(json);
        return ParseResponse.clone(jsonObject);
    }
    public static ParseResponse clone(JSONObject jsonObject) {
        ParseResponse response = null;
        Iterator<String> it = jsonObject.keySet().iterator();
        while(it.hasNext()) {
            if(response == null) {
                response = new ParseResponse();
            }
            String key = it.next();
            JSONValue value = jsonObject.get(key);
            response.put(key, value);
        }
        return response;
    }
    public String getObjectId() {
        if(get("objectId").isString() != null) {
            return get("objectId").isString().stringValue();
        }
        return null;
    }
    public String getCreatedAt() {
        if(get("createdAt").isString() != null) {
            return get("createdAt").isString().stringValue();
        }
        return null;
    }
    public String getUpdatedAt() {
        if(get("updatedAt").isString() != null) {
            return get("updatedAt").isString().stringValue();
        }
        return null;
    }
}
