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
@Table(name = Constants.csReimbTypeTable)

public class ReimbursementType implements Constants {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = csReimbTypeTblReimbTypeId)
	private int reimbTypeId = 0;

	@Column(name = csReimbTypeTblReimbType, length = 10, nullable = false, unique = true)
	private String reimbType = "";

	@Column(name = csReimbTypeTblReimbTypeDesc, length = 150, nullable = false)
	private String reimbTypeDesc = "";

		
	public ReimbursementType() {
		// TODO Auto-generated constructor stub
	}


	public int getReimbTypeId() {
		return reimbTypeId;
	}


	public String getReimbType() {
		return reimbType;
	}


	public void setReimbTypeId(int reimbTypeId) {
		this.reimbTypeId = reimbTypeId;
	}


	public void setReimbType(String reimbType) {
		this.reimbType = reimbType;
	}


	public String getReimbTypeDesc() {
		return reimbTypeDesc;
	}


	public void setReimbTypeDesc(String reimbTypeDesc) {
		this.reimbTypeDesc = reimbTypeDesc;
	}


	@Override
	public int hashCode() {
		return Objects.hash(reimbType, reimbTypeDesc, reimbTypeId);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReimbursementType other = (ReimbursementType) obj;
		return Objects.equals(reimbType, other.reimbType) && Objects.equals(reimbTypeDesc, other.reimbTypeDesc)
				&& reimbTypeId == other.reimbTypeId;
	}


	@Override
	public String toString() {
		return "ReimbursementType [reimbTypeId=" + reimbTypeId + ", reimbType=" + reimbType + ", reimbTypeDesc="
				+ reimbTypeDesc + "]";
	}


	
	
	
	
	
}
