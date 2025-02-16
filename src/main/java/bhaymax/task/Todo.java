package bhaymax.task;

import java.util.Scanner;
import java.util.regex.MatchResult;

import bhaymax.exception.file.InvalidTaskStatusException;
import bhaymax.exception.file.TaskDeSerialisationException;
import bhaymax.exception.file.WrongTaskFormatException;

/**
 * Represents a To-Do task
 */
public class Todo extends Task {
    public static final String TYPE = "T";
    public static final String NAME = "Todo Item";

    private static final String DE_SERIALISATION_FORMAT = "^T \\| ([0-1]) \\| (.+)$";

    private static final int EXPECTED_NUMBER_OF_REGEX_GROUPS = 2;
    private static final int REGEX_GROUP_STATUS = 1;
    private static final int REGEX_GROUP_DESCRIPTION = 2;

    private static final String TODO_COMPLETE = "1";
    private static final String TODO_INCOMPLETE = "0";

    /**
     * Constructor for To-Do task
     *
     * @param description the description of the To-Do task
     */
    public Todo(String description) {
        super(Todo.TYPE, description);
    }

    /**
     * Parses a serialised To-Do item (provided as a {@code String})
     * and returns the corresponding {@code Todo} object
     *
     * @param serialisedTodo the serialised to-do, as a {@code String}
     * @return a {@code Todo} object
     */
    public static Todo deSerialise(int lineNumber, String serialisedTodo) throws TaskDeSerialisationException {
        MatchResult matchResult = Todo.getMatchResult(lineNumber, serialisedTodo);

        String taskStatus = matchResult.group(REGEX_GROUP_STATUS);
        String taskDescription = matchResult.group(REGEX_GROUP_DESCRIPTION);
        Todo todo = new Todo(taskDescription);

        Todo.markTaskBasedOnStatus(lineNumber, taskStatus, todo);

        return todo;
    }

    private static void markTaskBasedOnStatus(int lineNumber, String taskStatus, Todo todo)
            throws InvalidTaskStatusException {
        switch (taskStatus) {
        case Todo.TODO_COMPLETE:
            todo.markAsDone();
            break;
        case Todo.TODO_INCOMPLETE:
            todo.markAsUndone();
            break;
        default:
            throw new InvalidTaskStatusException(lineNumber);
        }
    }

    private static MatchResult getMatchResult(int lineNumber, String serialisedTodo) throws WrongTaskFormatException {
        MatchResult matchResult;
        try {
            Scanner sc = new Scanner(serialisedTodo).useDelimiter(Task.DELIMITER);
            sc.findInLine(Todo.DE_SERIALISATION_FORMAT);
            matchResult = sc.match();
            sc.close();
        } catch (IllegalStateException e) {
            throw new WrongTaskFormatException(lineNumber, Todo.NAME, Todo.TYPE);
        }

        assert matchResult.groupCount() == Todo.EXPECTED_NUMBER_OF_REGEX_GROUPS;
        return matchResult;
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
