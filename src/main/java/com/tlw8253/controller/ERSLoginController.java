package com.tlw8253.controller;

import javax.servlet.http.HttpSession;

import io.javalin.Javalin;
//import io.javalin.http.Context;
import io.javalin.http.Handler;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tlw8253.app.Constants;
import com.tlw8253.dto.LoginDTO;
import com.tlw8253.dto.MessageDTO;
import com.tlw8253.model.User;
import com.tlw8253.service.ERSLoginService;

public class ERSLoginController implements Controller, Constants {
	private Logger objLogger = LoggerFactory.getLogger(ERSLoginController.class);
	private ERSLoginService objERSLoginService = new ERSLoginService();
	private LoginDTO objLoginDTO = new LoginDTO();

	Map<String, String> mPathParmaMap;
	Map<String, List<String>> mQueryParmaMap;
	int imPathParmaMapSize = 0;
	int imQueryParmaMap = 0;
	boolean bmQueryParmaMapIsEmpty = true;

	public ERSLoginController() {
		this.objERSLoginService = new ERSLoginService();
	}

	//
	// ###
	private Handler postErsLoginHandler = (objCtx) -> {
		String sMethod = "\n\t postErsLoginHandler(): ";
		objLogger.trace(sMethod + "Entered");

		objLoginDTO = objCtx.bodyAsClass(LoginDTO.class);
		User objUser = objERSLoginService.login(objLoginDTO);
		objLogger.debug(sMethod + "objUser: [" + objUser.toString() + "]");

		HttpSession httpSession = objCtx.req.getSession();
		httpSession.setAttribute(csSessionCurrentUser, objUser);
		if (httpSession.getAttribute(csSessionCurrentUser) == null) {
			objLogger.debug(sMethod + csSessionCurrentUser + " is null");
		}else {
			objLogger.debug(sMethod + csSessionCurrentUser + " is not null");
		}
		

		objCtx.json(objUser);
		objCtx.status(200);
	};

	//
	// ###
	private Handler getERSCurrentUserHandler = (objCtx) -> {
		String sMethod = "\n\t getERSCurrentUserHandler(): ";
		objLogger.trace(sMethod + "Entered");

		HttpSession httpSession = objCtx.req.getSession();
		objLogger.debug(sMethod + "Getting session attribute for: [" + csSessionCurrentUser + "]");
		if (httpSession.getAttribute(csSessionCurrentUser) == null) {
			objLogger.debug(sMethod + "no active session recorded for any user");
			objCtx.json(new MessageDTO(csMsgSessionUserNotActive));
			objCtx.status(401);
		} else {
			User objUser = (User) httpSession.getAttribute(csSessionCurrentUser);
			objLogger.debug(sMethod + "Active session recorded objUser: [" + objUser.toString() + "]");
			objCtx.json(objUser);
			objCtx.status(200);
		}

	};

	//
	// ###
	private Handler getERSValidateSessionUserHandler = (objCtx) -> {
		String sMethod = "\n\t postERSValidateSessionUserHandler(): ";
		objLogger.trace(sMethod + "Entered");

		HttpSession httpSession = objCtx.req.getSession();
		if (httpSession.getAttribute(csSessionCurrentUser) == null) {
			objLogger.debug(sMethod + "there is no active session for any user.");
			objCtx.json(new MessageDTO(csMsgSessionUserNotActive));
			objCtx.status(401);
		} else {
			User objCurrentUser = (User) httpSession.getAttribute(csSessionCurrentUser);
			objLogger.debug(sMethod + "objUser: [" + objCurrentUser.toString() + "]");
			objLoginDTO = objCtx.bodyAsClass(LoginDTO.class);
			objLogger.debug(sMethod + "objLoginDTO: [" + objLoginDTO.toString() + "]");

			if (objCurrentUser.getUsername().equals(objLoginDTO.getUsername())
					&& objCurrentUser.getPassword().equals(objLoginDTO.getPassword())) {
				objCtx.json(objCurrentUser);
				objCtx.status(200);
			} else {
				objLogger.debug(
						sMethod + "there is no active session for username: [" + objLoginDTO.getUsername() + "]");
				// objCtx.json(new MessageDTO(csMsgSessionUserNotActive));
				objCtx.json(csMsgSessionUserNotActive);
				objCtx.status(401);
			}
		}

	};

	//
	// ###
	private Handler postErsLogoutHandler = (objCtx) -> {
		String sMethod = "\n\t postErsLogoutHandler(): ";
		objLogger.trace(sMethod + "Entered");

		HttpSession httpSession = objCtx.req.getSession();
		if (httpSession.getAttribute(csSessionCurrentUser) == null) {
			objLogger.debug(sMethod + "there is no active session for any user.");
			objCtx.json(new MessageDTO(csMsgSessionUserLoggedOut));
			objCtx.status(200);
		} else {
			User objCurrentUser = (User) httpSession.getAttribute(csSessionCurrentUser);
			objLogger.debug(sMethod + "objUser: [" + objCurrentUser.toString() + "]");
			objLoginDTO = objCtx.bodyAsClass(LoginDTO.class);
			objLogger.debug(sMethod + "objLoginDTO: [" + objLoginDTO.toString() + "]");

			if (objCurrentUser.getUsername().equals(objLoginDTO.getUsername())
					&& objCurrentUser.getPassword().equals(objLoginDTO.getPassword())) {				
				objLogger.debug(sMethod + "username: [" + objLoginDTO.getUsername() + "] is being logged out.");
					httpSession.setAttribute(csSessionCurrentUser, null);
					objCtx.json(new MessageDTO(csMsgSessionUserLoggedOut));
					objCtx.status(200);
				} else {
					objLogger.debug(
							sMethod + "there is no active session for username: [" + objLoginDTO.getUsername() + "]");
					objCtx.json(new MessageDTO(csMsgSessionUserNotActive));
					// objCtx.json(csMsgSessionUserNotActive);
					objCtx.status(401);
			}
		}

	};

	@Override
	public void mapEndpoints(Javalin app) {

		//
		app.post(csRootEndpointERS_Login, postErsLoginHandler);
		app.post(csRootEndpointERS_Logout, postErsLogoutHandler);
		app.get(csRootEndpointERS_CurrentUser, getERSCurrentUserHandler);
		app.get(csRootEndpointERS_SessionValidate, getERSValidateSessionUserHandler);

	}

}
