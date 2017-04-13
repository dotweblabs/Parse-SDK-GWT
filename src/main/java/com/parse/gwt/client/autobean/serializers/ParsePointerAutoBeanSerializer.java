package com.parse.gwt.client.autobean.serializers;

import com.google.gwt.core.client.GWT;
import com.parse.gwt.client.autobean.AutoBeanSerializer;
import com.parse.gwt.client.autobean.BaseFactory;
import com.parse.gwt.client.autobean.ParsePointer;

public class ParsePointerAutoBeanSerializer extends AutoBeanSerializer {
    interface Factory extends BaseFactory<ParsePointer> {
    }
    public ParsePointerAutoBeanSerializer() {
        super();
        Factory factory = GWT.create(Factory.class);
        this.factory = factory;
    }
}