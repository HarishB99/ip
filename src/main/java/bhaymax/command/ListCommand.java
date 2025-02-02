package bhaymax.command;

import bhaymax.ui.Ui;
import bhaymax.task.TaskList;
import bhaymax.storage.Storage;

/**
 * Represents a {@code list} command
 */
public class ListCommand extends Command {
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) {
        taskList.printTasks(ui);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
