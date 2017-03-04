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
 * {@link RttiMethod} implementation
 * @author SHadoW
 *
 */
abstract class RttiMethodImpl extends RttiAnnotableEntityImpl implements
		RttiMethod
{
	Class<?>[] paramTypes = null;

	@Override
	public Class<?>[] getParameterTypes()
	{
		if (paramTypes == null) paramTypes = getParameterTypesInternal();
		return paramTypes;
	}

	protected abstract Class<?>[] getParameterTypesInternal();

	@Override
	public abstract Class<?> getReturnType();

	@Override
	public Object invoke(Object instance, Object... params)
			throws RttiInvocationException, RttiIllegalArgumentException
	{
    if (params == null) params = new Object[0];
	
		if ((instance == null) || (params.length != getParameterTypes().length))
			throw new RttiIllegalArgumentException();
		
	
		return invokeInternal(instance, params);
	}
	
	protected abstract Object invokeInternal(Object instance, Object... params)
		throws RttiInvocationException, RttiIllegalArgumentException;
	
	@Override
	public String toString(RttiObject instance)
	{
		StringBuilder s = new StringBuilder(super.toString(instance));
		s.append("\n");
		s.append(getReturnType().getName()).append(' ');
		s.append(getName()).append('(');
		for (Class<?> param : getParameterTypes())
		{
			s.append(param.getName()).append(' ');
		}
		s.append(')');
		return s.toString();
	}
}
