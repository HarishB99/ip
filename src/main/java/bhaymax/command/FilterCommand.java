package bhaymax.command;

import bhaymax.controller.MainWindow;
import bhaymax.exception.command.InvalidCommandFormatException;
import bhaymax.parser.Parser;
import bhaymax.storage.Storage;
import bhaymax.task.TaskList;

/**
 * Represents a {@code filter} command
 */
public class FilterCommand extends Command {
    public static final String COMMAND_FORMAT = "filter "
            + "{/on | /before | /after | /on_time | /before_time | /after_time} "
            + "{date (for /on | /before | /after): " + Parser.DATE_INPUT_FORMAT
            + " | date and time (for /on_time | /before_time | /after_time): " + Parser.DATETIME_INPUT_FORMAT + "}";

    private final String dateTime;
    private final FilterOption filterOption;

    /**
     * Sets up the timeframe that will be used to filter available tasks,
     * along with the nature of the filter (i.e., filter for tasks before the date,
     * after the date, exactly on the date)
     *
     * @param dateTime the date and/or time to filter by, as a {@code String}
     * @param filterOption a {@link FilterOption} enum value indicating the type of
     *                  filter (i.e., before the date, after the date, exactly on
     *                  the date, include/exclude time)
     * @see bhaymax.parser.Parser#DATE_INPUT_FORMAT
     * @see bhaymax.parser.Parser#DATETIME_INPUT_FORMAT
     */
    public FilterCommand(String dateTime, FilterOption filterOption) {
        this.dateTime = dateTime;
        this.filterOption = filterOption;
    }

    @Override
    public void execute(TaskList taskList, MainWindow mainWindowController, Storage storage)
            throws InvalidCommandFormatException {
        taskList.showTasksFilteredByDate(this.dateTime, this.filterOption, mainWindowController);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
