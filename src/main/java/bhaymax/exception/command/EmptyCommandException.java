package bhaymax.exception.command;

/**
 * Thrown when an empty command is provided
 */
public class EmptyCommandException extends InvalidCommandFormatException {
    public static final String ERROR_MESSAGE = "You have provided an empty command.";

    public EmptyCommandException() {
        super(EmptyCommandException.ERROR_MESSAGE);
    }
}
