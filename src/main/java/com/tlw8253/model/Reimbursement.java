package com.tlw8253.model;

import java.sql.Timestamp;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.sql.rowset.serial.SerialBlob;

import com.tlw8253.app.Constants;

@Entity
@Table(name = Constants.csReimburstementTable)

public class Reimbursement implements Constants {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = csReimbTblReimbId)
	private int reimbId = 0;

	@Column(name = csReimbTblReimbAmount, nullable = false)
	private double reimbAmount = 0.0;

	@Column(name = csReimbTblReimbSubmitted, nullable = false)
	private Timestamp reimbSubmitted;

	@Column(name = csReimbTblReimbResolved)
	private Timestamp reimbResolved;
	
	@Column(name = csReimbTblReimbDescription, length = 250, nullable = false)
	private String reimbDescription = "";
	
	@Column(name = csReimbTblReimbReceipt)
	private SerialBlob reimbReceipt = null;

	@ManyToOne
	@JoinColumn(name = csReimbTblReimbAuthor, nullable = false) // optional, I just want to give my own name for the foreign key column
	private User reimbAuthor;

	@ManyToOne
	@JoinColumn(name = csReimbTblReimbResolver, nullable = false) // optional, I just want to give my own name for the foreign key column
	private User reimbResolver;

	@ManyToOne
	@JoinColumn(name = csReimbStatusTblReimbStatusId, nullable = false) // optional, I just want to give my own name for the foreign key column
	private ReimbursementStatus reimbStatus;

	@ManyToOne
	@JoinColumn(name = csReimbTypeTblReimbTypeId, nullable = false) // optional, I just want to give my own name for the foreign key column
	private ReimbursementType reimbType;

	
	public Reimbursement() {
		reimbSubmitted = new Timestamp(0);
		reimbResolved = new Timestamp(0);
	}


	public int getReimbId() {
		return reimbId;
	}


	public double getReimbAmount() {
		return reimbAmount;
	}


	public Timestamp getReimbSubmitted() {
		return reimbSubmitted;
	}


	public Timestamp getReimbResolved() {
		return reimbResolved;
	}


	public String getReimbDescription() {
		return reimbDescription;
	}


	public SerialBlob getReimbReceipt() {
		return reimbReceipt;
	}


	public User getReimbAuthor() {
		return reimbAuthor;
	}


	public User getReimbResolver() {
		return reimbResolver;
	}


	public ReimbursementStatus getReimbStatus() {
		return reimbStatus;
	}


	public ReimbursementType getReimbType() {
		return reimbType;
	}


	public void setReimbId(int reimbId) {
		this.reimbId = reimbId;
	}


	public void setReimbAmount(double reimbAmount) {
		this.reimbAmount = reimbAmount;
	}


	public void setReimbSubmitted(Timestamp reimbSubmitted) {
		this.reimbSubmitted = reimbSubmitted;
	}


	public void setReimbResolved(Timestamp reimbResolved) {
		this.reimbResolved = reimbResolved;
	}


	public void setReimbDescription(String reimbDescription) {
		this.reimbDescription = reimbDescription;
	}


	public void setReimbReceipt(SerialBlob reimbReceipt) {
		this.reimbReceipt = reimbReceipt;
	}


	public void setReimbAuthor(User reimbAuthor) {
		this.reimbAuthor = reimbAuthor;
	}


	public void setReimbResolver(User reimbResolver) {
		this.reimbResolver = reimbResolver;
	}


	public void setReimbStatus(ReimbursementStatus reimbStatus) {
		this.reimbStatus = reimbStatus;
	}


	public void setReimbType(ReimbursementType reimbType) {
		this.reimbType = reimbType;
	}


	@Override
	public int hashCode() {
		return Objects.hash(reimbAmount, reimbAuthor, reimbDescription, reimbId, reimbReceipt, reimbResolved,
				reimbResolver, reimbStatus, reimbSubmitted, reimbType);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Reimbursement other = (Reimbursement) obj;
		return Double.doubleToLongBits(reimbAmount) == Double.doubleToLongBits(other.reimbAmount)
				&& Objects.equals(reimbAuthor, other.reimbAuthor)
				&& Objects.equals(reimbDescription, other.reimbDescription) && reimbId == other.reimbId
				&& Objects.equals(reimbReceipt, other.reimbReceipt)
				&& Objects.equals(reimbResolved, other.reimbResolved)
				&& Objects.equals(reimbResolver, other.reimbResolver) && Objects.equals(reimbStatus, other.reimbStatus)
				&& Objects.equals(reimbSubmitted, other.reimbSubmitted) && Objects.equals(reimbType, other.reimbType);
	}


	@Override
	public String toString() {
		return "Reimbursement [reimbId=" + reimbId + ", reimbAmount=" + reimbAmount + ", reimbSubmitted="
				+ reimbSubmitted + ", reimbResolved=" + reimbResolved + ", reimbDescription=" + reimbDescription
				+ ", reimbReceipt=" + reimbReceipt + ", reimbAuthor=" + reimbAuthor + ", reimbResolver=" + reimbResolver
				+ ", reimbStatus=" + reimbStatus + ", reimbType=" + reimbType + "]";
	}

	
	
}