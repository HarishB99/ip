package bhaymax.exception;

import java.util.List;

/**
 * Thrown when an unknown exception occurs
 */
public class UnknownException extends BhaymaxException {
    public UnknownException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getConcatenatedMessage(List.<ErrorMessageLine>of(
                new ErrorMessageLine("Sorry. It seems like an unknown error has occurred.", false),
                new ErrorMessageLine(super.getMessage(), true),
                new ErrorMessageLine("Maybe you could try restarting the app?", false)
        ));
    }
}
