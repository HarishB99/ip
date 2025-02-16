package bhaymax.exception.command;

/**
 * Thrown when a date provided by the user is not of valid format
 */
public class InvalidDateFormatInCommandException extends InvalidCommandFormatException {
    public InvalidDateFormatInCommandException() {
        super("I don't recognise the format of the date you provided.");
    }
}
