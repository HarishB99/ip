package bhaymax.task;

import java.util.Scanner;
import java.util.regex.MatchResult;

import bhaymax.exception.file.TaskDeSerialisationException;

/**
 * Represents a To-Do task
 */
public class Todo extends Task {
    public static final String TYPE = "T";
    public static final String ERROR_WRONG_TASK_FORMAT = Task.getErrorWrongTaskStatus(
            "Todo Item", Todo.TYPE, "");


    private static final String DE_SERIAL_FORMAT = "^T \\| ([0-1]) \\| (.+)$";
    private static final int DE_SERIAL_FORMAT_NUMBER_OF_ITEMS = 2;

    private static final int TODO_STATUS_GROUP = 1;
    private static final int TODO_DESCRIPTION_GROUP = 2;

    private static final String TODO_DONE = "1";
    private static final String TODO_NOT_DONE = "0";

    /**
     * Sets up the description of the To-Do task
     *
     * @param description the description of the To-Do task
     */
    public Todo(String description) {
        super(Todo.TYPE, description);
    }

    /**
     * Returns a {@code Todo} object by parsing
     * a given serialised to-do item, as a {@code String}
     *
     * @param serialisedTodo the serialised to-do, as a {@code String}
     * @return a {@code Todo} object
     */
    public static Todo deSerialise(int lineNumber, String serialisedTodo) throws TaskDeSerialisationException {
        Scanner sc = new Scanner(serialisedTodo).useDelimiter(Task.DELIMITER);
        sc.findInLine(Todo.DE_SERIAL_FORMAT);
        MatchResult matchResult = sc.match();
        sc.close();

        if (matchResult.groupCount() != Todo.DE_SERIAL_FORMAT_NUMBER_OF_ITEMS) {
            throw new TaskDeSerialisationException(lineNumber, Todo.ERROR_WRONG_TASK_FORMAT);
        }

        String taskStatus = matchResult.group(TODO_STATUS_GROUP);
        String taskDescription = matchResult.group(TODO_DESCRIPTION_GROUP);
        Todo todo = new Todo(taskDescription);
        if (taskStatus.equals(Todo.TODO_DONE)) {
            todo.markAsDone();
        } else if (taskStatus.equals(Todo.TODO_NOT_DONE)) {
            todo.markAsUndone();
        } else {
            throw new TaskDeSerialisationException(lineNumber, Task.ERROR_WRONG_TASK_STATUS);
        }
        return todo;
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
