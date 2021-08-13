package com.tlw8253.controller;

import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.Handler;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tlw8253.app.Constants;
import com.tlw8253.model.General;
import com.tlw8253.service.GeneralService;

public class GeneralController implements Controller, Constants {
	private Logger objLogger = LoggerFactory.getLogger(GeneralController.class);
	private GeneralService objGeneralService;

	Map<String, String> mPathParmaMap;
	Map<String, List<String>> mQueryParmaMap;
	int imPathParmaMapSize = 0;
	int imQueryParmaMap = 0;
	boolean bmQueryParmaMapIsEmpty = true;

	public GeneralController() {		
		this.objGeneralService = new GeneralService();
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
	// ### GET /non_specific -
	private Handler getReturnGeneral = (objCtx) -> {
		String sMethod = "getReturnGeneral(): ";
		boolean bContinue = true;
		objLogger.trace(sMethod + "Entered");
		
		String sParamPathGeneralId = "";
		String sParamQueryGeneralId = "";

		setContextMaps(objCtx);
		
		
		if (imPathParmaMapSize == 0) {
			objLogger.debug(sMethod + csMsgBadParamNoPathParm);
			objCtx.status(ciStatusCodeErrorBadRequest);
			objCtx.json(csMsgBadParamNoPathParm);
			bContinue = false;

		} else {
			sParamPathGeneralId = objCtx.pathParam(csParamPathName);
			objLogger.debug(sMethod + "Context path parameter general id: [" + sParamPathGeneralId + "]");
		}

		if (imQueryParmaMap == 0) {
			
			//Check for body params before erroring here
			
			
			objLogger.debug(sMethod + csMsgBadParamNoPathParm);
			objCtx.status(ciStatusCodeErrorBadRequest);
			objCtx.json(csMsgBadParamNoPathParm);
			bContinue = false;
		} else {
			sParamQueryGeneralId = objCtx.queryParam(csParamQueryGeneralId);
			objLogger.debug(sMethod + "Context query parameter general id: [" + sParamQueryGeneralId + "]");
		}

		 List<General> lstGeneral = objGeneralService.getReturnGeneral();
		
		objCtx.status(ciStatusCodeSuccess);
		objCtx.json(lstGeneral);
	};

	//
	// ### POST /non_specific -
	private Handler postCreateGeneral = (objCtx) -> {
		String sMethod = "postNonSpecific(): ";
		objLogger.trace(sMethod + "Entered");

		String sParamPathGeneralId = "";
		String sParamQueryGeneralId = "";

		setContextMaps(objCtx);
		
		
		if (imPathParmaMapSize == 0) {
			objLogger.debug(sMethod + "No context path parameter received.");
		} else {
			sParamPathGeneralId = objCtx.pathParam(csParamPathGeneralId);
			objLogger.debug(sMethod + "Context path parameter general id: [" + sParamPathGeneralId + "]");
		}

		if (imQueryParmaMap == 0) {
			objLogger.debug(sMethod + "No context query parameter received.");
		} else {
			sParamQueryGeneralId = objCtx.queryParam(csParamQueryGeneralId);
			objLogger.debug(sMethod + "Context query parameter general id: [" + sParamQueryGeneralId + "]");
		}

		 List<General> lstGeneral = objGeneralService.getReturnGeneral();
		
		objCtx.status(ciStatusCodeSuccess);
		objCtx.json(lstGeneral);
	
	};

	// ### PUT /non_specific -
	private Handler putUpdateGeneral = (objCtx) -> {
		String sMethod = "postNonSpecific(): ";
		objLogger.trace(sMethod + "Entered");

		logContextParameters(objCtx);

		String sParamPathNonSpecificId = objCtx.pathParam(csParamPathGeneralId);
		objLogger.debug(sMethod + "Context path parameter non specific id: [" + sParamPathNonSpecificId + "]");

		String sParamQueryNonSpecificId = objCtx.queryParam(csParamQueryGeneralId);
		objLogger.debug(sMethod + "Context query parameter non specific id: [" + sParamQueryNonSpecificId + "]");

		objCtx.status(ciStatusCodeSuccess);
		objCtx.json("NonSpecific() message or object");
	};

	// ### DELETE /non_specific -
	private Handler deleteRemoveGeneral = (objCtx) -> {
		String sMethod = "postNonSpecific(): ";
		objLogger.trace(sMethod + "Entered");

		logContextParameters(objCtx);

		String sParamPathNonSpecificId = objCtx.pathParam(csParamPathGeneralId);
		objLogger.debug(sMethod + "Context path parameter non specific id: [" + sParamPathNonSpecificId + "]");

		String sParamQueryNonSpecificId = objCtx.queryParam(csParamQueryGeneralId);
		objLogger.debug(sMethod + "Context query parameter non specific id: [" + sParamQueryNonSpecificId + "]");

		objCtx.status(ciStatusCodeSuccess);
		objCtx.json("NonSpecific() message or object");
	};

	@Override
	public void mapEndpoints(Javalin app) {

		//000.02 GET Get general request All Records: Records Returned
		//http://localhost:3015/general
		app.get(csRootEndpointGeneral, getReturnGeneral);		
		
		app.post(csRootEndpointGeneral + "/:" + csParamPathName, postCreateGeneral);
		
		
		
		app.put(csRootEndpointGeneral, putUpdateGeneral);
		app.delete(csRootEndpointGeneral, deleteRemoveGeneral);
	}

}
