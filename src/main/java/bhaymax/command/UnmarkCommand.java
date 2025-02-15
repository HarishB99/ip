package bhaymax.command;

import bhaymax.controller.MainWindow;
import bhaymax.exception.file.FileWriteException;
import bhaymax.storage.Storage;
import bhaymax.task.Task;
import bhaymax.task.TaskList;

/**
 * Represents a {@code unmark} command
 */
public class UnmarkCommand extends Command {
    private static final String RESPONSE_FORMAT = "Noted. Marking:" + System.lineSeparator()
                + "  %s" + System.lineSeparator()
                + "as incomplete.";

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
    public void execute(TaskList taskList, MainWindow mainWindowController, Storage storage) throws FileWriteException {
        Task markedTask = taskList.markTaskAsUndone(this.taskNumber);
        storage.saveTasks(taskList);
        String response = String.format(RESPONSE_FORMAT, markedTask);
        mainWindowController.showResponse(response);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
