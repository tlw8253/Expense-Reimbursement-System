package com.tlw8253.dto;

import com.tlw8253.app.Constants;

public class ReimbursementTypeDTO extends AddOrEditDTO implements Constants{
	//Class attributes are store in the parent in HashMap tables.	

	/*Stored in hashmaps in the super class
	//ers_reimbursement_type table
	*/
	
	//
	//###
	public ReimbursementTypeDTO() {
		super();
	}

	public ReimbursementTypeDTO(String sReimbType, String sReimbTypeDesc) {
		setReimbType(sReimbType);
		setReimbTypeDescription(sReimbTypeDesc);
	}

	public String getReimbTypeId() {
		return super.getDataElement(csReimbTypeTblReimbTypeId);
	}
	public int getReimbTypeIdAsInt() {
		return super.getIntDataElement(csReimbTypeTblReimbTypeId);
	}

	public String getReimbType() {
		return super.getDataElement(csReimbTypeTblReimbType);
	}

	public String getReimbTypeDescription() {
		return super.getDataElement(csReimbTypeTblReimbTypeDesc);
	}

	public void setReimbTypeId(int reimbTypeId) {
		super.setDataElement(csReimbTypeTblReimbTypeId, reimbTypeId);
	}
	public void setReimbTypeId(String reimbTypeId) {
		super.setDataElement(csReimbTypeTblReimbTypeId, reimbTypeId);
	}

	public void setReimbType(String reimbType) {
		super.setDataElement(csReimbTypeTblReimbType, reimbType);
	}

	public void setReimbTypeDescription(String reimbTypeDesc) {
		super.setDataElement(csReimbTypeTblReimbTypeDesc, reimbTypeDesc);
	}

	@Override
	public String toString() {
		return super.toString();
	}


	
	
}
