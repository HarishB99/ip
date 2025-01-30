import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.time.format.DateTimeFormatter;

public class Event extends Task {
    public static final String TYPE = "E";
    protected LocalDateTime start;
    protected LocalDateTime end;

    public Event(String description, String start, String end)
            throws DateTimeParseException {
        super(Event.TYPE, description);
        this.start = LocalDateTime.parse(
                start, DateTimeFormatter.ofPattern(Bhaymax.DATETIME_INPUT_FORMAT));
        this.end = LocalDateTime.parse(
                end, DateTimeFormatter.ofPattern(Bhaymax.DATETIME_INPUT_FORMAT));
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
                DateTimeFormatter.ofPattern(Bhaymax.DATETIME_INPUT_FORMAT));
    }

    private String getStartDateInOutputFormat() {
        return this.end.format(
                DateTimeFormatter.ofPattern(Bhaymax.DATETIME_OUTPUT_FORMAT));
    }

    private String getEndDateInInputFormat() {
        return this.end.format(
                DateTimeFormatter.ofPattern(Bhaymax.DATETIME_INPUT_FORMAT));
    }

    private String getEndDateInOutputFormat() {
        return this.end.format(
                DateTimeFormatter.ofPattern(Bhaymax.DATETIME_OUTPUT_FORMAT));
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: "
                + this.getStartDateInOutputFormat() + " to: "
                + this.getEndDateInOutputFormat() + ")";
    }
}
