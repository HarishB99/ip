package bhaymax.command;

import java.io.IOException;

import bhaymax.controller.MainWindow;
import bhaymax.storage.Storage;
import bhaymax.task.TaskList;

/**
 * Represents a {@code search} command
 */
public class SearchCommand extends Command {
    private final String searchTerm;
    private final String dateTime;
    private final FilterOpt filterOpt;

    /**
     * Sets up the search term that will be used to filter a list of tasks
     *
     * @param searchTerm the search term as a {@code String}
     */
    public SearchCommand(String searchTerm) {
        this.searchTerm = searchTerm;
        this.dateTime = "";
        this.filterOpt = FilterOpt.DATE_ON;
    }

    /**
     * Sets up parameters used in search
     *
     * @param searchTerm the search term as a {@code String}
     * @param dateTime the date and/or time to filter by, as a {@code String}
     * @param filterOpt a {@link FilterOpt} enum value indicating the type of
     *                  filter (i.e., before the date, after the date, exactly on
     *                  the date, include/exclude time)
     */
    public SearchCommand(String searchTerm, String dateTime, FilterOpt filterOpt) {
        this.searchTerm = searchTerm;
        this.dateTime = dateTime;
        this.filterOpt = filterOpt;
    }

    @Override
    public void execute(TaskList taskList, MainWindow mainWindowController, Storage storage) throws IOException {
        taskList.showTasksContainingSearchTerm(this.searchTerm, mainWindowController);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
