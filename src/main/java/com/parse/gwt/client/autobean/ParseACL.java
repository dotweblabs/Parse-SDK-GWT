package com.parse.gwt.client.autobean;

import com.google.web.bindery.autobean.shared.Splittable;
import com.google.web.bindery.autobean.shared.impl.StringQuoter;

/**
 * AutoBean implementation for Parse ACL
 */
public class ParseACL {

    private Splittable acl = null;

    public ParseACL() {
        acl = StringQuoter.createSplittable();
    }

    public ParseACL setPublicReadAccess(boolean isRead) {
        Splittable asterisk = StringQuoter.createSplittable();
        StringQuoter.create(isRead).assign(asterisk, "read");
        if(acl.get("*") != null && acl.get("*").get("write") != null) {
            boolean isWrite = acl.get("*").get("write").isBoolean();
            StringQuoter.create(isWrite).assign(asterisk, "write");
        }
        asterisk.assign(acl, "*");
        return this;
    }

    public ParseACL setPublicWriteAccess(boolean isWrite) {
        Splittable asterisk = StringQuoter.createSplittable();
        StringQuoter.create(isWrite).assign(asterisk, "write");
        if(acl.get("*") !=null && acl.get("*").get("read") != null) {
            boolean isRead = acl.get("*").get("read").isBoolean();
            StringQuoter.create(isRead).assign(asterisk, "read");
        }
        asterisk.assign(acl, "*");
        return this;
    }

    public ParseACL setObjectReadAccess(String objectId, boolean isRead) {
        Splittable objectACL = StringQuoter.createSplittable();
        StringQuoter.create(isRead).assign(objectACL, "read");
        if(acl.get(objectId) != null && acl.get(objectId).get("write") != null) {
            boolean isWrite = acl.get(objectId).get("write").isBoolean();
            StringQuoter.create(isWrite).assign(objectACL, "write");
        }
        objectACL.assign(acl, objectId);
        return this;
    }

    public ParseACL setObjectWriteAccess(String objectId, boolean isWrite) {
        Splittable objectACL = StringQuoter.createSplittable();
        StringQuoter.create(isWrite).assign(objectACL, "write");
        if(acl.get(objectId) != null && acl.get(objectId).get("read") != null) {
            boolean isRead = acl.get(objectId).get("read").isBoolean();
            StringQuoter.create(isWrite).assign(objectACL, "read");
        }
        objectACL.assign(acl, objectId);
        return this;
    }

    public Splittable asSplittable() {
        return acl;
    }

}
