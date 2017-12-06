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
import org.parseplatform.client.annotations.Column;
import org.parseplatform.types.*;
import org.parseplatform.util.DateUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;

import com.google.gwt.user.client.Window;

import static org.parseplatform.client.util.LogUtil.log;


public class GwtMarshaller implements Marshaller {
    //create model object
    ParseObject parseMODEL = new ParseObject();

    @Override
    public ParseObject marshall(java.lang.Object instance) {
        ParseObject parseholder = new ParseObject();

        if (instance == null) {
            throw new RuntimeException("Object cannot be null");
        }
        Class<?> declaringClass = instance.getClass();

        Field[] fields = GwtReflect.getPublicFields(instance.getClass());

        for (int c = 0; c < fields.length; c++){
            Class<?> fieldTYPE = fields[c].getType();

            java.lang.Object value = null;
            try {
                value = fields[c].get(instance);
            }
            catch (Exception e) {
                Browser.getWindow().getConsole().log(e.getMessage());
            }
        }

        for (int c = 0; c < fields.length; c++) {
            try {
                //mutable class match to gettype class value
                Class<?> fieldTYPE = fields[c].getType();
                Class<?> fieldType = fields[c].getType();

                //string type to string type
                String fieldName = fields[c].getName();

                Annotation[] testannotation = fields[c].getAnnotations();

                for (int n = 0; n < testannotation.length; n++) {

                    String annotationname = testannotation[n].annotationType().toString();

                    if (annotationname.substring(annotationname.lastIndexOf('.') + 1) == "Column") {
                        java.lang.Object value = null;

                        value = fields[c].get(instance);

                        if (value != null ) {
                            if (fieldType.getName() == String.class.getName()) {
                                String stringValue = value.toString();
                                parseholder.put(fieldName, new JSONString(stringValue));
                            } else if (fieldType.getName() == Boolean.class.getName() || fieldType.getName() == boolean.class.getName()) {
                                Boolean booleanValue = (Boolean) value;
                                if (booleanValue != null) {
                                    parseholder.put(fieldName, JSONBoolean.getInstance(booleanValue));
                                } else {
                                    parseholder.put(fieldName, null);
                                }
                            } else if (fieldType.getName() == Double.class.getName() || fieldType.getName() == Integer.class.getName() || fieldType.getName() == Long.class.getName()) {
                                if (value instanceof Double) {
                                    parseholder.put(fieldName, new JSONNumber((Double) value));
                                } else if (value instanceof Integer) {
                                    parseholder.put(fieldName, new JSONNumber((Integer) value));
                                } else if (value instanceof Long) {
                                    parseholder.put(fieldName, new JSONNumber((Long) value));
                                }
                            } else if (fieldType.getName() == float.class.getName() || fieldType.getName() == int.class.getName() || fieldType.getName() == long.class.getName()) {
                                //Browser.getWindow().getConsole().log("float int long " + fieldName + value.getClass().getName());
                                if (value.getClass().getName() == float.class.getName()) {
                                    parseholder.put(fieldName, new JSONNumber((float) value));
                                } else if (value.getClass().getTypeName() == int.class.getName()) {
                                    parseholder.put(fieldName, new JSONNumber((Integer) value));
                                } else if (value.getClass().getTypeName() == long.class.getName()) {
                                    parseholder.put(fieldName, new JSONNumber((Long) value));
                                } else if (value.getClass().getTypeName() == Integer.class.getName()) {
                                    parseholder.put(fieldName, new JSONNumber((Integer) value));
                                } else if (fieldType.getName() == int.class.getName()) {
                                    parseholder.put(fieldName, new JSONNumber((int) value));
                                } else if (value.getClass().getName() == Double.class.getName()) {
                                    parseholder.put(fieldName, new JSONNumber((Double) value));
                                } else if (value.getClass().getName() == Long.class.getName()) {
                                    parseholder.put(fieldName, new JSONNumber(Long.parseLong(value.toString())));
                                }
                            } else if (fieldType.getName() == Date.class.getName()) {
                                JSONObject jsonDate = new JSONObject();
                                Date date = (Date) value;
                                jsonDate.put("__type", new JSONString("Date"));
                                jsonDate.put("iso", new JSONString(DateUtil.getStringFormat(date)));
                                parseholder.put(fieldName, jsonDate);
                            } else if (fieldType.getName() == Map.class.getName()) { // Object
                                throw new RuntimeException("Map is not supported use com.parse.gwt.client.types.Object instead");
                            } else if (fieldType.getName() == List.class.getName()) {
                                List testList = (List) value;
                                JSONArray testARRAY = new JSONArray();
                                for (int p = 0; p < testList.size();p++) {
                                    Object testObject = testList.get(p);
                                    GwtReflect.magicClass(Object.class);
                                    ParseObject spiral = new ParseObject();
                                    Field[] subfields = GwtReflect.getPublicFields(testObject.getClass());
                                    for (int q = 0; q <subfields.length; q++) {
                                        Class<?> subfieldtype= subfields[q].getType();
                                        Object subfieldvalue = subfields[q].get(testObject);
                                    }
                                    spiral = marshall(testObject);
                                    testARRAY.set(p,spiral);
                                }
                                parseholder.put(fieldName,testARRAY);
                            } else if (fieldType.getName() == LinkedList.class.getName()) {
                                List testList = (List) value;
                                JSONArray testARRAY = new JSONArray();
                                for (int p = 0; p < testList.size();p++) {
                                    Object testObject = testList.get(p);
                                    ParseObject spiral = new ParseObject();
                                    Field[] subfields = GwtReflect.getPublicFields(testObject.getClass());
                                    for (int q = 0; q <subfields.length; q++) {
                                        Class<?> subfieldtype= subfields[q].getType();
                                        Object subfieldvalue = subfields[q].get(testObject);
                                    }
                                    spiral = marshall(testObject);
                                    testARRAY.set(p,spiral);
                                }
                                parseholder.put(fieldName,testARRAY);
                            }
                            else if (fieldType.getName() == File.class.getName()) {
                                JSONObject jsonFile = new JSONObject();
                                File file = (File) value;
                                jsonFile.put("__type", new JSONString("File"));
                                jsonFile.put("url", new JSONString(file.url));
                                jsonFile.put("name", new JSONString(file.name));
                                parseholder.put(fieldName, jsonFile);
                            } else if (fieldType.getName() == GeoPoint.class.getName()) {
                                GeoPoint geoPoint = (GeoPoint) value;
                                ParseGeoPoint parseGeoPoint = new ParseGeoPoint(geoPoint.longitude, geoPoint.latitude);
                                parseholder.put(fieldName, parseGeoPoint);
                            } else if (fieldType.getName() == Pointer.class.getName()) {
                                Pointer pointer = (Pointer) value;
                                JSONObject jsonPointer = new JSONObject();
                                jsonPointer.put("__type", new JSONString("Pointer"));
                                jsonPointer.put("className", new JSONString(pointer.className));
                                jsonPointer.put("objectId", new JSONString(pointer.objectId));
                                parseholder.put(fieldName, jsonPointer);
                            } else if (fieldType.getName() == Relation.class.getName()) {
                                Relation relation = (Relation) value;
                                JSONObject jsonRelation = new JSONObject();
                                jsonRelation.put("__type", new JSONString("Relation"));
                                jsonRelation.put("className", new JSONString(relation.className));
                                parseholder.put(fieldName, jsonRelation);
                            } else if (fieldType.getName() == Array.class.getName()) { //array to JSON object
                                Array arrayValue = (Array) value;
                                parseholder.put(fieldName, (JSONArray) value);
                            }  else if (fieldType.getName() == byte.class.getName()) {
                                parseholder.put(fieldName, new JSONNumber(Integer.parseInt(value.toString())));
                            } else if (fieldType.getName() == short.class.getName()) {
                                parseholder.put(fieldName, new JSONNumber((short) value));
                            } else if (fieldType.getName() == char.class.getName()) {
                                //prevent JSON format errors
                                //convert char to integer
                                parseholder.put(fieldName, new JSONString(value.toString()));
                            } else if (fieldType.getName() == double.class.getName()) {
                                parseholder.put(fieldName, new JSONNumber(Double.parseDouble(value.toString())));
                            } else if (fieldType.getName() == Byte.class.getName()) {
                                parseholder.put(fieldName, new JSONNumber(Integer.parseInt(value.toString())));
                            } else if (fieldType.getName() == Short.class.getName()) {
                                parseholder.put(fieldName, new JSONNumber(Short.parseShort(value.toString())));
                            } else if (fieldType.getName() == Float.class.getName()) {
                                parseholder.put(fieldName, new JSONNumber(Float.parseFloat(value.toString())));
                            } else if (fieldType.getName() == Character.class.getName()) {
                                parseholder.put(fieldName, new JSONString(value.toString()));
                            } else if (fieldType.getName() == byte[].class.getName()) {
                                parseholder.put(fieldName, new JSONNumber(Byte.parseByte(value.toString())));
                            } else if (fieldType.getName() == Byte[].class.getName()) {
                                parseholder.put(fieldName, new JSONNumber(Byte.parseByte(value.toString())));
                            } else if (fieldType.getName() == ParseACL.class.getName()) {
                                if(value != null) {
                                    ParseACL parseconvert = null;
                                    try {
                                        parseconvert = (ParseACL) value;
                                    } catch (Exception e) {
                                        Browser.getWindow().getConsole().log(e.getMessage());
                                    }
                                    parseholder.put(fieldName, parseconvert);
                                }
                            } else if (fieldType.getName() == ParseDate.class.getName()) {
                                if(value != null) {
                                    ParseDate parseconvert = null;
                                    try {
                                        parseconvert = (ParseDate) value;
                                    } catch (Exception e) {
                                        Browser.getWindow().getConsole().log(e.getMessage());
                                    }
                                    parseholder.put(fieldName, parseconvert);
                                }
                            } else if (fieldType.getName() == ParseFile.class.getName()) {
                                if(value != null) {
                                    ParseFile parseconvert = null;
                                    try {
                                        parseconvert = (ParseFile) value;
                                    } catch (Exception e) {
                                        Browser.getWindow().getConsole().log(e.getMessage());
                                    }
                                    parseholder.put(fieldName, parseconvert);
                                }
                            } else if (fieldType.getName() == ParseGeoPoint.class.getName()) {
                                if(value != null) {
                                    ParseGeoPoint parseconvert = null;
                                    try {
                                        parseconvert = (ParseGeoPoint) value;
                                    } catch (Exception e) {
                                        Browser.getWindow().getConsole().log(e.getMessage());
                                    }
                                    parseholder.put(fieldName, parseconvert);
                                }
                            } else if (fieldType.getName() == ParsePointer.class.getName()) {
                                if(value != null) {
                                    ParsePointer parseconvert = null;
                                    try {
                                        parseconvert = (ParsePointer) value;
                                    } catch (Exception e) {
                                        Browser.getWindow().getConsole().log(e.getMessage());
                                    }
                                    parseholder.put(fieldName, parseconvert);
                                }
                            } else if (fieldType.getName() == ParseRelation.class.getName()) {
                                if(value != null) {
                                    ParseRelation parseconvert = null;
                                    try {
                                        parseconvert = (ParseRelation) value;
                                    } catch (Exception e) {
                                        Browser.getWindow().getConsole().log(e.getMessage());
                                    }
                                    parseholder.put(fieldName, parseconvert);
                                }
                            } else if (fieldType.getName() == ParseRole.class.getName()) {
                                if(value != null) {
                                    ParseRole parseconvert = null;
                                    try {
                                        parseconvert = (ParseRole) value;
                                    } catch (Exception e) {
                                        Browser.getWindow().getConsole().log(e.getMessage());
                                    }
                                    parseholder.put(fieldName, parseconvert);
                                }
                            } else {
                                Object testObject = value;
                                ParseObject spiral = new ParseObject();
                                Field[] subfields = GwtReflect.getPublicFields(testObject.getClass());
                                for (int q = 0; q <subfields.length; q++) {
                                    Class<?> subfieldtype= subfields[q].getType();
                                    Object subfieldvalue = subfields[q].get(testObject);
                                }
                                spiral = marshall(testObject);
                                parseholder.put(fieldName,spiral);
                                log("Unlisted type of field");
                            }
                        } else {
                            parseholder.put(fieldName, JSONNull.getInstance());
                        }
                    }
                }
                //object value to field object value
            } catch (Exception e) {
                Browser.getWindow().getConsole().log("Error marshalling field " + fields[c].getName() + ":" + fields[c].getType().getName() + e.toString());
            }
        }
        return parseholder;
    }

    private static void marshallValue(String fieldName, Class<?> fieldType, java.lang.Object value, ParseObject parseholder) {
    }
}
