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
	private static ERSDTO objERSDTO;

	public static void main(String[] args) {
		String sMethod = "main(): ";
		objLogger.trace(sMethod + "Entered");
		
		

		//testAddNewEmployeeInvalid();

		
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
