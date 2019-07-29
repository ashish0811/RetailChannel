package com.wallet.reports;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONObject;

import com.onepay.retail.api.DataManager;
//import com.payone.pg.db.DataManager;

/**
 * Servlet implementation class ReportDisplay
 */
@WebServlet("/ReportDisplay")
public class ReportDisplayAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportDisplayAction() {
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
		
		DataManager dm = new DataManager();
		String txnType =request.getParameter("TXNType");
		String toDate = request.getParameter("toDate");
		String fromDate = request.getParameter("fromDate");
		String hiddenType = request.getParameter("actionType");
		//System.out.println(hiddenType);
		List<List<String>> reports = null;
		if(hiddenType.equals("reportDisp")){			
			try {
				reports = dm.dispReports(txnType,toDate,fromDate);
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			request.setAttribute("reports", reports);				
		}
		if(hiddenType.equals("download")){
			System.out.println(hiddenType);
			List<List<String>> reports1= null;
			try {
				reports1 = dm.downloadXLS();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			createReportDataToXLS(reports1);		
		}		
		getServletConfig().getServletContext().getRequestDispatcher("/index.jsp").forward(request,response);
	}
	
	private void createReportDataToXLS(List<List<String>> reportData) throws IOException {
		  Map< String, Object[]> reportDataMap = new TreeMap<String, Object[] >();
		  for (int i = 1; i <= reportData.size(); i++) {
			  List<String> rowData = reportData.get(i-1);
			  Object[] row = new Object[rowData.size()];
			  for (int j = 0; j < rowData.size(); j++) {
				  row[j] = rowData.get(j);
			  }
			  reportDataMap.put(i+"", row);
		  }
		  String fileName  = "report.xlsx";
		  
	      Set < String > keyid = reportDataMap.keySet();
	      
	      if (keyid != null && keyid.size() > 1) {
	    	  
	    	//Create blank workbook
		      XSSFWorkbook workbook = new XSSFWorkbook(); 
		      //Create a blank sheet
		      XSSFSheet spreadsheet = workbook.createSheet("ReportData");
		      //Create row object
		      XSSFRow row;
	    	  
		      int rowid = 0;
		      for (String key : keyid) {
		         row = spreadsheet.createRow(rowid++);
		         Object [] objectArr = reportDataMap.get(key);
		         int cellid = 0;
		         for (Object obj : objectArr)
		         {
		            Cell cell = row.createCell(cellid++);
		            cell.setCellValue((String)obj);
		         }
		      }
		      
		  //   String filePath = getServletConfig().getServletContext().getRealPath("E:/") +"sample";
			   String filePath = "D:/sample"; 
		      //create the upload folder if not exists
		      File folder = new File(filePath);
		      if(!folder.exists()){
		    	 folder.mkdir();
		      }
		      
		      File newFile = new File(filePath, fileName);
		      if(newFile.exists()){
		    	  newFile.delete();
		      }
	        FileOutputStream fos = new FileOutputStream(newFile);
	        workbook.write(fos);
	        fos.flush();
	        fos.close();
	        workbook.close();
	      }
	      
	}

}
