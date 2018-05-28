package org.parseplatform.client;

/*
File:  ParseError.java
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
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import elemental.client.Browser;

/**
 * @author Kerby Martino
 * @since 0-SNAPSHOT
 * @version 0-SNAPSHOT
 */
public class ParseError extends JSONObject {

    private Integer code;

    private String error;

    private Throwable t;

    public ParseError(Integer code, String error) {
        this.code = code;
        this.error = error;
    }

    public ParseError(Throwable t) {
        String message = null;
        this.t = t;
        if(t != null && t.getMessage() != null) {
            message = t.getMessage();
        }
        if(message != null) {
            JSONValue jsonError = JSONParser.parseStrict(message);
            if(jsonError != null && jsonError.isObject() != null) {
                JSONObject error = jsonError.isObject();
                Double d =  (error.get("code") != null && error.get("code").isNumber() != null)
                        ? error.get("code").isNumber().doubleValue(): null;
                this.code = d.intValue();
                this.error = (error.get("error") != null && error.get("error").isString() != null)
                        ? error.get("error").isString().stringValue() : null;
            } else {
                this.error = message;
            }
        }
    }

    public Integer getCode() {
        return code;
    }

    public String getError() {
        return error;
    }

    public Throwable getThrowable() {
        return t;
    }

    public void log() {
        Browser.getWindow().getConsole().log(this.toString());
    }
}
