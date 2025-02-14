package bhaymax.task.timesensitive;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.regex.MatchResult;

import bhaymax.exception.InvalidTaskStringFormatException;
import bhaymax.parser.Parser;
import bhaymax.task.Task;

/**
 * Represents a task with a deadline
 */
public class Deadline extends TimeSensitiveTask {
    public static final String TYPE = "D";
    private static final String SERIAL_FORMAT = "%s " + Task.DELIMITER + " %s";
    private static final String DESERIAL_FORMAT = "^D \\| ([0-1]) \\| (.+)"
            + " \\| (\\d{2}-\\d{2}-\\d{4} \\d{2}:\\d{2})";
    private static final int DESERIAL_FORMAT_NUMBER_OF_ITEMS = 3;
    private static final int DEADLINE_STATUS_GROUP = 1;
    private static final int DEADLINE_DESCRIPTION_GROUP = 2;
    private static final int DEADLINE_DEADLINE_GROUP = 3;
    protected LocalDateTime dueDate;

    /**
     * Sets up the description and the
     * due date of the deadline
     *
     * @param description the description of the task
     * @param dueDate the date and time the task is due, as a {@code String}
     * @throws DateTimeParseException if the deadline provided is not of the expected format
     * @see Parser#DATETIME_INPUT_FORMAT
     */
    public Deadline(String description, String dueDate)
            throws DateTimeParseException {
        super(Deadline.TYPE, description);
        this.dueDate = LocalDateTime.parse(
                dueDate, DateTimeFormatter.ofPattern(Parser.DATETIME_INPUT_FORMAT));
    }

    @Override
    public String serialise() {
        return String.format(Deadline.SERIAL_FORMAT, super.serialise(), this.getDeadlineInInputFormat());
    }

    /**
     * Returns a {@code Deadline} object by parsing
     * a given serialised deadline, as a {@code String}
     *
     * @param serialisedDeadline the serialised deadline, as a {@code String}
     * @return a {@code Deadline} object
     */
    public static Deadline deserialise(String serialisedDeadline) {
        Scanner sc = new Scanner(serialisedDeadline);
        sc.findInLine(Deadline.DESERIAL_FORMAT);
        MatchResult matchResult = sc.match();
        sc.close();

        if (matchResult.groupCount() != Deadline.DESERIAL_FORMAT_NUMBER_OF_ITEMS) {
            throw new InvalidTaskStringFormatException(
                    "Deadline in file should be of format"
                            + " 'D | {0,1} | {description} | {due-by date}'");
        }

        String deadlineStatus = matchResult.group(Deadline.DEADLINE_STATUS_GROUP);
        String deadlineDescription = matchResult.group(Deadline.DEADLINE_DESCRIPTION_GROUP);
        String deadlineDueDate = matchResult.group(Deadline.DEADLINE_DEADLINE_GROUP);

        Deadline deadline = new Deadline(deadlineDescription, deadlineDueDate);
        if (deadlineStatus.equals("1")) {
            deadline.markAsDone();
        } else if (deadlineStatus.equals("0")) {
            deadline.markAsUndone();
        } else {
            throw new InvalidTaskStringFormatException("Invalid value encountered for task status.");
        }
        return deadline;
    }

    private String getDeadlineInInputFormat() {
        return this.dueDate.format(
                DateTimeFormatter.ofPattern(Parser.DATETIME_INPUT_FORMAT));
    }

    private String getDeadlineInOutputFormat() {
        return this.dueDate.format(
                DateTimeFormatter.ofPattern(Parser.DATETIME_OUTPUT_FORMAT));
    }

    @Override
    boolean isBeforeDate(LocalDate date) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(Parser.DATE_FORMAT);
        LocalDate deadlineDate = LocalDate.parse(this.dueDate.format(dateFormatter), dateFormatter);
        return deadlineDate.isBefore(date);
    }

    @Override
    boolean isBeforeDateTime(LocalDateTime dateTime) {
        return this.dueDate.isBefore(dateTime);
    }

    @Override
    boolean isAfterDate(LocalDate date) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(Parser.DATE_FORMAT);
        LocalDate deadlineDate = LocalDate.parse(this.dueDate.format(dateFormatter), dateFormatter);
        return deadlineDate.isAfter(date);
    }

    @Override
    boolean isAfterDateTime(LocalDateTime dateTime) {
        return this.dueDate.isAfter(dateTime);
    }

    @Override
    boolean isOnDate(LocalDate date) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(Parser.DATE_FORMAT);
        LocalDate deadlineDate = LocalDate.parse(this.dueDate.format(dateFormatter), dateFormatter);
        return deadlineDate.isEqual(date);
    }

    @Override
    boolean isOnDateTime(LocalDateTime dateTime) {
        return this.dueDate.isEqual(dateTime);
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + this.getDeadlineInOutputFormat() + ")";
    }
}
