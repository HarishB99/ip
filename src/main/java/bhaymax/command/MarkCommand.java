package bhaymax.command;

import bhaymax.controller.MainWindow;
import bhaymax.exception.file.FileWriteException;
import bhaymax.storage.Storage;
import bhaymax.task.Task;
import bhaymax.task.TaskList;

/**
 * Represents a {@code mark} command
 */
public class MarkCommand extends Command {
    public static final String COMMAND_FORMAT =
            "mark {index number of task to be marked as completed - use 'list' to find the index}";

    private static final String RESPONSE_FORMAT = "Congratulations on completing the task:" + System.lineSeparator()
                + "  %s" + System.lineSeparator()
                + "I have marked it as complete.";

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
    public void execute(TaskList taskList, MainWindow mainWindowController, Storage storage) throws FileWriteException {
        Task markedTask = taskList.markTaskAsDone(this.taskNumber);
        storage.saveTasks(taskList);
        String response = String.format(RESPONSE_FORMAT, markedTask);
        mainWindowController.showExcitedResponse(response);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
