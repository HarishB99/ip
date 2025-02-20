package bhaymax.exception.command;

import bhaymax.command.DeleteCommand;

/**
 * Thrown when a task number is not provided for a delete command
 */
public class MissingTaskNumberForDeleteException extends MissingTaskNumberException {
    public MissingTaskNumberForDeleteException() {
        super(DeleteCommand.COMMAND_FORMAT);
    }
}
