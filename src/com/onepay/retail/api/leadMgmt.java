package com.onepay.retail.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Ref;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;


/**
 * Servlet implementation class TopupAccount
 */
public class leadMgmt extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public leadMgmt() {
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

		System.out.println("Received Data :  "+jb.toString());
		jsonObject = new JSONObject(jb.toString());
		
		if(validateParameters(jsonObject)){
		
			String retailerId = jsonObject.getString("retailerId");
			String partnerId = jsonObject.getString("partnerId");
			String password = jsonObject.getString("password");
			String barCode = jsonObject.getString("barCode");
			String bankId = jsonObject.getString("bankId");
			String amount = jsonObject.getString("amount");
			String mobileNo = jsonObject.getString("mobileNo");
			String referenceNo = jsonObject.getString("referenceNo");
			String dateTime = jsonObject.getString("dateTime");
			
			boolean b = new UserValidation().checkLoginDetails(partnerId, password);
			if(b){
				txnId=processtxn(retailerId,partnerId,barCode,bankId,amount,mobileNo,referenceNo,dateTime);
				
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
	
	private String processtxn(String retailerId, String partnerId, String barCode, String bankId, String amount,
			String mobileNo, String referenceNo, String dateTime) {
	
		String txnId=null;
		Connection conn = null;
		CallableStatement cs = null;
		try
		{

			conn = DBConnection.getConnection();
			cs=conn.prepareCall("{CALL pr_add_txn(?,?,?,?,?,?,?,?,?,?,?)}");  
			cs.setString(1,retailerId);  
			cs.setString(2,partnerId); 
			cs.setString(3,"Topup"); 
			cs.setString(4,bankId); 
			cs.setString(5,referenceNo); 
			cs.setString(6,amount); 
			cs.setString(7,mobileNo); 
			cs.setString(8,barCode);
			cs.setString(9,dateTime); 
			cs.setString(10,new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())); 
			cs.registerOutParameter(11,Types.BIGINT); 
			cs.execute();
			System.out.println("<<<<<<<<<<<<<<<<<<Wallet API Transaction >>>>>>>>>>>>>>>>>>>");
			System.out.println("Out Param2" +cs.getLong(11)); 
			txnId = cs.getLong(11)+"";
			
		} catch (Exception e) {
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
			String barCode = jsonObject.getString("barCode");
			String bankId = jsonObject.getString("bankId");
			String amount = jsonObject.getString("amount");
			String mobileNo = jsonObject.getString("mobileNo");
			String referenceNo = jsonObject.getString("referenceNo");
			String dateTime = jsonObject.getString("dateTime");
			return true;
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	

}
