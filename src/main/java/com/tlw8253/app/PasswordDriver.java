package com.tlw8253.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.tlw8253.util.PasswordUtil;
import com.tlw8253.util.Validate;



public class PasswordDriver {
	private static Logger objLogger = LoggerFactory.getLogger(PasswordDriver.class);

    public static void main(String[] args) {
       	String sMethod = "main(): ";
    	objLogger.trace(sMethod + "Entered");

    	//testPasswordUtil();
    	//test_Validate_hasAllowedPasswordChars();
    	//test_Validate_hasPasswordSpecialChar();
    }

    
    //
    //###
    private static void test_Validate_hasPasswordSpecialChar() {
       	String sMethod = "test_Validate_hasPasswordSpecialChar()";
    	objLogger.trace(sMethod + "Entered");
    
    	String sPwd = "A_Pass12345";
    	boolean bValidPwdChars = Validate.hasPasswordSpecialChar(sPwd);
    	objLogger.debug(sMethod + "sPwd: [" + sPwd + "] has special char: [" + bValidPwdChars + "]");
    
    	sPwd = "A(Pass12345";
    	bValidPwdChars = Validate.hasPasswordSpecialChar(sPwd);
    	objLogger.debug(sMethod + "sPwd: [" + sPwd + "] has special char: [" + bValidPwdChars + "]");

    }
    
    //
    //###
    private static void test_Validate_hasAllowedPasswordChars() {
       	String sMethod = "test_Validate_hasAllowedPasswordChars()";
    	objLogger.trace(sMethod + "Entered");

    	String sPwd = "A_Pass12345";
    	boolean bValidPwdChars = Validate.hasAllowedPasswordChars(sPwd);
    	objLogger.debug(sMethod + "sPwd: [" + sPwd + "] is valid: [" + bValidPwdChars + "]");
    
    	sPwd = "A(Pass12345";
    	bValidPwdChars = Validate.hasAllowedPasswordChars(sPwd);
    	objLogger.debug(sMethod + "sPwd: [" + sPwd + "] is valid: [" + bValidPwdChars + "]");
    	
    }
    
    //
    //###
    private static void testPasswordUtil() {
       	String sMethod = "testPasswordUtil()";
    	objLogger.trace(sMethod + "Entered");
    	
    	// Generate Salt. The generated value can be stored in DB.
    	String salt = test_PasswordUtil_getSalt(30);
    	objLogger.debug(sMethod + "salt: [" + salt + "]");
    	
        // Protect user's password. The generated value can be stored in DB.
    	String myPassword = "myPassword_123";
    	String mySecurePassword = test_PasswordUtil_generateSecurePassword(myPassword, salt);
        objLogger.debug(sMethod + "myPassword: [" + myPassword + "] mySecurePassword: [" + mySecurePassword + "]");
   
        boolean passwordMatch = test_PasswordUtil_verifyUserPassword(myPassword, mySecurePassword, salt);
        
        if(passwordMatch) 
        {
            System.out.println("Provided user password " + myPassword + " is correct.");
        } else {
            System.out.println("Provided password is incorrect");
        }   
    	
    }
    
    private static boolean test_PasswordUtil_verifyUserPassword(String providedPassword, String securePassword, String salt) {
       	String sMethod = "test_PasswordUtil_verifyUserPassword(): ";
    	objLogger.trace(sMethod + "Entered: sPwd: [" + providedPassword + "] securePassword: [" + securePassword + "] salt: [" + salt + "]");   	
    
    	boolean passwordMatch = PasswordUtil.verifyUserPassword(providedPassword, securePassword, salt);
    	
    	return passwordMatch;
    }

    
    // Protect user's password. The generated value can be stored in DB.
    private static String test_PasswordUtil_generateSecurePassword(String sPwd, String sSalt) {
       	String sMethod = "test_PasswordUtil_getSalt(): ";
    	objLogger.trace(sMethod + "Entered: sPwd: [" + sPwd + "] sSalt: [" + sSalt + "]");   	

    	String sSecurePassword = PasswordUtil.generateSecurePassword(sPwd, sSalt);
    	
    	objLogger.trace(sMethod + "Exit: sSecurePassword: [" + sSecurePassword + "]");    	
    	return sSecurePassword;
    }
    
    
 // Generate Salt. The generated value can be stored in DB.
    private static String test_PasswordUtil_getSalt(int iLen) {
       	String sMethod = "test_PasswordUtil_getSalt(): ";
    	objLogger.trace(sMethod + "Entered: iLen: [" + iLen + "]");   	
    	String sSalt = PasswordUtil.getSalt(30);
    	objLogger.trace(sMethod + "Exit: sSalt: [" + sSalt + "]"); 
    	return sSalt;
    }
	
}
