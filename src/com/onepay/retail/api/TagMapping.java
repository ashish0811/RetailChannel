package com.onepay.retail.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;


/**
 * Servlet implementation class TopupAccount
 */
public class TagMapping extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TagMapping() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Get Method not supported: ");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		PrintWriter out = response.getWriter();
		String sResp=null;
		JSONObject jsonObject=null;
		StringBuffer jb = new StringBuffer();
		String txnId=null;
		String line = null;
		try {
			BufferedReader reader = request.getReader();
			while ((line = reader.readLine()) != null)
				jb.append(line);

		jsonObject = new JSONObject(jb.toString());
		
		if(validateParameters(jsonObject)){
		
			String partnerId = jsonObject.getString("partnerId");
			String password = jsonObject.getString("password");
			String referenceNo = jsonObject.getString("referenceNo");
			boolean b = new UserValidation().checkLoginDetails(partnerId, password);
			if(b){
				txnId=processtxn(jsonObject,request);
				
				sResp = finalResponse(txnId, referenceNo,"Transaction Successful");
			}
			else{
				sResp = finalResponse("-2", referenceNo,"Invalid UserId/Password");
			}
			
		}else
		{
			sResp = finalResponse("0", "","Something went wrong");
			
		}
		
		}catch (Exception e) {
			e.printStackTrace();
		}
		out.write(sResp);
		out.flush();
		out.close();
	}
	
	private String processtxn(JSONObject jsonObject,HttpServletRequest request) {
		
		String txnId="0";
		Connection conn = null;
		CallableStatement cs = null;
		CallableStatement cs1 = null;
		try
		{System.out.println(jsonObject);
			String retailerId = jsonObject.getString("retailerId");//
			String partnerId = jsonObject.getString("partnerId");//
			String password = jsonObject.getString("password");
			String bankId = jsonObject.getString("bankId");
			String barCode = jsonObject.getString("barCode");
			String mobileNo = jsonObject.getString("mobileNo");
			String referenceNo = jsonObject.getString("referenceNo");//
			String dateTime = jsonObject.getString("dateTime");
			String accountNo = jsonObject.getString("accountNo");//
			String vehicleNumber = jsonObject.getString("vehicleNumber");//
			byte[] rcImage = decodeImage(jsonObject.getString("rcImage"));
			String amount = jsonObject.getString("amount");//


			conn = DBConnection.getConnection();
						
			cs1=conn.prepareCall("{CALL pr_tag_mapping(?,?,?,?,?,?,?,?,?,?,?,?)}");  
			cs1.setString(1,retailerId);  
			cs1.setString(2,partnerId); 
			//cs1.setString(3,new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			cs1.setString(3,dateTime);
			cs1.setString(4,referenceNo); 
			cs1.setString(5,accountNo); 
			cs1.setString(6,vehicleNumber); 
			//cs1.setBytes(7,rcImage); 
			cs1.setString(7, getPath(rcImage, mobileNo, "rc", request));
			cs1.setString(8,bankId); 
			cs1.setString(9,amount); 
			cs1.setString(10,mobileNo); 
			cs1.setString(11,barCode); 

			cs1.registerOutParameter(12,Types.BIGINT);
			cs1.execute();	
			System.out.println("<<<<<<<<<<<<<<<<<<Wallet API Transaction >>>>>>>>>>>>>>>>>>>");
			System.out.println("Out Param2" +cs1.getLong(12)); 
			txnId = cs1.getLong(12)+"";
		} catch (Exception e) {
			e.printStackTrace();
		}finally
		{
			try
			{  
				if (cs1 != null) 
					cs1.close();
				if (conn != null) 
					conn.close();

			} catch (Exception e) {
				e.printStackTrace();

			}

		}
		return txnId;
	}
	
	private String getPath(byte[] img,String mobile,String path,HttpServletRequest request)throws Exception
	{
		String spath = "/RetailImages"+"/"+path+"/"+mobile+".jpg";
		Files.write(Paths.get(spath),img);
		/*File file = new File(spath);request.getContextPath()+
		file.createNewFile();
		FileOutputStream fout = new FileOutputStream(spath);
		fout.write(img);*/
		return spath;
	}

	/*private String getPath(String aadhaarImage, String imagePath,HttpServletRequest request) {
		// TODO Auto-generated method stub
		
		//Save image get code from suraj
		String path = request.getContextPath()+"/"+imagePath;
		
		return path;
	}*/

	private String finalResponse(String txnId,String refNo,String sMessage)
	{
		JSONObject json = new JSONObject();
		if(txnId.equalsIgnoreCase("0")){
			json.put("txn_id", "NA");
			json.put("response_code", "01");
		}else
		{
			json.put("txn_id", txnId);
			json.put("response_code", "00");
		}
		json.put("response_message", sMessage);
		json.put("reference_no", refNo);
		json.put("date_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		
		
		return json.toString();
	}
		
	
	private boolean validateParameters(JSONObject jsonObject){
		try{
			String retailerId = jsonObject.getString("retailerId");
			String partnerId = jsonObject.getString("partnerId");
			String password = jsonObject.getString("password");
			String bankId = jsonObject.getString("bankId");
			String amount = jsonObject.getString("amount");
			String mobileNo = jsonObject.getString("mobileNo");
			String referenceNo = jsonObject.getString("referenceNo");
			String dateTime = jsonObject.getString("dateTime");
			String barCode = jsonObject.getString("barCode");

			return true;
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public byte[] decodeImage(String imageString)
	{
		//System.out.println("Image in String : "+imageString);
		return Base64.getDecoder().decode(imageString.getBytes());
	}

}