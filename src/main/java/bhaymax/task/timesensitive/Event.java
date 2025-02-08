package bhaymax.task.timesensitive;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import bhaymax.parser.Parser;
import bhaymax.task.Task;

/**
 * Represents a task pertaining to an event
 */
public class Event extends TimeSensitiveTask {
    public static final String TYPE = "E";
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
        this.start = LocalDateTime.parse(
                start, DateTimeFormatter.ofPattern(Parser.DATETIME_INPUT_FORMAT));
        this.end = LocalDateTime.parse(
                end, DateTimeFormatter.ofPattern(Parser.DATETIME_INPUT_FORMAT));
    }

    @Override
    public String serialise() {
        return super.serialise() + " " + Task.DELIMITER + " "
                + this.getStartDateInInputFormat()
                + " " + Task.DELIMITER + " "
                + this.getEndDateInInputFormat();
    }

    private String getStartDateInInputFormat() {
        return this.start.format(
                DateTimeFormatter.ofPattern(Parser.DATETIME_INPUT_FORMAT));
    }

    private String getStartDateInOutputFormat() {
        return this.start.format(
                DateTimeFormatter.ofPattern(Parser.DATETIME_OUTPUT_FORMAT));
    }

    private String getEndDateInInputFormat() {
        return this.end.format(
                DateTimeFormatter.ofPattern(Parser.DATETIME_INPUT_FORMAT));
    }

    private String getEndDateInOutputFormat() {
        return this.end.format(
                DateTimeFormatter.ofPattern(Parser.DATETIME_OUTPUT_FORMAT));
    }

    @Override
    boolean isBeforeDate(LocalDate date) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(Parser.DATE_FORMAT);
        LocalDate startDate = LocalDate.parse(this.start.format(dateFormatter), dateFormatter);
        LocalDate endDate = LocalDate.parse(this.end.format(dateFormatter), dateFormatter);
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
