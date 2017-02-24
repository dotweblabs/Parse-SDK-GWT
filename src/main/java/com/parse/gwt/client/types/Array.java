package com.parse.gwt.client.types;

import com.google.gwt.json.client.*;
import com.parse.gwt.client.util.DateUtil;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Kerby on 2/24/2017.
 */
public class Array extends JSONArray {
    public Boolean getBoolean(int index) {
        JSONValue value = get(index);
        if(value.isBoolean() != null) {
            return new Boolean(value.isBoolean().booleanValue());
        }
        return null;
    }
    public String getString(int index) {
        JSONValue value = get(index);
        if(value.isString() != null && value.isString().stringValue() != null) {
            return value.isString().stringValue();
        }
        return null;
    }
    public Double getNumber(int index) {
        JSONValue value = get(index);
        if(value.isNumber() != null) {
            double d = value.isNumber().doubleValue();
            return new Double(d);
        }
        return null;
    }
    public Date getDate(int index) {
        JSONValue value = get(index);
        if(value.isObject() != null) {
            if(value.isObject().get("__type") != null
                    && value.isObject().get("iso") != null
                    && value.isObject().get("__type").isString().stringValue().equals("Date")) {
                String type = value.isObject().get("__type").isString().stringValue();
                String iso = value.isObject().get("iso").isString().stringValue();
                Date date = DateUtil.iso8601String(iso);
                return date;
            }
        }
        return null;
    }
    public File getFile(int index) {
        JSONValue value = get(index);
        File file = null;
        if(value != null) {
            if(value.isObject().get("__type") != null
                    && value.isObject().get("name") != null
                    && value.isObject().get("url") != null
                    && value.isObject().get("__type").isString().stringValue().equals("File")) {
                if(file == null) {
                    file = new File();
                }
                String type = value.isObject().get("__type").isString().stringValue();
                String name = value.isObject().get("name").isString().stringValue();
                String url = value.isObject().get("url").isString().stringValue();
                file.url = url;
                file.name = name;
            }

        }
        return file;
    }
    public GeoPoint getGeoPoint(int index) {
        JSONValue value = get(index);
        GeoPoint geoPoint = null;
        if(value != null) {
            if(value.isObject().get("__type") != null
                    && value.isObject().get("name") != null
                    && value.isObject().get("url") != null
                    && value.isObject().get("__type").isString().stringValue().equals("GeoPoint")) {
                if(geoPoint == null) {
                    geoPoint = new GeoPoint();
                }
                String type = value.isObject().get("__type").isString().stringValue();
                String longitude = value.isObject().get("longitude").isString().stringValue();
                String latitude = value.isObject().get("latitude").isString().stringValue();
                geoPoint.longitude = Double.valueOf(longitude);
                geoPoint.latitude = Double.valueOf(latitude);
            }
        }
        return geoPoint;
    }
    public Pointer getPointer(int index) {
        JSONValue value = get(index);
        Pointer pointer = null;
        if(value != null) {
            if(value.isObject().get("__type") != null
                    && value.isObject().get("className") != null
                    && value.isObject().get("objectId") != null
                    && value.isObject().get("__type").isString().stringValue().equals("Pointer")) {
                if(pointer == null) {
                    pointer = new Pointer();
                }
                String type = value.isObject().get("__type").isString().stringValue();
                String className = value.isObject().get("className").isString().stringValue();
                String objectId = value.isObject().get("objectId").isString().stringValue();
                pointer.className = className;
                pointer.objectId = objectId;
            }
        }
        return pointer;
    }
    public Relation getRelation(int index) {
        JSONValue value = get(index);
        Relation relation = null;
        if(value != null) {
            if(value.isObject().get("__type") != null
                    && value.isObject().get("className") != null
                    && value.isObject().get("__type").isString().stringValue().equals("Relation")) {
                if(relation == null) {
                    relation = new Relation();
                }
                String type = value.isObject().get("__type").isString().stringValue();
                String className = value.isObject().get("className").isString().stringValue();
                relation.className = className;
            }
        }
        return relation;
    }
    public void putNull(int index) {
        set(index, JSONNull.getInstance());
    }
    public void putBoolean(int index, Boolean value) {
        if(value != null) {
            set(index, JSONBoolean.getInstance(value));
        }
    }
    public void putString(int index, String value) {
        if(value != null) {
            set(index, new JSONString(value));
        }
    }
    public void putNumber(int index, Integer value) {
        if(value != null) {
            set(index, new JSONNumber(value));
        }
    }
    public void putNumber(int index, Long value) {
        if(value != null) {
            set(index, new JSONNumber(value));
        }
    }
    public void putNumber(int index, Double value) {
        if(value != null) {
            set(index, new JSONNumber(value));
        }
    }
    /*
    public void set(int index, Boolean value) {

    }
    public void set(int index, boolean value) {

    }
    public void set(int index, String value) {

    }
    public void set(int index, Integer value) {

    }
    public void set(int index, Double value) {

    }
    public void set(int index, Long value) {

    }
    public void set(int index, int value) {

    }
    public void set(int index, double value) {

    }
    public void set(int index, long value) {

    }
    public void set(int index, Date value) {

    }
    public void set(int index, File value) {

    }
    public void set(int index, GeoPoint value) {

    }
    public void set(int index, Pointer value) {

    }
    public void set(int index, Relation value) {

    }
    */
}
