package bhaymax.command;

import java.io.IOException;

import bhaymax.controller.MainWindow;
import bhaymax.storage.Storage;
import bhaymax.task.Task;
import bhaymax.task.TaskList;

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
    public void execute(TaskList taskList, MainWindow mainWindowController, Storage storage) throws IOException {
        Task markedTask = taskList.markTaskAsUndone(this.taskNumber);
        storage.saveTasks(taskList);
        String response = "Noted. Marking:" + System.lineSeparator()
                + "  " + markedTask + System.lineSeparator()
                + "as incomplete.";
        mainWindowController.showResponse(response);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
