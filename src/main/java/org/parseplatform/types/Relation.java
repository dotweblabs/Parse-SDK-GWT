package org.parseplatform.types;

public class Relation {

    public String __type;
    public String className;

    public Relation() {
        set__type("Relation");
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String get__type() {
        return __type;
    }

    public void set__type(String __type) {
        this.__type = __type;
    }
}
