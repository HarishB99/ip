package bhaymax.command;

import java.util.HashMap;

import bhaymax.exception.InvalidCommandException;

/**
 * Provides enumeration values representing
 * the valid commands a user is allowed to
 * enter
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

    private static final HashMap<String, CommandString> COMMAND_STRINGS = new HashMap<String, CommandString>();

    static {
        for (CommandString commandStringEnum : values()) {
            COMMAND_STRINGS.put(
                    commandStringEnum.command, commandStringEnum);
        }
    }

    private final String command;

    CommandString(String command) {
        this.command = command;
    }

    public static boolean isValidCommandString(String commandString) {
        return COMMAND_STRINGS.containsKey(commandString);
    }

    /**
     * Returns the {@link CommandString} value corresponding to
     * the command string entered by the user
     *
     * @param commandString the command string entered by the user
     * @return a {@link CommandString} value corresponding to the
     *         given command string, if the command string is a recognised one
     * @throws InvalidCommandException If the command string provided is not recognised
     */
    public static CommandString valueOfCommandString(String commandString) throws InvalidCommandException {
        for (CommandString commandStringEnum : values()) {
            if (commandStringEnum.command.equals(commandString)) {
                return commandStringEnum;
            }
        }
        throw new InvalidCommandException();
    }
}
