import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    public static final String TYPE = "D";
    protected LocalDateTime deadline;

    public Deadline(String description, String deadline)
            throws DateTimeParseException {
        super(Deadline.TYPE, description);
        this.deadline = LocalDateTime.parse(
                deadline, DateTimeFormatter.ofPattern(Bhaymax.DATETIME_INPUT_FORMAT));
    }

    @Override
    public String serialise() {
        return super.serialise() + " " + Task.DELIMITER + " "
                + this.getDeadlineInInputFormat();
    }

    private String getDeadlineInInputFormat() {
        return this.deadline.format(
                DateTimeFormatter.ofPattern(Bhaymax.DATETIME_INPUT_FORMAT));
    }

    private String getDeadlineInOutputFormat() {
        return this.deadline.format(
                DateTimeFormatter.ofPattern(Bhaymax.DATETIME_OUTPUT_FORMAT));
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + this.getDeadlineInOutputFormat() + ")";
    }
}
