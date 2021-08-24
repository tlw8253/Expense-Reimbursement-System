package com.tlw8253.controller;

import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.Handler;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tlw8253.app.Constants;
import com.tlw8253.dto.AddOrEditDTO;
import com.tlw8253.dto.AddReimbDTO;
import com.tlw8253.dto.ReimbursementDTO;
import com.tlw8253.model.Reimbursement;
import com.tlw8253.model.User;
import com.tlw8253.service.ERSReimbService;
import com.tlw8253.service.ERSUserService;

public class ERSReimbController implements Controller, Constants {
	private Logger objLogger = LoggerFactory.getLogger(ERSReimbController.class);
	private ERSReimbService objERSReimbService;
	private ERSUserService objERSUserService;
	private ReimbursementDTO objReimbursementDTO;
	private AddReimbDTO objAddReimbDTO;

	Map<String, String> mPathParmaMap;
	Map<String, List<String>> mQueryParmaMap;
	int imPathParmaMapSize = 0;
	int imQueryParmaMap = 0;
	boolean bmQueryParmaMapIsEmpty = true;

	public ERSReimbController() {
		this.objERSReimbService = new ERSReimbService();
		this.objReimbursementDTO = new ReimbursementDTO();
		this.objAddReimbDTO = new AddReimbDTO();
		this.objERSUserService = new ERSUserService();
	}

	//
	// ###
	private void setContextMaps(Context objCtx) {
		String sMethod = "setContextMaps(): ";
		objLogger.trace(sMethod + "Entered");

		mPathParmaMap = objCtx.pathParamMap();
		imPathParmaMapSize = mPathParmaMap.size();
		mQueryParmaMap = objCtx.queryParamMap();
		imQueryParmaMap = mQueryParmaMap.size();
		bmQueryParmaMapIsEmpty = mQueryParmaMap.isEmpty();

		logContextParameters(objCtx);
	}

	//
	// ###
	private void logContextParameters(Context objCtx) {
		String sMethod = "logContextParameters(): ";
		objLogger.trace(sMethod + "Entered");

		mPathParmaMap = objCtx.pathParamMap();
		objLogger.debug(sMethod + "Context path parameter map: [" + mPathParmaMap + "]");
		objLogger.debug(sMethod + "Context path parameter map size: [" + imPathParmaMapSize + "]");

		mQueryParmaMap = objCtx.queryParamMap();
		objLogger.debug(sMethod + "Context query parameter map: [" + mQueryParmaMap + "]");
		objLogger.debug(sMethod + "Context query parameter map size: [" + imQueryParmaMap + "] isEmpty: ["
				+ bmQueryParmaMapIsEmpty + "]");
	}

	//
	// ###
	private Handler postAddNewReimbursement = (objCtx) -> {
		String sMethod = "\n\t postAddNewReimbursement(): ";
		objLogger.trace(sMethod + "Entered");
		Reimbursement objReimbursement = null;

		String sParamUsername = "";

		setContextMaps(objCtx);

		// expect 1 path parameters with user id
		if (imPathParmaMapSize == 1) {

			sParamUsername = objCtx.pathParam(csParamUserName);
			objLogger.debug(sMethod + "Context path parameter user id: [" + sParamUsername + "]");

			if (sParamUsername.length() == ciUsernameLength) {

				objAddReimbDTO = objCtx.bodyAsClass(AddReimbDTO.class);
				objLogger.debug(sMethod + "objAddReimbDTO body as class: [" + objAddReimbDTO.toString() + "]");
				
				objReimbursementDTO.setReimbAmount(objAddReimbDTO.getReimbAmount());
				objReimbursementDTO.setReimbDescription(objAddReimbDTO.getReimbDescription());
				objReimbursementDTO.setReimbReceipt(objAddReimbDTO.getReimbReceipt());
				objReimbursementDTO.setReimbType(objAddReimbDTO.getReimbType());
				objReimbursementDTO.setReimbAuthorUsername(sParamUsername);
				
				User objAuthor = objERSUserService.getUsersByUsername(sParamUsername);
				objReimbursementDTO.setReimbAuthorId(Integer.toString(objAuthor.getId()));
				
				
				objLogger.debug(sMethod + "objReimbursementDTO: [" + objReimbursementDTO.toString() + "]");
				
				//dummy this for now
				byte[] b = { 15, 16, 17, 18 };
				SerialBlob sbBlob = null;
				try {
					sbBlob = new SerialBlob(b);
				} catch (SerialException e) {
					objLogger.error(sMethod + "SerialException: [" + e.getMessage() + "]");
				} catch (SQLException e) {
					objLogger.error(sMethod + "SQLException: [" + e.getMessage() + "]");
				}
				objReimbursementDTO.setReimbReceipt(sbBlob);
		
				
				objReimbursement = objERSReimbService.addNewReimbursement(objReimbursementDTO);

				objCtx.status(ciStatusCodeSuccess);
				//objCtx.json(objReimbursement);
				objCtx.json(objAddReimbDTO);

			} else {
				objCtx.status(ciStatusCodeErrorBadRequest);
				objCtx.json(csMsgBadParamPathParmNotRightParam);

			}

		} else {
			objLogger.debug(
					sMethod + csMsgBadParamPathParmNotRightNumber + "  Did not receive the expected number of 1.");
			objCtx.status(ciStatusCodeErrorBadRequest);
			objCtx.json(csMsgBadParamPathParmNotRightNumber);
		}

	};

	
	
	
	//
	// ###
	//
	// ###
	private Handler getReimbursementById = (objCtx) -> {
		String sMethod = "getReimbursementById(): ";
		boolean bContinue = true;
		objLogger.trace(sMethod + "Entered");

		Reimbursement objReimbursement = null;

		String sParamReimbId = "";

		setContextMaps(objCtx);

		// expect 1 path parameters with user id
		if (imPathParmaMapSize == 1) {

			sParamReimbId = objCtx.pathParam(csParamPathReimbId);
			objLogger.debug(sMethod + "Context path parameter reimb id: [" + sParamReimbId + "]");

			
				objReimbursement = objERSReimbService.getReimbursementById(sParamReimbId);

				objCtx.status(ciStatusCodeSuccess);
				//objCtx.json(objReimbursement);
				objCtx.json(objReimbursement);


		} else {
			objLogger.debug(
					sMethod + csMsgBadParamPathParmNotRightNumber + "  Did not receive the expected number of 1.");
			objCtx.status(ciStatusCodeErrorBadRequest);
			objCtx.json(csMsgBadParamPathParmNotRightNumber);
		}

	};

	@Override
	public void mapEndpoints(Javalin app) {

		//
		app.post("/ers_reimb_add/:" + csParamUserName, postAddNewReimbursement);
		app.get("/ers_reimb_id/:" + csParamPathReimbId, getReimbursementById);
	}

}
