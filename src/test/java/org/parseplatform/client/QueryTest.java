package org.parseplatform.client;

/*
File:  QueryTest.java
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

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONBoolean;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.junit.client.GWTTestCase;

/**
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
