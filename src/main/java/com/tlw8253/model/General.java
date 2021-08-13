package com.tlw8253.model;

import java.util.Objects;

public class General {

	private String sGeneralName = "";
	private int iGeneralInt = 0;
	private double dGeneralDoulbe = 0.0;
	private boolean bGeneralBoolean = false;
	private int iGeneralRecId = 0;
	
	public General(String sSomeName, int iSomeInt, double dSomeDouble, boolean bSomeBoolean) {
		this.sGeneralName = sSomeName;
		this.iGeneralInt = iSomeInt;
		this.dGeneralDoulbe = dSomeDouble;
		this.bGeneralBoolean = bSomeBoolean;
	}

	public General(String sSomeName, int iSomeInt, double dSomeDouble, boolean bSomeBoolean, int iRecordId) {
		this.sGeneralName = sSomeName;
		this.iGeneralInt = iSomeInt;
		this.dGeneralDoulbe = dSomeDouble;
		this.bGeneralBoolean = bSomeBoolean;
		this.iGeneralRecId = iRecordId;
	}

	public int getRecordId() {
		return iGeneralRecId;
	}

	public void setRecordId(int iRecordId) {
		this.iGeneralRecId = iRecordId;
	}
	
	public String getName() {
		return sGeneralName;
	}


	public void setName(String sName) {
		this.sGeneralName = sName;
	}


	public int getIntValue() {
		return iGeneralInt;
	}


	public void setIntValue(int iIntValue) {
		this.iGeneralInt = iIntValue;
	}


	public double getDoulbeValue() {
		return dGeneralDoulbe;
	}


	public void setDoulbeValue(double dDoulbeValue) {
		this.dGeneralDoulbe = dDoulbeValue;
	}


	public boolean isBoolean() {
		return bGeneralBoolean;
	}


	public void setBoolean(boolean bBoolean) {
		this.bGeneralBoolean = bBoolean;
	}


	@Override
	public int hashCode() {
		return Objects.hash(bGeneralBoolean, dGeneralDoulbe, iGeneralInt, sGeneralName);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		General other = (General) obj;
		return bGeneralBoolean == other.bGeneralBoolean
				&& Double.doubleToLongBits(dGeneralDoulbe) == Double.doubleToLongBits(other.dGeneralDoulbe)
				&& iGeneralInt == other.iGeneralInt && Objects.equals(sGeneralName, other.sGeneralName);
	}


	@Override
	public String toString() {
		return "NonSpecificModel [sName=" + sGeneralName + ", iIntValue=" + iGeneralInt + ", dDoulbeValue=" + dGeneralDoulbe
				+ ", bBoolean=" + bGeneralBoolean + "]";
	}



}
