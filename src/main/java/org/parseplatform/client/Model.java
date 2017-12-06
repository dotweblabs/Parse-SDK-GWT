package org.parseplatform.client;

/**
 * Core model
 *
 * @author kerbymart
 * @version 1.0.0
 * @since 1.0.0
 */
public interface Model {
    Model fromParseObject(ParseObject parseObject);
    ParseObject toParseObject();
}
