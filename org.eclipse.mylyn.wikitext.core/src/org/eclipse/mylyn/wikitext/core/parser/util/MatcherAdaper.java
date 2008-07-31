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
package org.eclipse.mylyn.wikitext.core.parser.util;

/**
 *
 *
 * @author David Green
 */
public class MatcherAdaper implements Matcher {

	private java.util.regex.Matcher delegate;

	public MatcherAdaper(java.util.regex.Matcher delegate) {
		this.delegate = delegate;
	}

	public int end(int group) {
		return delegate.end(group);
	}

	public String group(int group) {
		return delegate.group(group);
	}

	public int start(int group) {
		return delegate.start(group);
	}

}
