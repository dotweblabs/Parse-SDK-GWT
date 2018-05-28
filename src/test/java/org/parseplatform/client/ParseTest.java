package org.parseplatform.client;

/*
File:  ParseTest.java
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

import com.google.gwt.json.client.JSONBoolean;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.Window;
import java.util.Date;

/**
 * Unit tests of {@link ParseTest}
 * 
 * @author Kerby Martino
 * @since 0-SNAPSHOT
 * @version 0-SNAPSHOT
 *
 */
public class ParseTest extends GWTTestCase {


    @Override
    public String getModuleName() {
        return "org.parseplatform.Parse";
    }

    public void testInitialize() {
        Parse.initialize(TestKeys.TEST_APP_ID, TestKeys.TEST_REST_API_KEY, TestKeys.TEST_MASTER_KEY);
    }

    public void testCreateUpdateParam(){
        delayTestFinish(20000);
        Parse.initialize(TestKeys.TEST_APP_ID, TestKeys.TEST_REST_API_KEY, TestKeys.TEST_MASTER_KEY);
        ParseConfig.update("TEST", new JSONString("test"), new ParseAsyncCallback<Config>() {
            @Override
            public void onFailure(ParseError error) {
                log("GET Code: " + error.getCode());
                fail("GET Error: " + error.getError());
                finishTest();
            }
            @Override
            public void onSuccess(Config config) {
                log(config.toString());
                finishTest();
            }
        });
    }

    public void testRetrieveConfig(){
        delayTestFinish(20000);
        Parse.initialize(TestKeys.TEST_APP_ID, TestKeys.TEST_REST_API_KEY, TestKeys.TEST_MASTER_KEY);
        ParseConfig.get(new ParseAsyncCallback<Config>() {
            @Override
            public void onFailure(ParseError error) {
                error.log();
                finishTest();
            }
            @Override
            public void onSuccess(Config config) {
                log(config.toString());
                finishTest();
            }
        });
    }

    public void testCreateObject() {
        Parse.initialize(TestKeys.TEST_APP_ID, TestKeys.TEST_REST_API_KEY, TestKeys.TEST_MASTER_KEY);
        ParseObject testObject = new ParseObject("TestObject");
        testObject.put("foo", new JSONString("bar"));
        testObject.create(new ParseAsyncCallback<ParseResponse>() {
            @Override
            public void onFailure(ParseError error) {
                error.log();
                finishTest();
            }
            @Override
            public void onSuccess(ParseResponse parseResponse) {
                log("POST Success objectId: " + parseResponse.getObjectId() + " createdAt: " + parseResponse.getCreatedAt());
                assertNotNull(parseResponse.getObjectId());
                assertNotNull(parseResponse.getCreatedAt());
                finishTest();
            }
        });
        delayTestFinish(5000);
    }

    public void testRetrieveObject() {
        delayTestFinish(20000);
        Parse.initialize(TestKeys.TEST_APP_ID, TestKeys.TEST_REST_API_KEY, TestKeys.TEST_MASTER_KEY);
        ParseObject testObject = new ParseObject("TestObject");
        testObject.put("foo", new JSONString("bar"));
        testObject.create(new ParseAsyncCallback<ParseResponse>() {
            @Override
            public void onFailure(ParseError error) {
                error.log();
                fail(error.getError());
                finishTest();
            }
            @Override
            public void onSuccess(ParseResponse parseResponse) {
                //log("Success objectId: " + parseResponse.getObjectId() + " createdAt: " + parseResponse.getCreatedAt());
                ParseObject ref = new ParseObject("TestObject");
                ref.setObjectId(parseResponse.getObjectId());
                log("POST Success: " + ref.getObjectId());
                ref.retrieve(new ParseAsyncCallback<ParseObject>() {
                    @Override
                    public void onFailure(ParseError error) {
                        error.log();
                        finishTest();
                    }
                    @Override
                    public void onSuccess(ParseObject parseObject) {
                        log("GET Success: " + parseObject.toString());
                        assertEquals("TestObject", parseObject.getClassName());
                        finishTest();
                    }
                });
            }
        });
        delayTestFinish(5000);
    }

    public void testDeleteObject() {
        delayTestFinish(2000);
        Parse.initialize(TestKeys.TEST_APP_ID, TestKeys.TEST_REST_API_KEY, TestKeys.TEST_MASTER_KEY);
        ParseObject testObject = new ParseObject("TestObject");
        testObject.put("boom", new JSONString("box"));

        // If not using Master Key, ACL causes 404 on this operation
        JSONObject acl = new JSONObject();
        JSONObject asterisk = new JSONObject();
        asterisk.put("read", JSONBoolean.getInstance(true));
        acl.put("*", asterisk);
        testObject.put("ACL", acl);
        testObject.create(new ParseAsyncCallback<ParseResponse>() {
            @Override
            public void onFailure(ParseError error) {
                error.log();
                finishTest();
            }
            @Override
            public void onSuccess(ParseResponse parseResponse) {
                ParseObject ref = new ParseObject("TestObject");
                ref.setObjectId(parseResponse.getObjectId());
                log("POST Success: " + ref.getObjectId());
                ref.delete(new ParseAsyncCallback<ParseResponse>() {
                    @Override
                    public void onFailure(ParseError error) {
                        error.log();
                        finishTest();
                    }
                    @Override
                    public void onSuccess(ParseResponse parseObject) {
                        // DELETE OP returns null
                        //log("DELETE Success: " + parseObject.toString());
                        //String updatedAt = parseObject.getUpdatedAt();
                        //assertNotNull(updatedAt);
                        finishTest();
                    }
                });
            }
        });
    }

