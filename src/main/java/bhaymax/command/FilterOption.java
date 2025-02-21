package bhaymax.command;

import bhaymax.exception.command.filter.InvalidFilterOptionException;

/**
 * Provides enumeration values representing the valid options that can be passed to the {@code filter} command
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
     * Returns the {@link FilterOption} value corresponding to the given filter option in {@code String}
     *
     * @param filterOptionString the filter option provided by the user, as a {@code String}
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
        throw new InvalidFilterOptionException(filterOptionString);
    }
}
