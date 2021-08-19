package com.tlw8253.app;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tlw8253.dto.ERSDTO;
import com.tlw8253.dto.ReimbursementStatusDTO;
import com.tlw8253.dto.ReimbursementTypeDTO;
import com.tlw8253.dto.UserDTO;
import com.tlw8253.dto.UserRoleDTO;
import com.tlw8253.model.ReimbursementStatus;
import com.tlw8253.model.ReimbursementType;
import com.tlw8253.model.User;
import com.tlw8253.model.UserRole;
import com.tlw8253.service.ERSAdminService;
import com.tlw8253.service.ERSService;
import com.tlw8253.util.*;

/**
 * This is a driver for admin functionality until an admin front-end is built.
 * It has methods to create the Hibranate schema, load static type values and add users. 
 * 
 * @author tlw8748253
 *
 */

public class Admin implements Constants {
	private static Logger objLogger = LoggerFactory.getLogger(Admin.class);
	private static ERSService objERSService = new ERSService();
	private static UserDTO objUserDTO;
	

	public Admin() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		String sMethod = "main(): ";
		objLogger.trace(sMethod + "Entered");

		//createTablesViaHibernate();		//NOTE: change configuration file to create
		//ersAdminAddStaticTableValues();
		addNewEmployee("tlw8253","A_Pass12345","Tomas","Ykel","tlw8253@wws.com",csUserRoles[ciUserRoleEmployee]);

	}

	//
	//###
	public static void addNewEmployee(String sUsername, String sPassword, String sFirstName, String sLastName, String sEmail, String sRole) {
		String sMethod = "addNewEmployee(): ";
		objLogger.trace(sMethod + "Entered");
		
		objUserDTO = new UserDTO(sUsername, sPassword, sFirstName, sLastName, sEmail, sRole);
		objLogger.debug(sMethod + "objUserDTO: [" + objUserDTO.toString() + "]");		
		try {
			User objEmployee = objERSService.addNewUser(objUserDTO);
			objLogger.debug(sMethod + "objEmployee: [" + objEmployee.toString() + "]");	
		} catch (Exception e) {
			objLogger.debug(sMethod + "Exception: [" + e.getMessage() + "]");	
		}

	}

	//
	//###
	private static void createTablesViaHibernate() {
		String sMethod = "createTablesViaHibernate(): ";
		objLogger.trace(sMethod + "Entered");

		// need to set the config.xm property to: <property name="hbm2ddl.auto">create</property>
		SessionFactory sf = SessionFactorySingleton.getSessionFactory();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		tx.commit();

	}

	//
	//###
	private static void ersAdminAddStaticTableValues() {
		addReimbursementStatus();
		addReimbursementType();
		addUserRole();
	}

	
	//
	//###
	private static void addReimbursementStatus() {
		String sMethod = "addReimbursementStatus(): ";
		objLogger.trace(sMethod + "Entered");

		String sStatus = csReimbStatus[ciReimbStatusPending];
		String sStatusDesc = "The status when a reimbursement request is first created and submitted by an user.";
		objLogger.debug(sMethod + "Adding status: [" + sStatus+ "] description: [" + sStatusDesc +"]");
		addReimbStatus(sStatus, sStatusDesc);
		
		sStatus = csReimbStatus[ciReimbStatusApproved];
		sStatusDesc = "The status when a reimbursement request is reviewed and approved by the Finance Manager.";
		objLogger.debug(sMethod + "Adding status: [" + sStatus+ "] description: [" + sStatusDesc +"]");
		addReimbStatus(sStatus, sStatusDesc);

		sStatus = csReimbStatus[ciReimbStatusDenied];
		sStatusDesc = "The status when a reimbursement request is reviewed and denied by the Finance Manager.";
		addReimbStatus(sStatus, sStatusDesc);
		
	}
	
	//
	//###
	private static void addReimbStatus(String sStatus, String sStatusDesc) {
		String sMethod = "addReimbStatus(): ";
		objLogger.trace(sMethod + "Entered");

		ERSAdminService objAdminService = new ERSAdminService();
		ReimbursementStatusDTO objReimbStatusDTO = new ReimbursementStatusDTO();

		objReimbStatusDTO.setReimbStatus(sStatus);
		objReimbStatusDTO.setReimbStatusDescription(sStatusDesc);
		
		try {
			ReimbursementStatus objReimbStatus = objAdminService.addReimbursementStatus(objReimbStatusDTO);
			objLogger.debug(sMethod + "objReimbStatus: [" + objReimbStatus.toString() + "]");
		} catch (Exception e) {
			objLogger.error(sMethod + "Exception during processing: [" + e.getMessage() + "]");
			//throw new DatabaseException(e.getMessage());
		}		
	}

	
	//
	//###
	private static void addReimbType(String sType, String sTypeDesc) {
		String sMethod = "addReimbType(): ";
		objLogger.trace(sMethod + "Entered");

		ERSAdminService objAdminService = new ERSAdminService();
		ReimbursementTypeDTO objReimbTypeDTO = new ReimbursementTypeDTO();

		objReimbTypeDTO.setReimbType(sType);
		objReimbTypeDTO.setReimbTypeDescription(sTypeDesc);
		
		try {
			ReimbursementType objReimbType = objAdminService.addReimbursementType(objReimbTypeDTO);
			objLogger.debug(sMethod + "objReimbType: [" + objReimbType.toString() + "]");
		} catch (Exception e) {
			objLogger.error(sMethod + "Exception during processing: [" + e.getMessage() + "]");
			//throw new DatabaseException(e.getMessage());
		}		
	}

	//
	//###
	private static void addReimbursementType() {
		String sMethod = "addReimbursementType(): ";
		objLogger.trace(sMethod + "Entered");

		String sType = csReimbType[ciReimbTypeLodging];
		String sTypeDesc = "Reimbursement expense related to overnight stays related to business travel.";
		objLogger.debug(sMethod + "Adding type: [" + sType+ "] description: [" + sTypeDesc +"]");
		addReimbType(sType, sTypeDesc);
		
		sType = csReimbType[ciReimbTypeTravel];
		sTypeDesc = "Reimbursement expense related to planes, trains, automobiles, etc.";
		objLogger.debug(sMethod + "Adding type: [" + sType+ "] description: [" + sTypeDesc +"]");
		addReimbType(sType, sTypeDesc);

		sType = csReimbType[ciReimbTypeFood];
		sTypeDesc = "Reimbursement expense related to meals related to business travel.";
		objLogger.debug(sMethod + "Adding type: [" + sType+ "] description: [" + sTypeDesc +"]");
		addReimbType(sType, sTypeDesc);

		sType = csReimbType[ciReimbTypeOther];
		sTypeDesc = "Reimbursement expense related to other expenses related to the business.";
		objLogger.debug(sMethod + "Adding type: [" + sType+ "] description: [" + sTypeDesc +"]");
		addReimbType(sType, sTypeDesc);

	}

	private static void addUserRole() {
		String sMethod = "addUserRole(): ";
		objLogger.trace(sMethod + "Entered");

		String sUserRole = csUserRoles[ciUserRoleEmployee];
		String sUserRoleDesc = "Any person actively employeed by the company with a valid username.";
		objLogger.debug(sMethod + "Adding type: [" + sUserRole+ "] description: [" + sUserRoleDesc +"]");
		addUsrRole(sUserRole, sUserRoleDesc);
		
		sUserRole = csUserRoles[ciUserRoleFinanceMgr];
		sUserRoleDesc = "Finance managers are authorized to approve and deny requests for expense reimbursement.";
		objLogger.debug(sMethod + "Adding type: [" + sUserRole+ "] description: [" + sUserRoleDesc +"]");
		addUsrRole(sUserRole, sUserRoleDesc);

		sUserRole = csUserRoles[ciUserRoleAdmin];
		sUserRoleDesc = "A super user of the system.  A system admin.";
		objLogger.debug(sMethod + "Adding type: [" + sUserRole+ "] description: [" + sUserRoleDesc +"]");
		addUsrRole(sUserRole, sUserRoleDesc);

	}

	//
	//###
	private static void addUsrRole(String sUserRole, String sUserRoleDesc) {
		String sMethod = "addReimbType(): ";
		objLogger.trace(sMethod + "Entered");

		ERSAdminService objAdminService = new ERSAdminService();
		UserRoleDTO objUserRoleDTO = new UserRoleDTO();

		objLogger.debug(sMethod + "Setting DTO: sUserRole: [" + sUserRole + "] sUserRoleDesc: [" + sUserRoleDesc + "]");
		
		objUserRoleDTO.setUserRole(sUserRole);
		objUserRoleDTO.setUserRoleDescription(sUserRoleDesc);

		objLogger.debug(sMethod + "objUserRoleDTO: [" + objUserRoleDTO.toString() + "]");

	
		try {
			UserRole objUserRole = objAdminService.addUserRole(objUserRoleDTO);
			objLogger.debug(sMethod + "objUserRole: [" + objUserRole.toString() + "]");
		} catch (Exception e) {
			objLogger.error(sMethod + "Exception during processing: [" + e.getMessage() + "]");
			//throw new DatabaseException(e.getMessage());
		}	
	}

	

	
	
}