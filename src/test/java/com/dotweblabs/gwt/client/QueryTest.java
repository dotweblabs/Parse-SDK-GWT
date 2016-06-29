package com.dotweblabs.gwt.client;

import com.google.gwt.json.client.JSONBoolean;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.junit.client.GWTTestCase;

/**
 * Created by paperspace on 6/29/2016.
 */
public class QueryTest extends GWTTestCase {

    @Override
    public String getModuleName() {
        return "com.dotweblabs.gwt.Parse";
    }

    public void testQuery() {
        ParseObject testObject = Parse.Objects.extend("TestObject");
        Query query = new Query(testObject);
        query.where(complexWhere());
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
