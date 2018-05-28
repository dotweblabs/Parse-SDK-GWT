package org.parseplatform.client;

/*
File:  Where.java
Version: 0-SNAPSHOT
Contact: hello@dotweblabs.com
----
Copyright (c) 2018, Dotweblabs Web Technologies
All rights reserved.
Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
    * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
    * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
    * Neither the name of Dotweblabs Web Technologies nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.
THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES,
INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;
import org.parseplatform.client.where.TextParameter;

/**
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
