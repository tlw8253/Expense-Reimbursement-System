package com.tlw8253.app;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
import com.tlw8253.dao.AccountDAOImpl;
import com.tlw8253.dto.AccountAddDTO;
import com.tlw8253.dto.AddOrEditClientDTO;
import com.tlw8253.dto.AddDTO;
import com.tlw8253.exception.*;
import com.tlw8253.exception.DatabaseException;
import com.tlw8253.javalin.JavalinHelper;
import com.tlw8253.model.Account;
import com.tlw8253.model.Client;
import com.tlw8253.service.AccountService;
import com.tlw8253.service.ClientService;

import com.tlw8253.util.*;
*

/**
 * This is a driver used during development to test functionality as it is
 * built. 
 * 
 * @author tlw8748253
 *
 */
public class Driver implements Constants {
	private final static Logger objLogger = LoggerFactory.getLogger(Driver.class);

	public static void main(String[] args) {

		// create / call static methods in this class to control test driving during development
		testSomeProcessing();
	}

	//
	// ###
	public static void testSomeProcessing() {
	}



}
