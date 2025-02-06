package bhaymax.command;

import java.io.IOException;

import bhaymax.main.Bhaymax;
import bhaymax.storage.Storage;
import bhaymax.task.TaskList;
import bhaymax.ui.Ui;

/**
 * Represents a command supported by {@link Bhaymax} Chatbot app
 */
public abstract class Command {
    /**
     * Executes the command
     *
     * @param taskList a {@link TaskList} object containing a list of tasks
     * @param ui a {@link Ui} object - used to print things to UI if applicable
     * @param storage a {@link Storage} object - used to save the list of tasks
     *                provided should it be modified
     * @throws IOException thrown by {@link Storage#saveTasks(TaskList)}
     *                     if an error occurs when saving tasks to file
     */
    public abstract void execute(TaskList taskList, Ui ui, Storage storage) throws IOException;

    /**
     * Returns a boolean value indicating
     * if this command is an exit command
     *
     * @return a boolean value indicating
     *         if this command is an exit command
     */
    public abstract boolean isExit();
}