    public void testUpdateObject() {
        delayTestFinish(2000);
        Parse.initialize(TestKeys.TEST_APP_ID, TestKeys.TEST_REST_API_KEY, TestKeys.TEST_MASTER_KEY);
        final ParseObject testObject = new ParseObject("TestObject");
        testObject.put("marko", new JSONString("marko"));
        testObject.create(new ParseAsyncCallback<ParseResponse>() {
            @Override
            public void onFailure(ParseError error) {
                error.log();
                finishTest();
            }
            @Override
            public void onSuccess(ParseResponse parseResponse) {
                ParseObject ref = new ParseObject(testObject.getClassName());
                ref.setObjectId(parseResponse.getObjectId());
                ref.putString("marko", "polo");
                ref.putString("jack", "sparrow");
                ref.putBoolean("status", false);
                ref.putNumber("count", 1L);
                log("POST Success: " + ref.getObjectId());
                ref.update(new ParseAsyncCallback<ParseResponse>() {
                    @Override
                    public void onFailure(ParseError error) {
                        error.log();
                        finishTest();
                    }
                    @Override
                    public void onSuccess(ParseResponse parseObject) {
                        log("UPDATE Success: " + parseObject.toString());
                        String updatedAt = parseObject.getUpdatedAt();
                        assertNotNull(updatedAt);
                        finishTest();
                    }
                });
            }
        });
    }

    public void testShouldFailSignupAlreadyExist() {
        delayTestFinish(3000);
        Parse.initialize(TestKeys.TEST_APP_ID, TestKeys.TEST_REST_API_KEY);
        ParseUser parseUser = new ParseUser("testUser", "testPassword");
        parseUser.putNumber("age", 18L);
        parseUser.signup(new ParseAsyncCallback<ParseResponse>() {
            @Override
            public void onFailure(ParseError error) {
                error.log();
                finishTest();
            }
            @Override
            public void onSuccess(ParseResponse parseResponse) {
                log(parseResponse.toString());
                fail();
                finishTest();
            }
        });
    }

    public void testSignup() {
        delayTestFinish(3000);
        Parse.initialize(TestKeys.TEST_APP_ID, TestKeys.TEST_REST_API_KEY);
        ParseUser parseUser = new ParseUser("testUser"  + new Date().toString(), "testPassword");
        parseUser.putNumber("age", 18L);
        parseUser.signup(new ParseAsyncCallback<ParseResponse>() {
            @Override
            public void onFailure(ParseError error) {
                error.log();
                fail(error.getError());
                finishTest();
            }
            @Override
            public void onSuccess(ParseResponse parseResponse) {
                log(parseResponse.toString());
                finishTest();
            }
        });
    }

    public void testAnonymousLogin() {
        Parse.initialize(TestKeys.TEST_APP_ID, TestKeys.TEST_REST_API_KEY);
        ParseUser.anonymousLogin(new ParseAsyncCallback<ParseResponse>() {
            @Override
            public void onFailure(ParseError error) {
                error.log();
                fail();
                finishTest();
            }
            @Override
            public void onSuccess(ParseResponse parseResponse) {
                assertNotNull(parseResponse);
                Window.alert(parseResponse.toString());
                finishTest();
            }
        });
        delayTestFinish(6000);
    }

    public void testLogin() {
        Parse.initialize(TestKeys.TEST_APP_ID, TestKeys.TEST_REST_API_KEY);
        ParseUser parseUser = new ParseUser("testUser", "testPassword");
        parseUser.login("testUser", "testPassword", new ParseAsyncCallback<ParseResponse>() {
            @Override
            public void onFailure(ParseError error) {
                error.log();
                fail(error.getError());
                finishTest();
            }
            @Override
            public void onSuccess(ParseResponse parseResponse) {
                assertNotNull(parseResponse);
                ParseObject parseObject = parseResponse.asParseObject(ParseConstants.CLASSNAME_USER);
                assertNotNull(parseObject);
                assertEquals(ParseConstants.CLASSNAME_USER, parseObject.getClassName());
                log(parseObject.toString());
                finishTest();
            }
        });
        delayTestFinish(3000);
    }

