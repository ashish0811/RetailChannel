package com.onepay.retail.api;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
//import org.apache.poi.util.SystemOutLogger;
import org.json.JSONObject;

public class TESTPost {

	/*public String postData(String reqData, String URL){
		
		try{
			String data = reqData;
			String line = null;	
			System.out.println("finalURL >>>>>> >>>> " +URL);
			URL url = new URL(URL);
			HttpURLConnection con = (HttpURLConnection)url.openConnection();

			con.setRequestMethod("POST");
			con.addRequestProperty("Content-Type", "application/json;charset=UTF-8");
		//for fleet api only
			((URLConnection)con).setRequestProperty("csrfPreventionSalt","2018CORPORATE");
		    ((URLConnection)con).setRequestProperty("userId","OneMove");
			
			con.addRequestProperty("Content-Length", data.getBytes().length+"");
			con.setDoOutput(true);
			con.connect();

			OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
			System.out.println("Data length :: " + data.getBytes().length);
			wr.write(data);
			wr.flush();
			StringBuffer response = new StringBuffer(); 
			if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
				System.out.println("HTTP OK....");
				BufferedReader br = new BufferedReader(new
						InputStreamReader(con.getInputStream()));

				while((line = br.readLine()) != null) {
					System.out.println(line);
					response.append(line);
				}
				br.close();
			}
			else{
				System.out.println("Response code :: "+con.getResponseCode()+" "+con.getResponseMessage());
				response = response.append(con.getResponseCode());   
				//Integer.valueOf(con.getResponseCode()).toString();
			}

			con.disconnect();
			response.toString();
			
			System.out.println("response :: "+response.toString());
			return response.toString();
		}catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public static void main2(String[] args) throws Exception{
		//outertxn();
		//outercustomer();
		//oneMoveCustomer();
		//String s = encodeFileToBase64Binary();
		//System.out.println(""+s);
	}
	
	
	
	
	
	
	
	
	
	private static void oneMoveCustomer()
	{
		System.out.println("****************************Calling Customer Create in oneMoveCustomer******************************");
		//JSONObject json  = new JSONObject();
		//{"Customer":{"custId":"0","branchId":"ATS1","staffName":"ghfdghfdgfdg","emailId":"fdgbfd@gmail.com","contactNo":"9782598556","isActive":1,"createdBy":"admin","dob":"25-sept-2018"},"Address":{"resiAddr1":"fdgdfgfdgfd","resiAddr2":"fdgfdgdfgfdg","resiPin":"546546","resiCity":"Mumbai","resiState":"Maharashtra","businessAddr1":"BC5Address1","businessAddr2":"BC5Address2","businessPin":"560008","businessCity":"Bangalore","businessState":"Karnataka"},"Accounts":[{"accountNo":"ashish444665","bankName":"ICICI","branchAddress":"fghfdgfdgfgdfdgf","ifscCode":"fdgfdg56546","accountType":"Current","isWallet":"Y"}],"KYCDetails":{"addressProofId":"Aadhar Card","addressProofNo":"567567654643","addresProofDocPath":"c://Users/dell/Desktop/bharat1.jpg","idProof":"Pan Card","idProofNo":"bfdg454354","idProofDocPath":"c://Users/dell/Desktop/bharat.jpg"},"Vehicles":[{"vehicleId":"0","vehicleNumber":"b897865701","vehicleClassId":"1","isCommercial":true,"engineNumber":"fdbfgu7865","chassisNumber":"vbvcbvcb978686454","Documents":{"pathRCBook":"/home/webapi36/src/documents/fdgd342342/RCBook/Desert.jpg","pathInsurance":"/home/webapi36/src/documents/fdgd342342/Insurance/Jellyfish.jpg","pathFrontPic":"/home/webapi36/src/documents/fdgd342342/FrontPic/Jellyfish.jpg","pathBackPic":"/home/webapi36/src/documents/fdgd342342/BackPic/Koala.jpg","otherDoc1":"3other doc1","otherDoc2":"3other doc 2","otherDoc3":"3other doc 3"}}]}
		String json=postCustomerData("F183040099");
		System.out.println(json);
		new TESTPost().postData(json,"http://192.168.1.99:8080/NETCHDFC/admin/customers/FleetMgmtAddCustomer");
	}
	
	
	
	public static String postCustomerData(String userId)
	{
		PreparedStatement ps = null;
		Connection con =null;
		ResultSet rs = null;	
		JSONObject jo = null;
		//JSONArray report = null;
		String query = null;
		try {
			 con = new DBConnection().getConnection();
			 query ="select * from tbl_fleet_owner_master where status_id = 1";
			 ps = con.prepareStatement(query);
			 rs = ps.executeQuery();
			 //report = new JSONArray();
			 if (rs.next()) 
			 {
				 String fleetId = rs.getString("fleet_owner_id");
				 jo = new JSONObject();
				 jo.put("Accounts", getCustomerAccount(userId));
				 jo.put("Address", getCustomerAddress(fleetId));
				 jo.put("Customer", getFleetOwner(fleetId));
				 jo.put("KYCDetails", getCustomerKYC(fleetId));
				//jo.put("Vehicles", getCustomerVehicle(rs.getString("fleet_owner_id")));
				//report=report.put(jo);
			 }	
			
		} catch (Exception e) 
		{	
			//log.error("DataManager.java Gerring Error   :::    ",e);
		} 

		finally{
				try {
					if(rs!=null)
					{
						rs.close();
						rs=null;
					}
					if(ps!=null)
					{
						ps.close();
						ps=null;
					}
					if(con!=null)
					{
						con.close();
						con=null;
					}
				} catch (Exception e) {
					System.out.println("Exception occurred :: "+e.getMessage());		
					e.printStackTrace();
				}
			}
		
		return jo.toString();
	}
	
	
	
	
	public static JSONArray getCustomerAccount(String user_id)
	{
		PreparedStatement ps = null;
		Connection con =null;
		ResultSet rs = null;		
		JSONArray report = null;
		String query = null;
		try {
			con = new DBConnection().getConnection();
			query ="SELECT * FROM tbl_user_bankaccount where user_id=?";
			ps = con.prepareStatement(query);
			ps.setString(1,user_id);		
			rs = ps.executeQuery();
			report = new JSONArray();
			if(rs.next()) 
			{
				JSONObject jo = new JSONObject();
								
				jo.put("accountNo", rs.getString("user_id"));
				jo.put("accountType", rs.getString("acc_type"));
				jo.put("bankName", "HDFC");
				jo.put("branchAddress", "Kurla");	
				jo.put("ifscCode", rs.getString("ifsc_code"));
				jo.put("createdOn", rs.getString("created_on"));
				jo.put("userId", rs.getString("user_id"));
				jo.put("isWallet", "N");				
				report.put(jo);
			}	
		} 
		
		catch (Exception e)
		{
			System.out.println("Exception occurred :: "+e.getMessage());		
			e.printStackTrace();
		} 

			finally{
				try {
					if(rs!=null)
					{
						rs.close();
						rs=null;
					}
					if(ps!=null)
					{
						ps.close();
						ps=null;
					}
					if(con!=null)
					{
						con.close();
						con=null;
					}
				} catch (Exception e) {
					System.out.println("Exception occurred :: "+e.getMessage());		
					e.printStackTrace();
				}
			}
		return report;
	}
	
	
	
	public static JSONObject getCustomerAddress(String user_id)
	{
		PreparedStatement ps = null;
		Connection con =null;
		ResultSet rs = null;		
		JSONObject jo = null;
		String query = null;
		try {
			 con = new DBConnection().getConnection();
			query ="SELECT * FROM tbl_address_master where user_id = ?";
			ps = con.prepareStatement(query);
			ps.setString(1,user_id);		
			rs = ps.executeQuery();
			
			while (rs.next()) 
			{
				jo = new JSONObject();
				jo.put("businessAddr1", rs.getString("resi_add1"));
				jo.put("businessAddr2", rs.getString("resi_address2"));
				jo.put("businessCity", rs.getString("resi_city"));		
				jo.put("businessPin", rs.getString("resi_pin"));
				jo.put("businessState", rs.getString("resi_state"));
				jo.put("resiAddr1", rs.getString("resi_add1"));
				jo.put("resiAddr2", rs.getString("resi_address2"));
				jo.put("resiCity", rs.getString("resi_city"));
				jo.put("resiPin", rs.getString("resi_pin"));
				jo.put("resiState", rs.getString("resi_state"));
				jo.put("userId", rs.getString("user_id"));

			}	
			
		} catch (Exception e) {
			System.out.println("Exception occurred :: "+e.getMessage());		
			e.printStackTrace();
		} 
		

		finally{
				try {
					if(rs!=null)
					{
						rs.close();
						rs=null;
					}
					if(ps!=null)
					{
						ps.close();
						ps=null;
					}
					if(con!=null)
					{
						con.close();
						con=null;
					}
				} catch (Exception e) {
					
					System.out.println("Exception occurred :: "+e.getMessage());		
					e.printStackTrace();
				}
			}
		
		return jo;
	}
	
	
	public static JSONObject getFleetOwner(String cust_id)
	{
		PreparedStatement ps = null;
		Connection con =null;
		ResultSet rs = null;		
		JSONObject jo = null;
		String query = null;
		try {
			con = new DBConnection().getConnection();
			query ="SELECT * from tbl_fleet_owner_master where fleet_owner_id = ?";
			ps = con.prepareStatement(query);
			ps.setString(1,cust_id);		
			rs = ps.executeQuery();
			
			while (rs.next()) 
			{
				//id, fleet_owner_id, company_name, first_name, middle_name, last_name, dob, email_address, contact_number, status_id, created_by, created_on, last_updated_by, last_updated_on
				jo = new JSONObject();
				jo.put("custId", rs.getString("fleet_owner_id"));
				jo.put("staffName", rs.getString("first_name")+" "+rs.getString("middle_name")+" "+rs.getString("last_name"));
				jo.put("approvedOn", rs.getString("last_updated_by"));
				jo.put("approvedby", "Admin");	
				jo.put("branchId", "1Pay");
				jo.put("contactNo", rs.getString("contact_number"));
				jo.put("createdBy", rs.getString("created_by"));
				jo.put("createdOn", rs.getString("created_on"));
				jo.put("emailId", rs.getString("email_address"));
				//jo.put("emailId", rs.getString("email_id"));
				jo.put("dob", rs.getString("dob"));
				jo.put("isActive", 1);
				//"isActive":1,
			}	
			
		} catch (Exception e) {
			
			System.out.println("Exception occurred :: "+e.getMessage());		
			e.printStackTrace();
		} 
		
		finally{
				try {
					if(rs!=null)
					{
						rs.close();
						rs=null;
					}
					if(ps!=null)
					{
						ps.close();
						ps=null;
					}
					if(con!=null)
					{
						con.close();
						con=null;
					}
				} 
				catch (SQLException e) 
				{
					System.out.println("Exception occurred :: "+e.getMessage());		
					e.printStackTrace();
				}
			}
		
		return jo;
	}
	
	public static JSONObject getCustomerKYC(String cust_id){
		PreparedStatement ps = null;
		Connection con =null;
		ResultSet rs = null;		
		
		JSONObject jo = null;
		String query = null;
		try {
			con = new DBConnection().getConnection();
			query ="SELECT * from tbl_user_kyc where user_id = ?";
			ps = con.prepareStatement(query);
			ps.setString(1,cust_id);	
			rs = ps.executeQuery();
			
			while (rs.next()) 
			{
				//kyc_id, user_id, aadhar_number, aadhar_proof_doc, aadhar_doc_link, pan_number, pan_proof_doc, pan_doc_link, other_doc, other_doc_link, status_id, created_by, created_on, last_updated_by, last_updated_on
				jo = new JSONObject();
				jo.put("addresProofDocPath", rs.getString("aadhar_proof_doc"));	 // aadhar
				jo.put("addressProofId", "Aadhar Card");
				jo.put("addressProofNo", rs.getString("aadhar_number"));
				jo.put("custId", rs.getString("user_id"));
				jo.put("idProof", "Pan Card");
				jo.put("idProofDocPath", rs.getString("pan_proof_doc"));  // pan
				jo.put("idProofNo", rs.getString("pan_number"));
				//jo.put("panPhoto", encodeFileToBase64Binary());
				//jo.put("aadharPhoto", encodeFileToBase64Binary());
				
				
				//encodeFileToBase64Binary();
				// get API Image for pan and aadhar... append by bharat  21-aug
				
				
				String aadharPhoto = encodeImageToBase64String(rs.getString("address_proof_doc_path"));
				
				String panPhoto = encodeImageToBase64String(rs.getString("id_proof_doc_path"));
				// log.info("Pan Encription String :: "+panPhoto);
				
				jo.put("panPhoto",panPhoto); 
				jo.put("aadharPhoto",aadharPhoto);
				
				//report.put(jo);
				
			}	
			
		} catch (Exception e) 
		{
			System.out.println("Exception occurred :: "+e.getMessage());		
			e.printStackTrace();
		} 
	
		finally{
				try {
					if(rs!=null)
					{
						rs.close();
						rs=null;
					}
					if(ps!=null)
					{
						ps.close();
						ps=null;
					}
					if(con!=null)
					{
						con.close();
						con=null;
					}
				} catch (Exception e) {
					System.out.println("Exception occurred :: "+e.getMessage());		
					e.printStackTrace();
				}
			}
		return jo;
	}
	
	public static JSONArray getCustomerVehicle(String user_id)
	{
		JSONArray report = null;
		try {
			report = new JSONArray();
			JSONObject jo = new JSONObject();
			report.put(jo);
			
		} catch (Exception e)
		{
			System.out.println("Exception occurred :: "+e.getMessage());		
			e.printStackTrace();
		} 
		return report;
	}
	
	public static JSONObject getVehicleDoc(String cust_id){
		JSONObject jo = null;
		try {
				jo = new JSONObject();
			
		} 
		catch (Exception e) 
		{
			System.out.println("Exception occurred :: "+e.getMessage());		
			e.printStackTrace();
		} 
		return jo;
	}
	
	public static String encodeFileToBase64Binary() throws IOException {
	    File file = new File("E:\\PG\\Aug-Sept\\18-9-2018\\adhF.jpg");
	    byte[] encoded = Base64.getEncoder().encode(FileUtils.readFileToByteArray(file));    ////encodeBase64(FileUtils.readFileToByteArray(file));
	    return new String(encoded, StandardCharsets.US_ASCII);
	}
	
	public static byte[] loadFile(File file) throws IOException {
	    InputStream is = new FileInputStream(file);

	    long length = file.length();
	    if (length > Integer.MAX_VALUE) {
	        // File is too large
	    }
	    byte[] bytes = new byte[(int)length];
	    
	    int offset = 0;
	    int numRead = 0;
	    while (offset < bytes.length
	           && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
	        offset += numRead;
	    }

	    if (offset < bytes.length) {
	        //throw new IOException("Could not completely read file "+file.getName());
	    }

	    is.close();
	    return bytes;
	}
		*/
	public static void main(String[] args) throws IOException {
		/*System.out.println("getCustomerAccount "+getCustomerAccount("F183040099"));
		
		System.out.println("getVehicleDoc "+getVehicleDoc("F183040099"));
		System.out.println("getCustomerVehicle "+getCustomerVehicle("F183040099"));*/
		
		
		URL url = new URL("http://114.79.59.150:9005/");
		HttpURLConnection con = (HttpURLConnection)url.openConnection();
		
		con.setRequestProperty("X-Forwarded-For","204.9.177.195");
		con.setRequestProperty("User-Agent","benevolent-bot/blog.techstacks.com");
		if(con.getResponseCode() == 200){
			System.out.println("Connection success");
		 }
		else{
			System.out.println("not success");
		}
	}
}

