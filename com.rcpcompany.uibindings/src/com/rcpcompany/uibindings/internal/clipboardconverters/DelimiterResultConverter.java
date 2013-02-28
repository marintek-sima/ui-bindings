package com.rcpcompany.uibindings.internal.clipboardconverters;

import java.util.ArrayList;
import java.util.StringTokenizer;

import com.rcpcompany.uibindings.model.utils.DoubleQuoteTokenizer;

public class DelimiterResultConverter {

	private final String mySeparatorRE;
	private final static String[] EMPTY_STRING_ARR = new String[0];

	public DelimiterResultConverter(String separator) {
		this.mySeparatorRE = separator;
	}

	protected String[][] convert(String content) {
		if (content == null) return null;
		while (content.endsWith("\n")) {
			content = content.substring(0, content.length() - 1);
		}
		content = content.replace("\r", "");
		final String[] lines = content.split("\n");
		final int noLines = lines.length;
		final String[][] result = new String[noLines][0];

		for (int i = 0; i < lines.length; i++) {
			if (lines[i].contains("\"")) {
				final DoubleQuoteTokenizer dqt = new DoubleQuoteTokenizer();
				result[i] = dqt.tokenize(lines[i], mySeparatorRE);
			} else {
				result[i] = splitLine(lines[i]);
			}
		}

		return result;
	}

	private String[] splitLine(String line) {
		final StringTokenizer tokenizer = new StringTokenizer(line, mySeparatorRE);

		final ArrayList<String> result = new ArrayList<String>();
		while (tokenizer.hasMoreTokens()) {
			result.add(tokenizer.nextToken());
		}

		return result.toArray(EMPTY_STRING_ARR);
	}

}
