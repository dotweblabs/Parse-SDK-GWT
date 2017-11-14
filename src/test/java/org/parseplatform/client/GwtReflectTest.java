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
import com.google.gwt.json.client.*;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.reflect.shared.GwtReflect;
import com.google.gwt.user.client.Window;
import elemental.client.Browser;

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
public class GwtReflectTest extends GWTTestCase {


    @Override
    public String getModuleName() {
        return "org.parseplatform.Parse";
    }

    public void testSimpleBean() {
        GwtReflect.magicClass(SimpleBean.class);

        SimpleBean simpleBean = new SimpleBean();
        try {
            //get all fields and methods
            List<String> fieldnames = new ArrayList<>();
            List<String> methodnames = new ArrayList<>();
            Class<?> declaringClass = simpleBean.getClass();
            Object[] fields = GwtReflect.getPublicFields(declaringClass);
            Object[] methods = GwtReflect.getPublicMethods(declaringClass);


            // check fields
            for (int c = 0; c < methods.length; c++) {

                methodnames.add(methods[c].toString().substring(methods[c].toString().lastIndexOf('.') + 1));
            }

            //GwtReflect.fieldSet(SimpleBean.class, "age", simpleBean, 10);
            //GwtReflect.fieldSet(SimpleBean.class, "name", simpleBean, "Juan Dela Cruz");
            // String reflection = "";

            //reflection = Boolean.toString(fields.getClass().isArray());

//            Browser.getWindow().getConsole().log(simpleBean.getName());
//            Browser.getWindow().getConsole().log(simpleBean.getAge());
            // Window.alert(fields[0].toString());
            // Window.alert(simpleBean.getName());
            // Window.alert(simpleBean.getAge() + "");
//            Browser.getWindow().getConsole().log(simpleBean.getName());
//            Browser.getWindow().getConsole().log(simpleBean.getAge());
            //Window.alert(simpleBean.getName());
            //Window.alert(simpleBean.getAge() + "");
            //assertEquals(10, simpleBean.getAge());
            //assertEquals("Juan Dela Cruz", simpleBean.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testMarshall() {
        GwtReflect.magicClass(SimpleBean.class);

        GwtMarshaller marshaller = GWT.create(GwtMarshaller.class);

        SimpleBean simpleBean = new SimpleBean();
        Class<?> archetype = simpleBean.getClass();

        simpleBean.setAge(50);
        simpleBean.setName("Old Guy");
        simpleBean.setBalance(999.00);
        try {

            GwtReflect.fieldSet(SimpleBean.class, "objectId", simpleBean, (String) "123");
            GwtReflect.fieldSet(SimpleBean.class, "i0", simpleBean, (int) 123);
            GwtReflect.fieldSet(SimpleBean.class, "i1", simpleBean, (int) 456);
            GwtReflect.fieldSet(SimpleBean.class, "i2", simpleBean, (int) 789);
            GwtReflect.fieldSet(SimpleBean.class, "i4", simpleBean, (int) 0);
            GwtReflect.fieldSet(SimpleBean.class, "testbyte", simpleBean, (byte) 32);
            GwtReflect.fieldSet(SimpleBean.class, "longint", simpleBean, (long) 123456789);
            simpleBean.setTestchar('c');


        } catch (Exception e) {
        }


        //simple bean to parse objects using accessors and mutators

        ParseObject parseObject = marshaller.marshall(simpleBean);

        Window.alert(">>>>>>>>" + parseObject.toString());
        //get non null values from model
        //populate expectation list
        //assert test expectation to mutant
        Window.alert("BEGIN TEST-----------------MARSHALL");
        Set<?> s = parseObject.keySet();
        //iterate and persist keys from model
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

    public void testUnmarshaller() {
        GwtReflect.magicClass(SimpleBean.class);

        GwtUnmarshaller unmarshaller = GWT.create(GwtUnmarshaller.class);

        double testDOUBLE = 10600.50;
        ParseObject parseObject = new ParseObject();
        parseObject.putString("objectId", "123");
        parseObject.putString("name", "Old Man"); // String
        parseObject.putNumber("age", 40); // integer
        parseObject.putNumber("i1", 1);
        parseObject.putNumber("i2", 2);
        parseObject.putNumber("i3", 3);
        parseObject.putNumber("i0", 0);
        parseObject.putNumber("balance", testDOUBLE);
        parseObject.putNumber("testbyte", 123);

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
