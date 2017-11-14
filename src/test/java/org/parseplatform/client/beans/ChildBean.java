package org.parseplatform.client.beans;

import com.google.gwt.reflect.client.strategy.ReflectionStrategy;
import org.parseplatform.client.*;
import org.parseplatform.client.annotations.Column;
import org.parseplatform.client.annotations.RuntimeRetention;

@RuntimeRetention
@ReflectionStrategy(keepEverything=true)
public class ChildBean {
    @Column
    public String name;
    @Column
    public int age;
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
}
