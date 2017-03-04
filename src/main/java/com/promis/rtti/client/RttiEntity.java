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
 * Base interface for any RTTI-capable element (class, field, ...)
 * @author SHadoW
 *
 */
interface RttiEntity
{
	/**
	 * Name of the entity (full class name, field name).
	 * @return
	 */
	public String getName();
	
	/**
	 * Returns textual representation of the object optionally with field 
	 * data of given object instance. 
	 * @param instance object that will be used for get/set operations or 
	 * <code>null</code> if the instance info is not necessary
	 * @return
	 */
	public String toString(RttiObject instance);
}
