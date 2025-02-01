package bhaymax.util;

/**
 * Holds two values of (optionally) differing types
 *
 * @param t The first value
 * @param u The second value
 * @param <T> The type of the first value
 * @param <U> The type of the second value
 */
public record Pair<T, U>(T t, U u) { }
