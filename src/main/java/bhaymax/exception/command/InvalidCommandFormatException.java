package bhaymax.exception.command;

import java.util.List;

import bhaymax.exception.BhaymaxException;
import bhaymax.exception.ErrorMessageLine;

/**
 * Thrown when the command entered by the user is not of valid format or is missing
 */
public class InvalidCommandFormatException extends BhaymaxException {
    public static final String ERROR_MESSAGE_INVALID_COMMAND_SYNTAX = "There is an error in your command:";
    public static final String ERROR_MESSAGE_TRY_AGAIN = "Try again.";

    /**
     * Create an {@code InvalidCommandFormatException} with the provided {message}
     *
     * @param message a message that will more accurately describe
     *                the reason behind the exception being thrown
     */
    public InvalidCommandFormatException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getConcatenatedMessage(List.<ErrorMessageLine>of(
                new ErrorMessageLine(
                        InvalidCommandFormatException.ERROR_MESSAGE_INVALID_COMMAND_SYNTAX, false),
                new ErrorMessageLine(super.getMessage(), true),
                new ErrorMessageLine(InvalidCommandFormatException.ERROR_MESSAGE_TRY_AGAIN, false)
        ));
    }
}
