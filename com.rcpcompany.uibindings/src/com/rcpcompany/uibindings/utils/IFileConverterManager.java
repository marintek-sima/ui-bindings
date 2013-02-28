/*******************************************************************************
 * Copyright (c) 2017, 2011 The RCP Company and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     The RCP Company - initial API and implementation
 *******************************************************************************/
package com.rcpcompany.uibindings.utils;

import java.io.File;
import java.util.List;

import com.rcpcompany.uibindings.internal.fileconverters.FileConverterManager;
import com.rcpcompany.uibindings.utils.IClipboardConverterManager.IResult;

/**
 * A set of services to ease the conversion of file data to tables.
 * <p>
 * Primary for use in super paste and super create.
 * 
 * @author Tonny Madsen, The RCP Company
 */
public interface IFileConverterManager {
	/**
	 * Factory methods...
	 */
	public static final class Factory {
		/**
		 * Private constructor to prevent construction.
		 */
		private Factory() {
		}

		public static IFileConverterManager getManager() {
			return FileConverterManager.getManager();
		}
	}

	/**
	 * Returns the result of converting the current file content.
	 * <p>
	 * The results are is priority order, so the first result is the most likely.
	 * 
	 * @param file
	 * 
	 * @return the results
	 */
	List<IResult> getFileConversions(File file);
}
