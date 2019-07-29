package com.onepay.retail.api;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;


/**
 * Servlet implementation class TopupAccount
 */
public class AddVehicle extends HttpServlet {
	static Logger log = Logger.getLogger(AddVehicle.class.getName());

	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddVehicle() {
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
		try
		{
			BufferedReader reader = request.getReader();
			while ((line = reader.readLine()) != null)
			{
				jb.append(line);
			}
			log.info("Received Data :  "+jb.toString());
			jsonObject = new JSONObject(jb.toString());
		
		if(validateParameters(jsonObject))
		{
		
			/*String retailerId = jsonObject.getString("retailerId");
			String partnerId = jsonObject.getString("partnerId");
			String password = jsonObject.getString("password");
			String referenceNo = jsonObject.getString("referenceNo");*/
			String retailerId = jsonObject.getString("retailerId");
			String partnerId = jsonObject.getString("partnerId");//
			String password = jsonObject.getString("password");
			String bankId = jsonObject.getString("bankId");//
			
			String mobileNo = jsonObject.getString("mobileNo");//
			String referenceNo = jsonObject.getString("referenceNo");//
			String dateTime = jsonObject.getString("dateTime");
			
			String vehicleNumber = jsonObject.getString("vehicleNumber");//
			String rcImage = jsonObject.getString("rcImage");//
			String amount = jsonObject.getString("amount"); //
			String address = jsonObject.getString("address");
			String isCommercial = jsonObject.getString("isCommercial"); 


			
			//System.out.println("Jason Data Aadhar Image : "+aadhaarImage);

			boolean b = new UserValidation().checkLoginDetails(partnerId, password);
			if(b)
			{
				String errorCode = new CommanErrors().CommanErrorsAddVehicle(mobileNo, amount, referenceNo, partnerId, bankId,vehicleNumber, rcImage,isCommercial);
				
				log.info("NewVehicleRegistration.java ::: CommanErrorsAddVehicle :: error code : " + errorCode+"------------"+isCommercial);
				
				/*if(errorCode.equals("00")) 
				{
					errorCode = new CommanErrors().RegError(mobileNo,cname, address, gender, pan, aadhaarNo, email_id, vehicleNumber, bankId);
				}*/
				
				//log.info("NewRegistration.java ::: RegError :: error code : " + errorCode);
				
				if(errorCode.equals("00")) 
				{
				
					txnId = processtxn(jsonObject,request);
				
					if(txnId.equals("0"))
						sResp = finalResponse("-3","NA", referenceNo,"Error while processing Transaction Request");
					else if(txnId.equals("-2"))
						sResp = finalResponse("-2","NA", referenceNo,"Incorrect Image Format");
					else
					{
						sResp = finalResponse(errorCode,txnId, referenceNo,"Transaction Successful");
						log.info("final response Registration********************"+sResp);

						int isSent = 0;
						String bodyText = "New Request for Registration in UAT from "+partnerId +" for mobile number : "+mobileNo+" with reference id : "+referenceNo+" and amount : "+amount;
						String subject = "New UAT Retail Registration--"+partnerId;

						/*String toRahul = "rahul@1pay.in";
						String toNavin = "navin@1pay.in";
						String toRishabh = "rishabh.mehta@1pay.in";
						String toshubhangi = "shubhangi.kokare@1pay.in ";
						String toSantosh = "santosh.jadhav@1pay.in ";
						String toAbhijeet = "abhijeet@1pay.in";*/

						String toAshish = "ashish.dubey@1pay.in";
						/*isSent=EmailService.sendMail(toRahul, subject, bodyText);
						isSent=EmailService.sendMail(toNavin, subject, bodyText);
						isSent=EmailService.sendMail(toRishabh, subject, bodyText);
						isSent=EmailService.sendMail(toshubhangi, subject, bodyText);
						isSent=EmailService.sendMail(toSantosh, subject, bodyText);
						isSent=EmailService.sendMail(toAbhijeet, subject, bodyText);*/
						isSent=EmailService.sendMail(toAshish, subject, bodyText);

					}
				}
				else
				{
					sResp = finalResponse(errorCode,errorCode, referenceNo,"Failed");
				}
			}
			else
			{
				sResp = finalResponse("-1","-1", referenceNo,"Invalid UserId/Password");
			}
			
		}
		else
		{
			sResp = finalResponse("-1","0", "","Something went wrong");
			
		}
		
		}catch (Exception e) {
			log.error("NewRegistration.java  :::  getting error :: () ",e);

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
		{//System.out.println(jsonObject);
			String retailerId = jsonObject.getString("retailerId");
			String partnerId = jsonObject.getString("partnerId");
			String password = jsonObject.getString("password");
			String bankId = jsonObject.getString("bankId");
			String mobileNo = jsonObject.getString("mobileNo");
			String referenceNo = jsonObject.getString("referenceNo");
			String dateTime = jsonObject.getString("dateTime");
			String vehicleNumber = jsonObject.getString("vehicleNumber");
			String amount = jsonObject.getString("amount"); 
			String address = jsonObject.getString("address");
			String isCommercial = jsonObject.getString("isCommercial"); 

			
			 byte[] rcImage = null; 
						
			
							
				rcImage = decodeImage(jsonObject.getString("rcImage"));
			
			
			if(null==rcImage)
			{
				return "-2";
			}
			
			
			log.info("retailerid"+retailerId+"ParterId"+partnerId+"Password"+password+"bankid"+bankId+"mobileno"+mobileNo+"RefNo"+referenceNo+"Datetime"+dateTime+"vehicalno"+vehicleNumber);
			conn = DBConnection.getConnection();
			String cname = null,gender = null,email_id=null,aadhaarNo=null,aadhaarImage=null,pan=null,panImage=null,custPhoto=null;
			
			JSONArray reqParams = DataManager.getRegParams(mobileNo);
			for(int i=0; i<reqParams.length();i++)
			{
				JSONObject obj = reqParams.getJSONObject(0);
				cname=obj.getString("cname");
				gender=obj.getString("gender");
				email_id=obj.getString("email");
				aadhaarNo=obj.getString("aadhaarNo");
				aadhaarImage=obj.getString("aadhaarImg");
				pan=obj.getString("pan");
				panImage=obj.getString("panImg");
				custPhoto=obj.getString("CustImg");
				address=obj.getString("address");
				
			}
			cs1=conn.prepareCall("{CALL pr_add_vehicle(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");  
			//cs1.setString(1,txnId);
			cs1.setString(1,cname);
			cs1.setString(2,retailerId);  
			cs1.setString(3,partnerId); 
			cs1.setString(4,gender); 
			cs1.setString(5,email_id); 
			cs1.setString(6,dateTime); 
			cs1.setString(7,referenceNo); 
			cs1.setString(8,aadhaarNo); 
			//cs1.setBytes(9, aadhaarImage);
			cs1.setString(9, aadhaarImage);
			cs1.setString(10,pan); 
			//cs1.setBytes(11,panImage); 
			cs1.setString(11, panImage);
			cs1.setString(12,vehicleNumber); 
			//cs1.setBytes(13,rcImage);
			cs1.setString(13, getPath(rcImage, mobileNo, "rc", request));
			cs1.setString(14,amount); 
			cs1.setString(15,bankId); 
			//cs1.setString(16,referenceNo); 
			cs1.setString(16,mobileNo); 
			//cs1.setBytes(17,custPhoto); 
			cs1.setString(17,custPhoto);
			cs1.setString(18,address);
			cs1.setString(19,isCommercial);


			cs1.registerOutParameter(20,Types.BIGINT);
			cs1.execute();
			
			log.info("<<<<<<<<<<<<<<<<<<Wallet API Transaction >>>>>>>>>>>>>>>>>>>");
			log.info("Out Param2" +cs1.getLong(20)); 
			txnId = cs1.getLong(20)+"";
		}
		catch (Exception e)
		{
			log.error("NewVehcileRegistration.java  :::  getting error :: () ",e);

			e.printStackTrace();
		}
		finally
		{
			try
			{  
				if (cs1 != null) 
					cs1.close();
				/*if (cs != null) 
					cs.close();*/
				if (conn != null) 
					conn.close();

			} catch (Exception e)
			{
				log.error("NewVehcileRegistration.java  :::  getting error :: () ",e);

				e.printStackTrace();

			}

		}
		return txnId;
	}
	
	private String getPath(byte[] img,String mobile,String path,HttpServletRequest request)throws Exception
	{
			String spath = "RetailImages"+File.separator+path+File.separator+mobile+".jpg";
		String npath = getServletContext().getRealPath(spath);
		
		//String actualservepath[]=npath.split("")

		//System.out.println("ACTUAL SERVER PATH : " +npath);
		
		String kpath = npath.substring(0, npath.indexOf(request.getContextPath())) +File.separator+ spath;
		log.info("NEW SERVER PATH : " +kpath);
		
		File file = new File(kpath);
		file.createNewFile();
		FileOutputStream fout = new FileOutputStream(file);
		fout.write(img);
		fout.close();
		return kpath;
	}



	private String finalResponse(String errorCode,String txnId,String refNo,String sMessage)
	{
		JSONObject json = new JSONObject();
		if(!errorCode.equalsIgnoreCase("00")){
			json.put("txn_id", "NA");
			json.put("response_code", errorCode);
		}else
		{
			json.put("txn_id", txnId);
			json.put("response_code", errorCode);//errorcode
		}
		json.put("response_message", sMessage);
		json.put("reference_no", refNo);
		json.put("date_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		
		
		return json.toString();
	}
		
	
	private boolean validateParameters(JSONObject jsonObject){
		try{		//System.out.println(" Data  "+ jsonObject);

			String retailerId = jsonObject.getString("retailerId");//
			String partnerId = jsonObject.getString("partnerId");//
			String password = jsonObject.getString("password");//
			String bankId = jsonObject.getString("bankId");//
			//String cname = jsonObject.getString("name");//
			String mobileNo = jsonObject.getString("mobileNo");//
			String referenceNo = jsonObject.getString("referenceNo");//
			String dateTime = jsonObject.getString("dateTime");//
			//String gender = jsonObject.getString("gender");//
			//String aadhaarNo = jsonObject.getString("aadhaarNo");//
			//String pan = jsonObject.getString("pan");//
			//String email_id = jsonObject.getString("email_id");//
			String vehicleNo = jsonObject.getString("vehicleNumber");//
			//String aadhaarImage = jsonObject.getString("aadhaarImage");//
			//String panImage = jsonObject.getString("panImage");//
			String rcImage = jsonObject.getString("rcImage");//
			String amount = jsonObject.getString("amount"); 
			String address = jsonObject.getString("address");
			String isCommercial = jsonObject.getString("isCommercial"); 


			return true;
		}
		catch (Exception e)
		{
			log.error("NewRegistration.java  :::  getting error :: () ",e);

			e.printStackTrace();
		}
		
		return false;
	}
	
	public byte[] decodeImage(String imageString)
	{
		byte[] returnImgArray = null;
		
		try 
		{
			returnImgArray = Base64.getDecoder().decode(imageString.getBytes());
		}
		catch (Exception e) 
		{
			log.error("NewRegistration.java  :::  getting error :: () ",e);

			returnImgArray = null;
			e.printStackTrace();
		}
		
		return returnImgArray;
	}
	

}