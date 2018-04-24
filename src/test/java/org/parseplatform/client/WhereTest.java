package org.parseplatform.client;

/*
File:  WhereTest.java
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
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.junit.client.GWTTestCase;
import org.parseplatform.client.where.TextParameter;

/**
 * Unit tests of {@link Where}
 *
 * @author Kerby Martino
 * @since 0-SNAPSHOT
 * @version 0-SNAPSHOT
 *
 */
public class WhereTest extends GWTTestCase {


    @Override
    public String getModuleName() {
        return "org.parseplatform.Parse";
    }

    public void testSimpleWhere() {
        Where where = new Where("playerName", new JSONString("Sean Plott"))
                .where("cheatMode", JSONBoolean.getInstance(false));
        log(where.toString());
    }

    public void testTextWhere() {
        TextParameter parameter = new TextParameter();
        parameter.setTerm("Sample query");
        Where where = new Where("description").text(parameter);
        log(where.toString());
    }

    public void testComplexWhere() {
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
        log(where.toString());
    }

    public void testOr() {
        String searchQuery = "test query";
        Where whereFirstName = new Where("firstname").regex(new JSONString("^" + searchQuery));
        Where whereLastName = new Where("lastname").regex(new JSONString("^" + searchQuery));
        Where where = new Where().or(whereFirstName).or(whereLastName);
        log(where.toString());
    }

    public static void log(String s){
        System.out.println(s);
    }

}
