package bhaymax.exception.command;

import bhaymax.parser.Parser;

/**
 * Thrown when a date and time provided by the user is not of valid format
 */
public class InvalidDateTimeFormatInCommandException extends InvalidCommandFormatException {
    public static final String ERROR_MESSAGE_FORMAT = "I don't recognise the format of the date and time you provided. "
            + "Format of date should be '%s'.";

    /**
     * Default constructor for {@code InvalidDateTimeFormatInCommandException}
     */
    public InvalidDateTimeFormatInCommandException() {
        super(String.format(
                InvalidDateTimeFormatInCommandException.ERROR_MESSAGE_FORMAT, Parser.DATETIME_INPUT_FORMAT));
    }
}
