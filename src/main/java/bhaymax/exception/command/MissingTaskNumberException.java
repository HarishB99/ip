package bhaymax.exception.command;

/**
 * Thrown when a task number is not provided for a command that requires it
 */
public class MissingTaskNumberException extends InvalidCommandFormatException {
    public MissingTaskNumberException() {
        super("A task number is required for the operation you are requesting me to perform.");
    }
}
