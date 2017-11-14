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
public class TestGwtUnmarshaller extends GWTTestCase {


    @Override
    public String getModuleName() {
        return "org.parseplatform.Parse";
    }

    public void testUnmarshaller() {
        GwtReflect.magicClass(SimpleBean.class);

        GwtUnmarshaller unmarshaller = GWT.create(GwtUnmarshaller.class);

        ParseObject parseObject = new ParseObject();
        parseObject.putString("objectId", "test-object-id");
        parseObject.putString("nullstr", null);
        parseObject.putNumber("testint", 1);
        parseObject.putNumber("testdouble", 2.0);
        parseObject.putNumber("testlong", 3L);
        parseObject.putNumber("testshort", 4);
        parseObject.put("testbyte", null); // TODO
        parseObject.putBoolean("testboolean", true);
        parseObject.putNumber("testfloat", 5.0);
        parseObject.putString("testchar", "a");

        parseObject.putNumber("testInteger", 6);
        parseObject.putNumber("testDouble", 7);
        parseObject.putNumber("testLong", 8L);
        parseObject.putNumber("testShort", 9);
        parseObject.put("testByte", null);
        parseObject.putBoolean("testBoolean", false);
        parseObject.putNumber("testFloat", 10);
        parseObject.putString("testCharacter", "a");
        parseObject.put("testbytes", null); // TODO
        parseObject.putString("testBytes", null); // TODO

        Window.alert("<<<<<<<<" + parseObject.toString());
        SimpleBean simpleBean = new SimpleBean();
        Class<?> archetype = simpleBean.getClass();
        //get non null values from model

        simpleBean = unmarshaller.unmarshall(SimpleBean.class, simpleBean, parseObject);
        //get field names, get method names
        //iterate field contents

        Set<?> s = parseObject.keySet();
        //iterate and persist keys from model
        Window.alert("BEGIN TEST-----------------UNMARSHALL");
        Iterator<?> i = s.iterator();
        do {
            String k = i.next().toString();
            Field[] fields = GwtReflect.getPublicFields(archetype);
            for (int c = 0; c < fields.length; c++) {
                if (k == fields[c].getName()) {
                    String message = null;
                    try {
                        Object expectableparse = null;
                        try {
                            expectableparse = parseObject.getString(k);
                            message = k + " " + parseObject.getString(k) + " <->" + " " + fields[c].getName() + " " + fields[c].get(simpleBean);
                        } catch (Exception e) {
                        }
                        if (expectableparse == null) {
                            expectableparse = parseObject.get(k).toString();
                            message = k + " " + parseObject.get(k).toString() + " <->" + " " + fields[c].getName() + " " + fields[c].get(simpleBean);
                        }
                        Object expectablefield = fields[c].get(simpleBean).toString();
                        if (expectablefield == expectableparse) {
                            message = "PASS: " + k + " " + expectableparse + " <-> " + " " + fields[c].getName() + " " + fields[c].get(simpleBean);
                        } else {
                            message = "FAIL: " + k + " " + expectableparse + " <-> " + " " + fields[c].getName() + " " + fields[c].get(simpleBean);
                        }
                        assertEquals(expectablefield, expectableparse); //find workaround for string bug
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
