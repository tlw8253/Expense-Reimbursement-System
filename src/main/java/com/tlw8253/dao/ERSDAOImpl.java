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
import com.tlw8253.model.User;
import com.tlw8253.util.SessionFactorySingleton;

public class ERSDAOImpl implements GenericDAO<User>, Constants {
	private Logger objLogger = LoggerFactory.getLogger(ERSDAOImpl.class);

	public ERSDAOImpl() {
		super();
	}

	@Override
	public User addRecord(AddOrEditDTO objGenericAddDTO) throws SQLException {
		String sMethod = "addRecord(): ";
		objLogger.trace(sMethod + "Entered");

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
	public List<User> getAllRecords() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getByRecordId(int sRecordId) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getByRecordIdentifer(String sRecordIdentifier) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User editRecord(String sRecordIdentifier, AddOrEditDTO objGenericEditDTO) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteRecord(String sRecordIdentifier) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public User getLogin(String sUsername) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getLoginJDBC(String sUsername) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}


}
