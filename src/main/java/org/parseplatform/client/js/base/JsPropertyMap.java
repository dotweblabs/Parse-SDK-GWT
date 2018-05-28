/**
 *
 * Copyright (c) 2017 Dotweblabs Web Technologies and others. All rights reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package org.parseplatform.client.js.base;

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
