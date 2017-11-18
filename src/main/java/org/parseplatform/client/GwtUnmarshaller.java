package org.parseplatform.client;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.json.client.*;
import com.google.gwt.reflect.shared.GwtReflect;
import elemental.client.Browser;
import org.parseplatform.client.annotations.Column;
import org.parseplatform.client.annotations.ComponentType;
import org.parseplatform.types.*;
import org.parseplatform.util.DateUtil;

import java.lang.annotation.Annotation;
import com.google.gwt.user.client.Window;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

public class GwtUnmarshaller implements Unmarshaller {
    @Override
    public <T> T unmarshall(Class<T> clazz, Object instance, ParseObject parseObject) {
        if (instance == null) {
            throw new RuntimeException("Object cannot be null");
        }
        Class<?> declaringClass = instance.getClass();
        //get objectID from model object
        String objID = null;
        //check if model object has objectId field
        if (objID != null) {
            //parseMODEL.putString("objectId", String.valueOf(objID));
        }
        //get annotations but specifically those with column from parameter object skip for now
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
                        //Browser.getWindow().getConsole().log("match");
                        Class<?> fieldType = fields[c].getType();
                        String fieldName = fields[c].getName();
                        Window.alert("Field Type        " + fields[c].getType());
                        Window.alert("Field Type Name   " + fields[c].getType().getName());
                        Window.alert("Field Name        " + fieldName);
                        //Browser.getWindow().getConsole().log("unmarshall " + fieldName +  " " + fieldType.getName() + " " + parseObject.get(k));
                        if (fieldType.getName() == String.class.getName()) {
                            String converter =  parseObject.getString(k);
                            GwtReflect.fieldSet(declaringClass, fieldName, instance, converter);
                        } else if (fieldType.getName() == Boolean.class.getName() || fieldType.getName() == boolean.class.getName()){
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
                        }  else if (fieldType.getName() == byte.class.getName()) {
                            // TODO
                        } else if (fieldType.getName() == char.class.getName() || fieldType.getName() == Character.class.getName()) {
                            char converter =  parseObject.getString(k).charAt(0);
                            GwtReflect.fieldSet(declaringClass, fieldName, instance, converter);
                        } else if (fieldType.getName() == Date.class.getName()) {
                            Window.alert("Date type found");
                            if(value != null && value.isObject() != null) {
                                if(value != null && value.isObject() != null
                                        && value.isObject().get("__type") != null
                                        && value.isObject().get("__type").isString() != null
                                        && value.isObject().get("__type").isString().stringValue().equals("Date")) {
                                    String dateString = value.isObject().get("iso").isString() != null ? value.isObject().get("iso").isString().stringValue() : null;
                                    Date date = DateUtil.iso8601String(dateString);
                                    GwtReflect.fieldSet(declaringClass, fieldName, instance, date);
                                }
                            }
                        } else if(fieldType.isArray()) {
                            Window.alert("Array type found");
                        } else if (fieldType.getName() == Map.class.getName()) { // Object
                            Window.alert("Map type found");
                            //throw new RuntimeException("Map is not supported use com.parse.gwt.client.types.Object instead");
                        } else if (fieldType.getName() == LinkedList.class.getName()) {

                            Annotation[] testannotation = fields[c].getAnnotations();
                            ComponentType componentType = fields[c].getAnnotation(ComponentType.class);
                            Window.alert("from annotation " + componentType.type().toString());

                            for (int n = 0; n < testannotation.length; n++) {
                                Window.alert( "type "  + testannotation[0].annotationType());
                            }
                            Window.alert("List type found   " );
                            Window.alert("List Generic Type ");
                            if(value.isArray() != null) {
                                List<Object> objectList = new LinkedList<>();
                                JSONArray jsonArray = parseObject.getJSONArray(fieldName);
                                Class<?> componentClass = componentType.type();
                                Window.alert("Component Class Name = " + componentClass.getName());
                                //Object newComponent = componentClass.newInstance();
                                Window.alert("Value array size  " + value.isArray().size());
                                Window.alert("Component Class  " + componentClass.getName());

                                for(int j=0;j<jsonArray.size();j++) {
                                    JSONValue jsonValue = jsonArray.get(j);
                                    objectList.add(isAssignable(jsonValue, componentClass));
                                }

                                Window.alert("array iteration done");
                                GwtReflect.fieldSet(declaringClass, fieldName, instance, objectList);
                            } else {
                                throw new RuntimeException("Cannot assign non-JSONArray to " + fieldType.getName());
                            }
                        } else if (fieldType.getName() == File.class.getName()) {
                            Window.alert("File type found");
                            JSONObject jsonFile = new JSONObject();
//                            File file = (File) value;
//                            jsonFile.put("__type", new JSONString("File"));
//                            jsonFile.put("url", new JSONString(file.url));
//                            jsonFile.put("name", new JSONString(file.name));
                        } else if (fieldType.getName() == GeoPoint.class.getName()) {
                            Window.alert("GeoPoint type found");
//                            GeoPoint geoPoint = (GeoPoint) value;
//                            ParseGeoPoint parseGeoPoint = new ParseGeoPoint(geoPoint.longitude, geoPoint.latitude);
                        } else if (fieldType.getName() == Pointer.class.getName()) {
                            Window.alert("Pointer type found");
                        } else if (fieldType.getName() == Relation.class.getName()) {
                            Window.alert("Relation type found");
                        } else if (fieldType.getName() == Array.class.getName()) {
                        } else if(fieldType.getName() == ParseACL.class.getName()) {
                        } else if(fieldType.getName() == ParseRole.class.getName()) {
                        } else if(fieldType.getName() == ParseGeoPoint.class.getName()) {
                        } else if(fieldType.getName() == ParseFile.class.getName()) {
                        } else if(fieldType.getName() == ParseRelation.class.getName()) {
                        } else if(fieldType.getName() == ParseDate.class.getName()) {
                        } else  {
                            JSONObject jsonObject = parseObject.getJSONObject(k);
                            ParseObject pojoObject = new ParseObject(jsonObject);
                            Class<?> pojoClass = fieldType;
                            Object pojo = pojoClass.newInstance();
                            pojo = unmarshall(pojoClass, pojo,pojoObject);
                            Window.alert("POJO type found   " + pojoClass.getName());
                            Window.alert("Parse Object      " + pojoObject.toString());
                            Window.alert("POJO Object       " + pojo.toString());
                            assert pojo != null;
                            GwtReflect.fieldSet(declaringClass, fieldName, instance, pojo);
                        }
                    }
                } catch (Exception e) {
                    Window.alert(e.getMessage());
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

    static <T> Class getArrayClass(T... param){
        return param.getClass();
    }

    static Object isAssignable(JSONValue value, Class<?> clazz) {
        if(clazz.getName() == String.class.getName()) {
            if(value != null && value.isString() != null) {
                return value.isString().stringValue();
            }
        } else if(clazz.getName() == Boolean.class.getName()
                || clazz.getName() == boolean.class.getName()) {
            if(value != null && value.isBoolean() != null) {
                return value.isBoolean().booleanValue();
            }
        } else if(clazz.getName() == Integer.class.getName()
                || clazz.getName() == int.class.getName()) {
            if(value != null && value.isNumber() != null) {
                Double doubleValue =  value.isNumber().doubleValue();
                return doubleValue.intValue();
            }
        } else if(clazz.getName() == Long.class.getName()
                || clazz.getName() == long.class.getName()) {
            if(value != null && value.isNumber() != null) {
                Double doubleValue =  value.isNumber().doubleValue();
                return doubleValue.longValue();
            }
        } else if(clazz.getName() == Double.class.getName()
                || clazz.getName() == double.class.getName()) {
            if(value != null && value.isNumber() != null) {
                Double doubleValue =  value.isNumber().doubleValue();
                return doubleValue;
            }
        } else if(clazz.getName() == Float.class.getName()
                || clazz.getName() == float.class.getName()) {
            if(value != null && value.isNumber() != null) {
                Double doubleValue =  value.isNumber().doubleValue();
                return doubleValue.floatValue();
            }
        } else if(clazz.getName() == Short.class.getName()
                || clazz.getName() == short.class.getName()) {
            if(value != null && value.isNumber() != null) {
                Double doubleValue =  value.isNumber().doubleValue();
                return doubleValue.shortValue();
            }
        } else if(clazz.getName() == Character.class.getName()
                || clazz.getName() == char.class.getName()) {
            // TODO
        } else if(clazz.getName() == Date.class.getName()) {
            if(value != null && value.isObject() != null) {
                JSONObject dateObject = value.isObject();
                if(dateObject.get("__type").isString() != null
                        && dateObject.get("__type").isString().stringValue().equals("Date")
                        && dateObject.get("iso") != null
                        && dateObject.get("iso").isString() != null) {
                    Date date = DateUtil.iso8601String(dateObject.get("iso").isString().stringValue());
                    return date;
                }
            }
        } else if(clazz.getName() == ParseACL.class.getName()) {
            // TODO
        } else if(clazz.getName() == ParseDate.class.getName()) {
            // TODO
        } else if(clazz.getName() == ParseFile.class.getName()) {
            // TODO
        } else if(clazz.getName() == ParseGeoPoint.class.getName()) {
            // TODO
        } else if(clazz.getName() == ParsePointer.class.getName()) {
            // TODO
        } else if(clazz.getName() == ParseRelation.class.getName()) {
            // TODO
        }
        return null;
    }

}
