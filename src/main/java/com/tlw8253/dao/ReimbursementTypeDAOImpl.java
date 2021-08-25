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
		String sMethod = "\n\t getAllRecords(): ";
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
	public ReimbursementType getByRecordId(int iRecordId) throws SQLException {
		String sMethod = "\n\t getByRecordId(): ";
		objLogger.trace(sMethod + "Entered");
			
		SessionFactory sf = SessionFactorySingleton.getSessionFactory();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		
		String sHQL = "";

		sHQL = "FROM ReimbursementType rt WHERE rt.reimbTypeId = :reimbTypeId"; //this works with using setParameter
		objLogger.debug(sMethod + "sHQL: [" + sHQL + "]" + " param: iRecordId: [" + iRecordId +"]");
		
		try {
			ReimbursementType ojbReimbursementType = 
					(ReimbursementType) session.createQuery(sHQL)
					.setParameter("reimbTypeId", iRecordId)
					.getSingleResult();
			objLogger.debug(sMethod + "ojbReimbursementType: [" + ojbReimbursementType.toString() + "]");
			
			
			tx.commit();
			return ojbReimbursementType;
			
		}catch(Exception e) {
			objLogger.error(sMethod + "Exception: cause: [" + e.getCause() + "] class name [" + e.getClass().getName() + "] [" + e.toString() + "]");
			objLogger.error(sMethod + "Exception: message: [" + e.getMessage() + "]");	
			return null;
		}finally {
			session.close();
		}	
	}

	@Override
	public ReimbursementType getByRecordIdentifer(String sRecordIdentifier) throws SQLException, HibernateException {
		String sMethod = "\n\t getByRecordIdentifer(): ";
		objLogger.trace(sMethod + "Entered");
			
		SessionFactory sf = SessionFactorySingleton.getSessionFactory();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		
		String sHQL = "";

		sHQL = "FROM ReimbursementType rt WHERE rt.reimbType = :reimbType";
		objLogger.debug(sMethod + "sHQL: [" + sHQL + "]" + " param: sRecordIdentifier: [" + sRecordIdentifier +"]");
		
		try {
			ReimbursementType objReimbursementType = 
					(ReimbursementType) session.createQuery(sHQL)
					.setParameter("reimbType", sRecordIdentifier)
					.getSingleResult();
			objLogger.debug(sMethod + "objReimbursementType: [" + objReimbursementType.toString() + "]");			
			
			tx.commit();
			return objReimbursementType;
			
		}catch(Exception e) {
			objLogger.error(sMethod + "Error getting Reimbursement Type by sRecordIdentifier: [" + sRecordIdentifier + "]");	
			objLogger.error(sMethod + "Exception: cause: [" + e.getCause() + "] class name [" + e.getClass().getName() + "] [" + e.toString() + "]");
			objLogger.error(sMethod + "Exception: message: [" + e.getMessage() + "]");	
			return null;
		}finally {
			session.close();
		}	
	}

	@Override
	public ReimbursementType addRecord(AddOrEditDTO objAddOrEditDTO) throws SQLException, HibernateException {
		String sMethod = "\n\t addRecord(): ";
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
	public ReimbursementType editRecord(AddOrEditDTO objGenericEditDTO)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteRecord(String sRecordIdentifier) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ReimbursementType getLogin(String sUsername, String sPassword) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ReimbursementType> getListByRecordIdentifer(int iListId, String sRecordIdentifier) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
