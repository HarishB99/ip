package bhaymax.exception.command;

/**
 * Thrown when a due-by date is not provided for a {@link bhaymax.task.timesensitive.Deadline}
 */
public class MissingDeadlineDueByDateException extends InvalidCommandFormatException {
    public MissingDeadlineDueByDateException() {
        super("A due-by date and time is required to create a deadline.");
    }
}
