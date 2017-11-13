package org.parseplatform.client;

import com.google.gwt.json.client.JSONBoolean;
import com.google.gwt.json.client.JSONNull;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.reflect.shared.GwtReflect;
import elemental.client.Browser;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Set;

public class GwtUnmarshaller implements Unmarshaller {
    @Override
    public <T> T unmarshall(Class<T> clazz, Object instance, ParseObject parseObject) {
        if(instance == null){
            throw new RuntimeException("Object cannot be null");
        }
        Class<?> declaringClass = instance.getClass();

        //get objectID from model object
        String objID = null;
        Field objectFIELD = null;
        try {
            objectFIELD = declaringClass.getField("objectId");
            objID = GwtReflect.fieldGet(declaringClass, "objectId", instance);
        } catch (Exception e) {
            e.printStackTrace();
        }



        //check if model object has objectId field
        if(objID != null ) {
            //parseMODEL.putString("objectId", String.valueOf(objID));
        }

        //get type information of object in parameter


        //get fields in parameter object class

        //parseObject.setObjectId(Integer.toString(fields.length));
        //get annotations but specifically those with column from parameter object skip for now


       //match keys from parse model to mutant
        Set<?> s =  parseObject.keySet();
        //Browser.getWindow().getConsole().log("key size");
        //Browser.getWindow().getConsole().log(s.size());
        //Set<String> keyset = parseObject.keySet();
        //iterate and persist keys from model
        // get fields from mutant
        Field[] fields = GwtReflect.getPublicFields(instance.getClass());
        Iterator<?> i = s.iterator();
        do {
            String k = i.next().toString();
            //iterate through mutant fields to match model
            for (int c = 0; c < fields.length; c++) {
                try {
                    if (k == fields[c].getName()) {
                        //Browser.getWindow().getConsole().log("match");

                        Class<?> classType = fields[c].getType();
                        String fieldName = fields[c].getName();
                        Object value = null;
                        JSONBoolean boolVal = null;
                        JSONString stringVal = null;
                        JSONNumber numVal = null;
                        try {
                            value = parseObject.get(k);
                            boolVal = parseObject.get(k).isBoolean();
                            stringVal = parseObject.get(k).isString();
                            numVal = parseObject.get(k).isNumber();


                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        if (value != null) {
                            //fieldType get name
                         if (boolVal != null) {
                                //Browser.getWindow().getConsole().log("Boolean type found");

                                Boolean booleanValue = (Boolean) value;
                                GwtReflect.fieldSet(declaringClass, fieldName, instance, booleanValue);
                                if (booleanValue != null) {
                                    //parseObject.put(fieldName, JSONBoolean.getInstance(booleanValue));
                                } else {
                                    // parseObject.put(fieldName, null);
                                }
                            } else if (numVal != null) {

                                try {
                                    //Browser.getWindow().getConsole().log("test int");
                                    int testINT =  Integer.parseInt(parseObject.get(k).toString());
                                    GwtReflect.fieldSet(declaringClass, fieldName, instance, testINT);
                                }

                                catch (Exception e) {
                                    double testDOUBLE = Double.parseDouble(parseObject.get(k).toString());
                                    GwtReflect.fieldSet(declaringClass, fieldName, instance, testDOUBLE);

                                }

                            } else if (stringVal != null) {

                             //String stringValue = (String) value;
                             String getstring = parseObject.getString(fieldName);
                             //Browser.getWindow().getConsole().log("string");
                             //Browser.getWindow().getConsole().log(stringValue);
                             GwtReflect.fieldSet(declaringClass, fieldName, instance, getstring);
                             //parseObject.put(fieldName, new JSONString(stringValue));
                         }
                        }
                    }
                } catch (Exception e) {}

            }
        }while(i.hasNext());



        /*

            //implement later

            else if(classType.getName() ==Date.class.getName()) {
                Browser.getWindow().getConsole().log("Date type found");
                JSONObject jsonDate = new JSONObject();
                Date date = (Date) value;
                jsonDate.put("__type", new JSONString("Date"));
                jsonDate.put("iso", new JSONString(DateUtil.getStringFormat(date)));
                parseObject.put(fieldName, jsonDate);
            } else if(classType.getName() == Map.class.getName()) { // Object
                Browser.getWindow().getConsole().log("Map type found");
                throw new RuntimeException("Map is not supported use com.parse.gwt.client.types.Object instead");
            } else if(classType.getName() == List.class.getName()) {
                Browser.getWindow().getConsole().log("List type found");
                throw new RuntimeException("List is not supported use com.parse.gwt.client.types.Array instead");
            } else if(classType.getName() == File.class.getName()) {
                Browser.getWindow().getConsole().log("File type found");
                JSONObject jsonFile = new JSONObject();
                File file = (File) value;
                jsonFile.put("__type", new JSONString("File"));
                jsonFile.put("url", new JSONString(file.url));
                jsonFile.put("name", new JSONString(file.name));
                parseObject.put(fieldName, jsonFile);
            } else if(classType.getName == GeoPoint.class.getName()) {
                Browser.getWindow().getConsole().log("GeoPoint type found");
                GeoPoint geoPoint = (GeoPoint) value;
                ParseGeoPoint parseGeoPoint = new ParseGeoPoint(geoPoint.longitude, geoPoint.latitude);
                parseObject.put(fieldName, parseGeoPoint);
            } else if(classType.getName() == Pointer.class.getName() ) {
                Browser.getWindow().getConsole().log("Pointer type found");
                Pointer pointer = (Pointer) value;
                JSONObject jsonPointer = new JSONObject();
                jsonPointer.put("__type", new JSONString("Pointer"));
                jsonPointer.put("className", new JSONString(pointer.className));
                jsonPointer.put("objectId", new JSONString(pointer.objectId));
                parseObject.put(fieldName, jsonPointer);
            } else if(classType.getName() == Relation.class.getName()) {
                Browser.getWindow().getConsole().log("Relation type found");
                Relation relation = (Relation) value;
                JSONObject jsonRelation = new JSONObject();
                jsonRelation.put("__type", new JSONString("Relation"));
                jsonRelation.put("className", new JSONString(relation.className));
                parseObject.put(fieldName, jsonRelation);
            } else if(classType.getName() == Array.class.getName()) {
                Browser.getWindow().getConsole().log("Array type found");
                Array arrayVaulue = (Array) value;
                parseObject.put(fieldName, (JSONArray) value);
            } else if(classType.equals(Objek.class)) {
                Browser.getWindow().getConsole().log("Object type found");
                parseObject.put(fieldName, (JSONObject) value);
            }
/          else {
                throw new RuntimeException("Unsupported type for field");
            }
        } else {
            parseObject.put(fieldName, JSONNull.getInstance());
        }*/
        return (T) instance;
    }
}
