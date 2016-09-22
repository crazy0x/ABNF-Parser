//
// Copyright 2013 Mike Friesen
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
//

package ca.gobits.bnf.tokenizer;

import java.util.Stack;

import ca.gobits.bnf.tokenizer.BNFToken.BNFTokenType;

/**
 * Implementation of the BNFTokenizerFactory.
 */
public class BNFTokenizerFactoryImpl implements BNFTokenizerFactory {

    /** CHARACTER_SYMBOL29. */
    private static final int CHARACTER_SYMBOL29 = 0xFFFF;
    /** CHARACTER_SYMBOL28. */
    private static final int CHARACTER_SYMBOL28 = 0xFF00;
    /** CHARACTER_SYMBOL27. */
    private static final int CHARACTER_SYMBOL27 = 0xFE6F;
    /** CHARACTER_SYMBOL26. */
    private static final int CHARACTER_SYMBOL26 = 0xFE30;
    /** CHARACTER_SYMBOL25. */
    private static final int CHARACTER_SYMBOL25 = 0x4DFF;
    /** CHARACTER_SYMBOL24. */
    private static final int CHARACTER_SYMBOL24 = 0x4DC0;
    /** CHARACTER_SYMBOL23. */
    private static final int CHARACTER_SYMBOL23 = 0x33FF;
    /** CHARACTER_SYMBOL22. */
    private static final int CHARACTER_SYMBOL22 = 0x3200;
    /** CHARACTER_SYMBOL21. */
    private static final int CHARACTER_SYMBOL21 = 0x303F;
    /** CHARACTER_SYMBOL20. */
    private static final int CHARACTER_SYMBOL20 = 0x3000;
    /** CHARACTER_SYMBOL19. */
    private static final int CHARACTER_SYMBOL19 = 0x2E7F;
    /** CHARACTER_SYMBOL18. */
    private static final int CHARACTER_SYMBOL18 = 0x2E00;
    /** CHARACTER_SYMBOL17. */
    private static final int CHARACTER_SYMBOL17 = 0x2BFF;
    /** CHARACTER_SYMBOL16. */
    private static final int CHARACTER_SYMBOL16 = 0x2000;
    /** CHARACTER_SYMBOL15. */
    private static final int CHARACTER_SYMBOL15 = 0x19FF;
    /** CHARACTER_SYMBOL14. */
    private static final int CHARACTER_SYMBOL14 = 0x19E0;
    /** CHARACTER_SYMBOL13. */
    private static final int CHARACTER_SYMBOL13 = 0xFF;
    /** CHARACTER_SYMBOL12. */
    private static final int CHARACTER_SYMBOL12 = 0xC0;
    /** CHARACTER_SYMBOL11. */
    private static final int CHARACTER_SYMBOL11 = 191;
    /** CHARACTER_SYMBOL10. */
    private static final int CHARACTER_SYMBOL10 = 123;
    /** CHARACTER_SYMBOL9. */
    private static final int CHARACTER_SYMBOL9 = 96;
    /** CHARACTER_SYMBOL8. */
    private static final int CHARACTER_SYMBOL8 = 91;
    /** CHARACTER_BACKWARD_SLASH. */
    private static final int CHARACTER_BACKWARD_SLASH = 92;
    /** CHARACTER_SYMBOL7. */
    private static final int CHARACTER_SYMBOL7 = 63;
    /** CHARACTER_SYMBOL6. */
    private static final int CHARACTER_SYMBOL6 = 58;
    /** CHARACTER_SYMBOL_COMMA. */
    private static final int CHARACTER_SYMBOL_COMMA = 44;
    /** CHARACTER_SYMBOL_STAR. */
    private static final int CHARACTER_SYMBOL_STAR = 42;
    /** CHARACTER_SYMBOL5. */
    private static final int CHARACTER_SYMBOL5 = 41;
    /** CHARACTER_SYMBOL4. */
    private static final int CHARACTER_SYMBOL4 = 40;
    /** CHARACTER_SYMBOL3. */
    private static final int CHARACTER_SYMBOL3 = 38;
    /** CHARACTER_SYMBOL2. */
    private static final int CHARACTER_SYMBOL2 = 36;
    /** CHARACTER_SYMBOL1. */
    private static final int CHARACTER_SYMBOL1 = 33;
    /** CHARACTER_SPACE. */
    private static final int CHARACTER_SPACE = 32;
    /** CHARACTER_NON_PRINTABLE_END. */
    private static final int CHARACTER_NON_PRINTABLE_END = 31;
    /** CHARACTER_NON_PRINTABLE_START. */
    private static final int CHARACTER_NON_PRINTABLE_START = 0;
    /** CHARACTER_CARRIAGE_RETURN. */
    private static final int CHARACTER_CARRIAGE_RETURN = 13;
    /** CHARACTER_NEWLINE. */
    private static final int CHARACTER_NEWLINE = 10;

