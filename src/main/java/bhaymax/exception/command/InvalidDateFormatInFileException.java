package bhaymax.exception.command;

import bhaymax.exception.file.InvalidFileFormatException;

/**
 * Thrown when a date encountered in file is not of valid format
 */
public class InvalidDateFormatInFileException extends InvalidFileFormatException {
    public InvalidDateFormatInFileException(int lineNumber) {
        super(lineNumber, "I don't recognise the date in the file.");
    }
}
