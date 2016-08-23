package com.parse.gwt.client;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONString;

public class Order extends JSONArray {
    public Order ascending(String key) {
        int index = -1;
        for(int i=0;i<size();i++){
            String value = get(i).isString().stringValue();
            if(value.equals(key)) {
                index = i;
                break;
            }
        }
        if(index != -1){
            set(index, new JSONString(key));
        }else {
            set(size(), new JSONString(key));
        }
        return this;
    }
    public Order descending(String key){
        int index = -1;
        for(int i=0;i<size();i++){
            String value = get(i).isString().stringValue();
            if(value.equals(key)) {
                index = i;
                break;
            }
        }
        if(index != -1){
            set(index, new JSONString("-" + key));
        }else {
            set(size(), new JSONString("-" + key));
        }
        return this;
    }
}
