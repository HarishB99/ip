package bhaymax.command;

import java.io.IOException;

import bhaymax.controller.MainWindow;
import bhaymax.storage.Storage;
import bhaymax.task.TaskList;

/**
 * Represents a command supported by {@code Bhaymax} Chatbot app
 */
public abstract class Command {
    /**
     * Executes the command
     *
     * @param taskList             a {@link TaskList} object containing a list of tasks
     * @param mainWindowController the {@link MainWindow} controller object - used to
     *                             display dialog boxes with the appropriate response
     * @param storage              a {@link Storage} object - used to save the list of tasks
     *                             provided should it be modified
     * @throws IOException thrown by {@link Storage#saveTasks(TaskList)}
     *                     if an error occurs when saving tasks to file
     */
    public abstract void execute(TaskList taskList, MainWindow mainWindowController, Storage storage)
            throws IOException;

    /**
     * Returns a boolean value indicating if this command is an exit command
     *
     * @return a boolean value indicating
     *         if this command is an exit command
     */
    public abstract boolean isExit();
}
