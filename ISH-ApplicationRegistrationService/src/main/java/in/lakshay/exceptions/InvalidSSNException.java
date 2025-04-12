package in.lakshay.exceptions;

/**
 * Exception thrown when an invalid SSN is provided
 */
public class InvalidSSNException extends RuntimeException {

	public InvalidSSNException() {
		super();
	}

	public InvalidSSNException(String msg) {
		super(msg);
	}

}
