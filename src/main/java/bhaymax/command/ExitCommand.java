package bhaymax.command;

import bhaymax.ui.Ui;
import bhaymax.task.TaskList;
import bhaymax.storage.Storage;

/**
 * Represents an {@code exit} command
 */
public class ExitCommand extends Command {
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) {
        ui.showFarewell();
    }

    @Override
    public boolean isExit() {
        return true;
    }
}
