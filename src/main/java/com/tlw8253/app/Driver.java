package com.tlw8253.app;

import java.sql.SQLException;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tlw8253.dto.LoginDTO;
import com.tlw8253.dto.ReimbursementDTO;
import com.tlw8253.dto.ReimbursementStatusDTO;
import com.tlw8253.dto.ReimbursementTypeDTO;
import com.tlw8253.dto.UserDTO;
import com.tlw8253.dto.UserRoleDTO;
import com.tlw8253.exception.AuthenticationFailureException;
import com.tlw8253.exception.BadParameterException;
import com.tlw8253.exception.DatabaseException;
import com.tlw8253.model.ReimbursementStatus;
import com.tlw8253.model.ReimbursementType;
import com.tlw8253.model.User;
import com.tlw8253.model.UserRole;
import com.tlw8253.service.ERSAdminService;
import com.tlw8253.service.ERSLoginService;
import com.tlw8253.service.ERSReimbService;
import com.tlw8253.service.ERSUserService;
import com.tlw8253.util.*;

/**
 * This is a driver for admin functionality until an admin front-end is built.
 * It has methods to create the Hibranate schema, load static type values and
 * add users.
 * 
 * @author tlw8748253
 *
 */

public class Driver implements Constants {
	private static Logger objLogger = LoggerFactory.getLogger(Driver.class);

	public static void main(String[] args) {
		String sMethod = "\n\t main(): ";
		objLogger.trace(sMethod + "Entered");

		test_ERSLoginService_login("tlw8253", "A_Pass12345");

	}

	//
	// ###
	public static void test_ERSLoginService_login(String sUsername, String sPassword) {
		String sMethod = "\n\t test_ERSLoginService_login(): ";
		objLogger.trace(sMethod + "Entered");
		
		
	}

}
















