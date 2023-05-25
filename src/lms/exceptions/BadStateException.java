package lms.exceptions;

/**
 * A special runtime Exception.
 */
public class BadStateException extends RuntimeException {
    /**
     * Constructs a BadStateException with no message
     */
    public BadStateException() {
        super();
    }

    /**
     * Constructs a BadStateException with a message
     * @param message The message associated with this exception
     */
    public BadStateException(String message) {
        super(message);

    }

    /**
     * Constructs a BadStateException with a message and a throwable cause
     * @param message The message associated with this exception
     * @param cause The cause associated with this exception
     */
    public BadStateException(String message,
                             Throwable cause) {
        super(message, cause);

    }

    /**
     * Constructs a BadStateException with a throwable cause
     * @param cause The cause associated with this exception
     */
    public BadStateException(Throwable cause) {
        super(cause);

    }
}

