/**
 * Copyright (c) 2017 Dotweblabs Web Technologies and others. All rights reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.parseplatform.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.reflect.shared.GwtReflect;
import com.google.gwt.user.client.Window;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Unit tests of {@link Where}
 *
 * @author Kerby Martino
 * @version 0-SNAPSHOT
 * @since 0-SNAPSHOT
 */
public class TestGwtMarshaller extends GWTTestCase {


    @Override
    public String getModuleName() {
        return "org.parseplatform.Parse";
    }

    public void testMarshall() {
        GwtReflect.magicClass(SimpleBean.class);

        GwtMarshaller marshaller = GWT.create(GwtMarshaller.class);

        SimpleBean simpleBean = new SimpleBean();
        Class<?> archetype = simpleBean.getClass();

        simpleBean.setObjectId("test-object-id");
        simpleBean.setTestint(1);
        simpleBean.setTestdouble(3.0);
        simpleBean.setTestlong(5L);
        simpleBean.setTestshort((short) 7);
        simpleBean.setTestbyte("test".getBytes()[0]);
        simpleBean.setTestbytes("tests".getBytes());
        simpleBean.setTestfloat(9);
        simpleBean.setTestboolean(false);
        simpleBean.setTestchar('a');

        simpleBean.setTestInteger(2);
        simpleBean.setTestDouble(4.0);
        simpleBean.setTestLong(6L);
        simpleBean.setTestShort((short) 8);
        simpleBean.setTestByte("Test".getBytes()[0]);
        simpleBean.setTestBytes(new Byte[10]); // TODO
        simpleBean.setTestFloat((float) 10);
        simpleBean.setTestBoolean(true);
        simpleBean.setTestCharacter('b');

        //simple bean to parse objects using accessors and mutators
        ParseObject parseObject = marshaller.marshall(simpleBean);
        Window.alert(">>>>>>>>" + parseObject.toString());
        //get non null values from model
        //populate expectation list
        //assert test expectation to mutant
        Window.alert("BEGIN TEST-----------------MARSHALL");

        assertEquals("test-object-id", parseObject.getString("objectId"));
        assertEquals("testint", parseObject.get("objectId"));

        /*
        Display values
         */
        Set<?> s = parseObject.keySet();
        //iterate and persist keys from model
        Iterator<?> i = s.iterator();
        do {
            String k = i.next().toString();
            Field[] fields = GwtReflect.getDeclaredFields(archetype);
            for (int c = 0; c < fields.length; c++) {
                if (k == fields[c].getName()) {
                    String message = null;
                    try {
                        Object expectableparse = null;
                        try {
                            expectableparse = parseObject.getString(k);
                            message = k + " " + parseObject.getString(k) + " <->" + " " + fields[c].getName() + "  " + fields[c].get(simpleBean);
                        } catch (Exception e) {
                        }
                        if (expectableparse == null) {
                            expectableparse = parseObject.get(k).toString();
                            message = k + " " + parseObject.get(k).toString() + " <->" + " " + fields[c].getName() + "  " + fields[c].get(simpleBean);
                        }
                        Object expectablefield = fields[c].get(simpleBean).toString();
                        if (expectablefield == expectableparse) {
                            message = "PASS: " + k + " " + expectableparse + " <-> " + " " + fields[c].getName() + "  " + fields[c].get(simpleBean);
                        } else {
                            message = "FAIL: " + k + " " + expectableparse + " <-> " + " " + fields[c].getName() + "  " + fields[c].get(simpleBean);
                        }
                        assertEquals(expectablefield, expectableparse);
                    } catch (Exception e) {
                        //Window.alert(e.toString());
                    }
                    Window.alert(message);
                }
            }
        } while (i.hasNext());
    }

    public static void log(String s) {
        System.out.println(s);
    }

}
