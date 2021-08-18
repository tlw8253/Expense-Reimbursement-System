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
import com.tlw8253.model.UserRole;
import com.tlw8253.util.SessionFactorySingleton;


public class UserRoleDAOImpl implements GenericDAO<UserRole>, Constants{
	private Logger objLogger = LoggerFactory.getLogger(UserRoleDAOImpl.class);

	public UserRoleDAOImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<UserRole> getAllRecords() throws SQLException {
		String sMethod = "getAllRecords(): ";
		objLogger.trace(sMethod + "Entered");

		// load a complete persistent objects into memory
		String sHQL = "FROM " + csHQL_ModelClassUsrRole; //fully qualify class name in HQL
		
		SessionFactory sf = SessionFactorySingleton.getSessionFactory();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();

		List<UserRole> lstUserRole = session.createQuery(sHQL).getResultList();
		objLogger.debug(sMethod + "lstReimbursementType: [" + lstUserRole.toString() + "]");
		
		tx.commit();
		session.close();
		
		return lstUserRole;
	}

	@Override
	public UserRole getByRecordId(int sRecordId) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserRole getByRecordIdentifer(String sRecordIdentifier) throws SQLException, HibernateException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserRole addRecord(AddOrEditDTO objAddOrEditDTO) throws SQLException, HibernateException {
		String sMethod = "addRecord(): ";
		objLogger.trace(sMethod + "Entered");

		objLogger.debug(sMethod + "objAddOrEditDTO: [" + objAddOrEditDTO.toString() + "]");
		
		SessionFactory sf = SessionFactorySingleton.getSessionFactory();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();

		String sStatus = objAddOrEditDTO.getDataElement(csUserRolesTblUserRole);
		String sStatusDesc = objAddOrEditDTO.getDataElement(csUserRolesTblUserRoleDesc);
		
		UserRole objUserRole = new UserRole(sStatus, sStatusDesc);
		
		objLogger.debug(sMethod + "objUserRole: [" + objUserRole.toString() + "]");
		
		session.persist(objUserRole);
		
		tx.commit();
		session.close();

		return objUserRole;
	}

	@Override
	public UserRole editRecord(String sRecordIdentifier, AddOrEditDTO objGenericEditDTO)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteRecord(String sRecordIdentifier) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public UserRole getLogin(String sUsername) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserRole getLoginJDBC(String sUsername) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
