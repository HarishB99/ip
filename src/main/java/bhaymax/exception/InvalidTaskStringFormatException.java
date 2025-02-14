package bhaymax.exception;

/**
 * Thrown when a serialised Task encountered is not of a valid format
 */
public class InvalidTaskStringFormatException extends RuntimeException {
    public InvalidTaskStringFormatException(String message) {
        super(message);
    }
}
