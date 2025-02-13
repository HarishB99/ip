package bhaymax.exception;

/**
 * Thrown when the filter option provided by the user is not recognised
 */
public class InvalidFilterOptionException extends RuntimeException {
    public InvalidFilterOptionException() {
        super("Unknown filter option");
    }
}