    /**
     * @param text - string
     * @return BNFToken - token;
     */
    @Override
    public BNFToken tokens(final String text) {
        return tokens(text, new BNFTokenizerParams());
    }

    /**
     * @param text - string
     * @param params - BNFTokenizerParams
     * @return BNFToken - token;
     */
    @Override
    public BNFToken tokens(final String text, final BNFTokenizerParams params) {

        Stack<BNFToken> stack = new Stack<BNFToken>();
        BNFFastForward ff = new BNFFastForward();

        BNFTokenizerType lastType = BNFTokenizerType.NONE;

        int len = text.length();

        for (int i = 0; i < len; i++) {

            char c = text.charAt(i);
            BNFTokenizerType type = getType(c, lastType);

            if (ff.isActive()) {

                ff.appendIfActive(c);

                boolean isFastForwardComplete = ff.isComplete(type, lastType, i, len);

                if (isFastForwardComplete) {

                    finishFastForward(stack, ff);
                    ff.complete();
                }

            } else {

                calculateFastForward(ff, type, stack, lastType);

                if (ff.isActive()) {

                    ff.appendIfActive(c);

                } else if (includeText(type, params)) {

                    if (isAppendable(lastType, type)) {

                        stack.peek().appendValue(c);

                    } else {
                        addBNFToken(stack, type, c);
                    }
                }
            }

            lastType = type;
        }

        return !stack.isEmpty() ? stack.firstElement() : new BNFToken("");
    }

    /**
     * Whether to include the text while parsing.
     * @param type - BNFTokenizerType
     * @param params - BNFTokenizerParams
     * @return boolean
     */
    private boolean includeText(final BNFTokenizerType type, final BNFTokenizerParams params) {
        return (params.isIncludeWhitespace() && type == BNFTokenizerType.WHITESPACE)
                || (params.isIncludeWhitespaceOther() && type == BNFTokenizerType.WHITESPACE_OTHER)
                || (params.isIncludeWhitespaceNewlines() && type == BNFTokenizerType.WHITESPACE_NEWLINE)
                || !isWhitespace(type);
    }

    /**
     * @param ff -
     * @param type -
     * @param stack -
     * @param lastType -
     */
    private void calculateFastForward(final BNFFastForward ff, final BNFTokenizerType type, final Stack<BNFToken> stack, final BNFTokenizerType lastType) {

        BNFToken last = !stack.isEmpty() ? stack.peek() : null;
        ff.setStart(BNFTokenizerType.NONE);

        // single line comment
        if (lastType == BNFTokenizerType.SYMBOL_FORWARD_SLASH && type == BNFTokenizerType.SYMBOL_FORWARD_SLASH) {

            ff.setStart(BNFTokenizerType.COMMENT_SINGLE_LINE);
            ff.setEnd(new BNFTokenizerType[] {BNFTokenizerType.WHITESPACE_NEWLINE});

            BNFToken token = stack.pop();
            ff.appendIfActive(token.getStringValue());

            // multi line comment
        } else if (lastType == BNFTokenizerType.SYMBOL_FORWARD_SLASH && type == BNFTokenizerType.SYMBOL_STAR) {

            ff.setStart(BNFTokenizerType.COMMENT_MULTI_LINE);
            ff.setEnd(new BNFTokenizerType[] {BNFTokenizerType.SYMBOL_FORWARD_SLASH, BNFTokenizerType.SYMBOL_STAR});

            BNFToken token = stack.pop();
            ff.appendIfActive(token.getStringValue());

        } else if (type == BNFTokenizerType.QUOTE_DOUBLE) {

            ff.setStart(BNFTokenizerType.QUOTE_DOUBLE);
            ff.setEnd(BNFTokenizerType.QUOTE_DOUBLE);

        } else if (type == BNFTokenizerType.QUOTE_SINGLE && !isWord(last)) {

            ff.setStart(BNFTokenizerType.QUOTE_SINGLE);
            ff.setEnd(BNFTokenizerType.QUOTE_SINGLE);
        }
    }

    /**
     * @param last - BNFToken
     * @return boolean
     */
    private boolean isWord(final BNFToken last) {
        return last != null && last.isWord();
    }

    /**
     * @param stack -
     * @param ff -
     */
    private void finishFastForward(final Stack<BNFToken> stack, final BNFFastForward ff) {

        if (isComment(ff.getStart())) {

            setNextToken(stack, null);

        } else {

            addBNFToken(stack, ff.getStart(), ff.getString());
        }
    }

    /**
     * @param stack -
     * @param type -
     * @param c -
     */
    private void addBNFToken(final Stack<BNFToken> stack, final BNFTokenizerType type, final char c) {
        addBNFToken(stack, type, String.valueOf(c));
    }

