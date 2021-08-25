package com.tlw8253.service;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tlw8253.app.Constants;
import com.tlw8253.dao.GenericDAO;
import com.tlw8253.dao.ReimbursementStatusDAOImpl;
import com.tlw8253.dao.ReimbursementTypeDAOImpl;
import com.tlw8253.dao.UserRoleDAOImpl;
import com.tlw8253.dto.ReimbursementStatusDTO;
import com.tlw8253.dto.ReimbursementTypeDTO;
import com.tlw8253.dto.UserRoleDTO;
import com.tlw8253.exception.*;
import com.tlw8253.model.UserRole;
import com.tlw8253.util.Validate;
import com.tlw8253.model.ReimbursementStatus;
import com.tlw8253.model.ReimbursementType;

public class ERSAdminService implements Constants {
	private Logger objLogger = LoggerFactory.getLogger(ERSAdminService.class);
	private GenericDAO<ReimbursementStatus> objReimbStatusDAO;
	private GenericDAO<ReimbursementType> objReimbTypeDAO;
	private GenericDAO<UserRole> objUserRoleDAO;

	public ERSAdminService() {
		this.objReimbStatusDAO = new ReimbursementStatusDAOImpl();
		this.objReimbTypeDAO = new ReimbursementTypeDAOImpl();
		this.objUserRoleDAO = new UserRoleDAOImpl();
	}

	// Cannot overload constructor with different signatures of GenericDAO<T> so
	// provide get methods
	public ERSAdminService getMockReimbStatusDAO(GenericDAO<ReimbursementStatus> objMockReimbStatusDAO) {
		this.objReimbStatusDAO = objMockReimbStatusDAO;
		return this;
	}

	public ERSAdminService getMockReimbTypeDAO(GenericDAO<ReimbursementType> objMockReimbTypeDAO) {
		this.objReimbTypeDAO = objMockReimbTypeDAO;
		return this;
	}

	public ERSAdminService getMockUserRoleDAO(GenericDAO<UserRole> objMockUserRoleDAO) {
		this.objUserRoleDAO = objMockUserRoleDAO;
		return this;
	}

	//
	// ###
	public List<ReimbursementStatus> getAllReimbursementStatus() throws DatabaseException {
		String sMethod = "getAllReimbursementStatus(): ";
		objLogger.trace(sMethod + "Entered");

		try {

			List<ReimbursementStatus> lstReimbStatus = objReimbStatusDAO.getAllRecords();
			objLogger.debug(sMethod + "lstReimbStatus: [" + lstReimbStatus.toString() + "]");
			return lstReimbStatus;

		} catch (SQLException e) {
			objLogger.warn(sMethod + "SQLException while getting all Reimbursement Status: [" + e.getMessage() + "]");
			throw new DatabaseException(csMsgDB_ErrorGettingAllReimbursementStatus);
		} catch (HibernateException e) {
			objLogger.warn(
					sMethod + "HibernateException while getting all Reimbursement Status: [" + e.getMessage() + "]");
			throw new DatabaseException(csMsgDB_ErrorGettingAllReimbursementStatus);
		} catch (Exception e) {
			objLogger.warn(sMethod + "Exception while getting all Reimbursement Status: [" + e.getMessage() + "]");
			throw new DatabaseException(csMsgDB_ErrorGettingAllReimbursementStatus);
		}
	}

	//
	// ###
	public List<ReimbursementType> getAllReimbursementType() throws DatabaseException {
		String sMethod = "getAllReimbursementType(): ";
		objLogger.trace(sMethod + "Entered");

		try {

			List<ReimbursementType> lstReimbType = objReimbTypeDAO.getAllRecords();
			objLogger.debug(sMethod + "lstReimbStatus: [" + lstReimbType.toString() + "]");
			return lstReimbType;

		} catch (SQLException e) {
			objLogger.warn(sMethod + "SQLException while getting all Reimbursement Type: [" + e.getMessage() + "]");
			throw new DatabaseException(csMsgDB_ErrorGettingReimbursementType);
		} catch (HibernateException e) {
			objLogger.warn(
					sMethod + "HibernateException while getting all Reimbursement Type: [" + e.getMessage() + "]");
			throw new DatabaseException(csMsgDB_ErrorGettingReimbursementType);
		} catch (Exception e) {
			objLogger.warn(sMethod + "Exception while getting all Reimbursement Type: [" + e.getMessage() + "]");
			throw new DatabaseException(csMsgDB_ErrorGettingReimbursementType);
		}
	}

