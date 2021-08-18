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
import com.tlw8253.dao.GenericDAO;
import com.tlw8253.dto.ReimbursementStatusDTO;
import com.tlw8253.dto.ReimbursementTypeDTO;
import com.tlw8253.exception.*;
import com.tlw8253.model.ReimbursementStatus;
import com.tlw8253.model.ReimbursementType;



public class ERSAdminServiceTest implements Constants {
	private static Logger objLogger = LoggerFactory.getLogger(ERSAdminServiceTest.class);
	
	private ERSAdminService objMockReimbStatus;
	private ERSAdminService objMockReimbType;
	private GenericDAO<ReimbursementStatus> objMockReimbStatusDAO;
	private GenericDAO<ReimbursementType> objMockReimbTypeDAO;

	public ERSAdminServiceTest() {
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
		this.objMockReimbStatusDAO = mock(GenericDAO.class);
		this.objMockReimbStatus = new ERSAdminService().getMockReimbStatusDAO(objMockReimbStatusDAO);
		this.objMockReimbTypeDAO = mock(GenericDAO.class);
		this.objMockReimbType = new ERSAdminService().getMockReimbTypeDAO(objMockReimbTypeDAO);
	}

	@After
	public void tearDown() throws Exception {
		objLogger.trace("tearDown()");
	}

	@Test	//# 01.000
	public void testAddReimbursementStatusSuccess() throws SQLException, BadParameterException, DatabaseException {
		objLogger.trace("testAddReimbStatus()");
		
		ReimbursementStatus mockRetValues = new ReimbursementStatus("REJECTED","The request was rejected.");
		ReimbursementStatusDTO objReimStatusDTO = new ReimbursementStatusDTO("REJECTED","The request was rejected.");
		when(objMockReimbStatusDAO.addRecord(objReimStatusDTO)).thenReturn(mockRetValues);
	
		ReimbursementStatus objActualValues = objMockReimbStatus.addReimbursementStatus(objReimStatusDTO);
		
		ReimbursementStatus objExpectedValues = new ReimbursementStatus("REJECTED","The request was rejected.");
		
		assertEquals(objExpectedValues, objActualValues);
	}
	
	@Test	//# 01.001
	public void testAddReimbursementStatusDuplicate() throws SQLException, BadParameterException, DatabaseException {
		objLogger.trace("testaddReimbursementStatusDuplicate()");		
		
		ReimbursementStatusDTO objReimStatusDTO = new ReimbursementStatusDTO("REJECTED","The request was rejected.");
		when(objMockReimbStatusDAO.addRecord(objReimStatusDTO)).thenThrow(SQLException.class);

		try {
			objMockReimbStatus.addReimbursementStatus(objReimStatusDTO);
			fail();
			
		}catch(DatabaseException e) {		
		assertEquals(csMsgDB_ErrorAddingReimbursementStatus, e.getMessage());
		}
	}

	@Test	//# 01.002
	public void testAddReimbursementStatusBadParamStatus() throws SQLException, BadParameterException, DatabaseException {
		objLogger.trace("testAddReimbursementStatusBadParamStatus()");		
		
		// invalid status length
		ReimbursementStatusDTO objReimStatusDTO = new ReimbursementStatusDTO("","The request was rejected.");
		try {
			objMockReimbStatus.addReimbursementStatus(objReimStatusDTO);
			fail();
			
		}catch(BadParameterException e) {		
		assertEquals(csMsgBadParamReimbStatus, e.getMessage());
		}
	}

