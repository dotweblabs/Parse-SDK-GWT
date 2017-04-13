package com.parse.gwt.client.autobean.serializers;

import com.google.gwt.core.client.GWT;
import com.parse.gwt.client.autobean.AutoBeanSerializer;
import com.parse.gwt.client.autobean.BaseFactory;
import com.parse.gwt.client.autobean.ParseFile;

public class ParseFileAutoBeanSerializer extends AutoBeanSerializer {
    interface Factory extends BaseFactory<ParseFile> {
    }
    public ParseFileAutoBeanSerializer() {
        super();
        Factory factory = GWT.create(Factory.class);
        this.factory = factory;
    }
}