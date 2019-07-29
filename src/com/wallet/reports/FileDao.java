package com.wallet.reports;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import com.onepay.retail.api.DBConnection;

//import com.recon.utils.DBConnection;

public class FileDao 
{
	public List<HDFCWallet> retailCSVList(File pathToCSVFile)
	{
		List<HDFCWallet> retailList = new ArrayList();
		try
		{
			BufferedReader reader = new BufferedReader(new FileReader(pathToCSVFile));
			String line = null;
			Scanner scanner = null;
			int index = 0;

			while ((line = reader.readLine()) != null) {
				HDFCWallet person = new HDFCWallet();
				scanner = new Scanner(line);
				scanner.useDelimiter(",");
				while (scanner.hasNext()) {
					String data = scanner.next();
					if (index == 0)
						person.setMobile(data.replace("\"",""));
					else if (index == 1)
						person.setName(data.replace("\"",""));
					else if (index == 2)
						person.setEmailId(data.replace("\"",""));
					else
						System.out.println("Invalid data::" + data);
					index++;
				}
				index = 0;
				retailList.add(person);
			}

			//close reader
			reader.close();
		}
		catch (Exception e) {
			System.out.println("Exception while reading csv"+e.getMessage());
			e.printStackTrace();
		}
		return retailList;
	}
	
	
	public void insertWallets(List<HDFCWallet> person) 
	{
		int total = 0;
		String sql = "INSERT INTO hdfc_wallet(mobile_no, name, email_id) VALUES(?,?,?)";
		Connection conn = null;
		PreparedStatement pstmt = null;
		int iteration = 0;
		try  
		{
			conn = new DBConnection().getConnection();
			System.out.println(sql);
			for(HDFCWallet per : person){
				if(iteration != 0) 
				{
					if(!isExistMobileNo(per.getMobile()))
					{
						total++;
						pstmt = conn.prepareStatement(sql);
						pstmt.setString(1, per.getMobile());
						pstmt.setString(2, per.getName());
						pstmt.setString(3, per.getEmailId());
						pstmt.executeUpdate();
					}
				}
				iteration++; 
			} 
			System.out.println("HDFC Wallet inserted successfully...........");
			System.out.println("Total wallets are "+total);
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
	}
	
	
	public boolean isExistMobileNo(String mobile) 
	{
		String sql = "Select mobile_no from hdfc_wallet where mobile_no=?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try  
		{
			conn = new DBConnection().getConnection();
			//System.out.println(sql);
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, mobile);
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				System.out.println("Found in hdfc_wallet");
				return true;
			}
			else
			{
				//System.out.println("Not Found in hdfc_wallet");
				return false;
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
		return false;
	}
}
