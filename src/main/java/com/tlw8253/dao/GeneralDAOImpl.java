package com.tlw8253.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tlw8253.app.Constants;
import com.tlw8253.dto.AddOrEditDTO;
import com.tlw8253.model.General;
import com.tlw8253.util.ConnectionUtility;

public class GeneralDAOImpl implements GenericDAO<General>, Constants {
	private Logger objLogger = LoggerFactory.getLogger(GeneralDAOImpl.class);

	public GeneralDAOImpl() {
		super();
	}

	@Override
	public List<General> getAllRecords() throws SQLException {
		String sMethod = "getAllRecords(): ";
		objLogger.trace(sMethod + "Entered");

		List<General> lstGeneral = new ArrayList<>(10);
		
		try (Connection conConnection = ConnectionUtility.getConnection()) {
			Statement objStatement = conConnection.createStatement();
			String sSQL = "SELECT * FROM " + csShellTable;
			objLogger.debug(sMethod + "sSQL statement: [" + sSQL + "]");

			ResultSet objResultSet = objStatement.executeQuery(sSQL);
			while (objResultSet.next()) {// data exists in the results set
				int iShellId = objResultSet.getInt(csShellTblShellId);
				String sShellName = objResultSet.getString(csShellTblShellName);
				int iShellInt = objResultSet.getInt(csShellTblShellInt);
				double dShellDouble = objResultSet.getDouble(csShellTblShellDbl);
				boolean bShellBoolean = objResultSet.getBoolean(csShellTblShellBool);

				General objGeneral = new General(sShellName, iShellInt, dShellDouble, bShellBoolean);
				objGeneral.setRecordId(iShellId);
				objLogger.debug(sMethod + "Add general object to list: [" + objGeneral.toString() + "]");
				lstGeneral.add(objGeneral);
				
			}
		}
		
		objLogger.debug(sMethod + "lstNonSpecificModel: [" + lstGeneral.toString() + "]");
		return lstGeneral;
	}

	@Override
	public General getByRecordIdentifer(String sRecordIdentifier) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public General addRecord(AddOrEditDTO objGenericAddDTO) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void deleteRecord(String sRecordIdentifier) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public General getByRecordId(int sRecordId) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public General editRecord(String sRecordIdentifier, AddOrEditDTO objGenericEditDTO) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
