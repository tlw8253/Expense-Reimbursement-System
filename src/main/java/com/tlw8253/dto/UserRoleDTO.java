package com.tlw8253.dto;

import com.tlw8253.app.Constants;

public class UserRoleDTO extends AddOrEditDTO implements Constants{
	//Class attributes are store in the parent in HashMap tables.	

	/*Stored in hashmaps in the super class
	//ers_user_role table
	*/
	
	//
	//###
	public UserRoleDTO() {
		super();
	}

	public UserRoleDTO(String sUserRole, String sUserRoleDesc) {
		setUserRole(sUserRole);
		setUserRoleDescription(sUserRoleDesc);
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

	public String getUserRoleDescription() {
		return super.getDataElement(csUserRolesTblUserRoleDesc);
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

	public void setUserRoleDescription(String userRoleDesc) {
		super.setDataElement(csUserRolesTblUserRoleDesc, userRoleDesc);
	}

	@Override
	public String toString() {
		return super.toString();
	}


	
	
}
