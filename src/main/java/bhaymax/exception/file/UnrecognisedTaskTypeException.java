package bhaymax.exception.file;

/**
 * Thrown when the task type in a serialised {@link bhaymax.task.Task} is not recognised
 */
public class UnrecognisedTaskTypeException extends TaskDeSerialisationException {
    public static final String ERROR_MESSAGE = "I don't recognise the task type mentioned in the file.";

    public UnrecognisedTaskTypeException(int lineNumber) {
        super(lineNumber, UnrecognisedTaskTypeException.ERROR_MESSAGE);
    }
}
