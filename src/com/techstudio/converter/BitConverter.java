package com.techstudio.converter;
import org.apache.log4j.*;
import com.techstudio.smpp.util.*;

public class BitConverter{
	public static void main(String[] arg){
		Logger logger = ConnLogger.getLogger();

		logger.info(frmHexStrToBitStr("024A3A51D195CDD004001B20550590610560558550548540820849900000"));
		logger.info(frmBitStrToHexStr("000000100100101000111010010100011101000110010101110011011101000000000100000000000001101100100000010101010000010110010000011000010000010101100000010101011000010101010000010101001000010101000000100000100000100001001001100100000000000000000000"));
	}
	
	public static int intValue(String bitstr){
		int total = 0 ;
		int powvalue = bitstr.length()-1;
		for ( double i=0; i<bitstr.length(); i++ ){
			if ( bitstr.charAt((int)i)!='0' )
				total = total + (int) Math.pow(2.0, powvalue);
			powvalue --;
		}
		return total;
	}
	
	public static String frmBitStrToHexStr(String str){
		String temp ="";
		for ( int i=0; i<str.length(); i=i+4){
			int ch = Integer.parseInt(str.substring(i, i+4));
			switch(ch){
				case 0:
					temp = temp + 0;
					break;
				case 1:
					temp = temp + 1;
					break;
				case 10:
					temp = temp + 2;
					break;
				case 11:
					temp = temp + 3;
					break;
				case 100:
					temp = temp + 4;
					break;
				case 101:
					temp = temp + 5;
					break;
				case 110:
					temp = temp + 6;
					break;
				case 111:
					temp = temp + 7;
					break;
				case 1000:
					temp = temp + 8;
					break;
				case 1001:
					temp = temp + 9;
					break;
				case 1010:
					temp = temp + 'A';
					break;
				case 1011:
					temp = temp + 'B';
					break;
				case 1100:
					temp = temp + 'C';
					break;
				case 1101:
					temp = temp + 'D';
					break;
				case 1110:
					temp = temp + 'E';
					break;
				case 1111:
					temp = temp + 'F';
					break;
			}
		}
		return temp;
	}
	
	public static String frmHexStrToBitStr(String str){
		String temp ="";
		for ( int i=0; i<str.length(); i++){
			char ch = str.charAt(i);
			switch(ch){
				case '0':
					temp = temp + "0000";
					break;
				case '1':
					temp = temp + "0001";
					break;
				case '2':
					temp = temp + "0010";
					break;
				case '3':
					temp = temp + "0011";
					break;
				case '4':
					temp = temp + "0100";
					break;
				case '5':
					temp = temp + "0101";
					break;
				case '6':
					temp = temp + "0110";
					break;
				case '7':
					temp = temp + "0111";
					break;
				case '8':
					temp = temp + "1000";
					break;
				case '9':
					temp = temp + "1001";
					break;
				case 'a':case 'A':
					temp = temp + "1010";
					break;
				case 'b':case 'B':
					temp = temp + "1011";
					break;
				case 'c':case 'C':
					temp = temp + "1100";
					break;
				case 'd':case 'D':
					temp = temp + "1101";
					break;
				case 'e':case 'E':
					temp = temp + "1110";
					break;
				case 'f':case 'F':
					temp = temp + "1111";
					break;
			}
		}
		return temp;
	}
	 
	public static String frmStrToBitStr(String str){
		String temp ="";
		for ( int i=0; i<str.length(); i++ ){
			int a = (int)str.charAt(i);			
			temp = temp + frmHexStrToBitStr(String.valueOf(a));
		}
		return temp;
	}
}

