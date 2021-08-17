package com.tlw8253.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.tlw8253.app.Constants;

@Entity
@Table(name = Constants.csEmployeeTable)

public class Employee implements Constants{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	@Column(name = csEmployeeTblUsername, length = 10)	
	private String username = "";
	
	@Column(name = csEmployeeTblPassword, length = 20)
	private String password = "";
	
	@Column(name = csEmployeeTblFirstName, length = 60)
	private String firstName = "";
	
	@Column(name = csEmployeeTblLastName, length = 60)
	private String lastName = "";
	
	@Column(name = csEmployeeTblEmail, length = 60)
	private String email = "";

	
	public Employee(String sUsername, String sPassword, String sFirstName, String sLastName, String sEmail) {
		this.username = sUsername;
		this.password = sPassword;
		this.firstName = sFirstName;
		this.lastName = sLastName;
		this.email = sEmail;		
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	@Override
	public int hashCode() {
		return Objects.hash(email, firstName, lastName, password, username);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		return Objects.equals(email, other.email) && Objects.equals(firstName, other.firstName)
				&& Objects.equals(lastName, other.lastName) && Objects.equals(password, other.password)
				&& Objects.equals(username, other.username);
	}
	
	
	@Override
	public String toString() {
		return "Employee [sUsername=" + username + ", sPassword=" + password + ", sFirstName=" + firstName
				+ ", sLastName=" + lastName + ", sEmail=" + email + "]";
	}
	
	
}
