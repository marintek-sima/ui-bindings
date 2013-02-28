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

import com.rcpcompany.uibindings.internal.clipboardconverters.CSVResultConverter;
import com.rcpcompany.uibindings.internal.clipboardconverters.IClipboardConverter;

/**
 * {@link IClipboardConverter} for Comma-Separated-Values.
 * <p>
 * Very common interchange format.
 * 
 * @author Tonny Madsen, The RCP Company
 */
public class CSVFileConverter extends CSVResultConverter implements IFileConverter {
	private final String myName;

	/**
	 * Constructs and returns a new converter with the specified name and the specified separator
	 * {@link String#split(String) regular expression}.
	 * 
	 * @param name the name of the converter
	 * @param separatorRE the regular expression for the converter
	 */
	public CSVFileConverter(String name, String separatorRE) {
		super(separatorRE);
		myName = name;
	}

	@Override
	public String getName() {
		return myName;
	}

	@Override
	public String[][] convert(String content) {
		return super.convert(content);
	}

}
