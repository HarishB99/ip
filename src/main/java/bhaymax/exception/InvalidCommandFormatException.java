package bhaymax.exception;

/**
 * Thrown when the command entered by the user
 * is not of valid format or is missing
 */
public class InvalidCommandFormatException extends Exception {
    public InvalidCommandFormatException(String message) {
        super(message);
    }
}
