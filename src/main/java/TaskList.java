import java.util.LinkedList;

public class TaskList {
    private final LinkedList<Task> taskList;

    public TaskList() {
        taskList = new LinkedList<>();
    }

    public boolean isValidIndex(int index) {
        return index >= 0 && index < this.taskList.size();
    }

    public int addTask(Task task) {
        this.taskList.add(task);
        return this.taskList.size();
    }

    public Task markTaskAsDone(int index) {
        Task taskToBeMarked = this.taskList.get(index);
        taskToBeMarked.markAsDone();
        this.taskList.set(index, taskToBeMarked);
        return taskToBeMarked;
    }

    public Task markTaskAsUndone(int index) {
        Task taskToBeMarked = this.taskList.get(index);
        taskToBeMarked.markAsUndone();
        this.taskList.set(index, taskToBeMarked);
        return taskToBeMarked;
    }

    public Pair<Task, Integer> removeTask(int index) {
        Task taskToBeRemoved = this.taskList.get(index);
        this.taskList.remove(taskToBeRemoved);
        return new Pair<Task, Integer>(taskToBeRemoved, this.taskList.size());
    }

    public void printTasksWithDateFilter(String dateTime, FilterOpt filterOpt, Ui ui) {
        if (this.taskList.isEmpty()) {
            ui.printWithIndent("There are no tasks to filter.", true);
            return;
        }

        for (Task task : this.taskList) {
            if (!(task instanceof TimeSensitiveTask timeSensitiveTask)) {
                continue;
            }
            switch (filterOpt) {
            case DATE_ON:
                if (!timeSensitiveTask.isOnDate(dateTime)) {
                    continue;
                }
                break;
            case DATE_BEFORE:
                if (!timeSensitiveTask.isBeforeDate(dateTime)) {
                    continue;
                }
                break;
            case DATE_AFTER:
                if (!timeSensitiveTask.isAfterDate(dateTime)) {
                    continue;
                }
                break;
            case TIME_ON:
                if (!timeSensitiveTask.isOnDateTime(dateTime)) {
                    continue;
                }
                break;
            case TIME_BEFORE:
                if (!timeSensitiveTask.isBeforeDateTime(dateTime)) {
                    continue;
                }
                break;
            case TIME_AFTER:
                if (!timeSensitiveTask.isAfterDateTime(dateTime)) {
                    continue;
                }
                break;
            default:
                continue;
            }
            ui.printWithIndent(
                    this.taskList.indexOf(timeSensitiveTask) + "." + timeSensitiveTask,
                    true);
        }
    }

    public String serialise() {
        return this.taskList.stream()
                .map(Task::serialise)
                .reduce((task1, task2) -> task1 + System.lineSeparator() + task2)
                .orElse("");
    }

    public void printTasks(Ui ui) {
        if (this.taskList.isEmpty()) {
            ui.printWithIndent(
                    "You're all caught up! You have no pending tasks!", true);
        } else {
            ui.printWithIndent("Here are the tasks in your list:", true);
        }
        for (int i = 0; i < this.taskList.size(); i++) {
            ui.printWithIndent((i + 1) + "." + taskList.get(i), true);
        }
    }
}
