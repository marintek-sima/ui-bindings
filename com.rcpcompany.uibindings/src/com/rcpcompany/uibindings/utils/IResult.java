package com.rcpcompany.uibindings.utils;

import java.util.List;

/**
 * A single result from the convertion of the clipboard.
 * 
 * @author Tonny Madsen, The RCP Company
 */
public interface IResult {
	/**
	 * Returns the names of all the converters with the specified table result.
	 * 
	 * @return a list of names
	 */
	List<String> getConverterNames();

	/**
	 * Returns the result table for the specified converters.
	 * <p>
	 * The returned table is in the format row-column, so <code>result.length == #rows</code> and
	 * <code>result[0].length == #columns</code>. All rows must have the identical number of
	 * columns.
	 * 
	 * @return the table.
	 */
	String[][] getTable();

	/**
	 * Returns the number of rows in the table.
	 * 
	 * @return the number of rows
	 */
	int getRows();

	/**
	 * Returns the number of columns in the table.
	 * 
	 * @return the number of columns
	 */
	int getColumns();
}
