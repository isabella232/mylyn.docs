/*******************************************************************************
 * Copyright (c) 2012, 2013 Stefan Seelmann and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Stefan Seelmann - initial API and implementation
 *******************************************************************************/

package org.eclipse.mylyn.internal.wikitext.markdown.tests;

/**
 * Tests for Markdown span elements. Follows specification at
 * <a>http://daringfireball.net/projects/markdown/syntax#span</a>.
 * 
 * @author Stefan Seelmann
 */
public class MarkdownLanguageSpanElementsTest extends MarkdownLanguageTestBase {

	/*
	 * Links. To create an inline link, use a set of regular parentheses immediately after the link text's 
	 * closing square bracket. Inside the parentheses, put the URL where you want the link to point, along 
	 * with an optional title for the link, surrounded in quotes.
	 */
	public void testInlineLinkWithTitle() {
		String markup = "This is [ an example ](http://example.com/ \"Title\") inline link.";
		String expectedHtml = "<p>This is <a href=\"http://example.com/\" title=\"Title\">an example</a> inline link.</p>";
		parseAndAssert(markup, expectedHtml);
	}

	public void testInlineLinkWithoutTitle() {
		String markup = "[This link](http://example.net/) has no title attribute.";
		String expectedHtml = "<p><a href=\"http://example.net/\">This link</a> has no title attribute.</p>";
		parseAndAssert(markup, expectedHtml);
	}

	/*
	 * If you're referring to a local resource on the same server, you can use relative paths:
	 */
	public void testInlineLinkAsRelativePath() {
		String markup = "See my [About](/about/) page for details.";
		String expectedHtml = "<p>See my <a href=\"/about/\">About</a> page for details.</p>";
		parseAndAssert(markup, expectedHtml);
	}

	public void testNoSpanWithinInlineLinks() throws Exception {
		String markup = "[Test](http://www.google.de/?q=t_es_t)";
		String expectedHtml = "<p><a href=\"http://www.google.de/?q=t_es_t\">Test</a></p>";
		parseAndAssert(markup, expectedHtml);
	}

	/*
	 * Reference-style links use a second set of square brackets, inside which you place a label of your choosing to
	 * identify the link. Then, anywhere in the document, you define your link label like this, on a line by itself.
	 * Link definitions are only used for creating links during Markdown processing, and are stripped from your 
	 * document in the HTML output.
	 * Note: Reference parsing is tested in LinkReferencesTest.
	 */
	public void testReferenceStyleLink() {
		String markup = "This is [an example][id] reference-style link.\n\n[id]: http://example.com/  \"Optional Title Here\"";
		String expectedHtml = "<p>This is <a href=\"http://example.com/\" title=\"Optional Title Here\">an example</a> reference-style link.</p>";
		parseAndAssert(markup, expectedHtml);
	}

	/*
	 * You can optionally use a space to separate the sets of brackets
	 */
	public void testReferenceStyleLinkWithSpace() {
		String markup = "This is [an example] [id] reference-style link.\n\n[id]: http://example.com/  \"Optional Title Here\"";
		String expectedHtml = "<p>This is <a href=\"http://example.com/\" title=\"Optional Title Here\">an example</a> reference-style link.</p>";
		parseAndAssert(markup, expectedHtml);
	}

	/*
	 * Link definition names may consist of letters, numbers, spaces, and punctuation.
	 */
	public void testReferenceStyleLinkNameAllowedCharacters() {
		String markup = "[Link][A-Z 1.0]\n\n[A-Z 1.0]: http://example.com/";
		String expectedHtml = "<p><a href=\"http://example.com/\">Link</a></p>";
		parseAndAssert(markup, expectedHtml);
	}

	/*
	 * But they are not case sensitive. E.g. these two links: [link text][a] and [link text][A] are equivalent.
	 */
	public void testReferenceStyleLinkNameIsNotCaseSensitive() {
		String markup = "[link text a][a] and [LINK TEXT A][A].\n\n[A]: http://example.com/";
		String expectedHtml = "<p><a href=\"http://example.com/\">link text a</a> and <a href=\"http://example.com/\">LINK TEXT A</a>.</p>";
		parseAndAssert(markup, expectedHtml);
	}

	/*
	 * The implicit link name shortcut allows you to omit the name of the link, in which case the link text itself 
	 * is used as the name. Just use an empty set of square brackets.
	 */
	public void testReferenceStyleLinkWithoutName() {
		String markup = "[Google][]\n\n[Google]: http://google.com/";
		String expectedHtml = "<p><a href=\"http://google.com/\">Google</a></p>";
		parseAndAssert(markup, expectedHtml);
	}

