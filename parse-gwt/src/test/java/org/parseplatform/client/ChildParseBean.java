package org.parseplatform.client;

import org.parseplatform.JSONMapper;

import java.util.List;
import java.util.Map;

@JSONMapper
public class ChildParseBean {
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
}
