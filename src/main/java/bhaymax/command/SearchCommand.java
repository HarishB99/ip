package bhaymax.command;

import java.io.IOException;

import bhaymax.controller.MainWindow;
import bhaymax.storage.Storage;
import bhaymax.task.TaskList;
import bhaymax.ui.Ui;

/**
 * Represents a {@code search} command
 */
public class SearchCommand extends Command {
    private final String searchTerm;

    /**
     * Sets up the search term that will be used
     * to filter a list of tasks
     *
     * @param searchTerm the search term as a {@code String}
     */
    public SearchCommand(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws IOException {
        taskList.printTasksContainingSearchTerm(this.searchTerm, ui);
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
