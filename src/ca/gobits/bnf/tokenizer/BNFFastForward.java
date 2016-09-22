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

import ca.gobits.bnf.tokenizer.BNFTokenizerFactory.BNFTokenizerType;

/**
 * BNFFastForward class.
 *
 */
public class BNFFastForward {

    /** Start Token Match. */
    private BNFTokenizerType start = BNFTokenizerType.NONE;

    /** End Tokens Match. */
    private BNFTokenizerType[] end = new BNFTokenizerType[] {BNFTokenizerType.NONE};

    /** String holder. */
    private final StringBuilder sb = new StringBuilder();

    /**
     * default constructor.
     */
    public BNFFastForward() {
    }

    /**
     * @return BNFTokenizerType
     */
    public BNFTokenizerType getStart() {
        return this.start;
    }

    /**
     * @param type - start BNFTokenizerType
     */
    public void setStart(final BNFTokenizerType type) {
        this.start = type;
    }

    /**
     * @return BNFTokenizerType[]
     */
    public BNFTokenizerType[] getEnd() {
        return this.end;
    }

    /**
     * @param type -
     */
    public void setEnd(final BNFTokenizerType[] type) {
        this.end = type;
    }

    /**
     * @param type -
     */
    public void setEnd(final BNFTokenizerType type) {
        this.end = new BNFTokenizerType[] {type};
    }

    /**
     * @return boolean
     */
    public boolean isActive() {
        return this.start != BNFTokenizerType.NONE;
    }

    /**
     * @param type -
     * @param lastType -
     * @param i -
     * @param len -
     * @return boolean
     */
    public boolean isComplete(final BNFTokenizerType type, final BNFTokenizerType lastType, final int i, final int len) {
        return isMatch(type, lastType) || (i == len - 1);
    }

    /**
     * @param type -
     * @param lastType -
     * @return boolean
     */
    private boolean isMatch(final BNFTokenizerType type, final BNFTokenizerType lastType) {

        boolean match = false;

        if (this.end.length == 1) {
            match = type == this.end[0];
        } else if (this.end.length == 2) {
            match = type == this.end[0] && lastType == this.end[1];
        }

        return match;
    }

    /**
     * complete fast forward.
     */
    public void complete() {
        this.start = BNFTokenizerType.NONE;
        setEnd(BNFTokenizerType.NONE);
        this.sb.delete(0, this.sb.length());
    }

    /**
     * Append character if fastforward is active.
     * @param c -
     */
    public void appendIfActive(final char c) {
        if (isActive()) {
            this.sb.append(String.valueOf(c));
        }
    }

    /**
     * Append stirng if fastforward is active.
     * @param s -
     */
    public void appendIfActive(final String s) {
        if (isActive()) {
            this.sb.append(s);
        }
    }

    /**
     * @return String
     */
    public String getString() {
        return this.sb.toString();
    }
}