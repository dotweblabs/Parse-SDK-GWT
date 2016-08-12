package com.parse.gwt.client;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;

/**
 *
 * Parse Pointer object
 *
 * @author Kerby Martino
 * @since 0-SNAPSHOT
 * @version 0-SNAPSHOT
 */
public class ParsePointer extends JSONObject {
    public ParsePointer() {}
    public ParsePointer(ParseObject reference) {
        put("__type", new JSONString("Pointer"));
        put("className", new JSONString(reference.getClassName()));
        put("objectId", new JSONString(reference.getObjectId()));
    }
    public static ParsePointer parse(String className, String objectId) {
        ParsePointer pointer = new ParsePointer();
        pointer.put("__type", new JSONString("Pointer"));
        pointer.put("className", new JSONString(className));
        pointer.put("objectId", new JSONString(objectId));
        return pointer;
    }
    public static ParsePointer clone(String className, JSONObject reference) {
        String objectId = reference.get("objectId").isString().stringValue();
        ParsePointer pointer = new ParsePointer();
        pointer.put("__type", new JSONString("Pointer"));
        pointer.put("className", new JSONString(className));
        pointer.put("objectId", new JSONString(objectId));
        return pointer;
    }
    public static ParsePointer clone(JSONObject reference) {
        String className = reference.get("className").isString().stringValue();
        String objectId = reference.get("objectId").isString().stringValue();
        ParsePointer pointer = new ParsePointer();
        pointer.put("__type", new JSONString("Pointer"));
        pointer.put("className", new JSONString(className));
        pointer.put("objectId", new JSONString(objectId));
        return pointer;
    }
    public String getReferenceObjectId() {
        return get("objectId").isString().stringValue();
    }
}
