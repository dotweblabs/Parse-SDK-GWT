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
import com.parse.gwt.client.annotations.ObjectId;
import com.parse.gwt.client.util.AnnotationUtil;

import java.lang.annotation.Annotation;
import java.util.List;

public class AnnotationTest extends GWTTestCase {
    @Override
    public String getModuleName() {
        return "com.parse.gwt.Parse";
    }
    public void testFunction() {
        TestObject testObject = new TestObject();
        testObject.setId("sample-object-id");
        List<Annotation> annotationList = AnnotationUtil.getAnnotations(testObject);
        assertFalse(annotationList.isEmpty());
    }
    public void testFieldWithAnnotation() {
        TestObject testObject = new TestObject();
        testObject.setId("sample-object-id");
        AnnotationUtil.AnnotatedField field = AnnotationUtil.getFieldWithAnnotation(ObjectId.class, testObject);
        assertNotNull(field);
    }
    public void testSetFieldWithAnnotation() {
        TestObject testObject = new TestObject();
        testObject.setId("sample-object-id");
        AnnotationUtil.AnnotatedField field = AnnotationUtil.getFieldWithAnnotation(ObjectId.class, testObject);
        field.getField().set(testObject, "new-sample-object-id");
        assertNotNull(field);
        assertEquals("new-sample-object-id", testObject.getId());
    }
}
