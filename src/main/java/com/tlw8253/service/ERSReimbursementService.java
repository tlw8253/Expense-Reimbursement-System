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
import com.tlw8253.dao.ReimbursementDAOImpl;
import com.tlw8253.dao.UserDAOImpl;
import com.tlw8253.dto.ReimbursementDTO;
import com.tlw8253.dto.UserDTO;
import com.tlw8253.exception.*;
import com.tlw8253.model.Reimbursement;
import com.tlw8253.model.User;
import com.tlw8253.util.Validate;

public class ERSReimbursementService implements Constants {
	private Logger objLogger = LoggerFactory.getLogger(ERSReimbursementService.class);
	private GenericDAO<User> objUserDAO;
	private GenericDAO<Reimbursement> objReimbursementDAO;

	public ERSReimbursementService() {
		this.objUserDAO = new UserDAOImpl();
		this.objReimbursementDAO = new ReimbursementDAOImpl();
	}

	public ERSReimbursementService getMockUserDAO(GenericDAO<User> objMockUserDAO) {
		this.objUserDAO = objMockUserDAO;
		return this;
	}

	public ERSReimbursementService getMockReimbursementDAO(GenericDAO<Reimbursement> objMockReimbursementDAO) {
		this.objReimbursementDAO = objMockReimbursementDAO;
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

		if (Validate.isAlphaNumeric(sUsername) && sUsername.length() == 7) {			
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
	// ###
	public Reimbursement addNewReimbursement(ReimbursementDTO objReimbursementDTO)
			throws DatabaseException, BadParameterException {
		String sMethod = "\n\t addNewReimbursement(): ";
		objLogger.trace(sMethod + "Entered");

		Timestamp objSubmitTimestamp = Timestamp.valueOf(LocalDateTime.now());
		objReimbursementDTO.setReimbSubmitted(objSubmitTimestamp); // set timestamp for new request for the submitter to
																	// now
		Timestamp objResolveTimestamp = new Timestamp(0);
		objReimbursementDTO.setReimbResolved(objResolveTimestamp); // set timestamp for new request for the resolver to
																	// 0
		objReimbursementDTO.setReimbStatus(csReimbStatus[ciReimbStatusPending]);
		objReimbursementDTO.setReimbResolverId("0"); // Cannot have a resolver when creating a new request.

		if (isValidReimbursementDTO(objReimbursementDTO)) {
			int iReimbAuthorId = Integer.parseInt(objReimbursementDTO.getReimbAuthorId());// was validated to be an int
			if (iReimbAuthorId > 0) {// must have a id greater than 0
				try {
					objReimbursementDTO.setReimbAuthorId(iReimbAuthorId); // set as int for the DAO
					double dReimbAmount = Double.parseDouble(objReimbursementDTO.getReimbAmount());
					objReimbursementDTO.setReimbAmount(dReimbAmount);

					Reimbursement objReimbursement = objReimbursementDAO.addRecord(objReimbursementDTO);
					return objReimbursement;
				} catch (Exception e) {// not sure what exception hibernate throws but not SQLException
					objLogger
							.error(sMethod + "Exception adding Reimbursement record: [" + objReimbursementDTO.toString()
									+ "] \n\t Exception: [" + e.toString() + "] \n\t[" + e.getMessage() + "]");
					throw new DatabaseException(csMsgDB_ErrorAddingReimbursement);
				}
			} else {
				objLogger.warn(sMethod + "Author Id cannot be zero iReimbAuthorId: [" + iReimbAuthorId + "]");
				throw new BadParameterException(csMsgBadParamAddReimb);
			}
		} else {
			objLogger.warn(sMethod + "objReimbursementDTO is not valid: [" + objReimbursementDTO.toString() + "]");
			throw new BadParameterException(csMsgBadParamAddReimb);
		}
	}

	//////////////////////////////////////// Utility Methods for this Class
	//////////////////////////////////////// //////////////////////////////////////////////////

	//
	// ### move the validation logic to public method so it
	// can be used by other methods here and by a driver.
	// Also include the debug message
	public boolean isValidUserDTO(UserDTO objUserDTO) {
		String sMethod = "\n\t isValidUserDTO(): ";
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
		// boolean bUserRoleNameIsValid = isValidUserRole(sUserRoleName);
		boolean bUserRoleNameIsValid = Validate.isValidValueInArray(sUserRoleName, csUserRoles);

		if (bUsernameIsAlphaNumeric && bFirstNameIsAlpha && bLastNameIsAlpha && bPasswordIsInFormat && bEmailIsInFormat
				&& bUserRoleNameIsValid) {
			isValid = true;
		} else {
			objLogger.warn(sMethod + "One or more add User Parameters did not pass validation.:");
			objLogger.warn(sMethod + "\t username: [" + sUsername + "] is valid: [" + bUsernameIsAlphaNumeric + "]");
			objLogger.warn(sMethod + "\t password correct format: [" + bPasswordIsInFormat + "]");
			objLogger.warn(sMethod + "\t first name: [" + sFirstName + "] is valid: [" + bFirstNameIsAlpha + "]");
			objLogger.warn(sMethod + "\t last name: [" + sLastName + "] is valid: [" + bLastNameIsAlpha + "]");
			objLogger.warn(sMethod + "\t email: [" + sEmail + "] correct format: [" + bEmailIsInFormat + "]");
			objLogger.warn(
					sMethod + "\t user role name: [" + sUserRoleName + "] is valid: [" + bUserRoleNameIsValid + "]");
		}
		return isValid;
	}

	//
	// ###
	public boolean isValidReimbursementDTO(ReimbursementDTO objReimbDTO) {
		String sMethod = "\n\t isValidReimbursementDTO(): ";
		objLogger.trace(sMethod + "Entered");
		boolean bValid = false;

		String sReimbAmount = objReimbDTO.getReimbAmount(); // validate as double and > 0
		String sReimbDescription = objReimbDTO.getReimbDescription(); // validate contains value > 10 characters
		SerialBlob sbReimbReceipt = objReimbDTO.getReimbReceipt(); // validate it has something
		String sReimbAuthorId = objReimbDTO.getReimbAuthorId(); // validate as integer and > 0
		String sReimbResolverId = objReimbDTO.getReimbResolverId(); // validate as integer and > 0
		String sReimbStatus = objReimbDTO.getReimbStatus(); // validate value is in constant array
		String sReimbType = objReimbDTO.getReimbType(); // validate value is in constant array

		boolean bReimbAmount = (Validate.isDouble(sReimbAmount) && Double.parseDouble(sReimbAmount) > 0);
		boolean bReimbDescription = (sReimbDescription.length() >= 10);
		boolean bReimbReceipt = false;
		try {
			bReimbReceipt = (sbReimbReceipt.length() > 0);
			bReimbReceipt = true;
		} catch (SerialException e) {
			objLogger.warn(sMethod + "SerialException: [" + e.getMessage() + "]");
		}
		boolean bReimbAuthorId = (Validate.isInt(sReimbAuthorId));
		boolean bReimbResolverId = (Validate.isInt(sReimbResolverId));
		boolean bReimbStatus = Validate.isValidValueInArray(sReimbStatus, csReimbStatus);
		boolean bReimbType = Validate.isValidValueInArray(sReimbType, csReimbType);

		if (bReimbAmount && bReimbDescription && bReimbReceipt && bReimbAuthorId && bReimbResolverId && bReimbStatus
				&& bReimbType) {
			objLogger.debug(sMethod + "Validated ReimbursementDTO: [" + objReimbDTO.toString() + "]");
			bValid = true;
		} else {
			objLogger.warn(sMethod + "One or more add Reimbursement Parameters did not pass validation.:");
			objLogger
					.warn(sMethod + "\t Reimbursement Amount: [" + sReimbAmount + "] is valid: [" + bReimbAmount + "]");
			objLogger.warn(sMethod + "\t Reimbursement Description: [" + sReimbDescription + "] is valid: ["
					+ bReimbDescription + "]");
			objLogger.warn(sMethod + "\t Reimbursement Receipt is valid: [" + bReimbReceipt + "]");
			objLogger.warn(
					sMethod + "\t Reimbursement AuthorId: [" + sReimbAuthorId + "] is valid: [" + bReimbAuthorId + "]");
			objLogger.warn(sMethod + "\t Reimbursement ResolverId: [" + sReimbResolverId + "] is valid: ["
					+ bReimbResolverId + "]");
			objLogger
					.warn(sMethod + "\t Reimbursement Status: [" + sReimbStatus + "] is valid: [" + bReimbStatus + "]");
			objLogger.warn(sMethod + "\t Reimbursement Type: [" + sReimbType + "] is valid: [" + bReimbType + "]");
		}

		return bValid;
	}

}
