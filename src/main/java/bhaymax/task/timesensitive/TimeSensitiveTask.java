package bhaymax.task.timesensitive;

import java.time.format.DateTimeParseException;

/**
 * An interface for task types that need to track time
 */
public interface TimeSensitiveTask {
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
