package bhaymax.exception.command;

/**
 * Thrown when no search term is provided for the {@link bhaymax.command.SearchCommand}
 */
public class MissingSearchTermException extends InvalidCommandFormatException {
    public static final String ERROR_MESSAGE = "The format of a search command is 'search <search term or phrase>'. "
            + "You are missing: <search term or phrase>";

    public MissingSearchTermException() {
        super(MissingSearchTermException.ERROR_MESSAGE);
    }
}
