import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.time.format.DateTimeFormatter;

public class Event extends Task implements TimeSensitiveTask {
    public static final String TYPE = "E";
    protected LocalDateTime start;
    protected LocalDateTime end;

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
    public boolean isBeforeDate(String date)
            throws DateTimeParseException {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(Parser.DATE_FORMAT);
        LocalDate thresholdDate = LocalDate.parse(date, dateFormatter);
        LocalDate startDate = LocalDate.parse(this.start.format(dateFormatter), dateFormatter);
        LocalDate endDate = LocalDate.parse(this.end.format(dateFormatter), dateFormatter);
        return endDate.isBefore(thresholdDate)
                || (startDate.isBefore(thresholdDate) && endDate.isAfter(thresholdDate));
    }

    @Override
    public boolean isBeforeDateTime(String date)
            throws DateTimeParseException {
        LocalDateTime thresholdTime = LocalDateTime.parse(
                date, DateTimeFormatter.ofPattern(Parser.DATETIME_INPUT_FORMAT));
        return this.end.isBefore(thresholdTime)
                || (this.start.isBefore(thresholdTime) && this.end.isAfter(thresholdTime));
    }

    @Override
    public boolean isAfterDate(String date)
            throws DateTimeParseException {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(Parser.DATE_FORMAT);
        LocalDate thresholdDate = LocalDate.parse(date, dateFormatter);
        LocalDate startDate = LocalDate.parse(this.start.format(dateFormatter), dateFormatter);
        LocalDate endDate = LocalDate.parse(this.end.format(dateFormatter), dateFormatter);
        return startDate.isAfter(thresholdDate)
                || (endDate.isAfter(thresholdDate) && startDate.isBefore(thresholdDate));
    }

    @Override
    public boolean isAfterDateTime(String date)
            throws DateTimeParseException {
        LocalDateTime thresholdTime = LocalDateTime.parse(
                date, DateTimeFormatter.ofPattern(Parser.DATETIME_INPUT_FORMAT));
        return this.start.isAfter(thresholdTime)
                || (this.end.isAfter(thresholdTime) && this.start.isBefore(thresholdTime));
    }

    @Override
    public boolean isOnDate(String date)
            throws DateTimeParseException {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(Parser.DATE_FORMAT);
        LocalDate thresholdDate = LocalDate.parse(date, dateFormatter);
        LocalDate startDate = LocalDate.parse(this.start.format(dateFormatter), dateFormatter);
        LocalDate endDate = LocalDate.parse(this.end.format(dateFormatter), dateFormatter);
        return startDate.isEqual(thresholdDate)
                || endDate.isEqual(thresholdDate);
    }

    @Override
    public boolean isOnDateTime(String date)
            throws DateTimeParseException {
        LocalDateTime thresholdTime = LocalDateTime.parse(
                date, DateTimeFormatter.ofPattern(Parser.DATETIME_INPUT_FORMAT));
        return this.start.isEqual(thresholdTime)
                || this.end.isEqual(thresholdTime);
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: "
                + this.getStartDateInOutputFormat() + " to: "
                + this.getEndDateInOutputFormat() + ")";
    }
}
