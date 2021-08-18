package com.tlw8253.app;

import java.util.List;

import java.util.*;
import java.lang.*;
import java.io.*;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tlw8253.dto.ERSDTO;
import com.tlw8253.dto.ReimbursementStatusDTO;
import com.tlw8253.exception.BadParameterException;
import com.tlw8253.exception.DatabaseException;
import com.tlw8253.model.ReimbursementStatus;
import com.tlw8253.model.User;
import com.tlw8253.service.ERSAdminService;
import com.tlw8253.service.ERSService;
import com.tlw8253.util.SessionFactorySingleton;



/**
 * This is a driver used during development to test functionality as it is
 * built. 
 * 
 * @author tlw8748253
 *
 */
public class Driver implements Constants {
	private final static Logger objLogger = LoggerFactory.getLogger(Driver.class);
	private static ERSService objERSService = new ERSService();
	private static ERSDTO objERSDTO;

	public static void main(String[] args) {
		String sMethod = "main(): ";
		objLogger.trace(sMethod + "Entered");
		
		
		//createTablesViaHibernate();
//		testAddNewEmployee();
		ersAdminAddValues();

	}
	
	private static void ersAdminAddValues() {
		addReimbursementStatus();
	}
	
	private static void addReimbursementStatus() {
		String sMethod = "addReimbursementStatus(): ";
		objLogger.trace(sMethod + "Entered");

		ERSAdminService objAdminService = new ERSAdminService();
		ReimbursementStatusDTO objReimbStatusDTO = new ReimbursementStatusDTO();
		
		String sStatus = "INITIAL";
		String sStatusDesc = "The status when a reimbursement request is first created and submitted by an user.";
		objReimbStatusDTO.setReimbStatus(sStatus);
		objReimbStatusDTO.setReimbStatusDescription(sStatusDesc);
		
		try {
			ReimbursementStatus objReimbStatus = objAdminService.addReimbursementStatus(objReimbStatusDTO);
			objLogger.trace(sMethod + "objReimbStatus: [" + objReimbStatus.toString() + "]");
		} catch (DatabaseException | BadParameterException e) {
			e.printStackTrace();
		}
	}
	
	private static void createTablesViaHibernate() {
		String sMethod = "testAddNewEmployee(): ";
		objLogger.trace(sMethod + "Entered");

		// need to set the config.xm property to: <property name="hbm2ddl.auto">create</property>
		SessionFactory sf = SessionFactorySingleton.getSessionFactory();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		tx.commit();

	}
	
	public static void testAddNewEmployee() {
		String sMethod = "testAddNewEmployee(): ";
		objLogger.trace(sMethod + "Entered");
		
		objERSDTO = new ERSDTO("tlw874","12345678","Tomas","Ykel","tlw874@wwms.com");
		objLogger.debug(sMethod + "objERSDTO: [" + objERSDTO.toString() + "]");
		User objEmployee;
		try {
			objEmployee = objERSService.addNewEmployee(objERSDTO);
			objLogger.debug(sMethod + "objEmployee: [" + objEmployee.toString() + "]");	
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			

	}

	public static void fizz(int N) {
		int iRem3 = 1;
		int iRem5 = 1;

		iRem3 = N % 3;
		iRem5 = N % 5;
		String sResult = "";

		if ((iRem3 == 0) && (iRem5 == 0)) {
			sResult = "FizzBuzz";
		} else {
			if (iRem3 == 0) {
				sResult = "Fizz";
			} else {
				if (iRem5 == 0) {
					sResult = "Buzz";
				} else {
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
		String[] arCipher = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15",
				"16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26" };
		String sEncryptedString = "";
		String sToUpper = S.toUpperCase();

		int iASCII = 0;

		for (int iCtr = 0; iCtr < S.length(); iCtr++) {
			iASCII = sToUpper.charAt(iCtr);
			iASCII -= 65;
			sEncryptedString += arCipher[iASCII];
		}

		System.out.println(sEncryptedString);

		// this is default OUTPUT. You can change it
		String result = sEncryptedString;

		// WRITE YOUR LOGIC HERE:

		// For optimizing code, You are free to make changes to "return type",
		// "variables" and "Libraries".
		return result;
	}

}
