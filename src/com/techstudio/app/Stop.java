package com.techstudio.app;
import com.techstudio.app.controller.*;
import com.techstudio.mail.*;

public class Stop implements SMPP_DefaultSetting{	
	
	public Stop(){
		try{
			/*
			String sql = "delete from msg where msgid=0";
			dbConn.rSQLretLng(sql);
			sql = "insert into msg (msgid, recp, msg, recptelco_id, encoding)values(0, '987654321', 'stopserver909', 'SUN', 'MS950')";
			dbConn.rSQLretLng(sql);
			*/
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
