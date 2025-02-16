package bhaymax.exception.command;

/**
 * Thrown when no search term is provided for the {@link bhaymax.command.SearchCommand}
 */
public class MissingSearchTermException extends InvalidCommandFormatException {
    public static final String ERROR_MESSAGE = "A search term (or phrase) is required to perform the search operation.";

    public MissingSearchTermException() {
        super(MissingSearchTermException.ERROR_MESSAGE);
    }
}
