package com.tlw8253.app;

import java.util.List;

import java.util.*;
import java.lang.*;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.io.*;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tlw8253.dao.ReimbursementStatusDAOImpl;
import com.tlw8253.dao.ReimbursementTypeDAOImpl;
import com.tlw8253.dao.UserDAOImpl;
import com.tlw8253.dao.UserRoleDAOImpl;
import com.tlw8253.dto.ReimbursementStatusDTO;
import com.tlw8253.dto.ReimbursementTypeDTO;
import com.tlw8253.dto.UserDTO;
import com.tlw8253.dto.UserRoleDTO;
import com.tlw8253.exception.BadParameterException;
import com.tlw8253.exception.DatabaseException;
import com.tlw8253.model.ReimbursementStatus;
import com.tlw8253.model.ReimbursementType;
import com.tlw8253.model.User;
import com.tlw8253.model.UserRole;
import com.tlw8253.service.ERSAdminService;
import com.tlw8253.service.ERSUserService;
import com.tlw8253.util.*;



/**
 * This is a driver used during development to test functionality as it is
 * built. 
 * 
 * @author tlw8748253
 *
 */
public class Driver implements Constants {
	private final static Logger objLogger = LoggerFactory.getLogger(Driver.class);
	private static ERSUserService objERSService = new ERSUserService();
	private static UserDTO objUserDTO = new UserDTO();

	public static void main(String[] args) {
		String sMethod = "\n\t main(): ";
		objLogger.trace(sMethod + "Entered");
		
		

		//testUserRoleDAOImpl_UserRole_getByRecordIdentifer();
		//testUserRoleDAOImpl_UserRole_getAllRecords();	
		//timestamp();
		//testUserDAOImpl_getByRecordId(1);
		//testReimbursementStatusDAOImpl_getByRecordId(1);
		//testReimbursementStatusDAOImpl_getByRecordIdentifer(csReimbStatus[ciReimbStatusDenied]);
		//testReimbursementTypeDAOImpl_getByRecordIdentifer(csReimbType[ciReimbTypeTravel]);
		
		testERSService_ControlTestCases();

		
	}
	
	public static void testERSService_ControlTestCases() {
		String sMethod = "\n\t testERSService_ControlTestCases(): ";
		objLogger.trace(sMethod + "Entered");
		
		//testERSService_getAllUsers();
		//testERSService_getUsersById("1");		
		//testERSService_addNewUser("zlb8253", "A_Pass12345", "Zack", "Brown", "zlb8253@wws.com", csUserRoles[ciUserRoleEmployee]);
		//Admin.addNewReimbursement("6");	//run after test above with new UID created
		//testERSService_getUsersByUsername("zlb8253");
		//testERSService_editExistingUser("zlb8253", "Zack", "Brown II", csUserRoles[ciUserRoleAdmin]);		
		//testERSService_deleteUserByUsername("zlb8253");
		
		
	}
	
	// ###
	public static void testERSService_deleteUserByUsername(String sUsername) {
		String sMethod = "\n\t testERSService_deleteUserByUsername(): ";
		objLogger.trace(sMethod + "Entered");

		try {
			boolean isDeleted =  objERSService.deleteUserByUsername(sUsername);
			objLogger.debug(sMethod + "User with username: [" + sUsername + "] isDeleted: [" + isDeleted + "]");
		} catch (Exception e) {
			objLogger.debug(sMethod + "Exception: [" + e.getMessage() + "]");
		}

	}

	// ###
	public static void testERSService_editExistingUser(String sUsername, String sFirstName, String sLastName, String sRole) {
		String sMethod = "\n\t testERSService_editExistingUser(): ";
		objLogger.trace(sMethod + "Entered");

		objUserDTO = new UserDTO(sUsername, "", sFirstName, sLastName, "", sRole);
		objLogger.debug(sMethod + "objUserDTO: [" + objUserDTO.toString() + "]");

		try {
			User objEmployee = objERSService.editExistingUser(objUserDTO);
			objLogger.debug(sMethod + "objEmployee: [" + objEmployee.toString() + "]");
		} catch (Exception e) {
			objLogger.debug(sMethod + "Exception: [" + e.getMessage() + "]");
		}

	}

	
	
