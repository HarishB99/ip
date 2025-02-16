package bhaymax.exception.command;

/**
 * Thrown when a start date is not provided for a {@link bhaymax.task.timesensitive.Event}
 */
public class MissingEventStartDateException extends InvalidCommandFormatException {
    public static final String ERROR_MESSAGE = "A start date and time is required to create an event.";

    public MissingEventStartDateException() {
        super(MissingEventStartDateException.ERROR_MESSAGE);
    }
}
