package bhaymax.exception.command;

/**
 * Thrown when the task index provided is not a number
 */
public class TaskIndexIsNotANumberException extends InvalidCommandFormatException {
    public TaskIndexIsNotANumberException() {
        super("The task number you provided is ... not a number.");
    }
}
