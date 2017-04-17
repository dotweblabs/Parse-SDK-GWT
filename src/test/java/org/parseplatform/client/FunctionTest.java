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

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class FunctionTest extends GWTTestCase {

    private static final String PARSE_API_ROOT = "http://localhost:1337/parse";

    @Override
    public String getModuleName() {
        return "org.parseplatform.Parse";
    }
    public void testFunction() {
        Parse.SERVER_URL = PARSE_API_ROOT;
        Parse.initialize(TestKeys.TEST_APP_ID, TestKeys.TEST_REST_API_KEY, TestKeys.TEST_MASTER_KEY);
        ParseObject object = new ParseObject();
        Parse.Cloud.run("hello", object, new AsyncCallback<ParseResponse>() {
            @Override
            public void onFailure(Throwable throwable) {
                log(throwable.getMessage());
                finishTest();
            }
            @Override
            public void onSuccess(ParseResponse parseResponse) {
                log(parseResponse.toString());
                assertNotNull(parseResponse.getResult().isString());
                String hi = parseResponse.getResult().isString().stringValue();
                assertEquals("Hi", hi);
                finishTest();
            }
        });
        delayTestFinish(10000);
    }

    public static void log(String s){
        System.out.println(s);
    }

}
