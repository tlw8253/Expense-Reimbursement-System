package com.tlw8253.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.sql.SQLException;
import java.util.List;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

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
import com.tlw8253.util.PasswordUtil;
import com.tlw8253.util.Validate;

public class ERSUserService implements Constants {
	private Logger objLogger = LoggerFactory.getLogger(ERSUserService.class);
	private GenericDAO<User> objUserDAO;

	public ERSUserService() {
		this.objUserDAO = new UserDAOImpl();
	}

	public ERSUserService getMockUserDAO(GenericDAO<User> objMockUserDAO) {
		this.objUserDAO = objMockUserDAO;
		return this;
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////////

	public User getErsLogin(String sParamUserName, String sParamPassword) {
		// TODO Auto-generated method stub
		return null;
	}

	//
	// ###
	public List<User> getAllUsers() throws DatabaseException {
		String sMethod = "\n\t getAllUsers(): ";
		objLogger.trace(sMethod + "Entered");

		try {
			List<User> lstUser = objUserDAO.getAllRecords();
			objLogger.debug(sMethod + "lstUser: [" + lstUser.toString() + "]");
			return lstUser;

		} catch (Exception e) {// not sure what exception hibernate throws but not SQLException
			objLogger.error(sMethod + "Exception getting all User records from the database: Exception: ["
					+ e.toString() + "] [" + e.getMessage() + "]");
			throw new DatabaseException(csMsgDB_ErrorGettingEmployees);
		}
	}

	//
	// ###
	public User getUsersById(String sUserId) throws DatabaseException, BadParameterException {
		String sMethod = "\n\t getUsersById(): ";
		objLogger.trace(sMethod + "Entered");

		if (Validate.isInt(sUserId) && Integer.parseInt(sUserId) > 0) {
			int iUserId = Integer.parseInt(sUserId);
			try {
				User objUser = objUserDAO.getByRecordId(iUserId);
				objLogger.debug(sMethod + "objUser: [" + objUser.toString() + "]");
				return objUser;

			} catch (Exception e) {// not sure what exception hibernate throws but not SQLException
				objLogger.error(sMethod + "Exception getting all User records from the database: Exception: ["
						+ e.toString() + "] [" + e.getMessage() + "]");
				throw new DatabaseException(csMsgDB_ErrorGettingEmployeeById);
			}
		}else {
			objLogger.debug(sMethod + "Invalid user id received: [" + sUserId + "]");
			throw new BadParameterException(csMsgBadParamGetUserById);			
		}
	}
	
	public User getUsersByUsername(String sUsername) throws DatabaseException, BadParameterException {
		String sMethod = "\n\t getUsersByUsername(): ";
		objLogger.trace(sMethod + "Entered");

		if (Validate.isAlphaNumeric(sUsername) && sUsername.length() == ciUsernameLength) {			
			try {
				User objUser = objUserDAO.getByRecordIdentifer(sUsername);
				objLogger.debug(sMethod + "objUser: [" + objUser.toString() + "]");
				return objUser;

			} catch (Exception e) {// not sure what exception hibernate throws but not SQLException
				objLogger.error(sMethod + "Exception getting all User records from the database: Exception: ["
						+ e.toString() + "] [" + e.getMessage() + "]");
				throw new DatabaseException(csMsgDB_ErrorGettingEmployeeByUsername);
			}
		}else {
			objLogger.debug(sMethod + "Invalid user name received: [" + sUsername + "]");
			throw new BadParameterException(csMsgBadParamGetUserByUsername);			
		}
	}

	//
	// ###
	public User addNewUser(UserDTO objUserDTO) throws DatabaseException, BadParameterException {
		String sMethod = "\n\t addNewEmployee(): ";
		objLogger.trace(sMethod + "Entered");

		if (isValidUserDTO(objUserDTO)) {
			try {
				objLogger.debug(sMethod + "Validated objUserDTO: [" + objUserDTO.toString() + "]");
				
				//The DTO is valid, the password is validated to program rules
				//now encrypt the password and get the salt encryption key
				String sPassword = objUserDTO.getPassword();
				String sSalt = PasswordUtil.getSalt(30);
				String sEncryptedPassword = PasswordUtil.generateSecurePassword(sPassword, sSalt);
				//over write clear password with encrypted value and add salt key
				objUserDTO.setPassword(sEncryptedPassword);
				objUserDTO.setPasswordSalt(sSalt);

				User objUser = objUserDAO.addRecord(objUserDTO);
				objLogger.debug(sMethod + "objEmployee: [" + objUser.toString() + "]");
				return objUser;

			} catch (Exception e) {// not sure what exception hibernate throws but not SQLException
				objLogger.error(sMethod + "Exception adding User record with username: [" + objUserDTO.getUsername()
						+ "] Exception: [" + e.toString() + "] [" + e.getMessage() + "]");
				throw new DatabaseException(csMsgDB_ErrorAddingEmployee);
			}

		} else {
			objLogger.warn(sMethod + "objUserDTO is not valid: [" + objUserDTO.toString() + "]");
			throw new BadParameterException(csMsgBadParamAddUser);
		}
	}

	
	//
	// ### Cannot update UserId or the username
	//will not update the record id, the company username, or the company email.
	//also password should be updated through separate process where old pwd and new pwd are entered
	public User editExistingUser(UserDTO objUserDTO) throws DatabaseException, BadParameterException {
		String sMethod = "\n\t EditExistingUser(): ";
		objLogger.trace(sMethod + "Entered");

		if (isValidUserDTOEditAttributes(objUserDTO)) {
			try {
				objLogger.debug(sMethod + "Validated objUserDTO: [" + objUserDTO.toString() + "]");

				User objUser = objUserDAO.editRecord(objUserDTO);
				objLogger.debug(sMethod + "objEmployee: [" + objUser.toString() + "]");
				return objUser;

			} catch (Exception e) {// not sure what exception hibernate throws but not SQLException
				objLogger.error(sMethod + "Exception adding User record with username: [" + objUserDTO.getUsername()
						+ "] Exception: [" + e.toString() + "] [" + e.getMessage() + "]");
				throw new DatabaseException(csMsgDB_ErrorGettingEmployees);
			}


		}else {
			objLogger.warn(sMethod + "objUserDTO to update user is not valid: [" + objUserDTO.toString() + "]");
			throw new BadParameterException(csMsgBadParamEditUser);

		}
	}
	
	//
	//###
	public boolean deleteUserByUsername(String sUsername) throws DatabaseException {
		String sMethod = "\n\t deleteUserByUsername(): ";
		objLogger.trace(sMethod + "Entered");
		boolean isDeleted = false;

		try {
			isDeleted = objUserDAO.deleteRecord(sUsername);
			objLogger.debug(sMethod + "User with username: [" + sUsername + "] isDeleted: [" + isDeleted + "]");
			return isDeleted;

		} catch (Exception e) {// not sure what exception hibernate throws but not SQLException
			objLogger.error(sMethod + "Exception getting all User records from the database: Exception: ["
					+ e.toString() + "] [" + e.getMessage() + "]");
			throw new DatabaseException(csMsgDB_ErrorDeletingAnEmployee);
		}
	}

	

	////////////////////// Utility Methods for this Class /////////////////////////////////////////

	//
	// ### Cannot update UserId or the username
	//will not update the record id, the company username, or the company email.
	//also password should be updated through separate process where old pwd and new pwd are entered
	public boolean isValidUserDTOEditAttributes(UserDTO objUserDTO) {
		String sMethod = "\n\t isValidUserDTOEditAttributes(): ";
		objLogger.trace(sMethod + "Entered");
		boolean isValid = false;
		
		
		String sFirstName = objUserDTO.getFirstName();
		String sLastName = objUserDTO.getLastName();
		String sUserRoleName = objUserDTO.getUserRoleName();
		
		boolean bFirstNameIsAlpha = Validate.isAlpha(sFirstName);
		boolean bLastNameIsAlpha = Validate.isAlphaPlusLastname(sLastName);
		boolean bUserRoleNameIsValid = Validate.isValidValueInArray(sUserRoleName, csUserRoles);
		
		if (bFirstNameIsAlpha && bLastNameIsAlpha && bUserRoleNameIsValid) {
			isValid=true;
		}
		else {
			
			objLogger.warn(sMethod + "One or more add User Parameters did not pass validation.:");
			objLogger.warn(sMethod + "\t first name: [" + sFirstName + "] is valid: [" + bFirstNameIsAlpha + "]");
			objLogger.warn(sMethod + "\t last name: [" + sLastName + "] is valid: [" + bLastNameIsAlpha + "]");
			objLogger.warn(sMethod + "\t user role name: [" + sUserRoleName + "] is valid: [" + bUserRoleNameIsValid + "]");
		}
		
		return isValid;
	}
	
	//
	public boolean isValidUserDTO(UserDTO objUserDTO) {
		String sMethod = "\n\t isValidUserDTO(): ";
		objLogger.trace(sMethod + "Entered");
		boolean isValid = false;

		String sUsername = objUserDTO.getUsername();
		String sPassword = objUserDTO.getPassword();
		String sEmail = objUserDTO.getEmail();

		isValid = isValidUserDTOEditAttributes(objUserDTO);
		
		boolean bUsernameIsAlphaNumeric = Validate.isAlphaNumeric(sUsername) && sUsername.length() == ciUsernameLength;
		boolean bPasswordIsInFormat = Validate.isPasswordFormat(sPassword, ciUserMinPassword, ciUserMaxPassword);
		boolean bEmailIsInFormat = Validate.isValidEmailAddress(sEmail);

		if (bUsernameIsAlphaNumeric &&  bPasswordIsInFormat && bEmailIsInFormat && isValid) {
			isValid = true;
		} else {
			objLogger.warn(sMethod + "One or more add User Parameters did not pass validation.:");
			objLogger.warn(sMethod + "\t username: [" + sUsername + "] is valid: [" + bUsernameIsAlphaNumeric + "]");
			objLogger.warn(sMethod + "\t password correct format: [" + bPasswordIsInFormat + "]");
			objLogger.warn(sMethod + "\t email: [" + sEmail + "] correct format: [" + bEmailIsInFormat + "]");
		}
		return isValid;
	}


}
