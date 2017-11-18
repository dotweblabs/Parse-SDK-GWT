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

import com.google.gwt.json.client.*;
import com.google.gwt.reflect.shared.GwtReflect;
import elemental.client.Browser;
import org.parseplatform.types.*;
import org.parseplatform.util.DateUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.parseplatform.client.util.LogUtil.log;


public class GwtMarshaller implements Marshaller {
    //create model object
    ParseObject parseMODEL = new ParseObject();

    @Override
    public ParseObject marshall(java.lang.Object instance) {
        //check if object in parameter is null
        if (instance == null) {
            throw new RuntimeException("Object cannot be null");
        }
        Class<?> declaringClass = instance.getClass();

        //get objectID from model object
        String objID = null;
        Field objectFIELD = null;

        //check if model object has objectId field
        if (objID != null) {
            // parseMODEL.putString("objectId", String.valueOf(objID));
        }
        //get type information of object in parameter
        //get fields in parameter object class
        Field[] fields = GwtReflect.getPublicFields(instance.getClass());

        //iterate for every annotated field since annotations will be done later change this to simple iterator
        for (int c = 0; c < fields.length; c++) {
            try {
                //mutable class match to gettype class value
                Class<?> fieldTYPE = fields[c].getType();

                //string type to string type
                String fieldName = fields[c].getName();

                Annotation[] testannotation = fields[c].getAnnotations();
                for (int n = 0; n < testannotation.length; n++) {
                    //ignore all non @column
                    //String annotationname = testannotation[n].annotationType().getName();
                    //if (annotationname.equals("Column")) {
                    String annotationname = testannotation[n].annotationType().toString();
                    if (annotationname.substring(annotationname.lastIndexOf('.') + 1) == "Column") {
                        java.lang.Object value = fields[c].get(instance);
                        //field name, field type, value conform to model object
                        marshallValue(fieldName, fieldTYPE, value, parseMODEL);
                    }
                }
                //object value to field object value
            } catch (Exception e) {
                Browser.getWindow().getConsole().warn("Error marshalling field " + fields[c].getName() + ":" + fields[c].getType().getName());
            }
        }
        return parseMODEL;
    }

