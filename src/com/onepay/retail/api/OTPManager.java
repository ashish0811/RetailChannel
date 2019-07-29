package com.onepay.retail.api;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;


public class OTPManager {

	/*private Connection getConnecton() throws Exception
	{
		Context ctx = new InitialContext();
		DataSource ds = (DataSource)ctx.lookup("java:/middleware");
		return ds.getConnection();

	}*/



	public void createOTP(String appID,String mobile_number,String OTP) {

		deleteOTP(mobile_number);
		Connection con = null;
		PreparedStatement ps = null;

		try {
			String sql = "insert into otp_master(app_id,mobile_number,otp,date_time) values(?,?,?,?)";//,user_id,mobile_no,aadhar_number,resp_status,) values(?,?,?,?,?,?)";
			//con = getConnecton();
			con = DBConnection.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, appID);
			ps.setString(2, mobile_number);
			ps.setString(3, OTP);
			ps.setString(4, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) );
			ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {

				if(ps!=null)
					ps.close();
				if(con!=null)
					con.close();

			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

	}
	public boolean validateOTP(String mobile_number,String otp) {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "select * from otp_master where mobile_number=? and otp=?";//,user_id,mobile_no,aadhar_number,resp_status,) values(?,?,?,?,?,?)";
			//con = getConnecton();
			con = DBConnection.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, mobile_number);
			ps.setString(2, otp);
			rs = ps.executeQuery();
			if(rs.next()){
				deleteOTP(mobile_number);
				return true;
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
		finally {
			try {
				if(rs!=null)
					rs.close();
				if(ps!=null)
					ps.close();
				if(con!=null)
					con.close();

			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return false;
	}

	private void deleteOTP(String mobile_number) {
		insertToSMSLog(mobile_number);
		Connection con = null;
		PreparedStatement ps = null;

		try {
			String sql = "delete from otp_master where mobile_number=?";//,user_id,mobile_no,aadhar_number,resp_status,) values(?,?,?,?,?,?)";
			//con = getConnecton();
			con = DBConnection.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, mobile_number);
			ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {

				if(ps!=null)
					ps.close();
				if(con!=null)
					con.close();

			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

	}

	public void insertToSMSLog(String mobile_number) {


		Connection con = null;
		PreparedStatement ps = null;

		try {
			String sql = "insert into sms_log select * from otp_master where mobile_number=?";

			//con = getConnecton();
			con = DBConnection.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, mobile_number);
			/*ps.setString(2, mobile_number);
			ps.setString(3, OTP);
			ps.setString(4, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) );*/
			ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {

				if(ps!=null)
					ps.close();
				if(con!=null)
					con.close();

			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

	}
	public void updateSMSResp(String string, String c_mobile_no, String otp, String resp) {
		Connection con = null;
		PreparedStatement ps = null;

		try {
			String sql = "update otp_master set smsc_resp=? where mobile_number=? and otp=? and app_id=?";
			//con = getConnecton();
			con = DBConnection.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, resp);
			ps.setString(2, c_mobile_no);
			ps.setString(3, otp);
			ps.setString(4, string);
			ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {

				if(ps!=null)
					ps.close();
				if(con!=null)
					con.close();

			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

	}
	
	public void updateSMSRespNNSB(String string, String c_mobile_no, String text, String resp) {
		Connection con = null;
		PreparedStatement ps = null;
		String current_date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		

		try {
			String sql = "INSERT INTO sms_log ( app_id, mobile_number, otp, date_time, smsc_resp) values (?, ?, ?, ?, ?)";
			//con = getConnecton();
			con = DBConnection.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, "NNSBTxn");
			ps.setString(2, c_mobile_no);
			ps.setString(3, text);
			ps.setString(4, current_date);
			ps.setString(5, resp);
			ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {

				if(ps!=null)
					ps.close();
				if(con!=null)
					con.close();

			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

	}


}
