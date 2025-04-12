package in.lakshay.exceptions;

/**
 * Base exception class for application-specific exceptions
 */
public class ApplicationException extends RuntimeException {
    
    public ApplicationException() {
        super();
    }
    
    public ApplicationException(String message) {
        super(message);
    }
    
    public ApplicationException(String message, Throwable cause) {
        super(message, cause);
    }
}
