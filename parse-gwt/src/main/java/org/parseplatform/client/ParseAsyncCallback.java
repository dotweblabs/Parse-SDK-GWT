package org.parseplatform.client;

public interface ParseAsyncCallback<T> {
    void onFailure(ParseError error);
    void onSuccess(T var1);
}
