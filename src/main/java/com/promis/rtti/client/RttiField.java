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

import com.promis.rtti.annotations.Index;
import com.promis.rtti.annotations.ReadOnly;

/**
 * Identifies public non-static field of a class.
 * Getters and setters are not mandatory, use annotations to specify access.
 * If getters and setters are used they must have public access.
 * Setters on read-only fields are not checked (ie. if you add setter to 
 * read-only field it won't be possible to set it via RTTI but it will using code).<br>
 * <b>Properties</b> are either public fields (with or without getters/setters
 * which will take precedence in case they are there) or fields with other 
 * accessibility but with at least a getter (in which case they will be 
 * read-only) and optionally a setter. But annotations can be defined on the
 * field itself not getter or setter. Properties with getters and setters that 
 * read/write data from/to other class members must still be declared as <b>private</b>
 * fields with {@link SuppressWarnings SuppressWarning("unused")}. This way you can
 * specify annotations on those properties.
 * @author SHadoW
 *
 */
public interface RttiField extends RttiAnnotableEntity
{
	/**
	 * Returns field type
	 * @return
	 */
	public Class<?> getType();
	/**
	 * Returns field index specified by {@link Index} annotation
	 * or {@link RttiConst}.DEFAULT_INDEX if no index is specified.
	 * @return
	 */
	public int getIndex();
	/**
	 * Returns <code>true</code> if field can only be read, specified
	 * by {@link ReadOnly} annotation or by defining non-public field
	 * with no setter (but with getter) or by defining the field as final.
	 * @return
	 */
	public boolean isReadonly();

	/**
	 * Retrieves field value<br>
	 * <b>Use with caution as type of the result cannot be checked here</b>  
	 * @param <E> Possible return parameter (should be the same as 
	 * whatever {@link getType} return
	 * @param instance
	 * @return
	 */
	public <E> E get(Object instance);
	
	/**
	 * Retrieves field value<br>
	 * This is type-safe variant
	 * @param clazz Possible return parameter (should be the same as 
	 * whatever {@link getType} return
	 * @param instance
	 * @return
	 * @throws ClassCastException it field is of different type
	 */
	public <E> E get(Object instance, Class<E> clazz);
	
	/**
	 * Sets field value
	 * @param instance
	 * @param value
	 * @throws RttiReadOnlyException if the field is read-only
	 */
	public void set(Object instance, final Object value);
}
