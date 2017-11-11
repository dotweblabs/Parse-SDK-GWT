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

import com.google.gwt.json.client.JSONBoolean;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.reflect.shared.GwtReflect;
import com.google.gwt.user.client.Window;
import elemental.client.Browser;

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
            GwtReflect.fieldSet(SimpleBean.class, "age", simpleBean, 10);
            GwtReflect.fieldSet(SimpleBean.class, "name", simpleBean, "Juan Dela Cruz");
//            Browser.getWindow().getConsole().log(simpleBean.getName());
//            Browser.getWindow().getConsole().log(simpleBean.getAge());
            Window.alert(simpleBean.getName());
            Window.alert(simpleBean.getAge() + "");
            assertEquals(10, simpleBean.getAge());
            assertEquals("Juan Dela Cruz", simpleBean.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void log(String s){
        System.out.println(s);
    }

}
