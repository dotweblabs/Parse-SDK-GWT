package com.parse.gwt.client.js;

import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, name = "Object", namespace = JsPackage.GLOBAL)
public interface JsPropertyMap<T> {
    @JsOverlay @SuppressWarnings("unchecked") default T get(String propertyName) { return (T) InternalJsUtil.get(this, propertyName); }
    @JsOverlay
    default boolean has(String propertyName) { return InternalJsUtil.has(this, propertyName); }
    @JsOverlay default void delete(String propertyName) { InternalJsUtil.delete(this, propertyName); }
    @JsOverlay default void set(String propertyName, T value) { InternalJsUtil.set(this, propertyName, value); }
    @JsOverlay default void forEach(JsForEachCallbackFn cb) { InternalJsUtil.forEach(this, cb); }
}
