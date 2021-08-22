package com.tlw8253.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*; // You may need to type this import manually to make use of 
//the argument matchers for Mockito, such as eq() or any()

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import com.tlw8253.app.Constants;
import com.tlw8253.dao.GenericDAO;
import com.tlw8253.dto.UserDTO;
import com.tlw8253.exception.BadParameterException;
import com.tlw8253.exception.DatabaseException;
import com.tlw8253.model.Reimbursement;
import com.tlw8253.model.User;

public class ERSUserServiceTest implements Constants {
	private static Logger objLogger = LoggerFactory.getLogger(ERSUserServiceTest.class);

	private ERSUserService objMockERSServiceUser;
	private ERSUserService objMockERSServiceReimbursement;

	private GenericDAO<User> objMockUserDAO;
	private GenericDAO<Reimbursement> objMockReimbursementDAO;

	public ERSUserServiceTest() {
		super();
	}

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		objLogger.trace("setUpBeforeClass()");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		objLogger.trace("tearDownAfterClass()");
	}

	@Before
	public void setUp() throws Exception {
		objLogger.trace("setUp()");
		// fake DAO using the GenericDAO<T> interface class
		this.objMockUserDAO = mock(GenericDAO.class);
		this.objMockERSServiceUser = new ERSUserService().getMockUserDAO(objMockUserDAO);
		this.objMockReimbursementDAO = mock(GenericDAO.class);
//		this.objMockERSServiceReimbursement = new ERSUserService().getMockReimbursementDAO(objMockReimbursementDAO);
	}

	@After
	public void tearDown() throws Exception {
		objLogger.trace("tearDown()");
	}

	@Test	//100.000
	public void test_addNewUserSuccess() throws SQLException, BadParameterException, DatabaseException {
		objLogger.trace("test_addNewUserSuccess()");

		User mockRetValues = new User("tlw8253", "A_Pass12345", "Tomas", "Ykel", "tlw8253@wws.com");
		UserDTO objUserDTO = new UserDTO("tlw8253", "A_Pass12345", "Tomas", "Ykel", "tlw8253@wws.com",
				csUserRoles[ciUserRoleEmployee]);
		when(objMockUserDAO.addRecord(objUserDTO)).thenReturn(mockRetValues);

		User objActualValues = objMockERSServiceUser.addNewUser(objUserDTO);
		User objExpectedValues = new User("tlw8253", "A_Pass12345", "Tomas", "Ykel", "tlw8253@wws.com");

		assertEquals(objExpectedValues, objActualValues);

	}

//a whole slew of negative validation test cases can be created.
	@Test	//101.000
	public void test_addNewUserBadPwdNoCapLetter() throws SQLException, BadParameterException, DatabaseException {
		String sMethod = "test_addNewUserBadPwdNoCapLetter(): ";
		objLogger.trace(sMethod);

		//This is a service level test, no Mock DAO involved.
		UserDTO objUserDTO = new UserDTO("tlw8253", "a_pass12345", "Tomas", "Ykel", "tlw8253@wws.com",
				csUserRoles[ciUserRoleEmployee]);
		try {
			objMockERSServiceUser.addNewUser(objUserDTO);
		} catch (BadParameterException e) {
			objLogger.debug(sMethod + "BadParameterException: [" + e.getMessage() + "]");
			assertEquals(csMsgBadParamAddUser, e.getMessage());
		}

	}

	
	
	
}
