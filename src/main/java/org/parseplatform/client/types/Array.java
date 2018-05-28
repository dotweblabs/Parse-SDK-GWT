package org.parseplatform.client.types;

/*
File:  Array.java
Version: 0-SNAPSHOT
Contact: hello@dotweblabs.com
----
Copyright (c) 2018, Dotweblabs Web Technologies
All rights reserved.
Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
    * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
    * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
    * Neither the name of Dotweblabs Web Technologies nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.
THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES,
INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

import com.google.gwt.json.client.*;
import org.parseplatform.client.util.DateUtil;
import java.util.Date;

/**
 * @author Kerby Martino
 * @since 0-SNAPSHOT
 * @version 0-SNAPSHOT
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
    public void putArray(int index, JSONArray value) {
        if(value != null) {
            set(index, value);
        }
    }
    public void putObject(int index, JSONObject value) {
        if(value != null) {
            set(index, value);
        }
    }
}
