package bhaymax.exception.file;

/**
 * Thrown when the status in a serialised {@link bhaymax.task.Task} is not recognised
 */
public class InvalidTaskStatusException extends TaskDeSerialisationException {
    public static final String ERROR_MESSAGE = "Invalid value encountered for task status.";

    public InvalidTaskStatusException(int lineNumber) {
        super(lineNumber, InvalidTaskStatusException.ERROR_MESSAGE);
    }
}
