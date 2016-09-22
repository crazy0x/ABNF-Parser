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

/**
 * BNFToken holder for the result from the tokenizer.
 */
public class BNFToken {

    /**
     * Type of tokens.
     */
    public enum BNFTokenType {
        /** COMMENT. */
        COMMENT,
        /** QUOTED_STRING. */
        QUOTED_STRING,
        /** NUMBER. */
        NUMBER,
        /** WORD. */
        WORD,
        /** SYMBOL. */
        SYMBOL,
        /** WHITESPACE. */
        WHITESPACE,
        /** WHITESPACE_NEWLINE. */
        WHITESPACE_NEWLINE
    }

    /** unique token identifier. */
    private int id;

    /** string value of token. */
    private String stringValue;

    /** type of token. */
    private BNFTokenType tokenType;

    /** next token in line. */
    private BNFToken nextToken;

    /** previous token. */
    private BNFToken previousToken;

    /**
     * default constructor.
     */
    public BNFToken() {
    }

    /**
     * constructor with string.
     * @param value -
     */
    public BNFToken(final String value) {
        this.stringValue = value;
    }

    /**
     * Append value to token.
     * @param c -
     */
    public void appendValue(final char c) {
        this.stringValue = this.stringValue + String.valueOf(c);
    }

    /**
     * @return String
     */
    public String getStringValue() {
        return this.stringValue;
    }

    /**
     * @param value -
     */
    public void setStringValue(final String value) {
        this.stringValue = value;
    }

    /**
     * @return BNFToken
     */
    public BNFToken getNextToken() {
        return this.nextToken;
    }

    /**
     * @param token -
     */
    public void setNextToken(final BNFToken token) {
        this.nextToken = token;
    }

    @Override
    public String toString() {
        return "TOKEN value: " + getStringValue() + " id: " + getId()
                + " type: " + getType();
    }

    /**
     * @return boolean
     */
    public boolean isSymbol() {
        return this.tokenType == BNFTokenType.SYMBOL;
    }

    /**
     * @return boolean
     */
    public boolean isWord() {
        return this.tokenType == BNFTokenType.WORD;
    }

    /**
     * @return boolean
     */
    public boolean isQuotedString() {
        return this.tokenType == BNFTokenType.QUOTED_STRING;
    }

    /**
     * @return boolean
     */
    public boolean isNumber() {
        return this.tokenType == BNFTokenType.NUMBER;
    }

    /**
     * @return boolean
     */
    public boolean isComment() {
        return this.tokenType == BNFTokenType.COMMENT;
    }

    /**
     * @return boolean
     */
    public boolean isWhitespace() {
        return this.tokenType == BNFTokenType.WHITESPACE;
    }

    /**
     * @return int
     */
    public int getId() {
        return this.id;
    }

    /**
     * @param identifier -
     */
    public void setId(final int identifier) {
        this.id = identifier;
    }

    /**
     * @return BNFTokenType
     */
    public BNFTokenType getType() {
        return this.tokenType;
    }

    /**
     * @param type -
     */
    public void setType(final BNFTokenType type) {
        this.tokenType = type;
    }

    /**
     * @return BNFToken
     */
    public BNFToken getPreviousToken() {
        return this.previousToken;
    }

    /**
     * @param token -
     */
    public void setPreviousToken(final BNFToken token) {
        this.previousToken = token;
    }
}
