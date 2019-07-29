package com.onepay.retail.api;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionPG
{
  public static Connection getConnection()
    throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException
  {
    Class.forName("com.mysql.jdbc.Driver").newInstance();
    /*String sURL = "jdbc:mysql://139.59.1.254:3306/pg";//139.59.1.254
    String sUserName = "root";
    String sPwd = "root@123";*/
   /* String sURL = "jdbc:mysql://192.168.150.15:3306/middleware";
    String sUserName = "payadmin";
    String sPwd = "pay@NM@RH5";*/
    
    /*Connection conn2 = DriverManager.getConnection(sURL, sUserName, sPwd);
    return conn2;*/
	  String userName = "sa";
	  String password = "root123";

	  String url = "jdbc:sqlserver://192.168.43.113:1433;databaseName=MTCDB1";

	  Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	  Connection conn = DriverManager.getConnection(url, userName, password);
	  return conn;
  }
  
  public static Connection getConnection1()
		    throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException
		  {
		    Class.forName("com.mysql.jdbc.Driver").newInstance();
		    String sURL = "jdbc:mysql://localhost:3306/netcnew";
		    String sUserName = "root";
		    String sPwd = "root";
		    Connection conn2 = DriverManager.getConnection(sURL, sUserName, sPwd);
		    return conn2;
		  }
  /*
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
