package bhaymax.task;

/**
 * Represents a generic task
 */
public abstract class Task implements Comparable<Task> {
    public static final String DELIMITER = "|";
    public static final String SERIALISATION_FORMAT = "%s " + Task.DELIMITER + " %d " + Task.DELIMITER + " %s";

    protected String description;
    protected String type;
    protected boolean isDone;

    /**
     * Constructor for Task object
     *
     * @param type the type of the task
     * @param description the description of the task
     */
    protected Task(String type, String description) {
        this.type = type;
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public void markAsUndone() {
        this.isDone = false;
    }

    /**
     * Returns a {@code String} representation of the {@code Task} object, suitable for saving to a file
     *
     * @return the {@code String} representation of this object, suitable for saving to a file
     */
    public String serialise() {
        return String.format(Task.SERIALISATION_FORMAT, this.type, (this.isDone ? 1 : 0), this.description);
    }

    public boolean hasSearchTerm(String searchTerm) {
        return this.description.toLowerCase().contains(searchTerm.toLowerCase());
    }

    @Override
    public String toString() {
        return "[" + this.getStatusIcon() + "] " + this.description;
    }

    @Override
    public int compareTo(Task task) {
        int differenceInType = this.type.compareTo(task.type);
        return (differenceInType != 0)
                ? differenceInType
                : this.description.toLowerCase().compareTo(task.description.toLowerCase());
    }
}
