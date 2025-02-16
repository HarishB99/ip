package bhaymax.exception.file;

/**
 * Thrown when a serialised {@link bhaymax.task.Task} encountered is not of a valid format
 * (and hence, not de-serialisable)
 */
public class TaskDeSerialisationException extends InvalidFileFormatException {
    public TaskDeSerialisationException(int lineNumber, String message) {
        super(lineNumber, message);
    }
}
