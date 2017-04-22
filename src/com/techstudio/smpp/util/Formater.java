package com.techstudio.smpp.util;

public class Formater {


	public static String replaceWAP(String param) {
	  String tmp = null;
	  if(param != null)
	  {
		  tmp = "";
		for(int cnt=0; cnt<param.length(); cnt++)
		{
			if(param.charAt(cnt) == '$')
			{
				tmp += param.charAt(cnt);
				tmp+= "$";
				
			}
			else if(param.charAt(cnt) == '&')
			{
				tmp+= "&#038;";
				
			}
			else
				tmp += param.charAt(cnt);

		}
		param = tmp;
	  }
		return param;
    }
	
	public static String replaceDB(String param) {
		  String tmp = null;
		  if(param!=null)
		  {
			  tmp = "";
			for(int cnt=0; cnt<param.length(); cnt++)
			{
				
				if(param.charAt(cnt) == '\'')
				{
					tmp += param.charAt(cnt);
					tmp+= "\'";
					
				}
				else
					tmp += param.charAt(cnt);

			}
			param = tmp;
		  }
			return param;
	    }
	
	

}
