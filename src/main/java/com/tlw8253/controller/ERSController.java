package com.tlw8253.controller;

import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.Handler;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tlw8253.app.Constants;
import com.tlw8253.model.User;
import com.tlw8253.service.ERSService;

public class ERSController implements Controller, Constants {
	private Logger objLogger = LoggerFactory.getLogger(ERSController.class);
	private ERSService objErsService;

	Map<String, String> mPathParmaMap;
	Map<String, List<String>> mQueryParmaMap;
	int imPathParmaMapSize = 0;
	int imQueryParmaMap = 0;
	boolean bmQueryParmaMapIsEmpty = true;

	public ERSController() {		
		this.objErsService = new ERSService();
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
	private Handler getErsLogin = (objCtx) -> {
		String sMethod = "getErsLogin(): ";
		boolean bContinue = true;
		objLogger.trace(sMethod + "Entered");
		User objUser = null;
		
		String sParamUserName = "";
		String sParamPassword = "";

		setContextMaps(objCtx);
		
		//expect 2 query parameters with login request
		if (imQueryParmaMap != 2) {			
			//Check for body params before erroring here		
			
			objLogger.debug(sMethod + csMsgBadParamQueryParm);
			objCtx.status(ciStatusCodeErrorBadRequest);
			objCtx.json(csMsgBadParamQueryParm);
			bContinue = false;
		} else {
			sParamUserName = objCtx.queryParam(csParamUserName);
			objLogger.debug(sMethod + "Context query parameter username: [" + sParamUserName + "]");
			sParamPassword = objCtx.queryParam(csParamPassword);
			objLogger.debug(sMethod + "Context query parameter password: [" + sParamPassword + "]");
		}

		if(bContinue) {
			objUser = objErsService.getErsLogin(sParamUserName, sParamPassword);
		  objLogger.debug(sMethod + "objEmployee: [" + objUser.toString() + "]");
		}
		
		objCtx.status(ciStatusCodeSuccess);
		objCtx.json(objUser);
	};

	//
	// ### 
	private Handler getErs = (objCtx) -> {
		String sMethod = "getErs(): ";
		boolean bContinue = true;
		objLogger.trace(sMethod + "Entered");

		setContextMaps(objCtx);
		
		//expect 2 query parameters with login request
		if (imQueryParmaMap != 2) {			
			//Check for body params before erroring here		
			
			objLogger.debug(sMethod + csMsgBadParamNoPathParm);
			objCtx.status(ciStatusCodeErrorBadRequest);
			objCtx.json(csMsgBadParamNoPathParm);
			bContinue = false;
		} else {
//			sParamUserName = objCtx.queryParam(csParamUserName);
//			objLogger.debug(sMethod + "Context query parameter username: [" + sParamUserName + "]");
//			sParamPassword = objCtx.queryParam(csParamPassword);
//			objLogger.debug(sMethod + "Context query parameter password: [" + sParamPassword + "]");
		}

		if(bContinue) {
//			objUser = objErsService.getErsLogin(sParamUserName, sParamPassword);
//		  objLogger.debug(sMethod + "objEmployee: [" + objUser.toString() + "]");
		}
		
		objCtx.status(ciStatusCodeSuccess);
		objCtx.json(null);
	};

	
	@Override
	public void mapEndpoints(Javalin app) {

		//
		app.get(csRootEndpointERS_Login, getErsLogin);
		
	}

}
