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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;


/**
 * Servlet implementation class TopupAccount
 */
public class inventoryMgmt extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public inventoryMgmt() {
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
		List<List<String>> flagMsg=null;
		String line = null;
		try {
			BufferedReader reader = request.getReader();
			while ((line = reader.readLine()) != null)
				jb.append(line);

			System.out.println("Received Data :  "+jb.toString());
			jsonObject = new JSONObject(jb.toString());
			
			//if(validateParameters(jsonObject)){
			
				String teamId = jsonObject.getString("teamId");
				System.out.println(teamId.toString());
				 flagMsg=processtxn(teamId);
				
				sResp = finalResponse(flagMsg);						
			//}
		
		}catch (Exception e) {
			e.printStackTrace();
		}
		out.write(sResp);
		out.flush();
		out.close();
	}
	
	private List<List<String>> processtxn(String teamId) {
	
		String txnId=null;
		Connection conn = null;
		CallableStatement cs = null;
		PreparedStatement ps2 = null;
		ResultSet rs = null;
		String flag = null;
	      List<List<String>> resp = null;

		try
		{
			
				conn = DBConnection.getConnection();
		    
				String q2 = "SELECT product_name as BarCode,(SELECT category_name FROM crm.category where id=internal_category) as VC_class,(SELECT salesteam FROM crm.salesteams where id=teamID) as Team_Id,bankId,status FROM crm.products where teamId=?";
				ps2 = conn.prepareStatement(q2);
				ps2.setString(1, teamId);
				rs =ps2.executeQuery();
		    
				
				resp = new ArrayList<List<String>>();
				while(rs.next()){
					
					
				List<String> rd = new ArrayList<String>();
				rd.add(rs.getString("BarCode"));
				rd.add(rs.getString("VC_class"));
				rd.add(rs.getString("Team_Id"));
				rd.add(rs.getString("bankId"));
				rd.add(rs.getString("status"));
 
				resp.add(rd);
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
		System.out.println(resp);
		return resp;
	}

	private String finalResponse(List<List<String>> flag)
	{	
		//System.out.println(flag);
		JSONObject json = new JSONObject();
		/*if(txnId.equalsIgnoreCase("0")){
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
		json.put("Transaction Status", flag);
		*/
		System.out.print(flag);
		for(int i=0;i < flag.size();i++) {
			json.put("key"+i,flag.get(i));
			/*json.put("BarCode", flag.get(i).get(0));
			json.put("VC_class", flag.get(i).get(1));
			json.put("TeamId", flag.get(i).get(2));
			json.put("bankId", flag.get(i).get(3));
			json.put("status", flag.get(i).get(4));*/
			//System.out.println(flag.get(i).get(i));
		}
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
