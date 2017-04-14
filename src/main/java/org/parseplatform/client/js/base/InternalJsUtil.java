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