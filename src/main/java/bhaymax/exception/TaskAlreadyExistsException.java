package bhaymax.exception;

/**
 * Thrown when an attempt to add a duplicate task to the tasks list occurs
 */
public class TaskAlreadyExistsException extends RuntimeException {
    public static final String ERROR_MESSAGE = "You have already added this task to your list.";

    public TaskAlreadyExistsException() {
        super(TaskAlreadyExistsException.ERROR_MESSAGE);
    }

    public TaskAlreadyExistsException(String message) {
        super(message);
    }
}
