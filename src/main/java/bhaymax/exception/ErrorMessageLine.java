package bhaymax.exception;

/**
 * Represents a line in an error message within the chatbot
 */
public class ErrorMessageLine {
    private static final String PREFIX = "[-] ";
    private static final String INDENT = "  ";

    private final String message;
    private final String messagePrefix;

    /**
     * Constructs a line of an error message using the provided message (line)
     *
     * @param message a line of message, as a {@code String}
     * @param isProminentLine whether this line is supposed to be prominent
     *                        (prominent lines have an additional indent)
     */
    public ErrorMessageLine(String message, boolean isProminentLine) {
        this.message = message;
        String prefix = ErrorMessageLine.PREFIX;
        if (isProminentLine) {
            prefix += ErrorMessageLine.INDENT;
        }
        this.messagePrefix = prefix;
    }

    @Override
    public String toString() {
        return this.messagePrefix + this.message;
    }
}
