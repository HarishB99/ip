package bhaymax.command;

import bhaymax.controller.MainWindow;
import bhaymax.exception.file.FileWriteException;
import bhaymax.storage.Storage;
import bhaymax.task.TaskList;
import bhaymax.task.timesensitive.Deadline;

/**
 * Represents a {@code deadline} command
 */
public class DeadlineCommand extends Command {
    private static final String RESPONSE_FORMAT = "Noted. Adding: " + System.lineSeparator()
            + "  %s" + System.lineSeparator()
            + "to your list of deadlines." + System.lineSeparator()
            + "You now have %d task%s to complete.";

    private final String taskDescription;
    private final String deadline;

    /**
     * Sets up the description and date of the deadline task to be created
     *
     * @param taskDescription the description of the deadline
     * @param deadline the date and time the deadline will be due by, as a {@code String}
     * @see bhaymax.parser.Parser#DATETIME_INPUT_FORMAT
     */
    public DeadlineCommand(String taskDescription, String deadline) {
        this.taskDescription = taskDescription;
        this.deadline = deadline;
    }

    @Override
    public void execute(TaskList taskList, MainWindow mainWindowController, Storage storage) throws FileWriteException {
        Deadline newDeadline = new Deadline(this.taskDescription, this.deadline);
        int taskListCount = taskList.addTask(newDeadline);
        storage.saveTasks(taskList);
        String response = String.format(
                DeadlineCommand.RESPONSE_FORMAT,
                newDeadline,
                taskListCount,
                taskListCount == 1 ? "" : "s");
        mainWindowController.showResponse(response);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
