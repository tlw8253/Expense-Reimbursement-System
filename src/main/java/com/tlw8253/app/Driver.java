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

import com.tlw8253.dao.UserRoleDAOImpl;
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
	private static ERSService objERSService = new ERSService();

	public static void main(String[] args) {
		String sMethod = "main(): ";
		objLogger.trace(sMethod + "Entered");
		
		

		//testUserRoleDAOImpl_UserRole_getByRecordIdentifer();
		//testUserRoleDAOImpl_UserRole_getAllRecords();
		
		timestamp();

		
	}
	
	public static void timestamp() {
		String sMethod = "timestamp(): ";
		objLogger.trace(sMethod + "Entered");
		
		objLogger.debug(sMethod + "LocalDateTime.now(): [" + LocalDateTime.now() + "]");
		Timestamp.valueOf(LocalDateTime.now());
		
		objLogger.debug(sMethod + "Timestamp.valueOf(LocalDateTime.now());: [" + Timestamp.valueOf(LocalDateTime.now()) + "]");
		
		Timestamp objTimestamp = Timestamp.valueOf(LocalDateTime.now());//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		
		objLogger.debug(sMethod + "objTimestamp.getTime(): [" + objTimestamp.getTime() + "]");
	}
	
	public static void testUserRoleDAOImpl_UserRole_getAllRecords() {
		String sMethod = "testUserRoleDAOImpl_UserRole_getAllRecords(): ";
		objLogger.trace(sMethod + "Entered");
		
		UserRoleDAOImpl objUserRoleDAOImpl = new UserRoleDAOImpl();

		try {
			List<UserRole> lstUserRole = objUserRoleDAOImpl.getAllRecords();
			objLogger.debug(sMethod + "lstUserRole: [" + lstUserRole.toString() + "]");
		} catch (Exception e) {
			objLogger.debug(sMethod + "Exception: [" + e.toString() + "] [" + e.getMessage() + "]");		
		}

		
	}
	
	public static void testUserRoleDAOImpl_UserRole_getByRecordIdentifer() {
		String sMethod = "testUserRoleDAOImpl_UserRole_getByRecordIdentifer(): ";
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
