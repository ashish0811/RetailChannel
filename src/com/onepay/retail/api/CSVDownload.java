package com.onepay.retail.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wallet.reports.AutoRecoCSVDao;

/**
 * Servlet implementation class CSVDownoad
 */
//@WebServlet("/CSVDownload")
public class CSVDownload extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CSVDownload() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/csv");
        String reportName =  "ReconReport_"
                +System.currentTimeMillis()+".csv";     
        response.setHeader("Content-disposition", "attachment; " +
                "filename=" + reportName);   

        ArrayList<String> rows = new ArrayList<String>();
        rows.add("Recon_Id, Txn_Id, Sp_Txn_Id, Bank_Txn_Id, Merchant_Txn_Id, Txn_Amount, Trans_Status, Recon_Status, Merchant_Id, Sp_Id, Bank_Id, Instrument_Id, Agg_Charges, Agg_GST, Bank_Charges, Bank_GST, Reseller_Charges, Reseller_GST, Sur_Charge, Net_Settlement, Total_Agg_Fees, Txn_Date, Recon_Date_Time, ReconUploadedBy, Payout_Generated, PayoutGeneratedBy, Payout_Date_Time, Modified_On, Modified_By");       
        rows.add("\n");

        /*for (int i = 0; i < 5; i++) {
            rows.add("Recon_Id, Txn_Id, Sp_Txn_Id, Bank_Txn_Id, Merchant_Txn_Id, Txn_Amount, Trans_Status, Recon_Status, Merchant_Id, Sp_Id, Bank_Id, Instrument_Id, Agg_Charges, Agg_GST, Bank_Charges, Bank_GST, Reseller_Charges, Reseller_GST, Sur_Charge, Net_Settlement, Total_Agg_Fees, Txn_Date, Recon_Date_Time, ReconUploadedBy, Payout_Generated, PayoutGeneratedBy, Payout_Date_Time, Modified_On, Modified_By");       
            rows.add("\n");
        }*/
        List<List<String>> list = AutoRecoCSVDao.downloadCSVReco();
        for (int i = 0; i < list.size() ; i++)
        {
			rows.add(list.get(i).get(0) + "," + list.get(i).get(1) + "," + list.get(i).get(3) + ","
					+ list.get(i).get(4) + "," + list.get(i).get(5) + "," + list.get(i).get(6) + ","
					+ list.get(i).get(7) + "," + list.get(i).get(8) + ","
					+ list.get(i).get(9) + "," + list.get(i).get(10) + "," + list.get(i).get(11)
					+ "," + list.get(i).get(12) + "," + list.get(i).get(13)+ "," + list.get(i).get(14)+ "," + list.get(i).get(15)+ "," + list.get(i).get(16)
					+ "," + list.get(i).get(17)+ "," + list.get(i).get(18)+ "," + list.get(i).get(19)+ "," + list.get(i).get(20)+ "," + list.get(i).get(21)
					+ "," + list.get(i).get(22)+ "," + list.get(i).get(23)+ "," + list.get(i).get(24)+ "," + list.get(i).get(25)+ "," + list.get(i).get(26)
					+ "," + list.get(i).get(27)+ "," + list.get(i).get(28));
			rows.add("\n");
		}
        
        
       

        Iterator<String> iter = rows.iterator();
        while (iter.hasNext()){
            String outputString = (String) iter.next();
            response.getOutputStream().print(outputString);
        }

        response.getOutputStream().flush();

    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
