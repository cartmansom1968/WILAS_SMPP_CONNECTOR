package com.techstudio.app.entities;

public class SwitchObj
{
	static final int POS_TYPE 		= 0;
	static final int POS_SEQ 		= 1;
	static final int POS_ENABLE 	= 5;
	
	static final int LENGTH_SEQ			= 4;	
	
	static final int SEND_BYTES			= 5;
	
	String type		= "";
	String seq 		= "";
	boolean enable;
	
	public SwitchObj(int[] data){
		try{
			type 		= String.valueOf((char)data[POS_TYPE]);

			for ( int i=0; i<LENGTH_SEQ; i++ )
				seq  		= seq + (char)data[i+POS_SEQ];
	
			
			char charenable 		= (char)data[POS_ENABLE];
			if ( charenable=='0' )
				enable = false;
			else
				enable = true;
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public boolean isEnabled(){
		return enable;
	}
	
	public String getSeq(){
		return seq;
	}
	
	/*
	public byte[] getReply(){
		try{
			byte[] sendbytes= new byte[SEND_BYTES];
			
			byte[] typebytes = type.getBytes();
			byte[] sequencebytes = seq.getBytes();
			
			sendbytes[POS_TYPE] = typebytes[0];
			
			for ( int i=0; i<sequencebytes.length; i++)
				sendbytes[i+POS_SEQ] = sequencebytes[i];
	
			return sendbytes;
		}catch(Exception e){
			e.printStackTrace();
		}		
		return null;
	}*/
}

