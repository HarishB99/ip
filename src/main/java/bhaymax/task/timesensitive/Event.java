package bhaymax.task.timesensitive;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.regex.MatchResult;

import bhaymax.exception.file.TaskDeSerialisationException;
import bhaymax.parser.Parser;
import bhaymax.task.Task;

/**
 * Represents a task pertaining to an event
 */
public class Event extends TimeSensitiveTask {
    public static final String TYPE = "E";
    public static final String ERROR_WRONG_TASK_FORMAT = Task.getErrorWrongTaskStatus(
            "Event",
            Event.TYPE,
            " " + Task.DELIMITER
                    + " {start date: dd-MM-yyyy HH:mm} " + Task.DELIMITER
                    + " {end date: dd-MM-yyyy HH:mm}");

    private static final String SERIAL_FORMAT = "%s " + Task.DELIMITER + " %s " + Task.DELIMITER + " %s";
    private static final String DE_SERIAL_FORMAT = "^E \\| ([0-1]) \\| (.+)"
            + " \\| (\\d{2}-\\d{2}-\\d{4} \\d{2}:\\d{2}) \\| (\\d{2}-\\d{2}-\\d{4} \\d{2}:\\d{2})$";
    private static final int DE_SERIAL_FORMAT_NUMBER_OF_ITEMS = 4;

    private static final int EVENT_STATUS_GROUP = 1;
    private static final int EVENT_DESCRIPTION_GROUP = 2;
    private static final int EVENT_START_DATE_GROUP = 3;
    private static final int EVENT_END_DATE_GROUP = 4;

    private static final String EVENT_DONE = "1";
    private static final String EVENT_NOT_DONE = "0";

    protected LocalDateTime start;
    protected LocalDateTime end;

    /**
     * Sets up the description of the event, as well as
     * the dates and times at which the event will start
     * and end
     *
     * @param description the description of the event
     * @param start the date and time when the event will start, as a {@code String}
     * @param end the date and time when the event will end, as a {@code String}
     * @throws DateTimeParseException if the start date or end date provided is not of the expected format
     * @see Parser#DATETIME_INPUT_FORMAT
     */
    public Event(String description, String start, String end)
            throws DateTimeParseException {
        super(Event.TYPE, description);
        this.start = LocalDateTime.parse(start, DateTimeFormatter.ofPattern(Parser.DATETIME_INPUT_FORMAT));
        this.end = LocalDateTime.parse(end, DateTimeFormatter.ofPattern(Parser.DATETIME_INPUT_FORMAT));
    }

    @Override
    public String serialise() {
        return String.format(Event.SERIAL_FORMAT,
                super.serialise(), this.getStartDateInInputFormat(), this.getEndDateInInputFormat());
    }

    /**
     * Returns a {@code Event} object by parsing
     * a given serialised event, as a {@code String}
     *
     * @param serialisedEvent the serialised event, as a {@code String}
     * @return a {@code Event} object
     */
    public static Event deSerialise(int lineNumber, String serialisedEvent) throws TaskDeSerialisationException {
        Scanner sc = new Scanner(serialisedEvent);
        sc.findInLine(Event.DE_SERIAL_FORMAT);
        MatchResult matchResult = sc.match();
        sc.close();

        if (matchResult.groupCount() != Event.DE_SERIAL_FORMAT_NUMBER_OF_ITEMS) {
            throw new TaskDeSerialisationException(lineNumber, Event.ERROR_WRONG_TASK_FORMAT);
        }

        String eventStatus = matchResult.group(Event.EVENT_STATUS_GROUP);
        String eventDescription = matchResult.group(Event.EVENT_DESCRIPTION_GROUP);
        String eventStart = matchResult.group(Event.EVENT_START_DATE_GROUP);
        String eventEnd = matchResult.group(Event.EVENT_END_DATE_GROUP);

        Event event;
        try {
            event = new Event(eventDescription, eventStart, eventEnd);
        } catch (DateTimeParseException e) {
            throw new TaskDeSerialisationException(lineNumber, Event.ERROR_WRONG_TASK_FORMAT);
        }

        switch (eventStatus) {
        case Event.EVENT_DONE:
            event.markAsDone();
            break;
        case Event.EVENT_NOT_DONE:
            event.markAsUndone();
            break;
        default:
            throw new TaskDeSerialisationException(lineNumber, Event.ERROR_WRONG_TASK_STATUS);
        }

        return event;
    }

    private String getStartDateInInputFormat() {
        return this.start.format(DateTimeFormatter.ofPattern(Parser.DATETIME_INPUT_FORMAT));
    }

    private String getStartDateInOutputFormat() {
        return this.start.format(DateTimeFormatter.ofPattern(Parser.DATETIME_INPUT_FORMAT));
    }

    private String getEndDateInInputFormat() {
        return this.end.format(DateTimeFormatter.ofPattern(Parser.DATETIME_INPUT_FORMAT));
    }

    private String getEndDateInOutputFormat() {
        return this.end.format(DateTimeFormatter.ofPattern(Parser.DATETIME_INPUT_FORMAT));
    }

    @Override
    boolean isBeforeDate(LocalDate date) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(Parser.DATE_FORMAT);
        LocalDate startDate = LocalDate.parse(
                this.start.format(dateFormatter),
                dateFormatter
        );
        LocalDate endDate = LocalDate.parse(
                this.end.format(dateFormatter),
                dateFormatter
        );
        return endDate.isBefore(date)
                || (startDate.isBefore(date) && endDate.isAfter(date));
    }

    @Override
    boolean isBeforeDateTime(LocalDateTime dateTime) {
        return this.end.isBefore(dateTime)
                || (this.start.isBefore(dateTime) && this.end.isAfter(dateTime));
    }

    @Override
    boolean isAfterDate(LocalDate date) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(Parser.DATE_FORMAT);
        LocalDate startDate = LocalDate.parse(this.start.format(dateFormatter), dateFormatter);
        LocalDate endDate = LocalDate.parse(this.end.format(dateFormatter), dateFormatter);
        return startDate.isAfter(date)
                || (endDate.isAfter(date) && startDate.isBefore(date));
    }

    @Override
    boolean isAfterDateTime(LocalDateTime dateTime) {
        return this.start.isAfter(dateTime)
                || (this.end.isAfter(dateTime) && this.start.isBefore(dateTime));
    }

    @Override
    boolean isOnDate(LocalDate date) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(Parser.DATE_FORMAT);
        LocalDate startDate = LocalDate.parse(
                this.start.format(dateFormatter), dateFormatter);
        LocalDate endDate = LocalDate.parse(
                this.end.format(dateFormatter), dateFormatter);
        return startDate.isEqual(date) || endDate.isEqual(date);
    }

    @Override
    boolean isOnDateTime(LocalDateTime dateTime) {
        return this.start.isEqual(dateTime)
                || this.end.isEqual(dateTime);
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: "
                + this.getStartDateInOutputFormat() + " to: "
                + this.getEndDateInOutputFormat() + ")";
    }
}
