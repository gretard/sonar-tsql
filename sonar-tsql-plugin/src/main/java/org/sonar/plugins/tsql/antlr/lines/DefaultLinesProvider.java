package org.sonar.plugins.tsql.antlr.lines;

import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.misc.Interval;
import org.sonar.plugins.tsql.antlr.IParsedNode;

public class DefaultLinesProvider implements ILinesProvider {

	private final CommonTokenStream stream;

	public DefaultLinesProvider(final CommonTokenStream stream) {
		this.stream = stream;
	}

	@Override
	public int getLine(final IParsedNode node) {
		if (node == null || node.getItem() == null) {
			return 0;
		}
		final Interval sourceInterval = node.getItem().getSourceInterval();
		final Token firstToken = stream.get(sourceInterval.a);
		final int line = firstToken.getLine();
		return line;
	}

}
