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
package com.promis.generators;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JParameter;
import com.google.gwt.core.ext.typeinfo.JParameterizedType;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.google.gwt.uibinder.rebind.IndentedWriter;
import com.promis.logging.client.Log;

public abstract class BaseGeneratorClient
{
	protected static final String IMPORT = "import %1$s;";
	protected static final String PACKAGE = "package %s;";
	protected static final String NULL = "null";
	protected static final String TRUE = "true";
	protected static final String FALSE = "false";
	/**
	 * String literal for printf format string <code>&quot;%s&quot;</code>
	 */
	protected static final String STR = "\"%s\"";
	/**
	 * Character literal for printf format string <code>'%s'</code>
	 */
	protected static final String CHR = "'%s'";
	/**
	 * Numeric literal for printf format string <code>&quot;%d&quot;</code>
	 */
	protected static final String NUM = "\"%d\"";
	/**
	 * New object (without parameters) literal for printf format string <code>new %s()</code>
	 */
	protected static final String NEW = "new %s()";
	
	
	IndentedWriter writer;
	protected GeneratorContext generatorContext;
	protected TypeOracle oracle;
	protected JClassType interfaceType;
	protected String implName;
	protected TreeLogger logger;
	
	final void generate(String packageName)
	{
		write(String.format(PACKAGE, packageName));
		newline();
		writeImports();
		writeClassIntro();
		writeFieldsIntro();
		doGenerate();
		writeOutro();
	}

	protected abstract void doGenerate();
	
	public String sprintf(String format, Object... params)
	{
		return String.format(format, params);
	}
		
	public final BaseGeneratorClient write(String format, Object... params)
	{
		writer.write(format, params);
		return this;
	}
	
	public final BaseGeneratorClient newline()
	{
		writer.newline();
		return this;
	}
	
	public final BaseGeneratorClient indent()
	{
		writer.indent();
		return this;
	}
	
	public final BaseGeneratorClient outdent()
	{
		writer.outdent();
		return this;
	}

	public void writeClassIntro()
	{
		write("public class %1$s %2$s %3$s {", implName,
				interfaceType.isInterface() != null ? "implements" : "extends", 
				interfaceType.getName());
		indent();
		//newline();
	}

	protected JParameter[] extractInterfaceMethodParams(JClassType interfaceType)
	{
		return interfaceType.getImplementedInterfaces()[0].getMethods()[0]
				.getParameters();
	}
	
	protected JClassType[] extractInterfaceGenericParams(JClassType interfaceType)
	{
		//This type cannot be generic, so superclass has to
		if (interfaceType.isInterface() != null) interfaceType = interfaceType.getImplementedInterfaces()[0];
		else interfaceType = interfaceType.getSuperclass();	//A class
		
		JParameterizedType g = interfaceType.isParameterized();
		if (g == null) return null;
		
		JClassType[] res = g.getTypeArgs();
		
		return res;
	}
	
	protected void writeFieldsIntro()
	{
		// nothing to do now
		//newline();
	}

	protected void writeImports()
	{
		write(IMPORT, GWT.class.getName());
		write(IMPORT, Log.class.getName());
		//write(IMPORT,
		// parameters[1].getType().getQualifiedSourceName());
		newline();
	}

	protected void writeOutro()
	{
		outdent();
		write("}");
	}
	
	public final BaseGeneratorClient writeBlockIntro(String format, Object... params)
	{
		write(format + " {", params);
		indent();
		return this;
	}
	
	public final BaseGeneratorClient writeBlockOutro()
	{
		writeBlockOutro(false);
		return this;
	}

	public final BaseGeneratorClient writeBlockOutro(boolean semicolon)
	{
		outdent();
		if (semicolon) write("};");
		else write("}");
		return this;
	}

	public final BaseGeneratorClient writeReturn(String result)
	{
		write("return %s;", result);
		return this;
	}
	
	public final BaseGeneratorClient writeReturn(int result)
	{
		write("return %d;", result);
		return this;
	}
	
	public final BaseGeneratorClient writeReturn(long result)
	{
		write("return %dL;", result);
		return this;
	}
	
	public final BaseGeneratorClient writeReturn(double result)
	{
		write("return %s;", String.valueOf(result));
		return this;
	}
	
	public final BaseGeneratorClient writeReturn(float result)
	{
		write("return %sf;", String.valueOf(result));
		return this;
	}
	
	public final BaseGeneratorClient writeReturn(boolean result)
	{
		if (result) writeReturn(TRUE);
		else writeReturn(FALSE);
		return this;
	}
	
	public final BaseGeneratorClient writeReturn(String resultFmt, Object... params)
	{
		String result = sprintf(resultFmt, params);
		write("return %s;", result);
		return this;
	}
	
	public final BaseGeneratorClient writeThrow(String exception)
	{
		write("throw new %s;", exception);
		return this;
	}
}
