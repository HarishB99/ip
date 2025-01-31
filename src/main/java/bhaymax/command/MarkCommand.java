package bhaymax.command;

import java.io.IOException;
import bhaymax.ui.Ui;
import bhaymax.task.Task;
import bhaymax.task.TaskList;
import bhaymax.storage.Storage;

public class MarkCommand extends Command {
    private final int taskNumber;

    public MarkCommand(int taskNumber) {
        this.taskNumber = taskNumber;
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws IOException  {
        Task markedTask = taskList.markTaskAsDone(this.taskNumber);
        storage.saveTasks(taskList);
        ui.printWithIndent(
                "Nice! I've marked this task as done:", true);
        ui.printWithIndent(
                "  " + markedTask, true);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
