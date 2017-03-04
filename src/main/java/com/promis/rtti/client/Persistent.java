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
 * Just a rename of {@link RttiObject} identifying an object
 * capable of serialization, assigning and comparing. 
 * @author SHadoW
 *
 */
public abstract class Persistent extends RttiObject implements Assignable, Comparable
{

	@Override
	public void assign(Object source)
	{
		if (!(source instanceof Persistent))
			throw new UnableToAssignException(source, this);
		((Persistent)source).assignTo(this);
	}

	/**
	 * Assign this to destination
	 * @param destination
	 * @throws UnableToAssignException if assignment is not possible
	 */
	protected void assignTo(Object destination)
	{
		if (destination != null)
		{
			RttiClass info = Rtti.getTypeInfo(this);
			if (info != null)
			{
				if (info.assign(destination, this)) return;
			}
		}
		
		throw new UnableToAssignException(this, destination);
	}
	
	@Override
	public boolean compare(Object to)
	{
		RttiClass info = Rtti.getTypeInfo(this);
		if (info != null)
		{
			return info.compare(to, this);
		}
		return false;
	}

	@Override
	public boolean equals(Object obj)
	{
		return compare(obj);
	}
}
