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
		return super.getDataElement(csEmployeeTblUsername);
	}

	public String getPassword() {
		return super.getDataElement(csEmployeeTblUsername);
	}

	public String getFirstName() {
		return super.getDataElement(csEmployeeTblUsername);
	}

	public String getLastName() {
		return super.getDataElement(csEmployeeTblUsername);
	}

	public String getEmail() {
		return super.getDataElement(csEmployeeTblUsername);
	}

	//### Setters
	public void setUsername(String username) {
		super.setDataElement(csEmployeeTblUsername, username);
	}

	public void setPassword(String password) {
		super.setDataElement(csEmployeeTblPassword, password);
	}

	public void setFirstName(String firstName) {
		super.setDataElement(csEmployeeTblFirstName, firstName);
	}

	public void setLastName(String lastName) {
		super.setDataElement(csEmployeeTblLastName, lastName);
	}

	public void setEmail(String email) {
		super.setDataElement(csEmployeeTblEmail, email);
	}


	
	
}
