package com.tlw8253.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionFactorySingleton {
	private static Logger objLogger = LoggerFactory.getLogger(SessionFactorySingleton.class);
	// This is a singleton SessionFactory, which means only a single instance of
	// SessionFactory will exist
	// during the lifetime of our application running
	private static SessionFactory sessionFactory;

	public synchronized static SessionFactory getSessionFactory() {
		String sMethod = "SessionFactory(): ";
		objLogger.trace(sMethod + "Entered");

		if (sessionFactory == null) {
			objLogger.debug(sMethod + "creating session configuration");
			Configuration config = new Configuration();
			String sDB_URL = System.getenv("localhost_db_url");
			String sDB_Name = System.getenv("p1_db_name");
			String sConnURL = sDB_URL + sDB_Name;
			
			objLogger.debug(sMethod + "sDB_URL: [" + sDB_URL + "] sDB_Name: [" + sDB_Name + "] sConnURL: [" + sConnURL + "]");			
			config.setProperty("hibernate.connection.url", sConnURL);
			
			String sDB_Username = System.getenv("localhost_db_username");
			objLogger.debug(sMethod + "localhost_db_username: [" + sDB_Username + "]");
			config.setProperty("hibernate.connection.username", sDB_Username);
			
			String sDB_pwd = System.getenv("localhost_db_password");
			objLogger.debug(sMethod + "localhost_db_password: [" + sDB_pwd + "]");
			config.setProperty("hibernate.connection.password", sDB_pwd);
			config.configure("hibernate.cfg.xml");

			objLogger.debug(sMethod + "building session factory");
			sessionFactory = config.buildSessionFactory();
			objLogger.debug(sMethod + "returned from building session factory");
		}

		return sessionFactory;
	}
}
