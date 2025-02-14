package bhaymax.task;

import java.util.Scanner;
import java.util.regex.MatchResult;

import bhaymax.exception.InvalidTaskStringFormatException;

/**
 * Represents a To-Do task
 */
public class Todo extends Task {
    public static final String TYPE = "T";

    private static final String DESERIAL_FORMAT = "^T \\| ([0-1]) \\| (.+)$";
    private static final int DESERIAL_FORMAT_NUMBER_OF_ITEMS = 2;
    private static final int TODO_STATUS_GROUP = 1;
    private static final int TODO_DESCRIPTION_GROUP = 2;

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
    public static Todo deserialise(String serialisedTodo) {
        Scanner sc = new Scanner(serialisedTodo).useDelimiter(Task.DELIMITER);
        sc.findInLine(Todo.DESERIAL_FORMAT);
        MatchResult matchResult = sc.match();
        sc.close();

        if (matchResult.groupCount() != Todo.DESERIAL_FORMAT_NUMBER_OF_ITEMS) {
            throw new InvalidTaskStringFormatException(
                    "Todo Item in file should be of format 'T | {0,1} | {description}'");
        }

        String taskStatus = matchResult.group(TODO_STATUS_GROUP);
        String taskDescription = matchResult.group(TODO_DESCRIPTION_GROUP);
        Todo todo = new Todo(taskDescription);
        int taskStatusNumber = Integer.parseInt(taskStatus);
        if (taskStatusNumber == 1) {
            todo.markAsDone();
        } else if (taskStatusNumber == 0) {
            todo.markAsUndone();
        } else {
            throw new InvalidTaskStringFormatException("Invalid value encountered for task status.");
        }
        return todo;
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
