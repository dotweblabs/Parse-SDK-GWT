package com.parse.gwt.client;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class FunctionTest extends GWTTestCase {

    private static final String PARSE_API_ROOT = "http://localhost:1337/parse";

    @Override
    public String getModuleName() {
        return "com.parse.gwt.Parse";
    }
    public void testFunction() {
        Parse.SERVER_URL = PARSE_API_ROOT;
        Parse.initialize(TestKeys.TEST_APP_ID, TestKeys.TEST_REST_API_KEY, TestKeys.TEST_MASTER_KEY);
        ParseObject object = new ParseObject();
        Parse.Cloud.run("hello", object, new AsyncCallback<ParseResponse>() {
            @Override
            public void onFailure(Throwable throwable) {
                log(throwable.getMessage());
            }
            @Override
            public void onSuccess(ParseResponse parseResponse) {
                log(parseResponse.toString());
                assertNotNull(parseResponse.getResult().isString());
                String hi = parseResponse.getResult().isString().stringValue();
                assertEquals("Hi", hi);
            }
        });
    }

    public static void log(String s){
        System.out.println(s);
    }

}