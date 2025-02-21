package bhaymax.exception.command;

/**
 * Thrown when a task number is not provided for a command that requires it
 */
public abstract class MissingTaskNumberException extends InvalidCommandFormatException {
    public static final String ERROR_MESSAGE_FORMAT =
            "A task number is required for the operation you are requesting me to perform. "
            + "Format of command should be '%s'.";

    public MissingTaskNumberException(String commandFormat) {
        super(String.format(MissingTaskNumberException.ERROR_MESSAGE_FORMAT, commandFormat));
    }
}
