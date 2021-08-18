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
import com.tlw8253.dto.ReimbursementTypeDTO;
import com.tlw8253.dto.UserRoleDTO;
import com.tlw8253.exception.BadParameterException;
import com.tlw8253.exception.DatabaseException;
import com.tlw8253.model.ReimbursementStatus;
import com.tlw8253.model.ReimbursementType;
import com.tlw8253.model.User;
import com.tlw8253.model.UserRole;
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
		//ersAdminAddStaticTableValues();
		//addReimbursementStatusTestException();
		//getAllReimbursementStatusTest();
		//addReimbursementType();
		addUserRole();
		

		
	}
	
	
	//
	//###
	private static void ersAdminAddStaticTableValues() {
		addReimbursementStatus();
		addReimbursementType();
		addUserRole();
	}
	
	//
	//###
	private static void addUserRole() {
		String sMethod = "addUserRole(): ";
		objLogger.trace(sMethod + "Entered");

		String sUserRole = "EMPLOYEE";
		String sUserRoleDesc = "Any person actively employeed by the company with a valid username.";
		objLogger.debug(sMethod + "Adding type: [" + sUserRole+ "] description: [" + sUserRoleDesc +"]");
		addUsrRole(sUserRole, sUserRoleDesc);
		
		sUserRole = "FINANCEMGR";
		sUserRoleDesc = "Finance managers are authorized to approve and deny requests for expense reimbursement.";
		objLogger.debug(sMethod + "Adding type: [" + sUserRole+ "] description: [" + sUserRoleDesc +"]");
		addUsrRole(sUserRole, sUserRoleDesc);

		sUserRole = "SUPERMAN";
		sUserRoleDesc = "A super user of the system.  A system admin.";
		objLogger.debug(sMethod + "Adding type: [" + sUserRole+ "] description: [" + sUserRoleDesc +"]");
		addUsrRole(sUserRole, sUserRoleDesc);

	}

	//
	//###
	private static void addUsrRole(String sUserRole, String sUserRoleDesc) {
		String sMethod = "addReimbType(): ";
		objLogger.trace(sMethod + "Entered");

		ERSAdminService objAdminService = new ERSAdminService();
		UserRoleDTO objUserRoleDTO = new UserRoleDTO();

		objLogger.debug(sMethod + "Setting DTO: sUserRole: [" + sUserRole + "] sUserRoleDesc: [" + sUserRoleDesc + "]");
		
		objUserRoleDTO.setUserRole(sUserRole);
		objUserRoleDTO.setUserRoleDescription(sUserRoleDesc);

		objLogger.debug(sMethod + "objUserRoleDTO: [" + objUserRoleDTO.toString() + "]");

	
		try {
			UserRole objUserRole = objAdminService.addUserRole(objUserRoleDTO);
			objLogger.debug(sMethod + "objUserRole: [" + objUserRole.toString() + "]");
		} catch (Exception e) {
			objLogger.error(sMethod + "Exception during processing: [" + e.getMessage() + "]");
			//throw new DatabaseException(e.getMessage());
		}	
	}

	
	//
	//###
	private static void addReimbursementType() {
		String sMethod = "addReimbursementType(): ";
		objLogger.trace(sMethod + "Entered");

		String sType = "LODGING";
		String sTypeDesc = "Reimbursement expense related to overnight stays related to business travel.";
		objLogger.debug(sMethod + "Adding type: [" + sType+ "] description: [" + sTypeDesc +"]");
		addReimbType(sType, sTypeDesc);
		
		sType = "TRAVEL";
		sTypeDesc = "Reimbursement expense related to planes, trains, automobiles, etc.";
		objLogger.debug(sMethod + "Adding type: [" + sType+ "] description: [" + sTypeDesc +"]");
		addReimbType(sType, sTypeDesc);

		sType = "FOOD";
		sTypeDesc = "Reimbursement expense related to meals related to business travel.";
		objLogger.debug(sMethod + "Adding type: [" + sType+ "] description: [" + sTypeDesc +"]");
		addReimbType(sType, sTypeDesc);

		sType = "OTHER";
		sTypeDesc = "Reimbursement expense related to other expenses related to the business.";
		objLogger.debug(sMethod + "Adding type: [" + sType+ "] description: [" + sTypeDesc +"]");
		addReimbType(sType, sTypeDesc);

	}

	//
	//###
	private static void addReimbType(String sType, String sTypeDesc) {
		String sMethod = "addReimbType(): ";
		objLogger.trace(sMethod + "Entered");

		ERSAdminService objAdminService = new ERSAdminService();
		ReimbursementTypeDTO objReimbTypeDTO = new ReimbursementTypeDTO();

		objReimbTypeDTO.setReimbType(sType);
		objReimbTypeDTO.setReimbTypeDescription(sTypeDesc);
		
		try {
			ReimbursementType objReimbType = objAdminService.addReimbursementType(objReimbTypeDTO);
			objLogger.debug(sMethod + "objReimbType: [" + objReimbType.toString() + "]");
		} catch (Exception e) {
			objLogger.error(sMethod + "Exception during processing: [" + e.getMessage() + "]");
			//throw new DatabaseException(e.getMessage());
		}		
	}

	
	//
	//###
	private static void addReimbursementStatusTestException() {
		String sMethod = "addReimbursementStatusTestException(): ";
		objLogger.trace(sMethod + "Entered");

		//duplicate add of a status
		String sStatus = "PENDING";
		String sStatusDesc = "The status when a reimbursement request is first created and submitted by an user.";
		objLogger.debug(sMethod + "Adding status: [" + sStatus+ "] description: [" + sStatusDesc +"]");
		addReimbStatus(sStatus, sStatusDesc);
	}
	
	//
	//###
	private static void addReimbursementStatus() {
		String sMethod = "addReimbursementStatus(): ";
		objLogger.trace(sMethod + "Entered");

		String sStatus = "PENDING";
		String sStatusDesc = "The status when a reimbursement request is first created and submitted by an user.";
		objLogger.debug(sMethod + "Adding status: [" + sStatus+ "] description: [" + sStatusDesc +"]");
		addReimbStatus(sStatus, sStatusDesc);
		
		sStatus = "APPROVED";
		sStatusDesc = "The status when a reimbursement request is reviewed and approved by the Finance Manager.";
		objLogger.debug(sMethod + "Adding status: [" + sStatus+ "] description: [" + sStatusDesc +"]");
		addReimbStatus(sStatus, sStatusDesc);

		sStatus = "DENIED";
		sStatusDesc = "The status when a reimbursement request is reviewed and denied by the Finance Manager.";
		addReimbStatus(sStatus, sStatusDesc);
		
	}
	
	//
	//###
	private static void addReimbStatus(String sStatus, String sStatusDesc) {
		String sMethod = "addReimbStatus(): ";
		objLogger.trace(sMethod + "Entered");

		ERSAdminService objAdminService = new ERSAdminService();
		ReimbursementStatusDTO objReimbStatusDTO = new ReimbursementStatusDTO();

		objReimbStatusDTO.setReimbStatus(sStatus);
		objReimbStatusDTO.setReimbStatusDescription(sStatusDesc);
		
		try {
			ReimbursementStatus objReimbStatus = objAdminService.addReimbursementStatus(objReimbStatusDTO);
			objLogger.debug(sMethod + "objReimbStatus: [" + objReimbStatus.toString() + "]");
		} catch (Exception e) {
			objLogger.error(sMethod + "Exception during processing: [" + e.getMessage() + "]");
			//throw new DatabaseException(e.getMessage());
		}		
	}
	
	//
	//###
	private static void getAllReimbursementStatusTest() {
		String sMethod = "getAlladdReimbursementStatusTest(): ";
		objLogger.trace(sMethod + "Entered");
		
		ERSAdminService objAdminService = new ERSAdminService();

		try {
			List<ReimbursementStatus> lstReimbStatus = objAdminService.getAllReimbursementStatus();
			objLogger.debug(sMethod + "lstReimbStatus: [" + lstReimbStatus.toString() + "]");
		} catch (Exception e) {
			objLogger.error(sMethod + "Exception during processing: [" + e.getMessage() + "]");
			//throw new DatabaseException(e.getMessage());
		}		
	}

	
	//
	//###
	private static void createTablesViaHibernate() {
		String sMethod = "testAddNewEmployee(): ";
		objLogger.trace(sMethod + "Entered");

		// need to set the config.xm property to: <property name="hbm2ddl.auto">create</property>
		SessionFactory sf = SessionFactorySingleton.getSessionFactory();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		tx.commit();

	}
	
	//
	//###
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
