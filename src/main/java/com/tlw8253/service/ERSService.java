package com.tlw8253.service;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tlw8253.app.Constants;
import com.tlw8253.dao.ERSDAOImpl;
import com.tlw8253.dao.GenericDAO;
import com.tlw8253.exception.DatabaseException;
import com.tlw8253.exception.*;
import com.tlw8253.model.General;

public class ERSService implements Constants {
	private Logger objLogger = LoggerFactory.getLogger(ERSService.class);
//	private GeneralDAOImpl objGenericDAOImpl;
	private GenericDAO<General> objGeneralDAO;

	public ERSService() {
		this.objGeneralDAO = new ERSDAOImpl();
	}

	public ERSService(GenericDAO<General> objMockedGeneralDAO) {
		this.objGeneralDAO = objMockedGeneralDAO;
	}

	
	public List<General> getReturnGeneral() throws DatabaseException, RecordNotFoundException {
		String sMethod = "getReturnGeneral(): ";
		objLogger.trace(sMethod + "Entered");

		try {
			List<General> lstGeneral = objGeneralDAO.getAllRecords();
			if (lstGeneral.size() == 0) {
				objLogger.debug(sMethod + csMsgGeneralRecordNotFound);
				throw new RecordNotFoundException(csMsgGeneralRecordNotFound);
			}
			
			objLogger.debug(sMethod + "lstGeneral: [" + lstGeneral.toString() + "]");
			return lstGeneral;
		} catch (SQLException objE) {
			objLogger.debug(sMethod + "SQLException: [" + objE.getMessage() + "]");
			throw new DatabaseException(csMsgDB_ErrorGettingAllGeneral);
		}
	}

}
