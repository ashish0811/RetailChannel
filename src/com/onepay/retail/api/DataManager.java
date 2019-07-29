package com.onepay.retail.api;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONArray;
import org.json.JSONObject;

import com.wallet.reports.Convertor;
import com.wallet.reports.reportsDAO;



public class DataManager
{
	
public boolean getLogin(String userid,String pass) throws SQLException{
	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	
	try{
		conn = new DBConnection().getConnection();
		String roleid = getRole(userid);
		String sql ="select * from user_master join account_master on user_master.userid = account_master.partner_id where userid=? and password=? and is_active=1";
		if(roleid.equals("1"))
			sql ="select * from user_master where userid=? and password=? and is_active=1";
		ps = conn.prepareStatement(sql);
		ps.setString(1,userid);
		ps.setString(2, pass);			
		rs = ps.executeQuery();
		if(rs.next()){
			
			return true;//take him to dahboard
		}else
		{
			return false; //back to loging screen 
		}
	}
	catch(Exception e){
		e.printStackTrace();
	}
	finally{
		rs.close();//close rs
		ps.close();//closed ps
		conn.close();//close conn
	}
	return false;
} 
	
	
public List<List<String>> dispReports(String txnType,String toDate,String fromDate) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException
{
		
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		Connection con = new DBConnection().getConnection();
		
		// List<List<String>> users = new ArrayList<List<String>>();
		List<List<String>> report = null;
		if(con != null)
		{
			
		try {
			String query = null;
			if((txnType != null) || (toDate != null && fromDate != null)){
			 //query = "SELECT * FROM transaction_master where (txn_datetime between ? and ?) or (transaction_type = ?)";
			//query = "SELECT * FROM transaction_master where (txn_datetime between DATE(?) and DATE(?)+1) or (transaction_type = ?)";
			query = "SELECT txn_id,retailer_id,(select user_name from user_master where userid=partner_id) as partner_name,transaction_type,bank_id,parter_ref_no,amount,mobile_no,barCode,req_datetime,txn_datetime from transaction_master  where (txn_datetime between DATE(?) and DATE(?)+1) or (transaction_type = ?)";	
			}else{
			 query = "SELECT * FROM transaction_master";
			}
			preparedStatement = con.prepareStatement(query);
			//preparedStatement.setString(1,"%"+bank_id+"%");
			if((txnType != null) || (toDate != null && fromDate != null)){
				preparedStatement.setString(1,toDate);
				preparedStatement.setString(2,fromDate);
				preparedStatement.setString(3,txnType);
			}
			preparedStatement.execute();
			
			rs = preparedStatement.executeQuery();
			
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			
			if (rs != null) 
			{
				report = new ArrayList<List<String>>();
							
			while (rs.next()) 
			{
				
				List<String> rd = new ArrayList<String>();
				rd.add(rs.getString("txn_id"));
				rd.add(rs.getString("retailer_id"));
				//rd.add(rs.getString("partner_id"));
				rd.add(rs.getString("partner_name"));
				rd.add(rs.getString("transaction_type"));
				rd.add(rs.getString("bank_id"));
				rd.add(rs.getString("parter_ref_no"));
				rd.add(rs.getString("amount"));
				rd.add(rs.getString("mobile_no"));
				rd.add(rs.getString("barCode"));
				rd.add(rs.getString("req_datetime"));
				rd.add(rs.getString("txn_datetime"));
				
				report.add(rd);				
			}
			}
			
			
			
		} catch (Exception e) {
			System.err.println("Inside DispReports :" + e);
			
		}
		
		finally{
			 try {
				if(rs != null) { rs.close(); rs=null; };
				 if(preparedStatement != null) preparedStatement.close();
				 if(con != null){ con.close(); con=null; }
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		}
		
		return report;
}


public List<List<String>> downloadXLS() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException
{
		
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		Connection con = null;
		List<List<String>> report = null;
		if(con != null)
		{
			
		try {
			String query = null;
			con =  DBConnection.getConnection();
			//query = "select t.*,r.*,m.*,m.vehicle_number from transaction_master t left join registration_master r on t.txn_id = r.txn_id left join tag_mapping_master m on t.txn_id=m.txn_id";
			query = "SELECT t.txn_id,t.retailer_id,t.partner_id,t.transaction_type,t.bank_id,t.parter_ref_no,t.amount,t.mobile_no,t.barCode,t.req_datetime,t.txn_datetime,r.customer_name,r.gender,r.email_id,r.date_time,r.ref_number,r.aadhaar_no,r.aadhaar_image,r.pan,r.pan_image,r.vehicle_number,r.rc_image,r.cust_photo,m.date_time,m.ref_number,m.account_no,m.vehicle_number as vn,m.rc_image from transaction_master t left join registration_master r on t.txn_id = r.txn_id left join tag_mapping_master m on t.txn_id=m.txn_id";
			
			preparedStatement = con.prepareStatement(query);
			preparedStatement.execute();
			rs = preparedStatement.executeQuery();
					
			
			List<String> rd1 = new ArrayList<String>();
			
			if (rs != null) 
				{
					report = new ArrayList<List<String>>();
						rd1.add("TXN ID");
						rd1.add("Retailer Id");
						rd1.add("Partner Id");
						rd1.add("Transaction Type");
						rd1.add("Bank Id");
						rd1.add("Reference Number");
						rd1.add("Amount");
						rd1.add("Mobile Number");
						rd1.add("Bar Code");
						rd1.add("Req Date Time");
						rd1.add("Transaction Date Time");
						rd1.add("Customer Name");
						rd1.add("Gender");
						rd1.add("Email Id");
						rd1.add("Date Time");
						rd1.add("Reference Number");
						rd1.add("Addhaar Number");
						rd1.add("Addhaar Image Path");
						rd1.add("Pan");
						rd1.add("Pan Image");
						rd1.add("Vehicle Number");
						rd1.add("RC Image Path");
						rd1.add("Customer Image Path");
						rd1.add("Date Time");
						rd1.add("Reference Number");
						rd1.add("Account Number");
						rd1.add("Vehicle Number");
						rd1.add("RC Image Path");
						report.add(rd1);
						while (rs.next()) 
						{				
							List<String> rd = new ArrayList<String>();
							rd.add(rs.getString("txn_id"));
							rd.add(rs.getString("retailer_id"));
							rd.add(rs.getString("partner_id"));
							rd.add(rs.getString("transaction_type"));
							rd.add(rs.getString("bank_id"));
							rd.add(rs.getString("parter_ref_no"));
							rd.add(rs.getString("amount"));
							rd.add(rs.getString("mobile_no"));
							rd.add(rs.getString("barCode"));
							rd.add(rs.getString("req_datetime"));
							rd.add(rs.getString("txn_datetime"));
							
							rd.add(rs.getString("customer_name"));
							rd.add(rs.getString("gender"));
							rd.add(rs.getString("email_id"));
							rd.add(rs.getString("date_time"));
							rd.add(rs.getString("ref_number"));
							rd.add(rs.getString("aadhaar_no"));
							rd.add(rs.getString("aadhaar_image"));
							rd.add(rs.getString("pan"));
							rd.add(rs.getString("pan_image"));
							rd.add(rs.getString("vehicle_number"));
							rd.add(rs.getString("rc_image"));
							rd.add(rs.getString("cust_photo"));
							
							rd.add(rs.getString("date_time"));
							rd.add(rs.getString("ref_number"));
							rd.add(rs.getString("account_no"));
							//rd.add(rs.getString("vehicle_number"));
							rd.add(rs.getString("vn"));
							rd.add(rs.getString("rc_image"));
							
							report.add(rd);				
						}
			}
			
			
			
		} catch (Exception e) {
			System.err.println("inside downloadxls :" + e);
			}
		
		finally{
			 try {
				if(rs != null) { rs.close(); rs=null; };
				 if(preparedStatement != null) preparedStatement.close();
				 if(con != null){ con.close(); con=null; }
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		}
		
		return report;
}


public List<List<String>> dispPartnerReports(String txnType,String toDate,String fromDate,String partnerId) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException
{
		
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		Connection con = null;
		List<List<String>> report = null;
		
			
		try {
			
			con = DBConnection.getConnection();
			
			if(con != null)
			{
				
				
				 fromDate =fromDate+" 00:00:00";
				 toDate =toDate+" 23:59:59";
				
				// fromDate ="'"+fromDate+"'";
				// toDate ="'"+toDate+"'";
			String query = null;
			query ="CALL reportDisplay(?,?,?,?)";
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setString(1,partnerId);
			preparedStatement.setString(2,toDate);
			preparedStatement.setString(3,fromDate);
			preparedStatement.setString(4,txnType);
			preparedStatement.execute();
			
			rs = preparedStatement.executeQuery();
			
			ResultSetMetaData rsmd = rs.getMetaData();
			//int columnCount = rsmd.getColumnCount();
			
			if (rs != null) 
			{
				if (txnType.equalsIgnoreCase("Topup") || txnType.equalsIgnoreCase("Register"))
				{
					System.out.println("DataManager.java txnType ::: "+txnType+"   fromDate  :: "+fromDate+"   toDate  ::: "+toDate);
				report = new ArrayList<List<String>>();					
				while (rs.next()) 
				{
					
					List<String> rd = new ArrayList<String>();
					rd.add(rs.getString("txn_id"));
					rd.add(rs.getString("retailer_id"));
					rd.add(rs.getString("partner_name"));
					rd.add(rs.getString("transaction_type"));
					rd.add(rs.getString("bank_id"));
					rd.add(rs.getString("parter_ref_no"));
					rd.add(rs.getString("amount"));
					rd.add(rs.getString("mobile_no"));
					rd.add(rs.getString("barCode"));
					Date rdate = rs.getDate("req_datetime");
					SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
					String newRdate = date.format(rdate);
					//System.out.println("RDate........."+newRdate);
					rd.add(newRdate);
					// rd.add(rs.getString("req_datetime"));
					Date txn_date = rs.getDate("txn_datetime");
					String txnDate = date.format(txn_date);
					//System.out.println("txnDate........."+txnDate);
					rd.add(txnDate);	
					rd.add(rs.getString("status"));				
					report.add(rd);				
				}
			}
				
				
				if (txnType.equalsIgnoreCase("Ledger"))
				{
				report = new ArrayList<List<String>>();	
				 System.out.println("get value for Ledger from db and set into list in DataManager.java");
				 System.out.println("DataManager.java txnType ::: "+txnType+"   fromDate  :: "+fromDate+"   toDate  ::: "+toDate);
				while (rs.next()) 
				{
					
					List<String> rd = new ArrayList<String>();
					rd.add(rs.getString("txn_id"));
					rd.add(rs.getString("status"));
					rd.add(rs.getString("retailer_id"));
					rd.add(rs.getString("account_id"));
					rd.add(rs.getString("opening_bal"));
					rd.add(rs.getString("debit_amount"));
					rd.add(rs.getString("credit_amount"));
					rd.add(rs.getString("closing_balance"));
					rd.add(rs.getString("txn_type"));
					
					rd.add(rs.getString("bank_id"));
					rd.add(rs.getString("parter_ref_no"));
					rd.add(rs.getString("mobile_no"));
					rd.add(rs.getString("remarks"));
					
					SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
					Date rdate = rs.getDate("req_datetime");
					Date txndate = rs.getDate("txn_datetime");
					String newRdate = null;
					String newTxndate = null;
					if(rdate!=null)
					 newRdate = date.format(rdate);
					if(txndate!=null)
						newTxndate= date.format(txndate);
					
					rd.add(newRdate);
					rd.add(newTxndate);
									
					report.add(rd);				
				}
			}
				
			}
			
			
			
		}
		}
		catch (Exception e) {
			System.err.println("inside method dispPartnerReports() :" + e);
		}
		
		finally{
			 try {
				if(rs != null) { rs.close(); rs=null; };
				 if(preparedStatement != null) preparedStatement.close();
				 if(con != null){ con.close(); con=null; }
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		//System.out.println(report);
		return report;
}

public List<List<String>> partnerDownloadXLS(String partnerId) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException
{
		System.out.print("DataManager PartnerID"+partnerId);
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		Connection con = null;
		// Connection con = new DBConnection().getConnection();
		List<List<String>> report = null;
		
			
		try {
			
			 con =  DBConnection.getConnection();
			
			String query = null;
			
			if (con != null )
			{
			//query = "select t.*,r.*,m.*,m.vehicle_number from transaction_master t left join registration_master r on t.txn_id = r.txn_id left join tag_mapping_master m on t.txn_id=m.txn_id";
			query = "SELECT t.txn_id,t.retailer_id,t.partner_id,t.transaction_type,t.bank_id,t.parter_ref_no,t.amount,t.mobile_no,t.barCode,t.req_datetime,t.txn_datetime,r.customer_name,r.gender,r.email_id,r.date_time,r.ref_number,r.aadhaar_no,r.aadhaar_image,r.pan,r.pan_image,r.vehicle_number,r.rc_image,r.cust_photo,m.date_time,m.ref_number,m.account_no,m.vehicle_number as vn,m.rc_image from transaction_master t left join registration_master r on t.txn_id = r.txn_id left join tag_mapping_master m on t.txn_id=m.txn_id where t.partner_id=?";
			
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setString(1,partnerId);
			//preparedStatement.execute();			
			rs = preparedStatement.executeQuery();
					
			
			List<String> rd1 = new ArrayList<String>();
			
			if (rs != null) 
				{
					report = new ArrayList<List<String>>();
						rd1.add("TXN ID");
						rd1.add("Retailer Id");
						rd1.add("Partner Id");
						rd1.add("Transaction Type");
						rd1.add("Bank Id");
						rd1.add("Reference Number");
						rd1.add("Amount");
						rd1.add("Mobile Number");
						rd1.add("Bar Code");
						rd1.add("Req Date Time");
						rd1.add("Transaction Date Time");
						rd1.add("Customer Name");
						rd1.add("Gender");
						rd1.add("Email Id");
						rd1.add("Date Time");
						rd1.add("Reference Number");
						rd1.add("Addhaar Number");
						rd1.add("Addhaar Image Path");
						rd1.add("Pan");
						rd1.add("Pan Image");
						rd1.add("Vehicle Number");
						rd1.add("RC Image Path");
						rd1.add("Customer Image Path");
						rd1.add("Date Time");
						rd1.add("Reference Number");
						rd1.add("Account Number");
						rd1.add("Vehicle Number");
						rd1.add("RC Image Path");
						report.add(rd1);
						while (rs.next()) 
						{				
							List<String> rd = new ArrayList<String>();
							rd.add(rs.getString("txn_id"));
							rd.add(rs.getString("retailer_id"));
							rd.add(rs.getString("partner_id"));
							rd.add(rs.getString("transaction_type"));
							rd.add(rs.getString("bank_id"));
							rd.add(rs.getString("parter_ref_no"));
							rd.add(rs.getString("amount"));
							rd.add(rs.getString("mobile_no"));
							rd.add(rs.getString("barCode"));
							/*rd.add(rs.getDate("req_datetime"));*/
							Date rdate = rs.getDate("req_datetime");
							SimpleDateFormat date = new SimpleDateFormat("YYYY-mm-dd HH:mm:ss");
							String newRdate = date.format(rdate);
							System.out.println("RDate........."+newRdate);
							if(rdate!=null)
								rd.add(newRdate);
							else
								rd.add("NULL");
							rd.add(rs.getString("txn_datetime"));
							
							rd.add(rs.getString("customer_name"));
							rd.add(rs.getString("gender"));
							rd.add(rs.getString("email_id"));
							rd.add(rs.getString("date_time"));
							rd.add(rs.getString("ref_number"));
							rd.add(rs.getString("aadhaar_no"));
							rd.add(rs.getString("aadhaar_image"));
							rd.add(rs.getString("pan"));
							rd.add(rs.getString("pan_image"));
							rd.add(rs.getString("vehicle_number"));
							rd.add(rs.getString("rc_image"));
							rd.add(rs.getString("cust_photo"));
							
							rd.add(rs.getString("date_time"));
							rd.add(rs.getString("ref_number"));
							rd.add(rs.getString("account_no"));
							//rd.add(rs.getString("vehicle_number"));
							rd.add(rs.getString("vn"));
							rd.add(rs.getString("rc_image"));
							
							report.add(rd);				
						}
			}
			
			
		}
			else
			{
				System.out.println("DB Connection is null !!!");
			}
			
			
		} catch (Exception e) {
			System.err.println("inside partnerDownloadXLS :" + e.getMessage());
			e.printStackTrace();
			}
		
		finally{
			 try {
				if(rs != null) { rs.close(); rs=null; };
				 if(preparedStatement != null) preparedStatement.close();
				 if(con != null){ con.close(); con=null; }
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		System.out.print(report);
		return report;
}
public String getRole(String userid){
	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	
	try{
		conn = new DBConnection().getConnection();
		String sql ="SELECT role_id FROM user_master where userid=?";
		ps = conn.prepareStatement(sql);
		ps.setString(1,userid);					
		rs = ps.executeQuery();
			if(rs.next()){
				String roleid = rs.getString("role_id");
				return roleid;
			}
			else{
				return null;
			}
		}
	catch(Exception e){
		e.printStackTrace();
	}
	
	return null;
}

	public static void main1(String[] args) throws Exception{
		
		DataManager dm = new DataManager();
		/*String txnType = "TAGISSUE";
		String toDate ="";
		String fromDate ="";
*/		String partnerId ="23456";
		List<List<String>> abc = dm.partnerDownloadXLS(partnerId);
		System.out.println("Hello");
		for(int i=0;i<abc.size();i++){
			System.out.println(abc.get(i));
		}
		
		  
	}
	
	 /*public void updateFlag(String txnId,String sMessage){
		  	Connection conn = null;
		    PreparedStatement ps = null;
		    ResultSet rs = null;
		    PreparedStatement ps2 = null;
		    try{
		    conn =DBConnection.getConnection();
			  String q2 = "Update transaction_master SET txn_status=? where txn_id=?";
		      ps2 = conn.prepareStatement(q2);
		      ps2.setString(1, sMessage);
		      ps2.setString(2, txnId);
		      ps2.executeUpdate();
		    }catch(Exception e){
		    	e.printStackTrace();
		    }
	  }*/
	
	public List<List<String>> dispAdminReports(String toDate,String fromDate) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException
	{
			
			PreparedStatement preparedStatement = null;
			ResultSet rs = null;
			Connection con = null;
			List<List<String>> report = null;
			
				
			try {
				String query = null;
				 con = new DBConnection().getConnection();
				 
				 if(con != null)
					{
						if(fromDate != null && toDate != null)
						{
						
						 fromDate =fromDate+" 00:00:00";
						 toDate =toDate+" 23:59:59";
						
						// fromDate ="'"+fromDate+"'";
						// toDate ="'"+toDate+"'";
				query ="select t.* ,r.* from transaction_master t left join registration_master r on t.txn_id=r.txn_id where t.req_datetime>=? and t.req_datetime<=? ";
				preparedStatement = con.prepareStatement(query);
				preparedStatement.setString(1,fromDate);

				preparedStatement.setString(2,toDate);
				
				preparedStatement.execute();
				
				rs = preparedStatement.executeQuery();
				
				ResultSetMetaData rsmd = rs.getMetaData();
				//int columnCount = rsmd.getColumnCount();
				
				if (rs != null) 
				{
					report = new ArrayList<List<String>>();	
					List<String> hd = new ArrayList<String>();
					hd.add("Ref Id");
					hd.add("Customer Name");
					hd.add("Partner Id");
					hd.add("Status");
					hd.add("Email Id");
					hd.add("Aadhaar Number");
					hd.add("Pan");
					hd.add("Mobile Number");
					hd.add("Amount");
					hd.add("Bank Id");
					hd.add("Request DateTime");
					hd.add("Transaction DateTime");
					hd.add("Vehicle Number");
					hd.add("Address");
					hd.add("Aadhhar Image");
					hd.add("Pan Image ");
					hd.add("RC Image");
					hd.add("Ref Number");
					hd.add("Transaction Type");
					hd.add("Gender");
					report.add(hd);	
					while (rs.next()) 
					{
						
						List<String> rd = new ArrayList<String>();
						rd.add(rs.getString("txn_id"));
						rd.add(rs.getString("customer_name"));
						rd.add(rs.getString("partner_id"));
						rd.add(rs.getString("status"));
						rd.add(rs.getString("email_id"));
						rd.add(rs.getString("aadhaar_no"));
						rd.add(rs.getString("pan"));
						rd.add(rs.getString("mobile_no"));
						rd.add(rs.getString("amount"));
						rd.add(rs.getString("bank_id"));
						SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
						Date rdate = rs.getDate("req_datetime");
						
						String newRdate = date.format(rdate);
						//System.out.println("RDate........."+newRdate);
						rd.add(newRdate);
						// rd.add(rs.getString("req_datetime"));
						Date txn_date = rs.getDate("txn_datetime");
						String txnDate = date.format(txn_date);
						//System.out.println("txnDate........."+txnDate);
						rd.add(txnDate);
						rd.add(rs.getString("vehicle_number"));
						rd.add(rs.getString("address"));
						rd.add(rs.getString("aadhaar_image"));
						rd.add(rs.getString("pan_image"));
						rd.add(rs.getString("rc_image"));
						rd.add(rs.getString("barCode"));
						rd.add(rs.getString("transaction_type"));
						rd.add(rs.getString("gender"));
						report.add(rd);				
					}
				
					
					
					
					
				}
				
					}
				
			} 
			}catch (Exception e) {
				System.err.println("Error getting ServiceProvidersMapping :" + e);
				
			}
			
			finally{
				 try {
					if(rs != null) { rs.close(); rs=null; };
					 if(preparedStatement != null) preparedStatement.close();
					 if(con != null){ con.close(); con=null; }
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			
		
			//System.out.println(report);
			return report;
	}

	
	
	public List<List<String>> dispAdminLedgerReports(String toDate,String fromDate) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException
	{
			
			PreparedStatement preparedStatement = null;
			ResultSet rs = null;
			Connection con = null;
			List<List<String>> report = null;
			
				
			try {
				String query = null;
				 con = new DBConnection().getConnection();
				 
				 if(con != null)
					{
						if(fromDate != null && toDate != null)
						{
						
						 fromDate =fromDate+" 00:00:00";
						 toDate =toDate+" 23:59:59";
						
						// fromDate ="'"+fromDate+"'";
						// toDate ="'"+toDate+"'";
				query ="select txn_id,status,account_id,opening_bal,debit_amount,credit_amount,closing_balance,txn_type,remarks,date_time from account_ledger where date_time>=? and date_time<=? ";
				preparedStatement = con.prepareStatement(query);
				preparedStatement.setString(1,fromDate);
				preparedStatement.setString(2,toDate);
				preparedStatement.execute();
				rs = preparedStatement.executeQuery();
				ResultSetMetaData rsmd = rs.getMetaData();
				//int columnCount = rsmd.getColumnCount();
				
				if (rs != null) 
				{
					report = new ArrayList<List<String>>();	
					List<String> hd = new ArrayList<String>();
					hd.add("Txn Id");
					hd.add("Status");
					hd.add("Account Id");
					hd.add("Opening Balance");
					hd.add("Debit Amount");
					hd.add("Credit Amount");
					hd.add("Closing Balance");
					hd.add("Txn Type");
					hd.add("Remark");
					hd.add("Date Time");
					
					report.add(hd);	
					while (rs.next()) 
					{
						           
						
						List<String> rd = new ArrayList<String>();
						rd.add(rs.getString("txn_id"));
						rd.add(rs.getString("status"));
						rd.add(rs.getString("account_id"));
						rd.add(rs.getString("opening_bal"));
						rd.add(rs.getString("debit_amount"));
						rd.add(rs.getString("credit_amount"));
						rd.add(rs.getString("closing_balance"));
						rd.add(rs.getString("txn_type"));
						rd.add(rs.getString("remarks"));
					   // rd.add(rs.getString("inserted_on"));
					    
					    System.out.println("date time  :: "+rs.getString("date_time"));
						SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
						Date txn_date = rs.getDate("date_time");
						String txnDate = date.format(txn_date);
	
						rd.add(txnDate);  // inserted on					
						report.add(rd);				
					}
				
					
					
					
					
				}
				
					}
				
			} 
			}catch (Exception e) {
				System.err.println("Error getting ledger entries :" + e);
				
			}
			
			finally{
				 try {
					if(rs != null) { rs.close(); rs=null; };
					 if(preparedStatement != null) preparedStatement.close();
					 if(con != null){ con.close(); con=null; }
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			
		
			//System.out.println(report);
			return report;
	}
	

	public String UpdateBarCode(String ref_id, String barcode, String status, String remarks) {

		Connection conn = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		String finalStatus="Failed";
		try{
			
			if(null==barcode || barcode.isEmpty()) {
				barcode="Not Applicable";
			}
			System.out.println("calling the UpdateBarCode() DataManage.java"); 
			conn =new DBConnection().getConnection();
			conn.setAutoCommit(false);
			String sql ="update transaction_master set barCode = ?, status=? , remarks = ?  where txn_id = ?";
			String sql1 ="update account_ledger set status=?  where txn_id = ?";

			ps = conn.prepareStatement(sql);
			ps.setString(1,barcode);	
			ps.setString(2,status);	
			ps.setString(3,remarks);
			ps.setString(4,ref_id);	
			
			int i =  ps.executeUpdate();
			
			ps1 = conn.prepareStatement(sql1);
			ps1.setString(1,status);	
			ps1.setString(2,ref_id);	
			ps1.executeUpdate();
			conn.commit();
			System.out.println("i :: "+i);
				if(i>0){
					
					finalStatus = "success";
				}
			
				if(status.equalsIgnoreCase("REFUND")) {
					String[] str = getTxn(ref_id).split("\\|");
					processRefundTopup(str[0],str[1],str[2],"Refund",ref_id);
				}
			}
		
		catch(Exception e){
			e.printStackTrace();
		}
		finally 
		{
			try {
				
				if(ps!=null)
				{
					ps.close();
					ps=null;
					
				}
				if(ps1!=null)
				{
					ps1.close();
					ps1=null;
					
				}
				if(conn!=null)
				{
					conn.close();
					conn=null;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		
		return finalStatus;
	}
	
	public String getTxn(String txnId){
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String partner_id= null;
		try{
			conn = new DBConnection().getConnection();
			String sql ="SELECT partner_id,amount,transaction_type FROM transaction_master where txn_id=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1,txnId);					
			rs = ps.executeQuery();
				if(rs.next()){
					
					int amt = Integer.parseInt(rs.getString("amount"));
					if(rs.getString("transaction_type").equalsIgnoreCase("Register"))
					{
						amt = amt - 100;
						//amount = String.valueOf(amt);
					}
					else if(rs.getString("transaction_type").equalsIgnoreCase("Topup"))
					{
						amt = amt +3;
						//amount = String.valueOf(amt);
					}
					partner_id = rs.getString("partner_id")+"|"+amt+"|"+rs.getString("transaction_type");
					//return roleid;
				}
				
			}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return partner_id;
	}
	
	
	public String processRefundTopup(String partnerId,String amount,String txnType, String remarks, String referenceNo) {
		
		String txnId="0"; 
		Connection conn = null;
		CallableStatement cs = null;
		try
		{
			
			conn = DBConnection.getConnection();
			cs=conn.prepareCall("{CALL topup_account(?,?,?,?,?)}");  
			cs.setString(1,partnerId); 
			cs.setString(2, remarks); 
			cs.setString(3,amount); 
			cs.setString(4,referenceNo); 
			cs.registerOutParameter(5,Types.INTEGER); 
			cs.execute();
			System.out.println("<<<<<<<<<<<<<<<<<<Topup Account>>>>>>>>>>>>>>>>>>>");
			System.out.println("Out Param2" +cs.getInt(5)); 
			txnId = cs.getInt(5)+"";
			
		} catch (Exception e) {
			e.printStackTrace();

			e.printStackTrace();
		}finally
		{
			try
			{  
				if (cs != null) 
					cs.close();
				if (conn != null) 
					conn.close();

			} catch (Exception e) {
				

				e.printStackTrace();

			}

		}
		return txnId;
	}

	public static JSONArray getRegParams(String mobileNo){
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		JSONArray req = null;
		try{
			conn = new DBConnection().getConnection();
			String sql ="SELECT customer_name,gender,email_id,aadhaar_no,aadhaar_image,pan,pan_image,cust_photo,address from middleware.registration_master rm,middleware.transaction_master tm where tm.txn_id= rm.txn_id and tm.transaction_type='Register' and tm.mobile_no=?";//6380161988
			ps = conn.prepareStatement(sql);
			ps.setString(1,mobileNo);					
			rs = ps.executeQuery();
				if(rs.next()){
					req = new JSONArray();
					JSONObject obj = new JSONObject();
					obj.put("cname", rs.getString("customer_name"));
					obj.put("gender", rs.getString("gender"));
					obj.put("email", rs.getString("email_id"));
					obj.put("aadhaarNo", rs.getString("aadhaar_no"));
					obj.put("aadhaarImg", rs.getString("aadhaar_image"));
					obj.put("pan", rs.getString("pan"));
					obj.put("panImg", rs.getString("pan_image"));
					obj.put("CustImg", rs.getString("cust_photo"));
					obj.put("address", rs.getString("address"));
					
					//req.put();
					req.put(obj);
					
				}
				
			}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return req;
	}
	
	public static void main(String[] args) {
		System.out.println(getRegParams("6380161988"));
	}
	
}
