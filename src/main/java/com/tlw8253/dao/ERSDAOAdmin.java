package com.tlw8253.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tlw8253.app.Constants;
import com.tlw8253.dto.AddOrEditDTO;
import com.tlw8253.model.ReimbursementStatus;
import com.tlw8253.util.SessionFactorySingleton;

public class ERSDAOAdmin implements GenericDAO<ReimbursementStatus>, Constants {
	private Logger objLogger = LoggerFactory.getLogger(ERSDAOAdmin.class);

	public ERSDAOAdmin() {
		super();
	}

	@Override
	public ReimbursementStatus addRecord(AddOrEditDTO objGenericAddDTO) throws SQLException {
		SessionFactory sf = SessionFactorySingleton.getSessionFactory();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
/*
		String username = objGenericAddDTO.getDataElement(csUserTblUsername);
		String password = objGenericAddDTO.getDataElement(csUserTblPassword);
		String firstName = objGenericAddDTO.getDataElement(csUserTblFirstName);
		String lastName = objGenericAddDTO.getDataElement(csUsrTblLastName);
		String email = objGenericAddDTO.getDataElement(csUserTblEmail);

		User objEmployee = new User(username, password, firstName, lastName, email);

		session.persist(objEmployee);
*/
		tx.commit();

		session.close();

		return null;
	}

	@Override
	public List<ReimbursementStatus> getAllRecords() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReimbursementStatus getByRecordId(int sRecordId) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReimbursementStatus getByRecordIdentifer(String sRecordIdentifier) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReimbursementStatus editRecord(String sRecordIdentifier, AddOrEditDTO objGenericEditDTO) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteRecord(String sRecordIdentifier) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ReimbursementStatus getLogin(String sUsername) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReimbursementStatus getLoginJDBC(String sUsername) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}


}
