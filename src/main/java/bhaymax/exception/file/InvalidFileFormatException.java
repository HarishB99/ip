package bhaymax.exception.file;

import java.util.List;

import bhaymax.exception.BhaymaxException;
import bhaymax.exception.ErrorMessageLine;

/**
 * Thrown when the file containing tasks is not of valid format
 */
public class InvalidFileFormatException extends BhaymaxException {
    public static final String ERROR_MESSAGE_FORMAT = "Line %d: %s";
    public static final String ERROR_MESSAGE_FILE_LOAD = "I encountered errors while loading your tasks from disk.";
    public static final String ERROR_MESSAGE_CHECK_FILE = "You might want to check your tasks file and try again.";

    public InvalidFileFormatException(int lineNumber, String message) {
        super(String.format(InvalidFileFormatException.ERROR_MESSAGE_FORMAT, lineNumber, message));
    }

    @Override
    public String getMessage() {
        return super.getConcatenatedMessage(List.<ErrorMessageLine>of(
                new ErrorMessageLine(InvalidFileFormatException.ERROR_MESSAGE_FILE_LOAD, false),
                new ErrorMessageLine(super.getMessage(), true),
                new ErrorMessageLine(InvalidFileFormatException.ERROR_MESSAGE_CHECK_FILE, false)
        ));
    }
}
