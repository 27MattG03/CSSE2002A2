package lms.exceptions;

public class UnsupportedActionException extends RuntimeException {
    public UnsupportedActionException() {
        super();
    }
    public UnsupportedActionException(String message) {
        super(message);

    }
    public UnsupportedActionException(String message,
                                      Throwable cause) {
        super(message,cause);
    }
    public UnsupportedActionException(Throwable cause) {
        super(cause);

    }

}
