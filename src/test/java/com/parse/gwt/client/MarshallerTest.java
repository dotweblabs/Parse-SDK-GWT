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
package com.parse.gwt.client;

import com.google.gwt.junit.client.GWTTestCase;
import com.parse.gwt.client.types.*;
import com.parse.gwt.client.types.Objek;

import java.util.Map;
import java.util.HashMap;


public class MarshallerTest extends GWTTestCase {
    @Override
    public String getModuleName() {
        return "com.parse.gwt.Parse";
    }

    public void testMarshall() {
        ParseObject parseObject = createParseObject();
        assertNotNull(parseObject);
        assertEquals("sample-object-id", parseObject.getObjectId());
        System.out.println(parseObject.toString());
    }
    public void testUnmarshall() {
        TestObject testObject = new TestObject();
        ParseObject parseObject = createParseObject();
        System.out.println("Parse Object = " + parseObject.toString());
        Parse.unmarshaller().unmarshall(TestObject.class, testObject, parseObject);
        assertNotNull(parseObject);
        assertEquals("sample-object-id", testObject.getId());
//        assertEquals(testObject.getSomething(), null);
        assertEquals(0.0, testObject.geoPoint.latitude);
        assertEquals(1.1, testObject.geoPoint.longitude);
        assertEquals("File Name", testObject.file.name);
        assertEquals("http://url.com/file.txt", testObject.file.url);
        assertEquals("test-object-id", testObject.pointer.objectId);
        assertEquals("TestObject", testObject.pointer.className);
        assertEquals("NewTestObject", testObject.relation.className);
        assertEquals(0.0, testObject.array.get(0).isNumber().doubleValue());
        assertEquals(1.1, testObject.array.get(1).isNumber().doubleValue());
        assertEquals(2.2, testObject.array.get(2).isNumber().doubleValue());
        assertEquals("_TestObject", parseObject.getClassName());
    }
    private ParseObject createParseObject() {
        TestObject testObject = new TestObject();
        testObject.setId("sample-object-id");
        Map<String, Object> map = new HashMap<>();
        map.put("child-1", "Child 1");
        map.put("child-2", "Child 2");

        File file = new File();
        GeoPoint geoPoint = new GeoPoint();
        Pointer pointer = new Pointer();
        Relation relation = new Relation();

        file.name = "File Name";
        file.url = "http://url.com/file.txt";

        geoPoint.latitude = 0.0;
        geoPoint.longitude = 1.1;

        pointer.objectId = "test-object-id";
        pointer.className = "TestObject";

        relation.className = "NewTestObject";

        testObject.file = file;
        testObject.geoPoint = geoPoint;
        testObject.pointer = pointer;
        testObject.relation = relation;

        Objek object = new Objek();
        object.putBoolean("isObject", true);

        Array array = new Array();
        array.putNumber(0, 0.0);
        array.putNumber(1, 1.1);
        array.putNumber(2, 2.2);

        testObject.array = array;
        testObject.object = object;

        //testObject.setChildren(map);
        ParseObject parseObject = Parse.marshaller().marshall(testObject);
        return parseObject;
    }
}
