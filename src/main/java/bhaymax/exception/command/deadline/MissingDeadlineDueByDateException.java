package bhaymax.exception.command.deadline;

import bhaymax.exception.command.InvalidCommandFormatException;

/**
 * Thrown when a due-by date is not provided for a {@link bhaymax.task.timesensitive.Deadline}
 */
public class MissingDeadlineDueByDateException extends InvalidCommandFormatException {
    public static final String ERROR_MESSAGE = "A due-by date and time is required to create a deadline.";

    public MissingDeadlineDueByDateException() {
        super(MissingDeadlineDueByDateException.ERROR_MESSAGE);
    }
}
