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
 * Parse Relation object
 *
 * @author Kerby Martino
 * @since 0-SNAPSHOT
 * @version 0-SNAPSHOT
 */
public class ParseRelation extends JSONObject {

    public ParseRelation() {}

    public ParseRelation(String className) {
        put("__type", new JSONString("Relation"));
        put("className", new JSONString(className));
    }

    public ParseRelation(ParseObject reference) {
        put("__type", new JSONString("Relation"));
        put("className", new JSONString(reference.getClassName()));
    }
    public static ParseRelation parse(String className) {
        ParseRelation pointer = new ParseRelation();
        pointer.put("__type", new JSONString("Relation"));
        pointer.put("className", new JSONString(className));
        return pointer;
    }
    public static ParseRelation clone(String className) {
        ParseRelation pointer = new ParseRelation();
        pointer.put("__type", new JSONString("Relation"));
        pointer.put("className", new JSONString(className));
        return pointer;
    }
    public static ParseRelation clone(JSONObject reference) {
        String className = reference.get("className").isString().stringValue();
        if(className == null) {
            throw new RuntimeException("Missing className");
        }
        ParseRelation pointer = new ParseRelation();
        pointer.put("__type", new JSONString("Relation"));
        pointer.put("className", new JSONString(className));
        return pointer;
    }
    public String getClassName() {
        return get("className") == null ? null : get("className").isString().stringValue();
    }
}