	//###
	public static void testERSService_getUsersByUsername(String sUsername) {
		String sMethod = "\n\t testERSService_getByRecordId(): ";
		objLogger.trace(sMethod + "Entered");
		
		try {
			User objUsr = objERSService.getUsersByUsername(sUsername);
			objLogger.trace(sMethod + "lstUsr: [" + objUsr.toString() + "]");
		}catch(Exception e) {			
			objLogger.debug(sMethod + "Error getting user by username: " + sUsername + "]" + "\n\t Exception: [" + e.toString() + "] [" + e.getMessage() + "]");		
		}
		
	}

	
	
	// ###
	public static void testERSService_addNewUser(String sUsername, String sPassword, String sFirstName, String sLastName,
			String sEmail, String sRole) {
		String sMethod = "\n\t testERSService_addNewUser(): ";
		objLogger.trace(sMethod + "Entered");

		objUserDTO = new UserDTO(sUsername, sPassword, sFirstName, sLastName, sEmail, sRole);
		objLogger.debug(sMethod + "objUserDTO: [" + objUserDTO.toString() + "]");

		try {
			User objEmployee = objERSService.addNewUser(objUserDTO);
			objLogger.debug(sMethod + "objEmployee: [" + objEmployee.toString() + "]");
		} catch (Exception e) {
			objLogger.debug(sMethod + "Exception: [" + e.getMessage() + "]");
		}

	}

	
	//###
	public static void testERSService_getUsersById(String sUserId) {
		String sMethod = "\n\t testERSService_getByRecordId(): ";
		objLogger.trace(sMethod + "Entered");
		
		try {
			User objUsr = objERSService.getUsersById(sUserId);
			objLogger.trace(sMethod + "lstUsr: [" + objUsr.toString() + "]");
		}catch(Exception e) {			
			objLogger.debug(sMethod + "Error getting user by id: " + sUserId + "]" + "\n\t Exception: [" + e.toString() + "] [" + e.getMessage() + "]");		
		}
		
	}

	//###
	public static void testERSService_getAllUsers() {
		String sMethod = "\n\t testERSService_getAllUsers(): ";
		objLogger.trace(sMethod + "Entered");
		
		try {
			List<User> lstUsr = objERSService.getAllUsers();
			objLogger.trace(sMethod + "lstUsr: [" + lstUsr.toString() + "]");
		}catch(Exception e) {			
			objLogger.debug(sMethod + "Exception: [" + e.toString() + "] [" + e.getMessage() + "]");		
		}
	}
	
	
	//###
	public static void testReimbursementTypeDAOImpl_getByRecordIdentifer(String sID) {
		String sMethod = "\n\t testReimbursementTypeDAOImpl_getByRecordIdentifer(): ";
		objLogger.trace(sMethod + "Entered");

		ReimbursementTypeDAOImpl objReimbursementTypeDAOImpl = new ReimbursementTypeDAOImpl();
		
		try {
			ReimbursementType objReimbursementType = objReimbursementTypeDAOImpl.getByRecordIdentifer(sID);
			objLogger.debug(sMethod + "objUser: [" + objReimbursementType.toString() + "]");
		} catch (Exception e) {
			objLogger.debug(sMethod + "Exception: [" + e.toString() + "] [" + e.getMessage() + "]");		
		}

	}

	
	//###
	public static void testReimbursementStatusDAOImpl_getByRecordIdentifer(String sID) {
		String sMethod = "\n\t testReimbursementStatusDAOImpl_getByRecordIdentifer(): ";
		objLogger.trace(sMethod + "Entered");

		ReimbursementStatusDAOImpl objReimbursementStatusDAOImpl = new ReimbursementStatusDAOImpl();
		
		try {
			ReimbursementStatus objReimbursementStatus = objReimbursementStatusDAOImpl.getByRecordIdentifer(sID);
			objLogger.debug(sMethod + "objUser: [" + objReimbursementStatus.toString() + "]");
		} catch (Exception e) {
			objLogger.debug(sMethod + "Exception: [" + e.toString() + "] [" + e.getMessage() + "]");		
		}

	}

	
	
