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

/**
 * Identifies class or interface method
 * @author SHadoW
 *
 */
public interface RttiMethod extends RttiAnnotableEntity
{
	/**
	 * Returns an array of {@link Class} objects that represent the formal 
	 * parameter types, in declaration order, of the method represented by this 
	 * <code>RttiMethod</code> object. Returns an array of length 0 if the 
	 * underlying method takes no parameters. 
	 * @return
	 */
	public Class<?>[] getParameterTypes();
	
	/**
	 * Returns a {@link Class} object that represents the formal return 
	 * type of the method represented by this <code>RttiMethod</code> object. 
	 * @return
	 */
	public Class<?> getReturnType();
	
	/**
	 * Invokes the method represented by this <code>RttiMethod</code> object.
	 * @param instance instance on which the execution will be done
	 * @param params list of function parameters
	 * @return any returning value from the method or <code>null</code>
	 * if the method doesn't return a value
	 * @throws RttiInvocationException when there is an error during invocation
	 * @throws RttiIllegalArgumentException when invalid <code>instance</code> 
	 * is passed or if argument list is incorrect
	 */
	public Object invoke(Object instance, Object... params)
			throws RttiInvocationException, RttiIllegalArgumentException;
}
