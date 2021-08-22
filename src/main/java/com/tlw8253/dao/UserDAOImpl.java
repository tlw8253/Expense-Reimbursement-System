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

public class UserDAOImpl implements GenericDAO<User>, Constants {
	private Logger objLogger = LoggerFactory.getLogger(UserDAOImpl.class);

	public UserDAOImpl() {
		super();
	}

	//
	// ###
	@Override // 20210820 completed
	public List<User> getAllRecords() throws SQLException {
		String sMethod = "\n\t getAllRecords(): ";
		objLogger.trace(sMethod + "Entered");

		// load a complete persistent objects into memory
		String sHQL = "FROM " + csHQL_ModelClassUser; // fully qualify class name in HQL

		SessionFactory sf = SessionFactorySingleton.getSessionFactory();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();

		try {
			List<User> lstUser = session.createQuery(sHQL).getResultList();
			objLogger.debug(sMethod + "lstReimbursementType: [" + lstUser.toString() + "]");
			tx.commit();
			return lstUser;
		} catch (Exception e) {
			objLogger.error(sMethod + "Exception: cause: [" + e.getCause() + "] class name [" + e.getClass().getName()
					+ "] [" + e.toString() + "]");
			objLogger.error(sMethod + "Exception: toString: [" + e.toString() + " message: [" + e.getMessage() + "]");
			return null;
		} finally {
			session.close();
		}

	}

	//
	// ###
	@Override // 20210820 completed
	public User getByRecordId(int iRecordId) throws SQLException {
		String sMethod = "\n\t getByRecordId(): ";
		objLogger.trace(sMethod + "Entered");

		SessionFactory sf = SessionFactorySingleton.getSessionFactory();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();

		String sHQL = "";
		// sHQL = "FROM User u WHERE u.userId = '" + iRecordId + "'"; //this works
		// without using setParameter
		sHQL = "FROM User u WHERE u.userId = :userId"; // this works with using setParameter
		objLogger.debug(sMethod + "sHQL: [" + sHQL + "]" + " param: iRecordId: [" + iRecordId + "]");

		try {
			User objUser = (User) session.createQuery(sHQL).setParameter("userId", iRecordId).getSingleResult();
			objLogger.debug(sMethod + "objUser: [" + objUser.toString() + "]");

			// Hibernate automatically return the UserRole object when getting the User.
			// Do not need to do a separate read.

			tx.commit();
			return objUser;

		} catch (Exception e) {
			objLogger.error(sMethod + "Exception: cause: [" + e.getCause() + "] class name [" + e.getClass().getName()
					+ "] [" + e.toString() + "]");
			objLogger.error(sMethod + "Exception: toString: [" + e.toString() + " message: [" + e.getMessage() + "]");
			return null;
		} finally {
			session.close();
		}

	}

	//
	// ###
	@Override // 20210820 completed
	public User getByRecordIdentifer(String sRecordIdentifier) throws SQLException, HibernateException {
		String sMethod = "\n\t getByRecordIdentifer(): ";
		objLogger.trace(sMethod + "Entered");

		SessionFactory sf = SessionFactorySingleton.getSessionFactory();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();

		String sHQL = "";
		sHQL = "FROM User u WHERE u.username = :username";
		objLogger.debug(sMethod + "sHQL: [" + sHQL + "]" + " param: sRecordIdentifier: [" + sRecordIdentifier + "]");

		try {
			User objUser = (User) session.createQuery(sHQL).setParameter("username", sRecordIdentifier)
					.getSingleResult();
			objLogger.debug(sMethod + "objUser: [" + objUser.toString() + "]");

			// Hibernate automatically return the UserRole object when getting the User.
			// Do not need to do a separate read.

			tx.commit();
			return objUser;

		} catch (Exception e) {
			objLogger.error(sMethod + "Exception: cause: [" + e.getCause() + "] class name [" + e.getClass().getName()
					+ "] [" + e.toString() + "]");
			objLogger.error(sMethod + "Exception: toString: [" + e.toString() + " message: [" + e.getMessage() + "]");
			return null;
		} finally {
			session.close();
		}
	}

	//
	// ###
	@Override // 20210819 1223 working method added user using Admin drive through service
				// layer
	public User addRecord(AddOrEditDTO objAddOrEditDTO) throws SQLException, HibernateException {
		String sMethod = "\n\t addRecord(): ";
		objLogger.trace(sMethod + "Entered");

		objLogger.debug(sMethod + "objAddOrEditDTO: [" + objAddOrEditDTO.toString() + "]");

		SessionFactory sf = SessionFactorySingleton.getSessionFactory();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();

		// by this time the service layer would have validated the parameters
		String sUsername = objAddOrEditDTO.getDataElement(csUserTblUsername);
		String sPassword = objAddOrEditDTO.getDataElement(csUserTblPassword);
		String sFirstName = objAddOrEditDTO.getDataElement(csUserTblFirstName);
		String sLastName = objAddOrEditDTO.getDataElement(csUsrTblLastName);
		String sEmail = objAddOrEditDTO.getDataElement(csUserTblEmail);
		String sRoleName = objAddOrEditDTO.getDataElement(csUserTblRoleName);

		User objUser = new User(sUsername, sPassword, sFirstName, sLastName, sEmail);

		try {
			// get UserRole object
			UserRoleDAOImpl objUserRoleDAOImpl = new UserRoleDAOImpl();
			UserRole objUserRole = objUserRoleDAOImpl.getByRecordIdentifer(sRoleName);
			objUser.setUserRole(objUserRole);

			objLogger.debug(sMethod + "objUserRole: [" + objUser.toString() + "]");

			session.persist(objUser);
			tx.commit();
			return objUser;

		} catch (Exception e) {
			objLogger.error(sMethod + "Exception: cause: [" + e.getCause() + "] class name [" + e.getClass().getName()
					+ "] [" + e.toString() + "]");
			objLogger.error(sMethod + "Exception: toString: [" + e.toString() + " message: [" + e.getMessage() + "]");
			return null;
		} finally {
			session.close();
		}

	}

