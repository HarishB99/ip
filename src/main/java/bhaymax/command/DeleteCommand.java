package bhaymax.command;

import bhaymax.controller.MainWindow;
import bhaymax.exception.file.FileWriteException;
import bhaymax.storage.Storage;
import bhaymax.task.Task;
import bhaymax.task.TaskList;
import bhaymax.util.Pair;

/**
 * Represents a {@code delete} command
 */
public class DeleteCommand extends Command {
    public static final String COMMAND_FORMAT =
            "delete {index number of task to be deleted - use 'list' to find the index}";

    private static final String RESPONSE_FORMAT = "Noted. Removing: " + System.lineSeparator()
                + "  %s" + System.lineSeparator()
                + "from your list of tasks." + System.lineSeparator();
    private static final String RESPONSE_FORMAT_NONEMPTY_TASK_LIST = "You now have %d task%s to complete.";

    private final int taskNumber;

    /**
     * Constructor for {@code DeleteCommand}
     *
     * @param taskNumber the index number of the task to be deleted
     */
    public DeleteCommand(int taskNumber) {
        this.taskNumber = taskNumber;
    }

    @Override
    public void execute(TaskList taskList, MainWindow mainWindowController, Storage storage) throws FileWriteException {
        Pair<Task, Integer> pair = taskList.removeTask(this.taskNumber);
        Task deletedTask = pair.first();
        int numberOfRemainingTasks = pair.second();
        storage.saveTasks(taskList);
        String response = String.format(RESPONSE_FORMAT, deletedTask);

        assert numberOfRemainingTasks >= 0;

        if (numberOfRemainingTasks == 0) {
            mainWindowController.showNormalResponse(response);
            mainWindowController.showExcitedResponse(TaskList.TASK_LIST_EMPTY);
            return;
        }
        response += String.format(
                RESPONSE_FORMAT_NONEMPTY_TASK_LIST,
                numberOfRemainingTasks,
                numberOfRemainingTasks == 1 ? "" : "s");
        mainWindowController.showNormalResponse(response);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
