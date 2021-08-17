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
			config.setProperty("hibernate.connection.username", System.getenv("p0_db_username"));
			config.setProperty("hibernate.connection.password", System.getenv("p0_db_password"));
			config.configure("hibernate.cfg.xml");

			objLogger.debug(sMethod + "building session factory");
			sessionFactory = config.buildSessionFactory();
			objLogger.debug(sMethod + "returned from building session factory");
		}

		return sessionFactory;
	}
}
