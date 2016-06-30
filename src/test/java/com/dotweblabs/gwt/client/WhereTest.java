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

import com.google.gwt.json.client.JSONBoolean;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.junit.client.GWTTestCase;

/**
 *
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
        return "com.dotweblabs.gwt.Parse";
    }

    public void testSimpleWhere() {
        Where where = new Where("playerName", new JSONString("Sean Plott"))
                .where("cheatMode", JSONBoolean.getInstance(false));
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

    public static void log(String s){
        System.out.println(s);
    }

}
