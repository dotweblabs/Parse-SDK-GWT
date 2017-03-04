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
package com.promis.rtti.generator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.google.gwt.core.ext.TreeLogger.Type;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JConstructor;
import com.google.gwt.core.ext.typeinfo.JEnumConstant;
import com.google.gwt.core.ext.typeinfo.JField;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.JParameter;
import com.google.gwt.core.ext.typeinfo.JPrimitiveType;
import com.google.gwt.core.ext.typeinfo.JType;
import com.google.gwt.core.ext.typeinfo.NotFoundException;
import com.promis.generators.BaseGenerator;
import com.promis.generators.BaseGeneratorClient;
import com.promis.rtti.annotations.GenerateMethods;
import com.promis.rtti.annotations.Index;
import com.promis.rtti.annotations.NoAssign;
import com.promis.rtti.annotations.ReadOnly;
import com.promis.rtti.client.annotations.NoCompare;

public class RttiClassGenerator extends BaseGenerator
{
	@Override
	protected BaseGeneratorClient getClient()
	{
		return new Client();
	}
	
	static class Client extends BaseGeneratorClient
	{
		static JClassType jString = null;
		static JClassType jInteger = null;
		static JClassType jDouble = null;
		static JClassType jBoolean = null;
		
		@Override
		protected void doGenerate()
		{
			//Refresh each time so isAssignableFrom works all the time while
			//debugging (ClassOracle probably gets recreated and returns new
			//instance for String)
			/*if (jString == null) */
			jString = oracle.findType("java.lang.String");
			jInteger = oracle.findType("java.lang.Integer");
			jDouble = oracle.findType("java.lang.Double");
			jBoolean = oracle.findType("java.lang.Boolean");
			//The type parameter is the one we are actually interested in
			JClassType overlayType = extractInterfaceGenericParams(this.interfaceType)[0];
			logger.log(Type.INFO, "Generating RTTI for " + overlayType.getQualifiedBinaryName());
			Class<?> clazz;
			try
			{
				clazz = Class.forName(overlayType.getQualifiedBinaryName());
			} 
			catch (ClassNotFoundException e)
			{
				clazz = null;
			}
			
			writeBlockIntro("public String getName()");
			writeReturn(STR, overlayType.getQualifiedBinaryName());
			writeBlockOutro();
			
			writeBlockIntro("public Class<?> getClazz()");
			writeReturn(overlayType.getQualifiedSourceName() + ".class");
			writeBlockOutro();
			
			writeBlockIntro("public boolean isClass()");
			writeReturn(overlayType.isClass() != null);
			writeBlockOutro();
			
			writeBlockIntro("public boolean isInterface()");
			writeReturn(overlayType.isInterface() != null);
			writeBlockOutro();
			
			writeBlockIntro("public boolean isAnnotation()");
			writeReturn(overlayType.isAnnotation() != null);
			writeBlockOutro();
			
			writeBlockIntro("public boolean isEnum()");
			writeReturn(overlayType.isEnum() != null);
			writeBlockOutro();
			
			writeBlockIntro("public boolean isPrimitive()");
			writeReturn(isPrimitiveClass(overlayType));
			writeBlockOutro();
			
			writeBlockIntro("public Object newInstance()");
			try
			{
				if (overlayType.isAbstract()) throw new NotFoundException(); //Cannot instantiate abstract type
				JConstructor constr = overlayType.getConstructor(new JType[0]);
				if (! constr.isPublic()) throw new NotFoundException();
				assert constr != null;
				writeReturn(NEW, overlayType.getQualifiedSourceName());
			}
			catch (NotFoundException e)
			{
				writeReturn(NULL);
			}
			writeBlockOutro();
			
			writeBlockIntro("protected RttiField[] getFieldsInternal()");
			writeFields(overlayType, clazz);
			writeBlockOutro();
			
			if (clazz != null) writeAnnotations(clazz.getAnnotations());
			else writeAnnotations(null);
			
			writeBlockIntro("public boolean assign(Object dest, Object source)");
			if ((overlayType.isClass() != null) &&
					(! overlayType.isAnnotationPresent(NoAssign.class)) &&
					(! isNativeClass(overlayType)))
			{
				write("if (dest == source) return %s;", TRUE);
				write("assert source instanceof %s;", 
						overlayType.getQualifiedSourceName());
				writeBlockIntro("if (dest instanceof %s)", 
						overlayType.getQualifiedSourceName());
				//Prepare correct type variables
				write("%1$s s = (%1$s)source;",
						overlayType.getQualifiedSourceName());
				write("%1$s d = (%1$s)dest;",
						overlayType.getQualifiedSourceName());
				writeAssign(overlayType);
				writeReturn(TRUE);
				writeBlockOutro();
				writeBlockIntro("else");
				//Try to assign superclass
				write("RttiClass info = Rtti.getTypeInfo(%s.class);",
						overlayType.getSuperclass().getQualifiedSourceName());
				write("if (info == null) return %s;", FALSE);
				writeReturn("info.assign(dest, source)");
				writeBlockOutro();
			}
			else writeReturn(FALSE);
			writeBlockOutro();
			
			writeBlockIntro("public boolean compare(Object to, Object from)");
			if ((overlayType.isClass() != null) && 
					(! overlayType.isAnnotationPresent(NoCompare.class)) &&
					(! isNativeClass(overlayType)))
			{
				write("if (to == from) return %s;", TRUE);
				write("assert from instanceof %s;", 
						overlayType.getQualifiedSourceName());
				write("if (from.getClass() != to.getClass()) return %s;", FALSE); 
				//Prepare correct type variables
				write("%1$s f = (%1$s)from;",
						overlayType.getQualifiedSourceName());
				write("%1$s t = (%1$s)to;",
						overlayType.getQualifiedSourceName());
				writeCompare(overlayType);
				writeReturn(TRUE);
			}
			else writeReturn(FALSE);
			writeBlockOutro();
			
			writeBlockIntro("protected RttiMethod[] getMethodsInternal()");
			if (overlayType.isAnnotationPresent(GenerateMethods.class)) 
				writeMethods(overlayType, clazz);
			else writeReturn(NULL);
			writeBlockOutro();
			
			writeBlockIntro("protected Object[] getEnumConstantsInternal()");
			if (overlayType.isEnum() != null)
			{
				JEnumConstant[] consts = 
					overlayType.isEnum().getEnumConstants();
				
				writeBlockIntro("final %s[] result = ", 
						overlayType.getQualifiedSourceName());
				int i = 0;
				for (JEnumConstant elem : consts)
				{
					write("%s.%s%s", 
							overlayType.getQualifiedSourceName(),
							elem.getName(), 
							(i != (consts.length - 1)) ? "," : "");
					i++;
				}
				writeBlockOutro(true);
				
				writeReturn("result");
			}
			else writeReturn(NULL);
			writeBlockOutro();
		}
		
