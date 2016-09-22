package com.iflytek.parser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ca.gobits.bnf.parser.BNFSequenceFactory;
import ca.gobits.bnf.parser.BNFSequenceFactoryImpl;

public enum ABNFProcessor {

	INSTANCE;

	public List<Map<String, List<String>>> parsedABNF;

	public void init(String path) {
		parsedABNF = new ArrayList<Map<String, List<String>>>();
		readfile(path);
	}

	/**
	 * 读取某个文件夹下的所有文件
	 */
	private boolean readfile(String filepath){
		try {
			BNFSequenceFactory factory = new BNFSequenceFactoryImpl();
			File file = new File(filepath);
			if (!file.isDirectory()) {
				parsedABNF.add(factory.map(file.getPath()));

			} else if (file.isDirectory()) {
				String[] filelist = file.list();
				for (int i = 0; i < filelist.length; i++) {
					File readfile = new File(filepath + "/" + filelist[i]);
					if (!readfile.isDirectory()) {
						parsedABNF.add(factory.map(readfile.getPath()));
					} else if (readfile.isDirectory()) {
						readfile(filepath + "/" + filelist[i]);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("readfile() Exception:" + e.getMessage());
		}
		return true;
	}

}
