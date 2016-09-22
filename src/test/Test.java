package test;

import com.iflytek.parser.ABNFParser;
import com.iflytek.parser.ABNFProcessor;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub 测试讯飞离线语法中的slot
		// String grammarStr =
		// "{ \n \"sn\":1, \"ls\":true, \"bg\":0, \"ed\":0, \"ws\":[{ \"bg\":0, "
		// +
		// "\"slot\":\"<commonclose>\", \"cw\":[{ \"id\":65535, \"w\":\"关闭\", \"gm\":0, "
		// +
		// "\"sc\":65 }] },{ \"bg\":0, \"slot\":\"<commonapp>\", \"cw\":[{  \"id\":65535, "
		// + "\"w\":\"拍照\", \"gm\":0, \"sc\":86 }] }], \"sc\":70 }";

		// String grammarStr =
		// "{ \"sn\":1, \"ls\":true, \"bg\":0, \"ed\":0, \"ws\":[{ \"bg\":0, \"slot\":\"<telephoneaction2>\","
		// +
		// " \"cw\":[{ \"id\":65535, \"w\":\"打电话\", \"gm\":0, \"sc\":67 }] },{ \"bg\":0, \"slot\":\"给\","
		// +
		// " \"cw\":[{ \"id\":65535, \"w\":\"给\", \"gm\":0, \"sc\":0 }] },{ \"bg\":0, \"slot\":\"<contact>\", \"cw\":[{ \"id\":65535,"
		// + "  \"w\":\"聂斌\", \"gm\":0, \"sc\":41 }] }], \"sc\":51 }";

		// String grammarStr =
		// "{ \"sn\": 1,\"ls\": true, \"bg\": 0, \"ed\": 0,  \"ws\": [{ \"bg\": 0, \"slot\": \"<cameratype1>\", \"cw\": [{ \"sc\": 81,\"w\": \"拍照\",\"id\": 65535,\"gm\": 0 }] }], \"sc\": 83}";

		String grammarStr = "{\"sn\":1,\"ls\":true,\"bg\":0,\"ed\":0,\"ws\":[{\"bg\":0,\"cw\":[{\"id\":65535,\"sc\":37,\"gm\":0,\"w\":\"向\"}],\"slot\":"
				+ "\"<walkto>\"},{\"bg\":0,\"cw\":[{\"id\":65535,\"sc\":85,\"gm\":0,\"w\":\"左\"}],\"slot\":\"<walkaction3>\"},{\"bg\":0,\"cw\":[{\"id\":"
				+ "65535,\"sc\":84,\"gm\":0,\"w\":\"转\"}],\"slot\":\"<walktype2>\"},{\"bg\":0,\"cw\":[{\"id\":65535,\"sc\":33,\"gm\":0,\"w\":\"1\"}]"
				+ ",\"slot\":\"<walkdigit>\"},{\"bg\":0,\"cw\":[{\"id\":65535,\"sc\":70,\"gm\":0,\"w\":\"百\"}],\"slot\":\"<walkbai>\"},{\"bg\":0"
				+ ",\"cw\":[{\"id\":65535,\"sc\":89,\"gm\":0,\"w\":\"2\"}],\"slot\":\"<walkdigit>\"},{\"bg\":0,\"cw\":[{\"id\":65535,\"sc\":100"
				+ ",\"gm\":0,\"w\":\"十\"}],\"slot\":\"<walkshi>\"},{\"bg\":0,\"cw\":[{\"id\":65535,\"sc\":100,\"gm\":0,\"w\":\"度\"}],\"slot\":\"<walkunit>\"}],\"sc\":67}";

		long time1 = System.currentTimeMillis();
		ABNFProcessor.INSTANCE.init("E:\\test");
		long time2 = System.currentTimeMillis();
		ABNFParser.parse4ABNF(grammarStr);
		long time3 = System.currentTimeMillis();
		
		System.out.println("init time: " + (time2 - time1) + "ms");
		System.out.println("parse time: " + (time3 - time2) + "ms");
	}
}
