package org.parseplatform.client;

/*
File:  ParseRelation.java
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

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;

/**
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
