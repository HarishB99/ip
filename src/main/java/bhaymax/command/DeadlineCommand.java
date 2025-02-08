package bhaymax.command;

import java.io.IOException;

import bhaymax.controller.MainWindow;
import bhaymax.storage.Storage;
import bhaymax.task.Task;
import bhaymax.task.TaskList;
import bhaymax.task.timesensitive.Deadline;

/**
 * Represents a {@code deadline} command
 */
public class DeadlineCommand extends Command {
    private final String taskDescription;
    private final String deadline;

    /**
     * Sets up the description and date
     * of the deadline task to be created
     *
     * @param taskDescription the description of the deadline (task)
     * @param deadline the date and time the task will be due, as a {@code String}
     * @see bhaymax.parser.Parser#DATETIME_INPUT_FORMAT
     */
    public DeadlineCommand(String taskDescription, String deadline) {
        this.taskDescription = taskDescription;
        this.deadline = deadline;
    }

    @Override
    public void execute(TaskList taskList, MainWindow mainWindowController, Storage storage) throws IOException {
        Task task = new Deadline(this.taskDescription, this.deadline);
        int taskListCount = taskList.addTask(task);
        storage.saveTasks(taskList);
        String response = "Got it. I've added this task:" + System.lineSeparator()
                + "  " + task + System.lineSeparator()
                + "Now you have " + taskListCount + " tasks in the list.";
        mainWindowController.showResponse(response);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
