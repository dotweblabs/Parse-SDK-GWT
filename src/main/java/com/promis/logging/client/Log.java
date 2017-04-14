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
package com.promis.logging.client;

import java.util.Set;
import java.util.logging.*;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.UmbrellaException;

public class Log
{
	private static final String NAME = "MyLogger";
	
	private static GWT.UncaughtExceptionHandler exceptionHandler =
		new GWT.UncaughtExceptionHandler()
		{
			@Override
			public void onUncaughtException(Throwable e)
			{
				if (e instanceof UmbrellaException)
				{
					UmbrellaException u = (UmbrellaException)e;
					Set<Throwable> causes = u.getCauses();
					if (causes.isEmpty()) exception(e);
					else 
					{
						if (causes.size() > 1) severe(e.getMessage());
						for (Throwable t : causes)
						{
							exception(t);
						}
					}
				}
				else exception(e);
			}
		};
		
	private static Logger getLogger()
	{
		return Logger.getLogger(NAME);
	}
	
	private static void log(Level level, String msg)
	{
		getLogger().log(level, msg);	
	}
	
	public static void info(String msg)
	{
		log(Level.INFO, msg);
	}

	public static void warning(String msg)
	{
		log(Level.WARNING, msg);
	}

	public static void severe(String msg)
	{
		log(Level.SEVERE, msg);
	}
	
	public static void exception(Throwable e)
	{
		getLogger().log(Level.SEVERE, "", e);
	}
	
	public static void logStack()
	{
		try
		{
			throw new Exception();
		}
		catch (Throwable e)
		{
			getLogger().log(Level.INFO, "", e);
		}
	}

	/**
	 * Returns exception handler that will log all unhandled thrown exceptions.
	 * It will also unwind exceptions if wrapped with {@link UmbrellaException}
	 * @return
	 */
	public static GWT.UncaughtExceptionHandler getExceptionHandler()
	{
		return exceptionHandler;
	}
}
