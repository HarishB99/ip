package bhaymax.exception.command;

import bhaymax.command.UnmarkCommand;

/**
 * Thrown when a task number is not provided for a unmark command
 */
public class MissingTaskNumberForUnmarkException extends MissingTaskNumberException {
    public MissingTaskNumberForUnmarkException() {
        super(UnmarkCommand.COMMAND_FORMAT);
    }
}