	//###
	public static void testReimbursementStatusDAOImpl_getByRecordId(int iID) {
		String sMethod = "\n\t testReimbursementStatusDAOImpl_getByRecordId(): ";
		objLogger.trace(sMethod + "Entered");

		ReimbursementStatusDAOImpl objReimbursementStatusDAOImpl = new ReimbursementStatusDAOImpl();
		
		try {
			ReimbursementStatus objReimbursementStatus = objReimbursementStatusDAOImpl.getByRecordId(iID);
			objLogger.debug(sMethod + "objUser: [" + objReimbursementStatus.toString() + "]");
		} catch (Exception e) {
			objLogger.debug(sMethod + "Exception: [" + e.toString() + "] [" + e.getMessage() + "]");		
		}

	}

	
	
	//###
	public static void testUserDAOImpl_getByRecordId(int iUID) {
		String sMethod = "\n\t testUserDAOImpl_getByRecordId(): ";
		objLogger.trace(sMethod + "Entered");

		UserDAOImpl objUserDAOImpl = new UserDAOImpl();
		
		try {
			User objUser = objUserDAOImpl.getByRecordId(iUID);
			objLogger.debug(sMethod + "objUser: [" + objUser.toString() + "]");
		} catch (Exception e) {
			objLogger.debug(sMethod + "Exception: [" + e.toString() + "] [" + e.getMessage() + "]");		
		}

	}
	
	//###
	public static void timestamp() {
		String sMethod = "\n\t timestamp(): ";
		objLogger.trace(sMethod + "Entered");
		
		objLogger.debug(sMethod + "LocalDateTime.now(): [" + LocalDateTime.now() + "]");
		Timestamp.valueOf(LocalDateTime.now());
		
		objLogger.debug(sMethod + "Timestamp.valueOf(LocalDateTime.now());: [" + Timestamp.valueOf(LocalDateTime.now()) + "]");
		
		Timestamp objTimestamp = Timestamp.valueOf(LocalDateTime.now());//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		
		objLogger.debug(sMethod + "objTimestamp.getTime(): [" + objTimestamp.getTime() + "]");
	}
	
	//###
	public static void testUserRoleDAOImpl_UserRole_getAllRecords() {
		String sMethod = "\n\t testUserRoleDAOImpl_UserRole_getAllRecords(): ";
		objLogger.trace(sMethod + "Entered");
		
		UserRoleDAOImpl objUserRoleDAOImpl = new UserRoleDAOImpl();

		try {
			List<UserRole> lstUserRole = objUserRoleDAOImpl.getAllRecords();
			objLogger.debug(sMethod + "lstUserRole: [" + lstUserRole.toString() + "]");
		} catch (Exception e) {
			objLogger.debug(sMethod + "Exception: [" + e.toString() + "] [" + e.getMessage() + "]");		
		}

		
	}
	
	//###
	public static void testUserRoleDAOImpl_UserRole_getByRecordIdentifer() {
		String sMethod = "\n\t testUserRoleDAOImpl_UserRole_getByRecordIdentifer(): ";
		objLogger.trace(sMethod + "Entered");
		
		UserRoleDAOImpl objUserRoleDAOImpl = new UserRoleDAOImpl();
		try {
			UserRole objUserRole = objUserRoleDAOImpl.getByRecordIdentifer(csUserRoles[ciUserRoleAdmin]);
			objLogger.debug(sMethod + "objUserRole: [" + objUserRole.toString() + "]");
		} catch (SQLException e) {
			objLogger.debug(sMethod + "SQLException: [" + e.toString() + "] [" + e.getMessage() + "]");
		} catch (Exception e) {
			objLogger.debug(sMethod + "Exception: [" + e.toString() + "] [" + e.getMessage() + "]");		
		}

	}



	
	//###
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



}
