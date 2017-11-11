package org.parseplatform.client;

public interface Unmarshaller {
    public <T> T unmarshall(Class<T> clazz, Object instance, ParseObject parseObject);
}