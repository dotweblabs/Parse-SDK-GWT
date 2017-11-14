package org.parseplatform.types;

import com.google.gwt.json.client.*;
import org.parseplatform.util.DateUtil;

import java.util.Date;

public class Objek extends JSONObject {
    public void putNull(String key) {
        put(key, JSONNull.getInstance());
    }
    public void putBoolean(String key, Boolean value) {
        if(value != null) {
            put(key, JSONBoolean.getInstance(value));
        }
    }
    public void putString(String key, String value) {
        if(value != null) {
            put(key, new JSONString(value));
        }
    }
    public void putNumber(String key, Integer value) {
        if(value != null) {
            put(key, new JSONNumber(value));
        }
    }
    public void putNumber(String key, Long value) {
        if(value != null) {
            put(key, new JSONNumber(value));
        }
    }
    public void putNumber(String key, Double value) {
        if(value != null) {
            put(key, new JSONNumber(value));
        }
    }
    public Boolean getBoolean(String key) {
        JSONValue value = get(key);
        if(value.isBoolean() != null) {
            return new Boolean(value.isBoolean().booleanValue());
        }
        return null;
    }
    public String getString(String key) {
        JSONValue value = get(key);
        if(value.isString() != null && value.isString().stringValue() != null) {
            return value.isString().stringValue();
        }
        return null;
    }
    public Double getNumber(String key) {
        JSONValue value = get(key);
        if(value.isNumber() != null) {
            double d = value.isNumber().doubleValue();
            return new Double(d);
        }
        return null;
    }
    public Date getDate(String key) {
        JSONValue value = get(key);
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
    public File getFile(String key) {
        JSONValue value = get(key);
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
    public GeoPoint getGeoPoint(String key) {
        JSONValue value = get(key);
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
    public Pointer getPointer(String key) {
        JSONValue value = get(key);
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
    public Relation getRelation(String key) {
        JSONValue value = get(key);
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
}