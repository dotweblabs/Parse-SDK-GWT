package com.parse.gwt.client;

import com.google.gwt.junit.client.GWTTestCase;
import com.parse.gwt.client.util.AnnotationUtil;

/**
 * Created by Kerby on 2/18/2017.
 */
public class AnnotationTest extends GWTTestCase {
    @Override
    public String getModuleName() {
        return "com.parse.gwt.Parse";
    }
    public void testFunction() {
        TestObject testObject = new TestObject();
        testObject.setId("sample-object-id");
        AnnotationUtil.getAnnotations(testObject);
    }
}
