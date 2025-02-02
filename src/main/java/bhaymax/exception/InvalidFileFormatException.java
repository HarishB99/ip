package bhaymax.exception;

/**
 * Thrown when the file containing tasks is not of valid format
 */
public class InvalidFileFormatException extends Exception {
    public InvalidFileFormatException(String message) {
        super(message);
    }
}