	/*
	 * Because link names may contain spaces, this shortcut even works for multiple words in the link text.
	 */
	public void testReferenceStyleLinkWithoutName2() {
		String markup = "Visit [Daring Fireball][] for more information.\n\n[Daring Fireball]: http://daringfireball.net/";
		String expectedHtml = "<p>Visit <a href=\"http://daringfireball.net/\">Daring Fireball</a> for more information.</p>";
		parseAndAssert(markup, expectedHtml);
	}

	public void testReferenceStyleLinkWithMissingReference() {
		String markup = "This is a [link][a] with missing reference.\n\n[b]: http://example.com/";
		String expectedHtml = "<p>This is a [link][a] with missing reference.</p>";
		parseAndAssert(markup, expectedHtml);
	}

	public void testNoSpanWithinReferenceStyleLinks() throws Exception {
		String markup = "[Test][test]\n\n[test]: http://www.google.de/?q=t_es_t";
		String expectedHtml = "<p><a href=\"http://www.google.de/?q=t_es_t\">Test</a></p>";
		parseAndAssert(markup, expectedHtml);
	}

	public void testNoLinkProcessingWithinCodeBlock() throws Exception {
		String markup = "    Code block [no link](http://example.com/).";
		String expectedHtml = "<pre><code>Code block [no link](http://example.com/).</code></pre>";
		parseAndAssert(markup, expectedHtml);
	}

	public void testNoLinkProcessingWithinInlineHtmlBlock() throws Exception {
		String markup = "<p>Inline html [no link](http://example.com/).</p>";
		String expectedHtml = "<p>Inline html [no link](http://example.com/).</p>";
		parseAndAssert(markup, expectedHtml);
	}

	public void testInlineLinkProcessingWithinAtxStyleHeading() throws Exception {
		String markup = "# Heading with [link](http://example.com/).";
		String expectedHtml = "<h1>Heading with <a href=\"http://example.com/\">link</a>.</h1>";
		parseAndAssert(markup, expectedHtml);
	}

	public void testReferenceLinkProcessingWithinAtxStyleHeading() throws Exception {
		String markup = "# Heading with [link][].\n\n[link]: http://example.com/";
		String expectedHtml = "<h1>Heading with <a href=\"http://example.com/\">link</a>.</h1>";
		parseAndAssert(markup, expectedHtml);
	}

	public void testInlineLinkProcessingWithinUnderlinedHeading() throws Exception {
		String markup = "Heading with [link](http://example.com/).\n===";
		String expectedHtml = "<h1>Heading with <a href=\"http://example.com/\">link</a>.</h1>";
		parseAndAssert(markup, expectedHtml);
	}

	public void testReferenceLinkProcessingWithinUnderlinedHeading() throws Exception {
		String markup = "Heading with [link][].\n===\n\n[link]: http://example.com/";
		String expectedHtml = "<h1>Heading with <a href=\"http://example.com/\">link</a>.</h1>";
		parseAndAssert(markup, expectedHtml);
	}

	public void testLinkProcessingWithinBlockQuote() throws Exception {
		String markup = "> Block quote [link](http://example.com/).";
		String expectedHtml = "<blockquote><p>Block quote <a href=\"http://example.com/\">link</a>.</p></blockquote>";
		parseAndAssert(markup, expectedHtml);
	}

	/*
	 * Inline image syntax
	 */
	public void testInlineImageWithTitle() {
		String markup = "![Alt text](/path/to/img.jpg \"Optional title\")";
		String expectedHtml = "<p><img alt=\"Alt text\" title=\"Optional title\" border=\"0\" src=\"/path/to/img.jpg\"/></p>";
		parseAndAssert(markup, expectedHtml);
	}

	public void testInlineImageWithoutTitle() {
		String markup = "![Alt text](/path/to/img.jpg)";
		String expectedHtml = "<p><img alt=\"Alt text\" border=\"0\" src=\"/path/to/img.jpg\"/></p>";
		parseAndAssert(markup, expectedHtml);
	}

	public void testNoSpanWithinInlineImage() throws Exception {
		String markup = "![Alt text](/path/to/my_nice_image.jpg)";
		String expectedHtml = "<p><img alt=\"Alt text\" border=\"0\" src=\"/path/to/my_nice_image.jpg\"/></p>";
		parseAndAssert(markup, expectedHtml);
	}