	//
	// ###
	public List<UserRole> getAllUserRole() throws DatabaseException {
		String sMethod = "getAllUserRole(): ";
		objLogger.trace(sMethod + "Entered");

		try {

			List<UserRole> lstUserRole = objUserRoleDAO.getAllRecords();
			objLogger.debug(sMethod + "lstUserRole: [" + lstUserRole.toString() + "]");
			return lstUserRole;

		} catch (SQLException e) {
			objLogger.warn(sMethod + "SQLException while getting all User Role: [" + e.getMessage() + "]");
			throw new DatabaseException(csMsgDB_ErrorGettingUserRole);
		} catch (HibernateException e) {
			objLogger.warn(sMethod + "HibernateException while getting all User Role: [" + e.getMessage() + "]");
			throw new DatabaseException(csMsgDB_ErrorGettingUserRole);
		} catch (Exception e) {
			objLogger.warn(sMethod + "Exception while getting all User Role: [" + e.getMessage() + "]");
			throw new DatabaseException(csMsgDB_ErrorGettingUserRole);
		}
	}

	//
	// ###
	public ReimbursementStatus addReimbursementStatus(ReimbursementStatusDTO objReimbStatusDTO)
			throws DatabaseException, BadParameterException {
		String sMethod = "addReimbStatusTableStatus(): ";
		objLogger.trace(sMethod + "Entered");

		String sStatus = objReimbStatusDTO.getReimbStatus();
		String sStatusDesc = objReimbStatusDTO.getReimbStatusDescription();

		if ((sStatus.length() == 0) || (sStatusDesc.length() == 0)) {
			objLogger.debug(sMethod + "Invalid parameters received sStatus: [" + sStatus + "] sStatusDesc: ["
					+ sStatusDesc + "]");
			throw new BadParameterException(csMsgBadParamReimbStatus);
		}

		try {

			ReimbursementStatus objReimbStatus = objReimbStatusDAO.addRecord(objReimbStatusDTO);
			return objReimbStatus;

		} catch (SQLException e) {
			objLogger.warn(sMethod + "SQLException while adding Reimbursement Status: [" + e.getMessage() + "]");
			throw new DatabaseException(csMsgDB_ErrorAddingReimbursementStatus);
		} catch (HibernateException e) {
			objLogger.warn(sMethod + "HibernateException while adding Reimbursement Status: [" + e.getMessage() + "]");
			throw new DatabaseException(csMsgDB_ErrorAddingReimbursementStatus);
		} catch (Exception e) {
			objLogger.warn(sMethod + "Exception while adding Reimbursement Status: [" + e.getMessage() + "]");
			throw new DatabaseException(csMsgDB_ErrorAddingReimbursementStatus);
		}

	}


