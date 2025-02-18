package bhaymax.command;

import bhaymax.controller.MainWindow;
import bhaymax.exception.command.InvalidCommandFormatException;
import bhaymax.exception.file.FileWriteException;
import bhaymax.storage.Storage;
import bhaymax.task.TaskList;
import bhaymax.task.timesensitive.Event;

/**
 * Represents a {@code event} command
 */
public class EventCommand extends Command {
    private static final String RESPONSE_FORMAT = "Noted. Adding: " + System.lineSeparator()
            + "  %s" + System.lineSeparator()
            + "to your list of events." + System.lineSeparator()
            + "You now have %d task%s to complete.";

    private final String taskDescription;
    private final String start;
    private final String end;

    /**
     * Constructor for {@code EventCommand}
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
    public void execute(TaskList taskList, MainWindow mainWindowController, Storage storage)
            throws FileWriteException, InvalidCommandFormatException {
        Event newEvent = new Event(this.taskDescription, this.start, this.end);
        int taskListCount = taskList.addTask(newEvent);
        storage.saveTasks(taskList);
        String response = String.format(
                RESPONSE_FORMAT,
                newEvent,
                taskListCount,
                taskListCount == 1 ? "" : "s");
        mainWindowController.showNormalResponse(response);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
