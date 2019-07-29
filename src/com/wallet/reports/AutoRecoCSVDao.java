package com.wallet.reports;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import com.mysql.jdbc.ResultSetMetaData;
import com.onepay.retail.api.DBConnection;

//import com.recon.utils.DBConnection;

public class AutoRecoCSVDao 
{
	
	
	public static  List<List<String>> downloadCSVReco() 
	{
		String sql = "Select * from tbl_reconmaster";
		Connection conn = null;
		List<List<String>> reconTxn = null;
		List<String> reconColumns = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try  
		{
			reconTxn = new ArrayList<List<String>>();
			conn = new DBConnection().getConnection();
			//System.out.println(sql);
			pstmt = conn.prepareStatement(sql);
			//pstmt.setString(1, mobile);
			rs = pstmt.executeQuery();
			//java.sql.ResultSetMetaData rsmd =  rs.getMetaData();
			while(rs.next())
			{
				reconColumns = new ArrayList<String>();				
				reconColumns.add(rs.getString("Recon_Id"));
				reconColumns.add(rs.getString("Txn_Id"));
				reconColumns.add(rs.getString("Sp_Txn_Id"));
				reconColumns.add(rs.getString("Bank_Txn_Id"));
				reconColumns.add(rs.getString("Merchant_Txn_Id"));
				reconColumns.add(rs.getString("Txn_Amount"));
				reconColumns.add(rs.getString("Trans_Status"));
				reconColumns.add(rs.getString("Recon_Status"));
				reconColumns.add(rs.getString("Merchant_Id"));
				reconColumns.add(rs.getString("Sp_Id"));
				reconColumns.add(rs.getString("Bank_Id"));
				reconColumns.add(rs.getString("Instrument_Id"));
				reconColumns.add(rs.getString("Agg_Charges"));
				reconColumns.add(rs.getString("Agg_GST"));
				reconColumns.add(rs.getString("Bank_Charges"));
				reconColumns.add(rs.getString("Bank_GST"));
				reconColumns.add(rs.getString("Reseller_Charges"));
				reconColumns.add(rs.getString("Reseller_GST"));
				reconColumns.add(rs.getString("Sur_Charge"));
				reconColumns.add(rs.getString("Net_Settlement"));
				reconColumns.add(rs.getString("Total_Agg_Fees"));
				reconColumns.add(rs.getString("Txn_Date"));
				reconColumns.add(rs.getString("Recon_Date_Time"));
				reconColumns.add(rs.getString("ReconUploadedBy"));
				
				reconColumns.add(rs.getString("Payout_Generated"));
				reconColumns.add(rs.getString("PayoutGeneratedBy"));
				reconColumns.add(rs.getString("Payout_Date_Time"));
				reconColumns.add(rs.getString("Modified_On"));
				reconColumns.add(rs.getString("Modified_By"));
				//System.out.println("Found in hdfc_wallet");
				
				reconTxn.add(reconColumns);
				
			}
			
			
		}
		catch (Exception e) 
		{
			System.out.println("Exception while insert in HDFC Wallet In FileDao :: "+e.getMessage());
			e.printStackTrace();
		}
		finally{
			try {
				if(pstmt!=null)
					pstmt.close();
				if(conn!=null)
					conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return reconTxn;
	}
	
	public static void main(String[] args) throws IOException {
		System.out.println("download starts now"+new Date());
		System.out.println(downloadCSVReco().size());
		System.out.println("download ends now"+new Date());
		//Runtime.getRuntime().exec("mysql -u root -p root123 -e 'SELECT * FROM pg.tbl_reconmaster'>reco.csv");
	}
}
