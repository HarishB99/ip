package bhaymax.exception.file;

import java.io.IOException;
import java.util.List;

import bhaymax.exception.BhaymaxException;
import bhaymax.exception.ErrorMessageLine;

/**
 * Thrown when an {@link java.io.IOException} occurs when saving tasks to file
 */
public class FileWriteException extends BhaymaxException {
    public static final String ERROR_MESSAGE_IO_ERROR = "An I/O error occurred while writing to tasks file.";
    public static final String ERROR_MESSAGE_FILE_WAS_NOT_SAVED =
            "Your last change was not saved. Maybe you could try again?";

    public FileWriteException(IOException e) {
        super(e.getMessage());
    }

    @Override
    public String getMessage() {
        return super.getConcatenatedMessage(List.<ErrorMessageLine>of(
                new ErrorMessageLine(FileWriteException.ERROR_MESSAGE_IO_ERROR, true),
                new ErrorMessageLine(super.getMessage(), false),
                new ErrorMessageLine(FileWriteException.ERROR_MESSAGE_FILE_WAS_NOT_SAVED, true)
        ));
    }
}
