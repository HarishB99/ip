package bhaymax.command;

import bhaymax.controller.MainWindow;
import bhaymax.storage.Storage;
import bhaymax.task.TaskList;

/**
 * Represents a {@code search} command
 */
public class SearchCommand extends Command {
    public static final String COMMAND_FORMAT = "search {search term or phrase}";

    private final String searchTerm;

    /**
     * Sets up the search term that will be used to filter a list of tasks
     *
     * @param searchTerm the search term as a {@code String}
     */
    public SearchCommand(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    @Override
    public void execute(TaskList taskList, MainWindow mainWindowController, Storage storage) {
        taskList.showTasksContainingSearchTerm(this.searchTerm, mainWindowController);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
