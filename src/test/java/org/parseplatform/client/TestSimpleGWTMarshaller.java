package org.parseplatform.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.reflect.shared.GwtReflect;
import com.google.gwt.user.client.Window;
import org.parseplatform.client.beans.TestBean;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * NOTE: For some reason, GwtRelect throws error when running test
 * you must `mvn clean` before any test, otherwise the error will be thrown
 */
public class TestSimpleGWTMarshaller extends GWTTestCase {

    {
        GwtReflect.magicClass(TestBean.class);
    }

    @Override
    public String getModuleName() {
        return "org.parseplatform.Parse";
    }

    public void testMarshall() {
        TestBean testBean = new TestBean();
        testBean.setStrings(new LinkedList<>(Arrays.asList("test", "test2")));
        GwtMarshaller marshaller = GWT.create(GwtMarshaller.class);
        ParseObject parseObject = marshaller.marshall(testBean);
        assertNotNull(parseObject);
        Window.alert(parseObject.toString());
    }
}
