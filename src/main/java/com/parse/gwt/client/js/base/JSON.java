package com.parse.gwt.client.js.base;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

/**
 * Created by Kerby on 4/14/2017.
 */
@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class JSON {
    public static native String stringify(Object o);
    public static native <O> O parse(String json);
}
