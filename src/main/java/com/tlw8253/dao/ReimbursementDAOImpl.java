package com.tlw8253.dao;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import javax.sql.rowset.serial.SerialBlob;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tlw8253.app.Constants;
import com.tlw8253.dto.AddOrEditDTO;
import com.tlw8253.model.Reimbursement;
import com.tlw8253.model.ReimbursementStatus;
import com.tlw8253.model.ReimbursementType;
import com.tlw8253.model.User;
import com.tlw8253.model.UserRole;
import com.tlw8253.util.SessionFactorySingleton;

public class ReimbursementDAOImpl implements GenericDAO<Reimbursement>, Constants {
	private Logger objLogger = LoggerFactory.getLogger(ReimbursementDAOImpl.class);
	private GenericDAO<User> objUserDAO;

	public ReimbursementDAOImpl() {
		this.objUserDAO = new UserDAOImpl();
	}

	//
	// ###
	@Override // 20210820 completed
	public List<Reimbursement> getAllRecords() throws SQLException {
		String sMethod = "\n\t getAllRecords(): ";
		objLogger.trace(sMethod + "Entered");

		// load a complete persistent objects into memory
		String sHQL = "FROM " + csHQL_ModelClassUser; // fully qualify class name in HQL

		SessionFactory sf = SessionFactorySingleton.getSessionFactory();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();

		try {
			List<Reimbursement> lstReimbursement = (List<Reimbursement>) session.createQuery(sHQL).getResultList();
			objLogger.debug(sMethod + "lstReimbursement: [" + lstReimbursement.toString() + "]");
			tx.commit();
			return lstReimbursement;
		} catch (Exception e) {
			objLogger.error(sMethod + "Exception: cause: [" + e.getCause() + "] class name [" + e.getClass().getName()
					+ "] [" + e.toString() + "]");
			objLogger.error(sMethod + "Exception: message: [" + e.getMessage() + "]");
			return null;
		} finally {
			session.close();
		}

	}

	@Override
	public Reimbursement getByRecordId(int iRecordId) throws SQLException {
		String sMethod = "\n\t getByRecordId(): ";
		objLogger.trace(sMethod + "Entered");

		SessionFactory sf = SessionFactorySingleton.getSessionFactory();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();

		String sHQL = "";
		sHQL = "FROM Reimbursement r WHERE r.reimbId = :reimbId";
		objLogger.debug(sMethod + "sHQL: [" + sHQL + "]" + " param: iRecordId: [" + iRecordId + "]");

		try {
			Reimbursement objReimbursement = (Reimbursement) session.createQuery(sHQL)
					.setParameter("reimbId", iRecordId).getSingleResult();
			objLogger.debug(sMethod + "objUser: [" + objReimbursement.toString() + "]");

			// Hibernate automatically return the UserRole object when getting the User.
			// Do not need to do a separate read.

			tx.commit();
			return objReimbursement;

		} catch (Exception e) {
			objLogger.error(sMethod + "Exception: cause: [" + e.getCause() + "] class name [" + e.getClass().getName()
					+ "] [" + e.toString() + "]");
			objLogger.error(sMethod + "Exception: toString: [" + e.toString() + " message: [" + e.getMessage() + "]");
			return null;
		} finally {
			session.close();
		}
	}

	@Override
	public Reimbursement getByRecordIdentifer(String sRecordIdentifier) throws SQLException, HibernateException {
		// TODO Auto-generated method stub
		return null;
	}

	// method specific for this <T> model
	@Override		//20210821 completed
	public List<Reimbursement> getListByRecordIdentifer(int iListId, String sRecordIdentifier)
			throws SQLException, HibernateException {
		String sMethod = "\n\t getListByRecordIdentifer(iListId): ";
		objLogger.trace(sMethod + "Entered");

		if (iListId == ciReimbRecByIdentifierAuthor) {
			User objUserAuthor = objUserDAO.getByRecordIdentifer(sRecordIdentifier);
			objLogger.debug(sMethod + "objUserAuthor: [" + objUserAuthor.toString() + "]");

			SessionFactory sf = SessionFactorySingleton.getSessionFactory();
			Session session = sf.openSession();
			Transaction tx = session.beginTransaction();
			String sHQL = "";
			sHQL = "FROM Reimbursement r WHERE r.reimbAuthor = :reimbAuthor";
			objLogger.debug(sMethod + "sHQL: [" + sHQL + "]" + " param: reimbAuthor: [" + objUserAuthor + "]");

			try {
				List<Reimbursement> lstReimbursement = (List<Reimbursement>) session.createQuery(sHQL)
						.setParameter("reimbAuthor", objUserAuthor).getResultList();
				tx.commit();
				return lstReimbursement;

			} catch (Exception e) {
				objLogger.error(sMethod + "Exception: cause: [" + e.getCause() + "] class name ["
						+ e.getClass().getName() + "] [" + e.toString() + "]");
				objLogger.error(
						sMethod + "Exception: toString: [" + e.toString() + " message: [" + e.getMessage() + "]");
				return null;
			} finally {
				session.close();
			}

		}

		return null;
	}