	//
	// ###
	public ReimbursementStatus getReimbursementStatusByName(String sStatusName)
			throws DatabaseException, BadParameterException {
		String sMethod = "getReimbursementStatusByName(): ";
		objLogger.trace(sMethod + "Entered");

		if (sStatusName.length() == 0) {
			objLogger.debug(sMethod + "Invalid parameters received sStatus: [" + sStatusName + "]");
			throw new BadParameterException(csMsgBadParamReimbStatus);
		}
		
		if(!Validate.isValidValueInArray(sStatusName, csReimbStatus)) {
			objLogger.debug(sMethod + "Invalid parameters received sStatus: [" + sStatusName + "] not a status define: [" + csReimbStatus.toString() + "]");
			throw new BadParameterException(csMsgBadParamReimbStatus);			
		}

		try {
			objLogger.debug(sMethod + "Getting status by name sStatus: [" + sStatusName + "]");
			ReimbursementStatus objReimbStatus = objReimbStatusDAO.getByRecordIdentifer(sStatusName);
			return objReimbStatus;

		} catch (SQLException e) {
			objLogger.warn(sMethod + "SQLException while getting Reimbursement Status: [" + e.getMessage() + "]");
			throw new DatabaseException(csMsgDB_ErrorGettingReimbursementStatus);
		} catch (HibernateException e) {
			objLogger.warn(sMethod + "HibernateException while getting Reimbursement Status: [" + e.getMessage() + "]");
			throw new DatabaseException(csMsgDB_ErrorGettingReimbursementStatus);
		} catch (Exception e) {
			objLogger.warn(sMethod + "Exception while getting Reimbursement Status: [" + e.getMessage() + "]");
			throw new DatabaseException(csMsgDB_ErrorGettingReimbursementStatus);
		}

	}


	
	//
	// ###
	public ReimbursementType addReimbursementType(ReimbursementTypeDTO objReimbTypeDTO)
			throws DatabaseException, BadParameterException {
		String sMethod = "addReimbStatusTableStatus(): ";
		objLogger.trace(sMethod + "Entered");

		String sType = objReimbTypeDTO.getReimbType();
		String sTypeDesc = objReimbTypeDTO.getReimbTypeDescription();

		if ((sType.length() == 0) || (sTypeDesc.length() == 0)) {
			objLogger.debug(
					sMethod + "Invalid parameters received sStatus: [" + sType + "] sStatusDesc: [" + sTypeDesc + "]");
			throw new BadParameterException(csMsgBadParamReimbType);
		}

		try {

			ReimbursementType objReimbType = objReimbTypeDAO.addRecord(objReimbTypeDTO);
			return objReimbType;

		} catch (SQLException e) {
			objLogger.warn(sMethod + "SQLException while adding Reimbursement Type: [" + e.getMessage() + "]");
			throw new DatabaseException(csMsgDB_ErrorAddingReimbursementType);
		} catch (HibernateException e) {
			objLogger.warn(sMethod + "HibernateException while adding Reimbursement Type: [" + e.getMessage() + "]");
			throw new DatabaseException(csMsgDB_ErrorAddingReimbursementType);
		} catch (Exception e) {
			objLogger.warn(sMethod + "Exception while adding Reimbursement Type: [" + e.getMessage() + "]");
			throw new DatabaseException(csMsgDB_ErrorAddingReimbursementType);
		}

	}

	//
	// ###
	public UserRole addUserRole(UserRoleDTO objUserRoleDTO) throws DatabaseException, BadParameterException {
		String sMethod = "addUserRole(): ";
		objLogger.trace(sMethod + "Entered");

		String sUserRole = objUserRoleDTO.getUserRole();
		String sUserRoleDesc = objUserRoleDTO.getUserRoleDescription();

		if ((sUserRole.length() == 0) || (sUserRoleDesc.length() == 0)) {
			objLogger.debug(sMethod + "Invalid parameters received sUserRole: [" + sUserRoleDesc + "] sUserRoleDesc: ["
					+ sUserRoleDesc + "]");
			throw new BadParameterException(csMsgBadParamUserRole);
		}

		objLogger.debug(sMethod + "objUserRoleDTO: [" + objUserRoleDTO.toString() + "]");
		try {

			UserRole objUserRole = objUserRoleDAO.addRecord(objUserRoleDTO);
			return objUserRole;

		} catch (SQLException e) {
			objLogger.warn(sMethod + "SQLException while adding User Role: [" + e.getMessage() + "]");
			throw new DatabaseException(csMsgDB_ErrorAddingUserRole);
		} catch (HibernateException e) {
			objLogger.warn(sMethod + "HibernateException while adding User Role: [" + e.getMessage() + "]");
			throw new DatabaseException(csMsgDB_ErrorAddingUserRole);
		} catch (Exception e) {
			objLogger.warn(sMethod + "Exception while adding User Role: [" + e.getMessage() + "]");
			throw new DatabaseException(csMsgDB_ErrorAddingUserRole);
		}

	}

}
