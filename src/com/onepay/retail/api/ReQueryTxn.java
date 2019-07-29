package com.onepay.retail.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;
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
public class ReQueryTxn extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReQueryTxn() {
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
		String flagMsg=null;
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
			//String barCode = jsonObject.getString("barCode");
			//String bankId = jsonObject.getString("bankId");
			//String amount = jsonObject.getString("amount");
			//String mobileNo = jsonObject.getString("mobileNo");
			String referenceNo = jsonObject.getString("referenceNo");
			//String dateTime = jsonObject.getString("dateTime");
			
			boolean b = new UserValidation().checkLoginDetails(partnerId, password);
			if(b){
				flagMsg=processtxn(retailerId,partnerId,referenceNo);
				
				sResp = finalResponse(flagMsg, referenceNo);
			}
			else{
				sResp = finalResponse("Invalid User", referenceNo);
			}
			
		}/*else
		{
			sResp = finalResponse("0", "","Something went wrong");
			
		}*/
		
		}catch (Exception e) {
			e.printStackTrace();
		}
		out.write(sResp);
		out.flush();
		out.close();
	}
	
	private String processtxn(String retailerId, String partnerId,  String referenceNo) {
	
		String txnId="NA";
		Connection conn = null;
		CallableStatement cs = null;
		PreparedStatement ps2 = null;
		ResultSet rs = null;
		String flag = null;

		try
		{
			
				conn = DBConnection.getConnection();
		    
				String q2 = "select txn_id from transaction_master where parter_ref_no=? and partner_id=?";
				ps2 = conn.prepareStatement(q2);
				ps2.setString(1, referenceNo);
				ps2.setString(2, partnerId);
				rs =ps2.executeQuery();
				if(rs.next()){
					txnId = rs.getString("txn_id");
					//flag = rs.getString("txn_status").toString();
				}
			
			
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

	private String finalResponse(String flag,String refNo)
	{//System.out.println(flag);
		JSONObject json = new JSONObject();
		/*if(txnId.equalsIgnoreCase("0")){
			json.put("txn_id", "NA");
			json.put("response_code", "01");
		}else
		{
			json.put("txn_id", txnId);
			json.put("response_code", "00");
		}
		json.put("response_message", sMessage);*/
		if(flag.equalsIgnoreCase("NA")) {
			json.put("response_code", "12");
			json.put("response_message", "Transaction not found");
		}
		else {
			json.put("response_code", "00");
			json.put("response_message", "Success");
		}
		json.put("reference_no", refNo);
		json.put("date_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		json.put("txn_id", flag);
		
		return json.toString();
	}
		
	
	private boolean validateParameters(JSONObject jsonObject){
		try{
			String retailerId = jsonObject.getString("retailerId");
			String partnerId = jsonObject.getString("partnerId");
			String password = jsonObject.getString("password");
			//String barCode = jsonObject.getString("barCode");
			//String bankId = jsonObject.getString("bankId");
			//String amount = jsonObject.getString("amount");
			//String mobileNo = jsonObject.getString("mobileNo");
			String referenceNo = jsonObject.getString("referenceNo");
			//String dateTime = jsonObject.getString("dateTime");
			return true;
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	

}
