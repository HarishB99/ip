package bhaymax.command;

import bhaymax.exception.InvalidFilterOptionException;

/**
 * Provides enumeration values representing
 * the options that can be passed to the {@code filter}
 * command
 */
public enum FilterOption {
    DATE_ON("/on"),
    DATE_BEFORE("/before"),
    DATE_AFTER("/after"),
    TIME_ON("/on_time"),
    TIME_BEFORE("/before_time"),
    TIME_AFTER("/after_time");

    private final String filterOptionString;

    FilterOption(String filterOptionString) {
        this.filterOptionString = filterOptionString;
    }

    /**
     * Returns the {@link FilterOption} value corresponding to
     * the filter option string entered by the user
     *
     * @param filterOptionString the filter option provided by the user, as a String
     * @return a {@link FilterOption} value corresponding to the given
     *         filter option string, if the filter option is recognised
     * @throws InvalidFilterOptionException If the filter option provided is not recognised
     */
    public static FilterOption valueOfFilterOptionString(String filterOptionString)
            throws InvalidFilterOptionException {
        for (FilterOption filterOption : FilterOption.values()) {
            if (filterOption.filterOptionString.equals(filterOptionString)) {
                return filterOption;
            }
        }
        throw new InvalidFilterOptionException();
    }
}