		protected JMethod findGetSetter(JClassType overlayType, JField field, 
				String prefix, JType[] param)
		{
			//First try with capital letter
			String s = prefix + prepGetSetter(field.getName());
			JMethod method = overlayType.findMethod(s, param);
			//If not found use original letter
			if (method == null)
			{
				s = prefix + field.getName();
				method = overlayType.findMethod(s, param);
			}
			
			return method;
		}
		
		/**
		 * Returns getter name if field has getter or null if not 
		 * @param field
		 * @return
		 */
		protected String hasGetter(JClassType overlayType, JField field)
		{
			JType[] param = {};
			JMethod method = findGetSetter(overlayType, field, "get", param);
			if ((method == null) && 
					(field.getType().getQualifiedSourceName().
							equals("java.lang.Boolean") ||
					field.getType().getQualifiedSourceName().
							equals("boolean")))
			{
				//Booleans may use "is" prefix
				method  = findGetSetter(overlayType, field, "is", param);
			}
			if ((method != null) && method.isPublic() && 
					method.getReturnType().equals(field.getType()))
				return method.getName();
			else return null;
		}

		protected String hasSetter(JClassType overlayType, JField field)
		{
			JType[] param = {null};
			param[0] = field.getType();
			JMethod method = findGetSetter(overlayType, field, "set", param);
			if ((method != null) && method.isPublic()) return method.getName();
			else return null;
		}
		
