package bhaymax.command;

import java.io.IOException;

import bhaymax.storage.Storage;
import bhaymax.task.Task;
import bhaymax.task.TaskList;
import bhaymax.task.timesensitive.Event;
import bhaymax.ui.Ui;

public class EventCommand extends Command {
    private final String taskDescription;
    private final String start;
    private final String end;

    public EventCommand(String taskDescription, String start, String end) {
        this.taskDescription = taskDescription;
        this.start = start;
        this.end = end;
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws IOException {
        Task task = new Event(this.taskDescription, this.start, this.end);
        int taskListCount = taskList.addTask(task);
        storage.saveTasks(taskList);
        ui.printWithIndent("Got it. I've added this task:", true);
        ui.printWithIndent("  " + task, true);
        ui.printWithIndent(
                "Now you have " + taskListCount + " tasks in the list.", true);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
