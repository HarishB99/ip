import java.io.IOException;

public class TodoCommand extends Command {
    private final String taskDescription;

    public TodoCommand(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws IOException {
        Task task = new Todo(this.taskDescription);
        int taskListCount = taskList.addTask(task);
        storage.saveTasks(taskList);
        ui.printWithIndent("Got it. I've added this task:", true);
        ui.printWithIndent("  " + task, true);
        ui.printWithIndent(
                "Now you have " + taskListCount + " tasks in the list.", true);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
