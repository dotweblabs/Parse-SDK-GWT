package org.parseplatform.client;

import org.parseplatform.JSONMapper;

import java.util.List;
import java.util.Map;

@JSONMapper
public class ParseBean {
    private int ainteger;
    private Integer aInteger;
    private double adouble;
    private Double aDouble;
    private ParseACL parseACL;
    private ParseDate parseDate;
    private ParseGeoPoint parseGeoPoint;
    private ParsePointer parsePointer;
    private ParseRelation parseRelation;
    private ParseRole parseRole;
    private ParseUser parseUser;
    private Map<String,Integer> integerMap;
    private Map<String,Double> doubleMap;
    private Map<String,Boolean> booleanMap;
    private List<String> stringList;
    private List<Double> doubleList;
    private List<Boolean> booleanList;
    private ChildParseBean childParseBean;
    private List<ChildParseBean> childParseBeans;
    private Map<String,ChildParseBean> childParseBeanMap;
    private Map<Integer,ChildParseBean> childParseBeanMap2;

    public int getAinteger() {
        return ainteger;
    }

    public void setAinteger(int ainteger) {
        this.ainteger = ainteger;
    }

    public Integer getaInteger() {
        return aInteger;
    }

    public void setaInteger(Integer aInteger) {
        this.aInteger = aInteger;
    }

    public double getAdouble() {
        return adouble;
    }

    public void setAdouble(double adouble) {
        this.adouble = adouble;
    }

    public Double getaDouble() {
        return aDouble;
    }

    public void setaDouble(Double aDouble) {
        this.aDouble = aDouble;
    }
}
