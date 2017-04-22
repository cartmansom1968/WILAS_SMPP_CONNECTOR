package com.techstudio.app;
import com.techstudio.app.controller.*;
import com.techstudio.mail.*;
import java.io.*;
import java.sql.*;

public class Restart extends Thread implements SMPP_DefaultSetting{			

	public Restart(Start mainclass){
		mainclass = null;
		reinit();
	}
	/*
	public Restart(Start_Query mainclass){
		mainclass = null;
		reinit();
	}
*/
	public void reinit(){
		try{				
			Runtime rt = Runtime.getRuntime();

			rt.exec("sh smpp/restart.bat");
		}catch(Exception e){
			e.printStackTrace();
		}
		System.exit(1);
	}
}
