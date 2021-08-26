package com.tlw8253.controller;

import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.Handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tlw8253.app.Constants;
import com.tlw8253.model.Reimbursement;
import com.tlw8253.model.User;
import com.tlw8253.service.ERSReimbService;
import com.tlw8253.service.ERSUserService;

public class ERSUserController implements Controller, Constants {
	private Logger objLogger = LoggerFactory.getLogger(ERSUserController.class);
	private ERSUserService objERSUserService;
	private ERSReimbService objERSReimbService;
	

	Map<String, String> mPathParmaMap;
	Map<String, List<String>> mQueryParmaMap;
	int imPathParmaMapSize = 0;
	int imQueryParmaMap = 0;
	boolean bmQueryParmaMapIsEmpty = true;

	public ERSUserController() {		
		this.objERSUserService = new ERSUserService();
		this.objERSReimbService = new ERSReimbService();
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
	private Handler getERSUserRole = (objCtx) -> {
		String sMethod = "getERSUserRole(): ";
		boolean bContinue = true;
		objLogger.trace(sMethod + "Entered");
		User objUser = null;
		
		String sParamUserId = "";

		setContextMaps(objCtx);
		
		//expect 1 path parameters with user id
		if (imPathParmaMapSize != 1) {			
			//Check for body params before erroring here		
			
			objLogger.debug(sMethod + csMsgBadParamPathParmNotRightNumber);
			objCtx.status(ciStatusCodeErrorBadRequest);
			objCtx.json(csMsgBadParamPathParmNotRightNumber);
			bContinue = false;
		} else {
			sParamUserId = objCtx.queryParam(csParamPathUserId);
			objLogger.debug(sMethod + "Context query parameter user id: [" + sParamUserId + "]");
		}

		if(bContinue) {
		}
		
		objCtx.status(ciStatusCodeSuccess);
		objCtx.json(objUser);
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
		
		//expect 1 path parameters with user id
		if (imPathParmaMapSize != 1) {			
			//Check for body params before erroring here		
			
			objLogger.debug(sMethod + csMsgBadParamPathParmNotRightNumber);
			objCtx.status(ciStatusCodeErrorBadRequest);
			objCtx.json(csMsgBadParamPathParmNotRightNumber);
			bContinue = false;
		} else {
			
			sParamUserId = objCtx.pathParam(csParamPathUserId);
			objLogger.debug(sMethod + "Context path parameter user id: [" + sParamUserId + "]");
			
		}

		if(bContinue) {
			objUser = objERSUserService.getUsersById(sParamUserId);
			objLogger.debug(sMethod + "objEmployee: [" + objUser.toString() + "]");
		}
		
		objCtx.status(ciStatusCodeSuccess);
		objCtx.json(objUser);
	};


	//
	// ### 
	private Handler getUserReimbursementByReimbId = (objCtx) -> {
		String sMethod = "getUserReimbursementByReimbId(): ";
		boolean bContinue = true;
		objLogger.trace(sMethod + "Entered");
		Reimbursement objReimbursement = new Reimbursement();
		
		String sParamUsername = "";
		String sParamReimbId = "";

		setContextMaps(objCtx);
		
		//expect 1 path parameters with user id
		if (imPathParmaMapSize != 2) {			
			//Check for body params before erroring here		
			
			objLogger.debug(sMethod + csMsgBadParamPathParmNotRightNumber + " received: [" + imPathParmaMapSize + "] expected 2.");
			objCtx.status(ciStatusCodeErrorBadRequest);
			objCtx.json(csMsgBadParamPathParmNotRightNumber);
			bContinue = false;
			
		} else {
			
			sParamUsername = objCtx.pathParam(csParamUserName);
			objLogger.debug(sMethod + "Context path parameter user name: [" + sParamUsername + "]");

			sParamReimbId = objCtx.pathParam(csParamPathReimbId);
			objLogger.debug(sMethod + "Context path parameter reimbursement id: [" + sParamReimbId + "]");
			
			bContinue = true;
			
		}

		if(bContinue) {
			//objUser = objERSUserService.getUsersById(sParamUserId);
			//objLogger.debug(sMethod + "objEmployee: [" + objUser.toString() + "]");
			
			objReimbursement = objERSReimbService.getUserReimbursementByReimbId(sParamReimbId, sParamUsername);

			objCtx.status(ciStatusCodeSuccess);
			objCtx.json(objReimbursement);
		}
		
	};

	
	//
	// ### 
	private Handler getUserReimbursementByStatus = (objCtx) -> {
		String sMethod = "getUserReimbursementByStatus(): ";
		boolean bContinue = true;
		objLogger.trace(sMethod + "Entered");
		List <Reimbursement> lstReimbursement = new ArrayList();
		
		String sParamUsername = "";
		String sParamReimStatus = "";

		setContextMaps(objCtx);
		
		//expect 1 path parameters with user id
		if (imPathParmaMapSize != 2) {			
			//Check for body params before erroring here		
			
			objLogger.debug(sMethod + csMsgBadParamPathParmNotRightNumber + " received: [" + imPathParmaMapSize + "] expected 2.");
			objCtx.status(ciStatusCodeErrorBadRequest);
			objCtx.json(csMsgBadParamPathParmNotRightNumber);
			bContinue = false;
			
		} else {
			
			sParamUsername = objCtx.pathParam(csParamUserName);
			objLogger.debug(sMethod + "Context path parameter user name: [" + sParamUsername + "]");

			sParamReimStatus = objCtx.pathParam(csParamReimStatus);
			objLogger.debug(sMethod + "Context path parameter reimbursement status: [" + sParamReimStatus + "]");
			
			bContinue = true;
			
		}

		if(bContinue) {
			
			lstReimbursement = objERSReimbService.getFilteredUserReimbursement(sParamUsername, sParamReimStatus);
			objLogger.debug(sMethod + "setting return list in json statement lstReimbursement: [" + lstReimbursement.toString() + "]");

			objCtx.status(ciStatusCodeSuccess);
			objCtx.json(lstReimbursement);
		}
		
	};


	
	@Override
	public void mapEndpoints(Javalin app) {

		//
		//app.get(csRootEndpointERS_UserRole + "/:" + csParamPathUserId, getERSUserRole);
		app.get("/ers_user_role/:user_id/role", getERSUserRole);
		app.get("/ers/:user_id", getERSUserById);
		app.get("/ers_user_reimb_rec/:" + csParamUserName + "/:" + csParamPathReimbId, getUserReimbursementByReimbId);
		app.get("/ers_user_reimb_filter/:"  + csParamUserName + "/:" + csParamReimStatus, getUserReimbursementByStatus);
	}

}
