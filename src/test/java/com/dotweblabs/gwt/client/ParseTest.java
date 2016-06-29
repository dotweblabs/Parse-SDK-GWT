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
package com.dotweblabs.gwt.client;

import com.dotweblabs.shape.client.HttpRequestException;
import com.dotweblabs.shape.client.Shape;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 *
 * Unit tests of {@link ParseTest}
 * @author Kerby Martino
 * @since 0-SNAPSHOT
 * @version 0-SNAPSHOT
 *
 */
public class ParseTest extends GWTTestCase {

    private static final String PARSE_API_ROOT = "https://parseapi.back4app.com/";

    private static final String TEST_APP_ID = "";
    private static final String TEST_CLIENT_KEY = "";
    private static final String TEST_JAVASCRIPT_KEY = "";
    private static final String TEST_MASTER_KEY = "";
    private static final String TEST_REST_API_KEY = "";

    @Override
    public String getModuleName() {
        return "com.dotweblabs.gwt.Parse";
    }

    public void testInitialize() {
        Parse.initialize(TestKeys.TEST_APP_ID, TestKeys.TEST_REST_API_KEY, TestKeys.TEST_MASTER_KEY);
        Parse.SERVER_URL = PARSE_API_ROOT;
    }

    public void testCreateObject() {
        Parse.initialize(TestKeys.TEST_APP_ID, TestKeys.TEST_REST_API_KEY, TestKeys.TEST_MASTER_KEY);
        Parse.SERVER_URL = PARSE_API_ROOT;
        ParseObject testObject = new ParseObject("TestObject");
        testObject.put("foo", new JSONString("bar"));
        log(testObject.toString());

        Parse.Objects.create(testObject, new AsyncCallback<ParseResponse>() {
            @Override
            public void onFailure(Throwable throwable) {
                HttpRequestException ex = (HttpRequestException) throwable;
                log("Error: " + ex.getCode());
            }
            @Override
            public void onSuccess(ParseResponse parseResponse) {
                log("Success objectId: " + parseResponse.getObjectId() + " createdAt: " + parseResponse.getCreatedAt());
                assertNotNull(parseResponse.getObjectId());
                assertNotNull(parseResponse.getCreatedAt());
            }
        });
    }

    public void testRetrieveObject() {

    }

    public static void log(String s){
        System.out.println(s);
    }

}
