package org.parseplatform.client.beans;

import com.google.gwt.reflect.client.strategy.ReflectionStrategy;
import org.parseplatform.client.annotations.Column;
import org.parseplatform.client.annotations.ComponentType;
import org.parseplatform.client.annotations.RuntimeRetention;

import java.util.LinkedList;

@RuntimeRetention
@ReflectionStrategy(keepEverything=true)
public class TestBean {

    public TestBean() {}

    @Column
    @ComponentType(type = String.class)
    public LinkedList<String> strings;

    public LinkedList<String> getStrings() {
        return strings;
    }

    public void setStrings(LinkedList<String> strings) {
        this.strings = strings;
    }
}
