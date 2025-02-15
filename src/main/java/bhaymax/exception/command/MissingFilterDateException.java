package bhaymax.exception.command;

/**
 * Thrown when a date is not specified for the {@link bhaymax.command.FilterCommand}
 */
public class MissingFilterDateException extends InvalidCommandFormatException {
    public MissingFilterDateException() {
        super("A date is required for the filter operation.");
    }
}
