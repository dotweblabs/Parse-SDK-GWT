package com.parse.gwt.client;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;

/**
 *
 * Parse Relation object
 *
 * @author Kerby Martino
 * @since 0-SNAPSHOT
 * @version 0-SNAPSHOT
 */
public class ParseRelation extends JSONObject {
    public ParseRelation() {}
    public ParseRelation(String className) {
        put("__type", new JSONString("Relation"));
        put("className", new JSONString(className));
    }

    public ParseRelation(ParseObject reference) {
        put("__type", new JSONString("Relation"));
        put("className", new JSONString(reference.getClassName()));
    }
    public static ParseRelation parse(String className) {
        ParseRelation pointer = new ParseRelation();
        pointer.put("__type", new JSONString("Relation"));
        pointer.put("className", new JSONString(className));
        return pointer;
    }
    public static ParseRelation clone(String className) {
        ParseRelation pointer = new ParseRelation();
        pointer.put("__type", new JSONString("Relation"));
        pointer.put("className", new JSONString(className));
        return pointer;
    }
    public static ParseRelation clone(JSONObject reference) {
        String className = reference.get("className").isString().stringValue();
        ParseRelation pointer = new ParseRelation();
        pointer.put("__type", new JSONString("Relation"));
        pointer.put("className", new JSONString(className));
        return pointer;
    }
}
