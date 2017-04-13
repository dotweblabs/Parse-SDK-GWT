package com.parse.gwt.client.js.parse;

import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

/**
 * Created by Kerby on 4/14/2017.
 */
@JsType(isNative = true, namespace = JsPackage.GLOBAL, name = "Object")
public class ParseDate {
    @JsProperty(name = "__type")
    public String type;
    public String iso;
    @JsOverlay
    public static ParseDate create() {
        ParseDate parseDate = new ParseDate();
        parseDate.type = "Date";
        return parseDate;
    }
}