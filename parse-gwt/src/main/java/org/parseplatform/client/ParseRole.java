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
package org.parseplatform.client;

import com.google.gwt.json.client.JSONObject;

/**
 *
 * Parse Role object
 *
 * @author Kerby Martino
 * @since 0-SNAPSHOT
 * @version 0-SNAPSHOT
 */
public class ParseRole extends ParseObject {
    public ParseRole(){
        setClassName("_Role");
    }
    public ParseRole(String name){
        setClassName("_Role");
        putString("name", name);
        put("users", new ParseRelation("_User"));
        put("roles", new ParseRelation("_Role"));
    }
    public void setACL(ParseACL acl) {
        put("ACL", acl);
    }
    public ParseACL getACL() {
        JSONObject acl = get("ACL").isObject();
        ParseACL parseACL = (ParseACL) acl;
        return parseACL;
    }
}
