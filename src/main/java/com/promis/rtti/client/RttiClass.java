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
 * Identifies class or interface type
 * @author SHadoW
 * 
 */
public interface RttiClass extends RttiAnnotableEntity
{
	boolean isEnum = false;

	/**
	 * Returns list of all defined fields (properties)
	 * fields of the given class or empty array if there are none.<br>
	 * See {@link RttiField} for explanation of <b>property</b> meaning.
	 * @return
	 */
	public RttiField[] getFields();
	
	/**
	 * Class.getClass() equivalent 
	 * @return
	 */
	public Class<?> getClazz();
	
	/**
	 * Returns <code>true</code> if this type represents a class
	 * @return
	 */
	public boolean isClass();
	
	/**
	 * Returns <code>true</code> if this type represents an interface
	 * @return
	 */
	public boolean isInterface();

	/**
	 * Returns <code>true</code> if this type represents an annotation
	 * @return
	 */
	public boolean isAnnotation();

	/**
	 * Returns <code>true</code> if this type represents a primitive type
	 * @return
	 */
	public boolean isPrimitive();
	
    /**
     * Returns true if and only if this class was declared as an enum in the
     * source code.
     *
     * @return true if and only if this class was declared as an enum in the
     *     source code
     */
    public boolean isEnum();
	
	/**
	 * Creates a new instance of the class represented by this <code>RttiClass</code> 
	 * object. The class is instantiated as if by a <b>new</b> expression with 
	 * an empty argument list. If the constructor isn't available <code>null</code>
	 * is returned.
	 * @return
	 */
	public Object newInstance();

	/**
	 * Assigns all visible fields from one object to another.
	 * Note that destination and source classes must share same
	 * superclass (whose fields will be assigned if it has RTTI generated). 
	 * Assignment of completely anonymous classes is not supported.
	 * @param dest 
	 * @param source source must be of type E (or descendant)
	 * @return <code>true</code> if the object was successfully assigned
	 */
	public boolean assign(Object dest, Object source);
	
	/**
	 * Compare all visible fields of one object to another.
	 * Note that destination and source classes must be the same.
	 * Object fields are compared either using <code>compare</code>
	 * if they implement {@link Comparable} interface or <code>equals</code>
	 * otherwise.
	 * Read-only fields are compared also.
	 * @param to 
	 * @param from from must be of type E
	 * @return <code>true</code> if the object are equal
	 */
	public boolean compare(Object to, Object from);
	
	/**
	 * Returns field with given name. If not found returns <code>null</code>.
	 * @param name
	 * @return
	 */
	public RttiField getField(final String name);
	
	/**
	 * Find field with given index. If not found returns <code>null</code>.
	 * @param index
	 * @return
	 */
	public RttiField findIndexedField(final int index);
	
	/**
	 * Returns list of all public methods or empty array if there
	 * are none. If method generation is turned off for this type 
	 * it returns <code>null</code>.
	 * @return
	 */
	public RttiMethod[] getMethods();
	
	/**
	 * Returns method with given name. If not found returns <code>null</code>.
	 * @param name
	 * @return
	 */
	public RttiMethod getMethod(final String name, Class<?>... paramTypes);
	
    /**
     * Returns the elements of this enum class or null if this
     * Class object does not represent an enum type.
	 * This is type-safe variant
     *
	 * @param clazz Possible return parameter (should be the same as 
     * @return an array containing the values comprising the enum class
     *     represented by this Class object in the order they're
     *     declared, or null if this Class object does not
     *     represent an enum type
     * @throws ClassCastException it enum is of different type
     */
    public <T> T[] getEnumConstants(Class<T> clazz);

    /**
     * Returns the elements of this enum class or null if this
     * Class object does not represent an enum type.
     *
	 * @return an array containing the values comprising the enum class
     *     represented by this Class object in the order they're
     *     declared, or null if this Class object does not
     *     represent an enum type
	 */
    public <T> T[] getEnumConstants();
}
