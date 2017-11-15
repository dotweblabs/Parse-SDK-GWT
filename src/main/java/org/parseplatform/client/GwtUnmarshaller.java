package org.parseplatform.client;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.json.client.*;
import com.google.gwt.reflect.shared.GwtReflect;
import elemental.client.Browser;
import org.parseplatform.types.*;
import org.parseplatform.util.DateUtil;

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
        Field objectFIELD = null;
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
                        Browser.getWindow().getConsole().log("Field Type        " + fields[c].getType());
                        Browser.getWindow().getConsole().log("Field Type Name   " + fields[c].getType().getName());
                        Browser.getWindow().getConsole().log("Field Name        " + fieldName);
                        //Browser.getWindow().getConsole().log("unmarshall " + fieldName +  " " + fieldType.getName() + " " + parseObject.get(k));
                        if (fieldType.getName() == String.class.getName()) {
                            String converter =  parseObject.getString(k);
                            GwtReflect.fieldSet(declaringClass, fieldName, instance, converter);
                        } else if (fieldType.getName() == Boolean.class.getName()){
                            Boolean converter = Boolean.parseBoolean( parseObject.get(k).toString());
                            GwtReflect.fieldSet(declaringClass, fieldName, instance, converter);
                        } else if (fieldType.getName() == boolean.class.getName()) {
                            boolean converter = Boolean.parseBoolean( parseObject.get(k).toString());
                            GwtReflect.fieldSet(declaringClass, fieldName, instance, converter);
                        } else if (fieldType.getName() == Float.class.getName() ) {
                            Float converter = Float.parseFloat( parseObject.get(k).toString());
                            GwtReflect.fieldSet(declaringClass, fieldName, instance, converter);
                        } else if (fieldType.getName() == Double.class.getName() ) {
                            Double converter = Double.parseDouble( parseObject.get(k).toString());
                            GwtReflect.fieldSet(declaringClass, fieldName, instance, converter);
                        } else if (fieldType.getName() == Integer.class.getName() ) {
                            Integer converter = Integer.parseInt( parseObject.get(k).toString());
                            GwtReflect.fieldSet(declaringClass, fieldName, instance, converter);
                        } else if (fieldType.getName() == Long.class.getName() ) {
                            Long converter = Long.parseLong( parseObject.get(k).toString());
                            GwtReflect.fieldSet(declaringClass, fieldName, instance, converter);
                        } else if (fieldType.getName() == Short.class.getName()) {
                            Short converter = Short.parseShort( parseObject.get(k).toString());
                            GwtReflect.fieldSet(declaringClass, fieldName, instance, converter);
                        } else if (fieldType.getName() == float.class.getName()) {
                            float converter = Float.parseFloat( parseObject.get(k).toString());
                            GwtReflect.fieldSet(declaringClass, fieldName, instance, converter);
                        } else if (fieldType.getName() == double.class.getName()) {
                            double converter = Double.parseDouble( parseObject.get(k).toString());
                            GwtReflect.fieldSet(declaringClass, fieldName, instance, converter);
                        } else if (fieldType.getName() == int.class.getName()) {
                            int converter =  Integer.parseInt( parseObject.get(k).toString());
                            GwtReflect.fieldSet(declaringClass, fieldName, instance, converter);
                        } else if (fieldType.getName() == long.class.getName() ) {
                            long converter = Long.parseLong( parseObject.get(k).toString());
                            GwtReflect.fieldSet(declaringClass, fieldName, instance, converter);
                        } else if (fieldType.getName() == short.class.getName() ) {
                            short converter = Short.parseShort( parseObject.get(k).toString());
                            GwtReflect.fieldSet(declaringClass, fieldName, instance, converter);
                        } else if (fieldType.getName() == byte.class.getName()) {
                            byte converter = Byte.parseByte( parseObject.get(k).toString());
                            GwtReflect.fieldSet(declaringClass, fieldName, instance, converter);
                        } else if (fieldType.getName() == char.class.getName()) {
                            char converter =  parseObject.get(k).toString().charAt(0);
                            GwtReflect.fieldSet(declaringClass, fieldName, instance, converter);
                        } else if (fieldType.getName() == Date.class.getName()) {
                            Browser.getWindow().getConsole().log("Date type found");
                            JSONObject jsonDate = new JSONObject();
//                            Date date = (Date) value;
//                            jsonDate.put("__type", new JSONString("Date"));
//                            jsonDate.put("iso", new JSONString(DateUtil.getStringFormat(date)));
                        } else if(fieldType.isArray()) {
                            Browser.getWindow().getConsole().log("Array type found");
                        } else if (fieldType.getName() == Map.class.getName()) { // Object
                            Browser.getWindow().getConsole().log("Map type found");
                            //throw new RuntimeException("Map is not supported use com.parse.gwt.client.types.Object instead");
                        } else if (fieldType.getName() == List.class.getName()) {

                            ParameterizedType genericType = (ParameterizedType) fields[c].getGenericType();
                            Browser.getWindow().getConsole().log("List type found   ");
                            Browser.getWindow().getConsole().log("List Generic Type " + genericType);
                            if(value.isArray() != null) {
                                Browser.getWindow().getConsole().log("Value array size  " + value.isArray().size());
                                List<Object> objectList = new LinkedList<>();
                                JSONArray jsonArray = value.isArray();
                                for(int a=0;a<jsonArray.size();a++) {
                                    JSONValue aValue = jsonArray.get(a);
                                    if(aValue != null && aValue.isObject() != null) {
                                        // TODO Marshall into type?
                                        JSONObject jsonObject = aValue.isObject();
                                        objectList.add(jsonObject);
                                    } else if(aValue != null && aValue.isArray() != null) {

                                    } else if(aValue != null && aValue.isNumber() != null) {

                                    } else if(aValue != null && aValue.isBoolean() != null) {

                                    } else if(aValue != null && aValue.isString() != null) {

                                    } else if(aValue != null && aValue.isNull() != null) {

                                    }
                                }
                                GwtReflect.fieldSet(declaringClass, fieldName, instance, objectList);
                            } else {
                                throw new RuntimeException("Cannot assign non-JSONArray to " + fieldType.getName());
                            }
                        } else if (fieldType.getName() == File.class.getName()) {
                            Browser.getWindow().getConsole().log("File type found");
                            JSONObject jsonFile = new JSONObject();
//                            File file = (File) value;
//                            jsonFile.put("__type", new JSONString("File"));
//                            jsonFile.put("url", new JSONString(file.url));
//                            jsonFile.put("name", new JSONString(file.name));
                        } else if (fieldType.getName() == GeoPoint.class.getName()) {
                            Browser.getWindow().getConsole().log("GeoPoint type found");
//                            GeoPoint geoPoint = (GeoPoint) value;
//                            ParseGeoPoint parseGeoPoint = new ParseGeoPoint(geoPoint.longitude, geoPoint.latitude);
                        } else if (fieldType.getName() == Pointer.class.getName()) {
                            Browser.getWindow().getConsole().log("Pointer type found");
//                            Pointer pointer = (Pointer) value;
//                            JSONObject jsonPointer = new JSONObject();
//                            jsonPointer.put("__type", new JSONString("Pointer"));
//                            jsonPointer.put("className", new JSONString(pointer.className));
//                            jsonPointer.put("objectId", new JSONString(pointer.objectId));
                        } else if (fieldType.getName() == Relation.class.getName()) {
                            Browser.getWindow().getConsole().log("Relation type found");
//                            Relation relation = (Relation) value;
//                            JSONObject jsonRelation = new JSONObject();
//                            jsonRelation.put("__type", new JSONString("Relation"));
//                            jsonRelation.put("className", new JSONString(relation.className));
                        } else if (fieldType.getName() == Array.class.getName()) {
                            //Browser.getWindow().getConsole().log("Array type found");
                            Array arrayVaulue = (Array) value;
                        } else if (fieldType.getName() == (Objek.class).getName()) {
                            //Browser.getWindow().getConsole().log("Object type found");
                        } else {
                            // TODO: POJO field
                            JSONObject jsonObject = parseObject.getJSONObject(k);
                            ParseObject pojoObject = new ParseObject(jsonObject);
                            Class<?> pojoClass = fieldType;
                            Object pojo = pojoClass.newInstance();
                            pojo = unmarshall(pojoClass, pojo,pojoObject);
                            Browser.getWindow().getConsole().log("POJO type found   " + pojoClass.getName());
                            Browser.getWindow().getConsole().log("Parse Object      " + pojoObject.toString());
                            Browser.getWindow().getConsole().log("POJO Object       " + pojo.toString());
                            assert pojo != null;
                            GwtReflect.fieldSet(declaringClass, fieldName, instance, pojo);
                        }
                    }
                } catch (Exception e) {
                    Browser.getWindow().getConsole().log(e.getMessage());
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
