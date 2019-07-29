package com.onepay.retail.api;

import org.json.JSONObject;

public class WalletRequestValidation {

	
	private String retailer_id,txn_type,cust_name,cust_mobile,cust_vehicleNo,bank_id,cust_aadhaar,cust_doc,amount,cust_walletId,cust_id,barcode,bankname;

	private boolean isValid;
	private String errorMessage;
	
	public String getRetailer_id() {
		return retailer_id;
	}
	public void setRetailer_id(String retailer_id) {
		this.retailer_id = retailer_id;
	}
	public String getTxn_type() {
		return txn_type;
	}
	public void setTxn_type(String txn_type) {
		this.txn_type = txn_type;
	}
	public String getCust_name() {
		return cust_name;
	}
	public void setCust_name(String cust_name) {
		this.cust_name = cust_name;
	}
	public String getCust_mobile() {
		return cust_mobile;
	}
	public void setCust_mobile(String cust_mobile) {
		this.cust_mobile = cust_mobile;
	}
	public String getCust_vehicleNo() {
		return cust_vehicleNo;
	}
	public void setCust_vehicleNo(String cust_vehicleNo) {
		this.cust_vehicleNo = cust_vehicleNo;
	}
	public String getBank_id() {
		return bank_id;
	}
	public void setBank_id(String bank_id) {
		this.bank_id = bank_id;
	}
	public String getCust_aadhaar() {
		return cust_aadhaar;
	}
	public void setCust_aadhaar(String cust_aadhaar) {
		this.cust_aadhaar = cust_aadhaar;
	}
	public String getCust_doc() {
		return cust_doc;
	}
	public void setCust_doc(String cust_doc) {
		this.cust_doc = cust_doc;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getCust_walletId() {
		return cust_walletId;
	}
	public void setCust_walletId(String cust_walletId) {
		this.cust_walletId = cust_walletId;
	}
	public String getCust_id() {
		return cust_id;
	}
	public void setCust_id(String cust_id) {
		this.cust_id = cust_id;
	}
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public String getBankname() {
		return bankname;
	}
	public void setBankname(String bankname) {
		this.bankname = bankname;
	}
	public boolean isValid() {
		return isValid;
	}
	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public WalletRequestValidation validateRequest(String jsonString){
		String message = "";
		isValid = true;
		String[] strVal = {"retailer_id,txn_type,cust_name,cust_mobile,cust_vehicleNo,bank_id,custAadhaar,custDoc,amount,custWalletId,custId,tagId,bankName"};

		try{									
			int j = 1;
			JSONObject jsonobject = new JSONObject(jsonString);
			System.out.println(strVal.length);
			for(int i=0;i<strVal.length;i++){
				if(null!=jsonobject.get(strVal[i]))
				{
					if(jsonobject.getString(strVal[i]).length()==0){													
						message = strVal[i]+" \t cannot be blank";
						System.out.println(strVal[i]+" cannot be blank "+j);
						isValid=false;		
						j = 0;				
					}				
				}else{
					
					setValues(i,jsonobject.getString(strVal[i]));
				}
			}
			if(j == 1){

				if(jsonobject.getString(strVal[4]).length()!=10){
					message = strVal[4]+" has invalid length";							
					System.out.println(strVal[4]+" has invalid length");
					isValid=false;	
				}
			}

			System.out.println(message);

			setErrorMessage(message);			
		}catch(Exception e){

			e.printStackTrace();
			isValid=false;
			setErrorMessage(e.getMessage());
		}		
		System.out.println(" && final message "+message);
		return this;
	}

	private void setValues(int i, String string) {
		//retailer_id,txn_type,cust_name,cust_mobile,cust_vehicleNo,bank_id,cust_aadhaar,cust_doc,amount,cust_walletId,cust_id,barcode,bankname
		if(i==0)
		setRetailer_id(string);	
		if(i==1)
			setTxn_type(string);
		if(i==2)
			setCust_name(string);
		if(i==3)
			setCust_mobile(string);
		if(i==4)
			setCust_vehicleNo(string);
		if(i==5)
			setBank_id(string);
		if(i==6)
			setCust_aadhaar(string);
		if(i==7)
			setCust_doc(string);
		if(i==8)
			setAmount(string);
		if(i==9)
			setCust_walletId(string);
		if(i==10)
			setCust_id(string);
		if(i==11)
			setBarcode(string);
		if(i==12)
			setBankname(string);
	}



}
