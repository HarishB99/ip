package bhaymax.command;

import bhaymax.controller.MainWindow;
import bhaymax.storage.Storage;
import bhaymax.task.TaskList;

/**
 * Represents a {@code clear} command
 */
public class ClearCommand extends Command {
    @Override
    public void execute(TaskList taskList, MainWindow mainWindowController, Storage storage) {
        mainWindowController.clearChat(true);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
