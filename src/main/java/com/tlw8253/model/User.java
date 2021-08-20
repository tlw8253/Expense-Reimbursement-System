package com.tlw8253.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.tlw8253.app.Constants;

@Entity
@Table(name = Constants.csUserTable)

public class User implements Constants {

	// appears primary key must be an integer for hibernate to work
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = csUserTblId)
	private int userId = 0;

	@Column(name = csUserTblUsername, length = 50, nullable = false, unique = true)
	private String username = "";

	@Column(name = csUserTblPassword, length = 50, nullable = false)
	private String password = "";

	@Column(name = csUserTblFirstName, length = 100, nullable = false)
	private String firstName = "";

	@Column(name = csUsrTblLastName, length = 100, nullable = false)
	private String lastName = "";

	@Column(name = csUserTblEmail, length = 150, nullable = false, unique = true)
	private String email = "";

	@ManyToOne
	@JoinColumn(name = csUserRolesTblUserRoleId, nullable = false)
	private UserRole userRole;

	public User() {
		super();
	}
	
	public User(String sUsername, String sPassword, String sFirstName, String sLastName, String sEmail) {
		this.username = sUsername;
		this.password = sPassword;
		this.firstName = sFirstName;
		this.lastName = sLastName;
		this.email = sEmail;
	}

	public UserRole getUserRole() {
		return userRole;
	}

	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}

	public int getId() {
		return userId;
	}

	public void setId(int id) {
		this.userId = id;
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
		return Objects.hash(email, firstName, lastName, password, userId, userRole, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(email, other.email) && Objects.equals(firstName, other.firstName)
				&& Objects.equals(lastName, other.lastName) && Objects.equals(password, other.password)
				&& userId == other.userId && Objects.equals(userRole, other.userRole)
				&& Objects.equals(username, other.username);
	}

	@Override
	public String toString() {
		return "\n\t User [userId=" + userId + ", username=" + username + ", password=" + password + ", firstName="
				+ firstName + ", lastName=" + lastName + ", email=" + email + ", userRole=" + userRole + "]";
	}



	
	
}
