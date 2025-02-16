package bhaymax.exception.command;

/**
 * Thrown when the task index provided is not a number
 */
public class TaskIndexIsNotANumberException extends InvalidCommandFormatException {
    public static final String ERROR_MESSAGE = "The task number you provided is ... not a number.";

    public TaskIndexIsNotANumberException() {
        super(TaskIndexIsNotANumberException.ERROR_MESSAGE);
    }
}
