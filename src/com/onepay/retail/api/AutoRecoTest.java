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
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

public class AutoRecoTest extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AutoRecoTest() {
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
		//PrintWriter out = response.getWriter();
		/*String finalResp=null;
		JSONObject jsonObject=null;
		StringBuffer jb = new StringBuffer();
		String line = null;
		String appid ="0";*/
		//try {
			/*BufferedReader reader = request.getReader();
			while ((line = reader.readLine()) != null)
				jb.append(line);*/

			//String sDecoded = jb.toString();
			//System.out.println("decoded...."+sDecoded);
			//jsonObject = new JSONObject(sDecoded);
			////String otp_proc = jsonObject.getString("otp_proc");
		//	String user_id = jsonObject.getString("userId");
			//String password = jsonObject.getString("password");
			//user validation hoga
		/*	boolean b = new UserValidation().checkLoginDetails(user_id, password);
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
					//sendSMS(otp, mobile_number);
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
		out.flush();*/
	}

	/*private String finalResponse(String resp_code,String sMessage,String otp)
	{
		JSONObject json = new JSONObject();
		json.put("otp", otp);
		json.put("response_code", resp_code);
		json.put("response_message", sMessage);
		json.put("date_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));


		return json.toString();
	}*/
	
	public static String generateMyNumber()
	{
		int fileId = 0; 
		fileId = (int)((Math.random() * 9000000)+1000000); 
		//System.out.print((aNumber));
		return (fileId+"").substring(0,2 );
	}

