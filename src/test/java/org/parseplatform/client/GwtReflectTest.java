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

import com.google.gwt.core.client.GWT;
import com.google.gwt.json.client.*;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.reflect.shared.GwtReflect;
import com.google.gwt.user.client.Window;
import elemental.client.Browser;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Unit tests of {@link Where}
 *
 * @author Kerby Martino
 * @since 0-SNAPSHOT
 * @version 0-SNAPSHOT
 *
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
            Object[]  fields = GwtReflect.getPublicFields(declaringClass);
            Object[]  methods = GwtReflect.getPublicMethods(declaringClass);





            // check fields
            for (int c = 0; c < methods.length ; c ++) {

                methodnames.add(methods[c].toString().substring(methods[c].toString().lastIndexOf('.')+1));
            }
            for (int c = 0; c < methodnames.size() ; c ++) {
                Window.alert("method name :" + methodnames.get(c));

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
        simpleBean.setAge(50);
        simpleBean.setName("Old Guy");
        simpleBean.setBalance(999.00);
        try {

            GwtReflect.fieldSet(SimpleBean.class, "objectId", simpleBean, "object");
            simpleBean.setI0(0);
            simpleBean.setI1(1);
            simpleBean.setI2(2);
            simpleBean.setI3(3);
            simpleBean.setI4(4);
            simpleBean.setI5(5);


        }
        catch (Exception e){}
        //simple bean to parse objects using accessors and mutators

        ParseObject parseObject = marshaller.marshall(simpleBean);
        Window.alert(">>>>>>>>" + parseObject.toString());
        //assertEquals(50, parseObject.getLong("age").intValue());
        //assertEquals("Old Guy", parseObject.getString("name"));
    }

    public void testUnmarshaller() {
        GwtReflect.magicClass(SimpleBean.class);
        GwtUnmarshaller unmarshaller = GWT.create(GwtUnmarshaller.class);

        ParseObject parseObject = new ParseObject();
        parseObject.putString("name", "Old Man"); // String
        parseObject.putNumber("age", 40); // integer
        parseObject.putNumber("balance", 10600.50); // double

        SimpleBean simpleBean = new SimpleBean();
        simpleBean = unmarshaller.unmarshall(SimpleBean.class, simpleBean, parseObject);
        //get field names, get method names
        assertEquals(40, simpleBean.getAge());
        assertEquals("Old Man", simpleBean.getName());

    }

    public static void log(String s){
        System.out.println(s);
    }

}
