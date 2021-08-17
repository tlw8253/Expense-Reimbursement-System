package com.tlw8253.app;

/**
 * Implementing class Constants as an interface allows the class to 
 * use defined variable direction without using qualifying notation: Constants.varName;
 * 
 * Classes not implementing class Constants can still access variable using qualifying notation: Constants.varName;
 * 
 * @author tlw8748253
 *
 */

/*
 * Interface methods are by default abstract and public
 * Interface attributes are by default public, static and final
 */
public interface Constants {

	//Return status codes
	//	source: https://developer.mozilla.org/en-US/docs/Web/HTTP/Status
	int ciStatusCodeSuccess = 200;				//The request has succeeded. 
	int ciStatusCodeSuccessCreated = 201;		//The request has succeeded and a new resource has been created as a result. 
	int ciStatusCodeSuccessNoContent = 204;		//There is no content to send for this request,

	int ciStatusCodeErrorBadRequest = 400;		//The server could not understand the request due to invalid syntax.
	int ciStatusCodeNotFound = 404;				//The server can not find the requested resource. In the browser.
	int ciStatusCodeImA_Teapot = 418;			//The server refuses the attempt to brew coffee with a teapot.

	int ciStatusCodeInternalServerError = 500;	//The server has encountered a situation it doesn't know how to handle.
	
	//context parameters
	int ciListingPort = 3015;
	
	//End Points
	String csRootEndpointGeneral = "/general";
	String csRootEndpointERS = "/ers";
	String csRootEndpointERS_Login = "/ers_login"; 
	String csRootEndpointERS_LoginJDBC = "/ers_login_jdbc"; 

	//HTTP request parameter constants
	String csParamPathGeneralId = "general_path_id";
	String csParamQueryGeneralId = "general_query_id";
	String csParamPathName = "name";
	String csParamUserName = "username";
	String csParamPassword = "password";

	
	//General Model Attribute Constants
	String csGenAtribNameValue = "non_specific_path_id";
	

	//database constants 
	String csDatabaseName = "exp_reimb_sys";							//database name
	String csEmployeeTable = "employee";
	String csDBEmployeeTable = csDatabaseName + "." + csEmployeeTable;		
		
	//table constants these must match the table attributes
	//  table: employhee
	String csEmployeeTblUsername = "emp_username";
	String csEmployeeTblPassword = "emp_password";
	String csEmployeeTblFirstName = "emp_first_name";
	String csEmployeeTblLastName = "emp_last_name";
	String csEmployeeTblEmail = "emp_email";
	
	
	/*
	 * shell_id INTEGER PRIMARY KEY AUTO_INCREMENT,
shell_name VARCHAR(255) NOT NULL CHECK(LENGTH(shell_name)> 0),
shell_int INTEGER NOT NULL,
shell_doulble DOUBLE NOT NULL,
shell_boolean BOOLEAN NOT NULL

	 * */
	
	//HTTP request parameter constants

	//Define program messages to use in the program and for testing
	String csMsgDB_ErrorGettingWithLogin = "Error with database during employee login.";	
	String csMsgEmployeeRecordNotFound = "Employee was not found in the database.";
	
	String csMsgBadParamNoPathParm = "Parmeter(s) expected. No Path Parameter(s) Received.";
	String csMsgBadParamNoQueryParm = "Parmeter(s) expected. No Query Parameter(s) Received.";
	String csMsgBadParamNoBodyParm = "Parmeter(s) expected. No Body Parameter(s) Received.";
	
	String csMsgAutenticationFailed = "Autentication failed for username and password provided.";
	
	/*
	String csMsgDB_ErrorGettingByClientId = "Database error getting the client by id.";
	String csMsgDB_ErrorAddingClient = "Database error when adding a client.";
	String csMsgDB_ErrorUpdatingClient = "Database error when updating client information.";
	String csMsgDB_ErrorDeletingClient = "Database error when deleting client information.";
	String csMsgDB_ErrorAddingAccountForClient = "Database error while adding a new account for a client.";
	String csMsgDB_ErrorDeletingAccountForClient = "Database error while deleteing client's account.";
	String csMsgDB_ErrorUpdatingAccountForClient = "Database error while updating an existing account for a client.";
	String csMsgDB_ErrorGettingAccount = "Database error while getting an account.";
	
	String csMsgBadParamClientId = "Client Id must be a number.";
	String csMsgBadParamClientName = "Client first and last name must contain values.";
	String csMsgBadParamNotInts = "One or more parameters are not numbers.";
	String csMsgBadParamAcctNumNotNumber = "Account number must be a number.";
	String csMsgBadParmAccountBalance = "Account balance did not tranlate to numeric value.";

	String csMsgClientNotFound = "Client was not found in the database.";
	String csMsgAccountNotFound = "Account not found in the database for the account number.";
	String csMsgAccountNotFoundForClient = "Account not found in the database for the client.";
	String csMsgAccountsNotFoundForClient = "Accounts were not found in the database for the client.";
	String csMsgAcctDoesNotBelongToClient = "Account number not assigned to this client.";
*/
}













