/*******************************************************************************
 * Copyright (c) 2018 Tasktop Technologies.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Kevin de Vlaming - initial API and implementation
 *******************************************************************************/

package org.eclipse.mylyn.internal.wikitext.creole.tests.documentbuilder;

import org.eclipse.mylyn.wikitext.parser.Attributes;
import org.eclipse.mylyn.wikitext.parser.DocumentBuilder.BlockType;

/**
 * @see http://www.wikicreole.org/wiki/Elements
 * @author Kevin de Vlaming
 */
public class CreoleDocumentBuilderListTest extends AbstractCreoleDocumentBuilderTest {

	public void testListBulleted() {
		builder.beginDocument();
		builder.beginBlock(BlockType.BULLETED_LIST, new Attributes());
		builder.beginBlock(BlockType.LIST_ITEM, new Attributes());
		builder.characters("X");
		builder.endBlock();
		builder.beginBlock(BlockType.LIST_ITEM, new Attributes());
		builder.characters("Y");
		builder.endBlock();
		builder.beginBlock(BlockType.LIST_ITEM, new Attributes());
		builder.characters("Z");
		builder.endBlock();
		builder.endBlock();
		builder.endDocument();
		assertMarkup("* X\n* Y\n* Z\n");
	}

	public void testListNumeric() {
		builder.beginDocument();
		builder.beginBlock(BlockType.NUMERIC_LIST, new Attributes());
		builder.beginBlock(BlockType.LIST_ITEM, new Attributes());
		builder.characters("One");
		builder.endBlock();
		builder.beginBlock(BlockType.LIST_ITEM, new Attributes());
		builder.characters("Two");
		builder.endBlock();
		builder.beginBlock(BlockType.LIST_ITEM, new Attributes());
		builder.characters("Three");
		builder.endBlock();
		builder.endBlock();
		builder.endDocument();
		assertMarkup("# One\n# Two\n# Three\n");
	}

	public void testListConsecutive() {
		builder.beginDocument();
		builder.beginBlock(BlockType.BULLETED_LIST, new Attributes());
		builder.beginBlock(BlockType.LIST_ITEM, new Attributes());
		builder.characters("Food");
		builder.endBlock();
		builder.endBlock();
		builder.beginBlock(BlockType.NUMERIC_LIST, new Attributes());
		builder.beginBlock(BlockType.LIST_ITEM, new Attributes());
		builder.characters("Drink");
		builder.endBlock();
		builder.endBlock();
		builder.endDocument();
		assertMarkup("* Food\n\n# Drink\n");
	}

	public void testListWithParagraphs() {
		builder.beginDocument();
		builder.beginBlock(BlockType.BULLETED_LIST, new Attributes());
		builder.beginBlock(BlockType.LIST_ITEM, new Attributes());
		builder.beginBlock(BlockType.PARAGRAPH, new Attributes());
		builder.characters("X");
		builder.endBlock();
		builder.endBlock();
		builder.beginBlock(BlockType.LIST_ITEM, new Attributes());
		builder.beginBlock(BlockType.PARAGRAPH, new Attributes());
		builder.characters("Y");
		builder.endBlock();
		builder.endBlock();
		builder.beginBlock(BlockType.LIST_ITEM, new Attributes());
		builder.beginBlock(BlockType.PARAGRAPH, new Attributes());
		builder.characters("Z");
		builder.endBlock();
		builder.endBlock();
		builder.endBlock();
		builder.endDocument();
		assertMarkup("* X\n\n* Y\n\n* Z\n\n");
	}

	public void testBulletedListWithNestedSublist() {
		builder.beginDocument();

		builder.beginBlock(BlockType.BULLETED_LIST, new Attributes()); //begin first list
		emitListItem("item 1");
		builder.beginBlock(BlockType.BULLETED_LIST, new Attributes()); //begin second list
		emitListItem("item 1.A.");
		emitListItem("item 1.B.");
		builder.endBlock(); //close second list
		emitListItem("item 2");
		builder.beginBlock(BlockType.NUMERIC_LIST, new Attributes()); //begin third list
		emitListItem("item 2.A.");
		emitListItem("item 2.B.");
		builder.endBlock(); //close third list
		builder.endBlock(); //close first list

		builder.beginBlock(BlockType.NUMERIC_LIST, new Attributes()); //begin fourth list
		emitListItem("item 3");
		builder.beginBlock(BlockType.BULLETED_LIST, new Attributes()); //begin fifth list
		emitListItem("item 3.A.");
		emitListItem("item 3.B.");
		builder.beginBlock(BlockType.NUMERIC_LIST, new Attributes()); //begin sixth list
		emitListItem("item 3.B.i.");
		emitListItem("item 3.B.ii.");
		builder.endBlock(); // close sixth list

		builder.beginBlock(BlockType.LIST_ITEM, new Attributes()); //begin list item
		builder.characters("item 3.C.");
		builder.lineBreak();
		builder.characters("item 3.C. line 2");
		builder.endBlock(); // close list item

		builder.endBlock(); // close fifth list
		builder.endBlock(); // close fourth list

		builder.endDocument();

		String markup = out.toString();

		assertEquals(//
				"* item 1\n" + //
						"** item 1.A.\n" + //
						"** item 1.B.\n" + //
						"* item 2\n" + //
						"## item 2.A.\n" + //
						"## item 2.B.\n" + //
						"\n" + //
						"# item 3\n" + //
						"** item 3.A.\n" + //
						"** item 3.B.\n" + //
						"### item 3.B.i.\n" + //
						"### item 3.B.ii.\n" + //
						"** item 3.C." + //
						"\\\\item 3.C. line 2\n", //
				markup);
	}

	private void emitListItem(String text) {
		builder.beginBlock(BlockType.LIST_ITEM, new Attributes());
		builder.characters(text);
		builder.endBlock();
	}

}