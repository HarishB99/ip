package bhaymax.exception.command;

/**
 * Thrown when a filter option is not specified for the {@link bhaymax.command.FilterCommand}
 */
public class MissingFilterOptionException extends InvalidCommandFormatException {
    public static final String ERROR_MESSAGE = "A filter option is required for the filter operation.";

    public MissingFilterOptionException() {
        super(MissingFilterOptionException.ERROR_MESSAGE);
    }
}
