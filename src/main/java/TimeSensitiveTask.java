import java.time.format.DateTimeParseException;

interface TimeSensitiveTask {
    boolean isBeforeDate(String date)
            throws DateTimeParseException;

    boolean isBeforeDateTime(String date)
            throws DateTimeParseException;

    boolean isAfterDate(String date)
            throws DateTimeParseException;

    boolean isAfterDateTime(String date)
            throws DateTimeParseException;

    boolean isOnDate(String date)
            throws DateTimeParseException;

    boolean isOnDateTime(String date)
            throws DateTimeParseException;
}
