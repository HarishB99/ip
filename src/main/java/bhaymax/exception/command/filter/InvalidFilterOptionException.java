package bhaymax.exception.command.filter;

import bhaymax.exception.command.InvalidCommandFormatException;

/**
 * Thrown when the filter option provided by the user is not recognised
 */
public class InvalidFilterOptionException extends InvalidCommandFormatException {
    public static final String ERROR_MESSAGE_FORMAT =
            "I don't recognise the filter option you provided: '%s'";

    public InvalidFilterOptionException(String filterOption) {
        super(String.format(InvalidFilterOptionException.ERROR_MESSAGE_FORMAT, filterOption));
    }
}
