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

import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.junit.client.GWTTestCase;

/**
 *
 * Unit tests of {@link Order}
 *
 * @author Kerby Martino
 * @since 0-SNAPSHOT
 * @version 0-SNAPSHOT
 *
 */
public class MapperTest extends GWTTestCase {


    @Override
    public String getModuleName() {
        return "org.parseplatform.Parse";
    }

    public void testMapper() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", new JSONNumber(10.0));
        jsonObject.put("balance", new JSONNumber(100.5));
        jsonObject.put("name", new JSONString("Sample name"));

        SimpleBean mappedBean = new SimpleBean_mapper().map(jsonObject);
        assertEquals(10.0, mappedBean.getId());
        assertEquals(100.5, mappedBean.getBalance());
        assertEquals("Sample name", mappedBean.getName());
    }

    public void testParseMapperMap() {
        JSONObject testObject = new JSONObject();
        testObject.put("ainteger", new JSONNumber(1));
        testObject.put("aInteger", new JSONNumber(2));
        testObject.put("adouble", new JSONNumber(1.0));
        testObject.put("aDouble", new JSONNumber(2.0));
        System.out.println(testObject.toString());
        //ParseBean parseBean = new ParseBean_mapper().map(testObject);
        //assertEquals(1, parseBean.getaInteger().intValue());
        //assertEquals(2, parseBean.getAinteger());
        //assertEquals(1.0, parseBean.getAdouble());
        //assertEquals(2.0, parseBean.getaDouble());
    }

    public void testParseMapperUnmap() {
        JSONObject testObject = new JSONObject();
        testObject.put("ainteger", new JSONNumber(1));
        testObject.put("aInteger", new JSONNumber(2));
        testObject.put("adouble", new JSONNumber(1.0));
        testObject.put("aDouble", new JSONNumber(2.0));

        ParseBean parseBean = new ParseBean_mapper().map(testObject);

        assertEquals(1, parseBean.getaInteger().intValue());
        assertEquals(2, parseBean.getAinteger());
        assertEquals(1.0, parseBean.getAdouble());
        assertEquals(2.0, parseBean.getaDouble());

        //ParseObject parseObject = new ParseBean_mapper().unmap(parseBean);

        //assertEquals(1, parseObject.getDouble("ainteger").intValue());
        //assertEquals(2, parseObject.getDouble("aInteger").intValue());
        //assertEquals(1.0, parseObject.getDouble("adouble").intValue());
        //assertEquals(2.0, parseObject.getDouble("aDouble").intValue());

    }

    public static void log(String s){
        System.out.println(s);
    }

}
