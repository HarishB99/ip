package bhaymax.exception.command;

/**
 * Thrown when a description is not provided for the task to be added
 */
public abstract class MissingTaskDescriptionException extends InvalidCommandFormatException {
    public static final String ERROR_MESSAGE_FORMAT = "A description is required for the task you want me to create. "
            + "Format of command should be: '%s'";

    public MissingTaskDescriptionException(String commandFormat) {
        super(String.format(MissingTaskDescriptionException.ERROR_MESSAGE_FORMAT, commandFormat));
    }
}
