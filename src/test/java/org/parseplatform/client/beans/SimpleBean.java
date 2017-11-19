package org.parseplatform.client.beans;

import com.google.gwt.reflect.client.strategy.ReflectionStrategy;
import org.parseplatform.client.ParseFile;
import org.parseplatform.client.annotations.Column;
import org.parseplatform.client.annotations.RuntimeRetention;

import java.util.LinkedList;
import java.util.List;

@RuntimeRetention
@ReflectionStrategy(keepEverything=true)
public class SimpleBean {

    @Column
    public String objectId;

    @Column
    public String nullstr = null;

    @Column
    public int testint;

    @Column
    public double testdouble;

    @Column
    public long testlong;

    @Column
    public short testshort;

    @Column
    public byte testbyte;

    @Column
    public boolean testboolean;

    @Column
    public float testfloat;

    @Column
    public char testchar;

    @Column
    public Integer testInteger;

    @Column
    public Double testDouble;

    @Column
    public Long testLong;

    @Column
    public Short testShort;

    @Column
    public Byte testByte;

    @Column
    public Boolean testBoolean;

    @Column
    public Float testFloat;

    @Column
    public Character testCharacter;

    @Column
    public byte[] testbytes;

    @Column
    public Byte[] testBytes;

    @Column
    public ParseFile file;

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getNullstr() {
        return nullstr;
    }

    public void setNullstr(String nullstr) {
        this.nullstr = nullstr;
    }

    public int getTestint() {
        return testint;
    }

    public void setTestint(int testint) {
        this.testint = testint;
    }

    public double getTestdouble() {
        return testdouble;
    }

    public void setTestdouble(double testdouble) {
        this.testdouble = testdouble;
    }

    public long getTestlong() {
        return testlong;
    }

    public void setTestlong(long testlong) {
        this.testlong = testlong;
    }

    public short getTestshort() {
        return testshort;
    }

    public void setTestshort(short testshort) {
        this.testshort = testshort;
    }

    public byte getTestbyte() {
        return testbyte;
    }

    public void setTestbyte(byte testbyte) {
        this.testbyte = testbyte;
    }

    public boolean isTestboolean() {
        return testboolean;
    }

    public void setTestboolean(boolean testboolean) {
        this.testboolean = testboolean;
    }

    public float getTestfloat() {
        return testfloat;
    }

    public void setTestfloat(float testfloat) {
        this.testfloat = testfloat;
    }

    public char getTestchar() {
        return testchar;
    }

    public void setTestchar(char testchar) {
        this.testchar = testchar;
    }

    public Integer getTestInteger() {
        return testInteger;
    }

    public void setTestInteger(Integer testInteger) {
        this.testInteger = testInteger;
    }

    public Double getTestDouble() {
        return testDouble;
    }

    public void setTestDouble(Double testDouble) {
        this.testDouble = testDouble;
    }

    public Long getTestLong() {
        return testLong;
    }

    public void setTestLong(Long testLong) {
        this.testLong = testLong;
    }

    public Short getTestShort() {
        return testShort;
    }

    public void setTestShort(Short testShort) {
        this.testShort = testShort;
    }

    public Byte getTestByte() {
        return testByte;
    }

    public void setTestByte(Byte testByte) {
        this.testByte = testByte;
    }

    public Boolean getTestBoolean() {
        return testBoolean;
    }

    public void setTestBoolean(Boolean testBoolean) {
        this.testBoolean = testBoolean;
    }

    public Float getTestFloat() {
        return testFloat;
    }

    public void setTestFloat(Float testFloat) {
        this.testFloat = testFloat;
    }

    public Character getTestCharacter() {
        return testCharacter;
    }

    public void setTestCharacter(Character testCharacter) {
        this.testCharacter = testCharacter;
    }

    public byte[] getTestbytes() {
        return testbytes;
    }

    public void setTestbytes(byte[] testbytes) {
        this.testbytes = testbytes;
    }

    public Byte[] getTestBytes() {
        return testBytes;
    }

    public void setTestBytes(Byte[] testBytes) {
        this.testBytes = testBytes;
    }

    public ParseFile getFile() {
        return file;
    }

    public void setFile(ParseFile file) {
        this.file = file;
    }
}
