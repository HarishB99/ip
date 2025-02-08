package bhaymax.command;

import java.io.IOException;

import bhaymax.controller.MainWindow;
import bhaymax.storage.Storage;
import bhaymax.task.Task;
import bhaymax.task.TaskList;

/**
 * Represents a {@code mark} command
 */
public class MarkCommand extends Command {
    private final int taskNumber;

    /**
     * Sets up the task number of the task to be marked as completed
     *
     * @param taskNumber the index number of the task to be marked as completed
     */
    public MarkCommand(int taskNumber) {
        this.taskNumber = taskNumber;
    }

    @Override
    public void execute(TaskList taskList, MainWindow mainWindowController, Storage storage) throws IOException {
        Task markedTask = taskList.markTaskAsDone(this.taskNumber);
        storage.saveTasks(taskList);
        String response = "Nice! I've marked this task as done:" + System.lineSeparator()
                + "  " + markedTask;
        mainWindowController.showResponse(response);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
