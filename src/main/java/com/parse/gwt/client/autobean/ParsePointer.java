package com.parse.gwt.client.autobean;

import com.google.web.bindery.autobean.shared.AutoBean;

public interface ParsePointer {
    @AutoBean.PropertyName("__type")
    String getType();
    String getClassName();
    String getObjectId();
    @AutoBean.PropertyName("__type")
    void setType(String type);
    void setClassName(String className);
    void setObjectId(String objectId);
}
