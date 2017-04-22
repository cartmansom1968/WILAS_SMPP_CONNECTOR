package com.techstudio.app.util;
import java.util.*;

public class WapPush
{
	public static void main(String[] arg){
		WapPush wp = new WapPush();
		try{
			String[][] arr = wp.prepareWapPush("", "http://203.118.48.210/colorzip/jar/");
			for ( int i=0; i<arr.length; i++ ){
				for ( int j=0; j<arr[i].length; j++ )
					System.out.println(arr[i][j]);
			}
		}catch(Exception e){}
	}
	
  public int getMsgNo(String title, String url) {

		String part1 = "42061B03AE81EA456E636F64696E672D76657273696F6E00312E32008DA302056A0045C60C03";
		String part2 = "000103";
		String part3 = "000101";
		String hexMessage = "",hexURL = "";
		for (int i=0;i<url.length();i++){
		  int x = (int)url.charAt(i);
		  hexURL += Integer.toHexString(x); //Convert to hex
		}
		for (int i=0;i<title.length();i++){
		  int x = (int)title.charAt(i);
		  hexMessage += Integer.toHexString(x); //Convert to hex
		}
		
		String body = part1 + hexURL + part2 + hexMessage + part3;
		int count = (int)(body.length()/256);
		if ( body.length()%256!=0 )
			count++;
  	return count;
  }
	
  public String[][] prepareWapPush(String title, String url) throws Exception {
		String part1 = "42061B03AE81EA456E636F64696E672D76657273696F6E00312E32008DA302056A0045C60C03";
		String part2 = "000103";
		String part3 = "000101";
		String hexMessage = "",hexURL = "";
		for (int i=0;i<url.length();i++){
		  int x = (int)url.charAt(i);
		  hexURL += Integer.toHexString(x); //Convert to hex
		}
		for (int i=0;i<title.length();i++){
		  int x = (int)title.charAt(i);
		  hexMessage += Integer.toHexString(x); //Convert to hex
		}
		
		String body = part1 + hexURL + part2 + hexMessage + part3;
		int count = (int)(body.length()/256);
		if ( body.length()%256!=0 )
			count++;		
			
		String[][] data = new String[count][2];
		for (int y=0; y<count; y++) {
			int ending = (y*256)+256;
			if (ending >= body.length()) {
			   ending = body.length();
			}
			data[y][0] = "0b05040b8423f00003010" + count +"0" + (y+1);
			data[y][1] = body.substring(y*256, ending);
		}	
  	return data;
  }
	

}
