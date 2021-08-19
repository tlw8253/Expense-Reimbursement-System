package com.tlw8253.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tlw8253.app.Constants;
import com.tlw8253.dto.AddOrEditDTO;
import com.tlw8253.model.User;
import com.tlw8253.model.UserRole;
//import com.tlw8253.model.UserRole;
import com.tlw8253.util.SessionFactorySingleton;


public class UserDAOImpl implements GenericDAO<User>, Constants{
	private Logger objLogger = LoggerFactory.getLogger(UserDAOImpl.class);

	public UserDAOImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<User> getAllRecords() throws SQLException {
		String sMethod = "getAllRecords(): ";
		objLogger.trace(sMethod + "Entered");

		// load a complete persistent objects into memory
		String sHQL = "FROM " + csHQL_ModelClassUser; //fully qualify class name in HQL
		
		SessionFactory sf = SessionFactorySingleton.getSessionFactory();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();

		List<User> lstUser = session.createQuery(sHQL).getResultList();
		objLogger.debug(sMethod + "lstReimbursementType: [" + lstUser.toString() + "]");
		
		tx.commit();
		session.close();
		
		return lstUser;
	}

	@Override
	public User getByRecordId(int sRecordId) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getByRecordIdentifer(String sRecordIdentifier) throws SQLException, HibernateException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override	//20210819 1223 working method added user using Admin drive through service layer
	public User addRecord(AddOrEditDTO objAddOrEditDTO) throws SQLException, HibernateException {
		String sMethod = "addRecord(): ";
		objLogger.trace(sMethod + "Entered");

		objLogger.debug(sMethod + "objAddOrEditDTO: [" + objAddOrEditDTO.toString() + "]");
		
		SessionFactory sf = SessionFactorySingleton.getSessionFactory();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();

		//by this time the service layer would have validated the parameters
		String sUsername = objAddOrEditDTO.getDataElement(csUserTblUsername);
		String sPassword = objAddOrEditDTO.getDataElement(csUserTblPassword);
		String sFirstName = objAddOrEditDTO.getDataElement(csUserTblFirstName);
		String sLastName = objAddOrEditDTO.getDataElement(csUsrTblLastName);
		String sEmail = objAddOrEditDTO.getDataElement(csUserTblEmail);
		String sRoleName = objAddOrEditDTO.getDataElement(csUserTblRoleName);
		
		User objUser = new User(sUsername, sPassword, sFirstName, sLastName, sEmail);
		
		//get UserRole object
		UserRoleDAOImpl objUserRoleDAOImpl = new UserRoleDAOImpl();
		UserRole objUserRole = objUserRoleDAOImpl.getByRecordIdentifer(sRoleName);
		objUser.setUserRole(objUserRole);
		
		objLogger.debug(sMethod + "objUserRole: [" + objUser.toString() + "]");
		
		session.persist(objUser);
		
		tx.commit();
		session.close();

		return objUser;
	}

	@Override
	public User editRecord(String sRecordIdentifier, AddOrEditDTO objGenericEditDTO)
			throws SQLException {
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
