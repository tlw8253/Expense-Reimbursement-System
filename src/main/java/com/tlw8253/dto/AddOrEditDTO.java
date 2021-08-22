package com.tlw8253.dto;

import java.util.HashMap;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

import javax.sql.rowset.serial.SerialBlob;

import java.lang.Integer;
import java.sql.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tlw8253.model.User;
import com.tlw8253.util.Utility;


//DTO - Data Transfer Object
public class AddOrEditDTO {
	private Logger objLogger = LoggerFactory.getLogger(AddOrEditDTO.class);
	
	//every data element should be in the hmStringDataElements for up front validation
	//then stored in its destination format after validation
	HashMap<String, String> hmStringDataElements; //expected format: <databaseTable.column>,<string value> 
	HashMap<String, Integer> hmIntegerDataElements; //expected format: <databaseTable.column>,<integer value>
	HashMap<String, Double> hmDoubleDataElements; //expected format: <databaseTable.column>,<double value>
	HashMap<String, Boolean> hmBooleanDataElements; //expected format: <databaseTable.column>,<boolean value>
	HashMap<String, Timestamp> hmTimestampDataElements; //expected format: <databaseTable.column>,<Timestamp value>
	HashMap<String, SerialBlob> hmSerialBlobDataElements; //expected format: <databaseTable.column>,<SerialBlob value>
	
	
	public AddOrEditDTO() {
		super();
		hmStringDataElements = new HashMap<String, String>();
		hmIntegerDataElements = new HashMap<String, Integer>();
		hmDoubleDataElements = new HashMap<String, Double>();
		hmBooleanDataElements = new HashMap<String, Boolean>();
		hmTimestampDataElements = new HashMap<String, Timestamp>();
		hmSerialBlobDataElements = new HashMap<String, SerialBlob>();
	}	
	

	public AddOrEditDTO(HashMap<String, String> hmDataElements) {
		this.hmStringDataElements = hmDataElements;
		hmIntegerDataElements = new HashMap<String, Integer>();
		hmDoubleDataElements = new HashMap<String, Double>();
		hmBooleanDataElements = new HashMap<String, Boolean>();
		hmTimestampDataElements = new HashMap<String, Timestamp>();
		hmSerialBlobDataElements = new HashMap<String, SerialBlob>();
	}

	
	
	
	public String getDataElement(String sElementName) {
		return (hmStringDataElements.get(sElementName));
	}
	public double getDoubleDataElement(String sElementName) {
		return (hmDoubleDataElements.get(sElementName).doubleValue());
	}
	public int getIntDataElement(String sElementName) {
		return (hmIntegerDataElements.get(sElementName).intValue());
	}
	public boolean isBooleanDataElement(String sElementName) {
		return (hmBooleanDataElements.get(sElementName).booleanValue());
	}
	public Timestamp getTimestampDataElement(String sElementName) {
		return (hmTimestampDataElements.get(sElementName));
	}
	public SerialBlob getSerialBlobDataElement(String sElementName) {
		return (hmSerialBlobDataElements.get(sElementName));
	}

	
	
	
	public void setDataElement(String sElementName, String sElementValue) {
		hmStringDataElements.put(sElementName, sElementValue);
	}
	public void setDataElement(String sElementName, int iElementValue) {
		hmIntegerDataElements.put(sElementName, Integer.valueOf(iElementValue));
	}
	public void setDataElement(String sElementName, double dElementValue) {
		hmDoubleDataElements.put(sElementName, Double.valueOf(dElementValue));
	}
	public void setDataElement(String sElementName, boolean bElementValue) {
		hmBooleanDataElements.put(sElementName, bElementValue);
	}
	public void setDataElement(String sElementName, Timestamp objTimestamp) {
		hmTimestampDataElements.put(sElementName, objTimestamp);
	}
	public void setDataElement(String sElementName, SerialBlob objSerialBlob) {
		hmSerialBlobDataElements.put(sElementName, objSerialBlob);
	}

	
	


	//
	//### create return string in specific order of keys passed in
	//		Only use the String data elements, since all other types should 
	//		have a string counterpart.
	public String toStringByKeys(String... sKey) {//Varargs parameter list
		String sMethod = "\n\t toStringByKeys(): ";
		String sToString = "";

		sToString = Utility.hashMapToStringByByKeyOrder(hmStringDataElements, sKey);
		objLogger.debug(sMethod + "[" + sToString + "]");
		
		return sToString;
	}

	


	@Override
	public String toString() {
		String sMethod = "\n\t toString(): ";	
		String sToString = "";
		
		sToString = Utility.hashMapToStringBySortedKeys(hmStringDataElements);
		
		objLogger.debug(sMethod + "[" + sToString + "]");
		return sToString;
	}
	
	
	
	
}
