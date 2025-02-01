package bhaymax.command;

import java.io.IOException;

import bhaymax.storage.Storage;
import bhaymax.task.TaskList;
import bhaymax.ui.Ui;

public abstract class Command {
    public abstract void execute(TaskList taskList, Ui ui, Storage storage) throws IOException;
    public abstract boolean isExit();
}