		protected boolean isPublished(JClassType overlayType, JField field)
		{
			//We are only interested in instance and accessible simple fields
			return (! field.isStatic()) && (field.getType().isArray() == null) && 
				(field.isPublic() || (hasGetter(overlayType, field) != null));
		}
		
		protected String getTypeName(JType type)
		{
			JClassType aClass = type.isClassOrInterface();
			if (aClass != null) return aClass.getQualifiedSourceName();
			
			JPrimitiveType primitive = type.isPrimitive();
			if (primitive != null) return primitive.getQualifiedBoxedSourceName();
			
			return null;		
		}
		
		protected boolean isSettableType(JType type)
		{
			if (type.isPrimitive() != null) return true;
			
			JClassType aObject = type.isClass();
			if (aObject == null) return false;
			
			if ((aObject.isAssignableTo(jString)) 
        || (aObject.isAssignableTo(jInteger))
        || (aObject.isAssignableTo(jDouble))
        || (aObject.isAssignableTo(jBoolean)))
				return true;
			
			return false;
		}
		
		protected boolean isSettable(JClassType overlayType, JField field)
		{
			return /*isSettableType(field.getType()) &&*/ //All types should be settable
				(field.isPublic() || (hasSetter(overlayType, field) != null));
		}
		
		protected boolean isReadOnly(JClassType overlayType, JField field)
		{
			ReadOnly readonly = field.getAnnotation(ReadOnly.class);
			
			return ((readonly != null) || (field.isFinal()) ||
					((! field.isPublic()) && (hasSetter(overlayType, field) == null)));
		}
		
		private String prepGetSetter(String s)
		{
			return s.substring(0, 1).toUpperCase() + s.substring(1);
		}
		
		protected void writeFields(JClassType baseOverlayType, Class<?> baseClazz)
		{		
			//Walk through class structure and obtain all visible fields
			int i = 0;
			JClassType overlayType = baseOverlayType;
			
			while (overlayType != null)
			{
				JField[] fields = overlayType.getFields();
				if (fields == null)
				{
					writeReturn("new RttiField[0]");
					return;
				}
				
				for (JField field : fields)
				{
					if (! isPublished(overlayType, field)) continue;
					i++;
				}
				
				overlayType = overlayType.getSuperclass();
			}
			
			if (i == 0)
			{
				writeReturn("new RttiField[0]");
				return;
			}
			
			write("RttiField[] result = new RttiField[%d];", i);			
			i = 0;
			overlayType = baseOverlayType;
			Class<?> clazz = baseClazz;
			while (overlayType != null)
			{
				JField[] fields = overlayType.getFields();
				for (JField field : fields)
				{
					if (! isPublished(overlayType, field)) continue;
					
					writeField(overlayType, clazz, field, i);
					
					i++;
				}
				overlayType = overlayType.getSuperclass();
				if (clazz != null) clazz = clazz.getSuperclass();
			}
			
			writeReturn("result");
		}
		
