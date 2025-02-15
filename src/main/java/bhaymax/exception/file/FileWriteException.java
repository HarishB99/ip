package bhaymax.exception.file;

import java.io.IOException;
import java.util.List;

import bhaymax.exception.BhaymaxException;
import bhaymax.exception.ErrorMessageLine;

/**
 * Thrown when an {@link java.io.IOException} occurs when saving tasks to file
 */
public class FileWriteException extends BhaymaxException {
    public FileWriteException(IOException e) {
        super(e.getMessage());
    }

    @Override
    public String getMessage() {
        return super.getConcatenatedMessage(List.<ErrorMessageLine>of(
                new ErrorMessageLine("An I/O error occurred while writing to tasks file.", true),
                new ErrorMessageLine(super.getMessage(), false),
                new ErrorMessageLine("Your last change was not saved. Maybe you could try again?",
                        true)
        ));
    }
}
