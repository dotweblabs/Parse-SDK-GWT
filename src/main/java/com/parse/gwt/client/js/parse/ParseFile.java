package com.parse.gwt.client.js.parse;

import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

/**
 * Created by Kerby on 4/14/2017.
 */
@JsType(isNative = true, namespace = JsPackage.GLOBAL, name = "Object")
public class ParseFile {
    @JsProperty(name = "__type")
    public String type;
    public String name;
    public String url;
    @JsOverlay
    public static ParseFile create() {
        ParseFile parseFile = new ParseFile();
        parseFile.type = "File";
        return parseFile;
    }
}