package com.techstudio.db;
import org.apache.commons.dbcp.*;
import java.sql.*;
import java.util.*;
import javax.sql.*;
import org.apache.log4j.*;

public final class DBManager {

	static DBManager	dbMgr = null;

	DBConnection dbConn;
	
	private DBManager(DBObj obj, Logger logger){
		dbConn = DBConnection.getInstance(obj, logger);
	}
	
	public synchronized static DBManager getInstance(DBObj obj, Logger logger){
		if ( dbMgr==null )
			dbMgr = new DBManager(obj, logger);
		return dbMgr;
	}
	
	public LinkedList getRecord (String rSQL, int fetchsize) throws SQLException 
	{
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		LinkedList<String[]> list = new LinkedList<String[]>();
		
		try {
			con = dbConn.getConnection();
			con.setAutoCommit(true);			
			stmt = con.createStatement();
			stmt.setMaxRows(fetchsize);
			rs = stmt.executeQuery(rSQL);
			ResultSetMetaData rsmd = rs.getMetaData();
			int nNumOfCol = rsmd.getColumnCount();
			while (rs.next()) {
				String[] saRow = new String[nNumOfCol];
				for (int i=0 ; i<nNumOfCol ; i++) {
					saRow[i] = rs.getString(i+1);
				}
				list.add(saRow);
			}
		}finally{
			if (rs!=null) 
				try { 
					rs.close(); 
					rs=null;
				} catch(Exception e) {}		
			if (stmt!=null) 
				try { 
					stmt.close(); 
					stmt=null;
				} catch(Exception e) {}
			if (con!=null) 
				try { 
					con.close(); 
					con=null;
				} catch(Exception e) {}				
		}
		return list;
	}
	
	public LinkedList getRecord (String rSQL) throws SQLException 
	{
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		LinkedList<String[]> list = new LinkedList<String[]>();
		
		try {
			con = dbConn.getConnection();
			con.setAutoCommit(true);	
			stmt = con.createStatement();
			rs = stmt.executeQuery(rSQL);
			
			ResultSetMetaData rsmd = rs.getMetaData();
			int nNumOfCol = rsmd.getColumnCount();
			while (rs.next()) {
				String[] saRow = new String[nNumOfCol];
				for (int i=0 ; i<nNumOfCol ; i++) {
					saRow[i] = rs.getString(i+1);
				}
				list.add(saRow);
			}
		}finally{
			if (rs!=null) 
				try { 
					rs.close(); 
					rs=null;
				} catch(Exception e) {}		
			if (stmt!=null) 
				try { 
					stmt.close(); 
					stmt=null;
				} catch(Exception e) {}
			if (con!=null) 
				try { 
					con.close(); 
					con=null;
				} catch(Exception e) {}				
		}
		return list;
	}	

	public  int updateRecord (String rSQL) throws SQLException 
	{
		Connection con = null;		
		int norec = 0;
		Statement stmt = null;
		try 
		{
			con = dbConn.getConnection();
			con.setAutoCommit(true);					
			stmt = con.createStatement();	
			norec = stmt.executeUpdate(rSQL);
		}finally{
			if (stmt!=null) 
				try { 
					stmt.close(); 
					stmt=null;
				} catch(Exception e) {}
			if (con!=null) 
				try { 
					con.close(); 
					con=null;
				} catch(Exception e) {}				
		}
		return norec;
	}	
	
	public int updateRecord (String sql, Vector v) throws SQLException 
	{
		Connection con = null;	
		int norec = 0;
		PreparedStatement pstmt = null;
		try {
			con = dbConn.getConnection();
			con.setAutoCommit(true);		
			pstmt = con.prepareStatement(sql);
			for ( int i=0; i<v.size(); i++ ){
				String[] strarr = (String[])v.elementAt(i);
				for ( int j=0; j<strarr.length; j++ )
					pstmt.setString((j+1), strarr[j]);
				int temprec = pstmt.executeUpdate();
				if (temprec>0 )
					norec+=temprec;
			}
		}finally{
			if (pstmt!=null) 
				try { 
					pstmt.close(); 
					pstmt=null;
				} catch(Exception e) {}
			if (con!=null) 
				try { 
					con.close(); 
					con=null;
				} catch(Exception e) {}				
		}	
		
		return norec;
	}	
	
	public void closeConnection(){
		try{
			dbConn.closeConnection();
		}catch(Exception e){}
	}
}
