package com.parse.gwt.client.autobean;

import com.google.gwt.core.client.GWT;
import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanCodex;
import com.google.web.bindery.autobean.shared.AutoBeanFactory;
import com.google.web.bindery.autobean.shared.AutoBeanUtils;

public class AutoBeanSerializer {

    protected BaseFactory factory;

    public AutoBeanSerializer() {
        factory = null;
    }

    public AutoBeanSerializer(BaseFactory factory) {
        super();
        this.factory = factory;
    }

    public <T> String encodeData(T data) {
        AutoBean<T> autoBean = AutoBeanUtils.getAutoBean(data);
        return AutoBeanCodex.encode(autoBean).getPayload();
    }

    public <T> T decodeData(Class<T> dataType, String json) {
        AutoBean<T> bean = AutoBeanCodex.decode(factory, dataType, json);
        return bean.as();
    }

    public <T> T make() {
        return (T) this.factory.bean().as();
    }

}