package bhaymax.task.timesensitive;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.time.format.DateTimeFormatter;
import bhaymax.task.Task;
import bhaymax.parser.Parser;

/**
 * Represents a task with a deadline
 */
public class Deadline extends Task implements TimeSensitiveTask {
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
    public boolean isBeforeDate(String date)
            throws DateTimeParseException {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(Parser.DATE_FORMAT);
        LocalDate thresholdDate = LocalDate.parse(date, dateFormatter);
        LocalDate deadlineDate = LocalDate.parse(this.deadline.format(dateFormatter), dateFormatter);
        return deadlineDate.isBefore(thresholdDate);
    }

    @Override
    public boolean isBeforeDateTime(String date)
            throws DateTimeParseException {
        LocalDateTime thresholdTime = LocalDateTime.parse(
                date, DateTimeFormatter.ofPattern(Parser.DATETIME_INPUT_FORMAT));
        return this.deadline.isBefore(thresholdTime);
    }

    @Override
    public boolean isAfterDate(String date)
            throws DateTimeParseException {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(Parser.DATE_FORMAT);
        LocalDate thresholdDate = LocalDate.parse(date, dateFormatter);
        LocalDate deadlineDate = LocalDate.parse(this.deadline.format(dateFormatter), dateFormatter);
        return deadlineDate.isAfter(thresholdDate);
    }

    @Override
    public boolean isAfterDateTime(String date)
            throws DateTimeParseException {
        LocalDateTime thresholdTime = LocalDateTime.parse(
                date, DateTimeFormatter.ofPattern(Parser.DATETIME_INPUT_FORMAT));
        return this.deadline.isAfter(thresholdTime);
    }

    @Override
    public boolean isOnDate(String date)
            throws DateTimeParseException {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(Parser.DATE_FORMAT);
        LocalDate thresholdDate = LocalDate.parse(date, dateFormatter);
        LocalDate deadlineDate = LocalDate.parse(this.deadline.format(dateFormatter), dateFormatter);
        return deadlineDate.isEqual(thresholdDate);
    }

    @Override
    public boolean isOnDateTime(String date)
            throws DateTimeParseException {
        LocalDateTime thresholdTime = LocalDateTime.parse(
                date, DateTimeFormatter.ofPattern(Parser.DATETIME_INPUT_FORMAT));
        return this.deadline.isEqual(thresholdTime);
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + this.getDeadlineInOutputFormat() + ")";
    }
}
