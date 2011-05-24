package com.puglieseweb.book.junit;

/**
 * 
 * Notice that processRequest does not declare any exceptions. This method is at
 * the top of the control stack and should catch and cope with any and all
 * errors internally. If it did throw an exception, the error would usually go
 * up to the Java Virtual Machine (JVM) or servlet container. The JVM or
 * container would then present the user with one of those nasty white screens.
 * Better you handle it yourself.
 * 
 * @author Emanuele Pugliese
 * @version 0.00.001 - 22 May 2011
 */
public interface Controller {
	/**
	 * Define a top-level method for processing an incoming request. After
	 * accepting the request, the controller dispatches it to the appropriate
	 * RequestHandler.
	 * 
	 * @param request
	 * @return response
	 */
	Response processRequest(Request request);

	/**
	 * Allows you to extend the Controller without modifying the Java source
	 **/
	void addHandler(Request request, RequestHandler requestHandler);
}
