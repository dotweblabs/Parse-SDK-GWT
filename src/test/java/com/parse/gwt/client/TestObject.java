package com.parse.gwt.client;

import com.parse.gwt.client.annotations.Entity;
import com.parse.gwt.client.annotations.ObjectId;

@Entity
public class TestObject {
    @ObjectId
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