    /**
     * @param stack -
     * @param type -
     * @param c -
     */
    private void addBNFToken(final Stack<BNFToken> stack, final BNFTokenizerType type, final String c) {

        BNFToken token = createBNFToken(c, type);

        if (!stack.isEmpty()) {
            BNFToken peek = stack.peek();
            peek.setNextToken(token);
            token.setId(peek.getId() + 1);
            token.setPreviousToken(peek);
        } else {
            token.setId(1);
        }

        stack.push(token);
    }

    /**
     * @param stack -
     * @param nextToken -
     */
    private void setNextToken(final Stack<BNFToken> stack, final BNFToken nextToken) {
        if (!stack.isEmpty()) {
            stack.peek().setNextToken(nextToken);
        }
    }

    /**
     * @param lastType -
     * @param current -
     * @return boolean
     */
    private boolean isAppendable(final BNFTokenizerType lastType, final BNFTokenizerType current) {
        return lastType == current && (current == BNFTokenizerType.LETTER || current == BNFTokenizerType.NUMBER);
    }

    /**
     * @param value -
     * @param type -
     * @return BNFToken
     */
    private BNFToken createBNFToken(final String value, final BNFTokenizerType type) {

        BNFToken token = new BNFToken();
        token.setStringValue(value);

        if (isComment(type)) {
            token.setType(BNFTokenType.COMMENT);
        } else if (isNumber(type)) {
            token.setType(BNFTokenType.NUMBER);
        } else if (isLetter(type)) {
            token.setType(BNFTokenType.WORD);
        } else if (isSymbol(type)) {
            token.setType(BNFTokenType.SYMBOL);
        } else if (type == BNFTokenizerType.WHITESPACE_NEWLINE) {
            token.setType(BNFTokenType.WHITESPACE_NEWLINE);
        } else if (isWhitespace(type)) {
            token.setType(BNFTokenType.WHITESPACE);
        } else if (isQuote(type)) {
            token.setType(BNFTokenType.QUOTED_STRING);
        }

        return token;
    }

    /**
     * @param type -
     * @return boolean
     */
    private boolean isQuote(final BNFTokenizerType type) {
        return type == BNFTokenizerType.QUOTE_DOUBLE || type == BNFTokenizerType.QUOTE_SINGLE;
    }

    /**
     * @param type -
     * @return boolean
     */
    private boolean isSymbol(final BNFTokenizerType type) {
        return type == BNFTokenizerType.SYMBOL
                || type == BNFTokenizerType.SYMBOL_HASH
                || type == BNFTokenizerType.SYMBOL_AT
                || type == BNFTokenizerType.SYMBOL_STAR
                || type == BNFTokenizerType.SYMBOL_FORWARD_SLASH
                || type == BNFTokenizerType.SYMBOL_BACKWARD_SLASH;
    }

    /**
     * @param type -
     * @return boolean
     */
    private boolean isWhitespace(final BNFTokenizerType type) {
        return type == BNFTokenizerType.WHITESPACE || type == BNFTokenizerType.WHITESPACE_OTHER || type == BNFTokenizerType.WHITESPACE_NEWLINE;
    }

    /**
     * @param type -
     * @return boolean
     */
    private boolean isComment(final BNFTokenizerType type) {
        return type == BNFTokenizerType.COMMENT_MULTI_LINE
                || type == BNFTokenizerType.COMMENT_SINGLE_LINE;
    }

    /**
     * @param type -
     * @return boolean
     */
    private boolean isNumber(final BNFTokenizerType type) {
        return type == BNFTokenizerType.NUMBER;
    }

    /**
     * @param type -
     * @return boolean
     */
    private boolean isLetter(final BNFTokenizerType type) {
        return type == BNFTokenizerType.LETTER;
    }

