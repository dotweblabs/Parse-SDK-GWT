package com.parse.gwt.client.autobean;

import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanFactory;

public interface BaseFactory<T> extends AutoBeanFactory {
    public AutoBean<T> bean();
}
