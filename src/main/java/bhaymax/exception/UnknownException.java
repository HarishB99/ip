package bhaymax.exception;

import java.util.List;

/**
 * Thrown when an unknown exception occurs
 */
public class UnknownException extends BhaymaxException {
    public static final String ERROR_MESSAGE_UNKNOWN_ERROR = "Sorry. It seems like an unknown error has occurred.";
    public static final String ERROR_MESSAGE_RESTART_APP = "Maybe you could try restarting the app?";

    public UnknownException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getConcatenatedMessage(List.<ErrorMessageLine>of(
                new ErrorMessageLine(UnknownException.ERROR_MESSAGE_UNKNOWN_ERROR, false),
                new ErrorMessageLine(super.getMessage(), true),
                new ErrorMessageLine(UnknownException.ERROR_MESSAGE_RESTART_APP, false)
        ));
    }
}
