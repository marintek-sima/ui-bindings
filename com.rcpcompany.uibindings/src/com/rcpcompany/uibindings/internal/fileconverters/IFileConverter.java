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
package com.rcpcompany.uibindings.internal.fileconverters;

import com.rcpcompany.uibindings.utils.IFileConverterManager;


/**
 * File converter for one file format.
 * 
 * @author Robert Heggdal, Itema as
 */
public interface IFileConverter {
	/**
	 * Returns the name of the format for this converter.
	 * <p>
	 * E.g. "CSV", "HTML Table", etc
	 * 
	 * @return the format name
	 */
	String getName();

	/**
	 * Converts the current format of the clipboard to a table of <code>Strings</code>.
	 * <p>
	 * The returned table is in the format specified by
	 * {@link IFileConverterManager.IResult#getTable()}.
	 * <p>
	 * The formnat is check by the caller.
	 * 
	 * @param content the String content to copy from
	 * @return the converted table or <code>null</code> if not possible
	 */
	String[][] convert(String content);
}
