public class ListCommand extends Command {
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) {
        taskList.printTasks(ui);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
