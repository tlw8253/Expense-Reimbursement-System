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
import com.tlw8253.dao.GeneralDAOImpl;
import com.tlw8253.dao.GenericDAO;
import com.tlw8253.model.General;

public class GeneralServiceTest implements Constants {
	private static Logger objLogger = LoggerFactory.getLogger(GeneralServiceTest.class);
	private GenericDAO<General> objMockDAO;
	private GeneralService objMockService;

	public GeneralServiceTest() {
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
		this.objMockDAO = mock(GenericDAO.class);
		this.objMockService = new GeneralService();
	}

	@After
	public void tearDown() throws Exception {
		objLogger.trace("tearDown()");
	}

	@Test
	public void testGetAllRecords_EmptyDataBase() throws SQLException{
		objLogger.trace("test_getAllClients_success()");
		
		List<General> mockRetValues = new ArrayList<>();		
		when(objMockDAO.getAllRecords()).thenReturn(mockRetValues);
		
		List<General> lstActualValues = objMockDAO.getAllRecords();
		List<General> lstExpectedValues = new ArrayList<>();		
		
		assertEquals(lstExpectedValues, lstActualValues);
	}
	

	@Test
	public void testGetAllRecords_Success() throws SQLException{
		objLogger.trace("test_getAllClients_success()");
		
		List<General> mockRetValues = new ArrayList<>();		
		mockRetValues.add(new General("Arnold", 1, 1.1, true, 1));
		mockRetValues.add(new General("Linda", 3, 3.3, false, 2));
		mockRetValues.add(new General("Edward", 5, 5.5, true, 3));
		mockRetValues.add(new General("Robert", 10, 10.01, false, 4));
		when(objMockDAO.getAllRecords()).thenReturn(mockRetValues);
		
		List<General> lstActualValues = objMockDAO.getAllRecords();		
		
		List<General> lstExpectedValues = new ArrayList<>();		
		lstExpectedValues.add(new General("Arnold", 1, 1.1, true, 1));
		lstExpectedValues.add(new General("Linda", 3, 3.3, false, 2));
		lstExpectedValues.add(new General("Edward", 5, 5.5, true, 3));
		lstExpectedValues.add(new General("Robert", 10, 10.01, false, 4));
		
		assertEquals(lstExpectedValues, lstActualValues);
	}
	

}



















