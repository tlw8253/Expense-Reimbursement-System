package com.tlw8253.dto;

import com.tlw8253.app.Constants;

public class ERSAdminDTO extends AddOrEditDTO implements Constants{
	//Class attributes are store in the parent in HashMap tables.	

	//ers_reimbursement_status table
	private int reimbStatusId;
	private String reimbStatus;
	
	//ers_reimbursement_status table
	private int reimbTypeId;
	private String reimType;
	
	//ers_user_roles table
	private int userRoleId;
	private String userRole;
	
	
	//
	//###
	public ERSAdminDTO() {
		super();
	}


	public int getReimbStatusId() {
		return reimbStatusId;
	}


	public String getReimbStatus() {
		return reimbStatus;
	}


	public int getReimbTypeId() {
		return reimbTypeId;
	}


	public String getReimType() {
		return reimType;
	}


	public int getUserRoleId() {
		return userRoleId;
	}


	public String getUserRole() {
		return userRole;
	}


	public void setReimbStatusId(int reimbStatusId) {
		this.reimbStatusId = reimbStatusId;
	}


	public void setReimbStatus(String reimbStatus) {
		this.reimbStatus = reimbStatus;
	}


	public void setReimbTypeId(int reimbTypeId) {
		this.reimbTypeId = reimbTypeId;
	}


	public void setReimType(String reimType) {
		this.reimType = reimType;
	}


	public void setUserRoleId(int userRoleId) {
		this.userRoleId = userRoleId;
	}


	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}


	
	
}