    /**
     * @param c -
     * @param lastType -
     * @return BNFTokenizerType
     */
    private BNFTokenizerType getType(final int c, final BNFTokenizerType lastType) {
        if (c == CHARACTER_NEWLINE || c == CHARACTER_CARRIAGE_RETURN) {
            return BNFTokenizerType.WHITESPACE_NEWLINE;
        } else if (c >= CHARACTER_NON_PRINTABLE_START && c <= CHARACTER_NON_PRINTABLE_END) {
            return BNFTokenizerType.WHITESPACE_OTHER;
        } else if (c == CHARACTER_SPACE) {
            return BNFTokenizerType.WHITESPACE;
        } else if (c == CHARACTER_SYMBOL1) {
            return BNFTokenizerType.SYMBOL;
        } else if (c == '"') { // From: 34 to: 34 From:0x22 to:0x22
            return lastType == BNFTokenizerType.SYMBOL_BACKWARD_SLASH ? BNFTokenizerType.QUOTE_DOUBLE_ESCAPED : BNFTokenizerType.QUOTE_DOUBLE;
        } else if (c == '#') { // From: 35 to: 35 From:0x23 to:0x23
            return BNFTokenizerType.SYMBOL_HASH;
        } else if (c >= CHARACTER_SYMBOL2 && c <= CHARACTER_SYMBOL3) {
            return BNFTokenizerType.SYMBOL;
        } else if (c == '\'') { // From: 39 to: 39 From:0x27 to:0x27
            return lastType == BNFTokenizerType.SYMBOL_BACKWARD_SLASH ? BNFTokenizerType.QUOTE_SINGLE_ESCAPED : BNFTokenizerType.QUOTE_SINGLE;
        } else if (c >= CHARACTER_SYMBOL4 && c <= CHARACTER_SYMBOL5) {
            return BNFTokenizerType.SYMBOL;
        } else if (c == CHARACTER_SYMBOL_STAR) {
            return BNFTokenizerType.SYMBOL_STAR;
        } else if (c == '+') { // From: 43 to: 43 From:0x2B to:0x2B
            return BNFTokenizerType.SYMBOL;
        } else if (c == CHARACTER_SYMBOL_COMMA) {
            return BNFTokenizerType.SYMBOL;
        } else if (c == '-') { // From: 45 to: 45 From:0x2D to:0x2D
            return BNFTokenizerType.NUMBER;
        } else if (c == '.') { // From: 46 to: 46 From:0x2E to:0x2E
            return BNFTokenizerType.NUMBER;
        } else if (c == '/') { // From: 47 to: 47 From:0x2F to:0x2F
            return BNFTokenizerType.SYMBOL_FORWARD_SLASH;
        } else if (c >= '0' && c <= '9') { // From: 48 to: 57 From:0x30 to:0x39
            return BNFTokenizerType.NUMBER;
        } else if (c >= CHARACTER_SYMBOL6 && c <= CHARACTER_SYMBOL7) {
            return BNFTokenizerType.SYMBOL;
        } else if (c == '@') { // From: 64 to: 64 From:0x40 to:0x40
            return BNFTokenizerType.SYMBOL_AT;
        } else if (c >= 'A' && c <= 'Z') { // From: 65 to: 90 From:0x41 to:0x5A
            return BNFTokenizerType.LETTER;
        } else if (c == CHARACTER_BACKWARD_SLASH) { // /
            return BNFTokenizerType.SYMBOL_BACKWARD_SLASH;
        } else if (c >= CHARACTER_SYMBOL8 && c <= CHARACTER_SYMBOL9) {
            return BNFTokenizerType.SYMBOL;
        } else if (c >= 'a' && c <= 'z') { // From: 97 to:122 From:0x61 to:0x7A
            return BNFTokenizerType.LETTER;
        } else if (c >= CHARACTER_SYMBOL10 && c <= CHARACTER_SYMBOL11) {
            return BNFTokenizerType.SYMBOL;
        } else if (c >= CHARACTER_SYMBOL12 && c <= CHARACTER_SYMBOL13) { // From:192 to:255 From:0xC0 to:0xFF
            return BNFTokenizerType.LETTER;
        } else if (c >= CHARACTER_SYMBOL14 && c <= CHARACTER_SYMBOL15) { // khmer symbols
            return BNFTokenizerType.SYMBOL;
        } else if (c >= CHARACTER_SYMBOL16 && c <= CHARACTER_SYMBOL17) { // various symbols
            return BNFTokenizerType.SYMBOL;
        } else if (c >= CHARACTER_SYMBOL18 && c <= CHARACTER_SYMBOL19) { // supplemental punctuation
            return BNFTokenizerType.SYMBOL;
        } else if (c >= CHARACTER_SYMBOL20 && c <= CHARACTER_SYMBOL21) { // cjk symbols & punctuation
            return BNFTokenizerType.SYMBOL;
        } else if (c >= CHARACTER_SYMBOL22 && c <= CHARACTER_SYMBOL23) { // enclosed cjk letters and months, cjk compatibility
            return BNFTokenizerType.SYMBOL;
        } else if (c >= CHARACTER_SYMBOL24 && c <= CHARACTER_SYMBOL25) { // yijing hexagram symbols
            return BNFTokenizerType.SYMBOL;
        } else if (c >= CHARACTER_SYMBOL26 && c <= CHARACTER_SYMBOL27) { // cjk compatibility forms, small form variants
            return BNFTokenizerType.SYMBOL;
        } else if (c >= CHARACTER_SYMBOL28 && c <= CHARACTER_SYMBOL29) { // hiragana & katakana halfwitdh & fullwidth forms, Specials
            return BNFTokenizerType.SYMBOL;
        } else {
            return BNFTokenizerType.LETTER;
        }
    }
}