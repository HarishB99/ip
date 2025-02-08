package bhaymax.command;

import java.io.IOException;

import bhaymax.controller.MainWindow;
import bhaymax.storage.Storage;
import bhaymax.task.Task;
import bhaymax.task.TaskList;
import bhaymax.task.timesensitive.Event;

/**
 * Represents a {@code event} command
 */
public class EventCommand extends Command {
    private final String taskDescription;
    private final String start;
    private final String end;

    /**
     * Sets up the description,
     * the start date with time
     * and the end date with time
     * of the event task to be created
     *
     * @param taskDescription the description of the event task
     * @param start the date and time at which the event will start, as a {@code String}
     * @param end the date and time at which the event will end, as a {@code String}
     * @see bhaymax.parser.Parser#DATETIME_INPUT_FORMAT
     */
    public EventCommand(String taskDescription, String start, String end) {
        this.taskDescription = taskDescription;
        this.start = start;
        this.end = end;
    }

    @Override
    public void execute(TaskList taskList, MainWindow mainWindowController, Storage storage) throws IOException {
        Task task = new Event(this.taskDescription, this.start, this.end);
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
