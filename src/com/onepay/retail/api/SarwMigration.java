package com.onepay.retail.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;


/**
 * Servlet implementation class TopupAccount
 */
public class SarwMigration extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SarwMigration() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Get Method not supported: ");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
	}
	
	public static Void GetDataFromMTPS() {
	
		
		Connection conn = null;
		Connection con = null;
		
		PreparedStatement ps = null;

		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;
		PreparedStatement ps4 = null;
		PreparedStatement ps5 = null;
		PreparedStatement ps6 = null;
		PreparedStatement ps7 = null;
		PreparedStatement ps8 = null;
		PreparedStatement ps9 = null;
		PreparedStatement ps10 = null;
		
		PreparedStatement psPo = null;
		PreparedStatement insertPo = null;
		PreparedStatement insertPoItem = null;
		PreparedStatement psInventory = null;
		PreparedStatement insertInventory= null;
		PreparedStatement psUnlinkedData= null;
		PreparedStatement txnDataMTPS= null;
		PreparedStatement txnData1Move= null;
		
		PreparedStatement challanDataMTPS= null;
		PreparedStatement challanData1Move= null;


		
		ResultSet rs = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		ResultSet rsPoData = null;
		ResultSet rsInventoryData = null;
		ResultSet rsUnlinkedData = null;
		ResultSet rsTransactionData = null;
		ResultSet rschallanData = null;
		

		try
		{
			
				conn = DBConnectionPG.getConnection();
				con = DBConnectionPG.getConnection1();
		    
				String q = "select TagRegistrationId,CustomerId,CustomerName,EmailId,MobileNumber,CompanyAddress,PAN,AadharNumber,IsCorporateCustomer,CustomerType,CustomerStatus,IsExemptedFromTollOnNH,IsExemptedFromTollOnSH,BankId,BankName,IFSCCode,VirtualAccountAddress,AccountNumber,AccountType,SecurityDepositAmount,MinThAmount,MaxThAmount,MonthlyLimit,AddtionalCustomerDocumentId,AdditionalDetails,CreatedBy,CreatedTime,LastUpdatedBy,LastUpdatedTime from TagRegistrations";
				String vTagData = "select VehicleDetailId,VehicleDetails.VehicleRegistrationNumber,VehicleDetails.VehicleClassMasterId,VehicleDetails.IsCommercialVehicle,VehicleDetails.TagDataId,VehicleDetails.TagRegistrationId,VehicleDetails.VehicleStatus,VehicleDetails.CreatedBy,VehicleDetails.CreatedTime,VehicleDetails.LastUpdatedBy,VehicleDetails.LastUpdatedTime,VehicleDetails.TagThresholdAmount,VehicleDetails.IsNewVehicle,VehicleDetails.SeurityDepositAmount,VehicleDetails.SeurityDepositPaymentMode,VehicleDetails.SeurityDepositIdentifier,VehicleDetails.EngineNumber,VehicleDetails.VIN,VehicleDetails.ChallanNumber,TagDatas.TID,TagDatas.EPCID,TagDatas.BarcodeData,TagDatas.SignatureData,VehicleClassMasters.VehicleClassCode,VehicleClassMasters.TagVCCode FROM MTCDB1.dbo.VehicleDetails inner join TagDatas on VehicleDetails.TagDataId=TagDatas.TagDataId inner join VehicleClassMasters on VehicleDetails.VehicleClassMasterId=VehicleClassMasters.VehicleClassMasterId where VehicleStatus=0";
				String vTagUnlinkedData = "SELECT VehicleDetails.VehicleDetailId,VehicleDetails.VehicleRegistrationNumber,VehicleDetails.VIN,VehicleDetails.EngineNumber,VehicleDetails.SeurityDepositIdentifier,VehicleDetails.SeurityDepositPaymentMode,VehicleDetails.SeurityDepositAmount,VehicleDetails.IsNewVehicle,VehicleDetails.TagThresholdAmount,VehicleDetails.VehicleClassMasterId,VehicleDetails.IsCommercialVehicle,VehicleDetails.TagDataId,VehicleDetails.TagRegistrationId,VehicleDetails.VehicleStatus,VehicleDetails.ChallanNumber,VehicleDetails.CreatedBy,VehicleDetails.CreatedTime,VehicleDetails.LastUpdatedBy,VehicleDetails.LastUpdatedTime,VehicleDetails.TagIssuanceCharges,TagDatas.TagDataId,TagDatas.TagPurchaseOrderId,TagDatas.TID,TagDatas.EPCID,TagDatas.BarcodeData,TagDatas.SignatureData,TagDatas.KillPassword,TagDatas.AccessPassword,TagDatas.DummyVRN,TagDatas.TagStatus,TagDatas.TagOwnerId,TagDatas.CreatedBy,TagDatas.CreatedTime,TagDatas.LastUpdatedBy,TagDatas.LastUpdatedTime,TagDatas.TagVehicleClass_TagVehicleClassId,VehicleClassMasters.VehicleClassCode FROM VehicleDetails right join TagDatas on VehicleDetails.TagDataId=TagDatas.TagDataId left join VehicleClassMasters on VehicleDetails.VehicleClassMasterId=VehicleClassMasters.VehicleClassMasterId";
				
				String txnDataSelect = "SELECT NETCTransactionsId,MessgeID,TransactionId,Note,ReferenceId,ReferenceUrl,TransactionTime,TransactionType,OriginalTransactionID,TagID,TID,AVC,WIM,MerchantId,MerchantType,MerchantSubType,LaneId,LaneDirection,LaneReaderId,ParkingFloor,ParkingZone,ParkingSlot,ParkingReaderId,ReaderReadTime,VehicleRegNo,VehicleStatus,IssueDate,ExceptionType,BankId,CommercialVehicle,TagSignature,SignatureAuth,TagVerified,ProcResResult,VehicleAuth,PublicKeyCVV,TransactionCounter,ReaderTxnStatus,PayerAddress,IssuerId,PayerCode,PayerName,PayerType,TransactionAmount,CurrencyCode,PayeeAddress,AcquirerID,PayeeCode,PayeeName,PayeeType,ResponseCode,TransactionStatus,ApprovalNum,IssuerErrorCode,ResponseAmount,ResponseCurrency,AccountType,AvailableBal,LedgerBal,AccountNumber,CustomerName,DisputeStatus,CreatedBy,CreatedTime,LastUpdatedBy,LastUpdatedTime,ResponsePublishTime,VehicleClassMaster_VehicleClassMasterId FROM NETCTransactions;";
				String txnDataInsert = "insert into req_pay_master(message_identifier, txn_timestamp, org_id, txn_id, acq_id, txn_type, txn_ref_id, original_txn_id, tag_id, tid, avc, wim, toll_plaza_id, toll_plaza_name, toll_geo_code, toll_plaza_type, plaza_reader_id, reader_ts, txn_amount, txn_time, vehicle_no, is_commercial, current_date_time, resp_status, resp_time, resp_id, cbs_reference_no, cbs_response_date, exception_code, recon_status,error_code) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				String respPayDataInsert ="insert into resp_pay_master( req_id, resp_xml, npci_resp_code, resp_date_time, post_status) values(?,?,?,?,?)";
				
				String challanDataSelect = "SELECT VehicleDetails.VehicleRegistrationNumber, VehicleDetails.CreatedTime, TagDatas.EPCID,  TagDatas.TID, VehicleDetails.ChallanNumber,VehicleDetails.VIN,VehicleDetails.EngineNumber FROM TagRegistrations INNER JOIN VehicleDetails ON TagRegistrations.TagRegistrationId = VehicleDetails.TagRegistrationId INNER JOIN TagDatas ON VehicleDetails.TagDataId = TagDatas.TagDataId where VehicleDetails.ChallanNumber <>'';";

				
				String cbsDataSelect = "SELECT  BankTransactionId,InternalTxnId,ACCOUNT_NUMBER,OPERATION,PLAZA_ID,PLAZA_NAME,VEHICLE_REG_NUMBER,REQUEST_REASON,TOLL_TXN_ID,ACQ_TXN_ID,TAGID,TXN_AMOUNT,CURRENCY,PAYMENT_TYPE,REVERSAL_ALLOWED,PAYMENT_LIABILITY,THRESHOLD_AMOUNT,ReaderReadTime,TxnTime,ApprovalNumber,AddInExceptionList,ResponseTime,TxnStatus,TxnStatusCode ,CreatedBy,CreatedTime,LastUpdatedBy,LastUpdatedTime FROM BankTransactions";
				String cbsDataInsert = "update req_pay_master set cbs_reference_no=?, cbs_response_date=?,error_code=? where InternalTxnId=";
				
				
				String q2 = "insert into customer_master(cust_id, branch_id, staff_name, email_id, contact_number, dob, is_active, created_by, created_on, approved_by, approved_on, is_deleted, is_approved) values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
				String q3 = "insert into account_master(user_id, acc_no, bank_name, branch_address, ifsc_code, account_type, iswallet, created_on, is_active, is_deleted) values(?,?,?,?,?,?,?,?,?,?)";
				String q4 = "insert into address_master(user_id, resi_add1, resi_address2, resi_pin, resi_city, resi_state, business_add1, business_add2, business_pin, business_city, business_state, is_deleted) values(?,?,?,?,?,?,?,?,?,?,?,?)";
				String q5 = "insert into customer_vehicle_master(vehicle_number, cust_id, vehicle_class_id, tag_class_id, engine_number, chassis_number, is_commercial, created_on, is_deleted) values(?,?,?,?,?,?,?,?,?)";
			//	String q6 = "insert into vehicle_document_master(vehicle_number, path_rc_book, path_insurance, path_front_pic, path_back_pic, other_doc1, other_doc2, other_doc3, is_deleted) values(?,?,?,?,?,?,?,?,?)";
				String q7 = "insert into customer_kyc_master(cust_id, address_proof_id, address_proof_no, address_proof_doc_path, id_proof, id_proof_no, id_proof_doc_path, is_deleted) values(?,?,?,?,?,?,?,?)";
				String q8 = "insert into vehicle_tag_linking(tid, tag_id, tag_class_id, vehicle_id, vehicle_number, customer_id, tag_status, signature_data, barcode_data, tag_pwd, kill_pwd, rfu1, rfu2, rfu3) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				
				String poData="SELECT TagPurchaseOrders.TagPurchaseOrderId,TagPurchaseOrders.OrderDateTime,TagPurchaseOrders.TagManufacturerId,TagPurchaseOrders.OrderStatus,TagPurchaseOrders.CreatedBy,TagPurchaseOrders.CreatedTime,TagPurchaseOrders.LastUpdatedBy,TagPurchaseOrders.LastUpdatedTime ,OrderItems.NumTags,OrderItems.ItemPrice,OrderItems.TagVehicleClassId,TagVehicleClasses.VehicleClassCode FROM OrderItems inner join TagPurchaseOrders on  TagPurchaseOrders.TagPurchaseOrderId =OrderItems.TagPurchaseOrderId inner join  TagVehicleClasses on TagVehicleClasses.TagVehicleClassId =OrderItems.TagVehicleClassId";
				
				//String vTagLinkingData="SELECT VehicleDetails.VehicleDetailId,VehicleDetails.VehicleRegistrationNumber,VehicleDetails.VIN,VehicleDetails.EngineNumber,VehicleDetails.SeurityDepositIdentifier,VehicleDetails.SeurityDepositPaymentMode,VehicleDetails.SeurityDepositAmount,VehicleDetails.IsNewVehicle,VehicleDetails.TagThresholdAmount,VehicleDetails.VehicleClassMasterId,VehicleDetails.IsCommercialVehicle,VehicleDetails.TagDataId,VehicleDetails.TagRegistrationId,VehicleDetails.VehicleStatus,VehicleDetails.ChallanNumber,VehicleDetails.CreatedBy,VehicleDetails.CreatedTime,VehicleDetails.LastUpdatedBy,VehicleDetails.LastUpdatedTime,VehicleDetails.TagIssuanceCharges,TagDatas.TagDataId,TagDatas.TagPurchaseOrderId,TagDatas.TID,TagDatas.EPCID,TagDatas.BarcodeData,TagDatas.SignatureData,TagDatas.KillPassword,TagDatas.AccessPassword,TagDatas.DummyVRN,TagDatas.TagStatus,TagDatas.TagOwnerId,TagDatas.CreatedBy,TagDatas.CreatedTime,TagDatas.LastUpdatedBy,TagDatas.LastUpdatedTime,TagDatas.TagVehicleClass_TagVehicleClassId,VehicleClassMasters.VehicleClassCode FROM VehicleDetails right join TagDatas on VehicleDetails.TagDataId=TagDatas.TagDataId left join VehicleClassMasters on VehicleDetails.VehicleClassMasterId=VehicleClassMasters.VehicleClassMasterId";
				String inventoryData="select TagDataId,TagPurchaseOrderId,TID,TagStatus,CreatedTime from TagDatas";
				String insertPoData="insert into po_master(po_id, po_date, po_status, supp_id, created_by, created_on, approved_by, approved_on, order_value, cgst, sgst, total_order_value) values(?,?,?,?,?,?,?,?,?,?,?,?)";
				String insertPoItemData="insert into po_item_master(po_id, tag_class_id, order_qty, unit_price) values(?,?,?,?)";
				String insertInventoryData="insert into inventory_master(po_id, tid, tag_class_id, tag_unique_id, branch_id, agent_id, status, date_time) values(?,?,?,?,?,?,?,?)";
				String challanDataInsert="insert into challan_master(tid, challan_id, tag_id, bank_name, vehicle_number, engine_number, chassis_number, created_date) values(?,?,?,?,?,?,?,?)";

				
				
				ps = conn.prepareStatement(q);
				ps9 = conn.prepareStatement(vTagData);
				
				psUnlinkedData = conn.prepareStatement(vTagUnlinkedData);
				
				txnDataMTPS = conn.prepareStatement(txnDataSelect);
				
				challanDataMTPS = conn.prepareStatement(challanDataSelect);
				
				psPo = conn.prepareStatement(poData);
				
				psInventory = conn.prepareStatement(inventoryData);
				
				
				rs =ps.executeQuery();
				rs2 =ps9.executeQuery();
				rsPoData=psPo.executeQuery();
				rsInventoryData=psInventory.executeQuery();
				rsUnlinkedData=psUnlinkedData.executeQuery();
				
				rsTransactionData=txnDataMTPS.executeQuery();
				
				rschallanData=challanDataMTPS.executeQuery();

				
				//System.out.println("Query result--------- " + rs.getString("CustomerId"));
				//ps2.executeQuery();
				
			/*	while(rs.next()){
					try {
						
						con.setAutoCommit(false);
						
					ps2 = con.prepareStatement(q2);
					ps3 = con.prepareStatement(q3);
					ps4 = con.prepareStatement(q4);
					ps7 = con.prepareStatement(q7);
					
					
					
					
					ps2.setString(1, rs.getString("TagRegistrationId"));
					
					if(null==rs.getString("CustomerId"))
						ps2.setString(2,"NA");
					else
						ps2.setString(2, rs.getString("CustomerId"));
					ps2.setString(3, rs.getString("CustomerName"));
					ps2.setString(4, rs.getString("EmailId"));
					ps2.setString(5, rs.getString("MobileNumber"));
					ps2.setString(6, "1985-02-04");
					ps2.setString(7, "1");
					ps2.setString(8, rs.getString("CreatedBy"));
					ps2.setString(9, rs.getString("CreatedTime"));
					ps2.setString(10, rs.getString("CreatedBy"));
					ps2.setString(11, rs.getString("CreatedTime"));
					ps2.setString(12, "0");
					ps2.setString(13, "1");
					
					
					//Account
					ps3.setString(1, rs.getString("TagRegistrationId"));
					ps3.setString(2, rs.getString("AccountNumber"));
					ps3.setString(3, rs.getString("BankName"));
					ps3.setString(4, "SARW");
					if(null== rs.getString("IFSCCode")) 
						ps3.setString(5,"NA");
					else
						ps3.setString(5, rs.getString("IFSCCode"));
					ps3.setString(6, rs.getString("AccountType"));
					ps3.setString(7, "Y");
					ps3.setString(8, rs.getString("CreatedTime"));
					ps3.setString(9, "1");
					ps3.setString(10, "0");
					
					//Address
					
					ps4.setString(1, rs.getString("TagRegistrationId"));
					ps4.setString(2, rs.getString("CompanyAddress"));
					ps4.setString(3,"NA");
					ps4.setString(4, "000000");
					ps4.setString(5, "NA");
					ps4.setString(6, "NA");
					ps4.setString(7, "NA");
					ps4.setString(8, "NA");
					ps4.setString(9, "000000");
					ps4.setString(10, "NA");
					ps4.setString(11, "NA");
					ps4.setString(12, "0");
					
					
					//kyc
					
					ps7.setString(1, rs.getString("TagRegistrationId"));
					ps7.setString(2, "aadhaar");
					if(null==rs.getString("AadharNumber"))
						ps7.setString(3,"NA");
					else
						ps7.setString(3,rs.getString("AadharNumber"));
					ps7.setString(4, "NA");
					ps7.setString(5, "PAN");
					if(null==rs.getString("PAN"))
						ps7.setString(6,"NA");
					else
						ps7.setString(6, rs.getString("PAN"));
					ps7.setString(7, "NA");
					ps7.setString(8, "0");
					
					
					
					ps2.executeUpdate();
					ps3.executeUpdate();
					ps4.executeUpdate();
					ps7.executeUpdate();
					
					
					String data ="SELECT TagRegistrations.TagRegistrationId,VehicleClassMasters.TagVCCode,VehicleDetails.VehicleRegistrationNumber,VehicleDetails.IsCommercialVehicle,VehicleDetails.CreatedBy,VehicleDetails.CreatedTime,VehicleDetails.EngineNumber,VehicleDetails.VIN,VehicleDetails.ChallanNumber,VehicleClassMasters.VehicleClassCode,TagDatas.EPCID, TagDatas.BarcodeData, TagDatas.TagOwnerId,TagDatas.TID FROM MTCDB1.dbo.TagRegistrations inner join dbo.VehicleDetails ON TagRegistrations.TagRegistrationId=VehicleDetails.TagRegistrationId inner join dbo.VehicleClassMasters ON dbo.VehicleDetails.VehicleClassMasterId = dbo.VehicleClassMasters.VehicleClassMasterId INNER JOIN dbo.TagDatas ON dbo.VehicleDetails.TagDataId = dbo.TagDatas.TagDataId where TagRegistrations.TagRegistrationId = "+rs.getString("TagRegistrationId");
					ps8 = conn.prepareStatement(data);
					rs1 =ps8.executeQuery();
					while(rs1.next()){
						try {
							
							con.setAutoCommit(false);
							
							ps5 = con.prepareStatement(q5);
					
					    ps5.setString(1, rs1.getString("VehicleRegistrationNumber"));
						ps5.setString(2,rs.getString("TagRegistrationId"));//cust id from mtps rs
						ps5.setString(3, rs1.getString("VehicleClassCode"));
						ps5.setString(4, rs1.getString("TagVCCode"));
						if(null==rs1.getString("EngineNumber"))
							ps5.setString(5,"NA");
						else
							ps5.setString(5,rs1.getString("EngineNumber"));
						if(null==rs1.getString("VIN"))
							ps5.setString(6,"NA");
						else
						ps5.setString(6, rs1.getString("VIN"));
						ps5.setString(7, rs1.getString("IsCommercialVehicle"));
						ps5.setString(8, rs1.getString("CreatedTime"));
						ps5.setString(9, "0");
						ps5.executeUpdate();
						//	ps6.executeUpdate();
							
							con.commit();
							System.out.println("Record added for vehicle id >>>> "+rs1.getString("VehicleRegistrationNumber"));
							
							} catch (Exception e) {
								System.out.println("Record failed for vehicle id >>>> "+rs1.getString("VehicleRegistrationNumber"));
								e.printStackTrace();
							}
						}
					
				
					//flag = rs.getString("txn_status").toString();
					
					
					
					con.commit();
					System.out.println("Record added for customer id >>>> "+rs.getString("TagRegistrationId"));
					
					} catch (Exception e) {
						System.out.println("Record failed for customer id >>>> "+rs.getString("TagRegistrationId"));
						e.printStackTrace();
					}
				}*/
					
					
					
			/*while(rs2.next()){
					try {
						
						con.setAutoCommit(false);
						
						ps10 = con.prepareStatement(q8);
						//ps6 = con.prepareStatement(q6);
						
						
						
						//tag linked vehicle
					
					ps10.setString(1, rs2.getString("TID"));
					
					
					
					ps10.setString(2, rs2.getString("EPCID"));
					ps10.setString(3, rs2.getString("TagVCCode"));
					ps10.setString(4, "NA");
					ps10.setString(5, rs2.getString("VehicleRegistrationNumber"));
					ps10.setString(6, rs2.getString("TagRegistrationId"));
					ps10.setString(7, "0");
					ps10.setString(8, rs2.getString("SignatureData"));
					ps10.setString(9, rs2.getString("BarcodeData"));
					ps10.setString(10, "tag001");
					ps10.setString(11, "kill007");
					ps10.setString(12, "NA");
					ps10.setString(13, "NA");
					ps10.setString(14, "NA");
						
						//document 
						
						//ps6.setString(1, "testVehicle");
						ps6.setString(1, rs1.getString("VehicleRegistrationNumber"));
						ps6.setString(2,"NA");
						ps6.setString(3, "NA");
						ps6.setString(4, "NA");
						ps6.setString(5, "NA");
						ps6.setString(6, "NA");
						ps6.setString(7, "NA");
						ps6.setString(8, "NA");
						ps6.setString(9, "0");
						
						
					
					ps10.executeUpdate();
					//	ps6.executeUpdate();
						
						con.commit();
						System.out.println("Record added for vehicle id >>>> "+rs2.getString("VehicleRegistrationNumber"));
						
						} catch (Exception e) {
							System.out.println("Record failed for vehicle id >>>> "+rs2.getString("VehicleRegistrationNumber"));
							e.printStackTrace();
						}
					}*/
				
			/*	while(rsPoData.next()){
					try {
						
						con.setAutoCommit(false);
						
						insertPo = con.prepareStatement(insertPoData);
						insertPoItem = con.prepareStatement(insertPoItemData);
						
						
						
						//po master
					
					insertPo.setString(1, rsPoData.getString("TagPurchaseOrderId"));
					insertPo.setString(2, rsPoData.getString("OrderDateTime"));
					insertPo.setString(3, "3");
					insertPo.setString(4, rsPoData.getString("TagManufacturerId"));
					insertPo.setString(5, rsPoData.getString("CreatedBy"));
					insertPo.setString(6, rsPoData.getString("CreatedTime"));
					insertPo.setString(7, rsPoData.getString("LastUpdatedBy"));
					insertPo.setString(8, rsPoData.getString("LastUpdatedTime"));
					insertPo.setString(9, "0");
					insertPo.setString(10, "0");
					insertPo.setString(11, "0");
					insertPo.setString(12, "0");
					
						
						//po item master 
						
						//ps6.setString(1, "testVehicle");
					insertPoItem.setString(1, rsPoData.getString("TagPurchaseOrderId"));
					insertPoItem.setString(2,"VC4");
					insertPoItem.setString(3, rsPoData.getString("NumTags"));
					insertPoItem.setString(4, rsPoData.getString("ItemPrice"));
						
						
						
					
					insertPo.executeUpdate();
					insertPoItem.executeUpdate();
						
						con.commit();
						System.out.println("Record added for order id >>>> "+rsPoData.getString("TagPurchaseOrderId"));
						
						} catch (Exception e) {
							System.out.println("Record failed for order id >>>> "+rsPoData.getString("TagPurchaseOrderId"));
							e.printStackTrace();
						}
					}*/
				
			/*	while(rsInventoryData.next()){
					try {
						
						con.setAutoCommit(false);
						
						insertInventory = con.prepareStatement(insertInventoryData);
						
						
						
						//inv master
					
						insertInventory.setString(1, rsInventoryData.getString("TagPurchaseOrderId"));
						insertInventory.setString(2, rsInventoryData.getString("TID"));
						insertInventory.setString(3, "VC4");
						insertInventory.setString(4, "0");
						insertInventory.setString(5, "0");
						insertInventory.setString(6, "0");
						insertInventory.setString(7, "3");
						insertInventory.setString(8, rsInventoryData.getString("CreatedTime"));
					
						//po item master 
					
						insertInventory.executeUpdate();
						
						con.commit();
						System.out.println("Record added for  tid >>>> "+rsInventoryData.getString("TID"));
						
						} catch (Exception e) {
							System.out.println("Record failed for  tid >>>> "+rsInventoryData.getString("TID"));
							e.printStackTrace();
						}
					}
				
				
				while(rsUnlinkedData.next()){
					try {
						
						con.setAutoCommit(false);
						
						psUnlinkedData = con.prepareStatement(q8);
						
						
						
						//po master
						psUnlinkedData.setString(1, rsUnlinkedData.getString("TID"));
						psUnlinkedData.setString(2, rsUnlinkedData.getString("EPCID"));
						psUnlinkedData.setString(3, "NA");
						psUnlinkedData.setString(4, "NA");
						psUnlinkedData.setString(5, rsUnlinkedData.getString("VehicleRegistrationNumber"));
						psUnlinkedData.setString(6, rsUnlinkedData.getString("TagRegistrationId"));
						psUnlinkedData.setString(7, "0");
						psUnlinkedData.setString(8, rsUnlinkedData.getString("SignatureData"));
						psUnlinkedData.setString(9, rsUnlinkedData.getString("BarcodeData"));
						psUnlinkedData.setString(10, "tag001");
						psUnlinkedData.setString(11, "kill007");
						psUnlinkedData.setString(12, "NA");
						psUnlinkedData.setString(13, "NA");
						psUnlinkedData.setString(14, "Now");
					
						//po item master 
					
						psUnlinkedData.executeUpdate();
						
						con.commit();
						System.out.println("Record added for  tid >>>> "+rsUnlinkedData.getString("TID"));
						
						} catch (Exception e) {
							System.out.println("Record failed for  tid >>>> "+rsUnlinkedData.getString("TID"));
							e.printStackTrace();
						}
					}*/
				
				while(rsTransactionData.next()){
					try {
						
						con.setAutoCommit(false);
						
						txnData1Move = con.prepareStatement(txnDataInsert);//req pay
					//	txnData1Move = con.prepareStatement(respPayDataInsert);
						
						
					//	txnData1Move.setString(6, rsTransactionData.getString("TransactionType"));
						
						//txn master
						txnData1Move.setString(1, rsTransactionData.getString("MessgeID"));
						txnData1Move.setString(2, rsTransactionData.getString("TransactionTime"));
						txnData1Move.setString(3, rsTransactionData.getString("IssuerId"));
						txnData1Move.setString(4, rsTransactionData.getString("TransactionId"));
						txnData1Move.setString(5, rsTransactionData.getString("PayerCode"));
						txnData1Move.setString(6, rsTransactionData.getString("TransactionType"));
						txnData1Move.setString(7, rsTransactionData.getString("ReferenceId"));
						txnData1Move.setString(8, rsTransactionData.getString("OriginalTransactionID"));
						txnData1Move.setString(9, rsTransactionData.getString("TagID"));
						txnData1Move.setString(10, rsTransactionData.getString("TID"));
						txnData1Move.setString(11, rsTransactionData.getString("AVC"));
						txnData1Move.setString(12, rsTransactionData.getString("WIM"));
						txnData1Move.setString(13, rsTransactionData.getString("MerchantId"));
						txnData1Move.setString(14, "NA");
						txnData1Move.setString(15, rsTransactionData.getString("PayerName"));
						txnData1Move.setString(16, rsTransactionData.getString("MerchantType"));
						txnData1Move.setString(17, rsTransactionData.getString("LaneReaderId"));
						txnData1Move.setString(18,rsTransactionData.getString("ReaderReadTime"));
						txnData1Move.setString(19, rsTransactionData.getString("TransactionAmount"));
						txnData1Move.setString(20, rsTransactionData.getString("CreatedTime"));
						txnData1Move.setString(21, rsTransactionData.getString("VehicleRegNo"));
						txnData1Move.setString(22, rsTransactionData.getString("CommercialVehicle"));
						txnData1Move.setString(23, rsTransactionData.getString("CreatedTime"));//now
						txnData1Move.setString(24, "0");
						txnData1Move.setString(25, rsTransactionData.getString("CreatedTime"));//now
						txnData1Move.setString(26, "00");
						txnData1Move.setString(27, rsTransactionData.getString("ResponsePublishTime"));//cbs txn id
						txnData1Move.setString(28, rsTransactionData.getString("ResponsePublishTime"));//cbs resp date
						txnData1Move.setString(29, "000");
						txnData1Move.setString(30, "RNS");
						txnData1Move.setString(31, "00");
						
					/*	txnData1Move.setString(1, rsTransactionData.getString("TransactionId"));
						txnData1Move.setString(2, "NA");
						txnData1Move.setString(3,"NA");
						txnData1Move.setString(4, "2019-04-30 05:22:17");
						txnData1Move.setString(5,"0");*/
					
						//po item master 
					
						txnData1Move.executeUpdate();
						
						con.commit();
						System.out.println("Record added for Transaction Id >>>> "+rsTransactionData.getString("TransactionId"));
						
						} catch (Exception e) {
							System.out.println("Record failed for Transaction Id >>>> "+rsTransactionData.getString("TransactionId"));
							e.printStackTrace();
						}
					}
				
			/*	while(rschallanData.next()){
					try {
						
						con.setAutoCommit(false);
						
						challanData1Move = con.prepareStatement(challanDataInsert);
						
						
						
						//txn master
						challanData1Move.setString(1, rschallanData.getString("TID"));
						
						challanData1Move.setString(2, "652150-"+rschallanData.getString("ChallanNumber")+"-1");
						challanData1Move.setString(3, rschallanData.getString("EPCID"));
						challanData1Move.setString(4, "SARASWAT");
						challanData1Move.setString(5, rschallanData.getString("VehicleRegistrationNumber"));
						if(rschallanData.getString("EngineNumber")==null) {
							challanData1Move.setString(6,"NA");
						}
						else {
						challanData1Move.setString(6, rschallanData.getString("EngineNumber"));
						}
						if(rschallanData.getString("VIN")==null) {
							challanData1Move.setString(7,"NA");
						}
						else {
						challanData1Move.setString(7, rschallanData.getString("VIN"));
						}
						challanData1Move.setString(8, rschallanData.getString("CreatedTime"));
						
					
						//po item master 
					
						challanData1Move.executeUpdate();
						
						con.commit();
						System.out.println("Record added for ChallanNumber >>>> "+rschallanData.getString("ChallanNumber"));
						
						} catch (Exception e) {
							System.out.println("Record failed for ChallanNumber >>>> "+rschallanData.getString("ChallanNumber"));
							e.printStackTrace();
						}
					}*/
				
				/*String bank = null;
				try {
				InitialContext initialContext = new javax.naming.InitialContext();
				bank = (String) initialContext.lookup("java:comp/env/host");
				System.out.println("varibale is"+bank);
				}
				catch (NamingException e) {
				e.getMessage();
				}*/
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally
		{
			try
			{  
				if (ps != null) 
					ps.close();
				if (ps2 != null) 
					ps2.close();
				if (ps3 != null) 
					ps3.close();
				if (ps4 != null) 
					ps4.close();
				if (ps5 != null) 
					ps5.close();
				if (ps6 != null) 
					ps6.close();
				if (ps7 != null) 
					ps7.close();
				if (ps8 != null) 
					ps8.close();
				if (ps9 != null) 
					ps9.close();
				if (ps10 != null) 
					ps10.close();
			
				
				if (psPo != null) 
					psPo.close();
				if (insertPo != null) 
					insertPo.close();
				if (insertPoItem != null) 
					insertPoItem.close();
				if (psInventory != null) 
					psInventory.close();
				if (insertInventory != null) 
					insertInventory.close();
				if (psUnlinkedData != null) 
					psUnlinkedData.close();
				if (txnDataMTPS != null) 
					txnDataMTPS.close();
				if (txnData1Move != null) 
					txnData1Move.close();
				if (challanDataMTPS != null) 
					challanDataMTPS.close();
				if (challanData1Move != null) 
					challanData1Move.close();
				if (conn != null) 
					conn.close();
				if (con != null) 
					con.close();

			} catch (Exception e) {
				e.printStackTrace();

			}

		}
		
		return null;
	}

	
	public static void main(String[] args) throws IOException {
		System.out.println("migrtaion starts now...");
		//String bank=null;
		//InitialContext initialContext;
		try {
			//System.out.println("variable starts now...");
			/*initialContext = new javax.naming.InitialContext();
			bank = (String) initialContext.lookup("java:comp/env/bank ");*/
			//getServletContext getServletContext = new getServletContext();
			//String testname = getServletContext().getInitParameter("testname");
			//System.out.println(testname);
			//System.out.println("varibale is"+bank);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		GetDataFromMTPS();
		
		//Runtime.getRuntime().exec("mysql -u root -p root123 -e 'SELECT * FROM pg.tbl_reconmaster'>reco.csv");
	}

}
