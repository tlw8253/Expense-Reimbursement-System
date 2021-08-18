package com.tlw8253.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.tlw8253.app.Constants;
import com.tlw8253.model.ReimbursementStatus;
import com.tlw8253.service.ERSAdminService;

import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.Handler;

public class ERSAdminController implements Controller, Constants {
	private Logger objLogger = LoggerFactory.getLogger(ERSAdminController.class);
	private ERSAdminService objERSAdminService = new ERSAdminService();
	
	Map<String, String> mPathParmaMap;
	Map<String, List<String>> mQueryParmaMap;
	int imPathParmaMapSize = 0;
	int imQueryParmaMap = 0;
	boolean bmQueryParmaMapIsEmpty = true;

	public ERSAdminController() {
		// TODO Auto-generated constructor stub
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

	private Handler postAddErsStatus = (objCtx) -> {
		String sMethod = "getErs(): ";
		boolean bContinue = true;
		objLogger.trace(sMethod + "Entered");

		setContextMaps(objCtx);
		String sParamReimStatus = "";
		String sParamReimStatusDesc = "";
		ReimbursementStatus objReimbStatus = null;
		
		//expect 2 query parameters with login request
		if (imQueryParmaMap != 2) {			
			
			objLogger.debug(sMethod + csMsgBadParamQueryParm);
			objCtx.status(ciStatusCodeErrorBadRequest);
			objCtx.json(csMsgBadParamQueryParm);
			bContinue = false;
		} else {
			sParamReimStatus = objCtx.queryParam(csParamReimStatus);
			objLogger.debug(sMethod + "Context query parameter reimbursement status: [" + sParamReimStatus + "]");
			sParamReimStatusDesc = objCtx.queryParam(csReimbStatusTblReimbStatusDesc);
			objLogger.debug(sMethod + "Context query parameter reimbursement status description: [" + sParamReimStatusDesc + "]");
		}

		if(bContinue) {
			objReimbStatus = objERSAdminService.addReimbursementStatus(null);
			objLogger.debug(sMethod + "objReimbStatus: [" + objReimbStatus.toString() + "]");
		}
		
		objCtx.status(ciStatusCodeSuccess);
		objCtx.json(objReimbStatus);
	};

	
	
	@Override
	public void mapEndpoints(Javalin app) {
		//
		app.post(csRootEndpointERSAdminStatus, postAddErsStatus);
		
	}

}
