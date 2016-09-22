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

package ca.gobits.bnf.parser;

/**
 * BNFSymbol - holder class for name or repetition.
 */
public class BNFSymbol {

    /** BNF Repetition. */
    public enum BNFRepetition {
        /** NONE, ZERO_OR_MORE. */
        NONE, ZERO_OR_MORE
    }

    /** name of symbol. */
    private String name;

    /** repetition of symbol. */
    private BNFRepetition repetition;

    /**
     * default constructor.
     */
    public BNFSymbol() {
        this.repetition = BNFRepetition.NONE;
    }

    /**
     * constructor with name.
     * @param original - name of symbol
     */
    public BNFSymbol(final String original) {
        this();
        this.name = original;
    }

    /**
     * constructor.
     * @param original - name of symbol
     * @param rep - repetition of symbol
     */
    public BNFSymbol(final String original, final BNFRepetition rep) {
        this(original);
        this.repetition = rep;
    }

    /**
     * @return String - name of symbol
     */
    public String getName() {
        return this.name;
    }

    /**
     * @return BNFRepetition - repetition of symbol
     */
    public BNFRepetition getRepetition() {
        return this.repetition;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
