package org.parseplatform.client;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.json.client.*;
import com.google.gwt.reflect.shared.GwtReflect;
import elemental.client.Browser;
import org.parseplatform.client.annotations.Column;
import org.parseplatform.client.annotations.ComponentType;
import org.parseplatform.client.util.LogUtil;
import org.parseplatform.types.*;
import org.parseplatform.util.DateUtil;

import java.lang.annotation.Annotation;

import com.google.gwt.user.client.Window;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

import static org.parseplatform.client.util.LogUtil.log;

public class GwtUnmarshaller implements Unmarshaller {
    @Override
    public <T> T unmarshall(Class<T> clazz, Object instance, ParseObject parseObject) {
        if (instance == null) {
            throw new RuntimeException("Object cannot be null");
        }
        Class<?> declaringClass = instance.getClass();
        // get annotations but specifically those with column from parameter object skip for now
        // get fields from mutant
        Field[] fields = GwtReflect.getPublicFields(instance.getClass());
        //match keys from parse model to mutant
        Set<?> s = parseObject.keySet();
        //iterate and persist keys from model
        Iterator<?> i = s.iterator();
        do {
            String k = i.next().toString();
            //iterate through mutant fields to match model
            for (int c = 0; c < fields.length; c++) {
                JSONValue value = null;
                try {
                    // JSON primitives
                    value = parseObject.get(k);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    if (parseObject.get(k) != null && k == fields[c].getName()) {
                        Class<?> fieldType = fields[c].getType();
                        String fieldName = fields[c].getName();
                        //log("Field Type        " + fields[c].getType());
                        //log("Field Type Name   " + fields[c].getType().getName());
                        //log("Field Name        " + fieldName);
                        if (fieldType.getName() == String.class.getName()) {
                            String converter = parseObject.getString(k);
                            GwtReflect.fieldSet(declaringClass, fieldName, instance, converter);
                        } else if (fieldType.getName() == Boolean.class.getName() || fieldType.getName() == boolean.class.getName()) {
                            Boolean b = parseObject.getBoolean(k);
                            GwtReflect.fieldSet(declaringClass, fieldName, instance, b);
                        } else if (fieldType.getName() == Float.class.getName() || fieldType.getName() == float.class.getName()) {
                            Float floatValue = parseObject.getDouble(k) != null ? parseObject.getDouble(k).floatValue() : null;
                            GwtReflect.fieldSet(declaringClass, fieldName, instance, floatValue);
                        } else if (fieldType.getName() == Double.class.getName() || fieldType.getName() == double.class.getName()) {
                            Double doubleValue = parseObject.getDouble(k);
                            GwtReflect.fieldSet(declaringClass, fieldName, instance, doubleValue);
                        } else if (fieldType.getName() == Integer.class.getName() || fieldType.getName() == int.class.getName()) {
                            Integer intValue = parseObject.getDouble(k) != null ? parseObject.getDouble(k).intValue() : null;
                            GwtReflect.fieldSet(declaringClass, fieldName, instance, intValue);
                        } else if (fieldType.getName() == Long.class.getName() || fieldType.getName() == long.class.getName()) {
                            Long longValue = parseObject.getLong(k);
                            GwtReflect.fieldSet(declaringClass, fieldName, instance, longValue);
                        } else if (fieldType.getName() == Short.class.getName() || fieldType.getName() == short.class.getName()) {
                            Short shortValue = parseObject.getDouble(k).shortValue();
                            GwtReflect.fieldSet(declaringClass, fieldName, instance, shortValue);
                        } else if (fieldType.getName() == byte.class.getName() || fieldType.getName() == Byte.class.getName()) {

                            byte converter = Byte.parseByte(value.toString());

                            GwtReflect.fieldSet(declaringClass, fieldName, instance, converter);
                        } else if (fieldType.getName() == char.class.getName() || fieldType.getName() == Character.class.getName()) {
                            char converter = parseObject.getString(k).charAt(0);
                            GwtReflect.fieldSet(declaringClass, fieldName, instance, converter);
                        } else if (fieldType.getName() == Date.class.getName()) {
                            //log("Date type found");
                            if (value != null && value.isObject() != null) {
                                if (value != null && value.isObject() != null
                                        && value.isObject().get("__type") != null
                                        && value.isObject().get("__type").isString() != null
                                        && value.isObject().get("__type").isString().stringValue().equals("Date")) {
                                    String dateString = value.isObject().get("iso").isString() != null ? value.isObject().get("iso").isString().stringValue() : null;
                                    Date date = DateUtil.iso8601String(dateString);
                                    GwtReflect.fieldSet(declaringClass, fieldName, instance, date);
                                }
                            }
                        } else if (fieldType.isArray()) {
                            //log("Array type found");
                        } else if (fieldType.getName() == Map.class.getName()) { // Object
                            //log("Map type found");
                            //throw new RuntimeException("Map is not supported use com.parse.gwt.client.types.Object instead");
                        } else if (fieldType.getName() == LinkedList.class.getName()) {
                            ComponentType componentType = fields[c].getAnnotation(ComponentType.class);
                            if (value.isArray() != null) {
                                List<Object> objectList = new LinkedList<>();
                                JSONArray jsonArray = parseObject.getJSONArray(fieldName);
                                Class<?> componentClass = componentType.type();
                                for (int j = 0; j < jsonArray.size(); j++) {
                                    try {
                                        JSONValue jsonValue = jsonArray.get(j);
                                        JSONObject jsonobject = jsonValue.isObject();
                                        Object compnentValue = isAssignable(jsonValue, componentClass);
                                        if (compnentValue == null) {
                                            Browser.getWindow().getConsole().log("WARNNING: Component Value is NULL");
                                        }
                                        objectList.add(compnentValue);
                                    } catch (Exception ex) {
                                        Browser.getWindow().getConsole().log(ex.getMessage());
                                    }
                                }
                                GwtReflect.fieldSet(declaringClass, fieldName, instance, objectList);
                            } else {
                                throw new RuntimeException("Cannot assign non-JSONArray to " + fieldType.getName());
                            }
                        } else if (fieldType.getName() == File.class.getName()) {
                            GwtReflect.fieldSet(declaringClass, fieldName, instance, isAssignable(value, File.class));
                        } else if (fieldType.getName() == GeoPoint.class.getName()) {
                            GwtReflect.fieldSet(declaringClass, fieldName, instance, isAssignable(value, GeoPoint.class));
                        } else if (fieldType.getName() == Pointer.class.getName()) {
                            GwtReflect.fieldSet(declaringClass, fieldName, instance, isAssignable(value, Pointer.class));
                        } else if (fieldType.getName() == Relation.class.getName()) {
                            GwtReflect.fieldSet(declaringClass, fieldName, instance, isAssignable(value, Relation.class));
                        } else if (fieldType.getName() == ParseACL.class.getName()) {
                            GwtReflect.fieldSet(declaringClass, fieldName, instance, isAssignable(value, ParseACL.class));
                        } else if (fieldType.getName() == ParseRole.class.getName()) {
                            GwtReflect.fieldSet(declaringClass, fieldName, instance, isAssignable(value, ParseRole.class));
                        } else if (fieldType.getName() == ParseGeoPoint.class.getName()) {
                            GwtReflect.fieldSet(declaringClass, fieldName, instance, isAssignable(value, ParseGeoPoint.class));
                        } else if (fieldType.getName() == ParseFile.class.getName()) {
                            GwtReflect.fieldSet(declaringClass, fieldName, instance, isAssignable(value, ParseFile.class));
                        } else if (fieldType.getName() == ParseRelation.class.getName()) {
                            GwtReflect.fieldSet(declaringClass, fieldName, instance, isAssignable(value, ParseRelation.class));
                        } else if (fieldType.getName() == ParseDate.class.getName()) {
                            GwtReflect.fieldSet(declaringClass, fieldName, instance, isAssignable(value, ParseDate.class));
                        } else if (fieldType.getName() == ParsePointer.class.getName()) {
                            GwtReflect.fieldSet(declaringClass, fieldName, instance, isAssignable(value, ParsePointer.class));
                        } else {
                            JSONObject jsonObject = parseObject.getJSONObject(k);
                            ParseObject pojoObject = new ParseObject(jsonObject);
                            Class<?> pojoClass = fieldType;
                            Object pojo = pojoClass.newInstance();
                            pojo = unmarshall(pojoClass, pojo, pojoObject);
                            //log("POJO type found   " + pojoClass.getName());
                            //log("Parse Object      " + pojoObject.toString());
                            //log("POJO Object       " + pojo.toString());
                            assert pojo != null;
                            GwtReflect.fieldSet(declaringClass, fieldName, instance, pojo);
                        }
                    } else {
                        //GwtReflect.fieldSet(declaringClass,  fields[c].getName(), instance, null);
                    }
                } catch (Exception e) {
                    //log(e.getMessage());
                }
            }
        } while (i.hasNext());
        //implement later
        return (T) instance;
    }

