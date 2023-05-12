package lms.exceptions;

/**
 * A special runtime exception used to indicated that an unsupported action or operation was attempted.
 */
public class UnsupportedActionException extends RuntimeException {
    /**
     * Constructs an UnsupportedActionException with no message
     */
    public UnsupportedActionException() {
        super();
    }
    /**
     * Constructs an UnsupportedActionException with the specified message
     * @param message The message to be displayed when the exception is thrown
     */
    public UnsupportedActionException(String message) {
        super(message);
    }
    /**
     * Constructs an UnsupportedActionException with the specified message and throwable cause
     * @param message The message to be displayed when the exception is thrown
     * @param cause The cause of the exception
     */
    public UnsupportedActionException(String message,
                                      Throwable cause) {
        super(message,cause);
    }
    /**
     * Constructs an UnsupportedActionException with the specified throwable cause
     * @param cause The throwable cause of the exception
     */
    public UnsupportedActionException(Throwable cause) {
        super(cause);

    }

}
