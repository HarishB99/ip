package bhaymax.exception.command;

/**
 * Thrown when a filter option is not specified for the {@link bhaymax.command.FilterCommand}
 */
public class MissingFilterOptionException extends InvalidCommandFormatException {
    public MissingFilterOptionException() {
        super("A filter option is required for the filter operation.");
    }
}
