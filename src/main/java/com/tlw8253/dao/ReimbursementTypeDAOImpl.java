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
import com.tlw8253.model.ReimbursementType;
import com.tlw8253.util.SessionFactorySingleton;


public class ReimbursementTypeDAOImpl implements GenericDAO<ReimbursementType>, Constants{
	private Logger objLogger = LoggerFactory.getLogger(ReimbursementTypeDAOImpl.class);

	public ReimbursementTypeDAOImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<ReimbursementType> getAllRecords() throws SQLException {
		String sMethod = "getAllRecords(): ";
		objLogger.trace(sMethod + "Entered");

		// load a complete persistent objects into memory
		String sHQL = "FROM " + csHQL_ModelClassReimbType; //fully qualify class name in HQL
		
		SessionFactory sf = SessionFactorySingleton.getSessionFactory();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();

		List<ReimbursementType> lstReimbursementType = session.createQuery(sHQL).getResultList();
		objLogger.debug(sMethod + "lstReimbursementType: [" + lstReimbursementType.toString() + "]");
		
		tx.commit();
		session.close();
		
		return lstReimbursementType;
	}

	@Override
	public ReimbursementType getByRecordId(int sRecordId) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReimbursementType getByRecordIdentifer(String sRecordIdentifier) throws SQLException, HibernateException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReimbursementType addRecord(AddOrEditDTO objAddOrEditDTO) throws SQLException, HibernateException {
		String sMethod = "addRecord(): ";
		objLogger.trace(sMethod + "Entered");

		SessionFactory sf = SessionFactorySingleton.getSessionFactory();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();

		String sStatus = objAddOrEditDTO.getDataElement(csReimbTypeTblReimbType);
		String sStatusDesc = objAddOrEditDTO.getDataElement(csReimbTypeTblReimbTypeDesc);
		
		ReimbursementType objReimbStatus = new ReimbursementType(sStatus, sStatusDesc);
		
		session.persist(objReimbStatus);
		
		tx.commit();
		session.close();

		return objReimbStatus;
	}

	@Override
	public ReimbursementType editRecord(String sRecordIdentifier, AddOrEditDTO objGenericEditDTO)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteRecord(String sRecordIdentifier) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ReimbursementType getLogin(String sUsername) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReimbursementType getLoginJDBC(String sUsername) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
