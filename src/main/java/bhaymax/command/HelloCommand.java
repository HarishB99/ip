package bhaymax.command;

import bhaymax.controller.MainWindow;
import bhaymax.storage.Storage;
import bhaymax.task.TaskList;

/**
 * Represents a {@code hello} command
 */
public class HelloCommand extends Command {
    @Override
    public void execute(TaskList taskList, MainWindow mainWindowController, Storage storage) {
        mainWindowController.showGreetingDialogBox();
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
