package org.parseplatform.client;

/*
File:  ParseConfig.java
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
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author Kerby Martino
 * @since 0-SNAPSHOT
 * @version 0-SNAPSHOT
 */
public class ParseConfig {
    public static void get(final ParseAsyncCallback<Config> callback) {
        Shape.get(Parse.SERVER_URL + "/config")
                .header("X-Parse-Application-Id", Parse.X_Parse_Application_Id)
                .header("X-Parse-REST-API-Key", Parse.X_Parse_REST_API_Key)
                .header("X-Parse-Master-Key", Parse.X_Parse_Master_Key)
                .asJson(new AsyncCallback<String>() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        callback.onFailure(new ParseError(throwable));
                    }
                    @Override
                    public void onSuccess(String s) {
                        try {
                            JSONValue jso = JSONParser.parseStrict(s);
                            if(jso != null && jso.isObject() != null) {
                                JSONObject params = jso.isObject().get("params").isObject();
                                org.parseplatform.client.Config config = org.parseplatform.client.Config.fromJSON(params.toString());
                                callback.onSuccess(config);
                            }

                        } catch (Exception e){
                            callback.onFailure(new ParseError(e));
                        }
                    }
                });
    }
    public static void update(String parameter, JSONValue value, final ParseAsyncCallback<org.parseplatform.client.Config> callback) {
        JSONObject params = new JSONObject();
        params.put(parameter, value);
        JSONObject body = new JSONObject();
        body.put("params", params);
        Shape.put(Parse.SERVER_URL + "/config")
                .header("X-Parse-Application-Id", Parse.X_Parse_Application_Id)
                .header("X-Parse-REST-API-Key", Parse.X_Parse_REST_API_Key)
                .header("X-Parse-Master-Key", Parse.X_Parse_Master_Key)
                .body(body.toString())
                .asJson(new AsyncCallback<String>() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        callback.onFailure(new ParseError(throwable));
                    }
                    @Override
                    public void onSuccess(String s) {
                        try {
                            org.parseplatform.client.Config config = org.parseplatform.client.Config.fromJSON(s);
                            callback.onSuccess(config);
                        } catch (Exception e){
                            callback.onFailure(new ParseError(e));
                        }
                    }
                });
    }
}
