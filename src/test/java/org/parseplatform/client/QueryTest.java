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

import com.dotweblabs.shape.client.HttpRequestException;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONBoolean;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 *
 * Unit tests of {@link ParseQuery}
 *
 * @author Kerby Martino
 * @since 0-SNAPSHOT
 * @version 0-SNAPSHOT
 *
 */
public class QueryTest extends GWTTestCase {

    private static final String PARSE_API_ROOT = "http://localhost:1337/parse";

    @Override
    public String getModuleName() {
        return "org.parseplatform.Parse";
    }

    public void testQuery() {
        Parse.initialize(TestKeys.TEST_APP_ID, TestKeys.TEST_REST_API_KEY, TestKeys.TEST_MASTER_KEY);
        ParseObject testObject = new ParseObject("TestObject");
        ParseQuery query = new ParseQuery(testObject);

        Where where = new Where("marko", new JSONString("marko"));

        query.where(where).find(new ParseAsyncCallback<ParseResponse>() {
            @Override
            public void onFailure(ParseError error) {
                error.log();
                fail(error.getError());
                finishTest();
            }

            @Override
            public void onSuccess(ParseResponse parseResponse) {
                JSONArray results = parseResponse.getResults();
                if(results != null) {
                    log("Query result: "  + results.toString());
                } else {
                    log(parseResponse.get("error").isString().stringValue());
                }
                finishTest();

            }
        });
        log(query.toString());
        delayTestFinish(10000);
    }

    public void testQueryPointer() {
        delayTestFinish(10000);
        Parse.initialize(TestKeys.TEST_APP_ID, TestKeys.TEST_REST_API_KEY, TestKeys.TEST_MASTER_KEY);
        createParseObject();
        ParseObject talentObject = new ParseObject("TestObject");
        ParseQuery query = new ParseQuery(talentObject);

        ParseObject pointer = new ParseObject();
        pointer.putString("__type", "Pointer");
        pointer.putString("className", "_User");
        pointer.putString("objectId", "QoSqhY3nV7");

        Where where = new Where("userId", pointer);

        query.where(where).find(new ParseAsyncCallback<ParseResponse>() {
            @Override
            public void onFailure(ParseError error) {
                error.log();
                fail(error.getError());
                finishTest();
            }
            @Override
            public void onSuccess(ParseResponse parseResponse) {
                log(parseResponse.toString());
                JSONArray results = parseResponse.getResults();
                assertNotNull(results);
                finishTest();
            }
        });
        delayTestFinish(5000);
    }

    public static void log(String s){
        System.out.println(s);
    }

    public void createParseObject() {
        ParseObject testObject = new ParseObject("TestObject");
        testObject.put("foo", new JSONString("bar"));

        ParseObject pointer = new ParseObject();
        pointer.putString("__type", "Pointer");
        pointer.putString("className", "_User");
        pointer.putString("objectId", "QoSqhY3nV7");

        testObject.put("userId", pointer);

        testObject.create(new ParseAsyncCallback<ParseResponse>() {
            @Override
            public void onFailure(ParseError error) {
                error.log();
                fail(error.getError());
            }
            @Override
            public void onSuccess(ParseResponse parseResponse) {
                log("POST Success objectId: " + parseResponse.getObjectId() + " createdAt: " + parseResponse.getCreatedAt());
                assertNotNull(parseResponse.getObjectId());
                assertNotNull(parseResponse.getCreatedAt());
            }
        });
    }

    public Where complexWhere() {
        Where where = new Where("playerName", new JSONString("Sean Plott"))
                .where("cheatMode", JSONBoolean.getInstance(false))
                .where("score").greaterThan(new JSONNumber(100L))
                .lessThan(new JSONNumber(1000L))
                .where("team").greaterThanOrEqualTo(new JSONString("Awesome Team"))
                .where("country").regex(new JSONString("^Spain"))
                .or(new Where("wins")
                        .greaterThan(new JSONNumber(150L))
                        .lessThan(new JSONNumber(5L)))
                .or(new Where("loses")
                        .greaterThan(new JSONNumber(10L))
                        .lessThan(new JSONNumber(10L)))
                .and(new Where("color", new JSONString("red"))
                        .where("champion", JSONBoolean.getInstance(true)));
        return where;
    }

    public void testQuery1() {
        Parse.initialize(TestKeys.TEST_APP_ID, TestKeys.TEST_REST_API_KEY, TestKeys.TEST_MASTER_KEY);
        Where where = new Where("isPublished", JSONBoolean.getInstance(true));
        ParseObject test = new ParseObject("Story");
        ParseQuery query = new ParseQuery(test);
        query.where(where);
        query.skip(0);
        query.limit(10);
        query.order(new Order().descending("createdAt"));
        //log("Query: " + query.toString());
        query.find(new ParseAsyncCallback<ParseResponse>() {
            @Override
            public void onFailure(ParseError error) {
                error.log();
                finishTest();
            }
            @Override
            public void onSuccess(ParseResponse parseResponse) {
                JSONArray jsonArray = parseResponse.getResults();
                log(jsonArray.size() + "");
//                log(parseResponse.toString());
                finishTest();
            }
        });
        delayTestFinish(10000);
    }

}
