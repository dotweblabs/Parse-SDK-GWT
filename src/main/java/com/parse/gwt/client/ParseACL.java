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
package com.parse.gwt.client;

import com.google.gwt.json.client.JSONBoolean;
import com.google.gwt.json.client.JSONObject;

/**
 *
 * Parse ACL object
 *
 * @author Kerby Martino
 * @since 0-SNAPSHOT
 * @version 0-SNAPSHOT
 */
public class ParseACL extends JSONObject {

    public void setPublicReadAccess(boolean isAccess) {
        JSONObject asterisk = get("*") != null ? get("*").isObject() : null;
        if(asterisk == null) {
            asterisk = new JSONObject();
            asterisk.put("read", JSONBoolean.getInstance(isAccess));
        } else {
            asterisk.put("read", JSONBoolean.getInstance(isAccess));
        }
        put("*", asterisk);
    }
    public void setPublicWriteAccess(boolean isAccess) {
        JSONObject asterisk = get("*") != null ? get("*").isObject() : null;
        if(asterisk == null) {
            asterisk = new JSONObject();
            asterisk.put("write", JSONBoolean.getInstance(isAccess));
        } else {
            asterisk.put("write", JSONBoolean.getInstance(isAccess));
        }
        put("*", asterisk);
    }
    public void setReadAccess(ParseObject object, Boolean isAccess) {
        if(object.getClassName().equals("_Role")) {
            ParseRole role = (ParseRole) object;
            String key = "role:" + role.get("name").isString().stringValue();
            if(get(key) == null) {
                JSONObject read = new JSONObject();
                read.put("read", JSONBoolean.getInstance(isAccess));
                put(key, read);
            } else {
                JSONObject acl = get(key).isObject();
                acl.put("read", JSONBoolean.getInstance(isAccess));
            }

        } else {
            if(get(object.getObjectId()) == null) {
                JSONObject read = new JSONObject();
                read.put("read", JSONBoolean.getInstance(isAccess));
                put(object.getObjectId(), read);
            } else {
                JSONObject acl = get(object.getObjectId()).isObject();
                acl.put("read", JSONBoolean.getInstance(isAccess));
            }
        }
    }
    public void setWriteAccess(ParseObject object, Boolean isAccess) {
        if(object.getClassName().equals("_Role")) {
            ParseRole role = (ParseRole) object;
            String key = "role:" + role.get("name").isString().stringValue();
            if(get(key) == null) {
                JSONObject write = new JSONObject();
                write.put("write", JSONBoolean.getInstance(isAccess));
                put(key, write);
            } else {
                JSONObject acl = get(key).isObject();
                acl.put("write", JSONBoolean.getInstance(isAccess));
            }

        } else {
            if(get(object.getObjectId()) == null) {
                JSONObject write = new JSONObject();
                write.put("write", JSONBoolean.getInstance(isAccess));
                put(object.getObjectId(), write);
            } else {
                JSONObject acl = get(object.getObjectId()).isObject();
                acl.put("write", JSONBoolean.getInstance(isAccess));
            }
        }
    }
}
