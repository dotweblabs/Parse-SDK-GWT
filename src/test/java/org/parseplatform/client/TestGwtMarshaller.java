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
import org.parseplatform.client.beans.ChildBean;
import org.parseplatform.client.beans.ParentBean;
import org.parseplatform.client.beans.SimpleBean;
import org.parseplatform.types.*;

import java.lang.reflect.Field;
import java.util.*;

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

        simpleBean.setFile(new ParseFile("sample.txt", "http://localhost:8080/sample.txt"));
        simpleBean.setGeoPoint(new ParseGeoPoint(100.10, 200.10));
        simpleBean.setPointer(new ParsePointer("TestObject", "test-object-id"));
        simpleBean.setRelation(new ParseRelation("TestObject"));
        //simple bean to parse objects using accessors and mutators
        ParseObject parseObject = marshaller.marshall(simpleBean);


        //Test ParsePointer
//        assertNotNull(parseObject.getJSONObject("pointer"));
//        JSONObject parsePointerObject = parseObject.getJSONObject("pointer");
//        ParsePointer parsePointer = new ParsePointer(parsePointerObject);
        //Test ParseRelation
//        assertNotNull(parseObject.getJSONObject("relation"));
//        JSONObject parseRelationObject = parseObject.getJSONObject("relation");
//        ParseRelation parseRelation = new ParseRelation((ParseObject) parseRelationObject);
//        assertNotNull(parseRelation);
//        assertEquals("TextObject", parseRelation.getClassName() );


        Window.alert(">>>>>>>> TEST SIMPLE BEAN" + parseObject.toString());
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
        assertNotNull(parseObject.getJSONObject("file"));

        JSONObject parseFileObject = parseObject.getJSONObject("file");
        ParseFile parseFile = new ParseFile(parseFileObject);
        assertNotNull(parseFile);
        assertEquals("sample.txt", parseFile.getName());
        assertEquals("http://localhost:8080/sample.txt", parseFile.getUrl());

        //Test ParseGeoPoint
        Window.alert("test " + parseObject.getJSONObject("geoPoint"));

        assertNotNull(parseObject.getJSONObject("geoPoint"));

        JSONObject geoPointObj = parseObject.getJSONObject("geoPoint");
        ParseGeoPoint parseGeoPoint = new ParseGeoPoint(geoPointObj);
        Window.alert("test " + geoPointObj);

        assertNotNull(geoPointObj);

        Window.alert(geoPointObj.toString());



        Window.alert(parseGeoPoint.toString());
        assertNotNull(parseGeoPoint);
        assertEquals(100.10, parseGeoPoint.getLongitude());
        assertEquals(200.10, parseGeoPoint.getLatitude());
    }

    public void testParentChildMarshaller() {
        Window.alert("WRAPPING CLASSES");
        //GwtReflect.magicClass(ChildBean.class);
        GwtReflect.magicClass(ParentBean.class);
        GwtReflect.magicClass(ChildBean.class);


        Window.alert("SETTING VALUES");
        ParentBean parentBean = new ParentBean();

        parentBean.setAge(99);
        parentBean.array = new Array();
        parentBean.array.putString(0, "a");
        parentBean.array.putString(1, "b");
        parentBean.array.putString(2, "c");

        Window.alert(parentBean.array.toString());
        ChildBean first = new ChildBean();

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

        first.setName("First");
        first.setAge(10);
        first.setACL(new ParseACL());
        first.setBirthdate(new ParseDate("2017-06-23T02:59:59.255Z"));
        //first.setFile(new ParseFile()); // TODO next
        first.setGeoPoint(new ParseGeoPoint(100.10, 200.10));

        Window.alert("SETTING PARSEOBJECT");
        ParseRelation relation = new ParseRelation(new ParseObject("TestObject"));
        first.setRelation(relation);
        relation = new ParseRelation(new ParseObject("Parent"));
        parentBean.setRelation(relation);
        //ParseObject parseObjectRef = new ParseObject("ReferenceObject");
        //parseObjectRef.setObjectId("0");
        // first.setPointer(new ParsePointer(parseObjectRef));
        first.setPointer(new ParsePointer("ReferenceObject", "0"));
        ParseRole role = new ParseRole("parserole");
        first.setRole(role);
        first.setPointer(new ParsePointer());



        ChildBean second = new ChildBean();
        ChildBean third = new ChildBean();

        ParseACL acl = new ParseACL();
        acl.setPublicWriteAccess(true);
        acl.setPublicReadAccess(true);

        parentBean.acl = acl;

        parentBean.setFavorite(first);

        parentBean.children = new LinkedList<>();
        parentBean.children.add(first);
        parentBean.children.add(second);
        parentBean.children.add(third);

        parentBean.setRole(role);

        ParsePointer pointer = new ParsePointer("pointer", "1234567890");
        ParseDate date = new ParseDate("2017-06-23T02:59:59.255Z");


        parentBean.setPointer(pointer);
        parentBean.date = date;

        File parentFILE = new File();
        parentFILE.name = "parent file";
        parentFILE.url = "C:\\PARENTFILE.FILE";
        parentBean.plainfile = parentFILE;

        GeoPoint parentGEO = new GeoPoint();
        parentGEO.latitude = 1.1;
        parentGEO.longitude = 1.1;
        parentBean.plaingeopoint = parentGEO;

        Pointer parentPointer = new Pointer();
        parentPointer.setClassName("pointer");
        parentPointer.setObjectId("1234567890");
        parentBean.plainpointer = parentPointer;

        Relation parentRelation = new Relation();
        parentRelation.setClassName("parent relation");
        parentBean.plainrelation = parentRelation;

        //parentBean.setChildren(Arrays.asList(first,second,third));


        Window.alert("BEGIN MARSHALL-----------------MARSHALL2");
        GwtMarshaller marshaller = GWT.create(GwtMarshaller.class);

        ParseObject parseObject = marshaller.marshall(parentBean);
        Window.alert(">>>>>>>> TESTPARENTCHILDMARSHALL" + parseObject.toString());

        Window.alert("BEGIN TEST-----------------MARSHALL2");

        Class<?> archetype = parentBean.getClass();
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
                            message = k + " " + parseObject.getString(k) + " <->" + " " + fields[c].getName() + "  " + fields[c].get(parentBean);
                        } catch (Exception e) {
                        }
                        if (expectableparse == null) {
                            expectableparse = parseObject.get(k).toString();
                            message = k + " " + parseObject.get(k).toString() + " <->" + " " + fields[c].getName() + "  " + fields[c].get(parentBean);
                        }
                        Object expectablefield = fields[c].get(parentBean).toString();
                        if (expectablefield == expectableparse) {
                            message = "PASS: " + k + " " + expectableparse + " <-> " + " " + fields[c].getName() + "  " + fields[c].get(parentBean);
                        } else {
                            message = "FAIL: " + k + " " + expectableparse + " <-> " + " " + fields[c].getName() + "  " + fields[c].get(parentBean);
                        }
                        //assertEquals(expectablefield, expectableparse);
                    } catch (Exception e) {
                        //Window.alert(e.toString());
                    }
                    Window.alert(message);
                }
            }
        } while (i.hasNext());
        JSONObject relJSON = parseObject.getJSONObject("relation");
        //ParseObject relOBJECT = new ParseObject( relJSON);
        JSONObject aclJSON = parseObject.getJSONObject("acl");
        //ParseObject aclOBJECT = new ParseObject( aclJSON);
        JSONObject pointerJSON = parseObject.getJSONObject("pointer");
        //ParseObject pointerOBJECT = new ParseObject( aclJSON);


        JSONValue dateVALUE =  parseObject.getJSONObject("date");
        JSONObject dateOBJECT = dateVALUE.isObject();
        ParseObject parseDate = new ParseObject(dateOBJECT);

        JSONValue plainfileVALUE =  parseObject.getJSONObject("plainfile");
        JSONObject plainfileOBJECT = plainfileVALUE.isObject();
        ParseObject parseplainfile= new ParseObject(plainfileOBJECT);

        JSONValue plainGEOVALUE=  parseObject.getJSONObject("plaingeopoint");
        JSONObject plainGEOOBJECT = plainGEOVALUE.isObject();
        ParseObject parseplaingeo= new ParseObject(plainGEOOBJECT);

        JSONValue plainPOINTERVALUE=  parseObject.getJSONObject("plainpointer");
        JSONObject plainPOINTEROBJECT = plainPOINTERVALUE.isObject();
        ParseObject parseplainpointer= new ParseObject(plainPOINTEROBJECT);

        JSONValue plainRELVALUE=  parseObject.getJSONObject("plainrelation");
        JSONObject plainRELOBJECT = plainRELVALUE.isObject();
        ParseObject parseplainrelation= new ParseObject(plainRELOBJECT);

        JSONArray childrenARRAY = parseObject.getJSONArray("children");
        JSONObject child = childrenARRAY.get(0).isObject();
        ParseObject childname = new ParseObject(child);

        assertEquals(relJSON, parseObject.get("relation"));
        assertEquals(aclJSON, parseObject.get("acl"));
        assertEquals("2017-06-23T02:59:59.255Z", parseDate.getString("iso"));
        assertEquals("parent file", parseplainfile.getString("name"));
        assertEquals(1.1, Double.parseDouble(parseplaingeo.get("latitude").toString()));
        assertEquals("parent relation", parseplainrelation.getString("className"));
        assertEquals("First", childname.getString("name"));
    }

    public static void log(String s) {
        System.out.println(s);
    }

}
