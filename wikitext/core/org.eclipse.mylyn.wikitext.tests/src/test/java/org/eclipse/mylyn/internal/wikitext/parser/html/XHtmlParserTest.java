/*******************************************************************************
 * Copyright (c) 2011, 2012 Tasktop Technologies
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     David Green - initial API and implementation
 *******************************************************************************/

package org.eclipse.mylyn.internal.wikitext.parser.html;

import org.eclipse.mylyn.wikitext.internal.parser.html.AbstractSaxHtmlParser;
import org.eclipse.mylyn.wikitext.internal.parser.html.XHtmlParser;

/**
 * @author David Green
 * @see XHtmlParser
 */
public class XHtmlParserTest extends AbstractSaxParserTest {

	@Override
	protected AbstractSaxHtmlParser createParser() {
		return new XHtmlParser();
	}

}
