package bhaymax.exception.command.event;

import bhaymax.command.EventCommand;
import bhaymax.exception.command.MissingTaskDescriptionException;

/**
 * Thrown when a description is not provided for an event
 */
public class MissingEventDescriptionException extends MissingTaskDescriptionException {
    public MissingEventDescriptionException() {
        super(EventCommand.COMMAND_FORMAT);
    }
}
