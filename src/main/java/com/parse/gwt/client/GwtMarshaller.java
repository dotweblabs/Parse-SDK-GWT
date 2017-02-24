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

import com.google.gwt.json.client.*;
import com.parse.gwt.client.annotations.Column;
import com.parse.gwt.client.annotations.Entity;
import com.parse.gwt.client.annotations.ObjectId;
import com.parse.gwt.client.types.*;
import com.parse.gwt.client.types.Objek;
import com.parse.gwt.client.util.AnnotationUtil;
import com.parse.gwt.client.util.DateUtil;
import com.promis.rtti.client.Rtti;
import com.promis.rtti.client.RttiClass;
import com.promis.rtti.client.RttiField;
import elemental.client.Browser;
import java.lang.annotation.Annotation;
import java.util.*;

/**
 *
 * GWT Object to ParseObject marshaller
 *
 * @author Kerby Martino
 * @since 0-SNAPSHOT
 * @version 0-SNAPSHOT
 */
public class GwtMarshaller implements Marshaller {
    @Override
    public ParseObject marshall(java.lang.Object instance) {
        if(instance == null){
            throw new RuntimeException("Object cannot be null");
        }
        Annotation annotation = AnnotationUtil.getClassAnnotation(Entity.class, instance);
        if(annotation ==  null) {
            throw  new RuntimeException("@Entity annotation is required");
        }
        AnnotationUtil.AnnotatedField objectIdField = AnnotationUtil.getFieldWithAnnotation(ObjectId.class,
                instance);
        ParseObject parseObject = new ParseObject();
        java.lang.Object objectId = objectIdField.getFieldValue();
        if(objectId != null && objectId.getClass().equals(String.class)) {
            parseObject.putString("objectId", String.valueOf(objectId));
        }
        RttiClass info = Rtti.getTypeInfo(instance);
        RttiField[] fields = info.getFields();
        List<AnnotationUtil.AnnotatedField> annotatedFields
                = AnnotationUtil.getFieldsWithAnnotation(Column.class, instance);
        for(AnnotationUtil.AnnotatedField annotatedField : annotatedFields) {
            RttiField rttiField = annotatedField.getField();
            if(objectIdField.getField().getName().equals(rttiField.getName())){
                continue;
            }
            try {
                Class<?> classType = rttiField.getType();
                String fieldName = rttiField.getName();
                System.out.println("ClassType: " + classType.getName());
                java.lang.Object value = rttiField.get(instance);
                marshallValue(fieldName, classType, value, parseObject);
            } catch (Exception e) {
                Browser.getWindow().getConsole().log("Error marshalling field " + rttiField.getName() + ":" + e.getMessage());
            }
        }
        return parseObject;
    }

