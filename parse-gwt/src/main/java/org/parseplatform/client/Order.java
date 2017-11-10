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

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONString;

public class Order extends JSONArray {
    public Order ascending(String key) {
        int index = -1;
        for(int i=0;i<size();i++){
            if(get(i) != null) {
                String value = get(i).isString().stringValue();
                if(value.equals(key)) {
                    index = i;
                    break;
                }
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
            if(get(i) != null) {
                String value = get(i).isString().stringValue();
                if(value.equals(key)) {
                    index = i;
                    break;
                }
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
