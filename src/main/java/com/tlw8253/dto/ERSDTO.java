package com.tlw8253.dto;

import com.tlw8253.app.Constants;

public class ERSDTO extends AddOrEditDTO implements Constants{
	//Class attributes are store in the parent in HashMap tables.	
	
	//
	//###
	public ERSDTO() {
		super();
	}

	public ERSDTO(String sSomeName, int iSomeInt, double dSomeDouble, boolean bSomeBoolean) {
		
	}

	public String getSomeName() {
		return super.getDataElement(csShellTblShellName);
	}

	public String getSomeInt() {
		return super.getDataElement(csShellTblShellInt);
	}
	public int getSomeIntAsInt() {
		return super.getIntDataElement(csShellTblShellInt);
	}

	public String getSomeDouble() {
		return super.getDataElement(csShellTblShellDbl);
	}
	public double getSomeDoubleAsDouble() {
		return super.getDoubleDataElement(csShellTblShellDbl);
	}

	public String isSomeBoolean() {
		return super.getDataElement(csShellTblShellBool);
	}
	public boolean isSomeBooleanAsBool() {
		return super.isBooleanDataElement(csShellTblShellBool);
	}
	
	public String recordId() {
		return super.getDataElement(csShellTblShellId);
	}
	public int recordIdAsInt() {
		return super.getIntDataElement(csShellTblShellId);
	}
	
	
	//Need to provide string setters for all model attributes by the name.
	//  Cannot overload the set method because objCtx.bodyAsClass(AddOrEditDTO.class);
	//  is using the set using the primitive parameter methods instead of string like I thought it would

	public void setSomeName(String sSomeName) {
		super.setDataElement(csShellTblShellName, sSomeName);
	}

	public void setSomeInt(int sSomeInt) {
		super.setDataElement(csShellTblShellInt, sSomeInt);
	}

	public void setSomeDouble(double sSomeDouble) {
		super.setDataElement(csShellTblShellDbl, sSomeDouble);
	}

	public void setSomeBoolean(boolean sSomeBoolean) {
		super.setDataElement(csShellTblShellBool, sSomeBoolean);
	}
	
	public void setRecordId(String sRecordId) {
		super.setDataElement(csShellTblShellId, sRecordId);
	}


	//
	//Provide setters for primitives
	//All primitive validation expected in caller
	public void setSomeIntAsInt(int sSomeInt) {
		super.setDataElement(csShellTblShellId, sSomeInt);
	}

	public void setSomeDoubleAsDouble(double sSomeDouble) {
		super.setDataElement(csShellTblShellId, sSomeDouble);
	}

	public void setSomeBooleanAsBool(boolean sSomeBoolean) {
		super.setDataElement(csShellTblShellId, sSomeBoolean);
	}

	public void setRecordIdAsInt(int iRecordId) {
		super.setDataElement(csShellTblShellId, iRecordId);
	}

	
	
}
