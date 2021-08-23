package com.tlw8253.dto;

import javax.sql.rowset.serial.SerialBlob;

import com.tlw8253.app.Constants;


public class AddReimbDTO extends AddOrEditDTO implements Constants{
	//Class attributes are store in the parent in HashMap tables.	
	//Contains the bare minimum for adding a Reimbursement record through the GUI
	//and as strings
	
	//
	//###
	public AddReimbDTO() {
		super();
	}

	public AddReimbDTO(String reimbAmount, String reimbDescription, String reimbReceipt, String reimbAuthorUsername, String sReimbType) {
		setReimbAmount(reimbAmount);
		setReimbDescription(reimbDescription);
		setReimbReceipt(reimbReceipt);
		//setReimbAuthorUsername(reimbAuthorUsername);
		setReimbType(sReimbType);		
	}

		
	public String getReimbAmount() {
		return super.getDataElement(csReimbTblReimbAmount);
	}

	public String getReimbDescription() {
		return super.getDataElement(csReimbTblReimbDescription);
	}

	public String getReimbReceipt() {
		return super.getDataElement(csReimbTblReimbReceipt);
	}

//	public String getReimbAuthorUsername() {
//		return super.getDataElement(csReimbTblReimbAuthorUName);
//	}

	public String getReimbType() {
		return super.getDataElement(csReimbTblReimbType);
	}





	public void setReimbAmount(String reimbAmount) {
		super.setDataElement(csReimbTblReimbAmount, reimbAmount);
	}

	public void setReimbDescription(String reimbDescription) {
		super.setDataElement(csReimbTblReimbDescription, reimbDescription);
	}

	public void setReimbReceipt(String reimbReceipt) {
		super.setDataElement(csReimbTblReimbReceipt, reimbReceipt);
	}

//	public void setReimbAuthorUsername(String reimbAuthorUsername) {
//		super.setDataElement(csReimbTblReimbAuthorUName, reimbAuthorUsername);
//	}


	public void setReimbType(String reimbType) {
		super.setDataElement(csReimbTblReimbType, reimbType);
	}



	@Override
	public String toString() {
		return super.toString();
	}


	
	
}
