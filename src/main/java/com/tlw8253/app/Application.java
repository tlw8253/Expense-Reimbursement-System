package com.tlw8253.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tlw8253.controller.Controller;
import com.tlw8253.controller.ERSAdminController;
import com.tlw8253.controller.ExceptionController;
/*
import com.tlw8253.controller.AccountController;
import com.tlw8253.controller.ClientController;
*/
//import com.tlw8253.controller.TestController; TODO:
import com.tlw8253.controller.ERSController;

import io.javalin.Javalin;


/**
 * This project is a Maven / JUnit / Logback / JDBC template / shell
 * 
 * This is the main driver for this project.
 * 
 * 
 * 
 * @author tlw8253
 *
 */
public class Application implements Constants {
	private final static Logger objLogger = LoggerFactory.getLogger(Application.class);
	private static Javalin objJavalinApp;
	
	public static void main(String[] args) {
		String sMethod = "main(): ";		
		objLogger.trace(sMethod + "Entered");
		
		objJavalinApp = Javalin.create();
		objLogger.debug(sMethod + "mapControllers(new ExceptionController(), new ERSController(), new ERSAdminController());");
		mapControllers(/*new TestController(),*/ new ExceptionController(), new ERSController(), new ERSAdminController());
		
		objLogger.info(sMethod + "Starting listening on port: [" + ciListingPort + "]");
		objJavalinApp.start(ciListingPort); // start up our Javalin server on port defined for this program	
		
	}
	
	
	//
	//###
	public static void mapControllers(Controller... controllers) {
		String sMethod = "mapControllers(): ";		
		objLogger.trace(sMethod + "Entered");

		for (Controller c : controllers) {
			c.mapEndpoints(Application.objJavalinApp);
		}
	}


}



















