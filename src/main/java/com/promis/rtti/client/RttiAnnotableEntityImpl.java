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

/**
 * {@link RttiAnnotableEntity} implementation
 * @author SHadoW
 *
 */
abstract class RttiAnnotableEntityImpl extends RttiEntityImpl 
implements RttiAnnotableEntity
{
	private Annotation[] annots = null;
	
	@Override
	public Annotation[] getAnnotations()
	{
		if (annots == null) annots = getAnnotationsInternal();
		return annots;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <E extends Annotation> E getAnnotation(Class<E> annotationClass)
	{
		final String name = annotationClass.getName();
		for (Annotation annot : getAnnotations())
		{
			if (annot.annotationType().getName().equals(name)) return (E)annot;
		}
		return null;
	}
	
	protected abstract Annotation[] getAnnotationsInternal();
	
	@Override
	public String toString(RttiObject instance)
	{
		getAnnotations();
		StringBuilder s = new StringBuilder();
		if ((annots != null) && (annots.length > 0))
		{
			s.append("\nAnnotations: (");
			boolean first = true;
			for (Annotation annot : annots)
			{
				RttiClass clazz = Rtti.getTypeInfo(annot.annotationType());
				if (clazz != null) s.append(clazz.toString(instance));
				else s.append(annot.annotationType().getName());
				if (! first) s.append(", ");
				first = false;
			}
			s.append(')');
		}
		return super.toString(instance) + s.toString();
	}
}