    private static void marshallValue(String fieldName, Class<?> classType, java.lang.Object value, JSONObject parseObject) {
        if(value != null) {
            if(classType.getName().equals(String.class.getName())) {
                Browser.getWindow().getConsole().log("String type found");
                String stringValue = (String) value;
                parseObject.put(fieldName, new JSONString(stringValue));
            } else if(classType.getName().equals(Boolean.class.getName())
                    || classType.getName().equals(boolean.class.getName())) {
                Browser.getWindow().getConsole().log("Boolean type found");
                Boolean booleanValue = (Boolean) value;
                if(booleanValue != null) {
                    parseObject.put(fieldName, JSONBoolean.getInstance(booleanValue));
                } else {
                    parseObject.put(fieldName, null);
                }
            } else if(classType.equals(Double.class) || classType.equals(Integer.class) || classType.equals(Long.class)) {
                Browser.getWindow().getConsole().log("Number type found");
                if(value instanceof Double) {
                    parseObject.put(fieldName, new JSONNumber((Double) value));
                } else if(value instanceof Integer) {
                    parseObject.put(fieldName, new JSONNumber((Integer) value));
                } else if(value instanceof  Long) {
                    parseObject.put(fieldName, new JSONNumber((Long) value));
                }
            } else if(classType.equals(double.class) || classType.equals(int.class) || classType.equals(long.class)) {
                Browser.getWindow().getConsole().log("Number type found");
                if(value.getClass().getName().equals(double.class)) {
                    parseObject.put(fieldName, new JSONNumber((Double) value));
                } else if(value.getClass().getName().equals(int.class)) {
                    parseObject.put(fieldName, new JSONNumber((Integer) value));
                } else if(value.getClass().getName().equals(long.class)) {
                    parseObject.put(fieldName, new JSONNumber((Long) value));
                }
            } else if(classType.equals(Date.class)) {
                Browser.getWindow().getConsole().log("Date type found");
                JSONObject jsonDate = new JSONObject();
                Date date = (Date) value;
                jsonDate.put("__type", new JSONString("Date"));
                jsonDate.put("iso", new JSONString(DateUtil.getStringFormat(date)));
                parseObject.put(fieldName, jsonDate);
            } else if(classType.equals(Map.class)) { // Object
                Browser.getWindow().getConsole().log("Map type found");
                throw new RuntimeException("Map is not supported use com.parse.gwt.client.types.Object instead");
            } else if(classType.equals(List.class)) {
                Browser.getWindow().getConsole().log("List type found");
                throw new RuntimeException("List is not supported use com.parse.gwt.client.types.Array instead");
            } else if(classType.equals(File.class)) {
                Browser.getWindow().getConsole().log("File type found");
                JSONObject jsonFile = new JSONObject();
                File file = (File) value;
                jsonFile.put("__type", new JSONString("File"));
                jsonFile.put("url", new JSONString(file.url));
                jsonFile.put("name", new JSONString(file.name));
                parseObject.put(fieldName, jsonFile);
            } else if(classType.equals(GeoPoint.class)) {
                Browser.getWindow().getConsole().log("GeoPoint type found");
                GeoPoint geoPoint = (GeoPoint) value;
                ParseGeoPoint parseGeoPoint = new ParseGeoPoint(geoPoint.longitude, geoPoint.latitude);
                parseObject.put(fieldName, parseGeoPoint);
            } else if(classType.equals(Pointer.class)) {
                Browser.getWindow().getConsole().log("Pointer type found");
                Pointer pointer = (Pointer) value;
                JSONObject jsonPointer = new JSONObject();
                jsonPointer.put("__type", new JSONString("Pointer"));
                jsonPointer.put("className", new JSONString(pointer.className));
                jsonPointer.put("objectId", new JSONString(pointer.objectId));
                parseObject.put(fieldName, jsonPointer);
            } else if(classType.equals(Relation.class)) {
                Browser.getWindow().getConsole().log("Relation type found");
                Relation relation = (Relation) value;
                JSONObject jsonRelation = new JSONObject();
                jsonRelation.put("__type", new JSONString("Relation"));
                jsonRelation.put("className", new JSONString(relation.className));
                parseObject.put(fieldName, jsonRelation);
            } else if(classType.equals(Array.class)) {
                Browser.getWindow().getConsole().log("Array type found");
                Array arrayVaulue = (Array) value;
                parseObject.put(fieldName, (JSONArray) value);
            } else if(classType.equals(Objek.class)) {
                Browser.getWindow().getConsole().log("Object type found");
                parseObject.put(fieldName, (JSONObject) value);
            } else {
                throw new RuntimeException("Unsupported type for field");
            }
        } else {
            parseObject.put(fieldName, JSONNull.getInstance());
        }
    }

    @Deprecated
    public static JSONArray marshallList(List field) {
        JSONArray array = new JSONArray();
        if(field != null) {
        }
        return array;
    }

    @Deprecated
    public static JSONObject marshallMap(Map field) {
        JSONObject jsonObject= null;
        Iterator<Map.Entry> it = field.entrySet().iterator();
        while(it.hasNext()) {
            Map.Entry entry = it.next();
            java.lang.Object key = entry.getKey();
            java.lang.Object value = entry.getValue();
            if(key instanceof String) {
                String stringKey = (String) key;
                if(jsonObject == null) {
                    jsonObject = new JSONObject();
                }
                marshallValue(stringKey, value.getClass(), value, jsonObject);
            } else {
                throw new RuntimeException("Map should have java.lang.String key only");
            }
        }
        return jsonObject;
    }



}
