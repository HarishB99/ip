package bhaymax.exception;

/**
 * Thrown when the user enters an
 * unrecognised command
 */
public class InvalidCommandException extends Exception {
    public InvalidCommandException() {
        super("Unrecognised command");
    }
}
