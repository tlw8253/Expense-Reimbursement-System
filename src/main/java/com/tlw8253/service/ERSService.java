package com.tlw8253.service;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tlw8253.app.Constants;
import com.tlw8253.dao.ERSDAOImpl;
import com.tlw8253.dao.GenericDAO;
import com.tlw8253.dto.ERSDTO;
import com.tlw8253.dto.UserDTO;
import com.tlw8253.exception.*;
import com.tlw8253.model.User;
import com.tlw8253.util.Validate;

public class ERSService implements Constants {
	private Logger objLogger = LoggerFactory.getLogger(ERSService.class);
//	private GeneralDAOImpl objGenericDAOImpl;
	private GenericDAO<User> objErsDAOHib;

	public ERSService() {
		this.objErsDAOHib = new ERSDAOImpl();
	}

	private boolean isValidUserRole(String sUserRole) {
		boolean bValid = false;
		
		for (int iCtr=0; iCtr<csUserRoles.length; iCtr++) {
			if(sUserRole.equalsIgnoreCase(csUserRoles[iCtr])) {
				bValid = true;
				break;
			}
		}
		
		return bValid;
	}
	
	//
	//###
	public User addNewUser(UserDTO objUserDTO) throws DatabaseException, BadParameterException {
		String sMethod = "addNewEmployee(): ";
		objLogger.trace(sMethod + "Entered");

		String sUsername = objUserDTO.getUsername();
		String sPassword = objUserDTO.getPassword();
		String sFirstName = objUserDTO.getFirstName();
		String sLastName = objUserDTO.getLastName();
		String sEmail = objUserDTO.getEmail();
		String sUserRoleName = objUserDTO.getUserRoleName();

		boolean bUsernameIsAlphaNumeric = Validate.isAlphaNumeric(sUsername);
		boolean bFirstNameIsAlpha = Validate.isAlpha(sFirstName);
		boolean bLastNameIsAlpha = Validate.isAlpha(sLastName);
		boolean bPasswordIsInFormat = Validate.isPasswordFormat(sPassword);
		boolean bEmailIsInFormat = Validate.isValidEmailAddress(sEmail);
		boolean bUserRoleNameIsValid = isValidUserRole(sUserRoleName);

		if (bUsernameIsAlphaNumeric && bFirstNameIsAlpha && bLastNameIsAlpha && bPasswordIsInFormat
				&& bEmailIsInFormat && bUserRoleNameIsValid) {
			try {
				User objEmployee = objErsDAOHib.addRecord(objUserDTO);
				objLogger.debug(sMethod + "objEmployee: [" + objEmployee.toString() + "]");
				return objEmployee;

			} catch (SQLException objE) {
				objLogger.debug(sMethod + "Error adding username: [" + sUsername + "] SQLException: ["
						+ objE.getMessage() + "]");
				throw new DatabaseException(csMsgDB_ErrorAddingEmployee);
			}

		} else {
			objLogger.warn(sMethod + "One or more add User Parameters did not pass validation.:");
			objLogger.warn(sMethod + "\t username: [" + sUsername + "] is valid: [" + bUsernameIsAlphaNumeric + "]");			
			objLogger.warn(sMethod + "\t password correct format: [" + bPasswordIsInFormat + "]");
			objLogger.warn(sMethod + "\t first name: [" + sFirstName + "] is valid: [" + bFirstNameIsAlpha + "]");
			objLogger.warn(sMethod + "\t last name: [" + sLastName + "] is valid: [" + bLastNameIsAlpha + "]");
			objLogger.warn(sMethod + "\t email: [" + sEmail + "] correct format: [" + bEmailIsInFormat + "]");
			objLogger.warn(sMethod + "\t user role name: [" + sUserRoleName + "] is valid: [" + bUserRoleNameIsValid + "]");
			
			throw new BadParameterException(csMsgBadParamAddUser);

		}
	}

	public User getErsLogin(String sParamUserName, String sParamPassword) {
		// TODO Auto-generated method stub
		return null;
	}

}
