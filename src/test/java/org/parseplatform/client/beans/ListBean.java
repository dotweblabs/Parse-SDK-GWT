package org.parseplatform.client.beans;

import com.google.gwt.reflect.client.strategy.ReflectionStrategy;
import org.parseplatform.client.annotations.Column;
import org.parseplatform.client.annotations.RuntimeRetention;

import java.util.List;

@RuntimeRetention
@ReflectionStrategy(keepEverything=true)
public class ListBean {
    @Column
    public List<String> testStrings;

    @Column
    public List<Integer> testIntegers;

    @Column
    public List<Double> testDoubles;

    @Column
    public List<Long> testLongs;

    @Column
    public List<Short> testShorts;

    @Column
    public List<Byte> testBytes;

    @Column
    public List<Boolean> testBooleans;

    @Column
    public List<Float> testFloats;

    @Column
    public List<Character> testCharacters;

}
