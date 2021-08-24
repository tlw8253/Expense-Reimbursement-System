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
import com.tlw8253.dto.ReimbursementDTO;
import com.tlw8253.dto.UserDTO;
import com.tlw8253.exception.*;
import com.tlw8253.model.Reimbursement;
import com.tlw8253.model.User;
import com.tlw8253.util.Validate;

public class ERSReimbService implements Constants {
	private Logger objLogger = LoggerFactory.getLogger(ERSReimbService.class);
//	private GenericDAO<User> objUserDAO;
	private GenericDAO<Reimbursement> objReimbursementDAO;

	public ERSReimbService() {
		this.objReimbursementDAO = new ReimbursementDAOImpl();
	}

	public ERSReimbService getMockReimbursementDAO(GenericDAO<Reimbursement> objMockReimbursementDAO) {
		this.objReimbursementDAO = objMockReimbursementDAO;
		return this;
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////////


	//
	// ###	20210820	completed
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
	// ###	20210820	completed
	public Reimbursement getReimbursementById(String sReimbursementId) throws DatabaseException, BadParameterException {
		String sMethod = "\n\t getReimbursementById(): ";
		objLogger.trace(sMethod + "Entered");

		if (Validate.isInt(sReimbursementId) && Integer.parseInt(sReimbursementId) > 0) {
			int iReimbursementId = Integer.parseInt(sReimbursementId);
			try {
				Reimbursement objReimbursement = objReimbursementDAO.getByRecordId(iReimbursementId);
				objLogger.debug(sMethod + "objUser: [" + objReimbursement.toString() + "]");
				return objReimbursement;

			} catch (Exception e) {// not sure what exception hibernate throws but not SQLException
				objLogger.error(sMethod + "Exception getting all User records from the database: Exception: ["
						+ e.toString() + "] [" + e.getMessage() + "]");
				throw new DatabaseException(csMsgDB_ErrorGettingReimbursementById);
			}
		}else {
			objLogger.debug(sMethod + "Invalid Reimbursement id received: [" + sReimbursementId + "]");
			throw new BadParameterException(csMsgBadParamGetReimbursementById);			
		}
	}
	
	//
	// ###	20210821	completed
	public List<Reimbursement> getUsersByAuthorUsername(String sAuthorUsername) throws DatabaseException, BadParameterException {
		String sMethod = "\n\t getUsersByAuthorName(): ";
		objLogger.trace(sMethod + "Entered");

		if (Validate.isAlphaNumeric(sAuthorUsername) && sAuthorUsername.length() == ciUsernameLength) {			
			try {
				List<Reimbursement> lstReimbursement = objReimbursementDAO.getListByRecordIdentifer(ciReimbRecByIdentifierAuthor, sAuthorUsername);
				objLogger.debug(sMethod + "objReimbursement: [" + lstReimbursement.toString() + "]");
				return lstReimbursement;

			} catch (Exception e) {// not sure what exception hibernate throws but not SQLException
				objLogger.error(sMethod + "Exception getting Reimbursement record by Author username:[" + sAuthorUsername+ "] Exception: ["
						+ e.toString() + "] [" + e.getMessage() + "]");
				throw new DatabaseException(csMsgDB_ErrorGettingReimbursementAuthor);
			}
		}else {
			objLogger.debug(sMethod + "Invalid author name received: [" + sAuthorUsername + "]");
			throw new BadParameterException(csMsgBadParamGetUserByUsername);			
		}
	}

	//
	// ###	20210820 completed
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
	//###
	public Reimbursement updateReimbursementByAuthor(ReimbursementDTO objReimbursementDTO) throws DatabaseException, BadParameterException {
		String sMethod = "\n\t updateReimbursementByAuthor(): ";
		objLogger.trace(sMethod + "Entered");
		
		objLogger.debug(sMethod + "objReimbursementDTO: [" + objReimbursementDTO.toString() + "]");
		
		int iReimbId = objReimbursementDTO.getReimbIdAsInt();
		try {
			objLogger.debug(sMethod + "get objReimbursementDBRecord by id: [" + iReimbId + "]");
			Reimbursement objReimbursementDBRecord = objReimbursementDAO.getByRecordId(iReimbId);
			objLogger.debug(sMethod + "received: objReimbursementDBRecord: [" + objReimbursementDBRecord.toString() + "]");
			
			//keep the record id
			objReimbursementDTO.setReimbId(iReimbId);
			
			//csReimbTblReimbAmount can be changed by the author
			
			objLogger.debug(sMethod + "setting timestamps in objReimbursementDTO.");
			//keep the original csReimbTblReimbSubmitted timestamp
			objReimbursementDTO.setReimbSubmitted(objReimbursementDBRecord.getReimbSubmitted());
			//keep any csReimbTblReimbResolved timestamp
			objReimbursementDTO.setReimbResolved(objReimbursementDBRecord.getReimbResolved());
			
			//csReimbTblReimbDescription can be changed by the author
			//csReimbTblReimbReceipt can be changed by the author
			
			objLogger.debug(sMethod + "setting ids and types in objReimbursementDTO.");
			//keep the csReimbTblReimbAuthorId, author cannot be changed
			objReimbursementDTO.setReimbAuthorId(objReimbursementDBRecord.getReimbAuthor().getId());

			//keep the csReimbTblReimbResolverId, if any resolver cannot be changed by author
			objLogger.debug(sMethod + "getting ReimbResolver object from DB record.");
			if (objReimbursementDBRecord.getReimbResolver() != null) {
				int iReimbResolverId = objReimbursementDBRecord.getReimbResolver().getId();
				objLogger.debug(sMethod + "iReimbResolverId from DB record: [" + iReimbResolverId + "]");
				objReimbursementDTO.setReimbResolverId(iReimbResolverId);
			}else {
				objLogger.debug(sMethod + "No ReimbResolver record received setting Id to 0.");
				objReimbursementDTO.setReimbResolverId(0);
			}
			
			//keep the csReimbStatusTblReimbStatusId, cannot be changed by author
			objReimbursementDTO.setReimbStatus(objReimbursementDBRecord.getReimbStatus().getReimbStatus());
			//csReimbTblReimbTypeId can be changed by the author
						
			objLogger.debug(sMethod + "calling isValidAuthorUpdateReimbDTO to validate the DTO.");
			//validate just the fields the user can update
			if(isValidAuthorUpdateReimbDTO(objReimbursementDTO)) {
				
				objLogger.debug(sMethod + "Validated objReimbursementDTO: [" + objReimbursementDTO.toString() + "]");
				double dReimbAmount = Double.parseDouble(objReimbursementDTO.getReimbAmount());
				objLogger.debug(sMethod + "Set reimbAmount as double in objReimbursementDTO: [" + dReimbAmount + "]");
				objReimbursementDTO.setReimbAmount(dReimbAmount);			
				
				Reimbursement objUpdatedReimbursement =  objReimbursementDAO.editRecord(objReimbursementDTO);
				objLogger.debug(sMethod + "objUpdatedReimbursement: [" + objUpdatedReimbursement.toString() + "]");
				return objUpdatedReimbursement;
				
			}else {
				objLogger.warn(sMethod + "objReimbursementDTO is not valid for updates: [" + objReimbursementDTO.toString() + "]");
				throw new BadParameterException(csMsgBadParamUpdateReimb);
			}		
			
		} catch (Exception e) {// not sure what exception hibernate throws but not SQLException
			objLogger
					.error(sMethod + "Exception updating Reimbursement record: [" + objReimbursementDTO.toString()
							+ "] \n\t Exception: [" + e.toString() + "] \n\t[" + e.getMessage() + "]");
			throw new DatabaseException(csMsgDB_ErrorUpdatingReimbursement);
		}
	}


	//
	//###
	public Reimbursement updateReimbursementByResolver(ReimbursementDTO objReimbursementDTO) throws DatabaseException, BadParameterException {
		String sMethod = "\n\t updateReimbursementByResolver(): ";
		objLogger.trace(sMethod + "Entered");


		objLogger.debug(sMethod + "objReimbursementDTO: [" + objReimbursementDTO.toString() + "]");

		int iReimbId = objReimbursementDTO.getReimbIdAsInt();	//get existing record id
		
		try {
			objLogger.debug(sMethod + "get objReimbursementDBRecord by id: [" + iReimbId + "]");
			Reimbursement objReimbursementDBRecord = objReimbursementDAO.getByRecordId(iReimbId);
			objLogger.debug(sMethod + "received: objReimbursementDBRecord: [" + objReimbursementDBRecord.toString() + "]");
			
			//keep the record id
			objReimbursementDTO.setReimbId(iReimbId);
			
			//csReimbTblReimbAmount cannot be changed by the resolver
			objLogger.debug(sMethod + "setting reimbamount in objReimbursementDTO.");
			objReimbursementDTO.setReimbAmount(objReimbursementDBRecord.getReimbAmount());
			
			objLogger.debug(sMethod + "setting submitted timestamp in objReimbursementDTO.");
			//keep the original csReimbTblReimbSubmitted timestamp
			objReimbursementDTO.setReimbSubmitted(objReimbursementDBRecord.getReimbSubmitted());
			
			objLogger.debug(sMethod + "setting new resolved timestamp in objReimbursementDTO.");
			//set current csReimbTblReimbResolved timestamp
			Timestamp objResolverTimestamp = Timestamp.valueOf(LocalDateTime.now());
			objReimbursementDTO.setReimbResolved(objResolverTimestamp);
			
			objLogger.debug(sMethod + "setting reimb description in objReimbursementDTO.");
			//csReimbTblReimbDescription cannot be changed by the resolver
			objReimbursementDTO.setReimbDescription(objReimbursementDBRecord.getReimbDescription());
			
			objLogger.debug(sMethod + "setting reimb receipt in objReimbursementDTO.");
			//csReimbTblReimbReceipt cannot be changed by the resolver
			objReimbursementDTO.setReimbReceipt(objReimbursementDBRecord.getReimbReceipt());
			
			objLogger.debug(sMethod + "setting author id objReimbursementDTO.");
			//keep the csReimbTblReimbAuthorId, author cannot be changed
			objReimbursementDTO.setReimbAuthorId(objReimbursementDBRecord.getReimbAuthor().getId());

			//the csReimbTblReimbResolverId should be passed in by the caller
			//need to convert to integer after dto validation
			
			//the csReimbStatusTblReimbStatusId can be changed by the resolver
			
			//csReimbTblReimbTypeId cannot be changed by the resolver
			objReimbursementDTO.setReimbTypeId(objReimbursementDBRecord.getReimbType().getReimbTypeId());
						
			objLogger.debug(sMethod + "calling isValidAuthorUpdateReimbDTO to validate the DTO.");
			//validate just the fields the user can update
			if(isValidResolverUpdateReimbDTO(objReimbursementDTO)) {
				
				objLogger.debug(sMethod + "Validated objReimbursementDTO: [" + objReimbursementDTO.toString() + "]");
				
				String sResolverId = objReimbursementDTO.getReimbResolverId();
				objLogger.debug(sMethod + "get sResolverId from DTO: [" + sResolverId + "]");
				
				int iResolverId = Integer.parseInt(objReimbursementDTO.getReimbResolverId());
				objLogger.debug(sMethod + "Set reimbResolverId as int in objReimbursementDTO: [" + iResolverId + "]");
				objReimbursementDTO.setReimbResolverId(iResolverId);				
				
				objLogger.debug(sMethod + "updating the Reimbursement through the DAO.editRecord.");
				Reimbursement objUpdatedReimbursement =  objReimbursementDAO.editRecord(objReimbursementDTO);
				objLogger.debug(sMethod + "objUpdatedReimbursement: [" + objUpdatedReimbursement.toString() + "]");
				return objUpdatedReimbursement;
				
			}else {
				objLogger.warn(sMethod + "objReimbursementDTO is not valid for updates: [" + objReimbursementDTO.toString() + "]");
				throw new BadParameterException(csMsgBadParamUpdateReimb);
			}		
			
		} catch (Exception e) {// not sure what exception hibernate throws but not SQLException
			objLogger
					.error(sMethod + "Exception updating Reimbursement record: [" + objReimbursementDTO.toString()
							+ "] \n\t Exception: [" + e.toString() + "] \n\t[" + e.getMessage() + "]");
			throw new DatabaseException(csMsgDB_ErrorUpdatingReimbursement);
		}
	}

	//////////////////////////////////////// Utility Methods for this Class
	////////////////////////////////////////

	//
	//###
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
			objLogger
					.warn(sMethod + "\t Reimbursement resolver id: [" + sResolverId + "] is valid: [" + bResolverId + "]");
			objLogger.warn(sMethod + "\t Reimbursement status: [" + bReimbStatus + "] is valid: ["
					+ bReimbStatus + "]");
		}		
		return bValid;
	}

	
	//
	//###
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

		if (bValidAuthorUpdateFields && bReimbAuthorId && bReimbResolverId && bReimbStatus	&& bReimbType) {
			objLogger.debug(sMethod + "Validated ReimbursementDTO: [" + objReimbDTO.toString() + "]");
			bValid = true;
		} else {
			objLogger.warn(sMethod + "One or more add Reimbursement Parameters did not pass validation.:");
			objLogger
					.warn(sMethod + "\t Reimbursement Author fields: is valid: [" + bValidAuthorUpdateFields + "]");
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
