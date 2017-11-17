/**
 * Copyright (c) 2017 Dotweblabs Web Technologies and others. All rights reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.parseplatform.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.reflect.shared.GwtReflect;
import com.google.gwt.user.client.Window;
import org.parseplatform.client.beans.ChildBean;
import org.parseplatform.client.beans.ParentBean;
import org.parseplatform.client.beans.SimpleBean;
import org.parseplatform.types.Array;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;

/**
 * Unit tests of {@link Where}
 *
 * @author Kerby Martino
 * @version 0-SNAPSHOT
 * @since 0-SNAPSHOT
 */
public class TestGwtMarshaller extends GWTTestCase {


    @Override
    public String getModuleName() {
        return "org.parseplatform.Parse";
    }

    public void testMarshall() {
        GwtReflect.magicClass(SimpleBean.class);

        GwtMarshaller marshaller = GWT.create(GwtMarshaller.class);

        SimpleBean simpleBean = new SimpleBean();


        simpleBean.setObjectId("test-object-id");
        simpleBean.setTestint(1);
        simpleBean.setTestdouble(3.0);
        simpleBean.setTestlong(5L);
        simpleBean.setTestshort((short) 7);
        simpleBean.setTestbyte("test".getBytes()[0]);
        simpleBean.setTestbytes("tests".getBytes());
        simpleBean.setTestfloat(9);
        simpleBean.setTestboolean(false);
        simpleBean.setTestchar('a');

        simpleBean.setTestInteger(2);
        simpleBean.setTestDouble(4.0);
        simpleBean.setTestLong(6L);
        simpleBean.setTestShort((short) 8);
        simpleBean.setTestByte("Test".getBytes()[0]);
        simpleBean.setTestBytes(new Byte[10]); // TODO
        simpleBean.setTestFloat((float) 10);
        simpleBean.setTestBoolean(true);
        simpleBean.setTestCharacter('b');

        //simple bean to parse objects using accessors and mutators
        ParseObject parseObject = marshaller.marshall(simpleBean);
        Window.alert(">>>>>>>>" + parseObject.toString());
        //get non null values from model
        //populate expectation list
        //assert test expectation to mutant
        Window.alert("BEGIN TEST-----------------MARSHALL");

        //assertEquals("test-object-id", parseObject.getString("objectId"));
        //assertEquals("testint", parseObject.get("objectId"));

        /*
        Display values
         */
        Class<?> archetype = simpleBean.getClass();
        Set<?> s = parseObject.keySet();
        //iterate and persist keys from model
        Iterator<?> i = s.iterator();
        do {
            String k = i.next().toString();
            Field[] fields = GwtReflect.getDeclaredFields(archetype);
            for (int c = 0; c < fields.length; c++) {
                if (k == fields[c].getName()) {
                    String message = null;
                    try {
                        Object expectableparse = null;
                        try {
                            expectableparse = parseObject.getString(k);
                            message = k + " " + parseObject.getString(k) + " <->" + " " + fields[c].getName() + "  " + fields[c].get(simpleBean);
                        } catch (Exception e) {
                        }
                        if (expectableparse == null) {
                            expectableparse = parseObject.get(k).toString();
                            message = k + " " + parseObject.get(k).toString() + " <->" + " " + fields[c].getName() + "  " + fields[c].get(simpleBean);
                        }
                        Object expectablefield = fields[c].get(simpleBean).toString();
                        if (expectablefield == expectableparse) {
                            message = "PASS: " + k + " " + expectableparse + " <-> " + " " + fields[c].getName() + "  " + fields[c].get(simpleBean);
                        } else {
                            message = "FAIL: " + k + " " + expectableparse + " <-> " + " " + fields[c].getName() + "  " + fields[c].get(simpleBean);
                        }
                        assertEquals(expectablefield, expectableparse);
                    } catch (Exception e) {
                        //Window.alert(e.toString());
                    }
                    Window.alert(message);
                }
            }
        } while (i.hasNext());
    }

//    public void testParentChildMarshaller() {
//        Window.alert("WRAPPING CLASSES");
//        //GwtReflect.magicClass(ChildBean.class);
//        GwtReflect.magicClass(ParentBean.class);
//
//
//
//        Window.alert("SETTING VALUES");
//        ParentBean parentBean = new ParentBean();
//
//        parentBean.setAge(99);
//        parentBean.array = new Array();
//        parentBean.array.putString(0,"a");
//        parentBean.array.putString(1,"b");
//        parentBean.array.putString(2,"c");
//
//        Window.alert(parentBean.array.toString());
//        ChildBean first = new ChildBean();
//
//        first.setName("First");
//        first.setAge(10);
//        first.setACL(new ParseACL());
//        first.setBirthdate(new ParseDate("2017-06-23T02:59:59.255Z"));
//        first.setFile(new ParseFile()); // TODO next
//        JSONObject geoPointRef = new JSONObject();
//        geoPointRef.put("longitude", new JSONNumber(100.10));
//        geoPointRef.put("latitude", new JSONNumber(200.10));
//        //first.setGeoPoint(ParseGeoPoint.clone(geoPointRef));
//
//        Window.alert("SETTING PARSEOBJECT");
//        ParseRelation relation = new ParseRelation(new ParseObject("TestObject"));
//        first.setRelation(relation);
//
//        ParseObject parseObjectRef = new ParseObject("ReferenceObject");
//        parseObjectRef.setObjectId("0");
//        first.setPointer(new ParsePointer(parseObjectRef));
//        ParseRole role = new ParseRole("parserole");
//        first.setRole(role);
//        first.setPointer(new ParsePointer());
//
//        ChildBean second = new ChildBean();
//        ChildBean third = new ChildBean();
//
//        parentBean.setFavorite(first);
//        parentBean.setChildren(Arrays.asList(first,second,third));
//
//
//        Window.alert("BEGIN MARSHALL-----------------MARSHALL2");
//        GwtMarshaller marshaller = GWT.create(GwtMarshaller.class);
//
//        ParseObject parseObject = marshaller.marshall(parentBean);
//        Window.alert(">>>>>>>>" + parseObject.toString());
//
//        Window.alert("BEGIN TEST-----------------MARSHALL2");
//
//        Class<?> archetype = parentBean.getClass();
//        Set<?> s = parseObject.keySet();
//        //iterate and persist keys from model
//        Iterator<?> i = s.iterator();
//        do {
//            String k = i.next().toString();
//            Field[] fields = GwtReflect.getDeclaredFields(archetype);
//            for (int c = 0; c < fields.length; c++) {
//                if (k == fields[c].getName()) {
//                    String message = null;
//                    try {
//                        Object expectableparse = null;
//                        try {
//                            expectableparse = parseObject.getString(k);
//                            message = k + " " + parseObject.getString(k) + " <->" + " " + fields[c].getName() + "  " + fields[c].get(parentBean);
//                        } catch (Exception e) {
//                        }
//                        if (expectableparse == null) {
//                            expectableparse = parseObject.get(k).toString();
//                            message = k + " " + parseObject.get(k).toString() + " <->" + " " + fields[c].getName() + "  " + fields[c].get(parentBean);
//                        }
//                        Object expectablefield = fields[c].get(parentBean).toString();
//                        if (expectablefield == expectableparse) {
//                            message = "PASS: " + k + " " + expectableparse + " <-> " + " " + fields[c].getName() + "  " + fields[c].get(parentBean);
//                        } else {
//                            message = "FAIL: " + k + " " + expectableparse + " <-> " + " " + fields[c].getName() + "  " + fields[c].get(parentBean);
//                        }
//                        //assertEquals(expectablefield, expectableparse);
//                    } catch (Exception e) {
//                        //Window.alert(e.toString());
//                    }
//                    Window.alert(message);
//                }
//            }
//        } while (i.hasNext());
//    }

    public static void log(String s) {
        System.out.println(s);
    }

}
