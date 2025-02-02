package bhaymax.task;

public class Task {
    public static final String DELIMITER = "|";
    protected String description;
    protected String type;
    protected boolean isDone;

    public Task(String type, String description) {
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

    public String serialise() {
        return this.type + " " + Task.DELIMITER + " "
                + (this.isDone ? "1" : "0")
                + " " + Task.DELIMITER + " "
                + this.description;
    }

    public boolean hasSearchTerm(String searchTerm) {
        return this.description.contains(searchTerm);
    }

    @Override
    public String toString() {
        return "[" + this.getStatusIcon() + "] "
                + this.description;
    }
}
