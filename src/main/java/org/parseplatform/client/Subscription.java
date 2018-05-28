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

/*
File:  Subscription.java
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

import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.sksamuel.gwt.websockets.Websocket;
import com.sksamuel.gwt.websockets.WebsocketListener;

/**
 * @author Kerby Martino
 * @since 0-SNAPSHOT
 * @version 0-SNAPSHOT
 */
public class Subscription {

    Websocket socket = null;
    private static boolean isConnected = false;
    private String className;
    private Where where;

    public Subscription(String className, Where where) {
        this.className = className;
        this.where = where;
        this.isConnected = false;
    }

    public void on(String event, final ParseAsyncCallback<ParseObject> callback){
        //throw new RuntimeException("Websocket is not yet connected");
        final String subcribeOp = event;
        socket = new Websocket("ws://" + Parse.getUri());
        socket.open();
        socket.addListener(new WebsocketListener() {
            @Override
            public void onClose() {
//                    Browser.getWindow().getConsole().log("onClose");
            }
            @Override
            public void onMessage(String s) {
//                    Browser.getWindow().getConsole().log("Message: " + s);
                JSONObject message = (JSONObject) JSONParser.parseStrict(s);
                if((message.get("op") != null) && (message.get("op").isString() != null)) {
                    String op = message.get("op").isString().stringValue();
                    if(op.equalsIgnoreCase("connected")) {
                        setIsConnected(true);
                        JSONObject connect = new JSONObject();
                        connect.put("op", new JSONString("subscribe"));
                        connect.put("requestId", new JSONNumber(1L));
                        JSONObject query = new JSONObject();
                        query.put("className", new JSONString("Order"));
                        query.put("where", new Where("status", new JSONString("new")));
                        connect.put("query", query);
                        socket.send(connect.toString());
                    } else if (op.equalsIgnoreCase("enter")||op.equalsIgnoreCase("create")||op.equalsIgnoreCase("leave")) {
                        if(subcribeOp.equals(op)) {
                            JSONObject jsonObject = message.get("object").isObject();
                            ParseObject parseObject = new ParseObject(className, jsonObject);
                            callback.onSuccess(parseObject);
                        }
                    } else if (op.equalsIgnoreCase("update")) {
                        if(subcribeOp.equals(op)) {
                            JSONObject jsonObject = message.get("object").isObject();
                            ParseObject parseObject = new ParseObject(className, jsonObject);
                            callback.onSuccess(parseObject);
                        }
                    }
                }
            }
            @Override
            public void onOpen() {
//                    Browser.getWindow().getConsole().log("onOpen");
                JSONObject connect = new JSONObject();
                connect.put("op", new JSONString("connect"));
                connect.put("applicationId", new JSONString(Parse.X_Parse_Application_Id));
                connect.put("restAPIKey", new JSONString(Parse.X_Parse_REST_API_Key));
                socket.send(connect.toString());
            }
        });
    }
    public void unsubscribe(){
        socket.close();
        setIsConnected(false);
    }

    private void setIsConnected(Boolean connected) {
        isConnected = connected;
    }

}
