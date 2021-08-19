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
	String csRootEndpointERS = "/ers";
	String csRootEndpointERS_Login = "/ers_login"; 
	
	String csRootEndpointERSAdminStatus = "/ers_admin_status";

	//HTTP request parameter constants
	String csParamPathName = "name";
	String csParamUserName = "username";
	String csParamPassword = "password";
	String csParamReimStatus = "reim_status";

	
	//General Model Attribute Constants
	String csGenAtribNameValue = "non_specific_path_id";
	

	//database constants 
	String csDatabaseName = "exp_reimb_sys";							//database name
	
	
		
	//table constants these must match the table attributes

	//  table: Reimbursement Status
	String csReimbStatusTable				= "ers_reimbursement_status";	//table name
	String csDBReimbStatusTable = csDatabaseName + "." +csReimbStatusTable; //qualified table name
	
	String csReimbStatusTblReimbStatusId 	= "reimb_status_id";		//PK primary key
	String csReimbStatusTblReimbStatus	 	= "reimb_status";			//String
	String csReimbStatusTblReimbStatusDesc 	= "reimb_status_desc";		//String
	String[] csReimbStatus = {"PENDING", "APPROVED", "DENIED"};
	int ciReimbStatusPending = 0;
	int ciReimbStatusApproved = 1;
	int ciReimbStatusDenied = 2;

	//  table: Reimbursement Status
	String csReimbTypeTable					= "ers_reimbursement_type";	//table name
	String csDBReimbTypeTable = csDatabaseName + "." +csReimbTypeTable; //qualified table name
	
	String csReimbTypeTblReimbTypeId 		= "reimb_type_id";		//PK primary key
	String csReimbTypeTblReimbType	 		= "reimb_type";			//String
	String csReimbTypeTblReimbTypeDesc	 	= "reimb_type_desc";	//String
	String[] csReimbType = {"LODGING", "TRAVEL", "FOOD" ,"OTHER"};
	int ciReimbTypeLodging = 0;
	int ciReimbTypeTravel = 1;
	int ciReimbTypeFood = 2;
	int ciReimbTypeOther = 3;
	
	//  table: User Roles
	String csUserRolesTable 				= "ers_user_roles";		//table name
	String csDBReimbRolesTable = csDatabaseName + "." + csUserRolesTable; //qualified table name
	
	String csUserRolesTblUserRoleId 		= "ers_user_role_id";	//PK primary key
	String csUserRolesTblUserRole	 		= "user_role";			//String
	String csUserRolesTblUserRoleDesc 		= "user_role_desc";			//String
	String[] csUserRoles = {"EMPLOYEE", "FINANCEMGR", "SUPERMAN"};
	int ciUserRoleEmployee = 0;
	int ciUserRoleFinanceMgr = 1;
	int ciUserRoleAdmin = 2;
	
	//  table: Reimbursement
	String csReimburstementTable 		= "ers_reimbursement";	//table name		
	String csDBReimburstementTable = csDatabaseName + "." + csReimburstementTable; //qualified table name
	
	String csReimbTblReimbId 			= "reimb_id";			//PK primary key
	String csReimbTblReimbAmount 		= "reimb_amount";		//number / double
	String csReimbTblReimbSubmitted 	= "reimb_submitted";	//time stamp
	String csReimbTblReimbResolved 		= "reimb_resolved";		//time stamp
	String csReimbTblReimbDescription 	= "reimb_description";	//string
	String csReimbTblReimbReceipt 		= "reimb_receipt";		//blob - image or word doc
	String csReimbTblReimbAuthor 		= "reimb_author";		//FK foreign key
	String csReimbTblReimbAuthorId 		= "reimb_author_id";	//For the DTO
	String csReimbTblReimbResolver 		= "reimb_resolver";		//FK foreign key
	String csReimbTblReimbResolverId 	= "reimb_resolver_id";	//For the DTO
	String csReimbTblReimbStatusId 		= "reimb_status_id";	//FK foreign key
	String csReimbTblReimbStatus 		= "reimb_status";		//For the DTO
	String csReimbTblReimbTypeId 		= "reimb_type_id";		//FK foreign key
	String csReimbTblReimbType	 		= "reimb_type";			//For the DTO
	
	
	//  table: user
	String csUserTable = "ers_users";							//table name
	String csDBUserTable = csDatabaseName + "." + csUserTable;		//if using JDBC
	
	String csUserTblId = "ers_users_id";
	String csUserTblUsername = "ers_username";
	String csUserTblPassword = "ers_password";
	String csUserTblFirstName = "ers_first_name";
	String csUsrTblLastName = "ers_last_name";
	String csUserTblEmail = "ers_email";
	String csUserTblRoleId = "ers_role_id";
	String csUserTblRoleName = csUserRolesTblUserRole;
	
	
	//HQL fully qualified class names
	String csHQL_ModelPackage = "com.tlw8253.model";
	String csHQL_ModelClassReimbStatus = csHQL_ModelPackage + ".ReimbursementStatus";
	String csHQL_ModelClassReimbType = csHQL_ModelPackage + ".ReimbursementType";
	String csHQL_ModelClassUserRole = csHQL_ModelPackage + ".UserRole";
	String csHQL_ModelClassReimbursement = csHQL_ModelPackage + ".Reimbursement";
	String csHQL_ModelClassUser = csHQL_ModelPackage + ".User";

	//Define program messages to use in the program and for testing
	String csMsgDB_ErrorGettingWithLogin = "Error with database during employee login.";	
	String csMsgDB_ErrorAddingReimbursementStatus = "Error with database when adding Reimbursement Status.";
	String csMsgDB_ErrorGettingReimbursementStatus = "Error with database when getting all Reimbursement Status.";
	String csMsgDB_ErrorAddingReimbursementType = "Error with database when adding Reimbursement Type.";
	String csMsgDB_ErrorGettingReimbursementType = "Error with database when getting all Reimbursement Types.";
	String csMsgDB_ErrorAddingUserRole = "Error with database when adding User Role.";
	String csMsgDB_ErrorGettingUserRole = "Error with database when getting all User Roles.";

	
	String csMsgEmployeeRecordNotFound = "Employee was not found in the database.";
	
	String csMsgBadParamNoPathParm = "Parmeter(s) expected. No Path Parameter(s) Received.";
	String csMsgBadParamNoQueryParm = "Parmeter(s) expected. No Query Parameter(s) Received.";
	String csMsgBadParamNoBodyParm = "Parmeter(s) expected. No Body Parameter(s) Received.";
	
	String csMsgBadParamQueryParm = "Parmeter(s) expected. Not right number of Query Parameter(s) received.";
	
	String csMsgBadParamReimbStatus = "Invalid Reimbursement Status parameters received.";
	String csMsgBadParamReimbType = "Invalid Reimbursement Type parameters received.";
	String csMsgBadParamUserRole = "Invalid User Role parameters received.";
	
	String csMsgBadParamAddUser = "One or more add user parameters are invalid.";
	
	String csMsgAutenticationFailed = "Autentication failed for username and password provided.";
	
	String csMsgDB_ErrorAddingEmployee = "Database error when adding an employee.";
	
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













