package ca.gobits.bnf.parser;

import java.util.List;

public class BNFSlot {

    private String jsonkey;

    private String jsonvalue;

    private String bnfkey;

    private List<String> bnfvalue;

    public BNFSlot() {

    }

    public BNFSlot(String key, List<String> value) {
        if(key.contains("{") && key.contains("}")) {
            String[] json = key.substring(key.indexOf("{") + 1, key.indexOf("}")).split("%");
            if(json.length > 1) {
                setJsonkey(json[0]);
                setJsonvalue(json[1]);
            } else {
                setJsonkey(json[0]);
                setJsonvalue(value.get(0));
            }
        }
        setBnfkey(key.split("\\{")[0].replace("$", ""));
        setBnfvalue(value);
    }

    public String getJsonkey() {
        return this.jsonkey;
    }

    public void setJsonkey(String jsonkey) {
        this.jsonkey = jsonkey;
    }

    public String getJsonvalue() {
        return this.jsonvalue;
    }

    public void setJsonvalue(String jsonvalue) {
        this.jsonvalue = jsonvalue;
    }

    public String getBnfkey() {
        return this.bnfkey;
    }

    public void setBnfkey(String bnfkey) {
        this.bnfkey = bnfkey;
    }

    public List<String> getBnfvalue() {
        return this.bnfvalue;
    }

    public void setBnfvalue(List<String> bnfvalue) {
        this.bnfvalue = bnfvalue;
    }
}
