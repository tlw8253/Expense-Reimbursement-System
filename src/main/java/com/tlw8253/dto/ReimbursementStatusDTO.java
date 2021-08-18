package com.tlw8253.dto;

import com.tlw8253.app.Constants;

public class ReimbursementStatusDTO extends AddOrEditDTO implements Constants{
	//Class attributes are store in the parent in HashMap tables.	

	/*Stored in hashmaps in the super class
	//ers_reimbursement_status table
	private int reimbStatusId;
	private String reimbStatus;
	private String reimbStatusDesc;
	
	*/
	
	//
	//###
	public ReimbursementStatusDTO() {
		super();
	}

	public ReimbursementStatusDTO(String sReimbStatus, String sReimbStatusDesc) {
		setReimbStatus(sReimbStatus);
		setReimbStatusDescription(sReimbStatusDesc);
	}

	public String getReimbStatusId() {
		return super.getDataElement(csReimbStatusTblReimbStatusId);
	}
	public int getReimbStatusIdAsInt() {
		return super.getIntDataElement(csReimbStatusTblReimbStatusId);
	}

	public String getReimbStatus() {
		return super.getDataElement(csReimbStatusTblReimbStatus);
	}

	public String getReimbStatusDescription() {
		return super.getDataElement(csReimbStatusTblReimbStatusDesc);
	}

	public void setReimbStatusId(int reimbStatusId) {
		super.setDataElement(csReimbStatusTblReimbStatusId, reimbStatusId);
	}
	public void setReimbStatusId(String reimbStatusId) {
		super.setDataElement(csReimbStatusTblReimbStatusId, reimbStatusId);
	}

	public void setReimbStatus(String reimbStatus) {
		super.setDataElement(csReimbStatusTblReimbStatus, reimbStatus);
	}

	public void setReimbStatusDescription(String reimbStatusDesc) {
		super.setDataElement(csReimbStatusTblReimbStatusDesc, reimbStatusDesc);
	}

	@Override
	public String toString() {
		return super.toString();
	}


	
	
}
