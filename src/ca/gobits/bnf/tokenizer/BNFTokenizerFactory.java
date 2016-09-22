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
 * BNFTokenizerFactory creates BNFTokens from strings.
 */
public interface BNFTokenizerFactory {

    /** BNFTokenizerTypes. */
    public enum BNFTokenizerType {
        /** NONE. */
        NONE,
        /** COMMENT_SINGLE_LINE. */
        COMMENT_SINGLE_LINE,
        /** COMMENT_MULTI_LINE. */
        COMMENT_MULTI_LINE,
        /** QUOTE_SINGLE. */
        QUOTE_SINGLE,
        /** QUOTE_SINGLE_ESCAPED. */
        QUOTE_SINGLE_ESCAPED,
        /** QUOTE_DOUBLE. */
        QUOTE_DOUBLE,
        /** QUOTE_DOUBLE_ESCAPED. */
        QUOTE_DOUBLE_ESCAPED,
        /** NUMBER. */
        NUMBER,
        /** LETTER. */
        LETTER,
        /** SYMBOL. */
        SYMBOL,
        /** SYMBOL_HASH. */
        SYMBOL_HASH,
        /** SYMBOL_AT. */
        SYMBOL_AT,
        /** SYMBOL_STAR. */
        SYMBOL_STAR,
        /** SYMBOL_FORWARD_SLASH. */
        SYMBOL_FORWARD_SLASH,
        /** SYMBOL_BACKWARD_SLASH. */
        SYMBOL_BACKWARD_SLASH,
        /** WHITESPACE. */
        WHITESPACE,
        /** WHITESPACE_OTHER. */
        WHITESPACE_OTHER,
        /** WHITESPACE_NEWLINE. */
        WHITESPACE_NEWLINE
    }

    /**
     * creates BNFTokens from a string.
     * @param text - string
     * @return BNFToken - token
     */
    BNFToken tokens(String text);

    /**
     * creates BNFTokens from a string with BNFTokenizerParams.
     * @param text - string
     * @param params - BNFTokenizerParams
     * @return BNFToken - token
     */
    BNFToken tokens(String text, BNFTokenizerParams params);
}
