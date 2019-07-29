package com.onepay.retail.api;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class DBConnection
{
  public static Connection getConnection()
    throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException
  {
    /*Class.forName("com.mysql.jdbc.Driver").newInstance();
    String sURL = "jdbc:mysql://139.59.1.254:3306/pg";//139.59.1.254
    String sUserName = "root";
    String sPwd = "root@123";*/
    Class.forName("com.mysql.jdbc.Driver").newInstance();
    /*String sURL = "jdbc:mysql://139.59.1.254:3306/middleware";//139.59.1.254
    String sUserName = "root";
    String sPwd = "root@123";*/
    /*String sURL = "jdbc:mysql://192.168.150.15:3306/middleware";
    String sUserName = "payadmin";
    String sPwd = "pay@NM@RH5";*/
    
    //Connection conn2 = DriverManager.getConnection(sURL, sUserName, sPwd);
    //return conn2;
    
    Connection conn = null;
	DataSource ds = null;
	try
	{
		Context context = new InitialContext();
		ds = (DataSource)context.lookup("java:comp/env/jdbc/getMiddlewareDB");
		if (ds != null)
			conn = ds.getConnection();
	}catch (Exception e) {
		e.printStackTrace();
	}
	return conn;
    
  }
  
  /*public static Connection getConnection1()
		    throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException
		  {
		    Class.forName("com.mysql.jdbc.Driver").newInstance();
		    String sURL = "jdbc:mysql://localhost:3306/crm";
		    String sUserName = "root";
		    String sPwd = "root";
		    Connection conn2 = DriverManager.getConnection(sURL, sUserName, sPwd);
		    return conn2;
		  }
  
  public static Connection getConnection2()
		    throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException
		  {
		    Class.forName("com.mysql.jdbc.Driver").newInstance();
		    String sURL = "jdbc:mysql://localhost:3306/netc";
		    String sUserName = "root";
		    String sPwd = "root";
		    Connection conn2 = DriverManager.getConnection(sURL, sUserName, sPwd);
		    return conn2;
		  }
  
  public Connection smsConnection()
    throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException
  {
    Class.forName("com.mysql.jdbc.Driver").newInstance();
    String sURL = "jdbc:mysql://localhost:3306/smsengine";
    String sUserName = "root";
    String sPwd = "root123";
    Connection conn = DriverManager.getConnection(sURL, sUserName, sPwd);
    return conn;
  }
*/


}
