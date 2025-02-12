package bhaymax.command;

import java.io.IOException;

import bhaymax.controller.MainWindow;
import bhaymax.storage.Storage;
import bhaymax.task.TaskList;

/**
 * Represents an {@code exit} command
 */
public class ExitCommand extends Command {
    @Override
    public void execute(TaskList taskList, MainWindow mainWindowController, Storage storage) throws IOException {
        mainWindowController.showFarewellDialogBox();
    }

    @Override
    public boolean isExit() {
        return true;
    }
}
