package lms.exceptions;

public class FileFormatException extends Exception {
    public FileFormatException() {
        super();
    }
    public FileFormatException(String message){
        super(message);
    }
    public FileFormatException(String message,
                               int lineNum) {
        super(String.format("%s (line: %o)", message, lineNum));
    }
    public FileFormatException(String message,
                               int lineNum,
                               Throwable cause) {
        super(String.format("%s (line: %o)", message, lineNum), cause);

    }
    public FileFormatException(String message,
                               Throwable cause) {
        super(message,cause);

    }
    public FileFormatException(Throwable cause) {
        super(cause);

    }
}
