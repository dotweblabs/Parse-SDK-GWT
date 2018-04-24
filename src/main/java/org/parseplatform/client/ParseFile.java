package org.parseplatform.client;

/*
File:  ParseFile.java
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

import com.dotweblabs.shape.client.Shape;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.rpc.AsyncCallback;
import java.util.Iterator;

/**
 * @author Kerby Martino
 * @since 0-SNAPSHOT
 * @version 0-SNAPSHOT
 */
public class ParseFile extends JSONObject {

    public static final String IMAGE_JPEG = "image/jpeg";
    public static final String IMAGE_PNG = "image/png";

    public ParseFile(String name, String url) {
        put("name", new JSONString(name));
        put("url", new JSONString(url));
        put("__type", new JSONString("File"));
    }

    public ParseFile(JSONObject jsonObject) {
        if(jsonObject != null) {
            put("name", (jsonObject.get("name") != null && jsonObject.get("name").isString() != null)  ? jsonObject.get("name").isString() : null);
            put("url", (jsonObject.get("url") != null && jsonObject.get("url").isString() != null) ? jsonObject.get("url").isString() : null);
            put("__type", new JSONString("File"));
        }
    }

    public String getName() {
        String name = get("name") != null && get("name").isString() != null ? get("name").isString().stringValue() : null;
        return name;
    }

    public String getUrl() {
        String url = get("url") != null && get("url").isString() != null ? get("url").isString().stringValue() : null;
        return url;
    }

    public static void upload(String base64file, String fileName, String mimeType, final ParseAsyncCallback<ParseResponse> callback) {
//            String data = "{__ContentType : \"" + mimeType + "\", base64 : " + base64file + "}";
        JSONObject data = new JSONObject();
        data.put("__ContentType", new JSONString(mimeType));
        data.put("base64", new JSONString(base64file));
        data.put("_ApplicationId", new JSONString(Parse.X_Parse_Application_Id));
        if(Parse.X_Parse_Javascript_Key != null && !Parse.X_Parse_Javascript_Key.isEmpty()) {
            data.put("_JavaScriptKey", new JSONString(Parse.X_Parse_Javascript_Key));
        }
        if(Parse.X_Parse_Session_Token != null && !Parse.X_Parse_Session_Token.isEmpty()) {
            data.put("_SessionToken", new JSONString(Parse.X_Parse_Session_Token));
        }
        Shape.post(Parse.SERVER_URL + Parse.FILES_URI + fileName)
                .header("Content-Type", "plain-text")
                .body(data.toString())
                .asJson(new AsyncCallback<String>() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        callback.onFailure(new ParseError(throwable));
                    }
                    @Override
                    public void onSuccess(String s) {
                        try {
                            JSONObject jsonObject = JSONParser.parseStrict(s).isObject();
                            ParseResponse response = new ParseResponse();
                            Iterator<String> it = jsonObject.keySet().iterator();
                            while (it.hasNext()) {
                                String key = it.next();
                                JSONValue jsonValue = jsonObject.get(key);
                                response.put(key, jsonValue);
                            }
                            callback.onSuccess(response);
                        } catch (Exception e) {
                            callback.onFailure(new ParseError(e));
                        }
                    }
                });
        
    }

}
