package bhaymax.command;

import java.io.IOException;

import bhaymax.storage.Storage;
import bhaymax.task.Task;
import bhaymax.task.TaskList;
import bhaymax.ui.Ui;

/**
 * Represents a {@code unmark} command
 */
public class UnmarkCommand extends Command {
    private final int taskNumber;

    /**
     * Sets up the task number of the task to be marked as incomplete
     *
     * @param taskNumber the index number of the task to be marked as incomplete
     */
    public UnmarkCommand(int taskNumber) {
        this.taskNumber = taskNumber;
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws IOException {
        Task markedTask = taskList.markTaskAsUndone(this.taskNumber);
        storage.saveTasks(taskList);
        ui.printWithIndent(
                "OK, I've marked this task as not done yet:", true);
        ui.printWithIndent(
                "  " + markedTask, true);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
