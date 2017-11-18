package org.parseplatform.client.beans;

import com.google.gwt.reflect.client.strategy.ReflectionStrategy;
import org.parseplatform.client.*;
import org.parseplatform.client.annotations.Column;
import org.parseplatform.client.annotations.ComponentType;
import org.parseplatform.client.annotations.RuntimeRetention;

import java.util.Date;
import java.util.LinkedList;

@RuntimeRetention
@ReflectionStrategy(keepEverything=true)
public class ChildBean {
    @Column
    public String name;
    @Column
    public int age;
    @Column
    public Date dob;

    @Column
    @ComponentType(type = String.class)
    public LinkedList<String> shows;

    @Column
    @ComponentType(type = Boolean.class)
    public LinkedList<Boolean> yess;

    @Column
    @ComponentType(type = Double.class)
    public LinkedList<Double> doubles;

    @Column
    @ComponentType(type = Float.class)
    public LinkedList<Float> floats;

    @Column
    @ComponentType(type = Short.class)
    public LinkedList<Short> shorts;

    @Column
    public ParseDate birthdate;
    @Column
    public ParseACL ACL;
    @Column
    public ParseRole role;
    @Column
    public ParseRelation relation;
    @Column
    public ParsePointer pointer;
    @Column
    public ParseGeoPoint geoPoint;
    @Column
    public ParseFile file;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public ParseFile getFile() {
        return file;
    }

    public void setFile(ParseFile file) {
        this.file = file;
    }

    public ParseGeoPoint getGeoPoint() {
        return geoPoint;
    }

    public void setGeoPoint(ParseGeoPoint geoPoint) {
        this.geoPoint = geoPoint;
    }

    public ParsePointer getPointer() {
        return pointer;
    }

    public void setPointer(ParsePointer pointer) {
        this.pointer = pointer;
    }

    public ParseDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(ParseDate birthdate) {
        this.birthdate = birthdate;
    }

    public ParseACL getACL() {
        return ACL;
    }

    public void setACL(ParseACL ACL) {
        this.ACL = ACL;
    }

    public ParseRole getRole() {
        return role;
    }

    public void setRole(ParseRole role) {
        this.role = role;
    }

    public ParseRelation getRelation() {
        return relation;
    }

    public void setRelation(ParseRelation relation) {
        this.relation = relation;
    }

    @Override
    public String toString() {
        String s = "";
        s = s + "name=" + name ;
        s = s + ",age=" + age;
        return s;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public LinkedList<String> getShows() {
        return shows;
    }

    public void setShows(LinkedList<String> shows) {
        this.shows = shows;
    }

    public LinkedList<Boolean> getYess() {
        return yess;
    }

    public void setYess(LinkedList<Boolean> yess) {
        this.yess = yess;
    }

    public LinkedList<Double> getDoubles() {
        return doubles;
    }

    public void setDoubles(LinkedList<Double> doubles) {
        this.doubles = doubles;
    }

    public LinkedList<Float> getFloats() {
        return floats;
    }

    public void setFloats(LinkedList<Float> floats) {
        this.floats = floats;
    }

    public LinkedList<Short> getShorts() {
        return shorts;
    }

    public void setShorts(LinkedList<Short> shorts) {
        this.shorts = shorts;
    }
}
