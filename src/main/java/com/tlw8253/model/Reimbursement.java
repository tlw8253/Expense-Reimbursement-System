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

	//this is required when adding a reimburstment record
	@Column(name = csReimbTblReimbAmount, nullable = false)	
	private double reimbAmount = 0.0;

	//this is set internally so should not make not null due to body as class errors
	@Column(name = csReimbTblReimbSubmitted)	//, nullable = false)
	private Timestamp reimbSubmitted;

	@Column(name = csReimbTblReimbResolved)
	private Timestamp reimbResolved;
	
	@Column(name = csReimbTblReimbDescription, length = 250, nullable = false)
	private String reimbDescription = "";
	
	
	@Column(name = csReimbTblReimbReceipt)
	private SerialBlob reimbReceipt = null;

	@Column(name = csReimbTblReimbResolverMsg)
	private String reimbResolverMsg;

	//this is required when adding a reimburstment record
	@ManyToOne
	@JoinColumn(name = csReimbTblReimbAuthorId, nullable = false) // 
	private User reimbAuthor;

	@ManyToOne
	@JoinColumn(name = csReimbTblReimbResolverId) // 
	private User reimbResolver;

	//this is set internally so should not make not null due to body as class errors
	@ManyToOne
	@JoinColumn(name = csReimbStatusTblReimbStatusId)	//, nullable = false) // 
	private ReimbursementStatus reimbStatus;

	//this is set internally so should not make not null due to body as class errors
	@ManyToOne
	@JoinColumn(name = csReimbTypeTblReimbTypeId)	//, nullable = false) // 
	private ReimbursementType reimbType;

	
	public Reimbursement() {
		//use when setting the timestamp values:
		//Timestamp objTimestamp = Timestamp.valueOf(LocalDateTime.now());
		
		reimbSubmitted = new Timestamp(0);
		reimbResolved = new Timestamp(0);
	}

	public Reimbursement(double dReimAmount, Timestamp tsReimbSubmitted, Timestamp tsReimbResolved, String sReimbDescription, SerialBlob sbReimbReceipt) {
		this.reimbAmount = dReimAmount;
		this.reimbSubmitted = tsReimbSubmitted;
		this.reimbResolved = tsReimbResolved;
		this.reimbDescription = sReimbDescription;
		this.reimbReceipt = sbReimbReceipt;	
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

	public String getReimbResolverMsg() {
		return reimbResolverMsg;
	}

	public void setReimbResolverMsg(String reimbResolverMsg) {
		this.reimbResolverMsg = reimbResolverMsg;
	}

	@Override
	public int hashCode() {
		return Objects.hash(reimbAmount, reimbAuthor, reimbDescription, reimbId, reimbReceipt, reimbResolved,
				reimbResolver, reimbResolverMsg, reimbStatus, reimbSubmitted, reimbType);
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
				&& Objects.equals(reimbResolver, other.reimbResolver)
				&& Objects.equals(reimbResolverMsg, other.reimbResolverMsg)
				&& Objects.equals(reimbStatus, other.reimbStatus)
				&& Objects.equals(reimbSubmitted, other.reimbSubmitted) && Objects.equals(reimbType, other.reimbType);
	}

	@Override
	public String toString() {
		return "Reimbursement [reimbId=" + reimbId + ", reimbAmount=" + reimbAmount + ", reimbSubmitted="
				+ reimbSubmitted + ", reimbResolved=" + reimbResolved + ", reimbDescription=" + reimbDescription
				+ ", reimbReceipt=" + reimbReceipt + ", reimbResolverMsg=" + reimbResolverMsg + ", reimbAuthor="
				+ reimbAuthor + ", reimbResolver=" + reimbResolver + ", reimbStatus=" + reimbStatus + ", reimbType="
				+ reimbType + "]";
	}



	
	
}
