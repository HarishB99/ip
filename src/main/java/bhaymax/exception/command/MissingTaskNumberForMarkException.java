package bhaymax.exception.command;

import bhaymax.command.MarkCommand;

/**
 * Thrown when a task number is not provided for a mark command
 */
public class MissingTaskNumberForMarkException extends MissingTaskNumberException {
    public MissingTaskNumberForMarkException() {
        super(MarkCommand.COMMAND_FORMAT);
    }
}
