package bhaymax.task;

/**
 * Represents a To-Do task
 */
public class Todo extends Task {
    public static final String TYPE = "T";

    /**
     * Sets up the description of the To-Do task
     *
     * @param description the description of the To-Do task
     */
    public Todo(String description) {
        super(Todo.TYPE, description);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
