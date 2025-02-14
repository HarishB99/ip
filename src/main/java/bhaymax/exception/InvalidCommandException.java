package bhaymax.exception;

/**
 * Thrown when the user enters an unrecognised command
 */
public class InvalidCommandException extends InvalidCommandFormatException {
    public InvalidCommandException(String command) {
        super("'" + command + "' command is not recognised");
    }
}
