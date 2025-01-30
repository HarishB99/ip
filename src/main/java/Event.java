public class Event extends Task {
    public static final String TYPE = "E";
    protected String start;
    protected String end;

    public Event(String description, String start, String end) {
        super(Event.TYPE, description);
        this.start = start;
        this.end = end;
    }

    @Override
    public String serialise() {
        return super.serialise() + " " + Task.DELIMITER + " "
                + this.start + " " + Task.DELIMITER + " "
                + this.end;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + this.start + " to: " + this.end + ")";
    }
}
