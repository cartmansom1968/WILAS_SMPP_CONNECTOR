package com.techstudio.deviceupdate;

public class Start
{
//447836747911
//23415
/*
C:/techstudio/GoToTry/config/mediator.conf
*/
	public static void main(String[] arg){
		if ( arg!=null && arg.length>0 )
			new Start(arg[0]);
		else{
			System.out.println("Invalid command");
			System.out.println("java com.techstudio.deviceupdate.Start <Config file>");
		}
	}

	public Start(String configfile){
		(new MainController(configfile)).start(); 
	}	
}

