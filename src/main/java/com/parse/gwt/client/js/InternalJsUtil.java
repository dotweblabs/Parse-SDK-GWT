package com.parse.gwt.client.js;

class InternalJsUtil {
    public static native Object get(Object obj, String key) /*-{ return obj[key]; }-*/;
    public static native boolean has(Object obj, String key) /*-{ return key in obj; }-*/;
    public static native Object delete(Object obj, String key) /*-{ delete obj[key]; }-*/;
    public static native void set(Object obj, String key, Object value) /*-{ obj[key] = value; }-*/;
    public static native void forEach(Object obj, JsForEachCallbackFn cb) /*-{
        for (var element in obj) { cb(element); }
    }-*/;
    private InternalJsUtil() {}
}