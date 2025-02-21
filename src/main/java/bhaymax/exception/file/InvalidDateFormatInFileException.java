package bhaymax.exception.file;

/**
 * Thrown when a date encountered in file is not of valid format
 */
public class InvalidDateFormatInFileException extends InvalidFileFormatException {
    public static final String ERROR_MESSAGE = "I don't recognise the format of the date provided in the tasks file.";

    public InvalidDateFormatInFileException(int lineNumber) {
        super(lineNumber, InvalidDateFormatInFileException.ERROR_MESSAGE);
    }
}
