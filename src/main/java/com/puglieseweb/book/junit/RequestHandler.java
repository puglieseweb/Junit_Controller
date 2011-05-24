/*
 * Puglieseweb (c) Copyright 2011. All Right Reserved.
 */
package com.puglieseweb.book.junit;

/**
 * RequestHandler is a helper component designed to do most of the dirty work.
 * It may call upon classes that throw any type of exception. So, Exception is
 * what you have the process method throw.
 * 
 * @author Emanuele Pugliese
 * @version 0.00.001 - 22 May 2011
 */
public interface RequestHandler {

	// --------------- METHODS SECTION ---------------
	/**
	 * Process a Request and return your Response. 
	 */
	Response process(Request request) throws Exception;
}
