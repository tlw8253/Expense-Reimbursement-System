package com.tlw8253.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.tlw8253.util.PasswordUtil;



public class PasswordDriver {
	private static Logger objLogger = LoggerFactory.getLogger(PasswordDriver.class);

    public static void main(String[] args)
    {
       	String sMethod = "main(): ";
    	objLogger.trace(sMethod + "Entered");
    	
    	// Generate Salt. The generated value can be stored in DB.
    	String salt = test_PasswordUtil_getSalt(30);
    	objLogger.debug(sMethod + "salt: [" + salt + "]");
    	
        // Protect user's password. The generated value can be stored in DB.
    	String myPassword = "myPassword123";
    	String mySecurePassword = test_PasswordUtil_generateSecurePassword(myPassword, salt);
        objLogger.debug(sMethod + "myPassword: [" + myPassword + "] mySecurePassword: [" + mySecurePassword + "]");            	
        
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
