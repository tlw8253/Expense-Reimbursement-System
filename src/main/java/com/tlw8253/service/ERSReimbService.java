package com.tlw8253.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tlw8253.app.Constants;
import com.tlw8253.dao.GenericDAO;
import com.tlw8253.dao.ReimbursementDAOImpl;
import com.tlw8253.dto.ReimbursementDTO;
import com.tlw8253.dto.UserDTO;
import com.tlw8253.exception.*;
import com.tlw8253.model.Reimbursement;
import com.tlw8253.model.ReimbursementStatus;
import com.tlw8253.model.User;
import com.tlw8253.util.Validate;

public class ERSReimbService implements Constants {
	private Logger objLogger = LoggerFactory.getLogger(ERSReimbService.class);
//	private GenericDAO<User> objUserDAO;
	private GenericDAO<Reimbursement> objReimbursementDAO;
	private ERSUserService objERSUserService;
	private ERSAdminService objERSAdminService;

	public ERSReimbService() {
		this.objReimbursementDAO = new ReimbursementDAOImpl();
		this.objERSUserService = new ERSUserService();
		this.objERSAdminService = new ERSAdminService();
	}

	public ERSReimbService getMockReimbursementDAO(GenericDAO<Reimbursement> objMockReimbursementDAO) {
		this.objReimbursementDAO = objMockReimbursementDAO;
		return this;
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////////

	//
	// ###
	public List<Reimbursement> getReimbursementByStatus(String sReimbStatus)
			throws DatabaseException, BadParameterException {
		String sMethod = "\n\t getReimbursementByStatus(): ";
		objLogger.trace(sMethod + "Entered: sReimbStatus: [" + sReimbStatus + "]");

		if (sReimbStatus.equalsIgnoreCase("ALL")) {
			return (getAllReimbursement());
		}

		if (Validate.isValidValueInArray(sReimbStatus, csReimbStatus)) {
			ReimbursementDAOImpl objReimbDAOImpl = new ReimbursementDAOImpl();
			try {
				List<Reimbursement> lstReimbursement = objReimbDAOImpl.getAllRecordsByStatus(sReimbStatus);
				objLogger.debug(sMethod + "lstReimbursement: [" + lstReimbursement.toString() + "]");
				return lstReimbursement;

			} catch (Exception e) {// not sure what exception hibernate throws but not SQLException
				objLogger.error(sMethod + "Exception getting all Reimbursement records from the database: Exception: ["
						+ e.toString() + "] [" + e.getMessage() + "]");
				throw new DatabaseException(csMsgDB_ErrorGettingReimbursements);
			}
		} else {
			objLogger.debug(sMethod + "status is not in list sReimbStatus: [" + sReimbStatus + "]");
			throw new BadParameterException(csMsgBadParamReimbStatus);
		}
	}

	//
	// ### 20210820 completed
	public List<Reimbursement> getAllReimbursement() throws DatabaseException {
		String sMethod = "\n\t getAllReimbursement(): ";
		objLogger.trace(sMethod + "Entered");

		try {
			List<Reimbursement> lstReimbursement = objReimbursementDAO.getAllRecords();
			objLogger.debug(sMethod + "lstReimbursement: [" + lstReimbursement.toString() + "]");
			return lstReimbursement;

		} catch (Exception e) {// not sure what exception hibernate throws but not SQLException
			objLogger.error(sMethod + "Exception getting all Reimbursement records from the database: Exception: ["
					+ e.toString() + "] [" + e.getMessage() + "]");
			throw new DatabaseException(csMsgDB_ErrorGettingReimbursements);
		}
	}

	//
	// ### 20210820 completed
	public Reimbursement getReimbursementById(String sReimbursementId)
			throws DatabaseException, BadParameterException, RecordNotFoundException {
		String sMethod = "\n\t getReimbursementById(): ";
		objLogger.trace(sMethod + "Entered");

		if (Validate.isInt(sReimbursementId) && Integer.parseInt(sReimbursementId) > 0) {
			int iReimbursementId = Integer.parseInt(sReimbursementId);
			try {
				Reimbursement objReimbursement = objReimbursementDAO.getByRecordId(iReimbursementId);
				objLogger.debug(sMethod + "objUser: [" + objReimbursement.toString() + "]");

				/*
				 * if (objReimbursement == null) { objLogger.debug(sMethod +
				 * "record not found by sReimbursementId: [" + sReimbursementId + "]"); throw
				 * new RecordNotFoundException(csMsgEmployeeRecordNotFound); }
				 */
				return objReimbursement;

			} catch (Exception e) {// not sure what exception hibernate throws but not SQLException
				objLogger.error(sMethod + "Exception getting User record: [" + sReimbursementId
						+ "] from the database: Exception: [" + e.toString() + "] [" + e.getMessage() + "]");
				throw new DatabaseException(csMsgDB_ErrorGettingReimbursementById);
			}
		} else {
			objLogger.debug(sMethod + "Invalid Reimbursement id received: [" + sReimbursementId + "]");
			throw new BadParameterException(csMsgBadParamGetReimbursementById);
		}
	}

	//
	// ### 20210821 completed
	public List<Reimbursement> getUsersByAuthorUsername(String sAuthorUsername)
			throws DatabaseException, BadParameterException {
		String sMethod = "\n\t getUsersByAuthorName(): ";
		objLogger.trace(sMethod + "Entered");

		if (Validate.isAlphaNumeric(sAuthorUsername) && sAuthorUsername.length() == ciUsernameLength) {
			try {
				List<Reimbursement> lstReimbursement = objReimbursementDAO
						.getListByRecordIdentifer(ciReimbRecByIdentifierAuthor, sAuthorUsername);
				objLogger.debug(sMethod + "objReimbursement: [" + lstReimbursement.toString() + "]");
				return lstReimbursement;

			} catch (Exception e) {// not sure what exception hibernate throws but not SQLException
				objLogger.error(sMethod + "Exception getting Reimbursement record by Author username:["
						+ sAuthorUsername + "] Exception: [" + e.toString() + "] [" + e.getMessage() + "]");
				throw new DatabaseException(csMsgDB_ErrorGettingReimbursementAuthor);
			}
		} else {
			objLogger.debug(sMethod + "Invalid author name received: [" + sAuthorUsername + "]");
			throw new BadParameterException(csMsgBadParamGetUserByUsername);
		}
	}

	//
	// ### 20210820 completed
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

	//
	// ###
	public Reimbursement updateReimbursementByAuthor(ReimbursementDTO objReimbursementDTO)
			throws DatabaseException, BadParameterException {
		String sMethod = "\n\t updateReimbursementByAuthor(): ";
		objLogger.trace(sMethod + "Entered");

		objLogger.debug(sMethod + "objReimbursementDTO: [" + objReimbursementDTO.toString() + "]");

		int iReimbId = objReimbursementDTO.getReimbIdAsInt();
		try {
			objLogger.debug(sMethod + "get objReimbursementDBRecord by id: [" + iReimbId + "]");
			Reimbursement objReimbursementDBRecord = objReimbursementDAO.getByRecordId(iReimbId);
			objLogger.debug(
					sMethod + "received: objReimbursementDBRecord: [" + objReimbursementDBRecord.toString() + "]");

			// keep the record id
			objReimbursementDTO.setReimbId(iReimbId);

			// csReimbTblReimbAmount can be changed by the author

			objLogger.debug(sMethod + "setting timestamps in objReimbursementDTO.");
			// keep the original csReimbTblReimbSubmitted timestamp
			objReimbursementDTO.setReimbSubmitted(objReimbursementDBRecord.getReimbSubmitted());
			// keep any csReimbTblReimbResolved timestamp
			objReimbursementDTO.setReimbResolved(objReimbursementDBRecord.getReimbResolved());

			// csReimbTblReimbDescription can be changed by the author
			// csReimbTblReimbReceipt can be changed by the author

			objLogger.debug(sMethod + "setting ids and types in objReimbursementDTO.");
			// keep the csReimbTblReimbAuthorId, author cannot be changed
			objReimbursementDTO.setReimbAuthorId(objReimbursementDBRecord.getReimbAuthor().getUserId());

			// keep the csReimbTblReimbResolverId, if any resolver cannot be changed by
			// author
			objLogger.debug(sMethod + "getting ReimbResolver object from DB record.");
			if (objReimbursementDBRecord.getReimbResolver() != null) {
				int iReimbResolverId = objReimbursementDBRecord.getReimbResolver().getUserId();
				objLogger.debug(sMethod + "iReimbResolverId from DB record: [" + iReimbResolverId + "]");
				objReimbursementDTO.setReimbResolverId(iReimbResolverId);
			} else {
				objLogger.debug(sMethod + "No ReimbResolver record received setting Id to 0.");
				objReimbursementDTO.setReimbResolverId(0);
			}

			// keep the csReimbStatusTblReimbStatusId, cannot be changed by author
			objReimbursementDTO.setReimbStatus(objReimbursementDBRecord.getReimbStatus().getReimbStatus());
			// csReimbTblReimbTypeId can be changed by the author

			objLogger.debug(sMethod + "calling isValidAuthorUpdateReimbDTO to validate the DTO.");
			// validate just the fields the user can update
			if (isValidAuthorUpdateReimbDTO(objReimbursementDTO)) {

				objLogger.debug(sMethod + "Validated objReimbursementDTO: [" + objReimbursementDTO.toString() + "]");
				double dReimbAmount = Double.parseDouble(objReimbursementDTO.getReimbAmount());
				objLogger.debug(sMethod + "Set reimbAmount as double in objReimbursementDTO: [" + dReimbAmount + "]");
				objReimbursementDTO.setReimbAmount(dReimbAmount);

				objLogger.debug(sMethod + "sending to DAO to update: objReimbursementDTO: ["
						+ objReimbursementDTO.toString() + "]");

				Reimbursement objUpdatedReimbursement = objReimbursementDAO.editRecord(objReimbursementDTO);
				objLogger.debug(sMethod + "objUpdatedReimbursement: [" + objUpdatedReimbursement.toString() + "]");
				return objUpdatedReimbursement;

			} else {
				objLogger.warn(sMethod + "objReimbursementDTO is not valid for updates: ["
						+ objReimbursementDTO.toString() + "]");
				throw new BadParameterException(csMsgBadParamUpdateReimb);
			}

		} catch (Exception e) {// not sure what exception hibernate throws but not SQLException
			objLogger.error(sMethod + "Exception updating Reimbursement record: [" + objReimbursementDTO.toString()
					+ "] \n\t Exception: [" + e.toString() + "] \n\t[" + e.getMessage() + "]");
			throw new DatabaseException(csMsgDB_ErrorUpdatingReimbursement);
		}
	}

	//
	// ###
	public Reimbursement updateReimbursementByResolver(ReimbursementDTO objReimbursementDTO)
			throws DatabaseException, BadParameterException {
		String sMethod = "\n\t updateReimbursementByResolver(): ";
		objLogger.trace(sMethod + "Entered");

		objLogger
				.debug(sMethod + "received from caller: objReimbursementDTO: [" + objReimbursementDTO.toString() + "]");
		// get the five items that should be in the resolver DTO
		String sReimbId = objReimbursementDTO.getReimbId();
		String sReimbResolverUserName = objReimbursementDTO.getReimbResolverUsername();
		String sReimbResolverStatus = objReimbursementDTO.getReimbStatus();
		String sReimbResolverStatusMsg = objReimbursementDTO.getReimbResolverMessage();

		objLogger.debug(sMethod + "paresed values from: objReimbursementDTO: sReimbId: [" + sReimbId
				+ "] sReimbResolverUserName: [" + sReimbResolverUserName + "] sReimbResolverStatus: ["
				+ sReimbResolverStatus + "] sReimbResolverStatusMsg: [" + sReimbResolverStatusMsg + "]");

		if (Validate.isInt(sReimbId) && Integer.parseInt(sReimbId) > 0) {
			int iParseReimbId = Integer.parseInt(sReimbId);
			objReimbursementDTO.setReimbId(iParseReimbId);
		} else {
			objLogger.debug(sMethod + "bad input parameter from DTO did not parse to int or was zero sReimbId: ["
					+ sReimbId + "]");
			throw new BadParameterException(csMsgBadParamGetReimbursementById);
		}
		int iReimbId = objReimbursementDTO.getReimbIdAsInt(); // get existing record id

		objLogger.debug(sMethod + "reimbursement record id iReimbId: [" + iReimbId + "]");

		try {
			// first get the resolver user object
			objLogger.debug(sMethod + "getting resolver user object by sReimbResolverUserName: ["
					+ sReimbResolverUserName + "]");
			User objResolver = objERSUserService.getUsersByUsername(sReimbResolverUserName);
			objLogger.debug(sMethod + "user object objResolver: [" + objResolver.toString() + "]");

			// second get the reimbursement status object
			objLogger.debug(sMethod + "getting reimbursement status object by sReimbResolverStatus: ["
					+ sReimbResolverStatus + "]");
			ReimbursementStatus objReimbursementStatus = objERSAdminService
					.getReimbursementStatusByName(sReimbResolverStatus);
			objLogger.debug(sMethod + "reimbursement status object objReimbursementStatus: ["
					+ objReimbursementStatus.toString() + "]");

			// third get the Reimbursement record we are editing from the database
			objLogger.debug(sMethod + "get Reimbursement DB Record by id: [" + iReimbId + "]");
			Reimbursement objReimbursementDBRecord = objReimbursementDAO.getByRecordId(iReimbId);
			objLogger.debug(
					sMethod + "received: objReimbursementDBRecord: [" + objReimbursementDBRecord.toString() + "]");

			// now setup the DTO for the DAO update
			objReimbursementDTO.setReimbId(iReimbId); // value used to get db record

			objReimbursementDTO.setReimbAmount(objReimbursementDBRecord.getReimbAmount()); // from db record
			objReimbursementDTO.setReimbAmount(Double.toString(objReimbursementDBRecord.getReimbAmount()));// also set
																											// as string

			objReimbursementDTO.setReimbSubmitted(objReimbursementDBRecord.getReimbSubmitted());// from db record
			objReimbursementDTO.setReimbSubmitted(objReimbursementDBRecord.getReimbSubmitted().toString());// also set
																											// as string

			Timestamp objSubmitTimestamp = Timestamp.valueOf(LocalDateTime.now());
			objReimbursementDTO.setReimbResolved(objSubmitTimestamp); // set now for being resolved
			objReimbursementDTO.setReimbResolved(objSubmitTimestamp.toString());// also set as string

			objReimbursementDTO.setReimbDescription(objReimbursementDBRecord.getReimbDescription());// from db record

			objReimbursementDTO.setReimbReceipt(objReimbursementDBRecord.getReimbReceipt()); // from db record
			objReimbursementDTO.setReimbReceipt(objReimbursementDBRecord.getReimbReceipt().toString());// also set as
																										// string

			objReimbursementDTO.setReimbResolverMessage(sReimbResolverStatusMsg); // from the resolver's DTO

			objReimbursementDTO.setReimbAuthorUsername(objReimbursementDBRecord.getReimbAuthor().getUsername());// from
																												// db
																												// record
			objReimbursementDTO.setReimbAuthorId(objReimbursementDBRecord.getReimbAuthor().getUserId());// from db record
			objReimbursementDTO.setReimbAuthorId(Integer.toString(objReimbursementDBRecord.getReimbAuthor().getUserId()));// also
																														// as
																														// string

			objReimbursementDTO.setReimbResolverUsername(objResolver.getUsername()); // from resolver's user object
			objReimbursementDTO.setReimbResolverId(objResolver.getUserId()); // from resolver's user object
			objReimbursementDTO.setReimbResolverId(Integer.toString(objResolver.getUserId())); // also as string

			objReimbursementDTO.setReimbStatus(objReimbursementStatus.getReimbStatus()); // from status object
			objReimbursementDTO.setReimbStatusId(objReimbursementStatus.getReimbStatusId()); // from status object

			objReimbursementDTO.setReimbType(objReimbursementDBRecord.getReimbType().getReimbType());// from db record
			objReimbursementDTO.setReimbTypeId(objReimbursementDBRecord.getReimbType().getReimbTypeId());// from db
																											// record

			objLogger.debug(sMethod + "final DTO object before calling DAO objReimbursementDTO: ["
					+ objReimbursementDTO.toString() + "]");

			// now validate the DTO
			if (isValidResolverUpdateReimbDTO(objReimbursementDTO)) {
				objLogger.debug(sMethod + "Validated objReimbursementDTO: [" + objReimbursementDTO.toString() + "]");
				objLogger.debug(sMethod + "updating the Reimbursement through the DAO.editRecord.");

				// Finally call the DAO to update the record
				Reimbursement objUpdatedReimbursement = objReimbursementDAO.editRecord(objReimbursementDTO);
				objLogger.debug(sMethod + "objUpdatedReimbursement: [" + objUpdatedReimbursement.toString() + "]");
				return objUpdatedReimbursement;

			} else {
				objLogger.warn(sMethod + "objReimbursementDTO is not valid for updates: ["
						+ objReimbursementDTO.toString() + "]");
				throw new BadParameterException(csMsgBadParamUpdateReimb);
			}

		} catch (Exception e) {// not sure what exception hibernate throws but not SQLException
			objLogger.error(sMethod + "Exception updating Reimbursement record: [" + objReimbursementDTO.toString()
					+ "] \n\t Exception: [" + e.toString() + "] \n\t[" + e.getMessage() + "]");
			throw new DatabaseException(csMsgDB_ErrorUpdatingReimbursement);
		}

		/**/
	}

	//
	// ###
	public Reimbursement getUserReimbursementByReimbId(String sReimbId, String sUsername)
			throws DatabaseException, BadParameterException {
		String sMethod = "\n\t getUserReimbursementByReimbId(): ";
		objLogger.trace(sMethod + "Entered: sReimbId: [" + sReimbId + "] sUsername: [ " + sUsername + "]");

		if (Validate.isAlphaNumeric(sUsername) && sUsername.length() == ciUsernameLength && Validate.isInt(sReimbId)) {
			try {
				Reimbursement objReimbursement = objReimbursementDAO.getByRecordId(Integer.parseInt(sReimbId));
				objLogger.debug(sMethod + "objReimbursement: [" + objReimbursement.toString() + "]");

				String sReimbUsername = objReimbursement.getReimbAuthor().getUsername();

				if (sReimbUsername.equalsIgnoreCase(sUsername)) {
					objLogger.debug(sMethod + "record being return DOES belongs to sUsername: [" + sUsername + "]");
					
					if (objReimbursement.getReimbResolver() == null) {
						objReimbursement.setReimbResolver(new User());
					}
					
					return objReimbursement;
				} else {
					objLogger.debug(sMethod + "record found DOES NOT belongs to sUsername: [" + sUsername + "]");
					throw new BadParameterException(csMsgBadParamGetUserReimbByIdDoesNotBelong);
				}

			} catch (Exception e) {// not sure what exception hibernate throws but not SQLException
				objLogger.error(
						sMethod + "Exception getting Reimbursement record sReimbId:[" + sReimbId + "] for sUsername: ["
								+ sUsername + "] Exception: [" + e.toString() + "] [" + e.getMessage() + "]");
				throw new DatabaseException(csMsgDB_ErrorGettingReimbursementAuthor);
			}
		} else {
			objLogger.debug(sMethod + "Invalid parameters received sReimbId: [" + sReimbId + "] sUsername: ["
					+ sUsername + "]");
			throw new BadParameterException(csMsgBadParamGetUserReimbursementById);
		}
	}

	//////////////////////////////////////// Utility Methods for this Class
	////////////////////////////////////////

	//
	// ###
	public boolean isValidResolverUpdateReimbDTO(ReimbursementDTO objReimbDTO) {
		String sMethod = "\n\t isValidResolverUpdateReimbDTO(): ";
		objLogger.trace(sMethod + "Entered");
		boolean bValid = false;

		String sResolverId = objReimbDTO.getReimbResolverId(); // validate as double and > 0
		String sReimbStatus = objReimbDTO.getReimbStatus(); // validate contains value > 10 characters

		boolean bResolverId = (Validate.isInt(sResolverId) && Integer.parseInt(sResolverId) > 0);
		boolean bReimbStatus = Validate.isValidValueInArray(sReimbStatus, csReimbStatus);

		if (bResolverId && bReimbStatus) {
			objLogger.debug(sMethod + "Validated Resolver ReimbursementDTO: [" + objReimbDTO.toString() + "]");
			bValid = true;
		} else {
			objLogger.warn(sMethod + "One or more resolver update Reimbursement Parameters did not pass validation.:");
			objLogger.warn(
					sMethod + "\t Reimbursement resolver id: [" + sResolverId + "] is valid: [" + bResolverId + "]");
			objLogger
					.warn(sMethod + "\t Reimbursement status: [" + bReimbStatus + "] is valid: [" + bReimbStatus + "]");
		}
		return bValid;
	}

	//
	// ###
	public boolean isValidAuthorUpdateReimbDTO(ReimbursementDTO objReimbDTO) {
		String sMethod = "\n\t isValidAuthorUpdateReimbDTO(): ";
		objLogger.trace(sMethod + "Entered");
		boolean bValid = false;

		String sReimbAmount = objReimbDTO.getReimbAmount(); // validate as double and > 0
		String sReimbDescription = objReimbDTO.getReimbDescription(); // validate contains value > 10 characters
		SerialBlob sbReimbReceipt = objReimbDTO.getReimbReceipt(); // validate it has something

		boolean bReimbAmount = (Validate.isDouble(sReimbAmount) && Double.parseDouble(sReimbAmount) > 0);
		boolean bReimbDescription = (sReimbDescription.length() >= 10);
		boolean bReimbReceipt = false;
		try {
			bReimbReceipt = (sbReimbReceipt.length() > 0);
			bReimbReceipt = true;
		} catch (SerialException e) {
			objLogger.warn(sMethod + "SerialException: [" + e.getMessage() + "]");
		}

		if (bReimbAmount && bReimbDescription && bReimbReceipt) {
			objLogger.debug(sMethod + "Validated Author ReimbursementDTO: [" + objReimbDTO.toString() + "]");
			bValid = true;
		} else {
			objLogger.warn(sMethod + "One or more update author Reimbursement Parameters did not pass validation.:");
			objLogger
					.warn(sMethod + "\t Reimbursement Amount: [" + sReimbAmount + "] is valid: [" + bReimbAmount + "]");
			objLogger.warn(sMethod + "\t Reimbursement Description: [" + sReimbDescription + "] is valid: ["
					+ bReimbDescription + "]");
			objLogger.warn(sMethod + "\t Reimbursement Receipt is valid: [" + bReimbReceipt + "]");
		}
		return bValid;
	}

	//
	// ###
	public boolean isValidReimbursementDTO(ReimbursementDTO objReimbDTO) {
		String sMethod = "\n\t isValidReimbursementDTO(): ";
		objLogger.trace(sMethod + "Entered");
		boolean bValid = false;

		boolean bValidAuthorUpdateFields = isValidAuthorUpdateReimbDTO(objReimbDTO);

		String sReimbAuthorId = objReimbDTO.getReimbAuthorId(); // validate as integer and > 0
		String sReimbResolverId = objReimbDTO.getReimbResolverId(); // validate as integer and > 0
		String sReimbStatus = objReimbDTO.getReimbStatus(); // validate value is in constant array
		String sReimbType = objReimbDTO.getReimbType(); // validate value is in constant array

		boolean bReimbAuthorId = (Validate.isInt(sReimbAuthorId));
		boolean bReimbResolverId = (Validate.isInt(sReimbResolverId));
		boolean bReimbStatus = Validate.isValidValueInArray(sReimbStatus, csReimbStatus);
		boolean bReimbType = Validate.isValidValueInArray(sReimbType, csReimbType);

		if (bValidAuthorUpdateFields && bReimbAuthorId && bReimbResolverId && bReimbStatus && bReimbType) {
			objLogger.debug(sMethod + "Validated ReimbursementDTO: [" + objReimbDTO.toString() + "]");
			bValid = true;
		} else {
			objLogger.warn(sMethod + "One or more add Reimbursement Parameters did not pass validation.:");
			objLogger.warn(sMethod + "\t Reimbursement Author fields: is valid: [" + bValidAuthorUpdateFields + "]");
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


	//
	// ### 20210820 completed
	public List<Reimbursement> getFilteredUserReimbursement(String sUsername, String sStatus) throws DatabaseException, BadParameterException {
		String sMethod = "\n\t getFilteredUserReimbursement(): ";
		objLogger.trace(sMethod + "Entered: [" + sUsername + "],[" + sStatus + "]");
		boolean bFilterAll = false;
		boolean bValidFilter = false;

		if (sStatus.equalsIgnoreCase("ALL")) {
			bFilterAll = true;
			bValidFilter = true;
		}

		if (!bFilterAll) {
			if (Validate.isValidValueInArray(sStatus, csReimbStatus)) {
				bValidFilter = true;
			}
		}
		
		objLogger.debug(sMethod + "Filters: bFilterAll: [" + bFilterAll + "] bValidFilter: [" + bValidFilter +"]" );

		if (bValidFilter) {
			try {
				List<Reimbursement> lstAllReimbursement = objReimbursementDAO.getAllRecords();
				objLogger.debug(sMethod + "lstAllReimbursement: [" + lstAllReimbursement.toString() + "]");
				
				List<Reimbursement> lstAllUserReimbursement = new ArrayList<Reimbursement>();
				
				objLogger.debug(sMethod + "adding all records to return array for sUsername: [" + sUsername + "]");
				for (int iCtr=0; iCtr<lstAllReimbursement.size(); iCtr++) {
					if(lstAllReimbursement.get(iCtr).getReimbAuthor().getUsername().equalsIgnoreCase(sUsername)) {	
						Reimbursement objReimbursement = lstAllReimbursement.get(iCtr);
						
						if(objReimbursement.getReimbResolver() == null) {
							objReimbursement.setReimbResolver(new User());
						}
							
						
						lstAllUserReimbursement.add(objReimbursement);
						objLogger.debug(sMethod + "add to all list objReimbursement: [" + objReimbursement.toString() + "]");
					}
				}
				
				
				
				objLogger.debug(sMethod + "check for status filter: [" + !bFilterAll + "]"); 
				if (bFilterAll) {
					objLogger.debug(sMethod + "all user reimbursements added lstAllUserReimbursement: [" + lstAllUserReimbursement.toString() + "]");
					return lstAllUserReimbursement;
				}else {
					List<Reimbursement> lstFliteredReimbursement = new ArrayList<Reimbursement>();
					
					objLogger.debug(sMethod + "adding filtered records to return array for sStatus: [" + sStatus + "]");
					for (int iCtr=0; iCtr<lstAllUserReimbursement.size(); iCtr++) {
						if(lstAllUserReimbursement.get(iCtr).getReimbStatus().getReimbStatus().equalsIgnoreCase(sStatus)) {
							Reimbursement objFilteredReimbursement = lstAllUserReimbursement.get(iCtr);
							lstFliteredReimbursement.add(objFilteredReimbursement);
							objLogger.debug(sMethod + "add to filtered list objFilteredReimbursement: [" + objFilteredReimbursement.toString() + "]");
						}
					}
					objLogger.debug(sMethod + "filtered user reimbursements added lstFliteredReimbursement: [" + lstFliteredReimbursement.toString() + "]");
					return lstFliteredReimbursement;
				}				

			} catch (Exception e) {// not sure what exception hibernate throws but not SQLException
				objLogger.error(sMethod + "Exception getting all Reimbursement records from the database: Exception: ["
						+ e.toString() + "] [" + e.getMessage() + "]");
				throw new DatabaseException(csMsgDB_ErrorGettingReimbursements);
			}
		}else {
			objLogger.debug(sMethod + "Invalid filter parameter received: [" + sStatus + "]");
			throw new BadParameterException(csMsgBadParamReimbStatus);
		}
		
	}

}
