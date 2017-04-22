package com.techstudio.app.entities;

public class SendObj implements com.techstudio.app.SMPP_DefaultSetting
{

	public long msgid;
	public String recp;
	public String msg;
	public int msgtype;
	public String tpoa;
	
	public SendObj(long msgid, String recp, String msg, int msgtype, String tpoa){
		this.msgid		= msgid;
		this.recp		= recp;
		this.msg		= msg;
		this.msgtype	= msgtype;	
		this.tpoa	= tpoa;			
	}
}

