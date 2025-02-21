package bhaymax.exception.command;

import bhaymax.parser.Parser;

/**
 * Thrown when a date provided by the user is not of valid format
 */
public class InvalidDateFormatInCommandException extends InvalidCommandFormatException {
    public static final String ERROR_MESSAGE_FORMAT = "I don't recognise the format of the date you provided. "
            + "Format of date should be '%s'.";

    public InvalidDateFormatInCommandException() {
        super(String.format(InvalidDateFormatInCommandException.ERROR_MESSAGE_FORMAT, Parser.DATE_INPUT_FORMAT));
    }
}
