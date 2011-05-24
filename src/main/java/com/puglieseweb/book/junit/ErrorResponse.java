package com.puglieseweb.book.junit;

public class ErrorResponse implements Response {
	private final Request originalRequest;
	private final Exception originalException;

	public ErrorResponse(Request request, Exception exception) {
		this.originalRequest = request;
		this.originalException = exception;
	}

	public Request getOriginalRequest() {
		return this.originalRequest;
	}

	public Exception getOriginalException() {
		return this.originalException;
	}

	public String getName() {
		return "Error Responce";
	}
}