	@Override // 20210820 Reimbursement addRecord working
	public Reimbursement addRecord(AddOrEditDTO objAddOrEditDTO) throws SQLException, HibernateException {
		String sMethod = "\n\t addRecord(): ";
		objLogger.trace(sMethod + "Entered");

		objLogger.debug(sMethod + "objAddOrEditDTO: [" + objAddOrEditDTO.toString() + "]");

		SessionFactory sf = SessionFactorySingleton.getSessionFactory();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();

		// by this time the service layer would have validated the parameters
		double dReimbAmount = objAddOrEditDTO.getDoubleDataElement(csReimbTblReimbAmount);
		Timestamp tsReimbSubmitted = objAddOrEditDTO.getTimestampDataElement(csReimbTblReimbSubmitted);
		Timestamp tsReimbResolved = objAddOrEditDTO.getTimestampDataElement(csReimbTblReimbResolved);
		String sReimbDescription = objAddOrEditDTO.getDataElement(csReimbTblReimbDescription);
		SerialBlob sbReimbReceipt = objAddOrEditDTO.getSerialBlobDataElement(csReimbTblReimbReceipt);

		// now check get dependent records from the database.
		int iAuthorUserId = objAddOrEditDTO.getIntDataElement(csReimbTblReimbAuthorId);
		objLogger.debug(sMethod + "Getting user object with iAuthorUserId: [" + iAuthorUserId + "]");
		UserDAOImpl objUserDAO = new UserDAOImpl();
		User objAuthor = objUserDAO.getByRecordId(iAuthorUserId);
		objLogger.debug(sMethod + "objUser: [" + objAuthor.toString() + "]");

		// There is no User Resolver object at this time

		// get the Reimbursement status by the name
		String sReimbStatus = objAddOrEditDTO.getDataElement(csReimbTblReimbStatus);
		objLogger.debug(sMethod + "Getting reimburstment status with sReimbStatus: [" + sReimbStatus + "]");
		ReimbursementStatusDAOImpl objReimbursementStatusDAOImpl = new ReimbursementStatusDAOImpl();
		ReimbursementStatus objReimbursementStatus = objReimbursementStatusDAOImpl.getByRecordIdentifer(sReimbStatus);
		objLogger.debug(sMethod + "objReimbursementStatus: [" + objReimbursementStatus.toString() + "]");

		// get the Reimbursement type by the name
		String sReimbType = objAddOrEditDTO.getDataElement(csReimbTblReimbType);
		objLogger.debug(sMethod + "Getting reimburstment type with sReimbType: [" + sReimbType + "]");
		ReimbursementTypeDAOImpl objReimbursementTypeDAOImpl = new ReimbursementTypeDAOImpl();
		ReimbursementType objReimbursementType = objReimbursementTypeDAOImpl.getByRecordIdentifer(sReimbType);
		objLogger.debug(sMethod + "objReimbursementType: [" + objReimbursementType.toString() + "]");

		// set some values through the constructor
		Reimbursement objReimbursement = new Reimbursement(dReimbAmount, tsReimbSubmitted, tsReimbResolved,
				sReimbDescription, sbReimbReceipt);
		// set some object through setters
		objReimbursement.setReimbAuthor(objAuthor);
		objReimbursement.setReimbStatus(objReimbursementStatus);
		objReimbursement.setReimbType(objReimbursementType);

		objLogger.debug(sMethod + "Adding objReimbursement: [" + objReimbursement.toString() + "]");

		try {
			session.persist(objReimbursement);
			tx.commit();
			return objReimbursement;
		} catch (Exception e) {
			objLogger.error(sMethod + "Exception: cause: [" + e.getCause() + "] class name [" + e.getClass().getName()
					+ "] [" + e.toString() + "]");
			objLogger.error(sMethod + "Exception: message: [" + e.getMessage() + "]");
			return null;
		} finally {
			session.close();
		}
	}

