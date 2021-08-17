package com.tlw8253.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tlw8253.app.Constants;
import com.tlw8253.dto.AddOrEditDTO;
import com.tlw8253.model.EmployeeJDBC;
import com.tlw8253.util.ConnectionUtility;

public class ERSDAOImpl implements GenericDAO<EmployeeJDBC>, Constants {
	private Logger objLogger = LoggerFactory.getLogger(ERSDAOImpl.class);

	public ERSDAOImpl() {
		super();
	}

	@Override
	public List<EmployeeJDBC> getAllRecords() throws SQLException {
		String sMethod = "getAllRecords(): ";
		objLogger.trace(sMethod + "Entered");
		
/*		

		List<Employee> lstGeneral = new ArrayList<>(10);
		
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

				Employee objGeneral = new Employee(sShellName, iShellInt, dShellDouble, bShellBoolean);
				objGeneral.setRecordId(iShellId);
				objLogger.debug(sMethod + "Add general object to list: [" + objGeneral.toString() + "]");
				lstGeneral.add(objGeneral);
				
			}
		}
		
		objLogger.debug(sMethod + "lstNonSpecificModel: [" + lstGeneral.toString() + "]");
		return lstGeneral;
*/
		return null;
	}

	@Override
	public EmployeeJDBC getByRecordIdentifer(String sRecordIdentifier) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EmployeeJDBC addRecord(AddOrEditDTO objGenericAddDTO) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void deleteRecord(String sRecordIdentifier) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public EmployeeJDBC getByRecordId(int sRecordId) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EmployeeJDBC editRecord(String sRecordIdentifier, AddOrEditDTO objGenericEditDTO) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EmployeeJDBC getLoginJDBC(String sUsername) throws SQLException {
		String sMethod = "getLogin(): ";

		try (Connection conConnection = ConnectionUtility.getConnection()) {
			String sSQL = "SELECT * FROM " + csDBEmployeeTable + " WHERE " + csEmployeeTblUsername + " = ?";
			objLogger.debug(sMethod + "sSQL statement: [" + sSQL + "]");
			
			PreparedStatement objPreparedStatmnt = conConnection.prepareStatement(sSQL);
			objPreparedStatmnt.setString(1, sUsername); 
			objLogger.debug(sMethod + "objPreparedStatmnt: [" + objPreparedStatmnt.toString() + "]");
			
			// Execute the query
			ResultSet objResultSet = objPreparedStatmnt.executeQuery();
			if (objResultSet.next()) {
				String sPassword = objResultSet.getString(csEmployeeTblPassword);
				String sFirstName = objResultSet.getString(csEmployeeTblFirstName);
				String sLastName = objResultSet.getString(csEmployeeTblLastName);
				String sEmail = objResultSet.getString(csEmployeeTblEmail);
				
				EmployeeJDBC objEmployee = new EmployeeJDBC(sUsername, sPassword, sFirstName, sLastName, sEmail);
				objLogger.debug(sMethod + "objEmployee: [" + objEmployee.toString() + "]");
				return objEmployee;
				
			}else {
				objLogger.debug(sMethod + "Employee with username: [" + sUsername + "] not found in database.");
				return null;
			}


		}

		
		
		
		
	}

	@Override
	public EmployeeJDBC getLogin(String sUsername) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
