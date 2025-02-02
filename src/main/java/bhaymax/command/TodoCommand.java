package bhaymax.command;

import java.io.IOException;
import bhaymax.storage.Storage;
import bhaymax.ui.Ui;
import bhaymax.task.Task;
import bhaymax.task.Todo;
import bhaymax.task.TaskList;

/**
 * Represents a {@code todo} command
 */
public class TodoCommand extends Command {
    private final String taskDescription;

    /**
     * Sets up the task of the To-Do task
     *
     * @param taskDescription the description of the to-do task
     */
    public TodoCommand(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws IOException {
        Task task = new Todo(this.taskDescription);
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
