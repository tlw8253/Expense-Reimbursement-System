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
import com.tlw8253.model.ReimbursementStatus;
import com.tlw8253.util.SessionFactorySingleton;


public class ReimbursementStatusDAOImpl implements GenericDAO<ReimbursementStatus>, Constants{
	private Logger objLogger = LoggerFactory.getLogger(ReimbursementStatusDAOImpl.class);

	public ReimbursementStatusDAOImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<ReimbursementStatus> getAllRecords() throws SQLException {
		String sMethod = "\n\t getAllRecords(): ";
		objLogger.trace(sMethod + "Entered");

		//String sSQL = "SELECT * FROM " + csDBReimbStatusTable + ";";
		// load a complete persistent objects into memory
		String sHQL = "FROM " + csHQL_ModelClassReimbStatus; //fully qualify class name in HQL
		
		SessionFactory sf = SessionFactorySingleton.getSessionFactory();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();

		List<ReimbursementStatus> lstReimbursementStatus = session.createQuery(sHQL).getResultList();
		objLogger.debug(sMethod + "lstReimbursementStatus: [" + lstReimbursementStatus.toString() + "]");
		
		tx.commit();
		session.close();
		
		return lstReimbursementStatus;
	}

	@Override
	public ReimbursementStatus getByRecordId(int iRecordId) throws SQLException {
		String sMethod = "\n\t getByRecordId(): ";
		objLogger.trace(sMethod + "Entered");
			
		SessionFactory sf = SessionFactorySingleton.getSessionFactory();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		
		String sHQL = "";

		sHQL = "FROM ReimbursementStatus rs WHERE rs.reimbStatusId = :reimbStatusId"; //this works with using setParameter
		objLogger.debug(sMethod + "sHQL: [" + sHQL + "]" + " param: iRecordId: [" + iRecordId +"]");
		
		try {
			ReimbursementStatus objReimbursementStatus = 
					(ReimbursementStatus) session.createQuery(sHQL)
					.setParameter("reimbStatusId", iRecordId)
					.getSingleResult();
			objLogger.debug(sMethod + "objReimbursementStatus: [" + objReimbursementStatus.toString() + "]");
			
			
			tx.commit();
			return objReimbursementStatus;
			
		}catch(Exception e) {
			objLogger.error(sMethod + "Exception: cause: [" + e.getCause() + "] class name [" + e.getClass().getName() + "] [" + e.toString() + "]");
			objLogger.error(sMethod + "Exception: message: [" + e.getMessage() + "]");	
			return null;
		}finally {
			session.close();
		}	

	}

	@Override
	public ReimbursementStatus getByRecordIdentifer(String sRecordIdentifier) throws SQLException, HibernateException {
		String sMethod = "\n\t getByRecordIdentifer(): ";
		objLogger.trace(sMethod + "Entered");
			
		SessionFactory sf = SessionFactorySingleton.getSessionFactory();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		
		String sHQL = "";

		sHQL = "FROM ReimbursementStatus rs WHERE rs.reimbStatus = :reimbStatus";
		objLogger.debug(sMethod + "sHQL: [" + sHQL + "]" + " param: sRecordIdentifier: [" + sRecordIdentifier +"]");
		
		try {
			ReimbursementStatus objReimbursementStatus = 
					(ReimbursementStatus) session.createQuery(sHQL)
					.setParameter("reimbStatus", sRecordIdentifier)
					.getSingleResult();
			objLogger.debug(sMethod + "objReimbursementStatus: [" + objReimbursementStatus.toString() + "]");			
			
			tx.commit();
			return objReimbursementStatus;
			
		}catch(Exception e) {
			objLogger.error(sMethod + "Error getting Reimbursement Status by sRecordIdentifier: [" + sRecordIdentifier + "]");
			objLogger.error(sMethod + "Exception: cause: [" + e.getCause() + "] class name [" + e.getClass().getName() + "] [" + e.toString() + "]");
			objLogger.error(sMethod + "Exception: message: [" + e.getMessage() + "]");	
			return null;
		}finally {
			session.close();
		}	
	}

	@Override
	public ReimbursementStatus addRecord(AddOrEditDTO objAddOrEditDTO) throws SQLException, HibernateException {
		String sMethod = "\n\t addRecord(): ";
		objLogger.trace(sMethod + "Entered");

		SessionFactory sf = SessionFactorySingleton.getSessionFactory();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();

		String sStatus = objAddOrEditDTO.getDataElement(csReimbStatusTblReimbStatus);
		String sStatusDesc = objAddOrEditDTO.getDataElement(csReimbStatusTblReimbStatusDesc);
		
		ReimbursementStatus objReimbStatus = new ReimbursementStatus(sStatus, sStatusDesc);
		
		session.persist(objReimbStatus);
		
		tx.commit();
		session.close();

		return objReimbStatus;
	}

	@Override
	public ReimbursementStatus editRecord(AddOrEditDTO objGenericEditDTO)
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
	public ReimbursementStatus getLogin(String sUsername) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
