package bhaymax.exception.command;

/**
 * Thrown when an end date is not provided for a {@link bhaymax.task.timesensitive.Event}
 */
public class MissingEventEndDateException extends InvalidCommandFormatException {
    public MissingEventEndDateException() {
        super("A end date and time is required to create an event.");
    }
}
