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
                                Object newComponent = componentClass.newInstance();
                                Window.alert("Value array size  " + value.isArray().size());
                                Window.alert("Component Class  " + componentClass.getName());
                                for(int a=0;a<jsonArray.size();a++) {
                                    try {
                                        JSONValue aValue = jsonArray.get(a);
                                        if(aValue != null && aValue.isObject() != null) {
                                            Window.alert("Before adding object to List");
                                            JSONObject jsonObject = aValue.isObject();
                                            ParseObject newParseObject = new ParseObject(jsonObject);
                                            Object newObject = unmarshall(componentClass, newComponent, newParseObject);
                                            assert newObject != null;
                                            objectList.add(newObject);
                                            Window.alert("After adding object to List " + newObject.getClass().getName());
                                            Window.alert("Object " + newObject.toString());
                                        } else if(aValue != null && aValue.isArray() != null) {
                                            Browser.getWindow().getConsole().log("Array component type is not supported");
                                        } else if(aValue != null && aValue.isNumber() != null) {
                                            Window.alert("Component is a JSON number");
                                            if (componentClass.getName() == Float.class.getName() || componentClass.getName() == float.class.getName()) {
                                                Double doubleValue = aValue.isNumber().doubleValue();
                                                Float floatValue =  doubleValue != null ? doubleValue.floatValue() : null;
                                                objectList.add(floatValue);
                                            } else if (componentClass.getName() == Double.class.getName() || componentClass.getName() == double.class.getName()) {
                                                Double doubleValue = aValue.isNumber().doubleValue();
                                                objectList.add(doubleValue);
                                            } else if (componentClass.getName() == Integer.class.getName() || componentClass.getName() == int.class.getName()) {
                                                Double doubleValue = aValue.isNumber().doubleValue();
                                                Integer intValue = doubleValue != null ? doubleValue.intValue() : null;
                                                objectList.add(intValue);
                                            } else if (componentClass.getName() == Long.class.getName() || componentClass.getName() == long.class.getName()) {
                                                Double doubleValue = aValue.isNumber().doubleValue();
                                                objectList.add(doubleValue.longValue());
                                            } else if (componentClass.getName() == Short.class.getName() || componentClass.getName() == short.class.getName()) {
                                                Double doubleValue = aValue.isNumber().doubleValue();
                                                objectList.add(doubleValue.shortValue());
                                            }
                                        } else if(aValue != null && aValue.isBoolean() != null) {
                                            if (componentClass.getName() == Boolean.class.getName() || componentClass.getName() == boolean.class.getName()){
                                                Boolean b = aValue.isBoolean().booleanValue();
                                                objectList.add(b);
                                            }
                                        } else if(aValue != null && aValue.isString() != null) {
                                            if (componentClass.getName() == String.class.getName()) {
                                                String converter =  aValue.isString().stringValue();
                                                objectList.add(converter);
                                            }
                                        } else if(aValue != null && aValue.isNull() != null) {
                                            objectList.add(null);
                                        }
                                    } catch (Exception e) {
                                        Window.alert("Error " + e.getMessage());
                                    }
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

}