		protected void writeField(JClassType overlayType, Class<?> clazz, 
				JField field, int arrayIndex)
		{
			writeBlockIntro("result[%d] = new RttiFieldImpl()", arrayIndex);
			
			writeBlockIntro("public String getName()");
			writeReturn(STR, field.getName());
			writeBlockOutro();
			
			if (clazz == null) 
				writeAnnotations(null);
			else try
			{
				Field f = clazz.getDeclaredField(field.getName());
				if (f != null) writeAnnotations(f.getAnnotations());
				else writeAnnotations(null);
			} 
			catch (SecurityException e)
			{
				writeAnnotations(null);
			} 
			catch (NoSuchFieldException e)
			{
				writeAnnotations(null);
			}
							
			writeBlockIntro("public Class<?> getType()");
			writeReturn(getTypeName(field.getType()) + ".class");
			writeBlockOutro();
			
			writeBlockIntro("public int getIndex()");
			Index index = field.getAnnotation(Index.class);
			if ((index == null) || (index.value().length == 0)) 
				writeReturn("RttiConst.DEFAULT_INDEX");
			else writeReturn(index.value()[0]);
			writeBlockOutro();
			
			writeBlockIntro("public boolean isReadonly()");
			boolean readonly = isReadOnly(overlayType, field); 
			writeReturn(readonly);
			writeBlockOutro();
			
			String typeCast = "(" + overlayType.getQualifiedSourceName() + ")";

			writeBlockIntro("protected Object getInternal(Object instance)");
			{
				String getter = hasGetter(overlayType, field);
				if (getter != null)
					writeReturn("(%sinstance).%s()", typeCast, getter);
				else writeReturn("(%sinstance).%s", typeCast, field.getName());
			}
			writeBlockOutro();
			
			writeBlockIntro("public void set(Object instance, final Object value)");
			if (readonly || (! isSettable(overlayType, field))) 
				writeThrow("RttiReadOnlyException(getName())");
			else 
			{
				String setter = hasSetter(overlayType, field);
				if (setter != null)
					write("(%sinstance).%s((%s)value);", typeCast, setter,
						getTypeName(field.getType()));
				else write("(%sinstance).%s = (%s)value;", typeCast, field.getName(),
					getTypeName(field.getType())); 
			}
			writeBlockOutro();
			
			writeBlockOutro(true);
		}
		
		private void writeAnnotations(Annotation[] annots)
		{
			writeBlockIntro("protected Annotation[] getAnnotationsInternal()");
			if (annots == null)
			{
				writeReturn("new Annotation[0]");
			}
			else writeAnnotationsInternal(annots);
			writeBlockOutro();
		}
		
		static final String rttiAnnotPackage = "com.promis.rtti.annotations";
		static final String javaAnnotPackage = "java.lang.annotation";
		
		protected boolean isRttiAnnotation(Class<? extends Annotation> annotation)
		{
			if (annotation.getPackage().getName().equals(rttiAnnotPackage))
				return false;
			if (annotation.getPackage().getName().equals(javaAnnotPackage))
				return false;
			return true;
		}
		
		protected boolean isDefaultObjectMethod(Method method)
		{
			final String name = method.getName();
			if (name.equals("hashCode")) return true;
			if (name.equals("toString")) return true;
			if (name.equals("equals")) return true;
			if (name.equals("annotationType")) return true;
				
			return false;
		}
		
		protected boolean isDefaultAnnotationMethod(Method method)
		{
			final String name = method.getName();
			if (isDefaultObjectMethod(method)) return true;
			if (name.equals("annotationType")) return true;
				
			return false;
		}
		
		protected boolean isPrimitiveClass(JClassType clazz)
		{
			try
			{
				return isPrimitiveClass(Class.forName(clazz.getQualifiedBinaryName()));
			}
			catch (ClassNotFoundException e)
			{
				return false;
			}
		}
		
		protected boolean isPrimitiveClass(Class<?> clazz)
		{
			if (clazz == Boolean.class) return true;
			if (clazz == Character.class) return true;
			if (clazz == Byte.class) return true;
			if (clazz == Short.class) return true;
			if (clazz == Integer.class) return true;
			if (clazz == Long.class) return true;
			if (clazz == Float.class) return true;
			if (clazz == Double.class) return true;
			if (clazz == Void.class) return true;
			
			return false;
		}
		
		/**
		 * Native + string
		 * @param clazz
		 * @return
		 */
		protected boolean isNativeClass(JClassType clazz)
		{
			try
			{
				return isNativeClass(Class.forName(clazz.getQualifiedSourceName()));
			}
			catch (ClassNotFoundException e)
			{
				return false;
			}
		}
		
