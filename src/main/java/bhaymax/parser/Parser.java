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
import bhaymax.exception.command.MissingSearchTermException;
import bhaymax.exception.command.MissingTaskNumberForDeleteException;
import bhaymax.exception.command.MissingTaskNumberForMarkException;
import bhaymax.exception.command.MissingTaskNumberForUnmarkException;
import bhaymax.exception.command.MissingTodoDescriptionException;
import bhaymax.exception.command.TaskIndexIsNotANumberException;
import bhaymax.exception.command.TaskIndexOutOfBoundsException;
import bhaymax.exception.command.UnrecognisedCommandException;
import bhaymax.exception.command.deadline.MissingDeadlineDescriptionException;
import bhaymax.exception.command.deadline.MissingDeadlineDueByDateException;
import bhaymax.exception.command.event.MissingEventDescriptionException;
import bhaymax.exception.command.event.MissingEventEndDateException;
import bhaymax.exception.command.event.MissingEventStartDateException;
import bhaymax.exception.command.filter.MissingFilterDateException;
import bhaymax.exception.command.filter.MissingFilterOptionException;
import bhaymax.task.TaskList;
import bhaymax.task.timesensitive.Deadline;
import bhaymax.task.timesensitive.Event;
import bhaymax.util.Pair;

/**
 * Parses a provided string from the user, that is expected to contain a full command,
 * and returns a {@link Command} object
 */
public class Parser {
    public static final String DATE_INPUT_FORMAT = "dd/MM/yyyy";
    public static final String DATETIME_INPUT_FORMAT = "dd/MM/yyyy HH:mm";
    public static final String DATETIME_OUTPUT_FORMAT = "dd MMM yyyy, EEE @ HH:mm";

    public static final int TWO_TOKENS = 2;

    public static final String COMMAND_DELIMITER = " ";

    public static final int FIRST_TOKEN = 0;
    public static final int SECOND_TOKEN = 1;
    public static final int INDEX_OFFSET = -1;

    private static Pair<CommandString, String> getCommandAndArgs(String userInput)
            throws InvalidCommandFormatException {
        String trimmedInput = userInput.trim();
        if (trimmedInput.isEmpty()) {
            throw new EmptyCommandException();
        }

        String[] tokens = trimmedInput.split(Parser.COMMAND_DELIMITER, Parser.TWO_TOKENS);
        String commandString = tokens[Parser.FIRST_TOKEN].trim().toLowerCase();
        String arguments = tokens.length == Parser.TWO_TOKENS
                ? tokens[Parser.SECOND_TOKEN].trim()
                : "";

        return new Pair<CommandString, String>(
                CommandString.valueOfCommandString(commandString),
                arguments
        );
    }

    private static int getTaskIndex(String arguments, TaskList taskList,
                                    InvalidCommandFormatException exceptionToThrowOnMissingTaskNumber)
            throws InvalidCommandFormatException {
        String trimmedArguments = arguments.trim();
        if (trimmedArguments.isEmpty()) {
            throw exceptionToThrowOnMissingTaskNumber;
        }

        try {
            int index = Integer.parseInt(trimmedArguments) + Parser.INDEX_OFFSET;
            if (!taskList.isValidIndex(index)) {
                throw new TaskIndexOutOfBoundsException();
            }
            return index;
        } catch (NumberFormatException exception) {
            throw new TaskIndexIsNotANumberException();
        }
    }

    private static String getTodoDescription(String arguments)
            throws InvalidCommandFormatException {
        if (arguments.isEmpty()) {
            throw new MissingTodoDescriptionException();
        }
        return arguments;
    }

    private static Pair<String, String> getTaskDescriptionAndArgs(
            String arguments,
            String option,
            InvalidCommandFormatException exceptionToThrowOnMissingDescription,
            InvalidCommandFormatException exceptionToThrowOnMissingArgs
    ) throws InvalidCommandFormatException {
        String[] tokens = arguments.split(option, Parser.TWO_TOKENS);
        String taskDescription = tokens[Parser.FIRST_TOKEN].trim();
        if (taskDescription.isEmpty()) {
            throw exceptionToThrowOnMissingDescription;
        }
        if (tokens.length < Parser.TWO_TOKENS) {
            throw exceptionToThrowOnMissingArgs;
        }
        String argumentForOption = tokens[Parser.SECOND_TOKEN].trim();
        if (argumentForOption.isEmpty()) {
            throw exceptionToThrowOnMissingArgs;
        }
        return new Pair<String, String>(taskDescription, argumentForOption);
    }

    private static Pair<String, String> getEventStartAndEndDates(String arguments)
            throws InvalidCommandFormatException {
        String[] tokens = arguments.split(Event.FLAG_END_DATE, Parser.TWO_TOKENS);
        String startDate = tokens[Parser.FIRST_TOKEN].trim();
        if (startDate.isEmpty()) {
            throw new MissingEventStartDateException();
        }
        if (tokens.length < Parser.TWO_TOKENS) {
            throw new MissingEventEndDateException();
        }
        String endDate = tokens[Parser.SECOND_TOKEN].trim();
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
        String[] tokens = arguments.split(Parser.COMMAND_DELIMITER, Parser.TWO_TOKENS);
        String filterOption = tokens[Parser.FIRST_TOKEN].trim();
        if (filterOption.isEmpty()) {
            throw new MissingFilterOptionException();
        }
        if (tokens.length < Parser.TWO_TOKENS) {
            throw new MissingFilterDateException();
        }
        String dateTime = tokens[Parser.SECOND_TOKEN].trim();
        if (dateTime.isEmpty()) {
            throw new MissingFilterDateException();
        }
        return new Pair<FilterOption, String>(
                FilterOption.valueOfFilterOptionString(filterOption),
                dateTime
        );
    }

    /**
     * Processes the command string entered by a user and returns a {@link Command} object
     *
     * @return a {@link Command} object representing the command entered by the user
     * @throws InvalidCommandFormatException If the format of the command entered by the user is incorrect
     */
    public static Command parse(String userInput, TaskList taskList)
            throws InvalidCommandFormatException {
        Pair<CommandString, String> commandAndArgs = Parser.getCommandAndArgs(userInput);
        CommandString commandString = commandAndArgs.first();
        String arguments = commandAndArgs.second();

        switch (commandString) {
        case LIST:
            return new ListCommand();
        case DELETE:
            return new DeleteCommand(Parser.getTaskIndex(arguments, taskList,
                    new MissingTaskNumberForDeleteException()));
        case MARK:
            return new MarkCommand(Parser.getTaskIndex(arguments, taskList,
                    new MissingTaskNumberForMarkException()));
        case UNMARK:
            return new UnmarkCommand(Parser.getTaskIndex(arguments, taskList,
                    new MissingTaskNumberForUnmarkException()));
        case TODO:
            return new TodoCommand(Parser.getTodoDescription(arguments));
        case DEADLINE:
            Pair<String, String> descriptionAndDueByDate = Parser.getTaskDescriptionAndArgs(
                    arguments, Deadline.FLAG_DUE_BY,
                    new MissingDeadlineDescriptionException(), new MissingDeadlineDueByDateException());
            String deadlineDescription = descriptionAndDueByDate.first();
            String dueByDate = descriptionAndDueByDate.second();
            return new DeadlineCommand(deadlineDescription, dueByDate);
        case EVENT:
            Pair<String, String> descriptionAndArgs = Parser.getTaskDescriptionAndArgs(
                    arguments, Event.FLAG_START_DATE,
                    new MissingEventDescriptionException(), new MissingEventStartDateException());
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
