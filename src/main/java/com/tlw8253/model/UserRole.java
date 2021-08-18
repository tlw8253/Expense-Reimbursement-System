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
@Table(name = Constants.csUserRolesTable)

public class UserRole implements Constants {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = csUserRolesTblUserRoleId)
	private int userRoleId = 0;

	@Column(name = csUserRolesTblUserRole, length = 10, nullable = false, unique = true)
	private String userRole = "";
	
	@Column(name = csUserRolesTblUserRoleDesc, length = 150, nullable = false)
	private String userRoleDesc = "";

	
	public UserRole() {
		// TODO Auto-generated constructor stub
	}

	public int getUserRoleId() {
		return userRoleId;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRoleId(int userRoleId) {
		this.userRoleId = userRoleId;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public String getUserRoleDesc() {
		return userRoleDesc;
	}

	public void setUserRoleDesc(String userRoleDesc) {
		this.userRoleDesc = userRoleDesc;
	}

	@Override
	public int hashCode() {
		return Objects.hash(userRole, userRoleDesc, userRoleId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserRole other = (UserRole) obj;
		return Objects.equals(userRole, other.userRole) && Objects.equals(userRoleDesc, other.userRoleDesc)
				&& userRoleId == other.userRoleId;
	}

	@Override
	public String toString() {
		return "UserRole [userRoleId=" + userRoleId + ", userRole=" + userRole + ", userRoleDesc=" + userRoleDesc + "]";
	}


	
	
	
	
}
