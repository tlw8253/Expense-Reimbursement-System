package com.tlw8253.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.mariadb.jdbc.Driver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConnectionUtility {
	private final static Logger objLogger = LoggerFactory.getLogger(ConnectionUtility.class);

	private ConnectionUtility() {
	}
	
	public static Connection getConnection() throws SQLException {
		String sMethod = "getConnection(): ";
		objLogger.trace(sMethod + "Entered");
				
		objLogger.debug(sMethod + "registering driver: DriverManager.registerDriver(new Driver());");
		DriverManager.registerDriver(new Driver());
		
		
		String sURL = System.getenv("localhost_db_url");	
		objLogger.debug(sMethod + "get environment variable: localhost_db_url: [" + sURL + "]");
		sURL = "jdbc:mariadb://localhost:3306/exp_reimb_sys";
		objLogger.debug(sMethod + "set sURL: [" + sURL + "]");
		
		String sUsername = System.getenv("localhost_db_username");
		objLogger.debug(sMethod + "get environment variable: localhost_db_username: [" + sUsername + "]");
		String sPassword = System.getenv("localhost_db_password");
		objLogger.debug(sMethod + "get environment variable: localhost_db_password: [" + sPassword + "]");
		//sPassword = "bogus";	//used for exception testing
		
		objLogger.debug(sMethod + "Attempting database connection: URL: [" + sURL + "] username: [" + sUsername + "]");
		Connection conConnection = DriverManager.getConnection(sURL, sUsername, sPassword);
			
		objLogger.debug(sMethod + "returning connection.");
		return(conConnection);
	}

	public static Connection getConnectionJDBC() throws SQLException {
		String sMethod = "getConnection(): ";
		objLogger.trace(sMethod + "Entered");
				
		DriverManager.registerDriver(new Driver());
		String sURL = System.getenv("ps_db_url");		
		sURL = "jdbc:mariadb://localhost:3306/exp_reimb_sys2";
		
		String sUsername = System.getenv("p0_db_username");
		String sPassword = System.getenv("p0_db_password");
		//sPassword = "bogus";	//used for exception testing
		
		objLogger.debug(sMethod + "Attempting database connection: URL: [" + sURL + "] username: [" + sUsername + "]");
		Connection conConnection = DriverManager.getConnection(sURL, sUsername, sPassword);
			
		return(conConnection);
	}

}
