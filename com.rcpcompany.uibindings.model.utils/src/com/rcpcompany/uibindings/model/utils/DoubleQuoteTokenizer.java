package com.rcpcompany.uibindings.model.utils;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class DoubleQuoteTokenizer {

	private final static String[] EMPTY_STRING_ARR = new String[0];

	public String[] tokenize(String line, String delimiter) {
		final StringTokenizer tokenizer = new StringTokenizer(line, delimiter);
		final ArrayList<String> result = new ArrayList<String>();
		boolean start = false;
		String specialToken = "";
		while (tokenizer.hasMoreTokens()) {
			final String nextToken = tokenizer.nextToken();

			// Check for a string (without spaces) that is surrounded by double quotes
			if (nextToken.startsWith("\"") && nextToken.endsWith("\"")) {
				result.add(nextToken.substring(1, nextToken.length() - 1));

			} else {

				// Check for a string starting with double quotes (and ending with a space / tab)
				if (nextToken.startsWith("\"") && !start) {
					specialToken = nextToken.substring(1);
					start = true;
					continue;
				}
				if (start) {
					if (nextToken.endsWith("\"")) {
						// We found a string ending with double quotes - which completes this
						// special token
						start = false;
						specialToken += " " + nextToken.substring(0, nextToken.length() - 1);
						result.add(specialToken);
					} else {
						// As long as we have not found a string ending with a double quote
						// we append to this special token
						specialToken += " " + nextToken;
					}
				} else {

					// Add normal tokens
					result.add(nextToken);
				}
			}
		}

		return result.toArray(EMPTY_STRING_ARR);
	}
}
