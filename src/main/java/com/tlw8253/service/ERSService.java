package com.tlw8253.service;

import java.sql.Timestamp;
import java.sql.SQLException;
import java.util.List;

import javax.sql.rowset.serial.SerialBlob;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tlw8253.app.Constants;
import com.tlw8253.dao.GenericDAO;
import com.tlw8253.dao.UserDAOImpl;
import com.tlw8253.dto.ReimbursementDTO;
import com.tlw8253.dto.UserDTO;
import com.tlw8253.exception.*;
import com.tlw8253.model.Reimbursement;
import com.tlw8253.model.User;
import com.tlw8253.util.Validate;

public class ERSService implements Constants {
	private Logger objLogger = LoggerFactory.getLogger(ERSService.class);
	private GenericDAO<User> objUserDAO;
	private GenericDAO<Reimbursement> objReimbursementDAO;

	public ERSService() {
		this.objUserDAO = new UserDAOImpl();
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
	//### move the validation logic to public method so it 
	//		can be used by other methods here and by a driver.
	//		Also include the debug message
	public boolean isValidUserDTO(UserDTO objUserDTO) {
		String sMethod = "isValidUserDTO(): ";
		objLogger.trace(sMethod + "Entered");
		boolean isValid = false;
		
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
			isValid =true;
		}else {
			objLogger.warn(sMethod + "One or more add User Parameters did not pass validation.:");
			objLogger.warn(sMethod + "\t username: [" + sUsername + "] is valid: [" + bUsernameIsAlphaNumeric + "]");			
			objLogger.warn(sMethod + "\t password correct format: [" + bPasswordIsInFormat + "]");
			objLogger.warn(sMethod + "\t first name: [" + sFirstName + "] is valid: [" + bFirstNameIsAlpha + "]");
			objLogger.warn(sMethod + "\t last name: [" + sLastName + "] is valid: [" + bLastNameIsAlpha + "]");
			objLogger.warn(sMethod + "\t email: [" + sEmail + "] correct format: [" + bEmailIsInFormat + "]");
			objLogger.warn(sMethod + "\t user role name: [" + sUserRoleName + "] is valid: [" + bUserRoleNameIsValid + "]");
		}		
		return isValid;
	}
	
	
	//
	//###
	public User addNewUser(UserDTO objUserDTO) throws DatabaseException, BadParameterException {
		String sMethod = "addNewEmployee(): ";
		objLogger.trace(sMethod + "Entered");
		
		if (isValidUserDTO(objUserDTO)) {
			try {
				objLogger.debug(sMethod + "Validated objUserDTO: [" + objUserDTO.toString() + "]");
				
				User objUser = objUserDAO.addRecord(objUserDTO);
				objLogger.debug(sMethod + "objEmployee: [" + objUser.toString() + "]");
				return objUser;

			} catch (SQLException objE) {
				objLogger.error(sMethod + "Error adding username: [" + objUserDTO.getUsername() + "] SQLException: ["
						+ objE.getMessage() + "]");
				throw new DatabaseException(csMsgDB_ErrorAddingEmployee);
			}

		} else {
			throw new BadParameterException(csMsgBadParamAddUser);
		}
	}

	
	public boolean isValidReimbursementDTO(ReimbursementDTO objReimbDTO) {
		boolean bValid = false;
		
		String sReimbAmount = objReimbDTO.getReimbAmount();				//validate as double and > 0
		Timestamp tsReimbSubmitted = objReimbDTO.getReimbSubmitted();	//validate as time value
		Timestamp tsReimbApprover = objReimbDTO.getReimbResolved();		//validate is 0, can't have a resolver's ts
		String sReimbDescription = objReimbDTO.getReimbDescription();	//validate contains value > 10 characters
		SerialBlob sbReimbReceipt = objReimbDTO.getReimbReceipt();		//validate it has something
		int iReimbAuthorId = objReimbDTO.getReimbAuthorIdAsInt();		//validate as integer and > 0
		int iReimbResolverId = objReimbDTO.getReimbResolverIdAsInt();	//validate is 0, can't have a resolver on new request
		String sReimbStatus = objReimbDTO.getReimbStatus();				//validate value is in constant array
		String sReimbType = objReimbDTO.getReimbType();					//validate value is in constant array
		
		
		return bValid;
	}
	
	
	public Reimbursement addNewReimbursement(ReimbursementDTO objReimbursementDTO) throws DatabaseException, BadParameterException {
		String sMethod = "addNewReimbursement(): ";
		objLogger.trace(sMethod + "Entered");
		
		
		
		return null;
	}
		
	
	
	
	public User getErsLogin(String sParamUserName, String sParamPassword) {
		// TODO Auto-generated method stub
		return null;
	}

}
