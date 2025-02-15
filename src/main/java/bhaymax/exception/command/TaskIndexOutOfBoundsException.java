package bhaymax.exception.command;

/**
 * Thrown when the task number provided by the user is out of bounds
 */
public class TaskIndexOutOfBoundsException extends InvalidCommandFormatException {
    public TaskIndexOutOfBoundsException() {
        super("I cannot find a task with the task number you provided.");
    }
}
