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
import bhaymax.exception.InvalidCommandException;
import bhaymax.exception.InvalidCommandFormatException;
import bhaymax.exception.InvalidFilterOptionException;
import bhaymax.task.TaskList;
import bhaymax.util.Pair;

// TODO: Invalid input handling
/**
 * Parses a provided string from the user,
 * that is expected to contain a full command,
 * and returns a {@link Command} object
 */
public class Parser {
    public static final String DATE_FORMAT = "dd-MM-yyyy";
    public static final String DATETIME_INPUT_FORMAT = "dd-MM-yyyy HH:mm";
    public static final String DATETIME_OUTPUT_FORMAT = "dd MMM yyyy, EEE @ HH:mm";

    private static final int STRING_SPLIT_LIMIT = 2;

    private static final String COMMAND_DELIMITER = " ";
    private static final String EMPTY_STRING = "";

    private static final String DEADLINE_OPT_BY = "/by";

    private static final String EVENT_OPT_START = "/from";
    private static final String EVENT_OPT_END = "/to";

    private static final String ERROR_MESSAGE_EMPTY_COMMAND = "Command is empty";
    private static final String ERROR_MESSAGE_MISSING_TASK_NUMBER = "Task number is required";
    private static final String ERROR_MESSAGE_INVALID_TASK_NUMBER = "Provided task number could not be found";
    private static final String ERROR_MESSAGE_MISSING_TASK_DESCRIPTION = "Task description is required";
    private static final String ERROR_MESSAGE_MISSING_DEADLINE = "Due by date and time is required for deadline";
    private static final String ERROR_MESSAGE_MISSING_START_TIME = "Start date and time is required for event";
    private static final String ERROR_MESSAGE_MISSING_END_TIME = "End date and time is required for event";
    private static final String ERROR_MESSAGE_MISSING_SEARCH_TERM = "Search term is required";
    private static final String ERROR_MESSAGE_MISSING_FILTER_OPTION = "Filter option is required";
    private static final String ERROR_MESSAGE_MISSING_FILTER_DATE = "Date and/or time is required for filtering tasks";

    private static Pair<CommandString, String> getCommandAndArgs(String userInput)
            throws InvalidCommandException, InvalidCommandFormatException {
        String trimmedInput = userInput.trim();
        if (trimmedInput.isEmpty()) {
            throw new InvalidCommandFormatException(Parser.ERROR_MESSAGE_EMPTY_COMMAND);
        }
        String[] tokens = trimmedInput.split(COMMAND_DELIMITER, STRING_SPLIT_LIMIT);
        String commandString = tokens[0].trim().toLowerCase();
        String arguments = tokens.length > 1 ? tokens[1].trim() : EMPTY_STRING;
        return new Pair<CommandString, String>(
                CommandString.valueOfCommandString(commandString),
                arguments
        );
    }

    private static int getTaskIndex(String arguments, TaskList taskList)
            throws InvalidCommandFormatException, NumberFormatException {
        String trimmedArguments = arguments.trim();
        if (trimmedArguments.isEmpty()) {
            throw new InvalidCommandFormatException(Parser.ERROR_MESSAGE_MISSING_TASK_NUMBER);
        }

        int index = Integer.parseInt(trimmedArguments) - 1;
        if (!taskList.isValidIndex(index)) {
            throw new InvalidCommandFormatException(Parser.ERROR_MESSAGE_INVALID_TASK_NUMBER);
        }

        return index;
    }

    private static String getTaskDescription(String arguments)
            throws InvalidCommandFormatException {
        if (arguments.isEmpty()) {
            throw new InvalidCommandFormatException(Parser.ERROR_MESSAGE_MISSING_TASK_DESCRIPTION);
        }
        return arguments;
    }

    private static Pair<String, String> getTaskDescriptionAndArgs(
            String arguments, String option, String errorMessageForMissingOption)
            throws InvalidCommandFormatException {
        String[] tokens = arguments.split(option, STRING_SPLIT_LIMIT);
        String taskDescription = tokens[0].trim();
        if (taskDescription.isEmpty()) {
            throw new InvalidCommandFormatException(Parser.ERROR_MESSAGE_MISSING_TASK_DESCRIPTION);
        }
        if (tokens.length < 2) {
            throw new InvalidCommandFormatException(errorMessageForMissingOption);
        }
        String argumentForOption = tokens[1].trim();
        if (argumentForOption.isEmpty()) {
            throw new InvalidCommandFormatException(errorMessageForMissingOption);
        }
        return new Pair<String, String>(taskDescription, argumentForOption);
    }

