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
        Task deletedTask = pair.t();
        int numberOfRemainingTasks = pair.u();
        storage.saveTasks(taskList);
        String response = "Noted. Removing: " + System.lineSeparator()
                + "  " + deletedTask + System.lineSeparator()
                + "from your list of tasks." + System.lineSeparator();
        if (numberOfRemainingTasks == 0) {
            mainWindowController.showResponse(response);
            mainWindowController.showExcitedResponse("Congratulations! You don't have any outstanding tasks!");
            return;
        }
        response += "You now have " + numberOfRemainingTasks + " task"
                + (numberOfRemainingTasks == 1 ? "" : "s") + " to complete.";
        mainWindowController.showResponse(response);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
