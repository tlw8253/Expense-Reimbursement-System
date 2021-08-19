package com.tlw8253.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Validate {
	private static Logger objLogger = LoggerFactory.getLogger(Validate.class);

	private Validate() {
		super();
	}

	//
	// ### Utility method to check if string is an primitive int
	public static boolean isInt(String sValue) {
		String sMethod = "isInt";
		boolean bIsValid = false;
		try {
			objLogger.debug(sMethod + "Checking if string value of: [" + sValue + "] is an integer.");
			Integer.parseInt(sValue);
			bIsValid = true;

		} catch (NumberFormatException objE) {
			objLogger.debug(sMethod + "String value of: [" + sValue + "] is NOT an integer.");
		}
		return bIsValid;
	}

	//
	// ### Utility method to check if string is an primitive int
	public static boolean isDouble(String sValue) {
		String sMethod = "isDouble";
		boolean bIsValid = false;
		try {
			objLogger.debug(sMethod + "Checking if string value of: [" + sValue + "] is a double.");
			Double.parseDouble(sValue);
			bIsValid = true;

		} catch (NumberFormatException objE) {
			objLogger.debug(sMethod + "String value of: [" + sValue + "] is NOT a double.");
		}
		return bIsValid;
	}

	public static boolean isAlphaNumeric(String sValue) {
		String sMethod = "isAlphaNumeric(): ";
		boolean bIsValid = false;

		bIsValid = sValue.matches("[A-Za-z0-9]+");
		objLogger.debug(sMethod + "String value of: [" + sValue + "] is AlphaNumeric: [" + bIsValid + "]");

		return bIsValid;
	}

	public static boolean isAlpha(String sValue) {
		String sMethod = "isAlpha(): ";
		boolean bIsValid = false;

		bIsValid = sValue.matches("[A-Za-z]+");
		objLogger.debug(sMethod + "String value of: [" + sValue + "] is AlphaNumeric: [" + bIsValid + "]");

		return bIsValid;
	}

	//
	// ### inspiration for validation from:
	// https://www.geeksforgeeks.org/java-program-to-check-the-validity-of-a-password-using-user-defined-exception/
	public static boolean isPasswordFormat(String sValue) {
		String sMethod = "isPasswordFormat(): ";
		boolean bIsValid = false;

		// for checking if password length
		// is between 8 and 15
		if (!((sValue.length() >= 8) && (sValue.length() <= 15))) {
			objLogger.debug(sMethod + "password length error: [" + sValue.length() + "]");
			return bIsValid;
		}

		String sStringAt0 = sValue.substring(0, 1);
		if (!isAlpha(sStringAt0)) {
			objLogger.debug(sMethod + "password error does not start with an alpha.");
			return bIsValid;
		}

		// to check space
		if (sValue.contains(" ")) {
			objLogger.debug(sMethod + "password eror contains a space.");
			return bIsValid;
		}

		// check for at least one numeric value
		int iCount = 0;
		for (int iCtr = 0; iCtr < sValue.length(); iCtr++) {
			if ((sValue.charAt(iCtr) >= 48) && (sValue.charAt(iCtr) >= 57)) {
				iCount++;
			}
		}

		if (iCount == 0) {
			objLogger.debug(sMethod + "password eror contains no numeric.");
			return bIsValid;
		}

		// check for at least one uppercase alpha
		iCount = 0;
		for (int iCtr = 0; iCtr < sValue.length(); iCtr++) {
			if ((sValue.charAt(iCtr) >= 65) && (sValue.charAt(iCtr) <= 90)) {
				iCount++;
			}
		}

		if (iCount == 0) {
			objLogger.debug(sMethod + "password eror contains no capital letter.");
			return bIsValid;
		}

		// check for at least one lowercase alpha
		iCount = 0;
		for (int iCtr = 0; iCtr < sValue.length(); iCtr++) {
			if ((sValue.charAt(iCtr) >= 97) && (sValue.charAt(iCtr) <= 122)) {
				iCount++;
			}
		}

		if (iCount == 0) {
			objLogger.debug(sMethod + "password eror contains no lower case letter.");
			return bIsValid;
		}

		// check for program defined password special character
		if (!hasPasswordSpecialChar(sValue)) {
			objLogger.debug(sMethod + "password eror contains no passowrd special characters.");
			return bIsValid;
		}

		bIsValid = true;
		return bIsValid;
	}

	private static boolean hasPasswordSpecialChar(String sValue) {
		String sMethod = "hasPasswordSpecialChar(): ";
		boolean bIsValid = true;

		// for password allowed special characters
		if (!(sValue.contains("~") || sValue.contains("$") || sValue.contains("^") || sValue.contains("_"))) {
			objLogger.debug(sMethod + "String contains no password allowed special characters.");
			bIsValid = false;
		}

		return bIsValid;
	}

	public boolean hasSpecialChar(String sValue) {
		String sMethod = "hasSpecialChar(): ";
		boolean bIsValid = true;

		// for special characters
		if (!(sValue.contains("`") || sValue.contains("~") || sValue.contains("@") || sValue.contains("#")
				|| sValue.contains("$") || sValue.contains("%") || sValue.contains("^") || sValue.contains("&")
				|| sValue.contains("*") || sValue.contains("(") || sValue.contains(")") || sValue.contains("-")
				|| sValue.contains("_") || sValue.contains("=") || sValue.contains("+") || sValue.contains("[")
				|| sValue.contains("{") || sValue.contains("]") || sValue.contains("}") || sValue.contains("\\")
				|| sValue.contains("|") || sValue.contains(";") || sValue.contains(":") || sValue.contains("'")
				|| sValue.contains("\"") || sValue.contains(";") || sValue.contains(",") || sValue.contains("<")
				|| sValue.contains(".") || sValue.contains(">") || sValue.contains("/") || sValue.contains("?"))) {
			objLogger.debug(sMethod + "String contains no special characters.");
			bIsValid = false;
		}

		return bIsValid;
	}

	//
	//### A very basic email validation
	public static boolean isValidEmailAddress(String sValue) {
		String sMethod = "hasSpecialChar(): ";
		boolean bIsValid = true;

		bIsValid = sValue.matches("^(.+)@(.+)$");
		objLogger.debug(sMethod + "String value of: [" + sValue + "] is email format: [" + bIsValid + "]");

		return bIsValid;
	}

}
