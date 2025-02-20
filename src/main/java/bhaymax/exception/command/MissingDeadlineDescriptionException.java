package bhaymax.exception.command;

import bhaymax.command.DeadlineCommand;

/**
 * Thrown when a description is not provided for a deadline item
 */
public class MissingDeadlineDescriptionException extends MissingTaskDescriptionException {
    public MissingDeadlineDescriptionException() {
        super(DeadlineCommand.COMMAND_FORMAT);
    }
}
