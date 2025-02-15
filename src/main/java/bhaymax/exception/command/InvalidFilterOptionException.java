package bhaymax.exception.command;

/**
 * Thrown when the filter option provided by the user is not recognised
 */
public class InvalidFilterOptionException extends InvalidCommandFormatException {
    public InvalidFilterOptionException(String filterOption) {
        super("I don't recognise the filter option you provided: '" + filterOption + "'");
    }
}
