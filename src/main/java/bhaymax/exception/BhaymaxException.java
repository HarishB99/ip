package bhaymax.exception;

import java.util.List;

/**
 * Represents an exception that can occur within the chatbot
 */
public class BhaymaxException extends Exception {
    protected BhaymaxException(String message) {
        super(message);
    }

    protected String getConcatenatedMessage(List<ErrorMessageLine> errorMessageLines) {
        return errorMessageLines.stream()
                .map(ErrorMessageLine::toString)
                .reduce((previousMessage, nextMessage)
                        -> previousMessage + System.lineSeparator() + nextMessage)
                .orElse("");
    }
}
