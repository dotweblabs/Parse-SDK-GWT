/*
 * The contents of this file are subject to the Mozilla Public License
 * Version 1.1 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * The Original Code is GWT RTTI library.
 *
 * The Initial Developer of the Original Code is
 * Jan "SHadoW" Rames <ramejan@gmail.com>.
 * Portions created by the Initial Developer are Copyright (C) 2011
 * the Initial Developer. All Rights Reserved.
 */
package com.promis.rtti.client;

import java.lang.annotation.Annotation;
import java.lang.annotation.RetentionPolicy;

import com.promis.rtti.annotations.GenerateRtti;

/**
 * Base class for any RTTI entity that can be annotated
 * (classes, fields, annotations, interfaces, ...).<br>
 * Note: by default annotations will be saved on fields
 * and classes/interfaces but RTTI info won't be generated for them
 * unless you specify {@link GenerateRtti} on them. To obtain annotation
 * type info use 
 * <code>Rtti.getTypeInfo(annotation.annotationType())</code>.<br>  
 * Also {@link RetentionPolicy}.RUNTIME must be set on any
 * annotations you want to use in client code.<br>
 * Note: that <b>only</b> Strings and primitive types are supported as 
 * return type of {@link Annotation} parameter. 
 * @author SHadoW
 *
 */
interface RttiAnnotableEntity extends RttiEntity
{
	/**
	 * Returns any annotations specified on the field or empty array
	 * if there are none (note that RTTI and JAVA specific annotations 
	 * are striped away. 
	 * @return
	 */
	public Annotation[] getAnnotations();
	
	/**
	 * Returns specified annotation instance or null
	 * @param <E> Type of the instance to search for
	 * @param annotationClass
	 * @return
	 */
	public <E extends Annotation> E getAnnotation(Class<E> annotationClass);
}
