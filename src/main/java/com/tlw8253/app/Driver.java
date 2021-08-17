package com.tlw8253.app;

import java.util.List;

import java.util.*; 
import java.lang.*;
import java.io.*;
import java.lang.Math;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
import com.tlw8253.dao.AccountDAOImpl;
import com.tlw8253.dto.AccountAddDTO;
import com.tlw8253.dto.AddOrEditClientDTO;
import com.tlw8253.dto.AddDTO;
import com.tlw8253.exception.*;
import com.tlw8253.exception.DatabaseException;
import com.tlw8253.javalin.JavalinHelper;
import com.tlw8253.model.Account;
import com.tlw8253.model.Client;
import com.tlw8253.service.AccountService;
import com.tlw8253.service.ClientService;

import com.tlw8253.util.*;
*

/**
 * This is a driver used during development to test functionality as it is
 * built. 
 * 
 * @author tlw8748253
 *
 */
public class Driver implements Constants {
	private final static Logger objLogger = LoggerFactory.getLogger(Driver.class);

	public static void main(String[] args) {

		// create / call static methods in this class to control test driving during development
		//testSomeProcessing();
		//cipher("ABC");
		for (int iCtr=1; iCtr<=30; iCtr++) {
		fizz(iCtr);
		}
	}

	

	public static void fizz(int N) {
		 int iRem3=1;
	        int iRem5 = 1;

	        iRem3 = N % 3;
	        iRem5 = N % 5;
	        String sResult = "";

	        if ((iRem3 == 0) && (iRem5 == 0)){
	            sResult = "Fizz Buzz";
	        }else {
	            if (iRem3 == 0){
	                sResult = "Fizz";
	            }else{
	                if (iRem5 == 0){
	                    sResult = "Buzz";
	                }
	                else{
	                    sResult = Integer.toString(N);
	                }
	            }                
	                	
	          }
	        System.out.println(sResult);        
	}
	
	
	//
	// ###
	public static void testSomeProcessing() {
	}

    public static String cipher(String S) {
    	String [] arCipher = {"01","02","03","04","05","06","07","08","09","10","11","12","13",
    						"14","15","16","17","18","19","20","21","22","23","24","25","26"};
    	String sEncryptedString = "";
    	String sToUpper = S.toUpperCase();
    	
    	int iASCII = 0;
    	
    	for (int iCtr=0; iCtr<S.length(); iCtr++) {
    		iASCII = sToUpper.charAt(iCtr);
    		iASCII -= 65;
    		sEncryptedString +=  arCipher[iASCII];    		
    	}
    	
    	System.out.println(sEncryptedString);
    		
    	
        //this is default OUTPUT. You can change it
        String result=sEncryptedString;
        
        //WRITE YOUR LOGIC HERE:
                
                
        //For optimizing code, You are free to make changes to "return type", "variables" and "Libraries".        
        return result;
    }



}
