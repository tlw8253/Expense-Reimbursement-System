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

public class ERSAdminService implements Constants {
	private Logger objLogger = LoggerFactory.getLogger(ERSAdminService.class);
	private GenericDAO<User> objErsDAOHib;

	public ERSAdminService() {
		this.objErsDAOHib = new ERSDAOImpl();
	}

	public ERSAdminService(GenericDAO<User> objMockedAdminDAO) {
		this.objErsDAOHib = objMockedAdminDAO;
	}


	//
	//###
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
