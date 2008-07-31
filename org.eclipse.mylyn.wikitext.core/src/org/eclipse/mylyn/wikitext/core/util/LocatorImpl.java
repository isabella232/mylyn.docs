/*******************************************************************************
 * Copyright (c) 2007, 2008 David Green and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     David Green - initial API and implementation
 *******************************************************************************/
package org.eclipse.mylyn.wikitext.core.util;

import org.eclipse.mylyn.wikitext.core.parser.Locator;

/**
 *
 *
 * @author David Green
 */
public class LocatorImpl implements Locator {

	private int documentOffset;
	private int lineCharacterOffset;
	private int lineDocumentOffset;
	private int lineLength;
	private int lineNumber;
	private int lineSegmentEndOffset;

	public LocatorImpl(Locator other) {
		documentOffset = other.getDocumentOffset();
		lineCharacterOffset = other.getLineCharacterOffset();
		lineDocumentOffset = other.getLineDocumentOffset();
		lineLength = other.getLineLength();
		lineNumber = other.getLineNumber();
		lineSegmentEndOffset = other.getLineSegmentEndOffset();
	}

	public int getDocumentOffset() {
		return documentOffset;
	}

	public int getLineCharacterOffset() {
		return lineCharacterOffset;
	}

	public int getLineDocumentOffset() {
		return lineDocumentOffset;
	}

	public int getLineLength() {
		return lineLength;
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public int getLineSegmentEndOffset() {
		return lineSegmentEndOffset;
	}

}