	/*
	 * Reference-style image syntax
	 */
	public void testReferenceStyleImage() {
		String markup = "![Alt text][id]\n\n[id]: url/to/image  \"Optional title attribute\"";
		String expectedHtml = "<p><img alt=\"Alt text\" title=\"Optional title attribute\" border=\"0\" src=\"url/to/image\"/></p>";
		parseAndAssert(markup, expectedHtml);
	}

	public void testNoSpanWithinReferenceStyleImage() {
		String markup = "![Alt text][id]\n\n[id]: /path/to/my_nice_image.jpg";
		String expectedHtml = "<p><img alt=\"Alt text\" border=\"0\" src=\"/path/to/my_nice_image.jpg\"/></p>";
		parseAndAssert(markup, expectedHtml);
	}

	/*
	 * Emphasis. Markdown treats asterisks * and underscores _ as indicators of emphasis. Text wrapped with one * or _ will be
	 * wrapped with an HTML em tag; double **'s or __ will be wrapped with an HTML strong tag.
	 */
	public void testEmphasisWithAsterisks() {
		parseAndAssert("*foo bar*", "<p><em>foo bar</em></p>");
	}

	public void testEmphasisWithUnderscore() {
		parseAndAssert("_foo bar_", "<p><em>foo bar</em></p>");
	}

	public void testStrongWithAsterisks() {
		parseAndAssert("**foo bar**", "<p><strong>foo bar</strong></p>");
	}

	public void testStrongWithUnderscore() {
		parseAndAssert("__foo bar__", "<p><strong>foo bar</strong></p>");
	}

	/*
	 * Emphasis can be used in the middle of a word.
	 */
	public void testEmphasisWithinWord() {
		parseAndAssert("un*frigging*believable", "<p>un<em>frigging</em>believable</p>");
	}

	/*
	 * But if you surround an * or _ with spaces, it'll be treated as a literal asterisk or underscore.
	 */
	public void testLiteralAsteriskAndUnderscore() {
		parseAndAssert("asterisk * underscore _", "<p>asterisk * underscore _</p>");
	}

	/*
	 * To produce a literal asterisk or underscore at a position where it would otherwise be used as an emphasis
	 * delimiter, you can backslash escape it.
	 */
	public void testEscapesAsterisk() {
		parseAndAssert("\\*foo bar\\*", "<p>*foo bar*</p>");
	}

	public void testEscapesUnderscore() {
		parseAndAssert("\\_foo bar\\_", "<p>_foo bar_</p>");
	}

	public void testEscapesDoubleAsterisk() {
		parseAndAssert("\\**foo bar\\**", "<p>**foo bar**</p>");
	}

	public void testEscapesDoubleUnderscore() {
		parseAndAssert("\\__foo bar\\__", "<p>__foo bar__</p>");
	}

	/*
	 * Code. To indicate a span of code, wrap it with backtick quotes.
	 */
	public void testCodeSpan() {
		String markup = "Use the `printf()` function.";
		String expectedHtml = "<p>Use the <code>printf()</code> function.</p>";
		parseAndAssert(markup, expectedHtml);
	}

	/*
	 * To include a literal backtick character within a code span, you can use multiple backticks as the opening and
	 * closing delimiters.
	 */
	public void testLiteralBacktickInCodeSpan() {
		String markup = "``There is a literal backtick (`) here.``";
		String expectedHtml = "<p><code>There is a literal backtick (`) here.</code></p>";
		parseAndAssert(markup, expectedHtml);
	}

	/*
	 * The backtick delimiters surrounding a code span may include spaces - one after the opening, one before the
	 * closing. This allows you to place literal backtick characters at the beginning or end of a code span:
	 */
	public void testLiteralBacktickAtBeginnionOrIndOfCodeSpan() {
		parseAndAssert("`` `foo` ``", "<p><code>`foo`</code></p>");
	}

	/*
	 * With a code span, ampersands and angle brackets are encoded as HTML entities automatically
	 */
	public void testCodeSpanEncodesAmpersandsAndAngleBrackets() {
		String markup = "`Encode tags <p> and enties &code;`";
		String expectedHtml = "<p><code>Encode tags &lt;p&gt; and enties &code;</code></p>";
		parseAndAssert(markup, expectedHtml);
	}

	public void testNoProcessingInCodeSpan() {
		String markup = "`Preserve *asterisk*.`";
		String expectedHtml = "<p><code>Preserve *asterisk*.</code></p>";
		parseAndAssert(markup, expectedHtml);
	}

}
