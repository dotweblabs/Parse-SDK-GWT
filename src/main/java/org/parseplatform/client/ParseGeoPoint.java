/**
 *
 * Copyright (c) 2017 Dotweblabs Web Technologies and others. All rights reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package org.parseplatform.client;

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
