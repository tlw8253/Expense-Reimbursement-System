package com.tlw8253.dao;

import java.sql.SQLException;
import java.util.List;

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReimbursementStatus getByRecordId(int sRecordId) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReimbursementStatus getByRecordIdentifer(String sRecordIdentifier) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReimbursementStatus addRecord(AddOrEditDTO objAddOrEditDTO) throws SQLException {
		String sMethod = "addRecord(): ";
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
	public ReimbursementStatus editRecord(String sRecordIdentifier, AddOrEditDTO objGenericEditDTO)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteRecord(String sRecordIdentifier) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ReimbursementStatus getLogin(String sUsername) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReimbursementStatus getLoginJDBC(String sUsername) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
