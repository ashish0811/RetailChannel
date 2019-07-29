package com.onepay.retail.api;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.ObjectInputStream.GetField;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;


/**
 * Servlet implementation class OTPGenerator
 */

public class OTPGenerator extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public OTPGenerator() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String finalResp=null;
		JSONObject jsonObject=null;
		StringBuffer jb = new StringBuffer();
		String line = null;
		String appid ="0";
		try {
			BufferedReader reader = request.getReader();
			while ((line = reader.readLine()) != null)
				jb.append(line);

			String sDecoded = jb.toString();
			System.out.println("decoded...."+sDecoded);
			jsonObject = new JSONObject(sDecoded);
			String otp_proc = jsonObject.getString("otp_proc");
			String user_id = jsonObject.getString("userId");
			String password = jsonObject.getString("password");
			//user validation hoga
			boolean b = new UserValidation().checkLoginDetails(user_id, password);
			if(b){


				String mobile_number = jsonObject.getString("mobileNo");
				if(otp_proc.equalsIgnoreCase("validate")){
					String otp = jsonObject.getString("otp");
					boolean bresp = new OTPManager().validateOTP(mobile_number, otp);
					finalResp=finalResponse("EE", "Failed","");;
					if(bresp)
					{
						finalResp=finalResponse("00", "Success","");
					}
				}
				else{
					String otp = generateMyNumber();
					new OTPManager().createOTP("ekyc", mobile_number, otp);
					sendSMS(otp, mobile_number);
					finalResp=finalResponse("00", "Success",otp);
				}
			}else {
				finalResp = finalResponse("-1","Invalid UserId/Password","");
			}

		}catch (Exception e) {
			e.printStackTrace();
			finalResp=finalResponse("E1", "System Error","");
		}

		//finalResp = getJsonResponse(finalResp);
		out.print(finalResp);
		out.flush();
	}

	private String finalResponse(String resp_code,String sMessage,String otp)
	{
		JSONObject json = new JSONObject();
		json.put("otp", otp);
		json.put("response_code", resp_code);
		json.put("response_message", sMessage);
		json.put("date_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));


		return json.toString();
	}
	/*private String getJsonResponse(String finalResp) {
		if(finalResp.equalsIgnoreCase("failed"))
			return sendErrorResponse();

		JSONObject json = new JSONObject();
		json.put("response_code", "00");
		json.put("response_message", "success");	
		JSONObject json1 = new JSONObject();
		json1.put("data", json.toString());
		return json1.toString();
	}

	private String sendErrorResponse() {

		JSONObject json = new JSONObject();
		json.put("error_type", "KYC_ERROR");
		json.put("error_description", "OTP generation failed!!");
		JSONObject json1 = new JSONObject();
		json1.put("errors", json.toString());
		System.out.println(json1.toString());
		return json1.toString();
	}*/
	public static String generateMyNumber()
	{
		int aNumber = 0; 
		aNumber = (int)((Math.random() * 9000000)+1000000); 
		//System.out.print((aNumber));
		return (aNumber+"").substring(0, 6);
	}


	private void sendSMS(String otp, String c_mobile_no) throws Exception{
		// TODO Auto-generated method stub
		String sURL = "http://apps.instatechnow.com/sendsms/sendsms.php?";

		URL obj = new URL(sURL);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		//add reuqest header

		con.setRequestMethod("POST");
		String urlParameters = "username=IS1pay&password=123456&type=TEXT&sender=BANKIT"
				+ "&mobile="+c_mobile_no+"&message="
				+ "Dear Customer,the OTP for RFID TAG ISSUANCE is "+otp+". Team BANKIT.";
		System.out.println(urlParameters);
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + sURL);
		System.out.println("Post parameters : " + urlParameters);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
				new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		//print result
		System.out.println(response.toString());
		new OTPManager().updateSMSResp("ekyc", c_mobile_no, otp,response.toString());



	}

	public static void main(String[] args) {
		System.out.println(generateMyNumber());
	}

}
