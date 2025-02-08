package bhaymax.task.timesensitive;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import bhaymax.parser.Parser;
import bhaymax.task.Task;

/**
 * Represents a task with a deadline
 */
public class Deadline extends TimeSensitiveTask {
    public static final String TYPE = "D";
    protected LocalDateTime deadline;

    /**
     * Sets up the description and the
     * due date of the deadline
     *
     * @param description the description of the task
     * @param deadline the date and time the task is due, as a {@code String}
     * @throws DateTimeParseException if the deadline provided is not of the expected format
     * @see Parser#DATETIME_INPUT_FORMAT
     */
    public Deadline(String description, String deadline)
            throws DateTimeParseException {
        super(Deadline.TYPE, description);
        this.deadline = LocalDateTime.parse(
                deadline, DateTimeFormatter.ofPattern(Parser.DATETIME_INPUT_FORMAT));
    }

    @Override
    public String serialise() {
        return super.serialise() + " " + Task.DELIMITER + " "
                + this.getDeadlineInInputFormat();
    }

    private String getDeadlineInInputFormat() {
        return this.deadline.format(
                DateTimeFormatter.ofPattern(Parser.DATETIME_INPUT_FORMAT));
    }

    private String getDeadlineInOutputFormat() {
        return this.deadline.format(
                DateTimeFormatter.ofPattern(Parser.DATETIME_OUTPUT_FORMAT));
    }

    @Override
    boolean isBeforeDate(LocalDate date) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(Parser.DATE_FORMAT);
        LocalDate deadlineDate = LocalDate.parse(this.deadline.format(dateFormatter), dateFormatter);
        return deadlineDate.isBefore(date);
    }

    @Override
    boolean isBeforeDateTime(LocalDateTime dateTime) {
        return this.deadline.isBefore(dateTime);
    }

    @Override
    boolean isAfterDate(LocalDate date) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(Parser.DATE_FORMAT);
        LocalDate deadlineDate = LocalDate.parse(this.deadline.format(dateFormatter), dateFormatter);
        return deadlineDate.isAfter(date);
    }

    @Override
    boolean isAfterDateTime(LocalDateTime dateTime) {
        return this.deadline.isAfter(dateTime);
    }

    @Override
    boolean isOnDate(LocalDate date) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(Parser.DATE_FORMAT);
        LocalDate deadlineDate = LocalDate.parse(this.deadline.format(dateFormatter), dateFormatter);
        return deadlineDate.isEqual(date);
    }

    @Override
    boolean isOnDateTime(LocalDateTime dateTime) {
        return this.deadline.isEqual(dateTime);
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + this.getDeadlineInOutputFormat() + ")";
    }
}
