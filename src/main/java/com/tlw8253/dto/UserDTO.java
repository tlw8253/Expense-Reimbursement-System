package com.tlw8253.dto;

import com.tlw8253.app.Constants;

public class UserDTO extends AddOrEditDTO implements Constants{
	//Class attributes are store in the parent in HashMap tables.	

	/*Stored in hashmaps in the super class
	//ers_user_role table
	*/

	//
	//###
	public UserDTO() {
		super();
	}

	public UserDTO(String username, String password, String firstName, String lastName, String email, String userRole) {
		super();
	}
	
	public String getUserId() {
		return super.getDataElement(csUserTblId);
	}
	public int getUserIdAsInt() {
		return super.getIntDataElement(csUserTblId);
	}

	public String getUsername() {
		return super.getDataElement(csUserTblUsername);
	}

	public String getPassword() {
		return super.getDataElement(csUserTblPassword);
	}

	public String getFirstName() {
		return super.getDataElement(csUserTblFirstName);
	}

	public String getLastName() {
		return super.getDataElement(csUsrTblLastName);
	}

	public String getEmail() {
		return super.getDataElement(csUserTblEmail);
	}

	public String getUserRoleName() {
		return super.getDataElement(csUserTblRoleName);
	}
	public int getUserRoleId() {
		return super.getIntDataElement(csUserTblRoleId);
	}

	public void setUserId(int userId) {
		super.setDataElement(csUserTblId, userId);
	}

	public void setUsername(String username) {
		super.setDataElement(csUserTblUsername, username);
	}

	public void setPassword(String password) {
		super.setDataElement(csUserTblPassword, password);
	}

	public void setFirstName(String firstName) {
		super.setDataElement(csUserTblFirstName, firstName);
	}

	public void setLastName(String lastName) {
		super.setDataElement(csUsrTblLastName, lastName);
	}

	public void setEmail(String email) {
		super.setDataElement(csUserTblEmail, email);
	}

	public void setUserRoleId(int userRoleId) {
		super.setDataElement(csUserTblRoleId, userRoleId);
	}

	public void setUserRoleName(String userRoleName) {
		super.setDataElement(csUserTblRoleName, userRoleName.toUpperCase());
	}

	
	@Override
	public String toString() {
		return super.toString();
	}


	
	
}
