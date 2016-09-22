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
 * BNFTokenizerParams.
 *
 * Parameters you can pass into the tokenizer
 */
public class BNFTokenizerParams {

    /** whether to include whitespace. */
    private boolean includeWhitespace;

    /** whether to include other whitespace. */
    private boolean includeWhitespaceOther;

    /** whether to include whitespace newlines. */
    private boolean includeWhitespaceNewlines;

    /**
     * default constructor.
     */
    public BNFTokenizerParams() {
    }

    /**
     * @return boolean
     */
    public boolean isIncludeWhitespace() {
        return this.includeWhitespace;
    }

    /**
     * @param include -
     */
    public void setIncludeWhitespace(final boolean include) {
        this.includeWhitespace = include;
    }

    /**
     * @return boolean
     */
    public boolean isIncludeWhitespaceOther() {
        return this.includeWhitespaceOther;
    }

    /**
     * @param include -
     */
    public void setIncludeWhitespaceOther(final boolean include) {
        this.includeWhitespaceOther = include;
    }

    /**
     * @return boolean
     */
    public boolean isIncludeWhitespaceNewlines() {
        return this.includeWhitespaceNewlines;
    }

    /**
     * @param include -
     */
    public void setIncludeWhitespaceNewlines(final boolean include) {
        this.includeWhitespaceNewlines = include;
    }
}
