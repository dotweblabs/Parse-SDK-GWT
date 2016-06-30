package com.dotweblabs.gwt.client;

import com.dotweblabs.shape.client.HttpRequestException;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONBoolean;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 *
 * Unit tests of {@link Parse.Query}
 *
 * @author Kerby Martino
 * @since 0-SNAPSHOT
 * @version 0-SNAPSHOT
 *
 */
public class QueryTest extends GWTTestCase {

    private static final String PARSE_API_ROOT = "https://parseapi.back4app.com/";

    @Override
    public String getModuleName() {
        return "com.dotweblabs.gwt.Parse";
    }

    public void testQuery() {

        Parse.initialize(TestKeys.TEST_APP_ID, TestKeys.TEST_REST_API_KEY, TestKeys.TEST_MASTER_KEY);
        Parse.SERVER_URL = PARSE_API_ROOT;
        ParseObject testObject = Parse.Objects.extend("TestObject");
        Parse.Query query = Parse.Query.extend(testObject);

        Where where = new Where("marko", new JSONString("marko"));

        query.where(where).find(new AsyncCallback<ParseResponse>() {
            @Override
            public void onFailure(Throwable throwable) {
                HttpRequestException ex = (HttpRequestException) throwable;
                log("Query error: " + ex.getCode());
                fail();
            }

            @Override
            public void onSuccess(ParseResponse parseResponse) {
                JSONArray results = parseResponse.getResults();
                if(results != null) {
                    log("Query result: "  + results.toString());
                } else {
                    log(parseResponse.get("error").isString().stringValue());
                }

            }
        });
        log(query.toString());
    }

    public static void log(String s){
        System.out.println(s);
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

}