    private static void marshallValue(String fieldName, Class<?> fieldType, java.lang.Object value, JSONObject parseObject) {
        //check if value is null if null continue, else do nothing
        if (value != null) {
            //fieldType get name
            if (fieldType.getName() == String.class.getName()) {
                String stringValue = value.toString();
                parseObject.put(fieldName, new JSONString(stringValue));
            } else if (fieldType.getName() == Boolean.class.getName() || fieldType.getName() == boolean.class.getName()) {
                Boolean booleanValue = (Boolean) value;
                if (booleanValue != null) {
                    parseObject.put(fieldName, JSONBoolean.getInstance(booleanValue));
                } else {
                    parseObject.put(fieldName, null);
                }
            } else if (fieldType.getName() == Double.class.getName() || fieldType.getName() == Integer.class.getName() || fieldType.getName() == Long.class.getName()) {
                if (value instanceof Double) {
                    parseObject.put(fieldName, new JSONNumber((Double) value));
                } else if (value instanceof Integer) {
                    parseObject.put(fieldName, new JSONNumber((Integer) value));
                } else if (value instanceof Long) {
                    parseObject.put(fieldName, new JSONNumber((Long) value));
                }
            } else if (fieldType.getName() == float.class.getName() || fieldType.getName() == int.class.getName() || fieldType.getName() == long.class.getName()) {
                //log("float int long " + fieldName + value.getClass().getName());
                if (value.getClass().getName() == float.class.getName()) {
                    parseObject.put(fieldName, new JSONNumber((float) value));
                } else if (value.getClass().getTypeName() == int.class.getName()) {
                    parseObject.put(fieldName, new JSONNumber((Integer) value));
                } else if (value.getClass().getTypeName() == long.class.getName()) {
                    parseObject.put(fieldName, new JSONNumber((Long) value));
                } else if (value.getClass().getTypeName() == Integer.class.getName()) {
                    parseObject.put(fieldName, new JSONNumber((Integer) value));
                } else if (fieldType.getName() == int.class.getName()) {
                    parseObject.put(fieldName, new JSONNumber((int) value));
                } else if (value.getClass().getName() == Double.class.getName()) {
                    parseObject.put(fieldName, new JSONNumber((Double) value));
                } else if (value.getClass().getName() == Long.class.getName()) {
                    parseObject.put(fieldName, new JSONNumber(Long.parseLong(value.toString())));
                }
            } else if (fieldType.getName() == Date.class.getName()) {
                log("Date type found");
                JSONObject jsonDate = new JSONObject();
                Date date = (Date) value;
                jsonDate.put("__type", new JSONString("Date"));
                jsonDate.put("iso", new JSONString(DateUtil.getStringFormat(date)));
                parseObject.put(fieldName, jsonDate);
            } else if (fieldType.getName() == Map.class.getName()) { // Object
                log("Map type found");
                throw new RuntimeException("Map is not supported use com.parse.gwt.client.types.Object instead");
            } else if (fieldType.getName() == List.class.getName()) {
                log("List type found");
                throw new RuntimeException("List is not supported use com.parse.gwt.client.types.Array instead");
            } else if (fieldType.getName() == File.class.getName()) {
                log("File type found");
                JSONObject jsonFile = new JSONObject();
                File file = (File) value;
                jsonFile.put("__type", new JSONString("File"));
                jsonFile.put("url", new JSONString(file.url));
                jsonFile.put("name", new JSONString(file.name));
                parseObject.put(fieldName, jsonFile);
            } else if (fieldType.getName() == GeoPoint.class.getName()) {
                log("GeoPoint type found");
                GeoPoint geoPoint = (GeoPoint) value;
                ParseGeoPoint parseGeoPoint = new ParseGeoPoint(geoPoint.longitude, geoPoint.latitude);
                parseObject.put(fieldName, parseGeoPoint);
            } else if (fieldType.getName() == Pointer.class.getName()) {
                log("Pointer type found");
                Pointer pointer = (Pointer) value;
                JSONObject jsonPointer = new JSONObject();
                jsonPointer.put("__type", new JSONString("Pointer"));
                jsonPointer.put("className", new JSONString(pointer.className));
                jsonPointer.put("objectId", new JSONString(pointer.objectId));
                parseObject.put(fieldName, jsonPointer);
            } else if (fieldType.getName() == Relation.class.getName()) {
                log("Relation type found");
                Relation relation = (Relation) value;
                JSONObject jsonRelation = new JSONObject();
                jsonRelation.put("__type", new JSONString("Relation"));
                jsonRelation.put("className", new JSONString(relation.className));
                parseObject.put(fieldName, jsonRelation);
            } else if (fieldType.getName() == Array.class.getName()) {
                log("Array type found");
                Array arrayValue = (Array) value;
                parseObject.put(fieldName, (JSONArray) value);
            } else if (fieldType.getName() == byte.class.getName()) {
                Browser.getWindow().getConsole().warn("byte type found " + value.toString());
                parseObject.put(fieldName, new JSONNumber(Integer.parseInt(value.toString())));
            } else if (fieldType.getName() == short.class.getName()) {
                parseObject.put(fieldName, new JSONNumber((short) value));
            } else if (fieldType.getName() == char.class.getName()) {
                //prevent JSON format errors
                //convert char to integer
                parseObject.put(fieldName, new JSONString(value.toString()));
            } else if (fieldType.getName() == double.class.getName()) {
                parseObject.put(fieldName, new JSONNumber(Double.parseDouble(value.toString())));
            } else if (fieldType.getName() == Byte.class.getName()) {
                parseObject.put(fieldName, new JSONNumber(Integer.parseInt(value.toString())));
            } else if (fieldType.getName() == Short.class.getName()) {
                parseObject.put(fieldName, new JSONNumber(Short.parseShort(value.toString())));
            } else if (fieldType.getName() == Float.class.getName()) {
                parseObject.put(fieldName, new JSONNumber(Float.parseFloat(value.toString())));
            } else if (fieldType.getName() == Character.class.getName()) {
                parseObject.put(fieldName, new JSONString(value.toString()));
            } else if (fieldType.getName() == byte[].class.getName()) {
                parseObject.put(fieldName, new JSONNumber(Byte.parseByte(value.toString())));
            } else if (fieldType.getName() == Byte[].class.getName()) {
                parseObject.put(fieldName, new JSONNumber(Byte.parseByte(value.toString())));
            } else if (fieldType.getName() == ParseACL.class.getName()) {
                //parseObject.put(fieldName, new JSONObject(value.));
            } else if (fieldType.getName() == ParseCloud.class.getName()) {
                //parseObject.put(fieldName, new JSONObject(value.));
            } else if (fieldType.getName() == ParseConfig.class.getName()) {
                //parseObject.put(fieldName, new JSONObject(value.));
            } else if (fieldType.getName() == ParseConstants.class.getName()) {
                //parseObject.put(fieldName, new JSONObject(value.));
            } else if (fieldType.getName() == ParseDate.class.getName()) {
                //parseObject.put(fieldName, new JSONObject(value.));
            } else if (fieldType.getName() == ParseError.class.getName()) {
                //parseObject.put(fieldName, new JSONObject(value.));
            } else if (fieldType.getName() == ParseGeoPoint.class.getName()) {
                //parseObject.put(fieldName, new JSONObject(value.));
            } else if (fieldType.getName() == ParseQuery.class.getName()) {
                //parseObject.put(fieldName, new JSONObject(value.));
            } else if (fieldType.getName() == ParseRelation.class.getName()) {
                //parseObject.put(fieldName, new JSONObject(value.));
            } else if (fieldType.getName() == ParseResponse.class.getName()) {
                //parseObject.put(fieldName, new JSONObject(value.));
            } else if (fieldType.getName() == ParseRole.class.getName()) {
                //parseObject.put(fieldName, new JSONObject(value.));
            } else if (fieldType.getName() == ParseUser.class.getName()) {
                //parseObject.put(fieldName, new JSONObject(value.));
            } else {
                //throw new RuntimeException("Unsupported type for field");
            }
        } else {
            parseObject.put(fieldName, JSONNull.getInstance());
        }
    }
}







