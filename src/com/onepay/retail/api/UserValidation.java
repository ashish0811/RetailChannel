package com.onepay.retail.api;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UserValidation {
	
	public boolean checkLoginDetails(String userId, String password)
	  {
	    Connection conn = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    try
	    {
	      conn = DBConnection.getConnection();
	      
	      String q1 = "select * from user_master where userid=? and password=?";
	      
	      System.out.println("UserValidation.java ::: checkLoginDetails() :: UserName : "+userId+" Password : "+password);
	      
	      ps = conn.prepareStatement(q1);
	      ps.setString(1, userId);
	      ps.setString(2, password);
	      rs = ps.executeQuery();
	      if (rs.next())
	      {
	        System.out.println("UserValidation.java ::: checkLoginDetails() :: User Validation Successful.");
	        return true;
	      }
	      System.out.println("UserValidation.java ::: checkLoginDetails() :: User Validation Failed.");
	      return false;
	    }
	    catch (Exception e)
	    {
	      e.printStackTrace();
	      return false;
	    }
	    finally
	    {
	      if (rs != null) {
	        try
	        {
	          rs.close();
	        }
	        catch (SQLException localSQLException9) {}
	      }
	      if (ps != null) {
	        try
	        {
	          ps.close();
	        }
	        catch (SQLException localSQLException10) {}
	      }
	      if (conn != null) {
	        try
	        {
	          conn.close();
	        }
	        catch (SQLException localSQLException11) {}
	      }
	    }
	  }
	  
	  public long insertSessionId(String userid)
	  {
	    Connection conn = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    PreparedStatement ps2 = null;
	    try
	    {
	      conn = DBConnection.getConnection();
	      String q1 = "delete from user_session where user_id=?";
	      ps = conn.prepareStatement(q1);
	      ps.setString(1, userid);
	      ps.execute();
	      
	      System.out.println("True");
	      long number = Math.abs(900000000) + 1000000000000000L;
	      String q2 = "insert into ser_session values(?,?,?)";
	      ps2 = conn.prepareStatement(q2);
	      ps2.setString(1, userid);
	      ps2.setLong(2, number);
	      ps2.setString(3,new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
	      ps2.executeUpdate();
	      return number;
	    }
	    catch (Exception e)
	    {
	      e.printStackTrace();
	      return 0L;
	    }
	    finally
	    {
	      if (ps2 != null) {
	        try
	        {
	          ps2.close();
	        }
	        catch (SQLException localSQLException8) {}
	      }
	      if (rs != null) {
	        try
	        {
	          rs.close();
	        }
	        catch (SQLException localSQLException9) {}
	      }
	      if (ps != null) {
	        try
	        {
	          ps.close();
	        }
	        catch (SQLException localSQLException10) {}
	      }
	      if (conn != null) {
	        try
	        {
	          conn.close();
	        }
	        catch (SQLException localSQLException11) {}
	      }
	    }
	  }
	

}
