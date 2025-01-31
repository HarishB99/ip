package bhaymax.command;

import java.io.IOException;
import bhaymax.storage.Storage;
import bhaymax.ui.Ui;
import bhaymax.task.Task;
import bhaymax.task.TaskList;

public class UnmarkCommand extends Command {
    private final int taskNumber;

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
