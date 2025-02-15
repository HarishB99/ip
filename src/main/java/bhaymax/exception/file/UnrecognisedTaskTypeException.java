package bhaymax.exception.file;

/**
 * Thrown when the task type in a serialised {@link bhaymax.task.Task} is not recognised
 */
public class UnrecognisedTaskTypeException extends TaskDeSerialisationException {
    public UnrecognisedTaskTypeException(int lineNumber, String message) {
        super(lineNumber, message);
    }
}
