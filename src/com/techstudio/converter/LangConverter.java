package com.techstudio.converter;
import com.techstudio.smpp.util.*;
import org.apache.log4j.*;

public class LangConverter{

/*
秨﹍笴栏
开始玩游戏
		String test = "虏癟代刚";0
		简讯测试
*/
	public static void main(String arg[]){
		Logger logger = ConnLogger.getLogger();

	/*	String test = "虏癟代刚";
		logger.info("--------------------------------------------");
		logger.info("for ms950 "+test);
		String out = LangConverter.toHexUnicodeStr(test, "MS950");
		logger.info("LangConverter.toHexUnicodeStr >> "+out+'.');*/
		logger.info(""+toHexUnicodeStr("虏癟代刚", "MS950"));
		logger.info("LangConverter.fromHexUnicodeStr >> "+LangConverter.fromHexUnicodeStr("7b808baf6d4b8bd5", "MS936")+'.');
		logger.info("LangConverter.fromHexUnicodeStr >> "+LangConverter.fromHexUnicodeStr("7c218a0a6e2c8a66", "MS936")+'.');
	/*	byte[] a = LangConverter.hexStringToBytes(out);
		try{
			String dbmsg = new String(a, "MS950");
			logger.info(dbmsg);
		}catch(Exception e){}*/
		logger.info("--------------------------------------------");
		logger.info("--------------------------------------------");
	/*	test = "开始玩游戏";
		logger.info("for ms936 "+test);
		String out3 = LangConverter.toHexUnicodeBytes(test, "MS936");
		logger.info(out3);*/
		logger.info("--------------------------------------------");
	}

//-------------------------------------------------------------------------------
	public static byte[] toUnicodeBytes(String msg, String lang){
		try{
			byte[] bytems = msg.getBytes();
			String strUnicode = new String(bytems, lang);
			return strUnicode.getBytes("UnicodeBigUnmarked");
		}catch(Exception e){}
		return null;
	}	

	public static String toUnicodeStr(String msg, String lang){
		try{
			byte[] bytems = msg.getBytes();
			String strUnicode = new String(bytems, lang);
			byte[] encodedbyte = strUnicode.getBytes("UnicodeBigUnmarked");
			return (new String(encodedbyte));
		}catch(Exception e){}
		return null;
	}	
//-------------------------------------------------------------------------------
	public static String fromUnicodeStr(String msg, String lang){
		try{
			byte[] bytems = msg.getBytes();
			String encodedStr = new String (bytems, "UnicodeBig");
			byte[] encodedBytes = encodedStr.getBytes(lang);
			return(new String(encodedBytes));			
		}catch(Exception e){}
		return null;
	}	
	
//-------------------------------------------------------------------------------
	public static String toHexUnicodeStr(String msg, String lang){
		try{
			byte[] unibytes = msg.getBytes();
			String strUnicode = new String(unibytes, lang);
			byte[] encodeBytes = strUnicode.getBytes("UnicodeBigUnmarked");
			return HexConverter.toHexStr(encodeBytes);
		}catch(Exception e){}
		return null;
	}
	
//-------------------------------------------------------------------------------
	public static String fromHexUnicodeStr(String msg, String lang){
		try{
			String output = HexConverter.fromHexStrToStr(msg);
			byte[] bytems = output.getBytes();
			String encodedStr = new String (bytems, "UnicodeBig");
			byte[] encodedBytes = encodedStr.getBytes(lang);
			output = new String(encodedBytes);
			return turnOffSpecialChars(output);			
		}catch(Exception e){}
		return null;
	}
	
//-------------------------------------------------------------------------------	
	private static String turnOffSpecialChars(String msg){
		String  turnedOffStr  = "";
		char  	turnOffChars  = '\\';
		String  specialChars  = "\\'";
		boolean spCharsExists = true;
		boolean spCharExists  = true;
		int     spCharIndex   = 0;
		int     startPosition = 0;

		msg = msg.trim();
		
		for ( int i=0; i<specialChars.length(); i++ ){
			turnedOffStr = "";
			while(true){
				spCharIndex = msg.indexOf(specialChars.charAt(i));
				
				if(spCharIndex == -1)
					break;
				else{
					turnedOffStr = turnedOffStr + msg.substring(0, spCharIndex) + turnOffChars + msg.charAt(spCharIndex);
					msg = msg.substring(spCharIndex + 1, msg.length());
				}
			}//while(spCharExists)
			turnedOffStr = turnedOffStr + msg;
			msg          = turnedOffStr;
		}

		return turnedOffStr;
	}
//-------------------------------------------------------------------------------
	public static void printBytes(byte[] by){
		Logger logger = ConnLogger.getLogger();

		for ( int i=0; i<by.length; i++ ){
			logger.info(UnicodeFormatter.byteToHex(by[i])+ ", ");
		}
	}
}

