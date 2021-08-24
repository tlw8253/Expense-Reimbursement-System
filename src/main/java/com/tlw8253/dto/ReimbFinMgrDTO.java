package com.tlw8253.dto;

import javax.sql.rowset.serial.SerialBlob;

import com.tlw8253.app.Constants;


public class ReimbFinMgrDTO extends AddOrEditDTO implements Constants{
	//Class attributes are store in the parent in HashMap tables.	
	//Contains the bare minimum for adding a Reimbursement record through the GUI
	//and as strings
	
	//
	//###
	public ReimbFinMgrDTO() {
		super();
	}

	public ReimbFinMgrDTO(String reimbId, String reimbStatus, String reimbDenyReason) {
		setReimbId(reimbId);
		setReimbStatus(reimbStatus);
		setReimbDenyReason(reimbDenyReason);
	}

	public String getReimbId() {
		return super.getDataElement(csReimbTblReimbId);
	}
	

	public String getReimbStatus() {
		return super.getDataElement(csReimbStatusTblReimbStatus);
	}

	public String getReimbDenyReason() {
		return super.getDataElement(csReimbStatusTblReimbStatusDenyReason);
	}

	public void setReimbId(String reimbId) {
		super.setDataElement(csReimbTblReimbId, reimbId);
	}

	public void setReimbStatus(String reimbStatus) {
		super.setDataElement(csReimbStatusTblReimbStatus, reimbStatus);
	}

	public void setReimbDenyReason(String reimbDenyReason) {
		super.setDataElement(csReimbStatusTblReimbStatusDenyReason, reimbDenyReason);
	}


	@Override
	public String toString() {
		return super.toString();
	}


	
	
}
