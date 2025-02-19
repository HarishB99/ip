package bhaymax.exception.command;

/**
 * Thrown when the end date for an event precedes its start date
 * (which is obviously invalid as you can't have an event end
 * before it even starts)
 */
public class InvalidTimeRangeForEventException extends InvalidCommandFormatException {
    public static final String ERROR_MESSAGE = "The date range provided for event is incorrect. "
            + "End date should be after or equal to the start date";

    public InvalidTimeRangeForEventException() {
        super(InvalidTimeRangeForEventException.ERROR_MESSAGE);
    }
}
