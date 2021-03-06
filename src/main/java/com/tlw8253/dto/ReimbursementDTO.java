package com.tlw8253.dto;

import java.sql.Timestamp;

import javax.sql.rowset.serial.SerialBlob;

import com.tlw8253.app.Constants;
import com.tlw8253.model.ReimbursementStatus;
import com.tlw8253.model.ReimbursementType;
import com.tlw8253.model.User;

public class ReimbursementDTO extends AddOrEditDTO implements Constants{
	//Class attributes are store in the parent in HashMap tables.	
	
	//
	//###
	public ReimbursementDTO() {
		super();
	}


		
	public String getReimbId() {
		return super.getDataElement(csReimbTblReimbId);
	}
	public int getReimbIdAsInt() {
		return super.getIntDataElement(csReimbTblReimbId);
	}

	public String getReimbAmount() {
		return super.getDataElement(csReimbTblReimbAmount);
	}
	public double getReimbAmountAsDouble() {
		return super.getDoubleDataElement(csReimbTblReimbAmount);
	}

	public Timestamp getReimbSubmitted() {
		return super.getTimestampDataElement(csReimbTblReimbSubmitted);
	}

	public Timestamp getReimbResolved() {
		return super.getTimestampDataElement(csReimbTblReimbResolved);
	}

	public String getReimbDescription() {
		return super.getDataElement(csReimbTblReimbDescription);
	}

	public SerialBlob getReimbReceipt() {
		return super.getSerialBlobDataElement(csReimbTblReimbReceipt);
	}

	public String getReimbResolverMessage() {
		return super.getDataElement(csReimbTblReimbResolverMsg);
	}

	public String getReimbAuthorId() {
		return super.getDataElement(csReimbTblReimbAuthorId);
	}
	public int getReimbAuthorIdAsInt() {
		return super.getIntDataElement(csReimbTblReimbAuthorId);
	}

	public String getReimbResolverId() {
		return super.getDataElement(csReimbTblReimbResolverId);
	}
	public int getReimbResolverIdAsInt() {
		return super.getIntDataElement(csReimbTblReimbResolverId);
	}

	public String getReimbStatus() {
		return super.getDataElement(csReimbTblReimbStatus);
	}

	public String getReimbType() {
		return super.getDataElement(csReimbTblReimbType);
	}

	public String getReimbResolverUsername() {
		return getDataElement(csReimbTblReimbResolverUName);
	}	





	public void setReimbId(int reimbId) {
		super.setDataElement(csReimbTblReimbId, reimbId);
	}
	public void setReimbId(String reimbId) {
		super.setDataElement(csReimbTblReimbId, reimbId);
	}

	public void setReimbAmount(String reimbAmount) {
		super.setDataElement(csReimbTblReimbAmount, reimbAmount);
	}
	public void setReimbAmount(double reimbAmount) {
		super.setDataElement(csReimbTblReimbAmount, reimbAmount);
	}


	public void setReimbSubmitted(String reimbSubmitted) {
		super.setDataElement(csReimbTblReimbSubmitted, reimbSubmitted);
	}
	public void setReimbSubmitted(Timestamp reimbSubmitted) {
		super.setDataElement(csReimbTblReimbSubmitted, reimbSubmitted);
	}

	public void setReimbResolved(String reimbResolved) {
		super.setDataElement(csReimbTblReimbResolved, reimbResolved);
	}
	public void setReimbResolved(Timestamp reimbResolved) {
		super.setDataElement(csReimbTblReimbResolved, reimbResolved);
	}

	public void setReimbDescription(String reimbDescription) {
		super.setDataElement(csReimbTblReimbDescription, reimbDescription);
	}

	public void setReimbReceipt(SerialBlob reimbReceipt) {
		super.setDataElement(csReimbTblReimbReceipt, reimbReceipt);
	}
	public void setReimbReceipt(String reimbReceipt) {
		super.setDataElement(csReimbTblReimbReceipt, reimbReceipt);
	}

	public void setReimbResolverMessage(String reimResolverMessage) {
		super.setDataElement(csReimbTblReimbResolverMsg, reimResolverMessage);
	}

	public void setReimbAuthorUsername(String reimbAuthorUsername) {
		super.setDataElement(csReimbTblReimbAuthorUName, reimbAuthorUsername);
	}
	public void setReimbAuthorId(String reimbAuthorId) {
		super.setDataElement(csReimbTblReimbAuthorId, reimbAuthorId);
	}
	public void setReimbAuthorId(int reimbAuthorId) {
		super.setDataElement(csReimbTblReimbAuthorId, reimbAuthorId);
	}

	public void setReimbResolverUsername(String reimbResolverUsername) {
		super.setDataElement(csReimbTblReimbResolverUName, reimbResolverUsername);
	}	
	public void setReimbResolverId(String reimbResolverId) {
		super.setDataElement(csReimbTblReimbResolverId, reimbResolverId);
	}
	public void setReimbResolverId(int reimbResolverId) {
		super.setDataElement(csReimbTblReimbResolverId, reimbResolverId);
	}

	public void setReimbStatus(String reimbStatus) {
		super.setDataElement(csReimbTblReimbStatus, reimbStatus);
	}
	public void setReimbStatusId(int reimbStatusId) {
		super.setDataElement(csReimbTblReimbStatusId, reimbStatusId);
	}

	public void setReimbType(String reimbType) {
		super.setDataElement(csReimbTblReimbType, reimbType);
	}
	public void setReimbTypeId(int reimbTypeId) {
		super.setDataElement(csReimbTblReimbTypeId, reimbTypeId);
	}



	@Override
	public String toString() {
		return super.toString();
	}


	
	
}
