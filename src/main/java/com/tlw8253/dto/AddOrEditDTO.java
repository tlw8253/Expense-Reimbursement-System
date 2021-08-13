package com.tlw8253.dto;

import java.util.HashMap;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.lang.Integer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tlw8253.util.Utility;


//DTO - Data Transfer Object
public class AddOrEditDTO {
	private Logger objLogger = LoggerFactory.getLogger(AddOrEditDTO.class);
	
	//every data element should be in the hmStringDataElements for up front validation
	//then stored in its destination format after validation
	HashMap<String, String> hmStringDataElements; //expected format: <databaseTable.column>,<string value> 
	HashMap<String, Integer> hmIntegerDataElements; //expected format: <databaseTable.column>,<integer value>
	HashMap<String, Double> hmDoubleDataElements; //expected format: <databaseTable.column>,<double value>
	HashMap<String, Boolean> hmBooleanDataElements; //expected format: <databaseTable.column>,<double value>
	
	
	public AddOrEditDTO() {
		super();
		hmStringDataElements = new HashMap<String, String>();
		hmIntegerDataElements = new HashMap<String, Integer>();
		hmDoubleDataElements = new HashMap<String, Double>();
		hmBooleanDataElements = new HashMap<String, Boolean>();
	}
	
	
	public AddOrEditDTO(HashMap<String, String> hmDataElements) {
		super();
		this.hmStringDataElements = hmDataElements;
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

	
	
	@Override
	public int hashCode() {
		return Objects.hash(hmBooleanDataElements, hmDoubleDataElements, hmIntegerDataElements, hmStringDataElements);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AddOrEditDTO other = (AddOrEditDTO) obj;
		return Objects.equals(hmBooleanDataElements, other.hmBooleanDataElements)
				&& Objects.equals(hmDoubleDataElements, other.hmDoubleDataElements)
				&& Objects.equals(hmIntegerDataElements, other.hmIntegerDataElements)
				&& Objects.equals(hmStringDataElements, other.hmStringDataElements);
	}


	//
	//### create return string in specific order of keys passed in
	//		Only use the String data elements, since all other types should 
	//		have a string counterpart.
	public String toStringByKeys(String... sKey) {//Varargs parameter list
		String sMethod = "toStringByKeys(): ";
		String sToString = "";

		/*
		for (int iCtr=0; iCtr<sKey.length; iCtr++) {
			String sThisKey = sKey[iCtr];
			objLogger.debug(sMethod + "sKey[" + sKey + "]: [" + sKey[iCtr] + "]" );
			
			sToString += "[" + sThisKey + "]: [" + hmStringDataElements.get(sThisKey) + "] ";			
		}
		objLogger.debug(sMethod + "[" + sToString + "]");		
		*/
		
		sToString = Utility.hashMapToStringByByKeyOrder(hmStringDataElements, sKey);
		objLogger.debug(sMethod + "[" + sToString + "]");
		
		return sToString;
	}

	


	@Override
	public String toString() {
		String sMethod = "toString(): ";	
		String sToString = "";
		
//		TreeSet<String> tsSortedNames = Utility.getHashMapSortedKeys(hmStringDataElements);
//		sToString = Utility.hashMapToStringBySortedKeys(tsSortedNames, hmStringDataElements);
		sToString = Utility.hashMapToStringBySortedKeys(hmStringDataElements);
		
		objLogger.debug(sMethod + "[" + sToString + "]");
		return sToString;
	}
	
	
	
	
}
