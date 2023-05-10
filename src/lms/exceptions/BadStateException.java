package lms.exceptions;

public class BadStateException extends RuntimeException{
    public BadStateException() {
        super();
    }
    public BadStateException(String message) {
        super(message);

    }
    public BadStateException(String message,
                             Throwable cause) {
        super(message, cause);

    }
    public BadStateException(Throwable cause) {
        super(cause);

    }
}

