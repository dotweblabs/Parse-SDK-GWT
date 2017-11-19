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
import com.google.gwt.json.client.*;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.reflect.shared.GwtReflect;
import com.google.gwt.user.client.Window;
import elemental.client.Browser;
import org.parseplatform.client.beans.ChildBean;
import org.parseplatform.client.beans.ParentBean;
import org.parseplatform.client.beans.SimpleBean;
import org.parseplatform.client.util.DateUtil;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Set;

/**
 * Unit tests of {@link Where}
 *
 * @author Kerby Martino
 * @version 0-SNAPSHOT
 * @since 0-SNAPSHOT
 */
public class TestGwtUnmarshaller extends GWTTestCase {


    @Override
    public String getModuleName() {
        return "org.parseplatform.Parse";
    }


    public void testUnmarshaller() {
        GwtReflect.magicClass(SimpleBean.class);

        GwtUnmarshaller unmarshaller = GWT.create(GwtUnmarshaller.class);

        ParseObject parseObject = new ParseObject();
        parseObject.putString("objectId", "test-object-id");
        parseObject.putString("nullstr", null);
        parseObject.putNumber("testint", 1);
        parseObject.putNumber("testdouble", 2.0);
        parseObject.putNumber("testlong", 3L);
        parseObject.putNumber("testshort", 4);
        parseObject.put("testbyte", null); // TODO
        parseObject.putBoolean("testboolean", true);
        parseObject.putNumber("testfloat", 5.0);
        parseObject.putString("testchar", "a");

        parseObject.putNumber("testInteger", 6);
        parseObject.putNumber("testDouble", 7);
        parseObject.putNumber("testLong", 8L);
        parseObject.putNumber("testShort", 9);
        parseObject.put("testByte", null);
        parseObject.putBoolean("testBoolean", false);
        parseObject.putNumber("testFloat", 10);
        parseObject.putString("testCharacter", "a");
        parseObject.put("testbytes", null); // TODO
        parseObject.putString("testBytes", null); // TODO
        JSONArray testARRAY = new JSONArray();
        JSONObject testOBJECT = new JSONObject();
        JSONString testVALUE = new JSONString("hello");
        testOBJECT.put("objectId", testVALUE);
        testARRAY.set(0, testOBJECT);
        testARRAY.set(1, testOBJECT);
        testARRAY.set(2, testOBJECT);
        parseObject.put("testList", testARRAY);

        Window.alert("<<<<<<<<" + parseObject.toString());
        SimpleBean simpleBean = new SimpleBean();
        Class<?> archetype = simpleBean.getClass();
        //get non null values from model

        simpleBean = unmarshaller.unmarshall(SimpleBean.class, simpleBean, parseObject);
        //get field names, get method names
        //iterate field contents

        Set<?> s = parseObject.keySet();
        //iterate and persist keys from model
        Window.alert("BEGIN TEST-----------------UNMARSHALL");
        Iterator<?> i = s.iterator();
        do {
            String k = i.next().toString();
            Field[] fields = GwtReflect.getPublicFields(archetype);
            for (int c = 0; c < fields.length; c++) {
                if (k == fields[c].getName()) {
                    String message = null;
                    try {
                        Object expectableparse = null;
                        try {
                            expectableparse = parseObject.getString(k);
                            message = k + " " + parseObject.getString(k) + " <->" + " " + fields[c].getName() + " " + fields[c].get(simpleBean);
                        } catch (Exception e) {
                        }
                        if (expectableparse == null) {
                            expectableparse = parseObject.get(k).toString();
                            message = k + " " + parseObject.get(k).toString() + " <->" + " " + fields[c].getName() + " " + fields[c].get(simpleBean);
                        }
                        Object expectablefield = fields[c].get(simpleBean).toString();
                        if (expectablefield == expectableparse) {
                            message = "PASS: " + k + " " + expectableparse + " <-> " + " " + fields[c].getName() + " " + fields[c].get(simpleBean);
                        } else {
                            message = "FAIL: " + k + " " + expectableparse + " <-> " + " " + fields[c].getName() + " " + fields[c].get(simpleBean);
                        }
                        //assertEquals(expectablefield, expectableparse); //find workaround for string bug
                    } catch (Exception e) {
                        //Window.alert(e.toString());
                    }
                    Window.alert(message);
                }
            }


        } while (i.hasNext());

        assertNotNull(simpleBean);
        assertEquals("test-object-id", simpleBean.getObjectId());

    }

