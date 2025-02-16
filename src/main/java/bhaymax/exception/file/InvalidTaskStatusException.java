package bhaymax.exception.file;

/**
 * Thrown when the status in a serialised {@link bhaymax.task.Task} is not recognised
 */
public class InvalidTaskStatusException extends TaskDeSerialisationException {
    public InvalidTaskStatusException(int lineNumber) {
        super(lineNumber, "Invalid value encountered for task status.");
    }
}
