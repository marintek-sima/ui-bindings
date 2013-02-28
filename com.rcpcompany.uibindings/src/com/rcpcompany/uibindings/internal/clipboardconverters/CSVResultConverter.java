package com.rcpcompany.uibindings.internal.clipboardconverters;

import com.rcpcompany.uibindings.model.utils.DoubleQuoteTokenizer;

public class CSVResultConverter {

	private final String mySeparatorRE;

	public CSVResultConverter(String separator) {
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
				result[i] = lines[i].split(mySeparatorRE);
			}
			// LogUtils.debug(this, "RE='" + mySeparatorRE + "': '" + lines[i] + "->" +
			// Arrays.toString(result[i]));
		}
		return result;
	}

}
