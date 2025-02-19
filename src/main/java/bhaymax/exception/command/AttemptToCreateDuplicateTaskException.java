package bhaymax.exception.command;

import bhaymax.exception.TaskAlreadyExistsException;

/**
 * Thrown when a user attempts to add a task that already exists
 */
public class AttemptToCreateDuplicateTaskException extends InvalidCommandFormatException {
    public AttemptToCreateDuplicateTaskException() {
        super(TaskAlreadyExistsException.ERROR_MESSAGE);
    }
}
