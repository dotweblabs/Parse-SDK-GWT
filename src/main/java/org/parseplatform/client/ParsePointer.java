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
package org.parseplatform.client;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;

/**
 *
 * Parse Pointer object
 *
 * @author Kerby Martino
 * @since 0-SNAPSHOT
 * @version 0-SNAPSHOT
 */
public class ParsePointer extends JSONObject {
    public ParsePointer() {}
    public ParsePointer(String className, String objectId) {
        put("className", new JSONString(className));
        put("objectId", new JSONString(objectId));
    }
    public ParsePointer(ParseObject reference) {
        put("__type", new JSONString("Pointer"));
        put("className", new JSONString(reference.getClassName()));
        put("objectId", new JSONString(reference.getObjectId()));
    }
    public static ParsePointer parse(String className, String objectId) {
        ParsePointer pointer = new ParsePointer();
        pointer.put("__type", new JSONString("Pointer"));
        pointer.put("className", new JSONString(className));
        pointer.put("objectId", new JSONString(objectId));
        return pointer;
    }
    public static ParsePointer clone(String className, JSONObject reference) {
        String objectId = reference.get("objectId").isString().stringValue();
        ParsePointer pointer = new ParsePointer();
        pointer.put("__type", new JSONString("Pointer"));
        pointer.put("className", new JSONString(className));
        pointer.put("objectId", new JSONString(objectId));
        return pointer;
    }
    public static ParsePointer clone(JSONObject reference) {
        String className = reference.get("className").isString().stringValue();
        String objectId = reference.get("objectId").isString().stringValue();
        ParsePointer pointer = new ParsePointer();
        pointer.put("__type", new JSONString("Pointer"));
        pointer.put("className", new JSONString(className));
        pointer.put("objectId", new JSONString(objectId));
        return pointer;
    }
    public String getReferenceObjectId() {
        return get("objectId").isString().stringValue();
    }
}
