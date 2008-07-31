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
package org.eclipse.mylyn.wikitext.core.parser.markup.block;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.eclipse.mylyn.wikitext.core.parser.Attributes;
import org.eclipse.mylyn.wikitext.core.parser.DocumentBuilder.BlockType;
import org.eclipse.mylyn.wikitext.core.parser.markup.Block;

/**
 *
 *
 * @author David Green
 */
public abstract class GlossaryBlock extends Block {

	protected int blockLineNumber = 0;

	private String style;

	@Override
	public int processLineContent(String line,int offset) {
		if (blockLineNumber++ > 0) {
			setClosed(true);
			return 0;
		}
		if (!getMarkupLanguage().isFilterGenerativeContents()) {
			SortedMap<String,String> glossary = new TreeMap<String, String>(state.getGlossaryTerms());

			builder.beginBlock(BlockType.DEFINITION_LIST, new Attributes(null,
					null, style == null ? null : "list-style: " + style, null));
			Attributes nullAttributes = new Attributes();
			for (Map.Entry<String, String> ent: glossary.entrySet()) {
				builder.beginBlock(BlockType.DEFINITION_TERM, nullAttributes);
				builder.characters(ent.getKey());
				builder.endBlock();

				builder.beginBlock(BlockType.DEFINITION_ITEM, nullAttributes);
				builder.characters(ent.getValue());
				builder.endBlock();
			}

			builder.endBlock();
		}
		return -1;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

}
