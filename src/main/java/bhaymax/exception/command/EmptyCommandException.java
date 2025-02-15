package bhaymax.exception.command;

/**
 * Thrown when an empty command is provided
 */
public class EmptyCommandException extends InvalidCommandFormatException {
    public EmptyCommandException() {
        super("You have provided an empty command.");
    }
}
