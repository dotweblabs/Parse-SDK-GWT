/**
 *
 * Copyright (c) 2016 Dotweblabs Web Technologies and others. All rights reserved.
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

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;

import java.util.Iterator;

/**
 *
 * Parse Config Object
 *
 * @author Kerby Martino
 * @since 0-SNAPSHOT
 * @version 0-SNAPSHOT
 *
 */
public class Config extends JSONObject {
    public static Config fromJSON(String json){
        Config config = new Config();
        JSONObject jsonObject = (JSONObject) JSONParser.parseStrict(json);
        Iterator<String> it = jsonObject.keySet().iterator();
        while(it.hasNext()) {
            String key = it.next();
            JSONValue value = jsonObject.get(key);
            config.put(key, value);
        }
        return config;
    }
}