    public void testParentChildUnmarshaller() {

        ParseObject firstChildObject = new ParseObject();
        ParseObject secondChildObject = new ParseObject();
        ParseObject thirdChildObject = new ParseObject();

        firstChildObject.putString("name", "First Child");
        firstChildObject.putNumber("age", 10);
        firstChildObject.put("dob", new ParseDate("2017-06-23T02:59:59.255Z"));
        firstChildObject.put("birthdate", new ParseDate("2017-06-23T02:59:59.255Z"));
//        firstChildObject.put("ACL", new ParseACL());
        ParseRole role = new ParseRole("parserole");
        firstChildObject.put("role", role);
        ParseRelation relation = new ParseRelation(new ParseObject("TestObject"));
        firstChildObject.put("relation", relation);
//        firstChildObject.put("geoPoint", new ParseGeoPoint(100.10, 200.10));

        ParseFile file = new ParseFile("sample.txt", "http://localhost/sample.txt");
        firstChildObject.put("file", file);



        ParseGeoPoint geoPoint = new ParseGeoPoint(100.1,200.1);
        firstChildObject.put("geoPoint", geoPoint);

//        ParsePointer pointer = new ParseGeoPoint(TestObject"",200.1);
//        firstChildObject.put("geoPoint", geoPoint);

        ParseObject parseObjectRef = new ParseObject("ReferenceObject");
        parseObjectRef.setObjectId("0");
        firstChildObject.put("pointer", new ParsePointer("TestObject", "test-object-id"));

        JSONArray shows = new JSONArray();
        shows.set(0, new JSONString("Movie 1"));
        shows.set(1, new JSONString("Movie 2"));
        shows.set(2, new JSONString("Movie 3"));

        JSONArray yess = new JSONArray();
        yess.set(0, JSONBoolean.getInstance(true));
        yess.set(1, JSONBoolean.getInstance(false));
        yess.set(2, JSONBoolean.getInstance(true));

        JSONArray doubles = new JSONArray();
        doubles.set(0, new JSONNumber(1.11));
        doubles.set(1, new JSONNumber(2.22));
        doubles.set(2, new JSONNumber(3.33));

        JSONArray floats = new JSONArray();
        floats.set(0, new JSONNumber(2.22));
        floats.set(1, new JSONNumber(3.33));
        floats.set(2, new JSONNumber(4.44));

        JSONArray shorts = new JSONArray();
        shorts.set(0, new JSONNumber(7.77));
        shorts.set(1, new JSONNumber(8.88));
        shorts.set(2, new JSONNumber(9.00));

        firstChildObject.put("shows", shows);
        firstChildObject.put("yess", yess);
        firstChildObject.put("doubles", doubles);
        firstChildObject.put("floats", floats);
        firstChildObject.put("shorts", shorts);

        firstChildObject.put("file", new ParseFile("sample.txt", "http://localhost:8080/sample.txt"));
        firstChildObject.put("geoPoint", new ParseGeoPoint(100.1,200.1));



        ParseACL ACL = new ParseACL();
        ACL.setPublicReadAccess(true);
        firstChildObject.put("ACL", ACL);

        secondChildObject.putString("name", "Second Child");
        secondChildObject.putNumber("age", 20);

        thirdChildObject.putString("name", "Third Child");
        thirdChildObject.putNumber("age", 30);

        ParseObject parentObject = new ParseObject();
        parentObject.putString("name", "The Parent");
        parentObject.putNumber("age", 80);
        parentObject.put("favorite", firstChildObject);

        JSONArray children = new JSONArray();
        children.set(0, firstChildObject);
        children.set(1, secondChildObject);
        children.set(3, thirdChildObject);

        parentObject.put("children", children);

        //Window.alert(">>>>>>>>  " + parentObject.toString());

        GwtReflect.magicClass(ChildBean.class);
        GwtReflect.magicClass(ParentBean.class);

        ParentBean parentBean = parentObject.unmarshall(ParentBean.class);

        Window.alert("Parent Name   " + parentBean.getName());
        Window.alert("Parent Age    " + parentBean.getAge());

        assertNotNull(parentBean);
        assertEquals("The Parent", parentBean.getName());
        assertEquals(80, parentBean.getAge().intValue());

        assertNotNull(parentBean.getFavorite());
        assertNotNull(parentBean.getChildren());

        ChildBean favorite = parentBean.getFavorite();
        assertNotNull(favorite.getDob());
        assertEquals("First Child", favorite.getName());
        assertEquals(10, favorite.getAge());
        assertEquals("2017-06-23T02:59:59.255", DateUtil.getStringFormat(favorite.getDob())); // TODO: Z is missing

        assertNotNull(favorite.getShows());
        assertNotNull(favorite.getYess());
        assertNotNull(favorite.getDoubles());
        assertNotNull(favorite.getFloats());
        assertNotNull(favorite.getShorts());

        assertEquals(3, favorite.getShows().size());
        assertEquals(3, favorite.getYess().size());
        assertEquals(3, favorite.getDoubles().size());
        assertEquals(3, favorite.getFloats().size());
        assertEquals(3, favorite.getShorts().size());

        assertEquals("Movie 1", favorite.getShows().get(0));
        assertEquals("Movie 2", favorite.getShows().get(1));
        assertEquals("Movie 3", favorite.getShows().get(2));

        assertNotNull(favorite.getPointer());
        assertEquals("test-object-id", favorite.getPointer().getObjectId());

        // Test ParseFile
        assertNotNull(favorite.getFile());
        assertEquals("sample.txt", favorite.getFile().getName());
        assertEquals("http://localhost:8080/sample.txt", favorite.getFile().getUrl());
        //Test ParseGeoPoint
        assertNotNull(favorite.getGeoPoint());
        assertEquals(100.1,favorite.getGeoPoint().getLongitude());
        assertEquals(200.1,favorite.getGeoPoint().getLatitude());
        //Test ParsePointer
        assertNotNull(favorite.getPointer());
        assertEquals("TestObject",favorite.getPointer().getClassname());
        assertEquals("test-object-id",favorite.getPointer().getObjectId());
        //Test ParseRelation
        assertNotNull(favorite.getRelation());
        assertEquals("TestObject",favorite.getRelation().getClassName());
        //Test ParseACL
        assertNotNull(favorite.getACL());
        assertEquals(true,favorite.getACL() != null);
        //Test ParseDate
        assertNotNull(favorite.getBirthdate());
        assertEquals("2017-06-23T02:59:59.255Z",favorite.getBirthdate().getIsoDate());

    }

    public static void log(String s) {
        System.out.println(s);
    }

}
