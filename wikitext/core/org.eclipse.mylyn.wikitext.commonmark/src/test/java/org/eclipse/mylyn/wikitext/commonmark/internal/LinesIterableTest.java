/*******************************************************************************
 * Copyright (c) 2015 David Green.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     David Green - initial API and implementation
 *******************************************************************************/

package org.eclipse.mylyn.wikitext.commonmark.internal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Test;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

public class LinesIterableTest {

	@Test(expected = NullPointerException.class)
	public void requiresLineSequence() {
		assertNotNull(new LinesIterable(null, Predicates.<Line> alwaysTrue()));
	}

	@Test(expected = NullPointerException.class)
	public void requiresPredicate() {
		assertNotNull(new LinesIterable(LineSequence.create(""), null));
	}

	@Test
	public void iteratorWithNoLines() {
		LinesIterable iterable = new LinesIterable(LineSequence.create(""), Predicates.<Line> alwaysTrue());
		Iterator<Line> iterator = iterable.iterator();
		assertNotNull(iterator);
		assertFalse(iterator.hasNext());
		assertThrows(NoSuchElementException.class, () -> iterator.next());
	}

	@Test
	public void iteratorAdvances() {
		LinesIterable iterable = new LinesIterable(LineSequence.create("one\ntwo"), Predicates.<Line> alwaysTrue());
		Iterator<Line> iterator = iterable.iterator();
		assertNotNull(iterator);
		assertTrue(iterator.hasNext());
		Line next = iterator.next();
		assertNotNull(next);
		assertEquals("one", next.getText());
		Line two = iterator.next();
		assertNotNull(two);
		assertEquals("two", two.getText());
	}

	@Test
	public void iteratorRemove() {
		LinesIterable iterable = new LinesIterable(LineSequence.create("one"), Predicates.<Line> alwaysTrue());
		Iterator<Line> iterator = iterable.iterator();
		assertNotNull(iterator);
		assertTrue(iterator.hasNext());
		Line next = iterator.next();
		assertNotNull(next);
		assertEquals("one", next.getText());
		assertThrows(UnsupportedOperationException.class, () -> iterator.remove());
	}

	@Test
	@SuppressWarnings("unchecked")
	public void hasNext() {
		Predicate<Line> predicate = mock(Predicate.class);
		LineSequence lineSequence = LineSequence.create("one");
		Iterator<Line> iterator = new LinesIterable(lineSequence, predicate).iterator();
		assertFalse(iterator.hasNext());
		doReturn(true).when(predicate).apply(any(Line.class));
		assertTrue(iterator.hasNext());
		lineSequence.advance();
		assertFalse(iterator.hasNext());
	}
}
