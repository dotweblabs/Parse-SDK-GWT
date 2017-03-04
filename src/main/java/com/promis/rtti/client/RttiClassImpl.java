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
 * {@link RttiClass} implementation
 * @author SHadoW
 *
 * @param <E> Type for which it is meant for
 */
abstract class RttiClassImpl<E> extends RttiAnnotableEntityImpl
implements RttiClass
{
	private RttiField[] fields = null;
	private RttiMethod[] methods = null;

	@Override
	public RttiField[] getFields()
	{
		if (fields == null) fields = getFieldsInternal();
		return fields;
	}
	
	protected abstract RttiField[] getFieldsInternal();
	
	@Override
	public abstract Class<?> getClazz();
	
	@Override
	public abstract Object newInstance();
	
	@Override
	public abstract boolean isClass();
	
	@Override
	public abstract boolean isInterface();

	@Override
	public abstract boolean isAnnotation();
	
	@Override
	public abstract boolean isPrimitive();

	@Override
	public abstract boolean assign(Object dest, Object source);

	@Override
	public abstract boolean compare(Object to, Object from);
	
	@Override
	public String toString(RttiObject instance)
	{
		StringBuilder s = new StringBuilder(" ");
		for (RttiField prop : getFields())
		{
			s.append("\n\t").append(prop.toString(instance));
		}
		return super.toString(instance) + s.toString();
	}
	
	@Override
	public RttiField getField(final String name)
	{
		if ((name == null) || (name.isEmpty())) return null;
		
		for (RttiField prop : getFields())
		{
			if (prop.getName().equals(name)) return prop;
		}
		return null;
	}
	
	@Override
	public RttiField findIndexedField(final int index)
	{
		if (index == RttiConst.DEFAULT_INDEX) return null;
		for (RttiField prop : getFields())
		{
			if (prop.getIndex() == index) return prop;
		}
		return null;
	}
	
	protected abstract RttiMethod[] getMethodsInternal();
	
	@Override
	public RttiMethod[] getMethods()
	{
		if (methods == null) methods = getMethodsInternal();
		return methods;
	}
	
	@Override
	public RttiMethod getMethod(final String name, Class<?>... paramTypes)
	{
    if (paramTypes == null) paramTypes = new Class<?>[0];
		getMethods();
		if (methods == null) return null;
		first:
		for (RttiMethod method : methods)
		{
			if ((method.getName().equals(name)) && 
					(method.getParameterTypes().length == paramTypes.length)) 
			{
				Class<?>[] params = method.getParameterTypes();
				for (int i = 0; i < paramTypes.length; i++)
				{
					if (paramTypes[i] != params[i]) continue first;
				}
				return method;
			}
		}
		return null;
	}
	
	private Object[] enumConstants = null;
	
	@SuppressWarnings("unchecked")
	@Override
	public <T extends Object> T[] getEnumConstants()
	{
		if (enumConstants == null) enumConstants = getEnumConstantsInternal();
		if (enumConstants == null) return null;
		return (T[])enumConstants;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T[] getEnumConstants(Class<T> clazz)
	{
		if (enumConstants == null) enumConstants = getEnumConstantsInternal();
		if (enumConstants == null) return null;
		if (enumConstants.length == 0) return (T[])enumConstants;
		
		if (isInstance(enumConstants[0], clazz)) return (T[])enumConstants;
		
		throw new ClassCastException();
	}
	
	protected abstract Object[] getEnumConstantsInternal();
}
