package com.tlw8253.dto;

import com.tlw8253.app.Constants;

public class ERSDTO extends AddOrEditDTO implements Constants{
	//Class attributes are store in the parent in HashMap tables.	

	//
	//###
	public ERSDTO() {
		super();
	}

	public ERSDTO(String username, String password, String firstName, String lastName, String email) {
		setUsername(username);
		setPassword(password);
		setFirstName(firstName);
		setLastName(lastName);
		setEmail(email);		
	}

	//### Getters
	public String getUsername() {
		return super.getDataElement(csUserTblUsername);
	}

	public String getPassword() {
		return super.getDataElement(csUserTblUsername);
	}

	public String getFirstName() {
		return super.getDataElement(csUserTblUsername);
	}

	public String getLastName() {
		return super.getDataElement(csUserTblUsername);
	}

	public String getEmail() {
		return super.getDataElement(csUserTblUsername);
	}

	//### Setters
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


	
	
}
