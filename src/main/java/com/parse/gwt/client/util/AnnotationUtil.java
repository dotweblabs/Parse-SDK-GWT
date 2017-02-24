/**
 *
 * Copyright (c) 2016 Dotweblabs Web Technologies and others. All rights reserved.
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
package com.parse.gwt.client.util;

import com.promis.rtti.client.Rtti;
import com.promis.rtti.client.RttiClass;
import com.promis.rtti.client.RttiField;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class AnnotationUtil {

    public static class AnnotatedField  {
        private Class<?> clazz;
        private Class<? extends Annotation> type;
        private RttiField field;
        private Object obj;

        private String fieldName;
        private Object fieldValue;

        public AnnotatedField(){};
        public AnnotatedField(Class<? extends Annotation> type, Object obj, RttiField field){
            this.type = type;
            this.field = field;
            this.obj = obj;
            this.clazz = obj.getClass();
            try {
                boolean isAccessible = field.isReadonly();
                //field.setAccessible(true);
                this.fieldName = field.getName();
                this.fieldValue = field.get(obj);
            } catch (Exception ex){

            }
        };

        public Class<?> getFieldType(){
            return field.getType();
        }

        public String getFieldName() {;
            return fieldName;
        }

        public Object getFieldValue(){
            return fieldValue;
        }

        public void setFieldValue(Object newValue) {
            //boolean isAccessible = field.isAccessible(); // Do I need this?
            boolean isAccessible = field.isReadonly();
            //field.setAccessible(true);
            Class fieldType = field.getType();
            try {
                Class newType = newValue.getClass().getClass();
                field.set(obj, newValue);
                fieldValue = newValue; // update
                //field.setAccessible(isAccessible);
            } catch (Exception ex){
                ex.printStackTrace(); // TODO
            }
        }

        public RttiField getField(){
            return this.field;
        }

        public Annotation annotation(){
            return field.getAnnotation(type);
        }
    }

    public static AnnotatedField getFieldWithAnnotation(Class<? extends Annotation> clazz, Object instance){
        RttiClass info = Rtti.getTypeInfo(instance);
        RttiField[] fields = info.getFields();
        for(RttiField rttiField : Arrays.asList(fields)) {
            if(rttiField.getAnnotation(clazz) != null) {
                return new AnnotatedField(clazz, instance, rttiField);
            }
        }
        return null;
    }

    /**
     * Returns the field name(s) and the value(s)
     * @param clazz annotation type
     * @param instance object to check
     * @return
     */
    public static List<AnnotatedField> getFieldsWithAnnotation(Class<? extends Annotation> clazz, Object instance){
        List<AnnotatedField> fields = null;
        RttiClass info = Rtti.getTypeInfo(instance);
        RttiField[] fieldArray = info.getFields();
        for(RttiField rttiField : Arrays.asList(fieldArray)) {
            // Keep in mind the original field accessible state
            boolean isAccessible = rttiField.isReadonly(); // Do I need this?
//            field.setAccessible(true); // TODO
            try {
                if(rttiField.getAnnotation(clazz) != null) {
                    if (fields==null){
                        fields = new LinkedList<AnnotatedField>();
                    }
                    fields.add(new AnnotatedField(clazz, instance, rttiField));
                }
//                field.setAccessible(isAccessible);
            } catch (Exception e){

            }
        }
        return fields;
    }

    public static List<Annotation> getAnnotations(Object instance){
        RttiClass info = Rtti.getTypeInfo(instance);
        List<Annotation> annotations = new LinkedList<>();
        if(info != null) {
            return new ArrayList<Annotation>(Arrays.asList(info.getAnnotations()));
        } else {
            return annotations;
        }
    }

    public static Annotation getClassAnnotation(Class<? extends Annotation> clazz, Object instance){
        List<Annotation> list = getAnnotations(instance);
        for (Annotation a : list){
            if (a.annotationType().equals(clazz)){
                return a;
            }
        }
        return null;
    }

    public static boolean isClassAnnotated(Class<? extends Annotation> clazz, Object instance){
        RttiClass info = Rtti.getTypeInfo(instance);
        return info.getAnnotation(clazz) != null;
    }

}