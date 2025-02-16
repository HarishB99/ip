package bhaymax.exception.file;

/**
 * Thrown when the task type in a serialised {@link bhaymax.task.Task} is not recognised
 */
public class UnrecognisedTaskTypeException extends TaskDeSerialisationException {
    public UnrecognisedTaskTypeException(int lineNumber) {
        super(lineNumber, "I don't recognise the task type mentioned in the file.");
    }
}
