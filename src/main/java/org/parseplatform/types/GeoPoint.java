package org.parseplatform.types;

public class GeoPoint {

    public String __type;
    public Double longitude;
    public Double latitude;

    public GeoPoint() {
        set__type("GeoPoint");
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String get__type() {
        return __type;
    }

    public void set__type(String __type) {
        this.__type = __type;
    }
}