    private void unmarshallList(List list, JSONObject jsonObject) {

    }

    private void unmarshallMap(Map map, JSONObject jsonObject) {

    }

    static <T> Class getArrayClass(T... param) {
        return param.getClass();
    }

    public Object isAssignable(JSONValue value, Class<?> clazz) {
        if (clazz.getName() == String.class.getName()) {
            if (value != null && value.isString() != null) {
                return value.isString().stringValue();
            }
        } else if (clazz.getName() == Boolean.class.getName()
                || clazz.getName() == boolean.class.getName()) {
            if (value != null && value.isBoolean() != null) {
                return value.isBoolean().booleanValue();
            }
        } else if (clazz.getName() == Integer.class.getName()
                || clazz.getName() == int.class.getName()) {
            if (value != null && value.isNumber() != null) {
                Double doubleValue = value.isNumber().doubleValue();
                return doubleValue.intValue();
            }
        } else if (clazz.getName() == Long.class.getName()
                || clazz.getName() == long.class.getName()) {
            if (value != null && value.isNumber() != null) {
                Double doubleValue = value.isNumber().doubleValue();
                return doubleValue.longValue();
            }
        } else if (clazz.getName() == Double.class.getName()
                || clazz.getName() == double.class.getName()) {
            if (value != null && value.isNumber() != null) {
                Double doubleValue = value.isNumber().doubleValue();
                return doubleValue;
            }
        } else if (clazz.getName() == Float.class.getName()
                || clazz.getName() == float.class.getName()) {
            if (value != null && value.isNumber() != null) {
                Double doubleValue = value.isNumber().doubleValue();
                return doubleValue.floatValue();
            }
        } else if (clazz.getName() == Short.class.getName()
                || clazz.getName() == short.class.getName()) {
            if (value != null && value.isNumber() != null) {
                Double doubleValue = value.isNumber().doubleValue();
                return doubleValue.shortValue();
            }
        } else if (clazz.getName() == byte.class.getName() || clazz.getName() == Byte.class.getName()) {
            byte converter = Byte.parseByte(value.toString());
            return converter;
        } else if (clazz.getName() == Character.class.getName()
                || clazz.getName() == char.class.getName()) {
            // TODO
        } else if (clazz.getName() == Date.class.getName()) {
            if (value != null && value.isObject() != null) {
                JSONObject dateObject = value.isObject();
                if (dateObject.get("__type").isString() != null
                        && dateObject.get("__type").isString().stringValue().equals("Date")
                        && dateObject.get("iso") != null
                        && dateObject.get("iso").isString() != null) {
                    Date date = DateUtil.iso8601String(dateObject.get("iso").isString().stringValue());
                    return date;
                }
            }
        } else if (clazz.getName() == ParseACL.class.getName()) {
            JSONObject ACL = value.isObject();
            if (value != null && value.isObject() != null) {
                return new ParseACL(value.isObject());
            }
        } else if (clazz.getName() == ParseDate.class.getName()) {
            if (value != null && value.isObject() != null) {
                String iso = value.isObject().get("iso") != null && value.isObject().get("iso").isString() != null
                        ? value.isObject().get("iso").isString().stringValue() : null;
                return new ParseDate(iso);
            } else if(value != null && value.isString() != null) {
                return new ParseDate(value.isString().stringValue());
            }
        } else if (clazz.getName() == ParseFile.class.getName()) {
            if (value != null && value.isObject() != null) {
                JSONObject fileObject = value.isObject();
                if (fileObject.get("__type") != null && fileObject.get("__type").isString() != null && fileObject.get("__type").isString().stringValue().equals("File")) {
                    String url = fileObject.get("url").isString() != null && fileObject.get("url").isString().stringValue() != null
                            ? fileObject.get("url").isString().stringValue() : null;
                    String name = fileObject.get("name").isString() != null && fileObject.get("name").isString().stringValue() != null
                            ? fileObject.get("name").isString().stringValue() : null;
                    return new ParseFile(name, url);
                }
            }
        } else if (clazz.getName() == ParseGeoPoint.class.getName()) {
            if (value != null && value.isObject() != null) {
                JSONObject geoPointObject = value.isObject();
                if (geoPointObject.get("__type").isString() != null && geoPointObject.get("__type").isString().stringValue().equals("GeoPoint")) {
                    Double longitude = (geoPointObject.get("longitude") != null && geoPointObject.get("longitude").isNumber() != null) ? geoPointObject.get("longitude").isNumber().doubleValue() : null;
                    Double latitude = (geoPointObject.get("latitude") != null && geoPointObject.get("latitude").isNumber() != null) ? geoPointObject.get("latitude").isNumber().doubleValue() : null;
                    return new ParseGeoPoint(Double.valueOf(longitude), Double.valueOf(latitude));
                }
            }
        } else if (clazz.getName() == ParsePointer.class.getName()) {
            if (value != null && value.isObject() != null) {
                JSONObject pointerObject = value.isObject();
                if (pointerObject.get("__type").isString() != null && pointerObject.get("__type").isString().stringValue().equals("Pointer")) {
                    String className = pointerObject.get("className").isString() != null && pointerObject.get("className").isString().stringValue() != null
                            ? pointerObject.get("className").isString().stringValue() : null;
                    String objectId = pointerObject.get("objectId").isString() != null && pointerObject.get("objectId").isString().stringValue() != null
                            ? pointerObject.get("objectId").isString().stringValue() : null;
                    return new ParsePointer(className, objectId);
                }
            }
        } else if (clazz.getName() == ParseRelation.class.getName()) {
            if (value != null && value.isObject() != null) {
                JSONObject relationObject = value.isObject();
                if (relationObject.get("__type").isString() != null && relationObject.get("__type").isString().stringValue().equals("Relation")) {
                    String className = relationObject.get("className").isString() != null && relationObject.get("className").isString().stringValue() != null
                            ? relationObject.get("className").isString().stringValue() : null;
                    return new ParseRelation(className);
                }
            }
        } else if (clazz.getName() == ParseRole.class.getName()) {
            // TODO
            return new ParseRole();
        } else if (clazz.getName() == File.class.getName()) {
            if (value != null && value.isObject() != null) {
                JSONObject fileObject = value.isObject();
                if (fileObject.get("__type").isString() != null && fileObject.get("__type").isString().stringValue().equals("File")) {
                    String url = fileObject.get("url").isString() != null && fileObject.get("url").isString().stringValue() != null
                            ? fileObject.get("url").isString().stringValue() : null;
                    String name = fileObject.get("name").isString() != null && fileObject.get("name").isString().stringValue() != null
                            ? fileObject.get("name").isString().stringValue() : null;
                    File file = new File();
                    file.setName(name);
                    file.setUrl(url);
                    return file;
                }
            }
        } else if (clazz.getName() == GeoPoint.class.getName()) {
            if (value != null && value.isObject() != null) {
                JSONObject geoPointObject = value.isObject();
                if (geoPointObject.get("__type").isString() != null && geoPointObject.get("__type").isString().stringValue().equals("GeoPoint")) {
                    Double longitude = (geoPointObject.get("longitude") != null && geoPointObject.get("longitude").isNumber() != null) ? geoPointObject.get("longitude").isNumber().doubleValue() : null;
                    Double latitude = (geoPointObject.get("latitude") != null && geoPointObject.get("latitude").isNumber() != null) ? geoPointObject.get("latitude").isNumber().doubleValue() : null;
                    GeoPoint geoPoint = new GeoPoint();
                    geoPoint.setLatitude(Double.valueOf(latitude));
                    geoPoint.setLongitude(Double.valueOf(longitude));
                    return geoPoint;
                }
            }
        } else if (clazz.getName() == Pointer.class.getName()) {
            if (value != null && value.isObject() != null) {
                JSONObject pointerObject = value.isObject();
                if (pointerObject.get("__type").isString() != null && pointerObject.get("__type").isString().stringValue().equals("Pointer")) {
                    String className = pointerObject.get("className").isString() != null && pointerObject.get("className").isString().stringValue() != null
                            ? pointerObject.get("className").isString().stringValue() : null;
                    String objectId = pointerObject.get("objectId").isString() != null && pointerObject.get("objectId").isString().stringValue() != null
                            ? pointerObject.get("objectId").isString().stringValue() : null;
                    Pointer pointer = new Pointer();
                    pointer.setClassName(className);
                    pointer.setObjectId(objectId);
                    return pointer;
                }
            }
        } else if (clazz.getName() == Relation.class.getName()) {
            if (value != null && value.isObject() != null) {
                JSONObject relationObject = value.isObject();
                if (relationObject.get("__type").isString() != null && relationObject.get("__type").isString().stringValue().equals("Relation")) {
                    String className = relationObject.get("className").isString() != null && relationObject.get("className").isString().stringValue() != null
                            ? relationObject.get("className").isString().stringValue() : null;
                    Relation relation = new Relation();
                    relation.setClassName(className);
                    return relation;
                }
            }
        } else {
            Object newInstance = null;
            if (value.isObject() != null) {
                JSONObject jsonObject = value.isObject();
                ParseObject parseObject = new ParseObject(jsonObject);
                try {
                    newInstance = clazz.newInstance();
                    if (newInstance != null && parseObject != null) {
                        newInstance = unmarshall(clazz, newInstance, parseObject);
                    }
                } catch (Exception e) {
                    Browser.getWindow().getConsole().log("ERROR: Cannot create new instance of " + clazz.getName() + " " + e.getMessage());
                }
            }
            if (newInstance == null) {
               Browser.getWindow().getConsole().log("WARNING: Cannot create new instance of " + clazz.getName());
            }
            return newInstance;
        }
        return null;
    }

}
