package com.parse.gwt.client.autobean;

import com.google.web.bindery.autobean.shared.Splittable;
import com.google.web.bindery.autobean.shared.impl.StringQuoter;

/**
 * Parse ACL AutoBean overlay
 */
public class ParseACL {

    private static final String ASTERISK = "*";
    private Splittable acl = null;

    public ParseACL() {
        acl = StringQuoter.createSplittable();
    }

    public ParseACL setPublicReadAccess(boolean isRead) {
        if(acl != null && acl.get(ASTERISK) != null) {
            Splittable asteriskACL = acl.get("*");
            StringQuoter.create(isRead).assign(asteriskACL, "read");
            asteriskACL.assign(acl, ASTERISK);
        } else {
            Splittable asteriskACL = StringQuoter.createSplittable();
            StringQuoter.create(isRead).assign(asteriskACL, "read");
            asteriskACL.assign(acl, ASTERISK);
        }
        return this;
    }

    public ParseACL setPublicWriteAccess(boolean isWrite) {
        if(acl != null && acl.get(ASTERISK) != null) {
            Splittable asteriskACL = acl.get("*");
            StringQuoter.create(isWrite).assign(asteriskACL, "write");
            asteriskACL.assign(acl, ASTERISK);
        } else {
            Splittable asteriskACL = StringQuoter.createSplittable();
            StringQuoter.create(isWrite).assign(asteriskACL, "write");
            asteriskACL.assign(acl, ASTERISK);
        }
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
