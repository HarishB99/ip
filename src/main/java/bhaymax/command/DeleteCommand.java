package bhaymax.command;

import java.io.IOException;

import bhaymax.storage.Storage;
import bhaymax.task.Task;
import bhaymax.task.TaskList;
import bhaymax.ui.Ui;
import bhaymax.util.Pair;

public class DeleteCommand extends Command {
    private final int taskNumber;

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
    public boolean isExit() {
        return false;
    }
}
