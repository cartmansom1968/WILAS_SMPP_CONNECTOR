package com.techstudio.app.entities;

public class LoginObj
{
	private final static int POS_TYPE 		= 0;
	private final static int POS_USERID 	= 1;
	private final static int POS_PASSWORD 	= 17;
	private final static int POS_SERVICEID 	= 33;
	private final static int TOTAL_BYTES 	= 36;

	private final static int USERID_MAX = 16;
	private final static int PASSWORD_MAX = 16;
	
	String type;		//0
	String userid;		//1-16
	String password;	//17-32
	String serviceid;	//33-35
	
	public LoginObj(String type, String userid, String password, String serviceid){		
		this.type 		= type;
		this.userid		= userid;
		this.password	= password;
		this.serviceid 	= serviceid;
	}

	public byte[] getBytes(){
		try{
			byte[] sendbytes= new byte[TOTAL_BYTES];
			
			byte[] typebytes = type.getBytes();
			byte[] useridbytes = userid.getBytes();
			byte[] passwordbytes = password.getBytes();
			byte[] serviceidbytes = serviceid.getBytes();
			
			String space = " ";
			byte[] spacebytes = space.getBytes();
			
			sendbytes[POS_TYPE] = typebytes[0];
			
			for ( int i=0; i<useridbytes.length; i++)
				sendbytes[i+POS_USERID] = useridbytes[i];
			for ( int i=useridbytes.length; i<USERID_MAX; i++)
				sendbytes[i+POS_USERID] = spacebytes[0];
				
			for ( int i=0; i<passwordbytes.length; i++)
				sendbytes[i+POS_PASSWORD] = passwordbytes[i];
			for ( int i=passwordbytes.length; i<PASSWORD_MAX; i++)
				sendbytes[i+POS_PASSWORD] = spacebytes[0];
				
			for ( int i=0; i<serviceidbytes.length; i++ )
				sendbytes[i+POS_SERVICEID] = serviceidbytes[i];	
			return sendbytes;
		}catch(Exception e){
			e.printStackTrace();
		}		
		return null;
	}

}

