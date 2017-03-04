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

import java.io.PrintWriter;

import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.TreeLogger.Type;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.NotFoundException;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.google.gwt.uibinder.rebind.IndentedWriter;

public abstract class BaseGenerator extends Generator
{
	protected JClassType interfaceType(TypeOracle oracle, String s,
			TreeLogger treeLogger) throws UnableToCompleteException
	{
		JClassType interfaceType;
		try
		{
			interfaceType = oracle.getType(s);
		}
		catch (NotFoundException e)
		{
			treeLogger.log(TreeLogger.ERROR, String.format(
					"%s: Could not find the interface [%s]. %s", e.getClass()
							.getName(), s, e.getMessage()));
			throw new UnableToCompleteException();
		}
		return interfaceType;
	}

	@Override
	public String generate(TreeLogger treeLogger,
			GeneratorContext generatorContext, String typeName)
			throws UnableToCompleteException
	{
		JClassType interfaceType = interfaceType(
				generatorContext.getTypeOracle(), typeName, treeLogger);

		String packageName = getPackageName(interfaceType);
		String implName = interfaceType.getName().replace(".", "_") + "Impl";
		TreeLogger logger = treeLogger.branch(Type.INFO, "Generating: " + implName);
		logger.log(Type.INFO, "Package: " + packageName);
		PrintWriter printWriter = generatorContext.tryCreate(treeLogger, packageName, implName); 
		if (printWriter != null)
		{
			BaseGeneratorClient client = getClient();
			
			client.writer = new IndentedWriter(printWriter);
			client.implName = implName;
			client.generatorContext = generatorContext;
			client.oracle = generatorContext.getTypeOracle();
			client.interfaceType = interfaceType;
			client.logger = logger;
			
			client.generate(packageName);
			generatorContext.commit(treeLogger, printWriter);
		}
		return packageName + "." + implName;
	}
	
	public String getPackageName(JClassType interfaceType)
	{
		return interfaceType.getPackage().getName();
	}

	protected abstract BaseGeneratorClient getClient();

}
