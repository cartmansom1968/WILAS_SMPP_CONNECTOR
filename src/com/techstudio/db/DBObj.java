package com.techstudio.db;


public class DBObj
{
		String urldbpath = "";
		String driver 		= "";
		String username 	= "";
		String password 	= "";

		int initialsize = 1;
		int maxsize = 1;

		public DBObj( String urldbpath, String driver, String username, String password, int initialsize, int maxsize){
			this.urldbpath = urldbpath;
			this.driver = driver;
			this.username = username;
			this.password = password;
			this.initialsize = initialsize;
			this.maxsize = maxsize;
		}
}

