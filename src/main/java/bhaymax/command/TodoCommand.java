package bhaymax.command;

import com.sun.javafx.tk.Toolkit;

import bhaymax.controller.MainWindow;
import bhaymax.exception.TaskAlreadyExistsException;
import bhaymax.exception.command.AttemptToCreateDuplicateTaskException;
import bhaymax.exception.command.InvalidCommandFormatException;
import bhaymax.exception.file.FileWriteException;
import bhaymax.storage.Storage;
import bhaymax.task.TaskList;
import bhaymax.task.Todo;

/**
 * Represents a {@code todo} command
 */
public class TodoCommand extends Command {
    private static final String RESPONSE_FORMAT = "Noted. Adding: " + System.lineSeparator()
            + "  %s" + System.lineSeparator()
            + "to your to-do list." + System.lineSeparator()
            + "You now have %d task%s to complete.";

    private final String taskDescription;

    /**
     * Sets up the task of the To-Do task
     *
     * @param taskDescription the description of the to-do task
     */
    public TodoCommand(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    @Override
    public void execute(TaskList taskList, MainWindow mainWindowController, Storage storage)
            throws FileWriteException, InvalidCommandFormatException {
        Todo newTodoTask = new Todo(this.taskDescription);
        int taskListCount;
        try {
            taskListCount = taskList.addTask(newTodoTask);
        } catch (TaskAlreadyExistsException e) {
            throw new AttemptToCreateDuplicateTaskException();
        }
        storage.saveTasks(taskList);
        String response = String.format(
                RESPONSE_FORMAT,
                newTodoTask,
                taskListCount,
                taskListCount == 1 ? "" : "s");
        mainWindowController.showNormalResponse(response);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
