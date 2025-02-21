package bhaymax.exception.command.deadline;

import bhaymax.command.DeadlineCommand;
import bhaymax.exception.command.InvalidCommandFormatException;

/**
 * Thrown when a due-by date is not provided for a {@link bhaymax.task.timesensitive.Deadline}
 */
public class MissingDeadlineDueByDateException extends InvalidCommandFormatException {
    public static final String ERROR_MESSAGE_FORMAT = "A due-by date and time is required to create a deadline. "
            + "Format of command should be '%s'.";

    public MissingDeadlineDueByDateException() {
        super(String.format(MissingDeadlineDueByDateException.ERROR_MESSAGE_FORMAT, DeadlineCommand.COMMAND_FORMAT));
    }
}
