package com.parse.gwt.client.js.parse;

import com.parse.gwt.client.js.base.JsPropertyMap;

/**
 * Created by Kerby on 4/14/2017.
 */
public class ParseACL implements JsPropertyMap<AccessControl> {

    public ParseACL setPublicReadAccess(boolean isRead) {
        if (get("*") != null) {
            get("*").read = isRead;
        }
        return this;
    }

    public ParseACL setPublicWriteAccess(boolean isWrite) {
        if (get("*") != null) {
            get("*").write = isWrite;
        }
        return this;
    }

    public ParseACL setObjectReadAccess(String objectId, boolean isRead) {
        if (get(objectId) != null) {
            get(objectId).read = isRead;
        }
        return this;
    }

    public ParseACL setObjectWriteAccess(String objectId, boolean isWrite) {
        if (get(objectId) != null) {
            get(objectId).write = isWrite;
        }
        return this;
    }

}
