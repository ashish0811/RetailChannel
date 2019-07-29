package com.onepay.retail.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DownloadKycDocs
 */
public class DownloadKycDocs extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public DownloadKycDocs() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String actionName = request.getParameter("actionName");
		System.out.println("DownloadKycDoc.java  ::: Action Name "+actionName);
	
	        try 
	    	{
	        	
	        	if(actionName.equalsIgnoreCase("DownloadKycDoc"))
	        	{
	        	String fileName = request.getParameter("downloadFileName");
	        	System.out.println("DownloadKycDoc.java  ::: fileName  :: "+fileName);
	        		
	        	FileInputStream in = new FileInputStream(new File(fileName));
	        		
	        	
	        	response.setContentType("application/octet-stream");
	    	    response.setHeader("Content-Disposition", "attachment; filename=\""+fileName.substring(fileName.lastIndexOf("/")+1)+"");
	        	ServletOutputStream out = response.getOutputStream();
	        	 
	        	byte[] outputByte = new byte[4096];
	        	//copy binary contect to output stream
	        	while(in.read(outputByte, 0, 4096) != -1)
	        	{
	        		out.write(outputByte, 0, 4096);
	        	}
	        	
	        	in.close();
	        	out.flush();
	        	out.close();
	        	}
	        
	        	/*else if(actionName.equalsIgnoreCase("UpdateBarcode"))
	        	{
	        		 String ref_id = request.getParameter("ref_id");
	        		 String barcode = request.getParameter("barcode");
	        		 String reqStatus = request.getParameter("barcode");
	        		 String remarks = request.getParameter("barcode");
	        		 String txnType = request.getParameter("barcode");
	        		 
	        		 System.out.println("ref_id  :: "+ref_id+"  barcode "+barcode);
	        		 
	        		 String status = new DataManager().UpdateBarCode(ref_id,reqStatus,barcode,remarks,txnType);
	        		 
	        		 if(status.equalsIgnoreCase("success"))
	        		 {
	        			
	        			 System.out.println("DownloadKycDocs.java BarcodeUpdation successfully");
	        			 request.setAttribute("message", "Barcode Update");
	        			 getServletConfig().getServletContext().getRequestDispatcher("/index.jsp").forward(request,response);
	        		 }
	        		 else
	        		 {
	        			 request.setAttribute("message", "BarcodeUpdation failed..");
	        			 System.out.println("DownloadKycDocs.java BarcodeUpdation failed..");
	        			 getServletConfig().getServletContext().getRequestDispatcher("/index.jsp").forward(request,response);
	        		 }
	        	}*/
	        	
	        	
	    	}
	        catch(Exception e)
	        {
	        	System.out.println("DownloadKycDoc.java ::: downloadDoc :: Error while downloading File : "+ e);
	        	
	    	}
		
	}
		

}
