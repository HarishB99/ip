package bhaymax.exception.command;

/**
 * Thrown when a description is not provided for the task to be added
 */
public class MissingTaskDescriptionException extends InvalidCommandFormatException {
    public static final String ERROR_MESSAGE = "A description is required for the task you want me to create";

    public MissingTaskDescriptionException() {
        super(MissingTaskDescriptionException.ERROR_MESSAGE);
    }
}
