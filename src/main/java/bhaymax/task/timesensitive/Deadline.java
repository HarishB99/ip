package bhaymax.task.timesensitive;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.regex.MatchResult;

import bhaymax.exception.file.InvalidTaskStatusException;
import bhaymax.exception.file.TaskDeSerialisationException;
import bhaymax.exception.file.WrongTaskFormatException;
import bhaymax.parser.Parser;
import bhaymax.task.Task;

/**
 * Represents a task with a deadline
 */
public class Deadline extends TimeSensitiveTask {
    public static final String TYPE = "D";
    public static final String NAME = "Deadline";

    public static final String FLAG_DUE_BY = "/by";

    // For construction of exception message
    public static final String DUE_DATE_INPUT_FORMAT = "{due-by date: " + Parser.DATETIME_INPUT_FORMAT + "}";

    private static final String SERIALISATION_FORMAT = "%s " + Task.DELIMITER + " %s";
    private static final String DE_SERIALISATION_FORMAT = "^D \\| ([0-1]) \\| (.+)"
            + " \\| (\\d{2}-\\d{2}-\\d{4} \\d{2}:\\d{2})";

    private static final int EXPECTED_NUMBER_OF_REGEX_GROUPS = 3;
    private static final int REGEX_GROUP_STATUS = 1;
    private static final int REGEX_GROUP_DESCRIPTION = 2;
    private static final int REGEX_GROUP_DEADLINE = 3;

    private static final String DEADLINE_COMPLETE = "1";
    private static final String DEADLINE_INCOMPLETE = "0";

    protected LocalDateTime dueDateTime;

    /**
     * Sets up the description and the
     * due date of the deadline
     *
     * @param description the description of the task
     * @param dueDateTime the date and time the task is due, as a {@code String}
     * @throws DateTimeParseException if the deadline provided is not of the expected format
     * @see Parser#DATETIME_INPUT_FORMAT
     */
    public Deadline(String description, String dueDateTime)
            throws DateTimeParseException {
        super(Deadline.TYPE, description,
                LocalDateTime.parse(dueDateTime, DateTimeFormatter.ofPattern(Parser.DATETIME_INPUT_FORMAT)));
        this.dueDateTime = LocalDateTime.parse(
                dueDateTime, DateTimeFormatter.ofPattern(Parser.DATETIME_INPUT_FORMAT));
    }

    @Override
    public String serialise() {
        return String.format(Deadline.SERIALISATION_FORMAT, super.serialise(), this.getDeadlineInInputFormat());
    }

    /**
     * Parses a serialised deadline (provided as a {@code String})
     * and returns the corresponding {@code Deadline} object
     *
     * @param serialisedDeadline the serialised deadline, as a {@code String}
     * @return a {@code Deadline} object
     */
    public static Deadline deSerialise(int lineNumber, String serialisedDeadline)
            throws TaskDeSerialisationException {
        MatchResult matchResult = Deadline.getMatchResult(lineNumber, serialisedDeadline);
        Deadline deadline = Deadline.getDeadline(lineNumber, matchResult);
        String deadlineStatus = matchResult.group(Deadline.REGEX_GROUP_STATUS);
        Deadline.markDeadlineBasedOnStatus(lineNumber, deadline, deadlineStatus);
        return deadline;
    }

    private static void markDeadlineBasedOnStatus(int lineNumber, Deadline deadline, String deadlineStatus)
            throws InvalidTaskStatusException {
        switch (deadlineStatus) {
        case Deadline.DEADLINE_COMPLETE:
            deadline.markAsDone();
            break;
        case Deadline.DEADLINE_INCOMPLETE:
            deadline.markAsUndone();
            break;
        default:
            throw new InvalidTaskStatusException(lineNumber);
        }
    }

    private static Deadline getDeadline(int lineNumber, MatchResult matchResult) throws WrongTaskFormatException {
        Deadline deadline;
        try {
            String deadlineDescription = matchResult.group(Deadline.REGEX_GROUP_DESCRIPTION);
            String deadlineDueDate = matchResult.group(Deadline.REGEX_GROUP_DEADLINE);
            deadline = new Deadline(deadlineDescription, deadlineDueDate);
        } catch (DateTimeParseException e) {
            throw new WrongTaskFormatException(
                    lineNumber, Deadline.NAME, Deadline.TYPE, Deadline.DUE_DATE_INPUT_FORMAT);
        }
        return deadline;
    }

    private static MatchResult getMatchResult(int lineNumber, String serialisedDeadline)
            throws WrongTaskFormatException {
        MatchResult matchResult;
        try {
            Scanner sc = new Scanner(serialisedDeadline);
            sc.findInLine(Deadline.DE_SERIALISATION_FORMAT);
            matchResult = sc.match();
            sc.close();
        } catch (IllegalStateException e) {
            throw new WrongTaskFormatException(
                    lineNumber, Deadline.NAME, Deadline.TYPE, Deadline.DUE_DATE_INPUT_FORMAT);
        }

        assert matchResult.groupCount() == Deadline.EXPECTED_NUMBER_OF_REGEX_GROUPS;
        return matchResult;
    }

    private String getDeadlineInInputFormat() {
        return this.dueDateTime.format(
                DateTimeFormatter.ofPattern(Parser.DATETIME_INPUT_FORMAT));
    }

    private String getDeadlineInOutputFormat() {
        return this.dueDateTime.format(
                DateTimeFormatter.ofPattern(Parser.DATETIME_OUTPUT_FORMAT));
    }

    private LocalDate getDueDateOnly() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(Parser.DATE_INPUT_FORMAT);
        return LocalDate.parse(this.dueDateTime.format(dateFormatter), dateFormatter);
    }

    @Override
    boolean isBeforeDate(LocalDate date) {
        return this.getDueDateOnly().isBefore(date);
    }

    @Override
    boolean isBeforeDateTime(LocalDateTime dateTime) {
        return this.dueDateTime.isBefore(dateTime);
    }

    @Override
    boolean isAfterDate(LocalDate date) {
        return this.getDueDateOnly().isAfter(date);
    }

    @Override
    boolean isAfterDateTime(LocalDateTime dateTime) {
        return this.dueDateTime.isAfter(dateTime);
    }

    @Override
    boolean isOnDate(LocalDate date) {
        return this.getDueDateOnly().isEqual(date);
    }

    @Override
    boolean isOnDateTime(LocalDateTime dateTime) {
        return this.dueDateTime.isEqual(dateTime);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Deadline deadline) {
            return this.compareTo(deadline) == 0;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + this.getDeadlineInOutputFormat() + ")";
    }
}
