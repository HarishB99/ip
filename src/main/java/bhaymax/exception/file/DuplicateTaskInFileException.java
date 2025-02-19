package bhaymax.exception.file;

/**
 * Thrown when there are duplicates in the tasks file
 */
public class DuplicateTaskInFileException extends InvalidFileFormatException {
    public static final String ERROR_MESSAGE = "This line has been duplicated at least once.";

    public DuplicateTaskInFileException(int lineNumber) {
        super(lineNumber, DuplicateTaskInFileException.ERROR_MESSAGE);
    }
}
