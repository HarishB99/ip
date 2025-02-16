package bhaymax.parser;

import bhaymax.command.ClearCommand;
import bhaymax.command.Command;
import bhaymax.command.CommandString;
import bhaymax.command.DeadlineCommand;
import bhaymax.command.DeleteCommand;
import bhaymax.command.EventCommand;
import bhaymax.command.ExitCommand;
import bhaymax.command.FilterCommand;
import bhaymax.command.FilterOption;
import bhaymax.command.HelloCommand;
import bhaymax.command.ListCommand;
import bhaymax.command.MarkCommand;
import bhaymax.command.SearchCommand;
import bhaymax.command.TodoCommand;
import bhaymax.command.UnmarkCommand;
import bhaymax.exception.command.EmptyCommandException;
import bhaymax.exception.command.InvalidCommandFormatException;
import bhaymax.exception.command.InvalidFilterOptionException;
import bhaymax.exception.command.MissingDeadlineDueByDateException;
import bhaymax.exception.command.MissingEventEndDateException;
import bhaymax.exception.command.MissingEventStartDateException;
import bhaymax.exception.command.MissingFilterDateException;
import bhaymax.exception.command.MissingFilterOptionException;
import bhaymax.exception.command.MissingSearchTermException;
import bhaymax.exception.command.MissingTaskDescriptionException;
import bhaymax.exception.command.MissingTaskNumberException;
import bhaymax.exception.command.TaskIndexIsNotANumberException;
import bhaymax.exception.command.TaskIndexOutOfBoundsException;
import bhaymax.exception.command.UnrecognisedCommandException;
import bhaymax.task.TaskList;
import bhaymax.util.Pair;

/**
 * Parses a provided string from the user,
 * that is expected to contain a full command,
 * and returns a {@link Command} object
 */
public class Parser {
    public static final String DATE_INPUT_FORMAT = "dd-MM-yyyy";
    public static final String DATETIME_INPUT_FORMAT = "dd-MM-yyyy HH:mm";
    public static final String DATETIME_OUTPUT_FORMAT = "dd MMM yyyy, EEE @ HH:mm";

    private static final int STRING_SPLIT_LIMIT = 2;

    private static final String COMMAND_DELIMITER = " ";

    private static final String DEADLINE_OPT_BY = "/by";

    private static final String EVENT_OPT_START = "/from";
    private static final String EVENT_OPT_END = "/to";

    private static Pair<CommandString, String> getCommandAndArgs(String userInput)
            throws InvalidCommandFormatException {
        String trimmedInput = userInput.trim();
        if (trimmedInput.isEmpty()) {
            throw new EmptyCommandException();
        }
        String[] tokens = trimmedInput.split(Parser.COMMAND_DELIMITER, Parser.STRING_SPLIT_LIMIT);
        String commandString = tokens[0].trim().toLowerCase();
        String arguments = tokens.length > 1 ? tokens[1].trim() : "";
        return new Pair<CommandString, String>(
                CommandString.valueOfCommandString(commandString),
                arguments
        );
    }

    private static int getTaskIndex(String arguments, TaskList taskList)
            throws InvalidCommandFormatException {
        String trimmedArguments = arguments.trim();
        if (trimmedArguments.isEmpty()) {
            throw new MissingTaskNumberException();
        }

        try {
            int index = Integer.parseInt(trimmedArguments) - 1;
            if (!taskList.isValidIndex(index)) {
                throw new TaskIndexOutOfBoundsException();
            }
            return index;
        } catch (NumberFormatException exception) {
            throw new TaskIndexIsNotANumberException();
        }
    }

    private static String getTaskDescription(String arguments)
            throws InvalidCommandFormatException {
        if (arguments.isEmpty()) {
            throw new MissingTaskDescriptionException();
        }
        return arguments;
    }

    private static Pair<String, String> getTaskDescriptionAndArgs(
            String arguments, String option, InvalidCommandFormatException exceptionToThrow)
            throws InvalidCommandFormatException {
        String[] tokens = arguments.split(option, Parser.STRING_SPLIT_LIMIT);
        String taskDescription = tokens[0].trim();
        if (taskDescription.isEmpty()) {
            throw new MissingTaskDescriptionException();
        }
        if (tokens.length < 2) {
            throw exceptionToThrow;
        }
        String argumentForOption = tokens[1].trim();
        if (argumentForOption.isEmpty()) {
            throw exceptionToThrow;
        }
        return new Pair<String, String>(taskDescription, argumentForOption);
    }

