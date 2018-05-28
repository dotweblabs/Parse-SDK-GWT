/**
 *
 * Copyright (c) 2017 Dotweblabs Web Technologies and others. All rights reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package org.parseplatform.client.js.parse;

import org.parseplatform.client.js.base.JsPropertyMap;

public class ParseACL implements JsPropertyMap<AccessControl> {

    public ParseACL setPublicReadAccess(boolean isRead) {
        if (get("*") != null) {
            get("*").read = isRead;
        } else {
            AccessControl accessControl = new AccessControl();
            accessControl.read = isRead;
            set("*", accessControl);
        }
        return this;
    }

    public ParseACL setPublicWriteAccess(boolean isWrite) {
        if (get("*") != null) {
            get("*").write = isWrite;
        } else {
            AccessControl accessControl = new AccessControl();
            accessControl.write = isWrite;
            set("*", accessControl);
        }
        return this;
    }

    public ParseACL setObjectReadAccess(String objectId, boolean isRead) {
        if (get(objectId) != null) {
            get(objectId).read = isRead;
        } else {
            AccessControl accessControl = new AccessControl();
            accessControl.read = isRead;
            set(objectId, accessControl);
        }
        return this;
    }

    public ParseACL setObjectWriteAccess(String objectId, boolean isWrite) {
        if (get(objectId) != null) {
            get(objectId).write = isWrite;
        }else {
            AccessControl accessControl = new AccessControl();
            accessControl.write = isWrite;
            set(objectId, accessControl);
        }
        return this;
    }

}
