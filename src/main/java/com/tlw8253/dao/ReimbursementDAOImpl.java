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
	//This method will:
	//	Cannot change: record id, reimbSubmitted, reimbAuthor
	//	Can change: reimbAmount, reimbResolved, reimbDescription, reimbReceipt, reimbResolver, reimbStatus, reimbType
	//It it left to the service layer to apply additional business rules such as not allowing change once approved.
	//The record being edited will need to be shown to the user with it's database image.  All field will need to be 
	//return with values that had values.
	@Override
	public Reimbursement editRecord(AddOrEditDTO objAddOrEditDTO) throws SQLException {
		String sMethod = "\n\t editRecord(): ";
		objLogger.trace(sMethod + "Entered");

		objLogger.debug(sMethod + "objAddOrEditDTO: [" + objAddOrEditDTO.toString() + "]");
		
		//no change to csReimbTblReimbId
		//get csReimbTblReimbAmount
		double dReimAmount = objAddOrEditDTO.getDoubleDataElement(csReimbTblReimbAmount);	//if zero don't change
		//no change to csReimbTblReimbSubmitted
		//get csReimbTblReimbResolved
		Timestamp tsReimbResolved = objAddOrEditDTO.getTimestampDataElement(csReimbTblReimbResolved);
		//get csReimbTblReimbDescription
		String sReimbDescription = objAddOrEditDTO.getDataElement(csReimbTblReimbDescription); 
		//get csReimbTblReimbReceipt
		SerialBlob sbReimbReceipt = objAddOrEditDTO.getSerialBlobDataElement(csReimbTblReimbReceipt);
		//no change to csReimbTblReimbAuthorId
		//get csReimbTblReimbResolverId
		String sReimbResolver = objAddOrEditDTO.getDataElement(csReimbTblReimbResolverUName);  //if blank, then must be editting by author
		

		User objReimbResolver = new User();
		if(sReimbResolver.length()==ciUsernameLength) { //check if there is a resolver
			//get the solver by username
			UserDAOImpl objUserDAOImpl = new UserDAOImpl();
			objReimbResolver = objUserDAOImpl.getByRecordIdentifer(sReimbResolver);			
		}
		
		// get ReimbursementStatus object
		String sReimbStatus = objAddOrEditDTO.getDataElement(csReimbTblReimbStatus);
		ReimbursementStatusDAOImpl objReimbursementStatusDAOImpl = new ReimbursementStatusDAOImpl();
		ReimbursementStatus objReimbursementStatus = objReimbursementStatusDAOImpl.getByRecordIdentifer(sReimbStatus);

		// get ReimbursementStatus object
		String sReimbType = objAddOrEditDTO.getDataElement(csReimbTblReimbType);
		ReimbursementTypeDAOImpl objReimbursementTypeDAOImpl = new ReimbursementTypeDAOImpl();
		ReimbursementType objReimbursementType = objReimbursementTypeDAOImpl.getByRecordIdentifer(sReimbType);


		//since this is an edit of an existing record, the DTO should have the primary key
		int iReimbId = objAddOrEditDTO.getIntDataElement(csReimbTblReimbId);		

		SessionFactory sf = SessionFactorySingleton.getSessionFactory();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();

		try {

			String sHQL = "";
			sHQL = "FROM Reimbursement r WHERE r.reimbId = :reimbId";
			objLogger.debug(sMethod + "sHQL: [" + sHQL + "]" + " param: iReimbId: [" + iReimbId + "]");

			// need to get user Reimbursement to edit here and not through class method
			// getByRecordIdentifer()
			// due to the way sessions are open/closed within each methods. When using the
			// other class
			// method we loose connection to session object and cannot update.
			Reimbursement objReimbursementToEdit = (Reimbursement) session.createQuery(sHQL).setParameter("reimbId", iReimbId).getSingleResult();

			// will not update the record id, the company username, or the company email.
			// also password should be updated through separate process where old pwd and
			// new pwd are entered
			// set user object with

			// The only fields allowed to change
			objReimbursementToEdit.setReimbAmount(dReimAmount);
			
			if(sReimbResolver.length()==ciUsernameLength) { //check if there is a resolver
				objReimbursementToEdit.setReimbResolved(tsReimbResolved);
				objReimbursementToEdit.setReimbAuthor(objReimbResolver);
			}
			objReimbursementToEdit.setReimbDescription(sReimbDescription);
			objReimbursementToEdit.setReimbReceipt(sbReimbReceipt);
			objReimbursementToEdit.setReimbResolver(objReimbResolver);
			objReimbursementToEdit.setReimbStatus(objReimbursementStatus);
			objReimbursementToEdit.setReimbType(objReimbursementType);
			

			objLogger.debug(sMethod + "DB update with objReimbursementToEdit: [" + objReimbursementToEdit.toString() + "]");

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
