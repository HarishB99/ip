package bhaymax.task.timesensitive;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.regex.MatchResult;

import bhaymax.exception.command.event.InvalidTimeRangeForEventException;
import bhaymax.exception.file.InvalidTaskStatusException;
import bhaymax.exception.file.TaskDeSerialisationException;
import bhaymax.exception.file.WrongTaskFormatException;
import bhaymax.parser.Parser;
import bhaymax.task.Task;

/**
 * Represents a task pertaining to an event
 */
public class Event extends TimeSensitiveTask {
    public static final String TYPE = "E";
    public static final String NAME = "Event";

    public static final String FLAG_START_DATE = "/from";
    public static final String FLAG_END_DATE = "/to";

    public static final String START_DATE_INPUT_FORMAT = "{start date: " + Parser.DATETIME_INPUT_FORMAT + "}";
    public static final String END_DATE_INPUT_FORMAT = "{end date (should be after or equal to start date): "
            + Parser.DATETIME_INPUT_FORMAT + "}";

    private static final String SERIALISATION_FORMAT = "%s " + Task.DELIMITER
            + " %s " + Task.DELIMITER
            + " %s";
    private static final String DE_SERIALISATION_FORMAT = "^E \\" + Task.DELIMITER
            + " ([0-1]) \\" + Task.DELIMITER
            + " (.+) \\" + Task.DELIMITER
            + " (\\d{2}/\\d{2}/\\d{4} \\d{2}:\\d{2}) \\" + Task.DELIMITER
            + " (\\d{2}/\\d{2}/\\d{4} \\d{2}:\\d{2})$";

    private static final int EXPECTED_NUMBER_OF_REGEX_GROUPS = 4;
    private static final int REGEX_GROUP_STATUS = 1;
    private static final int REGEX_GROUP_DESCRIPTION = 2;
    private static final int REGEX_GROUP_START_DATE = 3;
    private static final int REGEX_GROUP_END_DATE = 4;

    private static final String EVENT_COMPLETE = "1";
    private static final String EVENT_INCOMPLETE = "0";

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
            throws DateTimeParseException, InvalidTimeRangeForEventException {
        super(Event.TYPE, description,
                LocalDateTime.parse(start, DateTimeFormatter.ofPattern(Parser.DATETIME_INPUT_FORMAT)));
        this.start = LocalDateTime.parse(start, DateTimeFormatter.ofPattern(Parser.DATETIME_INPUT_FORMAT));
        this.end = LocalDateTime.parse(end, DateTimeFormatter.ofPattern(Parser.DATETIME_INPUT_FORMAT));
        if (this.end.isBefore(this.start)) {
            throw new InvalidTimeRangeForEventException();
        }
    }

    @Override
    public String serialise() {
        return String.format(Event.SERIALISATION_FORMAT,
                super.serialise(), this.getStartDateInInputFormat(), this.getEndDateInInputFormat());
    }

    /**
     * Parses a serialised event (provided as a {@code String})
     * and returns the corresponding {@code Event} object
     *
     * @param serialisedEvent the serialised event, as a {@code String}
     * @return a {@code Event} object
     */
    public static Event deSerialise(int lineNumber, String serialisedEvent) throws TaskDeSerialisationException {
        MatchResult matchResult = Event.getMatchResult(lineNumber, serialisedEvent);
        Event event = Event.getEvent(lineNumber, matchResult);
        String eventStatus = matchResult.group(Event.REGEX_GROUP_STATUS);
        Event.markEventBasedOnStatus(lineNumber, event, eventStatus);
        return event;
    }

    private static void markEventBasedOnStatus(int lineNumber, Event event, String eventStatus)
            throws InvalidTaskStatusException {
        switch (eventStatus) {
        case Event.EVENT_COMPLETE:
            event.markAsDone();
            break;
        case Event.EVENT_INCOMPLETE:
            event.markAsUndone();
            break;
        default:
            throw new InvalidTaskStatusException(lineNumber);
        }
    }

    private static Event getEvent(int lineNumber, MatchResult matchResult) throws WrongTaskFormatException {
        Event event;
        try {
            String eventDescription = matchResult.group(Event.REGEX_GROUP_DESCRIPTION);
            String eventStart = matchResult.group(Event.REGEX_GROUP_START_DATE);
            String eventEnd = matchResult.group(Event.REGEX_GROUP_END_DATE);
            event = new Event(eventDescription, eventStart, eventEnd);
        } catch (DateTimeParseException | InvalidTimeRangeForEventException e) {
            throw new WrongTaskFormatException(
                    lineNumber, Event.NAME, Event.TYPE,
                    Event.START_DATE_INPUT_FORMAT, Event.END_DATE_INPUT_FORMAT);
        }
        return event;
    }

    private static MatchResult getMatchResult(int lineNumber, String serialisedEvent) throws WrongTaskFormatException {
        MatchResult matchResult;

        try {
            Scanner sc = new Scanner(serialisedEvent);
            sc.findInLine(Event.DE_SERIALISATION_FORMAT);
            matchResult = sc.match();
            sc.close();
        } catch (IllegalStateException e) {
            throw new WrongTaskFormatException(
                    lineNumber, Event.NAME, Event.TYPE,
                    Event.START_DATE_INPUT_FORMAT, Event.END_DATE_INPUT_FORMAT);
        }

        assert matchResult.groupCount() == Event.EXPECTED_NUMBER_OF_REGEX_GROUPS;
        return matchResult;
    }

    private String getStartDateInInputFormat() {
        return this.start.format(DateTimeFormatter.ofPattern(Parser.DATETIME_INPUT_FORMAT));
    }

    private String getStartDateInOutputFormat() {
        return this.start.format(DateTimeFormatter.ofPattern(Parser.DATETIME_OUTPUT_FORMAT));
    }

    private String getEndDateInInputFormat() {
        return this.end.format(DateTimeFormatter.ofPattern(Parser.DATETIME_INPUT_FORMAT));
    }

    private String getEndDateInOutputFormat() {
        return this.end.format(DateTimeFormatter.ofPattern(Parser.DATETIME_OUTPUT_FORMAT));
    }

    @Override
    boolean isBeforeDate(LocalDate date) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(Parser.DATE_INPUT_FORMAT);
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
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(Parser.DATE_INPUT_FORMAT);
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
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(Parser.DATE_INPUT_FORMAT);
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
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Event event) {
            return this.compareTo(event) == 0;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: "
                + this.getStartDateInOutputFormat() + " to: "
                + this.getEndDateInOutputFormat() + ")";
    }
}
