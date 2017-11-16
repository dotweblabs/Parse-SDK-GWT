package org.parseplatform.client.beans;

import com.google.gwt.reflect.client.strategy.ReflectionStrategy;
import org.parseplatform.client.*;
import org.parseplatform.client.annotations.Column;
import org.parseplatform.client.annotations.RuntimeRetention;

import java.util.List;

@RuntimeRetention
@ReflectionStrategy(keepEverything=true)
public class ParentBean {
    @Column
    public String name;
    @Column
    public Integer age;
    @Column
    public ChildBean[] childBeans;
    @Column
    public ChildBean favorite;
    @Column
    public List<ChildBean> children;
    @Column
    public ParseACL acl;
    @Column
    public ParseRole role;
    @Column
    public ParseGeoPoint point;
    @Column
    public ParseFile file;
    @Column
    public ParseRelation relation;
    @Column
    public ParseResponse response;
    @Column
    public ParseQuery query;
    @Column
    public ParseDate date;
    @Column
    public ParseCloud cloud;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public ChildBean getFavorite() {
        return favorite;
    }

    public void setFavorite(ChildBean favorite) {
        this.favorite = favorite;
    }

    public List<ChildBean> getChildren() {
        return children;
    }

    public void setChildren(List<ChildBean> children) {
        this.children = children;
    }

    public ChildBean[] getChildBeans() {
        return childBeans;
    }

    public void setChildBeans(ChildBean[] childBeans) {
        this.childBeans = childBeans;
    }
}