	//
	//###
	//Need to let the caller deceide what values are updated for an Author or Resolver.
	@Override
	public Reimbursement editRecord(AddOrEditDTO objAddOrEditDTO) throws SQLException {
		String sMethod = "\n\t editRecord(): ";
		objLogger.trace(sMethod + "Entered");

		objLogger.debug(sMethod + "objAddOrEditDTO: [" + objAddOrEditDTO.toString() + "]");
		
		//The DTO should contained all the changed and unchanged elements and ids
		//no logic here to exclude anything from the update object
		
		//get the program defined database objects ids from the DTO
		int iReimbId = objAddOrEditDTO.getIntDataElement(csReimbTblReimbId);	//id of the record to update
		objLogger.debug(sMethod + "iReimbId: [" + iReimbId + "]");
		
		int iReimbAuthorId = objAddOrEditDTO.getIntDataElement(csReimbTblReimbAuthorId);		//
		objLogger.debug(sMethod + "iReimbAuthorId: [" + iReimbAuthorId + "]");
		int iReimbResolverId = objAddOrEditDTO.getIntDataElement(csReimbTblReimbResolverId);	//
		objLogger.debug(sMethod + "iReimbResolverId: [" + iReimbResolverId + "]");
		int iReimbStatusId = objAddOrEditDTO.getIntDataElement(csReimbTblReimbStatusId);		//
		objLogger.debug(sMethod + "iReimbStatusId: [" + iReimbStatusId + "]");
		int iReimbTypeId = objAddOrEditDTO.getIntDataElement(csReimbTblReimbTypeId);
		objLogger.debug(sMethod + "iReimbTypeId: [" + iReimbTypeId + "]");

		//get the dependent objects before the record to update due to how sessions are handle
		UserDAOImpl objUserDAOImpl = new UserDAOImpl();
		User objAuthor = objUserDAOImpl.getByRecordId(iReimbAuthorId);
		objLogger.debug(sMethod + "retreived author object: [" + objAuthor.toString() + "]");
		
		User objResolver = objUserDAOImpl.getByRecordId(iReimbResolverId);
		objLogger.debug(sMethod + "retreived resolver object: [" + objResolver.toString() + "]");		
		
		ReimbursementStatusDAOImpl objReimbursementStatusDAOImpl = new ReimbursementStatusDAOImpl();
		ReimbursementStatus objReimbursementStatus = objReimbursementStatusDAOImpl.getByRecordId(iReimbStatusId);
		objLogger.debug(sMethod + "retreived status object: [" + objReimbursementStatus.toString() + "]");

		ReimbursementTypeDAOImpl objReimbursementTypeDAOImpl = new ReimbursementTypeDAOImpl();
		ReimbursementType objReimbursementType = objReimbursementTypeDAOImpl.getByRecordId(iReimbTypeId);
		objLogger.debug(sMethod + "retrieved reimb type object: [" + objReimbursementType.toString() + "]");

		
		//now get the Reimbursement from the database so we can update changed values

		SessionFactory sf = SessionFactorySingleton.getSessionFactory();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();

		try {

			String sHQL = "";
			sHQL = "FROM Reimbursement r WHERE r.reimbId = :reimbId";
			objLogger.debug(sMethod + "sHQL: [" + sHQL + "]" + " param: iReimbId: [" + iReimbId + "]");
			Reimbursement objReimbursementToEdit = (Reimbursement) session.createQuery(sHQL).setParameter("reimbId", iReimbId).getSingleResult();
			objLogger.debug(sMethod + "DB update with objReimbursementToEdit: [" + objReimbursementToEdit.toString() + "]");
			
			//we have the record locked from the data base read, now update fields from the DTO except the record id
			objReimbursementToEdit.setReimbAmount(objAddOrEditDTO.getDoubleDataElement(csReimbTblReimbAmount));
			objReimbursementToEdit.setReimbSubmitted(objAddOrEditDTO.getTimestampDataElement(csReimbTblReimbSubmitted));
			objReimbursementToEdit.setReimbSubmitted(objAddOrEditDTO.getTimestampDataElement(csReimbTblReimbSubmitted));
			objReimbursementToEdit.setReimbDescription(objAddOrEditDTO.getDataElement(csReimbTblReimbDescription));
			objReimbursementToEdit.setReimbReceipt(objAddOrEditDTO.getSerialBlobDataElement(csReimbTblReimbReceipt));
			objReimbursementToEdit.setReimbResolverMsg(objAddOrEditDTO.getDataElement(csReimbTblReimbResolverMsg));
			objReimbursementToEdit.setReimbAuthor(objAuthor);
			objReimbursementToEdit.setReimbResolver(objResolver);
			objReimbursementToEdit.setReimbStatus(objReimbursementStatus);
			objReimbursementToEdit.setReimbType(objReimbursementType);		

			objLogger.debug(sMethod + "Values updated from the DTO for objReimbursementToEdit: [" + objReimbursementToEdit.toString() + "]");
			
			session.persist(objReimbursementToEdit);
			tx.commit();

			return objReimbursementToEdit;

		} catch (Exception e) {
			objLogger.error(sMethod + "Exception: cause: [" + e.getCause() + "] class name [" + e.getClass().getName()
					+ "] [" + e.toString() + "]");
			objLogger.error(sMethod + "Exception: toString: [" + e.toString() + " message: [" + e.getMessage() + "]");
			return null;
		} finally {
			session.close();
		}
		
		
		
	}

	@Override
	public boolean deleteRecord(String sRecordIdentifier) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Reimbursement getLogin(String sUsername, String sPassword) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
