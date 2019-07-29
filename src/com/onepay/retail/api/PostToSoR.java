package com.onepay.retail.api;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;

//import org.apache.poi.util.SystemOutLogger;
import org.json.JSONObject;

public class PostToSoR {

	public String postData(String Sordata, String URL){

		
		try{
			String data = Sordata;
			String line = null;	
			System.out.println("finalURL >>>>>> >>>> " +URL);
			URL url = new URL(URL);
			HttpURLConnection con = (HttpURLConnection)url.openConnection();

			con.setRequestMethod("POST");
			con.addRequestProperty("Content-Type", "application/json;charset=UTF-8");
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

			con.disconnect();
			response.toString();
			
			System.out.println("response :: "+response.toString());
			return response.toString();
		}catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	public static boolean getSpecialCharacterCount(String s) {
	     if (s == null || s.trim().isEmpty()) {
	         System.out.println("Incorrect format of string");
	         return false;
	     }
	     Pattern p = Pattern.compile("[^A-Za-z0-9]");
	     Matcher m = p.matcher(s);
	    // boolean b = m.matches();
	     boolean b = m.find();
	     if (b == true)
	        System.out.println("There is a special character in my string ");
	     else
	         System.out.println("There is no special char.");
	     return b;
	 }
	
	public static void main1(String[] args) {
		String s = "dhshdh";
		getSpecialCharacterCount(s);
	}

	public static void main(String[] args) throws Exception{

		/*SoRData params = new SoRData();
		params.setAadhaarNumber("290707909778");
		params.setAccountNo("123456");
		params.setAgentMobileNo("7666249024");
		params.setAmount("500");
		params.setAPITxnID("1234560892");
		params.setBankTxnID("7909099909");
		params.setBankRefID("3121");
		params.setCustomerMobileno("9833700965");
		params.setLatitude("0");
		params.setLongitude("0");
		params.setRemarks("Cash out transsaction");
		params.setSource("DT");
		params.setResponseCode("00");
		params.setResponseDescription("success");
		params.setTxnType("1");
		params.setTxnStatusRefID("2");
		params.setAuthId("8728278728");
		params.setDeviceID("PNB011");
		params.setCSRID("PN12345");
		params.setBankTerminalID("89898189899998000");
		params.setSTAN("123455");
		params.setAccountBalance("980.00");
		new PostToSoR().postData(params,"http://117.232.68.234:8080/api/AEPS/AEPSRequestSave");
		 */
		//File fi = new File("c://myfile.jpg");
		//byte[] fileContent = Files.readAllBytes(fi.toPath());
		//System.out.println("photo Data  "+Base64.getEncoder().encodeToString(fileContent));
		JSONObject json  = new JSONObject();
		//json.put("photograph", Base64.getEncoder().encodeToString(fileContent));
		json.put("vehicleNumber", "AP27UB5859");
		json.put("email_id", "ashishtest@gmail.com");
		json.put("pan", "AUCPT2800L");
		json.put("dateTime", "2018-12-10 15:10:57");//
		//json.put("password", "mlw@123");//
		json.put("password", "pnb@123");//
		//json.put("password", "aadhaarshila@123");//
		json.put("bankId", "HDFC");//
		json.put("amount", "700");
		json.put("referenceNo", "FT20190601229");//
		json.put("partnerId", "paynearby");//
		//json.put("partnerId", "aadhaarshila");//
		json.put("partnerId", "paynearby");//
		//json.put("mobileNo", "9831564879");//
		json.put("retailerId", "531262");//
		//json.put("barCode", "607799-001-1233");
		json.put("name", "rakesh");//
		json.put("gender", "male");
		json.put("aadhaarNo", "444433332222");
	//	json.put("accountNo", "92015564789321571");
		json.put("address", "kalyan");
		json.put("isCommercial", "N");

		//json.put("otp_proc", "validate");

		//json.put("otp_proc", "generate");
	    //json.put("userId", "admin");
		//json.put("password", "111111");
		json.put("mobileNo", "6380161988");
		json.put("smsBody", "Hello");
		json.put("userName", "ngpurxmltrans");
		json.put("smsPassword", "nnagrkbn");

		//json.put("otp", "374299");
		//json.put("teamId", "1");
		  
		File fi = new File("c://Users/ashish/Desktop/cust_reg.jpg");
		File fi1 = new File("c://Users/ashish/Desktop/ashish.jpg");

		byte[] fileContent = Files.readAllBytes(fi.toPath());
		byte[] fileContent1 = Files.readAllBytes(fi.toPath());
		byte[] fileContent2 = Files.readAllBytes(fi1.toPath());
		byte[] fileContent3 = Files.readAllBytes(fi1.toPath());

		//System.out.println("photo Data  "+Base64.getEncoder().encodeToString(fileContent));
		//JSONObject json  = new JSONObject();
		json.put("aadhaarImage", Base64.getEncoder().encodeToString(fileContent));
		json.put("panImage", Base64.getEncoder().encodeToString(fileContent1));
		json.put("rcImage", Base64.getEncoder().encodeToString(fileContent2));
		json.put("custPhoto", "");

		System.out.println(json);
		new PostToSoR().postData(json.toString(),"http://139.59.1.254:8080/RetailChannel/GetDetails");
		//new PostToSoR().postData(json.toString(),"https://1paypg.in/RetailChannel/GetDetailsV2");		
	}
}
