package bhaymax.task;

import java.util.LinkedList;

import bhaymax.command.FilterOpt;
import bhaymax.controller.MainWindow;
import bhaymax.task.timesensitive.TimeSensitiveTask;
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
     * Displays the tasks in the list (in dialog boxes)
     * that contain the provided search term
     *
     * @param searchTerm the search term to search for
     * @param mainWindowController the {@link MainWindow} controller object - used to
     *                             display dialog boxes with the matched tasks in this
     *                             list
     */
    public void showTasksContainingSearchTerm(String searchTerm, MainWindow mainWindowController) {
        String response = this.taskList.stream()
                .filter(task -> task.hasSearchTerm(searchTerm))
                .map(task -> (this.taskList.indexOf(task) + 1) + ". " + task)
                .reduce((previousTask, nextTask)
                        -> previousTask + System.lineSeparator() + nextTask)
                .orElse("No tasks match the given search term");
        mainWindowController.showResponse(response);
    }

    /**
     * Displays the tasks in the list (in dialog boxes)
     * that match the provided date filter
     *
     * @param dateTime the date and/or time to filter the list by
     * @param filterOpt the nature of the filter (i.e., show tasks
     *                  before the date, after the date, exactly on the date,
     *                  with/without time)
     * @param mainWindowController the {@link MainWindow} controller object - used to
     *                             display dialog boxes with the matched tasks in this
     *                             list
     * @see bhaymax.parser.Parser#DATE_FORMAT
     * @see bhaymax.parser.Parser#DATETIME_INPUT_FORMAT
     */
    public void showTasksWithDateFilter(
            String dateTime, FilterOpt filterOpt, MainWindow mainWindowController) {
        if (this.taskList.isEmpty()) {
            mainWindowController.showResponse("There are no tasks to filter.");
            return;
        }

        StringBuilder response = new StringBuilder();

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
            response.append((this.taskList.indexOf(timeSensitiveTask) + 1))
                    .append(". ").append(timeSensitiveTask)
                    .append(System.lineSeparator());
        }

        mainWindowController.showResponse(response.toString());
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
     * Shows the tasks in this list
     * as dialog boxes from the chatbot
     *
     * @param mainWindowController the {@link MainWindow} controller object - used to
     *                             display dialog boxes with the tasks in this list
     */
    public void showTasks(MainWindow mainWindowController) {
        String response = this.taskList.stream()
                .map(task -> (taskList.indexOf(task) + 1) + ". " + task)
                .reduce((previousTask, nextTask) -> previousTask + System.lineSeparator() + nextTask)
                .orElse("You're all caught up! You have no pending tasks!");
        mainWindowController.showResponse(response);
    }
}
