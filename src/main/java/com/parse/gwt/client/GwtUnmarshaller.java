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

import com.google.gwt.core.client.GWT;
import com.google.gwt.json.client.*;
import com.parse.gwt.client.annotations.Column;
import com.parse.gwt.client.annotations.Entity;
import com.parse.gwt.client.annotations.ObjectId;
import com.parse.gwt.client.types.*;
import com.parse.gwt.client.util.AnnotationUtil;
import com.parse.gwt.client.util.DateUtil;
import com.promis.rtti.client.RttiField;
import elemental.client.Browser;

import java.lang.annotation.Annotation;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 * GWT ParseObject to Object unmarshaller
 *
 * @author Kerby Martino
 * @since 0-SNAPSHOT
 * @version 0-SNAPSHOT
 */
public class GwtUnmarshaller implements Unmarshaller {
    @Override
    public <T> T unmarshall(Class<T> clazz, Object instance, ParseObject parseObject) {
        if(parseObject == null) {
            throw new RuntimeException("ParseObject cannot be null");
        }
        try {
            AnnotationUtil.AnnotatedField objectIdField = AnnotationUtil.getFieldWithAnnotation(ObjectId.class, instance);
            if(objectIdField != null) {
                RttiField rttiField = objectIdField.getField();
                String objectId = parseObject.getObjectId();
                rttiField.set(instance, objectId);
            }
            List<AnnotationUtil.AnnotatedField> annotatedFields = AnnotationUtil.getFieldsWithAnnotation(Column.class, instance);
            for(AnnotationUtil.AnnotatedField annotatedField : annotatedFields) {
                RttiField rttiField = annotatedField.getField();
                Column anno = (Column) annotatedField.annotation();
                String fieldName = rttiField.getName();
                Class classType =  annotatedField.getFieldType();
                if(anno != null && anno.name() != null && !anno.name().isEmpty()) {
                    fieldName = anno.name();
                }
                Browser.getWindow().getConsole().log("Field name: " + fieldName);
                if(classType.getName().equals(String.class.getName())) {
                    Browser.getWindow().getConsole().log("String type found");
                    JSONValue value = parseObject.get(fieldName);
                    if(value != null && value.isString() != null && value.isString().stringValue() != null) {
                        rttiField.set(instance, value.isString().stringValue());
                    }
                } else if(classType.getName().equals(Boolean.class.getName())
                        || classType.getName().equals(boolean.class.getName())) {
                    Browser.getWindow().getConsole().log("Boolean type found");
                    JSONValue value = parseObject.get(fieldName);
                    if(value != null && value.isBoolean() != null) {
                        rttiField.set(instance, value.isBoolean().booleanValue());
                    }
                } else if(classType.equals(Double.class) || classType.equals(Integer.class) || classType.equals(Long.class)) {
                    Browser.getWindow().getConsole().log("Number type found");
                    JSONValue value = parseObject.get(fieldName);
                    if(value != null && value.isNumber() != null) {
                        Double d = new Double(value.isNumber().doubleValue());
                        rttiField.set(instance, d);
                    }
                } else if(classType.equals(double.class) || classType.equals(int.class) || classType.equals(long.class)) {
                    Browser.getWindow().getConsole().log("Number type found");
                    JSONValue value = parseObject.get(fieldName);
                    if(value != null && value.isNumber() != null) {
                        Double d = new Double(value.isNumber().doubleValue());
                        rttiField.set(instance, d);
                    }
                } else if(classType.equals(Date.class)) {
                    Browser.getWindow().getConsole().log("Date type found");
                    JSONValue value = parseObject.get(fieldName);
                    if(value != null && value.isObject() != null
                            && value.isObject().get("__type") != null
                            && value.isObject().get("__type").isString() != null
                            && value.isObject().get("__type").isString().stringValue().equals("Date")) {
                        String dateString = value.isObject().get("iso").isString() != null ? value.isObject().get("iso").isString().stringValue() : null;
                        Date date = DateUtil.iso8601String(dateString);
                        rttiField.set(instance, date);
                    }
                } else if(classType.equals(Map.class)) { // Object
                    Browser.getWindow().getConsole().log("Map type found");
                    throw new RuntimeException("Map is not supported use com.parse.gwt.client.types.Object instead");
                } else if(classType.equals(List.class)) {
                    Browser.getWindow().getConsole().log("List type found");
                    throw new RuntimeException("List is not supported use com.parse.gwt.client.types.Array instead");
                } else if(classType.equals(File.class)) {
                    Browser.getWindow().getConsole().log("File type found");
                    JSONValue value = parseObject.get(fieldName);
                    if(value != null && value.isObject() != null
                            && value.isObject().get("__type") != null
                            && value.isObject().get("__type").isString() != null
                            && value.isObject().get("__type").isString().stringValue().equals("File")) {
                        String name = value.isObject().get("name").isString() != null ? value.isObject().get("name").isString().stringValue() : null;
                        String url = value.isObject().get("url").isString() != null ? value.isObject().get("url").isString().stringValue() : null;
                        File file = new File();
                        file.url = url;
                        file.name = name;
                        rttiField.set(instance, file);
                    }
                } else if(classType.equals(GeoPoint.class)) {
                    Browser.getWindow().getConsole().log("GeoPoint type found");
                    JSONValue value = parseObject.get(fieldName);
                    if(value != null && value.isObject() != null
                            && value.isObject().get("__type") != null
                            && value.isObject().get("__type").isString() != null
                            && value.isObject().get("__type").isString().stringValue().equals("GeoPoint")) {
                        double latitude = value.isObject().get("latitude").isNumber() != null ? value.isObject().get("latitude").isNumber().doubleValue() : null;
                        double longitude = value.isObject().get("longitude").isNumber() != null ? value.isObject().get("longitude").isNumber().doubleValue() : null;
                        GeoPoint geoPoint = new GeoPoint();
                        geoPoint.latitude = Double.valueOf(latitude);
                        geoPoint.longitude = Double.valueOf(longitude);
                        rttiField.set(instance, geoPoint);
                    }
                } else if(classType.equals(Pointer.class)) {
                    Browser.getWindow().getConsole().log("Pointer type found");
                    JSONValue value = parseObject.get(fieldName);
                    if(value != null && value.isObject() != null
                            && value.isObject().get("__type") != null
                            && value.isObject().get("__type").isString() != null
                            && value.isObject().get("__type").isString().stringValue().equals("Pointer")) {
                        String className = value.isObject().get("className").isString() != null ? value.isObject().get("className").isString().stringValue() : null;
                        String objectId = value.isObject().get("objectId").isString() != null ? value.isObject().get("objectId").isString().stringValue() : null;
                        Pointer pointer = new Pointer();
                        pointer.className = className;
                        pointer.objectId = objectId;
                        rttiField.set(instance, pointer);
                    }
                } else if(classType.equals(Relation.class)) {
                    Browser.getWindow().getConsole().log("Relation type found");
                    JSONValue value = parseObject.get(fieldName);
                    if(value != null && value.isObject() != null
                            && value.isObject().get("__type") != null
                            && value.isObject().get("__type").isString() != null
                            && value.isObject().get("__type").isString().stringValue().equals("Relation")) {
                        String className = value.isObject().get("className").isString() != null ? value.isObject().get("className").isString().stringValue() : null;
                        Relation relation = new Relation();
                        relation.className = className;
                        rttiField.set(instance, relation);
                    }
                } else if(classType.equals(Array.class)) {
                    Browser.getWindow().getConsole().log("Array type found");
                    JSONValue value = parseObject.get(fieldName);
                    if(value != null && value.isArray() != null) {
                        Array array = new Array();
                        int size = value.isArray().size();
                        for(int i=0;i<size;i++) {
                            JSONValue arrayValue = value.isArray().get(i);
                            if(arrayValue.isArray() != null) {
                                array.putArray(i, arrayValue.isArray());
                            } else if(arrayValue.isObject() != null) {
                                array.putObject(i, arrayValue.isObject());
                            } else if(arrayValue.isString() != null) {
                                array.putString(i, arrayValue.isString().stringValue());
                            } else if(arrayValue.isBoolean() != null) {
                                array.putBoolean(i, arrayValue.isBoolean().booleanValue());
                            } else if(arrayValue.isNumber() != null) {
                                array.putNumber(i, new Double(arrayValue.isNumber().doubleValue()));
                            } else if(arrayValue.isNull() != null) {
                                array.putNull(i);
                            }
                        }
                        rttiField.set(instance, array);
                    }
                } else if(classType.getName().equals(Objek.class.getName())) {
                    Browser.getWindow().getConsole().log("Object type found");
                    JSONValue value = parseObject.get(fieldName);
                    if(value != null && value.isObject() != null) {
                        Objek objek = new Objek();
                        Iterator<String> it = value.isObject().keySet().iterator();
                        while(it.hasNext()) {
                            String key = it.next();
                            objek.put(key, value.isObject().get(key));
                        }
                        rttiField.set(instance, objek);
                    }
                } else {
                    throw new RuntimeException("Unsupported type for field: " + fieldName);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (T) instance;
    }
}
