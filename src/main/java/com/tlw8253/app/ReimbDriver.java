package com.tlw8253.app;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tlw8253.dto.ReimbursementDTO;
import com.tlw8253.model.Reimbursement;
import com.tlw8253.service.ERSReimbursementService;

/**
 * This is a driver used during development to test functionality as it is
 * built.
 * 
 * @author tlw8748253
 *
 */
public class ReimbDriver implements Constants {
	private final static Logger objLogger = LoggerFactory.getLogger(ReimbDriver.class);
	private static ERSReimbursementService objERSReimbursementService = new ERSReimbursementService();

	public static void main(String[] args) {
		String sMethod = "\n\t main(): ";
		objLogger.trace(sMethod + "Entered");

		testERSReimbursementService_ControlTestCases();

	}

	public static void testERSReimbursementService_ControlTestCases() {
		String sMethod = "\n\t testERSReimbursementService_ControlTestCases(): ";
		objLogger.trace(sMethod + "Entered");

		// testERSReimbursementService_getAllReimbursement();
		// testERSReimbursementService_getReimbursementById("1");
		// testERSReimbursementService_getUsersByAuthorUsername("tlw8253");
		// testERSReimbursementService_updateReimbursementByAuthor(1);
		testERSReimbursementService_updateReimbursementByResolver(1);

	}

	// ###
	public static void testERSReimbursementService_updateReimbursementByResolver(int iRecId) {
		String sMethod = "\n\t testERSReimbursementService_updateReimbursementByResolver(): ";
		objLogger.trace(sMethod + "Entered");

		// all Author fields will be preserved by the service layer

		ReimbursementDTO objReimbursementDTO = new ReimbursementDTO();
		objReimbursementDTO.setReimbResolverId("2");
		objReimbursementDTO.setReimbResolverUsername("smp8253");
		objReimbursementDTO.setReimbId(iRecId);
		objReimbursementDTO.setReimbType(csReimbType[ciReimbTypeTravel]);
		//service layer should set new timestamp for resolver on each update
		
		//set new reimb status
		objReimbursementDTO.setReimbStatus(csReimbStatus[ciReimbStatusDenied]);
		objLogger.debug(sMethod + "objReimbursementDTO: [" + objReimbursementDTO.toString() + "]");
		

		try {
			Reimbursement objReimbursement = objERSReimbursementService.updateReimbursementByResolver(objReimbursementDTO);
			objLogger.debug(sMethod + "objReimbursement: [" + objReimbursement.toString() + "]");
		} catch (Exception e) {
			objLogger.debug(sMethod + "Exception: [" + e.getMessage() + "]");
		}
	}

	// ###
	public static void testERSReimbursementService_updateReimbursementByAuthor(int iRecId) {
		String sMethod = "\n\t testERSReimbursementService_updateReimbursementByAuthor(): ";
		objLogger.trace(sMethod + "Entered");

		byte[] b = { 15, 16, 17, 18, 19, 20 };
		SerialBlob sbBlob = null;

		boolean bContinue = false;
		try {
			sbBlob = new SerialBlob(b);
			bContinue = true;
		} catch (SerialException e) {
			objLogger.error(sMethod + "SerialException: [" + e.getMessage() + "]");
		} catch (SQLException e) {
			objLogger.error(sMethod + "SQLException: [" + e.getMessage() + "]");
		}

		if (bContinue) {
			ReimbursementDTO objReimbursementDTO = new ReimbursementDTO();
			objReimbursementDTO.setReimbId(iRecId);
			objReimbursementDTO.setReimbAmount("1000.58");
			objReimbursementDTO.setReimbDescription("New descripton for edit.");
			objReimbursementDTO.setReimbReceipt(sbBlob);
			objReimbursementDTO.setReimbAuthorId("1");
			objReimbursementDTO.setReimbResolverId("");
			objReimbursementDTO.setReimbType(csReimbType[ciReimbTypeTravel]);
			objLogger.debug(sMethod + "objReimbursementDTO: [" + objReimbursementDTO.toString() + "]");

			try {
				Reimbursement objReimbursement = objERSReimbursementService
						.updateReimbursementByAuthor(objReimbursementDTO);
				objLogger.debug(sMethod + "objReimbursement: [" + objReimbursement.toString() + "]");
			} catch (Exception e) {
				objLogger.debug(sMethod + "Exception: [" + e.getMessage() + "]");
			}
		}
	}

	// ###
	public static void testERSReimbursementService_getUsersByAuthorUsername(String sUsername) {
		String sMethod = "\n\t testERSReimbursementService_getReimbursementById(): ";
		objLogger.trace(sMethod + "Entered");

		try {
			List<Reimbursement> lstReimbursement = objERSReimbursementService.getUsersByAuthorUsername(sUsername);
			objLogger.debug(sMethod + "objReimbursement: [" + lstReimbursement.toString() + "]");
		} catch (Exception e) {
			objLogger.debug(sMethod + "Exception: [" + e.getMessage() + "]");
		}
	}

	// ###
	public static void testERSReimbursementService_getReimbursementById(String sReimbId) {
		String sMethod = "\n\t testERSReimbursementService_getReimbursementById(): ";
		objLogger.trace(sMethod + "Entered");

		try {
			Reimbursement objReimbursement = objERSReimbursementService.getReimbursementById(sReimbId);
			objLogger.debug(sMethod + "objReimbursement: [" + objReimbursement.toString() + "]");
		} catch (Exception e) {
			objLogger.debug(sMethod + "Exception: [" + e.getMessage() + "]");
		}
	}

	// ###
	public static void testERSReimbursementService_getAllReimbursement() {
		String sMethod = "\n\t testERSReimbursementService_getAllReimbursement(): ";
		objLogger.trace(sMethod + "Entered");

		try {
			List<Reimbursement> lstReimbursement = objERSReimbursementService.getAllReimbursement();
			objLogger.debug(sMethod + "lstReimbursement: [" + lstReimbursement.toString() + "]");
		} catch (Exception e) {
			objLogger.debug(sMethod + "Exception: [" + e.getMessage() + "]");
		}
	}

}
