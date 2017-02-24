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
 * {@link RttiField} implementation
 * @author SHadoW
 *
 */
abstract class RttiFieldImpl extends RttiAnnotableEntityImpl
implements RttiField
{
	@Override
	public abstract Class<?> getType();
	@Override
	public abstract int getIndex();
	@Override
	public abstract boolean isReadonly();
	
	protected abstract Object getInternal(Object instance);
	@Override
	public abstract void set(Object instance, final Object value);
	
	@SuppressWarnings("unchecked")
	@Override
	public <E> E get(Object instance)
	{
		return (E)getInternal(instance);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <E> E get(Object instance, Class<E> clazz)
	{
		Object result = getInternal(instance);
		if (! isInstance(result, clazz))	//Class.isInstance is not supported by GWT
		{
			//Allow conversion of some primitive types
			if (clazz.isPrimitive())
			{
				if ((clazz == boolean.class) && (result instanceof Boolean))
					return (E)result; 
				if ((clazz == int.class) && (result instanceof Integer))
					return (E)result;
				if ((clazz == char.class) && (result instanceof Character))
					return (E)result;
				if ((clazz == byte.class) && (result instanceof Byte))
					return (E)result;
				if ((clazz == short.class) && (result instanceof Short))
					return (E)result;
				if ((clazz == long.class) && (result instanceof Long))
					return (E)result;
				if ((clazz == float.class) && (result instanceof Float))
					return (E)result;
				if ((clazz == double.class) && (result instanceof Double))
					return (E)result;
			}
			throw new ClassCastException();
		}
		return (E)result;
	}
	
	@Override
	public String toString(RttiObject instance)
	{
		String s = super.toString(instance) + 
		" Type: " + getType().getName() +
		" Index: " + getIndex() + 
		" ReadOnly: " + isReadonly();
		if (instance == null) return s;
		else return s + " Value: " + getInternal(instance).toString();
	}
}
