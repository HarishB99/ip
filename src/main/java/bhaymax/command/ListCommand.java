package bhaymax.command;

import bhaymax.controller.MainWindow;
import bhaymax.storage.Storage;
import bhaymax.task.TaskList;

/**
 * Represents a {@code list} command
 */
public class ListCommand extends Command {
    public static final String COMMAND_FORMAT = "list";

    @Override
    public void execute(TaskList taskList, MainWindow mainWindowController, Storage storage) {
        taskList.showTasks(mainWindowController);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
