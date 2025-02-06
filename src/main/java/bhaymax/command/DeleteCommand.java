package bhaymax.command;

import java.io.IOException;

import bhaymax.controller.MainWindow;
import bhaymax.storage.Storage;
import bhaymax.task.Task;
import bhaymax.task.TaskList;
import bhaymax.ui.Ui;
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
    public void execute(TaskList taskList, Ui ui, Storage storage)
            throws IOException {
        Pair<Task, Integer> pair = taskList.removeTask(this.taskNumber);
        Task deletedTask = pair.t();
        int numberOfRemainingTasks = pair.u();
        storage.saveTasks(taskList);
        ui.printWithIndent(
                "Noted. I've removed this task:", true);
        ui.printWithIndent(
                "  " + deletedTask, true);
        ui.printWithIndent(
                "Now you have " + numberOfRemainingTasks + " tasks in the list.", true);
    }

    @Override
    public void execute(TaskList taskList, MainWindow mainWindowController, Storage storage) throws IOException {
        Pair<Task, Integer> pair = taskList.removeTask(this.taskNumber);
        Task deletedTask = pair.t();
        int numberOfRemainingTasks = pair.u();
        storage.saveTasks(taskList);
        mainWindowController.showResponse("Noted. I've removed this task:");
        mainWindowController.showResponse("  " + deletedTask);
        mainWindowController.showResponse("Now you have " + numberOfRemainingTasks + " tasks in the list.");
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
