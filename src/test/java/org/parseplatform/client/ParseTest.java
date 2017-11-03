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
import com.google.gwt.json.client.JSONBoolean;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.Date;

/**
 *
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
        Parse.SERVER_URL = TestKeys.TEST_API_ROOT;
    }

    public void testCreateUpdateParam(){
        delayTestFinish(20000);
        Parse.SERVER_URL = TestKeys.TEST_API_ROOT;
        Parse.initialize(TestKeys.TEST_APP_ID, TestKeys.TEST_REST_API_KEY, TestKeys.TEST_MASTER_KEY);
        ParseConfig.update("TEST", new JSONString("test"), new AsyncCallback<Config>() {
            @Override
            public void onFailure(Throwable throwable) {
                log("GET Error: " + throwable.getMessage());
                fail("GET Error: " + throwable.getMessage());
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
        Parse.SERVER_URL = TestKeys.TEST_API_ROOT;
        Parse.initialize(TestKeys.TEST_APP_ID, TestKeys.TEST_REST_API_KEY, TestKeys.TEST_MASTER_KEY);
        ParseConfig.get(new AsyncCallback<Config>() {
            @Override
            public void onFailure(Throwable throwable) {
                log("GET Error: " + throwable.getMessage());
                fail("GET Error: " + throwable.getMessage());
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
        Parse.SERVER_URL = TestKeys.TEST_API_ROOT;
        Parse.initialize(TestKeys.TEST_APP_ID, TestKeys.TEST_REST_API_KEY, TestKeys.TEST_MASTER_KEY);
        ParseObject testObject = new ParseObject("TestObject");
        testObject.put("foo", new JSONString("bar"));
        testObject.create(new AsyncCallback<ParseResponse>() {
            @Override
            public void onFailure(Throwable throwable) {
                HttpRequestException ex = (HttpRequestException) throwable;
                log("POST Error: " + ex.getCode());
                fail("POST Error: " + ex.getCode());
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
        Parse.SERVER_URL = TestKeys.TEST_API_ROOT;
        Parse.initialize(TestKeys.TEST_APP_ID, TestKeys.TEST_REST_API_KEY, TestKeys.TEST_MASTER_KEY);
        ParseObject testObject = new ParseObject("TestObject");
        testObject.put("foo", new JSONString("bar"));
        testObject.create(new AsyncCallback<ParseResponse>() {
            @Override
            public void onFailure(Throwable throwable) {
                HttpRequestException ex = (HttpRequestException) throwable;
                log("POST Error: " + ex.getCode());
                fail();
                finishTest();
            }
            @Override
            public void onSuccess(ParseResponse parseResponse) {
                //log("Success objectId: " + parseResponse.getObjectId() + " createdAt: " + parseResponse.getCreatedAt());
                ParseObject ref = new ParseObject("TestObject");
                ref.setObjectId(parseResponse.getObjectId());
                log("POST Success: " + ref.getObjectId());
                ref.retrieve(new AsyncCallback<ParseObject>() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        HttpRequestException ex = (HttpRequestException) throwable;
                        log("GET Error: " + ex.getCode());
                        fail("GET Error: " + ex.getCode());
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
        Parse.SERVER_URL = TestKeys.TEST_API_ROOT;
        Parse.initialize(TestKeys.TEST_APP_ID, TestKeys.TEST_REST_API_KEY, TestKeys.TEST_MASTER_KEY);
        ParseObject testObject = new ParseObject("TestObject");
        testObject.put("boom", new JSONString("box"));

        // If not using Master Key, ACL causes 404 on this operation
        JSONObject acl = new JSONObject();
        JSONObject asterisk = new JSONObject();
        asterisk.put("read", JSONBoolean.getInstance(true));
        acl.put("*", asterisk);
        testObject.put("ACL", acl);
        testObject.create(new AsyncCallback<ParseResponse>() {
            @Override
            public void onFailure(Throwable throwable) {
                HttpRequestException ex = (HttpRequestException) throwable;
                log("POST Error: " + ex.getCode());
                finishTest();
            }
            @Override
            public void onSuccess(ParseResponse parseResponse) {
                ParseObject ref = new ParseObject("TestObject");
                ref.setObjectId(parseResponse.getObjectId());
                log("POST Success: " + ref.getObjectId());
                ref.delete(new AsyncCallback<ParseResponse>() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        HttpRequestException ex = (HttpRequestException) throwable;
                        log("DELETE Error: " + ex.getCode());
                        fail("DELETE Error: " + ex.getCode());
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
        Parse.SERVER_URL = TestKeys.TEST_API_ROOT;
        Parse.initialize(TestKeys.TEST_APP_ID, TestKeys.TEST_REST_API_KEY, TestKeys.TEST_MASTER_KEY);
        final ParseObject testObject = new ParseObject("TestObject");
        testObject.put("marko", new JSONString("marko"));
        testObject.create(new AsyncCallback<ParseResponse>() {
            @Override
            public void onFailure(Throwable throwable) {
                HttpRequestException ex = (HttpRequestException) throwable;
                log("POST Error: " + ex.getCode());
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
                ref.update(new AsyncCallback<ParseResponse>() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        HttpRequestException ex = (HttpRequestException) throwable;
                        log("UPDATE Error: " + ex.getCode());
                        fail("UPDATE Error: " + ex.getCode());
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
        parseUser.signup(new AsyncCallback<ParseResponse>() {
            @Override
            public void onFailure(Throwable throwable) {
                log(throwable.getMessage());
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
        parseUser.signup(new AsyncCallback<ParseResponse>() {
            @Override
            public void onFailure(Throwable throwable) {
                log(throwable.getMessage());
                fail();
                finishTest();
            }
            @Override
            public void onSuccess(ParseResponse parseResponse) {
                log(parseResponse.toString());
                finishTest();
            }
        });
    }

    public void testLogin() {
        delayTestFinish(3000);
        Parse.SERVER_URL = TestKeys.TEST_API_ROOT;
        Parse.initialize(TestKeys.TEST_APP_ID, TestKeys.TEST_REST_API_KEY);
        ParseUser parseUser = new ParseUser("testUser", "testPassword");
        parseUser.login("testUser", "testPassword", new AsyncCallback<ParseResponse>() {
            @Override
            public void onFailure(Throwable throwable) {
                log(throwable.getMessage());
                fail();
                finishTest();
            }
            @Override
            public void onSuccess(ParseResponse parseResponse) {
                log(parseResponse.toString());
                finishTest();
            }
        });
    }

    public void testBecome() {
        delayTestFinish(3000);
        Parse.SERVER_URL = TestKeys.TEST_API_ROOT;
        Parse.initialize(TestKeys.TEST_APP_ID, TestKeys.TEST_REST_API_KEY);
        ParseUser parseUser = new ParseUser("testUser", "testPassword");
        parseUser.login("testUser", "testPassword", new AsyncCallback<ParseResponse>() {
            @Override
            public void onFailure(Throwable throwable) {
                log(throwable.getMessage());
                fail();
                finishTest();
            }
            @Override
            public void onSuccess(ParseResponse parseResponse) {
                //log(parseResponse.toString());
                String sessionToken = parseResponse.get("sessionToken").isString().stringValue();
                ParseUser.become(sessionToken, new AsyncCallback<ParseObject>() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        log(throwable.getMessage());
                        fail();
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
        parseObject.create(new AsyncCallback<ParseResponse>() {
            @Override
            public void onFailure(Throwable throwable) {
                log(throwable.getMessage());
                fail();
                finishTest();
            }
            @Override
            public void onSuccess(ParseResponse parseResponse) {
                parseObject.setObjectId(parseResponse.getObjectId());
                final ParseObject relatedObject = new ParseObject("TestRelatedObject");
                relatedObject.create(new AsyncCallback<ParseResponse>() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        log(throwable.getMessage());
                        fail();
                        finishTest();
                    }
                    @Override
                    public void onSuccess(ParseResponse parseResponse) {
                        String objectId = parseResponse.getObjectId();
                        relatedObject.setObjectId(objectId);
                        parseObject.createRelation("testRelatedObjects", relatedObject.getPointer(), new AsyncCallback<ParseResponse>() {
                            @Override
                            public void onFailure(Throwable throwable) {
                                throwable.printStackTrace();
                                log(throwable.getMessage());
                                fail();
                                finishTest();
                            }
                            @Override
                            public void onSuccess(ParseResponse parseResponse) {
                                log(parseResponse.toString());
                                log("Testing relation():");
                                parseObject.put("testRelatedObjects", ParseRelation.clone(relatedObject.getClassName()));
                                parseObject.relation("testRelatedObjects").find(new AsyncCallback<ParseResponse>() {
                                    @Override
                                    public void onFailure(Throwable throwable) {
                                        log("Failed: " + throwable.getMessage());
                                        fail();
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
                                parseObject.getRelation("testRelatedObjects", relatedObject, new AsyncCallback<ParseResponse>() {
                                    @Override
                                    public void onFailure(Throwable throwable) {
                                        log("Failed: " + throwable.getMessage());
                                        fail();
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
        Parse.SERVER_URL = "http://localhost:1337/parse";
        Parse.initialize("myAppId", "myRESTApiKey", "myMasterKey");
        ParseObject storeObject = new ParseObject("Store");
        ParseObject productObject = new ParseObject("Product");
        storeObject.setObjectId("tTJv7g8aDf");
        storeObject.getRelation("products", productObject,"name", "E",
                new AsyncCallback<ParseResponse>() {
            @Override
            public void onFailure(Throwable throwable) {
                log("Failed: " + throwable.getMessage());
                fail();
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
