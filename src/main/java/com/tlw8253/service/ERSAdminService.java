package com.tlw8253.service;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tlw8253.app.Constants;
import com.tlw8253.dao.ERSDAOImpl;
import com.tlw8253.dao.GenericDAO;
import com.tlw8253.dao.ReimbursementStatusDAOImpl;
import com.tlw8253.dto.ERSAdminDTO;
import com.tlw8253.dto.ERSDTO;
import com.tlw8253.dto.ReimbursementStatusDTO;
import com.tlw8253.exception.*;
import com.tlw8253.model.User;
import com.tlw8253.model.ReimbursementStatus;

public class ERSAdminService implements Constants {
	private Logger objLogger = LoggerFactory.getLogger(ERSAdminService.class);
	private GenericDAO<ReimbursementStatus> objReimbStatusDAO;

	public ERSAdminService() {
		this.objReimbStatusDAO = new ReimbursementStatusDAOImpl();
	}

	public ERSAdminService(GenericDAO<User> objMockedAdminDAO) {
//		this.objErsDAOHib = objMockedAdminDAO;
	}

	//
	// ###
	public User addNewEmployee(ERSDTO objERSDTO) throws DatabaseException {
		String sMethod = "addNewEmployee(): ";
		objLogger.trace(sMethod + "Entered");

		/*
		 * try {
		 * 
		 * }catch(SQLException objE) { }
		 * 
		 */
		return null;
	}

	public ReimbursementStatus addReimbursementStatus(ReimbursementStatusDTO objReimbStatusDTO)
			throws DatabaseException, BadParameterException {
		String sMethod = "addReimbStatusTableStatus(): ";
		objLogger.trace(sMethod + "Entered");

		String sStatus = objReimbStatusDTO.getReimbStatus();
		String sStatusDesc = objReimbStatusDTO.getReimbStatusDescription();

		if ((sStatus.length() < 6) || (sStatusDesc.length() == 0)) {
			objLogger.debug(sMethod + "Invalid parameters received sStatus: [" + sStatus + "] sStatusDesc: ["
					+ sStatusDesc + "]");
			throw new BadParameterException(csMsgBadParamReimbStatus);
		}

		try {
			ReimbursementStatus objReimbStatus = objReimbStatusDAO.addRecord(objReimbStatusDTO);
			return objReimbStatus;
		} catch (SQLException e) {
			objLogger
					.debug(sMethod + "SQLException while adding adding Reimbursement Status: [" + e.getMessage() + "]");
			throw new DatabaseException(csMsgDB_ErrorAddingReimbursementStatus);
		}
	}

}










