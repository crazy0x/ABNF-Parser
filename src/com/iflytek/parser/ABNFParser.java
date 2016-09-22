package com.iflytek.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.gobits.bnf.parser.BNFSlot;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class ABNFParser {
	
	public static JSONObject parse4ABNF(String grammarStr) {
		// 讯飞语法的slot list
        JSONObject rs = new JSONObject();
        JSONObject semantic = new JSONObject();
        JSONObject slot = new JSONObject();
        semantic.put("slots", slot);
        rs.put("rc", 0);
        rs.put("semantic", semantic);
        
        JSONObject grammar = JSONObject.parseObject(grammarStr);
        List<BNFSlot> list = assembleGrammarList(grammar, rs);
        
        boolean isMatch = false;
        
        List<Map<String, List<String>>> ABNF = ABNFProcessor.INSTANCE.parsedABNF;

        Map<String, BNFSlot> slotMap = new HashMap<String, BNFSlot>();
        String mainKey = "";
        List<String> mainValue = new ArrayList<String>();

        //读取bnf文件到缓存
		for (Map<String, List<String>> result : ABNF) {
        	for(Map.Entry<String, List<String>> entry : result.entrySet()) {
        		if(entry.getKey().contains("$") && !entry.getKey().contains("main")) {
        			BNFSlot sl = new BNFSlot(entry.getKey() , entry.getValue());
        			slotMap.put(sl.getBnfkey(), sl);
        		} else if(entry.getKey().contains("main")) {
        			mainKey = entry.getKey();
        			mainValue = entry.getValue();
        		}
        	}
        	
        	//解析匹配并输出json
        	isMatch = isMatch(list, mainValue);
        	if(isMatch) {
        		rs.put("service", mainKey.replace("$main{biz:", "").replace("}", ""));
        		for(BNFSlot bnfSlot : list){
        			BNFSlot s = slotMap.get(bnfSlot.getJsonkey());
        			if (s != null) {
        				if (s.getJsonkey().equals("operation")) {
        					rs.put("operation", s.getJsonvalue());
        				} else {
        					if(s.getJsonkey().equals("num") || s.getJsonkey().equals("walkbai") || s.getJsonkey().equals("walkshi")) {
        						String num = (String) (slot.get("num") == null ? "" : slot.get("num"));
        						slot.put("num", num + bnfSlot.getJsonvalue());
        					} else {
        						slot.put(s.getJsonkey() == null ? bnfSlot.getJsonkey() : s.getJsonkey(), bnfSlot.getJsonvalue());
        					}
        				}
        			}
        		}
        		break;
        	}
        }

        System.out.println("isMatch = " + isMatch);
        System.out.println("rs = " + rs);
        return rs;
	}
	
	private static boolean isMatch(List<BNFSlot> list, List<String> mainValue) {
        boolean isMatch = false;
        for(String str : mainValue) {
            int match = 0;
            for (BNFSlot slot: list) {
                if(str.contains(slot.getJsonkey())){
                    match++;
                }
            }
            if(match == list.size()){
                isMatch = true;
            }
        }
        return isMatch;
    }

    private static List<BNFSlot> assembleGrammarList(JSONObject grammar, JSONObject rs) {
        JSONArray array = (JSONArray) grammar.get("ws");
        String text = "";
        List<BNFSlot> list = new ArrayList<BNFSlot>();
        for(int i = 0; i < array.size(); i++) {
            BNFSlot slot = new BNFSlot();
            slot.setJsonkey(((JSONObject) array.get(i)).getString("slot").replace("<", "").replace(">", ""));
            String w = ((JSONObject) array.get(i)).getJSONArray("cw").getJSONObject(0).getString("w");
            slot.setJsonvalue(w);
            list.add(slot);
            text = text + w;
        }
        rs.put("text", text);
        return list;
    }
}
