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

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;

import ca.gobits.bnf.tokenizer.BNFToken;
import ca.gobits.bnf.tokenizer.BNFToken.BNFTokenType;
import ca.gobits.bnf.tokenizer.BNFTokenizerFactory;
import ca.gobits.bnf.tokenizer.BNFTokenizerFactoryImpl;
import ca.gobits.bnf.tokenizer.BNFTokenizerParams;

/**
 * PropertyParser.
 */
public class PropertyParser {

    /** instance of BNFTokenizerFactory. */
    private final BNFTokenizerFactory tokenizer = new BNFTokenizerFactoryImpl();

    /**
     * @param is -
     * @return Map<String, String>
     * @throws IOException -
     */
    public Map<String, String> parse(final InputStream is) throws IOException {
        String str = IOUtils.toString(is);
        return parse(str);
    }

    /**
     * @param str -
     * @return Map<String, String>
     */
    public Map<String, String> parse(final String str) {

        Map<String, String> map = new HashMap<String, String>();
        BNFTokenizerParams params = new BNFTokenizerParams();
        params.setIncludeWhitespace(true);
        params.setIncludeWhitespaceNewlines(true);
        BNFToken token = this.tokenizer.tokens(str, params);

        String start = "";
        StringBuilder sb = new StringBuilder();

        while (token != null) {

            if (token.getType() == BNFTokenType.WHITESPACE_NEWLINE) {

                if (hasText(start) && hasText(sb.toString())) {
                    map.put(start.trim(), sb.toString().trim());
                }

                start = "";
                sb = new StringBuilder();

            } else if (token.getStringValue().equals("=")) {

                start = sb.toString();
                sb = new StringBuilder();

            } else {

                sb.append(token.getStringValue());
            }

            token = token.getNextToken();
        }

        if (hasText(start) && hasText(sb.toString())) {
            map.put(start.trim(), sb.toString().trim());
        }

        return map;
    }

    /**
     * @param s -
     * @return boolean
     */
    private boolean hasText(final String s) {
        return s != null && s.length() > 0;
    }
}