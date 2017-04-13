package com.parse.gwt.client.js.parse;

import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

/**
 * Created by Kerby on 4/14/2017.
 */
@JsType(isNative = true, namespace = JsPackage.GLOBAL, name = "Object")
public class ParsePointer {
    @JsProperty(name = "__type")
    public String type;
    public String className;
    public String objectId;
    @JsOverlay
    public static ParsePointer create() { // Should be native?
        ParsePointer parsePointer = new ParsePointer();
        parsePointer.type = "Pointer";
        return parsePointer;
    }
}