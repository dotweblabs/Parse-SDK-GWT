package com.parse.gwt.client.autobean;

import com.google.gwt.core.client.GWT;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.parse.gwt.client.autobean.serializers.ParseDateAutoBeanSerializer;
import com.parse.gwt.client.autobean.serializers.ParseFileAutoBeanSerializer;
import com.parse.gwt.client.autobean.serializers.ParsePointerAutoBeanSerializer;

/**
 * Helper methods for Parse
 */
public class AutoParse {
    public static ParsePointer createPointer(String className, String objectId) {
        ParsePointerAutoBeanSerializer serializer = GWT.create(ParsePointerAutoBeanSerializer.class);
        ParsePointer parsePointer = serializer.make();
        parsePointer.setType("Pointer");
        parsePointer.setClassName(className);
        parsePointer.setObjectId(objectId);
        return parsePointer;
    }
    public static JSONValue createPointerJson(String className, String objectId) {
        ParsePointerAutoBeanSerializer serializer = GWT.create(ParsePointerAutoBeanSerializer.class);
        ParsePointer parsePointer = serializer.make();
        parsePointer.setType("Pointer");
        parsePointer.setClassName(className);
        parsePointer.setObjectId(objectId);
        String jsonPointer = serializer.encodeData(parsePointer);
        return JSONParser.parseStrict(jsonPointer);
    }
    public static ParseFile createFile(String name, String url) {
        ParseFileAutoBeanSerializer serializer = GWT.create(ParseFileAutoBeanSerializer.class);
        ParseFile parseFile = serializer.make();
        parseFile.set__type("File");
        parseFile.setName(name);
        parseFile.setUrl(url);
        return parseFile;
    }
    public static ParseDate createDate(String iso) {
        ParseDateAutoBeanSerializer serializer = GWT.create(ParseDateAutoBeanSerializer.class);
        ParseDate date = serializer.make();
        date.set__type("Date");
        date.setIso(iso);
        return date;
    }
}
