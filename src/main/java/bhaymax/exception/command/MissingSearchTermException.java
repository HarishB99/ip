package bhaymax.exception.command;

import bhaymax.command.SearchCommand;

/**
 * Thrown when no search term is provided for the {@link bhaymax.command.SearchCommand}
 */
public class MissingSearchTermException extends InvalidCommandFormatException {
    public static final String ERROR_MESSAGE = " You are missing a search term or a search phrase. "
            + "The format of a search command is '" + SearchCommand.COMMAND_FORMAT + "'.";

    public MissingSearchTermException() {
        super(MissingSearchTermException.ERROR_MESSAGE);
    }
}
