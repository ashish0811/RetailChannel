package com.onepay.retail.api;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;


public class SMSNNSB extends HttpServlet 
{
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.getWriter().append("Get Method Not Allowed");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		PrintWriter out = response.getWriter();
		String finalResp=null;

		String userName = request.getParameter("userName");
		String smsPassword = request.getParameter("smsPassword");
		String mobileNo = "91"+request.getParameter("mobileNo");
		String smsBody = request.getParameter("smsBody");
		
		System.out.println("SMSNNSB.java ::: Mobile number : "+mobileNo+" And Text message : "+smsBody);
		
		try 
		{
			boolean b = new UserValidation().checkLoginDetails(userName, smsPassword);

			if(b)
			{			
				String resp = sendSMS(smsBody,mobileNo,userName,smsPassword);	
				
				System.out.println("SMSNNSB.java ::: Final Response from SMS Function :: "+resp);
				finalResp = finalResponse(resp);				
			}
			else 
			{
				finalResp = finalResponse("Fail");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			finalResp = finalResponse( "System Error");
		}

		out.print(finalResp);
		out.flush();
		out.close();
	}

	private String finalResponse(String sMessage)
	{
		/*JSONObject json = new JSONObject();
		json.put("otp", otp);
		json.put("response_code", resp_code);
		json.put("response_message", sMessage);
		json.put("date_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		 */

		return sMessage;
	}	

	public static String sendSMS(String text, String c_mobile_no, String smsUser, String smsPwd) throws Exception
	{	
		/*String smsUser = "ngpurxmltrans";
		String smsPwd = "nnagrkbn";*/

		String sURL = "http://api.myvaluefirst.com/psms/servlet/psms.Eservice2";		

		String finalResp = null;

		String xmlString = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?><!DOCTYPE MESSAGE SYSTEM \"http://127.0.0.1:80/psms/dtd/messagev12.dtd\"><MESSAGE VER=\"1.2\"><USER USERNAME=\""+smsUser+"\" PASSWORD=\""+smsPwd+"\" /><SMS  UDH=\"0\" CODING=\"1\" TEXT=\""+text+"\" PROPERTY=\"0\" ID=\"1\" TEMPLATE=\"\" EMAILTEXT=\"\" ATTACHMENT=\"\"><ADDRESS FROM=\"NNSBNK\" TO=\""+c_mobile_no+"\" EMAIL=\"\" SEQ=\"1\" TAG=\"123456\"/></SMS></MESSAGE>";

		String postingParams = "action=send&data="+ URLEncoder.encode(xmlString,"UTF-8");

		System.out.println("SMSNNSB.java ::: sendSMS() :: Sending 'POST' request for data : " + xmlString);

		URL obj = new URL(sURL);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("POST");	
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(postingParams);
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();

		System.out.println("SMSNNSB.java ::: sendSMS() :: Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null)
		{
			response.append(inputLine);			 
		}

		in.close();
		if(responseCode!=200) {

		}
		//print result
		System.out.println("SMSNNSB.java ::: sendSMS() :: SML Response String : "+response.toString());
		try
		{
			Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new StringReader(response.toString())));
			NodeList txnNode = doc.getElementsByTagName("GUID");
			String guId = null;
			String code = null;
			String resp = null;

			//String respmsg = null;
			if (txnNode.getLength() > 0) 
			{
				Element respCodeElement = (Element) txnNode.item(0);
				guId = respCodeElement.getAttribute("GUID");
				System.out.println("SMSNNSB.java ::: sendSMS() :: GUID :: "+guId);

				if(doc.getElementsByTagName("ERROR").getLength()>0)
				{	
					NodeList txnNode1 = doc.getElementsByTagName("ERROR");
					Element respCodeElement1 = (Element) txnNode1.item(0);
					code = respCodeElement1.getAttribute("CODE");
					System.out.println("SMSNNSB.java ::: sendSMS() :: CODE :: "+code);
				}
				if(code != null) 
				{
					resp ="GUID-"+ guId+" Error Code-"+ code ;
					finalResp = "Fail";				
				}
				else
				{
					resp = "GUID-"+guId;
					finalResp = "SUCCESS";
				}

				new OTPManager().updateSMSRespNNSB("NNSBTxn", c_mobile_no, text,resp.toString());			
			}
		}
		catch (Exception e) 
		{
			System.out.println("SMSNNSB.java ::: sendSMS() :: Exception occurred while parsing SMS XML response : "+e.getMessage());
			e.printStackTrace();
		}

		return finalResp;
	}
	
	
	/*private String getJsonResponse(String finalResp)
	 {
		if(finalResp.equalsIgnoreCase("failed"))
			return sendErrorResponse();

		JSONObject json = new JSONObject();
		json.put("response_code", "00");
		json.put("response_message", "success");	
		JSONObject json1 = new JSONObject();
		json1.put("data", json.toString());
		return json1.toString();
	}

	private String sendErrorResponse() 
	{
		JSONObject json = new JSONObject();
		json.put("error_type", "KYC_ERROR");
		json.put("error_description", "OTP generation failed!!");
		JSONObject json1 = new JSONObject();
		json1.put("errors", json.toString());
		System.out.println(json1.toString());
		return json1.toString();
	} */

	public static void main(String[] args) throws Exception 
	{
		sendSMS("Customer, Your A/c has been debited by Rs.100.00 for toll by NN45SB4545 at  tollplaza on 2018-11-29.Bal amt in FASTAG A/c is Rs ***","917388875660","ngpurxmltrans","nnagrkbn");
	}
}