    private static Pair<String, String> getEventStartAndEndDates(String arguments)
            throws InvalidCommandFormatException {
        String[] tokens = arguments.split(Parser.EVENT_OPT_END, Parser.STRING_SPLIT_LIMIT);
        String startDate = tokens[0].trim();
        if (startDate.isEmpty()) {
            throw new MissingEventStartDateException();
        }
        if (tokens.length < 2) {
            throw new MissingEventEndDateException();
        }
        String endDate = tokens[1].trim();
        if (endDate.isEmpty()) {
            throw new MissingEventEndDateException();
        }
        return new Pair<String, String>(startDate, endDate);
    }

    private static String getSearchTerm(String arguments) throws InvalidCommandFormatException {
        if (arguments.isEmpty()) {
            throw new MissingSearchTermException();
        }
        return arguments;
    }

    private static Pair<FilterOption, String> getFilterOptionAndDate(String arguments)
            throws InvalidCommandFormatException {
        String[] tokens = arguments.split(Parser.COMMAND_DELIMITER, Parser.STRING_SPLIT_LIMIT);
        String filterOption = tokens[0].trim();
        if (filterOption.isEmpty()) {
            throw new MissingFilterOptionException();
        }
        if (tokens.length < 2) {
            throw new MissingFilterDateException();
        }
        String dateTime = tokens[1].trim();
        if (dateTime.isEmpty()) {
            throw new MissingFilterDateException();
        }
        return new Pair<FilterOption, String>(
                FilterOption.valueOfFilterOptionString(filterOption),
                dateTime
        );
    }

    /**
     * Processes the command string entered by a user
     * and returns a {@link Command} object
     *
     * @return a {@link Command} object representing the command entered by the user
     * @throws InvalidCommandFormatException If the format of the command entered by the user is incorrect
     */
    public static Command parse(String userInput, TaskList taskList)
            throws InvalidCommandFormatException, InvalidFilterOptionException {
        Pair<CommandString, String> commandAndArgs = Parser.getCommandAndArgs(userInput);
        CommandString commandString = commandAndArgs.first();
        String arguments = commandAndArgs.second();

        switch (commandString) {
        case LIST:
            return new ListCommand();
        case DELETE:
            return new DeleteCommand(Parser.getTaskIndex(arguments, taskList));
        case MARK:
            return new MarkCommand(Parser.getTaskIndex(arguments, taskList));
        case UNMARK:
            return new UnmarkCommand(Parser.getTaskIndex(arguments, taskList));
        case TODO:
            return new TodoCommand(Parser.getTaskDescription(arguments));
        case DEADLINE:
            Pair<String, String> descriptionAndDeadline = Parser.getTaskDescriptionAndArgs(
                    arguments, Parser.DEADLINE_OPT_BY, new MissingDeadlineDueByDateException());
            String deadlineDescription = descriptionAndDeadline.first();
            String deadline = descriptionAndDeadline.second();
            return new DeadlineCommand(deadlineDescription, deadline);
        case EVENT:
            Pair<String, String> descriptionAndArgs = Parser.getTaskDescriptionAndArgs(
                    arguments, Parser.EVENT_OPT_START, new MissingEventStartDateException());
            String eventDescription = descriptionAndArgs.first();
            String eventArguments = descriptionAndArgs.second();
            Pair<String, String> eventStartAndEnd = Parser.getEventStartAndEndDates(eventArguments);
            String eventStart = eventStartAndEnd.first();
            String eventEnd = eventStartAndEnd.second();
            return new EventCommand(eventDescription, eventStart, eventEnd);
        case SEARCH:
            return new SearchCommand(Parser.getSearchTerm(arguments));
        case FILTER:
            Pair<FilterOption, String> filterOptionAndDate = Parser.getFilterOptionAndDate(arguments);
            FilterOption filterOption = filterOptionAndDate.first();
            String dateTime = filterOptionAndDate.second();
            return new FilterCommand(dateTime, filterOption);
        case CLEAR:
            return new ClearCommand();
        case HI:
            // Fallthrough
        case HELLO:
            return new HelloCommand();
        case BYE:
            // Fallthrough
        case EXIT:
            return new ExitCommand();
        default:
            throw new UnrecognisedCommandException(commandString.name().toLowerCase());
        }
    }
}
