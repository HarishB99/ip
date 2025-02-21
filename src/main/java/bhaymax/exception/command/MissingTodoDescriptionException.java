package bhaymax.exception.command;

import bhaymax.command.TodoCommand;

/**
 * Thrown when a description is not provided for a to-do item
 */
public class MissingTodoDescriptionException extends MissingTaskDescriptionException {
    public MissingTodoDescriptionException() {
        super(TodoCommand.COMMAND_FORMAT);
    }
}