		/**
		 * Native + string
		 * @param clazz
		 * @return
		 */
		protected boolean isNativeClass(Class<?> clazz)
		{
			if (isPrimitiveClass(clazz)) return true;
			if (clazz == String.class) return true;
			return false;
		}
		
		protected void writeAnnotationsInternal(Annotation[] annots)
		{
			int i = 0;
			
			for (Annotation annot : annots)
			{
				if (! isRttiAnnotation(annot.annotationType())) continue;
				i++;
			}
			
			if (i == 0)
			{
				writeReturn("new Annotation[0]");
				return;
			}
			
			write("Annotation[] result = new Annotation[%d];", i);
			
			i = 0;
			for (Annotation annot : annots)
			{
				Class<? extends Annotation> annotation = annot.annotationType();
				if (! isRttiAnnotation(annotation)) continue;
				
				writeBlockIntro("result[%d] = new %s()", i, annotation.getName());
				
				writeBlockIntro("public Class<? extends Annotation> annotationType()");
				writeReturn(annotation.getName() + ".class");
				writeBlockOutro();
				for (Method method : annotation.getMethods())
				{
					//Skip default methods which are implemented by Object
					//itself or Annotation proxy
					if (isDefaultAnnotationMethod(method)) continue;
					
					try
					{
						Object res = method.invoke(annot);
						if (res instanceof String[])
							writeBlockIntro("public String[] %s()", 
									method.getName());
						else writeBlockIntro("public %s %s()", 
								method.getReturnType().getName(), method.getName());
						
						if (res instanceof String)
						{
							writeReturn(STR, (String)res);
						}
						else if (res instanceof Character)
						{
							writeReturn(CHR, res.toString());
						}
						else if (res instanceof Long)
						{
							writeReturn((Long)res);
						}
						else if (res instanceof Float)
						{
							writeReturn((Float)res);
						}
						else if (res instanceof Double)
						{
							writeReturn((Double)res);
						}
						else if ((! (res instanceof Void)) &&
								(isPrimitiveClass(res.getClass())))
						{
							writeReturn(res.toString());
						}
						else if (res instanceof String[])
						{
							StringBuilder s = new StringBuilder();
							for (String str : (String[])res)
							{
								if (s.length() != 0) s.append(','); 
								s.append('"').append(str).append('"');
								
							}
							writeReturn("new String[] {%s}", s.toString());
						}
						else
						{
							throw new RuntimeException("Unsupported return type " + 
									res.getClass().getName() + 
									" of Annotation parameter function");
						}
					}
					catch (Exception e)
					{
						if (e instanceof RuntimeException) throw (RuntimeException)e;
						else throw new RuntimeException("Exception " + e.getClass().getName() + 
								"caught while invoking Annotation parameter function");
					}
					writeBlockOutro();
				}
				
				writeBlockOutro(true);				
				i++;
			}
			writeReturn("result");
		}
		
		private void writeAssign(JClassType overlayType)
		{
			if (overlayType == null) return;
			
			for (JField field : overlayType.getFields())
			{
				if (isReadOnly(overlayType, field) || 
						(field.isAnnotationPresent(NoAssign.class)) ||
						(! isSettable(overlayType, field))) continue;

				String setter = hasSetter(overlayType, field);
				String getter = hasGetter(overlayType, field);
				if (getter == null)
				{
					//Direct access
					getter = field.getName();
				}
				else getter = getter + "()";
				
				if (setter != null)
					write("d.%s(s.%s);", setter, getter);
				else write("d.%s = s.%s;", field.getName(), getter);  
			}
			
			writeAssign(overlayType.getSuperclass());
		}
		
