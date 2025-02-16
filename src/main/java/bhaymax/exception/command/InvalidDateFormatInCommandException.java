package bhaymax.exception.command;

/**
 * Thrown when a date provided by the user is not of valid format
 */
public class InvalidDateFormatInCommandException extends InvalidCommandFormatException {
    public static final String ERROR_MESSAGE = "I don't recognise the format of the date you provided.";

    public InvalidDateFormatInCommandException() {
        super(InvalidDateFormatInCommandException.ERROR_MESSAGE);
    }
}
