package com.tlw8253.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tlw8253.controller.Controller;
import com.tlw8253.controller.ERSAdminController;
import com.tlw8253.controller.ExceptionController;
import com.tlw8253.controller.ERSUserController;
import com.tlw8253.controller.ERSLoginController;
import com.tlw8253.controller.ERSReimbController;

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

//http://localhost:3015/index.html
//http://localhost:5500/index.html	Live server
/*
 *	Two night stay while traveling for business.
 * 
 * 	tlw8253
	smp8253
	cwg8253
	cqe8253
	A_Pass12345
 */

public class Application implements Constants {
	private final static Logger objLogger = LoggerFactory.getLogger(Application.class);
	private static Javalin objJavalinApp;
	
	public static void main(String[] args) {
		String sMethod = "\n\t main(): ";		
		objLogger.trace(sMethod + "Entered");

		//Chrome will not allow sending request without the following config
		objJavalinApp = Javalin.create((config) -> {
			config.enableCorsForAllOrigins();
			config.addStaticFiles("ERS-Client");
		});

//		objJavalinApp = Javalin.create();		
		
		objLogger.debug(sMethod + "mapControllers(new ExceptionController(), new ERSController(), new ERSAdminController(), new ERSLoginController(), new ERSReimbController() );");
		mapControllers(/*new TestController(),*/ new ExceptionController(), new ERSUserController(), 
				new ERSAdminController(), new ERSLoginController(), new ERSReimbController() );
		
		objLogger.info(sMethod + "Starting listening on port: [" + ciListingPort + "]");
		objJavalinApp.start(ciListingPort); // start up our Javalin server on port defined for this program	
		
	}
	
	
	//
	//###
	public static void mapControllers(Controller... controllers) {
		String sMethod = "\n\t mapControllers(): ";		
		objLogger.trace(sMethod + "Entered");

		for (Controller c : controllers) {
			c.mapEndpoints(Application.objJavalinApp);
		}
	}


}



