public static String fileId=generateMyNumber();
	public static void fileUploadData() {
		//String fileId=generateMyNumber();
		String filename="RecoTest."+new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss").format(new Date());
		
		Connection con = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;

		try {
			String sql = "insert into tbl_fileupload(FileId,FileName,FileConfigId,FileType,UploadedOn,UploadedBy,Modified_On,Modified_By,Is_Deleted,statusCode,ServiceId) values(?,?,?,?,?,?,?,?,?,?,?)";//,user_id,mobile_no,aadhar_number,resp_status,) values(?,?,?,?,?,?)";
			//String sql1 = "insert into tbl_tmprecon(merchantid,TXN_ID,bank_txn_id,aggre_txn_id,amount,date_time,rrn,auth_id,txn_status,rfu1,rfu2,rfu3,rfu4,rfu5,rfu6,FileId,IsException,Exception,Counter) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";//,user_id,mobile_no,aadhar_number,resp_status,) values(?,?,?,?,?,?)";

			//con = getConnecton();
			con = DBConnectionPG.getConnection();
			ps = con.prepareStatement(sql);
			//ps1 = con.prepareStatement(sql1);
			ps.setString(1, fileId);
			ps.setString(2, filename);
			ps.setInt(3, 1);
			ps.setString(4, "xlsx");
			ps.setString(5, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) );
			ps.setString(6, "admin");
			ps.setString(7, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) );
			ps.setString(8, "admin");
			ps.setString(9, "N");
			ps.setString(10, "1");
			ps.setInt(11, 4);
			
			/*ps1.setString(1, "");
			ps1.setString(2, "");
			ps1.setString(3, "");
			ps1.setString(4, "");
			ps1.setString(5, "");
			ps1.setString(6, "");
			ps1.setString(7, "");
			ps1.setString(8, "");
			ps1.setString(9, "");
			ps1.setString(10, "");
			ps1.setString(11, "");
			ps1.setString(12, "");
			ps1.setString(13, "");
			ps1.setString(14, "");
			ps1.setString(15, "");
			ps1.setString(16, fileId);
			ps1.setString(17, "");
			ps1.setString(18, "");
			ps1.setString(19, "");*/
			ps.executeUpdate();
			//ps1.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {

				if(ps!=null)
					ps.close();
				if(ps1!=null)
					ps1.close();
				if(con!=null)
					con.close();

			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

	}
	
	public static void txnDataToTempReco(String[] anArray) {
		
		Connection con = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		String value = null;
		String allId = "";
		
		  int arrayLength = anArray.length;
		     for (int i = 0; i <= arrayLength-1; i++) {
		         value = anArray[i];
		         if(i==0)
	              {
	            	  allId = allId.concat("")+value;
	              }
	              else
	              {
	            	  allId = allId.concat(",")+"'"+value+"'";
	              }
		        System.out.println("The array contains the value: " + allId);
		     }
		
		

		try {
			//String sql = "select merchant_id,Id,service_rrn,service_txn_id,txn_amount,date_time from tbl_transactionmaster";
			//String sql1 = "update tbl_tmprecon set merchantid=?,TXN_ID=?,bank_txn_id=?,aggre_txn_id=?,amount=?,date_time=?,txn_status=?,FileId=?";
			String sql1 = "insert into tbl_tmprecon(merchantid,TXN_ID,bank_txn_id,aggre_txn_id,amount,date_time,rrn,auth_id) select merchant_id,Id,service_rrn,service_txn_id,txn_amount,date_time,service_rrn,service_auth_id from tbl_transactionmaster where txn_id in("+allId+")";//,user_id,mobile_no,aadhar_number,resp_status,) values(?,?,?,?,?,?)";
			String sql="update tbl_tmprecon set FileId=? where FileId is NULL";
			System.out.println("final query-------- " + sql1);
			//con = getConnecton();
			con = DBConnectionPG.getConnection();
			ps = con.prepareStatement(sql);
			ps1 = con.prepareStatement(sql1);
			//rs = ps.executeQuery();
			
			/*while (rs.next()) 
			{
				
				String merchantId= rs.getString("merchant_id");
				String txnId= rs.getString("Id");
				String bankTxnId= rs.getString("service_rrn");
				String aggrTxnId= rs.getString("service_txn_id");
				String amount= rs.getString("txn_amount");
				String dateTime= rs.getString("date_time");*/
				
				/*ps1.setString(1, merchantId);
				ps1.setString(2, txnId);
				ps1.setString(3, bankTxnId);
				ps1.setString(4, aggrTxnId);
				ps1.setString(5, amount);
				ps1.setString(6, dateTime);
				ps1.setString(7, "");
				ps1.setString(8, "");
				ps1.setString(9, "SUCCESS");
				ps1.setString(10, "");
				ps1.setString(11, "");
				ps1.setString(12, "");
				ps1.setString(13, "");
				ps1.setString(14, "");
				ps1.setString(15, "");
				ps1.setString(16, fileId);
				ps1.setString(17, "");
				ps1.setString(18, "");
				ps1.setString(19, "");*/
				
		//	}	
		
			//ps1.setString(1, "NRNS");
			ps.setString(1, fileId);
			ps1.executeUpdate();
			ps.executeUpdate();
			cs=con.prepareCall("{CALL pro_reconfinal(?)}"); 
			cs.setString(1,fileId);
			cs.execute();

			

		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(rs!=null)
					rs.close();
				if(ps!=null)
					ps.close();
				if(ps1!=null)
					ps1.close();
				if (cs != null) 
					cs.close();
				if(con!=null)
					con.close();

			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

	}
	
	/*private static void txnIdValues(String[] anArray) 
    {
	     int arrayLength = anArray.length;
	     for (int i = 0; i <= arrayLength-1; i++) {
	        String value = anArray[i];
	        System.out.println("The array contains the value: " + value);
	     }
	 }*/
	public static int convertToJulian(String unformattedDate)
    {
    /*Unformatted Date: ddmmyyyy*/
    int resultJulian = 0;
    if(unformattedDate.length() > 0)
    {
     /*Days of month*/
     int[] monthValues = {31,28,31,30,31,30,31,31,30,31,30,31};

     String dayS, monthS, yearS;
     dayS = unformattedDate.substring(0,2);
     monthS = unformattedDate.substring(2, 4);
     yearS = unformattedDate.substring(4, 8);

     /*Convert to Integer*/
     int day = Integer.valueOf(dayS);
     int month = Integer.valueOf(monthS);
     int year = Integer.valueOf(yearS); 

         //Leap year check
         if(year % 4 == 0)
         {
          monthValues[1] = 29;    
         }
         //Start building Julian date
         String julianDate = "";
         //last two digit of year: 2012 ==> 12
         julianDate += yearS.substring(3,4);

         int julianDays = 0;
         for (int i=0; i < month-1; i++)
         {
          julianDays += monthValues[i];
         }
         julianDays += day;

             if(String.valueOf(julianDays).length() < 2)
             {
              julianDate += "00";
             }
             if(String.valueOf(julianDays).length() < 3)
             {
              julianDate += "0";
             }

        julianDate += String.valueOf(julianDays);
    resultJulian =  Integer.valueOf(julianDate);
    
 }
 return resultJulian;
}
	public static void main(String[] args) throws IOException {
		System.out.println(generateMyNumber());
		//fileUploadData();
		//String array[] = {"100183321939337751","100183321939593153"};
		//txnDataToTempReco(array);
		String s= new SimpleDateFormat("ddMMyyyyHH").format(new Date());
		System.out.println(convertToJulian(s)+s.substring(8,10));
		//Runtime.getRuntime().exec("mysql -u root -p root123 -e 'SELECT * FROM pg.tbl_reconmaster'>reco.csv");
	}

}
