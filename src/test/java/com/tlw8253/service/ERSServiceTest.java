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
// the argument matchers for Mockito, such as eq() or any()

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;


import com.tlw8253.app.Constants;
import com.tlw8253.dao.ERSDAOImpl;
import com.tlw8253.dao.GenericDAO;

public class ERSServiceTest implements Constants {
	private static Logger objLogger = LoggerFactory.getLogger(ERSServiceTest.class);
	private ERSService objMockService;

	public ERSServiceTest() {
		// TODO Auto-generated constructor stub
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
		//fake DAO using the GenericDAO<T> interface class		
		this.objMockService = new ERSService();
	}

	@After
	public void tearDown() throws Exception {
		objLogger.trace("tearDown()");
	}

	@Test
	public void testGetAllRecords_EmptyDataBase() throws SQLException{
		objLogger.trace("test_getAllClients_success()");
		
	}
	

	@Test
	public void testGetAllRecords_Success() throws SQLException{
		objLogger.trace("test_getAllClients_success()");
/*
		List<Employee> mockRetValues = new ArrayList<>();		
		mockRetValues.add(new Employee("Arnold", 1, 1.1, true, 1));
		mockRetValues.add(new Employee("Linda", 3, 3.3, false, 2));
		mockRetValues.add(new Employee("Edward", 5, 5.5, true, 3));
		mockRetValues.add(new Employee("Robert", 10, 10.01, false, 4));
		when(objMockDAO.getAllRecords()).thenReturn(mockRetValues);
		
		List<Employee> lstActualValues = objMockDAO.getAllRecords();		
		
		List<Employee> lstExpectedValues = new ArrayList<>();		
		lstExpectedValues.add(new Employee("Arnold", 1, 1.1, true, 1));
		lstExpectedValues.add(new Employee("Linda", 3, 3.3, false, 2));
		lstExpectedValues.add(new Employee("Edward", 5, 5.5, true, 3));
		lstExpectedValues.add(new Employee("Robert", 10, 10.01, false, 4));
		
		assertEquals(lstExpectedValues, lstActualValues);
*/
	}
	

}



