		private void writeCompare(JClassType overlayType)
		{
			if (overlayType == null) return;
									
			for (JField field : overlayType.getFields())
			{
				if (! isPublished(overlayType, field)) continue;
				if (field.isAnnotationPresent(NoCompare.class)) continue;

				String getter = hasGetter(overlayType, field);
				if (getter == null)
				{
					//Direct access
					getter = field.getName();
				}
				else getter = getter + "()";
				
				if (field.getType().isPrimitive() != null)
					write("if (f.%1$s != t.%1$s) return %2$s;", getter, FALSE);
				else if (field.getType().isEnum() != null)
				{
					//Don't bother with Comparable to speed things up
					//for enums
					write("if (f.%1$s != t.%1$s) return %2$s;", getter, FALSE);
				}
				else if (field.getType().isClass() != null)
				{
					//First check equality of instances
					writeBlockIntro("if (f.%1$s != t.%1$s)", getter);
					//Then check the actual values
					writeBlockIntro("if (f.%s != null)", getter);
					//From is not null
					write("if (t.%s == null) return %s;", getter, FALSE);	//But to is 
					if ((field.getType().isClass().isAssignableTo(jString))
            || (field.getType().isClass().isAssignableTo(jInteger))
            || (field.getType().isClass().isAssignableTo(jDouble))
            || ((field.getType().isClass().isAssignableTo(jBoolean))))
					{
						//Don't bother with Comparable to speed things up
						//for strings, Integers, Booleans and Doubles
						write("if (! f.%1$s.equals(t.%1$s)) " +
								"return %2$s;", getter, FALSE);
					}
					else
					{
						writeBlockIntro("if (f.%s instanceof Comparable)", getter);
						write("if (! ((Comparable)f.%1$s).compare(t.%1$s)) " +
								"return %2$s;", getter, FALSE);
						writeBlockOutro();
						write("else if (! f.%1$s.equals(t.%1$s)) " +
								"return %2$s;", getter, FALSE);
					}
					writeBlockOutro();
					write("else if (t.%s != null) return %s;", getter, FALSE);	//from is null to is not
					writeBlockOutro();
				}
				else throw new RuntimeException("Unsupported field to compare " + 
						field.getType().toString() + " " +field.getName());
					
			}
			
			writeCompare(overlayType.getSuperclass());
		}
		
		@Override
		protected void writeImports()
		{
			super.writeImports();
			write(IMPORT, Annotation.class.getName());
			newline();
		}
		
		protected boolean isSupportedMethod(String name)
		{
			if (name.equals("wait")) return false;
			if (name.equals("notify")) return false;
			if (name.equals("notifyAll")) return false;
			if (name.equals("toString")) return false;
			if (name.equals("hashCode")) return false;
			if (name.equals("getClass")) return false;
			if (name.equals("equals")) return false;
			
			return true;
		}
		
		public static Class<?> getPrimitiveClass(Class<?> clazz)
		{
			assert clazz.isPrimitive();
			
			if (clazz == boolean.class) return Boolean.class;
			if (clazz == char.class) return Character.class;
			if (clazz == byte.class) return Byte.class;
			if (clazz == short.class) return Short.class;
			if (clazz == int.class) return Integer.class;
			if (clazz == long.class) return Long.class;
			if (clazz == float.class) return Float.class;
			if (clazz == double.class) return Double.class;
			if (clazz == void.class) return Void.class;
			
			return null;
		}
		
		protected Class<?> assertClass(Class<?> clazz)
		{
			if (clazz.isPrimitive()) return getPrimitiveClass(clazz);
			else return clazz;
		}
		
		protected Class<?>[] getTypes(JParameter[] params)
		{
			Class<?>[] result = new Class<?>[params.length];
			
			for (int i = 0; i < params.length; i++)
			{
				try
				{
					JType type = params[i].getType();
					JPrimitiveType primitive = type.isPrimitive();
					if (primitive != null)
					{
						switch (primitive)
						{
						case BOOLEAN: result[i] = boolean.class; break;
						case BYTE: result[i] = byte.class; break;
						case CHAR: result[i] = char.class; break;
						case DOUBLE: result[i] = double.class; break;
						case FLOAT: result[i] = float.class; break;
						case INT: result[i] = int.class; break;
						case LONG: result[i] = long.class; break;
						case SHORT: result[i] = short.class; break;
						case VOID: result[i] = void.class; break;
						} 
					}
					else result[i] = Class.forName(type.
							getQualifiedSourceName());
				}
				catch (ClassNotFoundException e)
				{
					throw new RuntimeException(e);
				}
			}
			
			return result;
		}

