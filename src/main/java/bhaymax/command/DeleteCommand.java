package bhaymax.command;

import java.io.IOException;

import bhaymax.controller.MainWindow;
import bhaymax.storage.Storage;
import bhaymax.task.Task;
import bhaymax.task.TaskList;
import bhaymax.util.Pair;

/**
 * Represents a {@code delete} command
 */
public class DeleteCommand extends Command {
    private static final String RESPONSE_FORMAT = "Noted. Removing: " + System.lineSeparator()
                + "  %s" + System.lineSeparator()
                + "from your list of tasks." + System.lineSeparator();
    private static final String RESPONSE_EMPTY_TASK_LIST = "Congratulations! You don't have any outstanding tasks!";
    private static final String RESPONSE_FORMAT_NONEMPTY_TASK_LIST = "You now have %d task%s to complete.";

    private final int taskNumber;

    /**
     * Sets up the task number of the task to be deleted
     *
     * @param taskNumber the index number of the task to be deleted
     */
    public DeleteCommand(int taskNumber) {
        this.taskNumber = taskNumber;
    }

    @Override
    public void execute(TaskList taskList, MainWindow mainWindowController, Storage storage) throws IOException {
        Pair<Task, Integer> pair = taskList.removeTask(this.taskNumber);
        Task deletedTask = pair.first();
        int numberOfRemainingTasks = pair.second();
        storage.saveTasks(taskList);
        String response = String.format(RESPONSE_FORMAT, deletedTask);
        if (numberOfRemainingTasks == 0) {
            mainWindowController.showResponse(response);
            mainWindowController.showExcitedResponse(RESPONSE_EMPTY_TASK_LIST);
            return;
        }
        response += String.format(
                RESPONSE_FORMAT_NONEMPTY_TASK_LIST,
                numberOfRemainingTasks,
                numberOfRemainingTasks == 1 ? "" : "s");
        mainWindowController.showResponse(response);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
