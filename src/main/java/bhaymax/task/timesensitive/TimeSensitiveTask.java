package bhaymax.task.timesensitive;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import bhaymax.command.FilterOption;
import bhaymax.parser.Parser;
import bhaymax.task.Task;

/**
 * An interface for task types that need to track time
 */
public abstract class TimeSensitiveTask extends Task {
    /**
     * Sets up the type and description of the task
     *
     * @param type        the type of the task
     * @param description the description of the task
     */
    protected TimeSensitiveTask(String type, String description) {
        super(type, description);
    }

    abstract boolean isBeforeDate(LocalDate date);

    abstract boolean isBeforeDateTime(LocalDateTime dateTime);

    abstract boolean isAfterDate(LocalDate date);

    abstract boolean isAfterDateTime(LocalDateTime dateTime);

    abstract boolean isOnDate(LocalDate date);

    abstract boolean isOnDateTime(LocalDateTime dateTime);

    /**
     * Checks whether the date(s) of this task
     * fall within the provided date filter
     *
     * @param dateTimeString the date and/or time to filter by, as a {@code String}
     * @param filterOption a {@link FilterOption} enum value indicating the type of
     *                  filter (i.e., before the date, after the date, exactly on
     *                  the date, include/exclude time)
     * @return a boolean value indicating if this task is within
     *         this provided date filter
     * @throws DateTimeParseException if date and/or time provided is not of the expected format
     * @see bhaymax.parser.Parser#DATE_FORMAT
     * @see bhaymax.parser.Parser#DATETIME_INPUT_FORMAT
     */
    public boolean hasDateMatchingFilter(String dateTimeString, FilterOption filterOption)
            throws DateTimeParseException {
        switch (filterOption) {
        case DATE_ON:
            // Fallthrough
        case DATE_BEFORE:
            // Fallthrough
        case DATE_AFTER:
            LocalDate date = LocalDate.parse(
                    dateTimeString, DateTimeFormatter.ofPattern(Parser.DATE_FORMAT));
            if (filterOption.equals(FilterOption.DATE_ON)) {
                return this.isOnDate(date);
            }
            if (filterOption.equals(FilterOption.DATE_BEFORE)) {
                return this.isBeforeDate(date);
            }
            return this.isAfterDate(date);
        case TIME_ON:
            // Fallthrough
        case TIME_BEFORE:
            // Fallthrough
        case TIME_AFTER:
            LocalDateTime dateTime = LocalDateTime.parse(
                    dateTimeString, DateTimeFormatter.ofPattern(Parser.DATETIME_INPUT_FORMAT));
            if (filterOption.equals(FilterOption.TIME_ON)) {
                return this.isOnDateTime(dateTime);
            }
            if (filterOption.equals(FilterOption.TIME_BEFORE)) {
                return this.isBeforeDateTime(dateTime);
            }
            return this.isAfterDateTime(dateTime);
        default:
            return false;
        }
    }
}
