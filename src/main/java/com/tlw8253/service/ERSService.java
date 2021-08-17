package com.tlw8253.service;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tlw8253.app.Constants;
import com.tlw8253.dao.ERSDAOImpl;
import com.tlw8253.dao.ERSDAOJDBCImpl;
import com.tlw8253.dao.GenericDAO;
import com.tlw8253.dto.ERSDTO;
import com.tlw8253.exception.*;
import com.tlw8253.model.User;
import com.tlw8253.model.EmployeeJDBC;

public class ERSService implements Constants {
	private Logger objLogger = LoggerFactory.getLogger(ERSService.class);
//	private GeneralDAOImpl objGenericDAOImpl;
	private GenericDAO<EmployeeJDBC> objErsDAOJDBC;
	private GenericDAO<User> objErsDAOHib;

	public ERSService() {
		this.objErsDAOJDBC = new ERSDAOJDBCImpl();
		this.objErsDAOHib = new ERSDAOImpl();
	}

	public ERSService(GenericDAO<EmployeeJDBC> objMockedGeneralDAO) {
		this.objErsDAOJDBC = objMockedGeneralDAO;
	}

	public List<EmployeeJDBC> getReturnGeneral() throws DatabaseException, RecordNotFoundException {
		String sMethod = "getReturnGeneral(): ";
		objLogger.trace(sMethod + "Entered");

		try {
			List<EmployeeJDBC> lstGeneral = objErsDAOJDBC.getAllRecords();
			if (lstGeneral.size() == 0) {
				objLogger.debug(sMethod + "record not found");
				throw new RecordNotFoundException("record not found");
			}

			objLogger.debug(sMethod + "lstGeneral: [" + lstGeneral.toString() + "]");
			return lstGeneral;
		} catch (SQLException objE) {
			objLogger.debug(sMethod + "SQLException: [" + objE.getMessage() + "]");
			throw new DatabaseException(sMethod);
		}
	}


	//
	//###
	public EmployeeJDBC getErsLoginJDBC(String sUsername, String sPassword) throws DatabaseException, RecordNotFoundException, AuthenticationFailureException {
		String sMethod = "getErsLogin(): ";
		objLogger.trace(sMethod + "Entered");

		try {
			EmployeeJDBC objEmployee = objErsDAOJDBC.getLoginJDBC(sUsername);

			if (objEmployee == null) {
				String sMsg = "Employee with username: [" + sUsername + "] not in database.";
				objLogger.debug(sMethod + sMsg);
				throw new RecordNotFoundException(csMsgEmployeeRecordNotFound);
			}
			objLogger.debug(sMethod + "objEmployee: [" + objEmployee.toString() + "]");

			// password validation here
			String sEmpPwdDB = objEmployee.getPassword();
			if(sEmpPwdDB.equalsIgnoreCase(sPassword)) {		
				objLogger.debug(sMethod + "Employee with username: [" + sUsername + "] authenticated.");
				return objEmployee;
			} else {
				objLogger.debug(sMethod + "Employee with username: [" + sUsername + "] failed autentication.");
				throw new AuthenticationFailureException(csMsgAutenticationFailed);
			}

			

		} catch (SQLException objE) {
			objLogger.debug(sMethod + "SQLException: [" + objE.getMessage() + "]");
			throw new DatabaseException(csMsgDB_ErrorGettingWithLogin);
		}
		
	}

	
	//
	//###
	public EmployeeJDBC getErsLogin(String sUsername, String sPassword) throws DatabaseException, RecordNotFoundException, AuthenticationFailureException {
		String sMethod = "getErsLogin(): ";
		objLogger.trace(sMethod + "Entered");

		try {
			EmployeeJDBC objEmployee = objErsDAOJDBC.getLogin(sUsername);

			if (objEmployee == null) {
				String sMsg = "Employee with username: [" + sUsername + "] not in database.";
				objLogger.debug(sMethod + sMsg);
				throw new RecordNotFoundException(csMsgEmployeeRecordNotFound);
			}
			objLogger.debug(sMethod + "objEmployee: [" + objEmployee.toString() + "]");

			// password validation here
			String sEmpPwdDB = objEmployee.getPassword();
			if(sEmpPwdDB.equalsIgnoreCase(sPassword)) {		
				objLogger.debug(sMethod + "Employee with username: [" + sUsername + "] authenticated.");
				return objEmployee;
			} else {
				objLogger.debug(sMethod + "Employee with username: [" + sUsername + "] failed autentication.");
				throw new AuthenticationFailureException(csMsgAutenticationFailed);
			}

			

		} catch (SQLException objE) {
			objLogger.debug(sMethod + "SQLException: [" + objE.getMessage() + "]");
			throw new DatabaseException(csMsgDB_ErrorGettingWithLogin);
		}
		
	}
	
	
	public User addNewEmployee(ERSDTO objERSDTO) throws DatabaseException {
		String sMethod = "addNewEmployee(): ";
		objLogger.trace(sMethod + "Entered");

		
		String sUsername = objERSDTO.getDataElement(csUserTblUsername);
		String sPassword = objERSDTO.getDataElement(csUserTblPassword);
		String sFirstName = objERSDTO.getDataElement(csUserTblFirstName);
		String sLastName = objERSDTO.getDataElement(csUsrTblLastName);
		String sEmail = objERSDTO.getDataElement(csUserTblEmail);
		
		try {
		User objEmployee = objErsDAOHib.addRecord(objERSDTO);
		objLogger.debug(sMethod + "objEmployee: [" + objEmployee.toString() + "]");
		return objEmployee;
		
		}catch(SQLException objE) {
			objLogger.debug(sMethod + "Error adding username: [" + sUsername + "] SQLException: [" + objE.getMessage() + "]");
			throw new DatabaseException(csMsgDB_ErrorAddingEmployee);
		}
	}
	
	
}
