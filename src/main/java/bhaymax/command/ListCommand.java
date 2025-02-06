package bhaymax.command;

import java.io.IOException;

import bhaymax.controller.MainWindow;
import bhaymax.storage.Storage;
import bhaymax.task.TaskList;
import bhaymax.ui.Ui;

/**
 * Represents a {@code list} command
 */
public class ListCommand extends Command {
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) {
        taskList.printTasks(ui);
    }

    @Override
    public void execute(TaskList taskList, MainWindow mainWindowController, Storage storage) throws IOException {
        taskList.showTasks(mainWindowController);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
