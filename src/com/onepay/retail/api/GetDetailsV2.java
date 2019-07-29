package com.onepay.retail.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.SQLException;
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
public class GetDetailsV2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetDetailsV2() {
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
			String bankId = jsonObject.getString("bankId");
			String mobileNo = jsonObject.getString("mobileNo");
			
			
			boolean b = new UserValidation().checkLoginDetails(partnerId, password);
			if(b){
				sResp=processtxn(bankId,mobileNo);
							
			}
			else{
				sResp = finalResponse("NA", "Invalid User/Password","","" );
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
	
	public String processtxn(String bankId, String mobileNo) {
	
		
		Connection conn = null;
		CallableStatement cs = null;
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		String sResp = null;

		try
		{
			
				conn = DBConnection.getConnection();
		    
				/*String q2 = "select a.customer_name,a.email_id,b.mobile_no,b.bank_id from registration_master a,transaction_master b "
						+ "where a.txn_id=b.txn_id and b.mobile_no=? and b.bank_id=? and b.transaction_type='Register'";*/
				String q2 = "select * from hdfc_wallet "
						+ "where mobile_no=?";// and b.bank_id=? ";
				ps2 = conn.prepareStatement(q2);
				ps2.setString(1, mobileNo);
				//ps2.setString(2, bankId);
				rs =ps2.executeQuery();
				String q3 = "select * from transaction_master "
						+ "where mobile_no=?";// and b.bank_id=? ";
				ps3 = conn.prepareStatement(q3);
				ps3.setString(1, mobileNo);
				rs1 =ps3.executeQuery();
				if(rs.next()){
					sResp = finalResponse("00",rs.getString("name"), rs.getString("email_id"),"HDFC");
					//txnId = rs.getString("txn_id");
					//flag = rs.getString("txn_status").toString();
				}
				else if(rs1.next())
				{
					String finalStatus = rs1.getString("status");
					if(finalStatus.equalsIgnoreCase("Open")) {
					sResp = finalResponse("21","The Request is in progress","","HDFC");
					}
					else if(finalStatus.equalsIgnoreCase("Refund"))
					{
						sResp = finalResponse("22","The Request has been rejected","","");
						
					}
				}
				
				
				else 
				{
					sResp = finalResponse("NA", "No Record Found","","");
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
				sResp = finalResponse("NA", "No Record Found","","");
				e.printStackTrace();

			}

		}
		
		return sResp;
	}

	private String finalResponse(String flag,String refNo, String string, String bank)
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
			json.put("response_code", "14");
			json.put("response_message", refNo);
		}
		else if(flag.equals("21")) {
			json.put("response_code", "21");
			json.put("response_message", refNo);
		}
		else if(flag.equals("22")) {
			json.put("response_code", "22");
			json.put("response_message", refNo);
		}
		else {
			json.put("response_code", "00");
			json.put("response_message", "Success");
			json.put("customer_name", refNo);
			json.put("email", string);
			json.put("bankid", bank);
		}
		
		json.put("date_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		
		
		return json.toString();
	}
	
	private boolean validateParameters(JSONObject jsonObject){
		try{
			String retailerId = jsonObject.getString("retailerId");
			String partnerId = jsonObject.getString("partnerId");
			String password = jsonObject.getString("password");
			//String barCode = jsonObject.getString("barCode");
			String bankId = jsonObject.getString("bankId");
			//String amount = jsonObject.getString("amount");
			String mobileNo = jsonObject.getString("mobileNo");
			//String referenceNo = jsonObject.getString("referenceNo");
		//	String dateTime = jsonObject.getString("dateTime");
			return true;
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	

}
