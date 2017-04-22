package com.techstudio.smpp.util;
import com.techstudio.smpp.*;
import org.apache.log4j.*;

public class ByteBuffer implements DefaultSetting{
	Logger logger = ConnLogger.getLogger();

	byte[] buffer;
	byte zero = (byte)0x0;

	public ByteBuffer(){}

	public ByteBuffer(int data){
		buffer = new byte[SZ_INT];
    buffer[3] = (byte)(data & 0xff);
    buffer[2] = (byte)((data >>> 8) & 0xff);
    buffer[1] = (byte)((data >>> 16) & 0xff);
    buffer[0] = (byte)((data >>> 24) & 0xff);
	}
	
	public ByteBuffer(byte[] data){
		buffer = data;
	}
	
	public ByteBuffer(String data){
		add(data);
	}

	public void add(String value){
		if ( value==null ) 
			return;
			
		if ( buffer==null ){
			try{
				buffer = value.getBytes(CHAR_ENC);
				add(zero);
			}catch(Exception e){}
			return;
		}
		
		if ( value.equals("") ){

			byte[] tempbuffer = new byte[buffer.length+1];
			int i=0;
			for ( i=0; i<buffer.length; i++ )
				tempbuffer[i] = buffer[i];
			tempbuffer[i] = zero;
			buffer = tempbuffer;
			return;
		}
		
		try{
			byte[] bytevalue = value.getBytes(CHAR_ENC);
			
			byte[] tempbuffer = new byte[buffer.length+bytevalue.length+1];
			
			for ( int i=0; i<buffer.length; i++ )
				tempbuffer[i] = buffer[i];
			
			int length = buffer.length;
			for ( int i=0; i<bytevalue.length; i++ )
				tempbuffer[i+length] = bytevalue[i];
			tempbuffer[length+bytevalue.length] = zero;
			buffer = tempbuffer;
		}catch(Exception e){
		}
	}
	
	public void add(byte value){
		if ( buffer==null ){
			buffer = new byte[1];
			buffer[0] = value;
			return;
		}
		
		byte[] tempbuffer = new byte[buffer.length+1];
		
		for ( int i=0; i<buffer.length; i++ )
			tempbuffer[i] = buffer[i];

		tempbuffer[buffer.length] = value;
		buffer = tempbuffer;
	}

	public void add(byte[] value){
		if ( buffer==null ){
			buffer = value;
			return;
		}
		
		byte[] tempbuffer = new byte[buffer.length+value.length];
		
		for ( int i=0; i<buffer.length; i++ )
			tempbuffer[i] = buffer[i];

		int length = buffer.length;
		for ( int i=0; i<value.length; i++ )
			tempbuffer[i+length] = value[i];

		buffer = tempbuffer;
	}
	
	public void add(short value){;
		byte[] intbuffer = new byte[SZ_SHORT];
	  intbuffer[1] = (byte)(value & 0xff);
	  intbuffer[0] = (byte)((value >>> 8) & 0xff);
		add(intbuffer);
	}
	
	public void add(int value){;
		byte[] intbuffer = new byte[SZ_INT];
	    intbuffer[3] = (byte)(value & 0xff);
        intbuffer[2] = (byte)((value >>> 8) & 0xff);
        intbuffer[1] = (byte)((value >>> 16) & 0xff);
        intbuffer[0] = (byte)((value >>> 24) & 0xff);
		add(intbuffer);
	}
	
	public byte removeByte(){
		byte returnbyte = buffer[0];
		byte[] tempbuffer = new byte[buffer.length-1];
		for ( int j=1; j<buffer.length; j++ )
			tempbuffer[j-1] = buffer[j];
		buffer = tempbuffer;
		return returnbyte;
	}
	
	public int removeInt(){
		int result = 0;
    if (buffer.length >= SZ_INT) {
      result |= buffer[0]&0xff;
      result <<= 8;
      result |= buffer[1]&0xff;
      result <<= 8;
      result |= buffer[2]&0xff;
      result <<= 8;
      result |= buffer[3]&0xff;
			copyBuffer(SZ_INT, buffer.length);
      return result;
    } 
		return 0;
	}

	public String removeString(){
		String result = "";

		if ( buffer[0]==zero ){
			byte[] tempbuffer = new byte[buffer.length-1];
			for ( int j=1; j<buffer.length; j++ )
				tempbuffer[j-1] = buffer[j];
			buffer = tempbuffer;
			return "";
		}

		try{
			int i=1;
			for ( i=1; i<buffer.length; i++ )
				if ( buffer[i]==zero )
					break;
				
			byte[] tempbuffer1 = new byte[i];
			for ( int j=0; j<i; j++ )
				tempbuffer1[j] = buffer[j];

			byte[] tempbuffer2 = new byte[buffer.length-i-1];
			for ( int j=(i+1); j<buffer.length; j++ )
				tempbuffer2[j-i-1] = buffer[j];
			buffer = tempbuffer2;
		//logger.info(new String (tempbuffer1, "MS950"));
		//gamecode = new String(gamecode.getBytes(), "UnicodeBig");
			result     = new String(tempbuffer1, CHAR_ENC );
		/*byte[] tempBytes = temp.getBytes();
			result       = new String( tempBytes, "UnicodeBig" );*/
		//result = new String (tempbuffer1, CHAR_ENC);
			//logger.info(result);
		}catch(Exception e){
			e.printStackTrace();
		}		
		return result;
	}
	
	public byte[] removeBytes(int no){
		if (buffer.length>no){
			byte[] returnbytes = new byte[no];
			for ( int i=0; i<no; i++ )
				returnbytes[i] = buffer[i];
			byte[] leftbytes = new byte[buffer.length-no];
			for ( int j=no; j<buffer.length; j++ )
				leftbytes[j-no] = buffer[j];
			buffer = leftbytes;
			return returnbytes;
		}
		else if (buffer.length==no){
			byte[] temp = buffer;
			buffer = null;
			return temp;
		}
		return null;
	}
	
	public void copyBuffer(int start, int end){
		if ( end>buffer.length){
			//logger.info("copyBuffer >> Buffer length < end index");
			return;
		}
		byte[] tempbuff = new byte[end-start];
		for ( int i=0; i<end-start; i++ )
			tempbuff[i] = buffer[start+i];
		buffer = tempbuff;
	}
	
	public byte[] getBytes(){
		return buffer;
	}
	
	public int getLength(){
		if ( buffer==null )
			return 0;
		return buffer.length;
	}

}

