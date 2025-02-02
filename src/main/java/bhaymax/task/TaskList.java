package bhaymax.task;

import java.util.LinkedList;
import java.util.List;

import bhaymax.command.FilterOpt;
import bhaymax.task.timesensitive.TimeSensitiveTask;
import bhaymax.ui.Ui;
import bhaymax.util.Pair;

/**
 * Represents a list of tasks
 */
public class TaskList {
    private final LinkedList<Task> taskList;

    public TaskList() {
        taskList = new LinkedList<>();
    }

    /**
     * Checks whether a given index number for a task
     * is valid (in other words, to see if a task with
     * the given index number exists)
     *
     * @param index the index number of a task
     * @return a boolean value indicating if the index number
     *         is valid
     */
    public boolean isValidIndex(int index) {
        return index >= 0 && index < this.taskList.size();
    }

    /**
     * Adds a task to the list of tasks
     *
     * @param task an object of {@link Task} type to be added
     * @return the number of tasks in the list after adding
     *         the new task
     */
    public int addTask(Task task) {
        this.taskList.add(task);
        return this.taskList.size();
    }

    /**
     * Marks a task as completed
     *
     * @param index the index number of the task to be marked as completed
     * @return the {@link Task} that was marked as completed
     */
    public Task markTaskAsDone(int index) {
        Task taskToBeMarked = this.taskList.get(index);
        taskToBeMarked.markAsDone();
        this.taskList.set(index, taskToBeMarked);
        return taskToBeMarked;
    }

    /**
     * Marks a task as incomplete
     *
     * @param index the index number of the task to be marked as incomplete
     * @return the {@link Task} that was marked as incomplete
     */
    public Task markTaskAsUndone(int index) {
        Task taskToBeMarked = this.taskList.get(index);
        taskToBeMarked.markAsUndone();
        this.taskList.set(index, taskToBeMarked);
        return taskToBeMarked;
    }

    /**
     * Removes a task from the list of tasks
     *
     * @param index the index number of the task to be removed
     * @return a {@link Pair} object containing the {@link Task}
     *         that was removed and the number of remaining tasks
     *         in the list, in that order
     */
    public Pair<Task, Integer> removeTask(int index) {
        Task taskToBeRemoved = this.taskList.get(index);
        this.taskList.remove(taskToBeRemoved);
        return new Pair<Task, Integer>(taskToBeRemoved, this.taskList.size());
    }

    /**
     * Prints the tasks in the list
     * that contain the provided
     * search term
     *
     * @param searchTerm the search term to search for
     * @param ui the {@link Ui} object - will be used for printing the tasks to the CLI-UI
     */
    public void printTasksContainingSearchTerm(String searchTerm, Ui ui) {
        List<Task> filteredTasks = this.taskList.stream()
                .filter(task -> task.hasSearchTerm(searchTerm))
                .toList();

        if (filteredTasks.isEmpty()) {
            ui.printWithIndent("No tasks match the given search term", true);
            return;
        }

        for (Task task : filteredTasks) {
            ui.printWithIndent((this.taskList.indexOf(task) + 1)
                            + "." + task,
                    true);
        }
    }

    /**
     * Prints the tasks in the list
     * that match the provided date
     * filter
     *
     * @param dateTime the date and/or time to filter the list by
     * @param filterOpt the nature of the filter (i.e., show tasks
     *                  before the date, after the date, exactly on the date,
     *                  with/without time)
     * @param ui the {@link Ui} object - will be used for printing the tasks to the CLI-UI
     * @see bhaymax.parser.Parser#DATE_FORMAT
     * @see bhaymax.parser.Parser#DATETIME_INPUT_FORMAT
     */
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
            ui.printWithIndent((this.taskList.indexOf(timeSensitiveTask) + 1)
                            + "." + timeSensitiveTask,
                    true);
        }
    }

    /**
     * Returns a {@code String} representation
     * of the {@code TaskList} object that is suitable for
     * saving to a file
     *
     * @return the {@code String} representation of the
     *         {@code TaskList} object that is suitable
     *         for saving to a file
     */
    public String serialise() {
        return this.taskList.stream()
                .map(Task::serialise)
                .reduce((task1, task2) -> task1 + System.lineSeparator() + task2)
                .orElse("");
    }

    /**
     * Prints the tasks in the list
     *
     * @param ui the {@link Ui} object - will be used for printing the tasks to the CLI-UI
     */
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
