package com.onepay.retail.api;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.wallet.reports.FileDao;
import com.wallet.reports.HDFCWallet;

/**
 * Servlet implementation class FileUploadHandler
 */

public class FileUploadHandler extends HttpServlet {

    private final String UPLOAD_DIRECTORY = "/home/HDFCWallet";
    //@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //process only if its multipart content
        if(ServletFileUpload.isMultipartContent(request))
        {
        	String name = null;
            try {

                List<FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
                for(FileItem item : multiparts)
                {
                    if(!item.isFormField())
                    {
                        name = new File(item.getName()).getName();
                        System.out.println("NAME :: "+name);
                        item.write( new File(UPLOAD_DIRECTORY + File.separator + name));
                    }
                }
                File newfile = new File(UPLOAD_DIRECTORY+File.separator+name);
                FileDao fileDao = new FileDao();
                //fileDao.txnCSVList(newfile);
                //System.out.println(fileDao.retailCSVList(newfile));
                List<HDFCWallet> hdfcList = fileDao.retailCSVList(newfile);
                fileDao.insertWallets(hdfcList);
                /*for(HDFCWallet per: ls)
                {
                	System.out.println(" mobile : "+per.getMobile()+" name : "+per.getName()+" email : "+per.getEmailId());
                }*/
                //File uploaded successfully
               request.setAttribute("message", "File Uploaded Successfully");

            } catch (Exception ex) {
               request.setAttribute("message", "File Upload Failed due to " + ex);
            }         
        }
        else
        {
            request.setAttribute("message","Sorry this Servlet only handles file upload request");
        }
        request.getRequestDispatcher("/updateWalletInfo.jsp").forward(request, response);
    }
}













/*@WebServlet("/FileUploadHandler")
public class FileUploadHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    *//**
     * @see HttpServlet#HttpServlet()
     *//*
    public FileUploadHandler() {
        super();
        // TODO Auto-generated constructor stub
    }

	*//**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 *//*
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	*//**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 *//*
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}*/
