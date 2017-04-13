package com.parse.gwt.client.autobean.serializers;

import com.google.gwt.core.client.GWT;
import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanCodex;
import com.google.web.bindery.autobean.shared.AutoBeanUtils;
import com.parse.gwt.client.autobean.AutoBeanSerializer;
import com.parse.gwt.client.autobean.BaseFactory;
import com.parse.gwt.client.autobean.ParseDate;
import com.parse.gwt.client.autobean.ParsePointer;

public class ParseDateAutoBeanSerializer extends AutoBeanSerializer {
    interface Factory extends BaseFactory<ParseDate> {
    }
    public ParseDateAutoBeanSerializer() {
        super();
        Factory factory = GWT.create(Factory.class);
        this.factory = factory;
    }

    @Override
    public <T> String encodeData(T data) {
        AutoBean<T> autoBean = AutoBeanUtils.getAutoBean(data);
        return AutoBeanCodex.encode(autoBean).getPayload();
    }
}