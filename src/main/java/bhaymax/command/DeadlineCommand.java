package bhaymax.command;

import java.io.IOException;

import bhaymax.controller.MainWindow;
import bhaymax.storage.Storage;
import bhaymax.task.TaskList;
import bhaymax.task.timesensitive.Deadline;

/**
 * Represents a {@code deadline} command
 */
public class DeadlineCommand extends Command {
    private final String taskDescription;
    private final String deadline;

    /**
     * Sets up the description and date of the deadline task to be created
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
        Deadline newDeadline = new Deadline(this.taskDescription, this.deadline);
        int taskListCount = taskList.addTask(newDeadline);
        storage.saveTasks(taskList);
        String response = "Noted. Adding: " + System.lineSeparator()
                + "  " + newDeadline + System.lineSeparator()
                + "to your list of deadlines." + System.lineSeparator()
                + "You now have " + taskListCount + " task" + (taskListCount == 1 ? "" : "s") + " to complete.";
        mainWindowController.showResponse(response);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
