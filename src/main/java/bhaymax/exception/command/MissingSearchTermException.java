package bhaymax.exception.command;

/**
 * Thrown when no search term is provided for the {@link bhaymax.command.SearchCommand}
 */
public class MissingSearchTermException extends InvalidCommandFormatException {
    public MissingSearchTermException() {
        super("A search term (or phrase) is required to perform the search operation.");
    }
}
