package com.techstudio.app.entities;

public class QueryObj
{

	String sourceno;
	String telcomsgid;
	long msgid;
	public QueryObj(long msgid, String sourceno, String telcomsgid){		
		this.sourceno 	= sourceno;
		this.msgid 		= msgid;
		this.telcomsgid 		= telcomsgid;
		
	}

	public String getSourceNo(){
		return sourceno;
	}

	public long getMsgID(){
		return msgid;
	}


	public String getTelcoMsgID(){
		return telcomsgid;
	}	
}

