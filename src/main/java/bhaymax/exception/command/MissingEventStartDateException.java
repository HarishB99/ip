package bhaymax.exception.command;

/**
 * Thrown when a start date is not provided for a {@link bhaymax.task.timesensitive.Event}
 */
public class MissingEventStartDateException extends InvalidCommandFormatException {
    public MissingEventStartDateException() {
        super("A start date and time is required to create an event.");
    }
}
