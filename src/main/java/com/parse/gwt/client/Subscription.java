package com.parse.gwt.client;

import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.sksamuel.gwt.websockets.Websocket;
import com.sksamuel.gwt.websockets.WebsocketListener;

/**
 *
 *
 * @author Kerby Martino
 * @since 0-SNAPSHOT
 * @version 0-SNAPSHOT
 *
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

    public void on(String event, final AsyncCallback<ParseObject> callback){
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
                        isConnected = true;
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
                            ParseObject parseObject = ParseObject.clone(className, jsonObject);
                            callback.onSuccess(parseObject);
                        }
                    } else if (op.equalsIgnoreCase("update")) {
                        if(subcribeOp.equals(op)) {
                            JSONObject jsonObject = message.get("object").isObject();
                            ParseObject parseObject = ParseObject.clone(className, jsonObject);
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
                connect.put("applicationId", new JSONString(Parse._appId));
                connect.put("restAPIKey", new JSONString(Parse._restApiKey));
                socket.send(connect.toString());
            }
        });
    }
    public void unsubscribe(){
        socket.close();
        isConnected = false;
    }
}