    private static Pair<String, String> getEventStartAndEndDates(String arguments)
            throws InvalidCommandFormatException {
        String[] tokens = arguments.split(EVENT_OPT_END, STRING_SPLIT_LIMIT);
        String startDate = tokens[0].trim();
        if (startDate.isEmpty()) {
            throw new InvalidCommandFormatException(Parser.ERROR_MESSAGE_MISSING_START_TIME);
        }
        if (tokens.length < 2) {
            throw new InvalidCommandFormatException(Parser.ERROR_MESSAGE_MISSING_END_TIME);
        }
        String endDate = tokens[1].trim();
        if (endDate.isEmpty()) {
            throw new InvalidCommandFormatException(Parser.ERROR_MESSAGE_MISSING_END_TIME);
        }
        return new Pair<String, String>(startDate, endDate);
    }

    private static String getSearchTerm(String arguments) throws InvalidCommandFormatException {
        if (arguments.isEmpty()) {
            throw new InvalidCommandFormatException(Parser.ERROR_MESSAGE_MISSING_SEARCH_TERM);
        }
        return arguments;
    }

    private static Pair<FilterOption, String> getFilterOptionAndDate(String arguments)
            throws InvalidCommandFormatException, InvalidFilterOptionException {
        String[] tokens = arguments.split(COMMAND_DELIMITER, STRING_SPLIT_LIMIT);
        String filterOption = tokens[0].trim();
        if (filterOption.isEmpty()) {
            throw new InvalidCommandFormatException(Parser.ERROR_MESSAGE_MISSING_FILTER_OPTION);
        }
        if (tokens.length < 2) {
            throw new InvalidCommandFormatException(Parser.ERROR_MESSAGE_MISSING_FILTER_DATE);
        }
        String dateTime = tokens[1].trim();
        if (dateTime.isEmpty()) {
            throw new InvalidCommandFormatException(Parser.ERROR_MESSAGE_MISSING_FILTER_DATE);
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
            throws InvalidCommandFormatException, InvalidCommandException, InvalidFilterOptionException {
        Pair<CommandString, String> commandAndArgs = Parser.getCommandAndArgs(userInput);
        CommandString commandString = commandAndArgs.first();
        String arguments = commandAndArgs.second();

        switch (commandString) {
        case LIST:
            return new ListCommand();
        case DELETE:
            return new DeleteCommand(getTaskIndex(arguments, taskList));
        case MARK:
            return new MarkCommand(getTaskIndex(arguments, taskList));
        case UNMARK:
            return new UnmarkCommand(getTaskIndex(arguments, taskList));
        case TODO:
            return new TodoCommand(getTaskDescription(arguments));
        case DEADLINE:
            Pair<String, String> descriptionAndDeadline = getTaskDescriptionAndArgs(
                    arguments, DEADLINE_OPT_BY, ERROR_MESSAGE_MISSING_DEADLINE);
            String deadlineDescription = descriptionAndDeadline.first();
            String deadline = descriptionAndDeadline.second();
            return new DeadlineCommand(deadlineDescription, deadline);
        case EVENT:
            Pair<String, String> descriptionAndArgs = getTaskDescriptionAndArgs(
                    arguments, EVENT_OPT_START, ERROR_MESSAGE_MISSING_START_TIME);
            String eventDescription = descriptionAndArgs.first();
            String eventArguments = descriptionAndArgs.second();
            Pair<String, String> eventStartAndEnd = getEventStartAndEndDates(eventArguments);
            String eventStart = eventStartAndEnd.first();
            String eventEnd = eventStartAndEnd.second();
            return new EventCommand(eventDescription, eventStart, eventEnd);
        case SEARCH:
            return new SearchCommand(getSearchTerm(arguments));
        case FILTER:
            Pair<FilterOption, String> filterOptionAndDate = getFilterOptionAndDate(arguments);
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
            throw new InvalidCommandException(userInput.split(Parser.COMMAND_DELIMITER)[0]);
        }
    }
}
