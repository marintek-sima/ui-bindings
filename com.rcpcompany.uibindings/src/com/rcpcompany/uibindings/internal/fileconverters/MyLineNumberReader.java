package com.rcpcompany.uibindings.internal.fileconverters;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;

public class MyLineNumberReader extends LineNumberReader {

	private final char[] commentChars = { '#', '\'' };

	/**
	 * Create a new line-numbering reader, using the default input-buffer size.
	 */
	public MyLineNumberReader(Reader in) {
		super(in);
	}

	@Override
	public String readLine() throws IOException {
		final String line = super.readLine();
		if (line == null) return null;
		if (line.charAt(0) == commentChars[0] || line.charAt(0) == commentChars[1]) return readLine();
		return line;
	}
}
