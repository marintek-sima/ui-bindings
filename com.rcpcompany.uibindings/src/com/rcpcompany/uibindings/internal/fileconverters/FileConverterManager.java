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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.rcpcompany.uibindings.utils.IClipboardConverterManager;
import com.rcpcompany.uibindings.utils.IClipboardConverterManager.IResult;
import com.rcpcompany.uibindings.utils.IFileConverterManager;
import com.rcpcompany.utils.logging.LogUtils;

/**
 * Implementation of {@link IClipboardConverterManager}.
 * 
 * @author Tonny Madsen, The RCP Company
 */
public class FileConverterManager implements IFileConverterManager {

	private static IFileConverterManager MANAGER = null;

	public static IFileConverterManager getManager() {
		if (MANAGER == null) {
			MANAGER = new FileConverterManager();
		}
		return MANAGER;
	}

	private final IFileConverter[] myConverters = new IFileConverter[] {

	new CSVFileConverter("Comma Separated Values", "[ \t]*,[ \t]*"),
			new CSVFileConverter("Tab Separated Values", " *\t *"),
			new CSVFileConverter("Semicolon Separated Values", "[ \t]*;[ \t]*"),
			new CSVFileConverter("Space Separated Values", " +"),
			new DelimiterFileConverter("Delimiter Separated Values", " \t") };

	@Override
	public List<IResult> getFileConversions(File file) {
		final String content = getContentFromFile(file);
		final List<IResult> results = new ArrayList<IResult>();

		CC: for (final IFileConverter cc : myConverters) {
			final String[][] table;
			try {
				table = cc.convert(content);
			} catch (final Exception ex) {
				LogUtils.error(cc, ex);
				continue CC;
			}
			if (table == null) {
				continue;
			}

			/*
			 * Check for well-formed tables
			 */
			int columns = -1;
			if (table.length == 0) {
				continue CC;
			}
			for (int y = 0; y < table.length; y++) {
				final String[] row = table[y];
				if (row == null || row.length == 0) {
					continue CC;
				}
				if (columns == -1) {
					columns = row.length;
				} else if (columns != row.length) {
					continue CC;
				}
			}

			/*
			 * Look for a match
			 */
			IResult res = null;
			T: for (final IResult r : results) {
				if (r.getRows() != table.length) {
					continue T;
				}
				if (r.getColumns() != table[0].length) {
					continue T;
				}

				final String[][] t = r.getTable();
				for (int y = 0; y < t.length; y++) {
					final String[] row1 = table[y];
					final String[] row2 = t[y];
					if (!Arrays.equals(row1, row2)) {
						continue T;
					}
				}
				res = r;
				break;
			}

			/*
			 * Create a new table
			 */
			if (res == null) {
				res = new Result(table);
				results.add(res);
			}

			res.getConverterNames().add(cc.getName());
		}
		/*
		 * Sort by
		 * 
		 * - number of columns
		 * 
		 * - number of converters with the specified result
		 */
		Collections.sort(results, new Comparator<IResult>() {
			@Override
			public int compare(IResult o1, IResult o2) {
				final int c1 = o1.getColumns() * o1.getRows();
				final int c2 = o2.getColumns() * o2.getRows();
				if (c1 != c2) return c2 - c1;
				return o2.getConverterNames().size() - o1.getConverterNames().size();
			}
		});
		return results;
	}

	private String getContentFromFile(File file) {
		int lineNumber = 0;
		MyLineNumberReader reader = null;
		try {
			reader = new MyLineNumberReader(new BufferedReader(new FileReader(file.getAbsolutePath())));
			final StringBuffer sb = new StringBuffer();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line).append("\n");
				lineNumber = reader.getLineNumber();
			}
			final String ret = sb.toString();
			if (ret.isEmpty()) return null;
			return ret;
		} catch (final IOException e) {
			throw new RuntimeException("Error reading line " + lineNumber + " in file: " + file.getAbsolutePath(), e);
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (final IOException e) {
				// ignore
			}
		}
	}

	/**
	 * Implementation of {@link IResult} for use with
	 * {@link IFileConverterManager#getFileConversions()}.
	 */
	public static class Result implements IResult {
		private final String[][] myTable;
		private final List<String> myNames = new ArrayList<String>();

		/**
		 * Constructs and returns a new result for the specified table.
		 * 
		 * @param table the table of this result
		 */
		public Result(String[][] table) {
			myTable = table;

		}

		@Override
		public List<String> getConverterNames() {
			return myNames;
		}

		@Override
		public String[][] getTable() {
			return myTable;
		}

		@Override
		public int getRows() {
			return myTable.length;
		}

		@Override
		public int getColumns() {
			return myTable[0].length;
		}
	}
}
