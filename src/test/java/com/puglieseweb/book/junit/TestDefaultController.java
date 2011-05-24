/*
 * Puglieseweb (c) Copyright 2011. All Right Reserved.
 */
package com.puglieseweb.book.junit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Emanuele Pugliese
 * @version 0.00.001 - 22 May 2011
 */
public class TestDefaultController {
	private DefaultController controller;
	private Request request;
	private RequestHandler handler;

	@Before
	public void instantiate() throws Exception {
		controller = new DefaultController();
		// Instanziate test objects
		request = new SampleRequest();
		handler = new SampleHandler();

		// Controller is the object under test
		controller.addHandler(request, handler);
	}

	/**
	 * Set up a request object that returns a known name (Test)
	 */
	private class SampleRequest implements Request {
		// ----------- ATTRIBUTES SECTION -----------
		private static final String DEFAULT_NAME = "Test";
		private final String name;
		
		// ----------- CONSTRUCTORS SECTION -----------
		public SampleRequest(String name){
			this.name = name;
		}
		
		/**
		 * Constructor -
		 */
		public SampleRequest() {
			this(DEFAULT_NAME);
		}
		
		// ----------- METHODS SECTION -----------
		public String getName() {
			return this.name;
		}
	}

	/**
	 * Implement an Request Handler that process
	 */
	private class SampleHandler implements RequestHandler {
		public Response process(Request request) throws Exception {
			return new SampleResponse();
		}
	}

	/**
	 * Implement an Request Handler that throws an exception
	 */
	private class SampleExceptionHandler implements RequestHandler{
		public Response process(Request request) throws Exception {
			throw new Exception("error processing request");
		}
	}
	
	/**
	 * SampleResponse has an identity (represented by getName()) and its own
	 * equals method
	 */
	private class SampleResponse implements Response {
		private static final String NAME = "Test";

		public String getName() {
			return NAME;
		}

		@Override
		public boolean equals(Object object) {
			boolean result = false;
			if (object instanceof SampleResponse) {
				result = ((SampleResponse) object).getName().equals(getName());
			}
			return result;
		}

		@Override
		public int hashCode() {
			return NAME.hashCode();
		}
	}
	

	// ------------ TESTS SECTION ------------
	@Test
	public void testAddHandler() {
		RequestHandler handler2 = controller.getHandler(request);
		assertSame(
				"Handler we set in controller should be the same handler we get",
				handler2, handler);
	}

	@Test
	public void testProcessRequest() {
		Response response = controller.processRequest(request);

		// This is important because you
		// call the getClass method on the Response object
		assertNotNull("Must not return a null responce", response);
		assertEquals("Response should be of type SampleResponse",
				SampleResponse.class, response.getClass());
		
		// This assert would work just because we gave an identity to the Response object
		assertEquals("Response should be the same object SampleResponse", new SampleResponse(), response);

	}
	
	@Test
	public void testProcessRequestAnswersErrorResponse(){
		SampleRequest request = new SampleRequest("testError");
		SampleExceptionHandler handler = new SampleExceptionHandler();
	
		controller.addHandler(request, handler);
		Response response = controller.processRequest(request);
		
		assertNotNull("Must not return a null response", response);
		assertEquals(ErrorResponse.class, response.getClass());
	}
	
	/**
	 * Check the undocumented RuntimeException 
	 */
	@Test(expected=RuntimeException.class)
	public void testGetHandlerNotDefined(){
		SampleRequest request = new SampleRequest("testNotDefined");
		
		//The following line is supposed to throw a RuntimeException
		controller.getHandler(request);
	}
	
	@Test(expected=RuntimeException.class)
	public void testAddRequestDuplicateName(){
		SampleRequest request = new SampleRequest();
		SampleHandler handler = new SampleHandler();
		
		//The following line is supposed to throw a RuntimeException
		controller.addHandler(request, handler);
	}
}
