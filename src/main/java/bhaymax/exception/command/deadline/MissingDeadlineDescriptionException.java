package bhaymax.exception.command.deadline;

import bhaymax.command.DeadlineCommand;
import bhaymax.exception.command.MissingTaskDescriptionException;

/**
 * Thrown when a description is not provided for a deadline item
 */
public class MissingDeadlineDescriptionException extends MissingTaskDescriptionException {
    public MissingDeadlineDescriptionException() {
        super(DeadlineCommand.COMMAND_FORMAT);
    }
}
