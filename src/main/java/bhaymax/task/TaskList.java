package bhaymax.task;

import java.util.Comparator;
import java.util.LinkedList;

import bhaymax.command.FilterOption;
import bhaymax.controller.MainWindow;
import bhaymax.task.timesensitive.TimeSensitiveTask;
import bhaymax.util.Pair;

/**
 * Represents a list of tasks
 */
public class TaskList {
    public static final String TASK_LIST_EMPTY = "Congratulations! You don't have any outstanding tasks!";
    public static final String TASK_LIST_BULLET_POINT_SEPARATOR = ". ";

    public static final String NO_MATCH_FILTER = "There are no tasks in your list for me to filter.";
    public static final String NO_MATCH_SEARCH = "There are no tasks that match the search term you provided.";

    private final LinkedList<Task> tasks;

    public TaskList() {
        this.tasks = new LinkedList<>();
    }

    /**
     * Checks whether a given index number for a task is valid
     * (in other words, to see if a task with the given index number exists)
     *
     * @param index the index number of a task
     * @return a boolean value indicating if the index number is valid
     */
    public boolean isValidIndex(int index) {
        return index >= 0 && index < this.tasks.size();
    }

    /**
     * Adds a task to the list of tasks
     *
     * @param task an object of {@link Task} type to be added
     * @return the number of tasks in the list after adding the new task
     */
    public int addTask(Task task) {
        this.tasks.add(task);
        return this.tasks.size();
    }

    /**
     * Marks a task as completed
     *
     * @param index the index number of the task to be marked as completed
     * @return the {@link Task} that was marked as completed
     */
    public Task markTaskAsDone(int index) {
        Task taskToBeMarked = this.tasks.get(index);
        taskToBeMarked.markAsDone();
        this.tasks.set(index, taskToBeMarked);
        return taskToBeMarked;
    }

    /**
     * Marks a task as incomplete
     *
     * @param index the index number of the task to be marked as incomplete
     * @return the {@link Task} that was marked as incomplete
     */
    public Task markTaskAsUndone(int index) {
        Task taskToBeMarked = this.tasks.get(index);
        taskToBeMarked.markAsUndone();
        this.tasks.set(index, taskToBeMarked);
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
        Task taskToBeRemoved = this.tasks.get(index);
        this.tasks.remove(taskToBeRemoved);
        return new Pair<Task, Integer>(taskToBeRemoved, this.tasks.size());
    }

    /**
     * Shows the tasks in this list as dialog boxes in the chatbot
     *
     * @param mainWindowController the {@link MainWindow} controller object - used to
     *                             display dialog boxes with the tasks in this list
     */
    public void showTasks(MainWindow mainWindowController) {
        this.tasks.sort(Task::compareTo);
        this.tasks.stream()
                //.sorted()
                .map(task -> (tasks.indexOf(task) + 1) + TaskList.TASK_LIST_BULLET_POINT_SEPARATOR + task)
                .reduce((previousTask, nextTask)
                        -> previousTask + System.lineSeparator() + nextTask)
                .ifPresentOrElse(mainWindowController::showNormalResponse, () -> mainWindowController
                        .showExcitedResponse(TaskList.TASK_LIST_EMPTY));
    }

    /**
     * Displays the tasks in the list (as dialog boxes) that match the provided date (and optionally time) frame
     *
     * @param dateTime the date and/or time to filter the list by
     * @param filterOption the nature of the filter (i.e., show tasks
     *                     before the date, after the date, exactly on the date,
     *                     with/without time)
     * @param mainWindowController the {@link MainWindow} controller object - used to
     *                             display dialog boxes with the matched tasks in this list
     * @see bhaymax.parser.Parser#DATE_INPUT_FORMAT
     * @see bhaymax.parser.Parser#DATETIME_INPUT_FORMAT
     */
    public void showTasksFilteredByDate(
            String dateTime, FilterOption filterOption, MainWindow mainWindowController) {
        this.tasks.sort(Task::compareTo);
        String response = this.tasks.stream()
                //.sorted()
                .filter(task -> task instanceof TimeSensitiveTask)
                .filter(timeSensitiveTask -> (
                        (TimeSensitiveTask) timeSensitiveTask).hasDateMatchingFilter(dateTime, filterOption))
                .map(task -> (this.tasks.indexOf(task) + 1) + TaskList.TASK_LIST_BULLET_POINT_SEPARATOR + task)
                .reduce((previousTask, nextTask)
                        -> previousTask + System.lineSeparator() + nextTask)
                .orElse(TaskList.NO_MATCH_FILTER);
        mainWindowController.showNormalResponse(response);
    }

    /**
     * Displays the tasks in the list (in dialog boxes) that contain the provided search term
     *
     * @param searchTerm the search term to search for
     * @param mainWindowController the {@link MainWindow} controller object - used to
     *                             display dialog boxes with the matched tasks in this list
     */
    public void showTasksContainingSearchTerm(String searchTerm, MainWindow mainWindowController) {
        this.tasks.sort(Task::compareTo);
        String response = this.tasks.stream()
                //.sorted()
                .filter(task -> task.hasSearchTerm(searchTerm))
                .map(task -> (this.tasks.indexOf(task) + 1) + TaskList.TASK_LIST_BULLET_POINT_SEPARATOR + task)
                .reduce((previousTask, nextTask)
                        -> previousTask + System.lineSeparator() + nextTask)
                .orElse(TaskList.NO_MATCH_SEARCH);
        mainWindowController.showNormalResponse(response);
    }

    /**
     * Returns a {@code String} representation of the {@code TaskList} object that is suitable for saving to a file
     *
     * @return the {@code String} representation of the {@code TaskList} object that is suitable for saving to a file
     */
    public String serialise() {
        this.tasks.sort(Task::compareTo);
        return this.tasks.stream()
                //.sorted()
                .map(Task::serialise)
                .reduce((task1, task2)
                        -> task1 + System.lineSeparator() + task2)
                .orElse("");
    }
}
