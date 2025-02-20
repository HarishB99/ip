package bhaymax.exception.command;

import bhaymax.command.EventCommand;

/**
 * Thrown when a description is not provided for an event
 */
public class MissingEventDescriptionException extends MissingTaskDescriptionException {
    public MissingEventDescriptionException() {
        super(EventCommand.COMMAND_FORMAT);
    }
}
