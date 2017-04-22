package com.techstudio.converter;

import java.io.*;
import java.lang.*;
import com.techstudio.smpp.util.*;
import org.apache.log4j.*;

public class HexConverter{

	public static String toHexStr (byte[] bytearray){
			String hex;
			hex = "";
			for (int k = 0; k < bytearray.length; k++) 
				hex = hex + UnicodeFormatter.byteToHex(bytearray[k]);
			return hex;
	}
	
	public static String toHexStr_notwork (String string){
			String original;
			original = "";
			original = string;
			String hex;
			hex = "";
			byte[] stringBytes = original.getBytes();
			for (int k = 0; k < stringBytes.length; k++) 
				hex = hex + UnicodeFormatter.byteToHex(stringBytes[k]);

			return hex;
	}
	
	public static String intToHexString(int in_int){
		if (in_int < 16)
			return "0"+Integer.toHexString(in_int);
		else 
			return Integer.toHexString(in_int);		
	}
	
	public static int calculate_length(String string){
		return string.length();
	}
	
	//-------------------------------------------------------------------------------
	public static byte[] fromHexStrToBytes( String strHex ) {
		if (strHex.length() > 0) {
			int intNumBytes		= (strHex.length() / 2);
			byte[] temp 			= new byte[intNumBytes];
			int intArrayIndex = 0;
			int intStringIndex= 0;
			Character CharMSB = null;
			Character CharLSB = null;		
			String strMSB 		= "";
			String strLSB 		= "";
			byte byteMSB 			= 0;
			byte byteLSB 			= 0;
			char charMSB;
			char charLSB;
			
			for (int k=1; k<=intNumBytes; k++){
				charMSB = strHex.charAt(intStringIndex);
				charLSB = strHex.charAt(intStringIndex + 1);
				
				CharMSB = new Character(charMSB);
				CharLSB = new Character(charLSB);
				
				strMSB = CharMSB.toString();
				strLSB = CharLSB.toString();
				
				byteMSB = Byte.parseByte(strMSB, 16);				
				byteLSB = Byte.parseByte(strLSB, 16);
				
				temp[intArrayIndex] = (byte)((byteMSB*16) + byteLSB);
				intStringIndex = intStringIndex + 2;
				intArrayIndex = intArrayIndex + 1;
			}
			return temp;
		}else{
			return null;
		}
	} //convertHexStringToBytes
	
	//-------------------------------------------------------------------------------
	public static String fromHexStrToStr( String strHex ){
		byte[] temp 			= fromHexStrToBytes(strHex);
		if ( temp!=null)
			return new String(temp);
		return null;
	} //convertHexStringToBytes
	

	public static byte[] deBinaryCode(String content) {
		String enUnicode = null;
		byte[] b=new byte[content.length()/2];
		int j=0;
		for (int i = 0; i < content.length(); i++) {
			if (enUnicode == null) {
				enUnicode = String.valueOf(content.charAt(i));
			} else {
				enUnicode = enUnicode + content.charAt(i);
			}
			if (i % 2 == 1) {
				if (enUnicode != null) {
					b[j]=(byte)Integer.valueOf(enUnicode, 16).intValue();
					j++;
				}
				enUnicode = null;
			}

		}
		return b;
	}
	


	public static String toHexStr (String string){
		String[] unicode = new String[500];
		String result = "";
		char[] charArr = string.toCharArray();
		for(int c=0; c<charArr.length;c++)
		{
			unicode[c]=toEscape(charArr[c]);
			result=result+unicode[c];
		}

		return result;
	}


	private static String byteToHex(byte b) {
		char hexDigit[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		char[] array = { hexDigit[(b >> 4) & 0x0f], hexDigit[b & 0x0f] };
		return new String(array);
	}

	public static String charToHex(char c) {
		byte hi = (byte) (c >>> 8);
		byte lo = (byte) (c & 0xff);
		return byteToHex(hi) + byteToHex(lo);
	}

	public static String toEscape(char c) {
		String body = charToHex(c);
		String zeros = "000";
		return (zeros.substring(0, 4 - body.length()) + body);
	}
}

