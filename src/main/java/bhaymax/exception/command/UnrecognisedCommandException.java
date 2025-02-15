package bhaymax.exception.command;

/**
 * Thrown when the user enters an unrecognised command
 */
public class UnrecognisedCommandException extends InvalidCommandFormatException {
    public UnrecognisedCommandException(String command) {
        super("I don't recognise the command you provided: '" + command + "'");
    }
}
