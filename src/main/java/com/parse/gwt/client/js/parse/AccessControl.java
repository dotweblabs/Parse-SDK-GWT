package com.parse.gwt.client.js.parse;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

/**
 * Created by Kerby on 4/14/2017.
 */
@JsType(isNative = true, namespace = JsPackage.GLOBAL, name = "Object")
public class AccessControl {
    public boolean read;
    public boolean write;
}