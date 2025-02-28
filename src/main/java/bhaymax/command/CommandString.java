package bhaymax.command;

import bhaymax.exception.command.UnrecognisedCommandException;

/**
 * Provides enumeration values representing the valid commands a user is allowed to enter
 */
public enum CommandString {
    LIST("list"),
    MARK("mark"),
    UNMARK("unmark"),
    TODO("todo"),
    DEADLINE("deadline"),
    SEARCH("search"),
    FILTER("filter"),
    EVENT("event"),
    DELETE("delete"),
    CLEAR("clear"),
    HELLO("hello"),
    HI("hi"),
    BYE("bye"),
    EXIT("exit");

    private final String command;

    CommandString(String command) {
        this.command = command;
    }

    /**
     * Returns the {@link CommandString} value corresponding to the given command in {@code String}
     *
     * @param commandString the command string entered by the user, as a {@code String}
     * @return a {@link CommandString} value corresponding to the
     *         given command string, if the command string is a recognised one
     * @throws UnrecognisedCommandException If the command string provided is not recognised
     */
    public static CommandString valueOfCommandString(String commandString) throws UnrecognisedCommandException {
        for (CommandString commandStringEnum : CommandString.values()) {
            if (commandStringEnum.command.equals(commandString)) {
                return commandStringEnum;
            }
        }
        throw new UnrecognisedCommandException(commandString);
    }
}