		protected void writeMethods(JClassType overlayType, Class<?> clazz)
		{
			Method[] methods = clazz.getMethods();
			
			int i = 0;
			for (Method method : methods)
			{
				if (! isSupportedMethod(method.getName())) continue;
				i++;
			}
			
			write("RttiMethod[] result = new RttiMethodImpl[%d];", i);
			
			i = 0;
			for (Method method : methods)
			{
				if (! isSupportedMethod(method.getName())) continue;
				writeBlockIntro("result[%d] = new RttiMethodImpl()", i);

				writeBlockIntro("public String getName()").
					writeReturn(STR, method.getName()).
				writeBlockOutro();
				
				/*writeAnnotations(clazz.getMethod(method.getName(), 
						getTypes(method.getParameters())).getAnnotations());*/
				writeAnnotations(method.getAnnotations());
				
				writeBlockIntro("protected Class<?>[] getParameterTypesInternal()");
				writeBlockIntro("Class<?>[] result = ");
				int last = method.getParameterTypes().length - 1;
				int index = 0;
				for (Class<?> param : method.getParameterTypes())
				{
					write(param.getName() + ".class" + 
							((last == index) ? "" : ','));
					index++;
				}
				writeBlockOutro(true);
				writeReturn("result");
				writeBlockOutro();
				
				writeBlockIntro("public Class<?> getReturnType()").
					writeReturn(method.getReturnType().getName() + ".class").
				writeBlockOutro();
				
				writeBlockIntro("protected Object invokeInternal(Object instance, Object... params) " +
						"throws RttiInvocationException, RttiIllegalArgumentException");
				write("if (! (instance instanceof %s))", 
						overlayType.getQualifiedSourceName()).indent().
					writeThrow("RttiIllegalArgumentException()").
				outdent();
				//Class must be converted from primitive to object to 
				//allow type conversion conventions of primitives
				//params are always a class
				//Parameter count is checked in RttiMethodImpl
				index = 0;
				for (Class<?> param : method.getParameterTypes())
				{
					if (param.isPrimitive())
					{
						//Primitives cannot be null
						write("if (! (params[%d] instanceof %s))", index, 
								assertClass(param).getName()).indent().
							writeThrow("RttiIllegalArgumentException()").
						outdent();
					}
					else
					{
						//Objects can be null
						write("if ((params[%1$d] != null) && (! (params[%1$d] instanceof %2$s)))", index, 
								assertClass(param).getName()).indent().
							writeThrow("RttiIllegalArgumentException()").
						outdent();
					}
					index++;
				}
				writeBlockIntro("try");
				StringBuilder s = new StringBuilder(String.format("((%s)instance).%s(", 
						overlayType.getQualifiedSourceName(), method.getName()));
				index = 0;
				for (Class<?> param : method.getParameterTypes())
				{
					s.append(String.format("(%s)params[%d]", 
							assertClass(param).getName(), index));
					if (last != index) s.append(", ");
					index++;
				}
				s.append(')');
				if (method.getReturnType() == void.class)
				{
					s.append(';');
					write(s.toString());
					writeReturn(NULL);
				}
				else writeReturn(s.toString());
				writeBlockOutro();	//try
				writeBlockIntro("catch (Throwable e)");
				writeThrow("RttiInvocationException(e)");
				writeBlockOutro();	//catch
				writeBlockOutro();
				
				writeBlockOutro(true);				
				
				i++;
			}
			writeReturn("result");
		}
	}
}
