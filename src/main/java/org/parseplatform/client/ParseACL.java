package org.parseplatform.client;

/*
File:  ParseACL.java
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

import com.google.gwt.json.client.JSONBoolean;
import com.google.gwt.json.client.JSONObject;
import java.util.Iterator;

/**
 * @author Kerby Martino
 * @since 0-SNAPSHOT
 * @version 0-SNAPSHOT
 */
public class ParseACL extends JSONObject {

    public ParseACL() {}

    public ParseACL(JSONObject jsonObject) {
        Iterator<String> it = jsonObject.keySet().iterator();
        while(it.hasNext()) {
            String key = it.next();
            put(key, jsonObject.get(key));
        }
    }

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
