package com.tlw8253.controller;

import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.Handler;

import java.util.List;
import java.util.Map;

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
				
				objLogger.debug(sMethod + "objReimbursementDTO: [" + objReimbursementDTO.toString() + "]");
				
				//objReimbursement = objERSReimbService.addNewReimbursement(objReimbursementDTO);

				objCtx.status(ciStatusCodeSuccess);
				//objCtx.json(objReimbursement);
				objCtx.json(objReimbursementDTO);

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
	private Handler getERSUserById = (objCtx) -> {
		String sMethod = "getERSUserById(): ";
		boolean bContinue = true;
		objLogger.trace(sMethod + "Entered");
		User objUser = null;

		String sParamUserId = "";

		setContextMaps(objCtx);

		// expect 1 path parameters with user id
		if (imPathParmaMapSize != 1) {
			// Check for body params before erroring here

			objLogger.debug(sMethod + csMsgBadParamPathParmNotRightNumber);
			objCtx.status(ciStatusCodeErrorBadRequest);
			objCtx.json(csMsgBadParamPathParmNotRightNumber);
			bContinue = false;
		} else {

			sParamUserId = objCtx.pathParam(csParamPathUserId);
			objLogger.debug(sMethod + "Context path parameter user id: [" + sParamUserId + "]");

		}

		if (bContinue) {
//			objUser = objERSUserService.getUsersById(sParamUserId);
			objLogger.debug(sMethod + "objEmployee: [" + objUser.toString() + "]");
		}

		objCtx.status(ciStatusCodeSuccess);
		objCtx.json(objUser);
	};

	@Override
	public void mapEndpoints(Javalin app) {

		//
		app.post("/ers_reimb_add/:" + csParamUserName, postAddNewReimbursement);
	}

}
