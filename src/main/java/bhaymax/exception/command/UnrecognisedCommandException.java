package bhaymax.exception.command;

/**
 * Thrown when the user enters an unrecognised command
 */
public class UnrecognisedCommandException extends InvalidCommandFormatException {
    public static final String ERROR_MESSAGE_FORMAT = "I don't recognise the command you provided: '%s'";

    public UnrecognisedCommandException(String command) {
        super(String.format(UnrecognisedCommandException.ERROR_MESSAGE_FORMAT, command));
    }
}
