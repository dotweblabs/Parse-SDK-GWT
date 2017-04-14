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
package com.parse.gwt.client;

import com.parse.gwt.client.annotations.Column;
import com.parse.gwt.client.annotations.Entity;
import com.parse.gwt.client.annotations.ObjectId;
import com.parse.gwt.client.types.*;
import com.parse.gwt.client.types.Objek;
import com.promis.rtti.annotations.GenerateMethods;
import com.promis.rtti.annotations.GenerateRtti;

@GenerateMethods
@GenerateRtti
@Entity(name="_TestObject")
public class TestObject {

    @Column
    public Objek object;

    @Column
    public Objek nullObject;

    @Column
    public Array array;

    @Column
    public Array nullArray;

    @Column
    public File file;

    @Column(name="geoPoint")
    public GeoPoint geoPoint;

    @Column
    public Pointer pointer;

    @Column
    public Relation relation;

    //@Column
    //public Map<String,String> children;

    public String willNotBeMarshalled;

    @Column
    public String willBeMarshalled;

    @Column
    public Boolean isSomething;

    @Column
    public boolean isType;

    @ObjectId
    public String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isType() {
        return isType;
    }

    public void setType(boolean type) {
        isType = type;
    }

    public Boolean getSomething() {
        return isSomething;
    }

    public void setSomething(Boolean something) {
        isSomething = something;
    }

//    public Map getChildren() {
//        return children;
//    }
//
//    public void setChildren(Map children) {
//        this.children = children;
//    }
}
