package bhaymax.util;

/**
 * Holds two values of differing (or the similar) types
 *
 * @param <T>    The type of the first value
 * @param <U>    The type of the second value
 * @param first  The first value
 * @param second The second value
 */
public record Pair<T, U>(T first, U second) { }
