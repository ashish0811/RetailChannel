package com.wallet.reports;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.onepay.retail.api.DBConnection;
import com.onepay.retail.api.DataManager;

//import com.payone.pg.db.DataManager;

/**
 * Servlet implementation class login
 */
@WebServlet("/login")
public class login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session=request.getSession();  
		String userid = request.getParameter("userid");
		String pass = request.getParameter("password");
		System.out.println("User ID "+userid+"pass "+pass);
		try {
				if(new DataManager().getLogin(userid, pass)){
					String roleid = new DataManager().getRole(userid);
						if(roleid.equals("1")){
							response.sendRedirect("dashboard.jsp");
							
						}else{
							session.setAttribute("userId",userid);
							

							Connection conn = null;
							PreparedStatement ps = null;
							ResultSet rs = null;
							
							try{
								conn = new DBConnection().getConnection();
								String sql ="select * from user_master join account_master on user_master.userid = account_master.partner_id where userid=? and password=? and is_active=1";
								ps = conn.prepareStatement(sql);
								ps.setString(1,userid);
								ps.setString(2, pass);			
								rs = ps.executeQuery();
								if(rs.next()){
									System.out.println("Set Balance  ::: "+rs.getString("balance"));
									session.setAttribute("balance",rs.getString("balance"));
								}else
								{
									System.out.println("Getting Error using set balance :: in login.java ");
									 //back to loging screen 
								}
							}
							catch(Exception e){
								e.printStackTrace();
							}
							finally{
								rs.close();//close rs
								ps.close();//closed ps
								conn.close();//close conn
							}
							

							
							response.sendRedirect("partnerReports.jsp");
						}
				}else{
					//response.sendRedirect("login.jsp");
					
					request.setAttribute("invalid", "*Invalid UserName and Password");
					getServletConfig().getServletContext().getRequestDispatcher("/login.jsp").forward(request,response);
				}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
