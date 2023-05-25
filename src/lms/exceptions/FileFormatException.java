package lms.exceptions;

/**
 * An exception thrown when a file being processed or read is not in the expected format.
 */
public class FileFormatException extends Exception {
    /**
     * Throws a FileFormatException with no message
     */
    public FileFormatException() {
        super();
    }

    /**
     * Constructs a FileFormatException with a specified message.
     * @param message The message associated with this Exception.
     */
    public FileFormatException(String message) {
        super(message);
    }

    /**
     * Constructs a FileFormatException with a specified error message and line number.
     * @param message The specified error message.
     * @param lineNum The line number where the error occurred.
     */
    public FileFormatException(String message,
                               int lineNum) {
        super(String.format("%s (line: %s)", message, lineNum));
    }

    /**
     * Constructs a FileFormatException with a specified error message, line number and throwable cause.
     * @param message The specified error message.
     * @param lineNum The line number where the error occurred.
     * @param cause The throwable cause of the exception.
     */
    public FileFormatException(String message,
                               int lineNum,
                               Throwable cause) {
        super(String.format("%s (line: %s)", message, lineNum), cause);

    }

    /**
     * Constructs a FileFormatException with a specified error message and throwable cause.
     * @param message The specified error message.
     * @param cause The throwable cause of the exception.
     */
    public FileFormatException(String message,
                               Throwable cause) {
        super(message, cause);

    }

    /**
     * Constructs a FileFormatException with a throwable cause.
     * @param cause The throwable cause of the exception.
     */
    public FileFormatException(Throwable cause) {
        super(cause);

    }
}