	//
	// ### check that user exists, do not allow userid, username, or password to
	// update
	@Override // 20210820 completed
	public User editRecord(AddOrEditDTO objAddOrEditDTO) throws SQLException {
		String sMethod = "\n\t editRecord(): ";
		objLogger.trace(sMethod + "Entered");

		objLogger.debug(sMethod + "objAddOrEditDTO: [" + objAddOrEditDTO.toString() + "]");

		// get UserRole object
		String sRoleName = objAddOrEditDTO.getDataElement(csUserTblRoleName);
		UserRoleDAOImpl objUserRoleDAOImpl = new UserRoleDAOImpl();
		UserRole objUserRole = objUserRoleDAOImpl.getByRecordIdentifer(sRoleName);

		// by this time the service layer would have validated the parameters
		String sUsername = objAddOrEditDTO.getDataElement(csUserTblUsername);

		SessionFactory sf = SessionFactorySingleton.getSessionFactory();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();

		try {

			String sHQL = "";
			sHQL = "FROM User u WHERE u.username = :username";
			objLogger.debug(sMethod + "sHQL: [" + sHQL + "]" + " param: sUsername: [" + sUsername + "]");

			// need to get user object to edit here and not through class method
			// getByRecordIdentifer()
			// due to the way sessions are open/closed within each methods. When using the
			// other class
			// method we loose connection to session object and cannot update.
			User objUserToEdit = (User) session.createQuery(sHQL).setParameter("username", sUsername).getSingleResult();

			// will not update the record id, the company username, or the company email.
			// also password should be updated through separate process where old pwd and
			// new pwd are entered
			// set user object with
			String sFirstName = objAddOrEditDTO.getDataElement(csUserTblFirstName);
			String sLastName = objAddOrEditDTO.getDataElement(csUsrTblLastName);

			// The only fields allowed to change
			objUserToEdit.setFirstName(sFirstName);
			objUserToEdit.setLastName(sLastName);
			objUserToEdit.setUserRole(objUserRole);

			objLogger.debug(sMethod + "DB update with objUserToEdit: [" + objUserToEdit.toString() + "]");

			session.persist(objUserToEdit);
			tx.commit();

			return objUserToEdit;

		} catch (Exception e) {
			objLogger.error(sMethod + "Exception: cause: [" + e.getCause() + "] class name [" + e.getClass().getName()
					+ "] [" + e.toString() + "]");
			objLogger.error(sMethod + "Exception: toString: [" + e.toString() + " message: [" + e.getMessage() + "]");
			return null;
		} finally {
			session.close();
		}
	}

	//
	// ### most companies do not delete records especially user, instead they
	// archive them for legal reasons.  The program will delete the record.
	//
	//
	@Override	//20210820 working, deletes fk records in Reimbursement first than user.
	public boolean deleteRecord(String sRecordIdentifier) throws SQLException {
		String sMethod = "\n\t editRecord(): ";
		objLogger.trace(sMethod + "Entered");

		SessionFactory sf = SessionFactorySingleton.getSessionFactory();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();

		try {
			String sHQL = "";
			sHQL = "FROM User u WHERE u.username = :username";
			objLogger.debug(sMethod + "sHQL: [" + sHQL + "]" + " param: sUsername: [" + sRecordIdentifier + "]");
			
			//get the user object by username since it is uniquely tied to an auto generated key
			User objUserToDelete = (User) session.createQuery(sHQL).setParameter("username", sRecordIdentifier).getSingleResult();
			objLogger.debug(sMethod + "Retrive objUserToDelete by sRecordIdentifier: [" + objUserToDelete.toString() + "]");
			
			//delete fk records in Reimbursement table
			//	encountered issues when trying to set user object as a parameter, sure it is syntax related
			sHQL = "DELETE FROM Reimbursement WHERE reimbAuthor.userId = " + objUserToDelete.getId();		
			int iResult  = session.createQuery(sHQL).executeUpdate();
			objLogger.debug(sMethod + "Number of dependent Reimbursement records deleted: [" + iResult + "]");
					
			//now delete the object retrieved.
			session.delete(objUserToDelete); 
			objLogger.debug(sMethod + "User object with sRecordIdentifier: [" + sRecordIdentifier + "] was deleted.");
		
			tx.commit();
			return true;

		} catch (Exception e) {
			objLogger.error(sMethod + "Exception: cause: [" + e.getCause() + "] class name [" + e.getClass().getName()
					+ "] [" + e.toString() + "]");
			objLogger.error(sMethod + "Exception: toString: [" + e.toString() + " message: [" + e.getMessage() + "]");
			return false;
		} finally {
			session.close();
		}
	}


	@Override
	public User getLogin(String sUsername) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> getListByRecordIdentifer(int iListId, String sRecordIdentifier) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
