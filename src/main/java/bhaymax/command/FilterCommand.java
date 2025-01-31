package bhaymax.command;

import java.io.IOException;
import bhaymax.ui.Ui;
import bhaymax.task.TaskList;
import bhaymax.storage.Storage;

public class FilterCommand extends Command {
    private final String dateTime;
    private final FilterOpt filterOpt;

    public FilterCommand(String dateTime, FilterOpt filterOpt) {
        this.dateTime = dateTime;
        this.filterOpt = filterOpt;
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws IOException {
        taskList.printTasksWithDateFilter(this.dateTime, this.filterOpt, ui);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
