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
import com.tlw8253.model.Reimbursement;
import com.tlw8253.util.SessionFactorySingleton;


public class ReimbursementDAO implements GenericDAO<Reimbursement>, Constants{
	private Logger objLogger = LoggerFactory.getLogger(ReimbursementDAO.class);

	public ReimbursementDAO() {
		super();
	}

	@Override
	public List<Reimbursement> getAllRecords() throws SQLException {
		String sMethod = "getAllRecords(): ";
		objLogger.trace(sMethod + "Entered");

		// load a complete persistent objects into memory
		String sHQL = "FROM " + csHQL_ModelClassUser; //fully qualify class name in HQL
		
		SessionFactory sf = SessionFactorySingleton.getSessionFactory();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();

		List<Reimbursement> lstReimbursement = session.createQuery(sHQL).getResultList();
		objLogger.debug(sMethod + "lstReimbursementType: [" + lstReimbursement.toString() + "]");
		
		tx.commit();
		session.close();
		
		return lstReimbursement;
	}

	@Override
	public Reimbursement getByRecordId(int sRecordId) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Reimbursement getByRecordIdentifer(String sRecordIdentifier) throws SQLException, HibernateException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override	//
	public Reimbursement addRecord(AddOrEditDTO objAddOrEditDTO) throws SQLException, HibernateException {
		String sMethod = "addRecord(): ";
		objLogger.trace(sMethod + "Entered");

		objLogger.debug(sMethod + "objAddOrEditDTO: [" + objAddOrEditDTO.toString() + "]");
		
		SessionFactory sf = SessionFactorySingleton.getSessionFactory();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();

		//by this time the service layer would have validated the parameters
		double dReimbAmount = objAddOrEditDTO.getDoubleDataElement(csReimbTblReimbAmount);
		String sPassword = objAddOrEditDTO.getDataElement(csUserTblPassword);
		String sFirstName = objAddOrEditDTO.getDataElement(csUserTblFirstName);
		String sLastName = objAddOrEditDTO.getDataElement(csUsrTblLastName);
		String sEmail = objAddOrEditDTO.getDataElement(csUserTblEmail);
		String sRoleName = objAddOrEditDTO.getDataElement(csUserTblRoleName);
		
		//Reimbursement objReimbursement = new Reimbursement(sUsername, sPassword, sFirstName, sLastName, sEmail);
		
		//get Reimbursement Status and Type
		//UserRoleDAOImpl objUserRoleDAOImpl = new UserRoleDAOImpl();
		//UserRole objUserRole = objUserRoleDAOImpl.getByRecordIdentifer(sRoleName);
		//objUser.setUserRole(objUserRole);
		
		//objLogger.debug(sMethod + "objUserRole: [" + objUser.toString() + "]");
		
		//session.persist(objUser);
		
		tx.commit();
		session.close();

		return null;
	}

	@Override
	public Reimbursement editRecord(String sRecordIdentifier, AddOrEditDTO objGenericEditDTO)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteRecord(String sRecordIdentifier) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Reimbursement getLogin(String sUsername) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Reimbursement getLoginJDBC(String sUsername) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