	@Test	//# 01.003
	public void testAddReimbursementStatusBadParamStatusDesc() throws SQLException, BadParameterException, DatabaseException {
		objLogger.trace("testAddReimbursementStatusBadParamStatus()");		
		
		//invalid status description length
		ReimbursementStatusDTO objReimStatusDTO = new ReimbursementStatusDTO("REJECT","");
		try {
			objMockReimbStatus.addReimbursementStatus(objReimStatusDTO);
			fail();
			
		}catch(BadParameterException e) {		
		assertEquals(csMsgBadParamReimbStatus, e.getMessage());
		}
	}

	
	@Test	//# 02.000
	public void testGetAllReimbursementStatusSuccess() throws SQLException{
		objLogger.trace("getAllReimbursementStatusSuccess()");

		List<ReimbursementStatus> mockRetValues = new ArrayList<>();		
		mockRetValues.add(new ReimbursementStatus("PENDING", "The status when a reimbursement request is first created and submitted by an user."));
		mockRetValues.add(new ReimbursementStatus("APPROVED", "The status when a reimbursement request is reviewed and approved by the Finance Manager."));
		mockRetValues.add(new ReimbursementStatus("DENIED", "The status when a reimbursement request is reviewed and denied by the Finance Manager."));
		when(objMockReimbStatusDAO.getAllRecords()).thenReturn(mockRetValues);
		
		List<ReimbursementStatus> lstActualValues = objMockReimbStatusDAO.getAllRecords();		
		
		List<ReimbursementStatus> lstExpectedValues = new ArrayList<>();
		lstExpectedValues.add(new ReimbursementStatus("PENDING", "The status when a reimbursement request is first created and submitted by an user."));
		lstExpectedValues.add(new ReimbursementStatus("APPROVED", "The status when a reimbursement request is reviewed and approved by the Finance Manager."));
		lstExpectedValues.add(new ReimbursementStatus("DENIED", "The status when a reimbursement request is reviewed and denied by the Finance Manager."));
		
		assertEquals(lstExpectedValues, lstActualValues);

	}
	
	@Test	//# 02.001
	public void testGetAllReimbursementStatusException() throws SQLException, BadParameterException, DatabaseException {
		objLogger.trace("getAllReimbursementStatusException()");		
		
		ReimbursementStatusDTO objReimStatusDTO = new ReimbursementStatusDTO("REJECTED","The request was rejected.");
		when(objMockReimbStatusDAO.getAllRecords()).thenThrow(SQLException.class);

		try {
			objMockReimbStatus.getAllReimbursementStatus();
			fail();
			
		}catch(DatabaseException e) {		
		assertEquals(csMsgDB_ErrorGettingReimbursementStatus, e.getMessage());
		}
	}

	
	@Test	//# 03.000
	public void testAddReimbursementTypeSuccess() throws SQLException, BadParameterException, DatabaseException {
		objLogger.trace("testAddReimbStatus()");
		
		ReimbursementType mockRetValues = new ReimbursementType("MOVIE","Expenses related to watching movies.");
		ReimbursementTypeDTO objReimbTypeDTO = new ReimbursementTypeDTO("MOVIE","Expenses related to watching movies.");
		when(objMockReimbTypeDAO.addRecord(objReimbTypeDTO)).thenReturn(mockRetValues);
	
		ReimbursementType objActualValues = objMockReimbType.addReimbursementType(objReimbTypeDTO);
		
		ReimbursementType objExpectedValues = new ReimbursementType("MOVIE","Expenses related to watching movies.");
		
		assertEquals(objExpectedValues, objActualValues);
	}

	
	@Test	//# 04.000
	public void testGetAllReimbursementTypeSuccess() throws SQLException{
		objLogger.trace("testGetAllReimbursementTypeSuccess()");

		List<ReimbursementType> mockRetValues = new ArrayList<>();		
		mockRetValues.add(new ReimbursementType("LODGING", "Reimbursement expense related to overnight stays related to business travel."));
		mockRetValues.add(new ReimbursementType("TRAVEL", "Reimbursement expense related to planes, trains, automobiles, etc."));
		mockRetValues.add(new ReimbursementType("FOOD", "Reimbursement expense related to meals related to business travel."));
		mockRetValues.add(new ReimbursementType("OTHER", "Reimbursement expense related to other expenses related to the business."));
		when(objMockReimbTypeDAO.getAllRecords()).thenReturn(mockRetValues);
		
		List<ReimbursementType> lstActualValues = objMockReimbTypeDAO.getAllRecords();		
		
		List<ReimbursementType> lstExpectedValues = new ArrayList<>();
		lstExpectedValues.add(new ReimbursementType("LODGING", "Reimbursement expense related to overnight stays related to business travel."));
		lstExpectedValues.add(new ReimbursementType("TRAVEL", "Reimbursement expense related to planes, trains, automobiles, etc."));
		lstExpectedValues.add(new ReimbursementType("FOOD", "Reimbursement expense related to meals related to business travel."));
		lstExpectedValues.add(new ReimbursementType("OTHER", "Reimbursement expense related to other expenses related to the business."));
		
		assertEquals(lstExpectedValues, lstActualValues);

	}

	
	
}
