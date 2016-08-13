package com.parse.gwt.client;

import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;

/**
 *
 * Parse GeoPoint object
 *
 * @author Kerby Martino
 * @since 0-SNAPSHOT
 * @version 0-SNAPSHOT
 */
public class ParseGeoPoint extends JSONObject {
    public ParseGeoPoint() {}
    public ParseGeoPoint(Double longitude, Double latitude) {
        put("__type", new JSONString("GeoPoint"));
        put("longitude", new JSONNumber(longitude));
        put("latitude", new JSONNumber(latitude));
    }
    public Double getLongitude() {
        Double longitude = get("longitude").isNumber().doubleValue();
        return longitude;
    }
    public Double getLatitude() {
        Double latitude = get("latitude").isNumber().doubleValue();
        return latitude;
    }
    public static ParseGeoPoint clone(JSONObject reference) {
        String longitude = reference.get("longitude").isString().stringValue();
        String latitude = reference.get("latitude").isString().stringValue();
        ParseGeoPoint geoPoint = new ParseGeoPoint();
        geoPoint.put("__type", new JSONString("GeoPoint"));
        geoPoint.put("longitude", new JSONString(longitude));
        geoPoint.put("latitude", new JSONString(latitude));
        return geoPoint;
    }
}
