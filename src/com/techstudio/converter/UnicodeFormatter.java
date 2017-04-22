package com.techstudio.converter;
import java.io.*;
import com.techstudio.smpp.util.*;
import org.apache.log4j.*;

/**
* Do data convertion
*/

public class UnicodeFormatter  {
	Logger logger = ConnLogger.getLogger();
	/**
	* convert byte to String
	*/
	public static String byteToHex(byte b) {
		char hexDigit[] = {
			'0', '1', '2', '3', '4', '5', '6', '7',
				'8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
			};
		char[] array = { hexDigit[(b >> 4) & 0x0f], hexDigit[b & 0x0f] };
		return new String(array);
	}
	
	public static String bytesToHex(byte b[]) {
		Logger logger = ConnLogger.getLogger();

	try{
		char hexDigit[] = {
				'0', '1', '2', '3', '4', '5', '6', '7',
					'8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
				};
		String out ="";
		for ( int i=0; i<b.length; i++){
			char[] array = { hexDigit[(b[i] >> 4) & 0x0f], hexDigit[b[i] & 0x0f] };
			out = out + new String(array);
			
		}
		return out;
		}catch(Exception e){logger.info(e);}
		return "";
	}
	
	/**
	* convert character to hexadecimal
	*/
	public static String charToHex(char c) {
		byte hi = (byte) (c >>> 8);
		byte lo = (byte) (c & 0xff);
		return byteToHex(hi) + byteToHex(lo);
	}
}
