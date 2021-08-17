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
@Table(name = Constants.csReimbStatusTable)

public class ReimbursementStatus implements Constants {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = csReimbStatusTblReimbStatusId)
	private int reimbStatusId = 0;

	@Column(name = csReimbStatusTblReimbStatus, length = 10, nullable = false, unique = true)
	private String reimbStatus = "";

	
	public ReimbursementStatus() {
		// TODO Auto-generated constructor stub
	}


	public int getReimbStatusId() {
		return reimbStatusId;
	}


	public String getReimbStatus() {
		return reimbStatus;
	}


	public void setReimbStatusId(int reimbStatusId) {
		this.reimbStatusId = reimbStatusId;
	}


	public void setReimbStatus(String reimbStatus) {
		this.reimbStatus = reimbStatus;
	}


	@Override
	public int hashCode() {
		return Objects.hash(reimbStatus, reimbStatusId);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReimbursementStatus other = (ReimbursementStatus) obj;
		return Objects.equals(reimbStatus, other.reimbStatus) && reimbStatusId == other.reimbStatusId;
	}


	@Override
	public String toString() {
		return "ReimbursementStatus [reimbStatusId=" + reimbStatusId + ", reimbStatus=" + reimbStatus + "]";
	}

	
	
	
	
}
