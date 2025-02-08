package bhaymax.command;

import java.io.IOException;

import bhaymax.controller.MainWindow;
import bhaymax.storage.Storage;
import bhaymax.task.TaskList;

/**
 * Represents a {@code filter} command
 */
public class FilterCommand extends Command {
    private final String dateTime;
    private final FilterOpt filterOpt;

    /**
     * Sets up the date with which the available tasks
     * will be filtered, along with the nature of the filter
     * (i.e., filter for tasks before the date, after the date,
     * exactly on the date)
     *
     * @param dateTime the date and/or time to filter by, as a {@code String}
     * @param filterOpt a {@link FilterOpt} enum value indicating the type of
     *                  filter (i.e., before the date, after the date, exactly on
     *                  the date, include/exclude time)
     * @see bhaymax.parser.Parser#DATE_FORMAT
     * @see bhaymax.parser.Parser#DATETIME_INPUT_FORMAT
     */
    public FilterCommand(String dateTime, FilterOpt filterOpt) {
        this.dateTime = dateTime;
        this.filterOpt = filterOpt;
    }

    @Override
    public void execute(TaskList taskList, MainWindow mainWindowController, Storage storage) throws IOException {
        taskList.showTasksWithDateFilter(this.dateTime, this.filterOpt, mainWindowController);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
