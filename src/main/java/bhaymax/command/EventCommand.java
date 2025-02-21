package bhaymax.command;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import bhaymax.controller.MainWindow;
import bhaymax.exception.TaskAlreadyExistsException;
import bhaymax.exception.command.AttemptToCreateDuplicateTaskException;
import bhaymax.exception.command.InvalidCommandFormatException;
import bhaymax.exception.command.InvalidDateTimeFormatInCommandException;
import bhaymax.exception.command.event.InvalidTimeRangeForEventException;
import bhaymax.exception.file.FileWriteException;
import bhaymax.parser.Parser;
import bhaymax.storage.Storage;
import bhaymax.task.TaskList;
import bhaymax.task.timesensitive.Event;

/**
 * Represents a {@code event} command
 */
public class EventCommand extends Command {
    public static final String COMMAND_FORMAT = "event {description} /from " + Event.START_DATE_INPUT_FORMAT
            + " /to " + Event.END_DATE_INPUT_FORMAT;

    private static final String RESPONSE_FORMAT = "Noted. Adding: " + System.lineSeparator()
            + "  %s" + System.lineSeparator()
            + "to your list of events." + System.lineSeparator()
            + "You now have %d task%s to complete.";

    private final String taskDescription;
    private final LocalDateTime start;
    private final LocalDateTime end;

    /**
     * Constructor for {@code EventCommand}
     *
     * @param taskDescription the description of the event task
     * @param start the date and time at which the event will start, as a {@code String}
     * @param end the date and time at which the event will end, as a {@code String}
     * @see bhaymax.parser.Parser#DATETIME_INPUT_FORMAT
     */
    public EventCommand(String taskDescription, String start, String end)
            throws InvalidDateTimeFormatInCommandException, InvalidTimeRangeForEventException {
        try {
            this.taskDescription = taskDescription;
            this.start = LocalDateTime.parse(start, DateTimeFormatter.ofPattern(Parser.DATETIME_INPUT_FORMAT));
            this.end = LocalDateTime.parse(end, DateTimeFormatter.ofPattern(Parser.DATETIME_INPUT_FORMAT));
            if (this.end.isBefore(this.start)) {
                throw new InvalidTimeRangeForEventException();
            }
        } catch (DateTimeParseException e) {
            throw new InvalidDateTimeFormatInCommandException();
        }
    }

    @Override
    public void execute(TaskList taskList, MainWindow mainWindowController, Storage storage)
            throws FileWriteException, InvalidCommandFormatException {
        Event newEvent = new Event(this.taskDescription, this.start, this.end);
        int taskListCount;
        try {
            taskListCount = taskList.addTask(newEvent);
        } catch (TaskAlreadyExistsException e) {
            throw new AttemptToCreateDuplicateTaskException();
        }
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
