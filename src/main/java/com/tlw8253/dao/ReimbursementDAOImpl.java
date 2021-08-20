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
import com.tlw8253.util.SessionFactorySingleton;

public class ReimbursementDAOImpl implements GenericDAO<Reimbursement>, Constants {
	private Logger objLogger = LoggerFactory.getLogger(ReimbursementDAOImpl.class);

	public ReimbursementDAOImpl() {
		super();
	}

	@Override
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
			objLogger.debug(sMethod + "lstReimbursementType: [" + lstReimbursement.toString() + "]");
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
	public Reimbursement getByRecordId(int sRecordId) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Reimbursement getByRecordIdentifer(String sRecordIdentifier) throws SQLException, HibernateException {
		// TODO Auto-generated method stub
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

	@Override
	public Reimbursement editRecord(AddOrEditDTO objGenericEditDTO) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteRecord(String sRecordIdentifier) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Reimbursement getLogin(String sUsername) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}


}
