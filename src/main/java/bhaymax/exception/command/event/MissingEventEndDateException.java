package bhaymax.exception.command.event;

import bhaymax.exception.command.InvalidCommandFormatException;

/**
 * Thrown when an end date is not provided for a {@link bhaymax.task.timesensitive.Event}
 */
public class MissingEventEndDateException extends InvalidCommandFormatException {
    public static final String ERROR_MESSAGE = "A end date and time is required to create an event.";

    public MissingEventEndDateException() {
        super(MissingEventEndDateException.ERROR_MESSAGE);
    }
}
