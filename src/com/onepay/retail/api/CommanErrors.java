package com.onepay.retail.api;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;

import org.json.JSONObject;

public class CommanErrors {
	
	public String CommanErrorsApp(String mobileNo,String Amount,String referenceno,String partnerId,String bankId, String aadharImg, String panImg, String rcImg,String isCommercial)
	{
		String i="00";
		if(mobileNo==null || mobileNo.isEmpty())
			i="01";
		else if(Amount==null || Integer.parseInt(Amount)<500 || Amount.isEmpty())
			i="04";
		else if(referenceno==null || !processtxn(partnerId, referenceno).equals("0") || referenceno.isEmpty())
			i="03";
		else if(bankId==null || bankId.isEmpty())
			i="13";
		else if(partnerId==null || partnerId.isEmpty())
			i="14";
		else if(aadharImg==null || aadharImg.toString().length() <= 0 || aadharImg.toString().isEmpty())
			i="15";
		else if(panImg==null || panImg.toString().length() <= 0 || panImg.toString().isEmpty())
			i="16";
		else if(rcImg==null || rcImg.toString().length() <= 0 || rcImg.toString().isEmpty())
			i="17";
		else if(isCommercial==null || isCommercial.toString().isEmpty())
			i="19";
		else if(isCommercial.equalsIgnoreCase("Y") && Integer.parseInt(Amount)<700)
			i="20";
		
		return i;
	}
	
	public String CommanErrorsAppOld(String mobileNo,String Amount,String referenceno,String partnerId,String bankId, String aadharImg, String panImg, String rcImg)
	{
		String i="00";
		if(mobileNo==null || mobileNo.isEmpty())
			i="01";
		else if(Amount==null || Integer.parseInt(Amount)<500 || Amount.isEmpty())
			i="04";
		else if(referenceno==null || !processtxn(partnerId, referenceno).equals("0") || referenceno.isEmpty())
			i="03";
		else if(bankId==null || bankId.isEmpty())
			i="13";
		else if(partnerId==null || partnerId.isEmpty())
			i="14";
		else if(aadharImg==null || aadharImg.toString().length() <= 0 || aadharImg.toString().isEmpty())
			i="15";
		else if(panImg==null || panImg.toString().length() <= 0 || panImg.toString().isEmpty())
			i="16";
		else if(rcImg==null || rcImg.toString().length() <= 0 || rcImg.toString().isEmpty())
			i="17";
		
		
		return i;
	}
	
	public String CommanErrorsAppTopup(String mobileNo,String Amount,String referenceno,String partnerId,String bankId) throws ParseException
	{
		String i="00";
		if(mobileNo==null || mobileNo.isEmpty())
			i="01";
		else if(Amount==null || Integer.parseInt(Amount)<100 || Amount.isEmpty())
			i="04";
		else if(referenceno==null || !processtxn(partnerId, referenceno).equals("0") || referenceno.isEmpty())
			i="03";
		else if(bankId==null || bankId.isEmpty())
			i="13";
		else if(partnerId==null || partnerId.isEmpty())
			i="14";
		else {
			
			String sResp = new GetDetailsV2().processtxn(bankId,mobileNo);
			JSONObject jsonObject = new JSONObject(sResp);
			if(!jsonObject.getString("response_code").equalsIgnoreCase("00"))
			{
				i = "18";			
			}
			
		}
		
			return i;
	}
	
	public String CommanErrorsAddVehicle(String mobileNo,String Amount,String referenceno,String partnerId,String bankId,String vehicleNo, String rcImg, String isCommercial) throws ParseException
	{
		String i="00";
		if(mobileNo==null || mobileNo.isEmpty())
			i="01";
		else if(Amount==null || Integer.parseInt(Amount)<500 || Amount.isEmpty())
			i="04";
		else if(isCommercial.equalsIgnoreCase("Y") && Integer.parseInt(Amount)<700)
			i="20";
		else if(referenceno==null || !processtxn(partnerId, referenceno).equals("0") || referenceno.isEmpty())
			i="03";
		else if(null==vehicleNo || vehicleNo.isEmpty())
			i="11";
		else if(bankId==null || bankId.isEmpty())
			i="13";
		else if(partnerId==null || partnerId.isEmpty())
			i="14";
		else if(rcImg==null || rcImg.toString().length() <= 0 || rcImg.toString().isEmpty())
			i="17";
		else if(isCommercial==null || isCommercial.toString().isEmpty())
			i="19";
		else {
			
			String sResp = new GetDetailsV2().processtxn(bankId,mobileNo);
			JSONObject jsonObject = new JSONObject(sResp);
			if(!jsonObject.getString("response_code").equalsIgnoreCase("00"))
			{
				i = "18";			
			}
			
		}
		
		
		return i;
	}
	
	
	//address/PAN/Addhar/Email/Vehicle no/gender
	public String RegError(String mobile,String name,String address,String gender,String PAN,String aadharNo,String emailId,String vehicleNo,String bankId ){
		String i="00";
		
		if(null==name || name.isEmpty())
			i="05";
		else if(null==address || address.isEmpty())
			i="06";
		else if(null==gender || gender.isEmpty())
			i="07";
		else if(null==PAN || PAN.isEmpty())
			i="08";
		else if(null==aadharNo || aadharNo.isEmpty())
			i="09";
		else if(null==emailId || emailId.isEmpty())
			i="10";
		else if(null==vehicleNo || vehicleNo.isEmpty())
			i="11";
		else if(ismobileExist(bankId, mobile))
			i="02";
		
		return i;
	}
	
	private String processtxn(String partnerId,  String referenceNo) {
		
		String txnId="0";
		Connection conn = null;
		//CallableStatement cs = null;
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
			{  if (rs != null) 
					rs.close();
				if (ps2 != null) 
					ps2.close();
				if (conn != null) 
					conn.close();

			} catch (Exception e) {
				e.printStackTrace();

			}

		}
		
		return txnId;
	}
	
	public boolean ismobileExist(String bankId,  String mobileNo) {
		
		boolean isexists = false;
		Connection conn = null;
		PreparedStatement ps2 = null;
		ResultSet rs = null;
		
		try
		{
			
				conn = DBConnection.getConnection();
		    
				String q2 = "select txn_id from transaction_master where bank_id=? and mobile_no=? and transaction_type=?";
				ps2 = conn.prepareStatement(q2);
				ps2.setString(1, bankId);
				ps2.setString(2, mobileNo);
				ps2.setString(3, "Register");
				rs =ps2.executeQuery();
				if(rs.next()){
					isexists = true;
					//flag = rs.getString("txn_status").toString();
				}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally
		{
			try
			{  if (rs != null) 
					rs.close();
				if (ps2 != null) 
					ps2.close();
				if (conn != null) 
					conn.close();

			}  catch (Exception e) {
				e.printStackTrace();

			}

		}
		
		return isexists;
	}
	
	
	public String topUpError(){
		String i="0";
		
		return i;
	}
}
