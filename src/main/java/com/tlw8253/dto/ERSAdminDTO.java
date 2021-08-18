package com.tlw8253.dto;

import com.tlw8253.app.Constants;

public class ERSAdminDTO extends AddOrEditDTO implements Constants{
	//Class attributes are store in the parent in HashMap tables.	

	/*Stored in hashmaps in the super class
	//ers_reimbursement_status table
	private int reimbStatusId;
	private String reimbStatus;
	
	//ers_reimbursement_status table
	private int reimbTypeId;
	private String reimType;
	
	//ers_user_roles table
	private int userRoleId;
	private String userRole;
	*/
	
	//
	//###
	public ERSAdminDTO() {
		super();
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


	public String getReimbTypeId() {
		return super.getDataElement(csReimbTypeTblReimbTypeId);
	}
	public int getReimbTypeIdAsInt() {
		return super.getIntDataElement(csReimbTypeTblReimbTypeId);
	}


	public String getReimType() {
		return super.getDataElement(csReimbTypeTblReimbType);
	}


	public String getUserRoleId() {
		return super.getDataElement(csUserRolesTblUserRoleId);
	}
	public int getUserRoleIdAsInt() {
		return super.getIntDataElement(csUserRolesTblUserRoleId);
	}


	public String getUserRole() {
		return super.getDataElement(csUserRolesTblUserRole);
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


	public void setReimbTypeId(int reimbTypeId) {
		super.setDataElement(csReimbTypeTblReimbTypeId, reimbTypeId);
	}
	public void setReimbTypeId(String reimbTypeId) {
		super.setDataElement(csReimbTypeTblReimbTypeId, reimbTypeId);
	}


	public void setReimType(String reimType) {
		super.setDataElement(csReimbTypeTblReimbType, reimType);
	}


	public void setUserRoleId(int userRoleId) {
		super.setDataElement(csUserRolesTblUserRoleId, userRoleId);
	}
	public void setUserRoleId(String userRoleId) {
		super.setDataElement(csUserRolesTblUserRoleId, userRoleId);
	}


	public void setUserRole(String userRole) {
		super.setDataElement(csUserRolesTblUserRole, userRole);
	}


	@Override
	public String toString() {
		return super.toString();
	}


	
	
}