    public void testBecome() {
        delayTestFinish(3000);
        Parse.initialize(TestKeys.TEST_APP_ID, TestKeys.TEST_REST_API_KEY);
        ParseUser parseUser = new ParseUser("testUser", "testPassword");
        parseUser.login("testUser", "testPassword", new ParseAsyncCallback<ParseResponse>() {
            @Override
            public void onFailure(ParseError error) {
                error.log();
                fail(error.getError());
                finishTest();
            }
            @Override
            public void onSuccess(ParseResponse parseResponse) {
                //log(parseResponse.toString());
                String sessionToken = parseResponse.get("sessionToken").isString().stringValue();
                ParseUser.become(sessionToken, new ParseAsyncCallback<ParseObject>() {
                    @Override
                    public void onFailure(ParseError error) {
                        error.log();
                        fail(error.getError());
                        finishTest();
                    }
                    @Override
                    public void onSuccess(ParseObject parseObject) {
                        log(parseObject.toString());
                        finishTest();
                    }
                });
                finishTest();
            }
        });
    }

    public void testGetRelation(){
        delayTestFinish(60000);
//        Parse.SERVER_URL = "http://localhost:1337/parse";
//        Parse.initialize("myAppId", "myRESTApiKey", "myMasterKey");
        final ParseObject parseObject = new ParseObject("TestObject");
        parseObject.put("foo", new JSONString("bar"));
        parseObject.create(new ParseAsyncCallback<ParseResponse>() {
            @Override
            public void onFailure(ParseError error) {
                error.log();
                fail(error.getError());
                finishTest();
            }
            @Override
            public void onSuccess(ParseResponse parseResponse) {
                parseObject.setObjectId(parseResponse.getObjectId());
                final ParseObject relatedObject = new ParseObject("TestRelatedObject");
                relatedObject.create(new ParseAsyncCallback<ParseResponse>() {
                    @Override
                    public void onFailure(ParseError error) {
                        error.log();
                        fail(error.getError());
                        finishTest();
                    }
                    @Override
                    public void onSuccess(ParseResponse parseResponse) {
                        String objectId = parseResponse.getObjectId();
                        relatedObject.setObjectId(objectId);
                        parseObject.createRelation("testRelatedObjects", relatedObject.getPointer(), new ParseAsyncCallback<ParseResponse>() {
                            @Override
                            public void onFailure(ParseError error) {
                                error.log();
                                fail(error.getError());
                                finishTest();
                            }
                            @Override
                            public void onSuccess(ParseResponse parseResponse) {
                                log(parseResponse.toString());
                                log("Testing relation():");
                                parseObject.put("testRelatedObjects", ParseRelation.clone(relatedObject.getClassName()));
                                parseObject.relation("testRelatedObjects").find(new ParseAsyncCallback<ParseResponse>() {
                                    @Override
                                    public void onFailure(ParseError error) {
                                        error.log();
                                        fail(error.getError());
                                        finishTest();
                                    }
                                    @Override
                                    public void onSuccess(ParseResponse parseResponse) {
                                        String resp = parseResponse.toString();
                                        log(relatedObject.getObjectId() + " relation() test: " + resp);
                                        finishTest();
                                    }
                                });
                                final String[] response = new String[1];
                                log("Testing Parse.Objects.getRelation():");
                                parseObject.getRelation("testRelatedObjects", relatedObject, new ParseAsyncCallback<ParseResponse>() {
                                    @Override
                                    public void onFailure(ParseError error) {
                                        error.log();
                                        fail(error.getError());
                                        finishTest();
                                    }
                                    @Override
                                    public void onSuccess(ParseResponse parseResponse) {
                                        response[0] = parseResponse.toString();
                                        log(response[0]);
                                        finishTest();
                                    }
                                });
                            }
                        });
                    }
                });
            }
        });
        delayTestFinish(10000);
    }

    public void testGetRelationWithParam(){
        delayTestFinish(60000);
        Parse.initialize(TestKeys.TEST_APP_ID, TestKeys.TEST_REST_API_KEY, TestKeys.TEST_MASTER_KEY);
        ParseObject storeObject = new ParseObject("Store");
        ParseObject productObject = new ParseObject("Product");
        storeObject.setObjectId("tTJv7g8aDf");
        storeObject.getRelation("products", productObject,"name", "E",
                new ParseAsyncCallback<ParseResponse>() {
            @Override
            public void onFailure(ParseError error) {
                error.log();
                fail(error.getError());
                finishTest();
            }

            @Override
            public void onSuccess(ParseResponse parseResponse) {
                log(parseResponse.toString());
                finishTest();
            }
        });
    }

    public void testGetUri() {
        Parse.SERVER_URL = "http://parseapi.back4app.com/";
        String uri = Parse.getUri();
        assertEquals("parseapi.back4app.com/", uri);
        Parse.SERVER_URL = "http://localhost:1337/parse";
        uri = Parse.getUri();
        assertEquals("localhost:1337/parse", uri);
        log(uri);
    }

    public void testCreateParseRole() {
        ParseRole role = new ParseRole("Test Role");
        ParseACL acl = new ParseACL();
        acl.setPublicReadAccess(true);
        acl.setPublicWriteAccess(false);
        role.setACL(acl);
        log((role.toString()));
    }

    public void testCreateParseACL() {
        ParseRole role = new ParseRole("Test Role");
        ParseACL acl = new ParseACL();
        acl.setWriteAccess(role, true);
        acl.setReadAccess(role, true);
        log((acl.toString()));
    }

    public static void log(String s){
        System.out.println(s);
    }

}
