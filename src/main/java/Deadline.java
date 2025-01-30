public class Deadline extends Task {
    public static final String TYPE = "D";
    protected String deadline;

    public Deadline(String description, String deadline) {
        super(Deadline.TYPE, description);
        this.deadline = deadline;
    }

    @Override
    public String serialise() {
        return super.serialise() + " " + Task.DELIMITER + " "
                + this.deadline;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + this.deadline + ")";
    }
}
