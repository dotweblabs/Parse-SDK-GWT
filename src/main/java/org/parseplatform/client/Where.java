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

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;
import org.parseplatform.client.where.TextParameter;

/**
 *
 *
 * @author Kerby Martino
 * @since 0-SNAPSHOT
 * @version 0-SNAPSHOT
 */
public class Where extends JSONObject {
    //private JSONObject filters;
    String key;

    public Where() {
    }

    public Where(String key) {
        this.key = key;
    }
    public Where(String key, JSONValue value) {
        this.key = key;
        put(key, value);
       // this.filters = new JSONObject();
    }

    public Where lessThan(JSONValue value) {
        JSONObject operation = new JSONObject();
        operation.put("$lt", value);
        if(get(key) != null){
            JSONValue jsonValue = get(key);
            if(jsonValue.isObject() != null){
                jsonValue.isObject().put("$lt", value);
            }
        } else {
            put(key, operation);
        }
        return this;
    }

    public Where lessThanOrEqualTo(JSONValue value) {
        JSONObject operation = new JSONObject();
        operation.put("$lte", value);
        if(get(key) != null){
            JSONValue jsonValue = get(key);
            if(jsonValue.isObject() != null){
                jsonValue.isObject().put("$lte", value);
            }
        } else {
            put(key, operation);
        }        return this;
    }

    public Where greaterThan(JSONValue value) {
        JSONObject operation = new JSONObject();
        operation.put("$gt", value);
        if(get(key) != null){
            JSONValue jsonValue = get(key);
            if(jsonValue.isObject() != null){
                jsonValue.isObject().put("$gt", value);
            }
        } else {
            put(key, operation);
        }
        return this;
    }

    public Where greaterThanOrEqualTo(JSONValue value) {
        JSONObject operation = new JSONObject();
        operation.put("$gte", value);
        if(get(key) != null){
            JSONValue jsonValue = get(key);
            if(jsonValue.isObject() != null){
                jsonValue.isObject().put("$gte", value);
            }
        } else {
            put(key, operation);
        }
        return this;
    }

    public Where notEqualTo(JSONValue value) {
        JSONObject operation = new JSONObject();
        operation.put("$ne", value);
        if(get(key) != null){
            JSONValue jsonValue = get(key);
            if(jsonValue.isObject() != null){
                jsonValue.isObject().put("$ne", value);
            }
        } else {
            put(key, operation);
        }
        return this;
    }

    public Where containedIn(JSONValue value) {
        JSONObject operation = new JSONObject();
        operation.put("$in", value);
        if(get(key) != null){
            JSONValue jsonValue = get(key);
            if(jsonValue.isObject() != null){
                jsonValue.isObject().put("$in", value);
            }
        } else {
            put(key, operation);
        }
        return this;
    }

    public Where notContainedIn(JSONValue value) {
        JSONObject operation = new JSONObject();
        operation.put("$nin", value);
        if(get(key) != null){
            JSONValue jsonValue = get(key);
            if(jsonValue.isObject() != null){
                jsonValue.isObject().put("$nin", value);
            }
        } else {
            put(key, operation);
        }
        return this;
    }

    public Where exists(JSONValue value) {
        JSONObject operation = new JSONObject();
        operation.put("$exists", value);
        if(get(key) != null){
            JSONValue jsonValue = get(key);
            if(jsonValue.isObject() != null){
                jsonValue.isObject().put("$exists", value);
            }
        } else {
            put(key, operation);
        }
        return this;
    }

    public Where select(JSONValue value) {
        JSONObject operation = new JSONObject();
        operation.put("$select", value);
        if(get(key) != null){
            JSONValue jsonValue = get(key);
            if(jsonValue.isObject() != null){
                jsonValue.isObject().put("$select", value);
            }
        } else {
            put(key, operation);
        }
        return this;
    }

    public Where dontSelect(JSONValue value) {
        JSONObject operation = new JSONObject();
        operation.put("$dontSelect", value);
        if(get(key) != null){
            JSONValue jsonValue = get(key);
            if(jsonValue.isObject() != null){
                jsonValue.isObject().put("$dontSelect", value);
            }
        } else {
            put(key, operation);
        }
        return this;
    }

    public Where all(JSONValue value) {
        JSONObject operation = new JSONObject();
        operation.put("$all", value);
        if(get(key) != null){
            JSONValue jsonValue = get(key);
            if(jsonValue.isObject() != null){
                jsonValue.isObject().put("$all", value);
            }
        } else {
            put(key, operation);
        }
        return this;
    }

    public Where regex(JSONValue value) {
        JSONObject operation = new JSONObject();
        operation.put("$regex", value);
        if(get(key) != null){
            JSONValue jsonValue = get(key);
            if(jsonValue.isObject() != null){
                jsonValue.isObject().put("$regex", value);
            }
            put(key, operation);
        } else {
            put(key, operation);
        }
        return this;
    }

    public Where text(TextParameter param) {
        JSONObject operation = new JSONObject();
        operation.put("$text", param);
        if(get(key) != null){
            JSONValue jsonValue = get(key);
            if(jsonValue.isObject() != null){
                jsonValue.isObject().put("$text", param);
            }
            put(key, jsonValue);
        } else {
            put(key, operation);
        }
        return this;
    }

    public Where options(JSONValue value) {
        JSONObject operation = new JSONObject();
        operation.put("$options", value);
        if(get(key) != null){
            JSONValue jsonValue = get(key);
            if(jsonValue.isObject() != null){
                jsonValue.isObject().put("$options", value);
            }
        } else {
            put(key, operation);
        }
        return this;
    }

    public Where or(Where where) {
        JSONArray or = get("$or") != null ? get("$or").isArray() : null;
        if(or != null){
            or.set(or.size(), where);
        } else {
            JSONArray array = new JSONArray();
            array.set(array.size(), where);
            put("$or", array);
        }
        return this;
    }

    public Where and(Where where) {
        JSONArray or = get("$and") != null ? get("$and").isArray() : null;
        if(or != null){
            or.set(or.size(), where);
        } else {
            JSONArray array = new JSONArray();
            array.set(array.size(), where);
            put("$and", array);
        }
        return this;
    }

    public Where where(String key) {
        this.key = key;
        return this;
    }

    public Where where(String key, JSONValue value) {
        this.key = key;
        put(key, value);
        return this;
    }

}
