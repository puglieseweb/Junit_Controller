package com.puglieseweb.book.junit;

import java.util.HashMap;
import java.util.Map;

public class DefaultController implements Controller {
	// ---------- ATTRIBUTES SECTION ----------
	/**
	 * Acts as a registry for your requesthandlers
	 */
	private final Map requestHandlers = new HashMap();

	// ---------- METHODS SECTION -----------
	/**
	 * Fetch the RequestHandler for a given request If a RequestHandler has not
	 * been registered, you throw a RuntimeException
	 * (java.lang.RuntimeException), because this happenstance represents a
	 * programming mistake rather than an issue raised by a user or external
	 * system. Java does not require you to declare the RuntimeException in the
	 * method’s signature, but you can still catch it as an exception. An
	 * improvement would be to add a specific exception to the controller
	 * framework (NoSuitableRequestHandlerException, for example).
	 */
	protected RequestHandler getHandler(Request request)
	{
		if (!this.requestHandlers.containsKey(request.getName())) {
			String message = "Cannot find handler for request name " + "["
					+ request.getName() + "]";
			throw new RuntimeException(message);
		}
		// returns the appropriate handler to its caller
		return (RequestHandler) this.requestHandlers.get(request.getName());
	}

	/**
	 * Dispatches the appropriate handler for the request and passes back the
	 * handler’s Response. If an exception bubbles up, it is caught in the
	 * ErrorResponse class, shown in listing 2.7.
	 */
	public Response processRequest(Request request) {
		Response response;
		try {
			response = getHandler(request).process(request);
		} catch (Exception exception) {
			response = new ErrorResponse(request, exception);
		}
		return response;
	}

	/**
	 * Check to see whether the name for the handler has been registered, and
	 * throw an exception if it has
	 */
	public void addHandler(Request request, RequestHandler requestHandler) {
		if (this.requestHandlers.containsKey(request.getName())) {
			throw new RuntimeException("A request handler has "
					+ "already been registered for request name " + "["
					+ request.getName() + "]");
			//
		} else {
			this.requestHandlers.put(request.getName(), requestHandler);
		}
	}
}
