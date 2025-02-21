package bhaymax.exception.command.filter;

import bhaymax.exception.command.InvalidCommandFormatException;

/**
 * Thrown when a date is not specified for the {@link bhaymax.command.FilterCommand}
 */
public class MissingFilterDateException extends InvalidCommandFormatException {
    public static final String ERROR_MESSAGE = "A date is required for the filter operation.";

    public MissingFilterDateException() {
        super(MissingFilterDateException.ERROR_MESSAGE);
    }
}
