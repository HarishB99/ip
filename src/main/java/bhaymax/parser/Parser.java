package bhaymax.parser;

import java.util.StringTokenizer;

import bhaymax.command.Command;
import bhaymax.command.CommandString;
import bhaymax.command.DeadlineCommand;
import bhaymax.command.DeleteCommand;
import bhaymax.command.EventCommand;
import bhaymax.command.ExitCommand;
import bhaymax.command.FilterCommand;
import bhaymax.command.FilterOpt;
import bhaymax.command.ListCommand;
import bhaymax.command.MarkCommand;
import bhaymax.command.SearchCommand;
import bhaymax.command.TodoCommand;
import bhaymax.command.UnmarkCommand;
import bhaymax.exception.InvalidCommandException;
import bhaymax.exception.InvalidCommandFormatException;
import bhaymax.task.TaskList;

/**
 * Provides methods to parse a command string
 * to a {@link Command} object
 */
public class Parser {
    public static final String DATE_FORMAT = "dd-MM-yyyy";
    public static final String DATETIME_INPUT_FORMAT = "dd-MM-yyyy HH:mm";
    public static final String DATETIME_OUTPUT_FORMAT = "dd MMM yyyy, EEE @ HH:mm";
    public static final String DEADLINE_OPT_BY = "/by";
    public static final String EVENT_OPT_START = "/from";
    public static final String EVENT_OPT_END = "/to";
    public static final String FILTER_OPT_BEFORE = "/before";
    public static final String FILTER_OPT_AFTER = "/after";
    public static final String FILTER_OPT_ON = "/on";
    public static final String FILTER_OPT_BEFORE_TIME = "/before_time";
    public static final String FILTER_OPT_AFTER_TIME = "/after_time";
    public static final String FILTER_OPT_ON_TIME = "/on_time";

    /**
     * Processes the command string entered by a user
     * and returns a {@link Command} object
     *
     * @param fullCommandString the command string entered by the user
     * @param taskList the {@link TaskList} object containing a list of tasks
     * @return a {@link Command} object representing the command entered by the user
     * @throws InvalidCommandFormatException If the format of the command entered by the user is incorrect
     */
    public static Command parse(String fullCommandString, TaskList taskList)
            throws InvalidCommandFormatException, InvalidCommandException {
        StringTokenizer tokenizer = new StringTokenizer(fullCommandString);
        if (!tokenizer.hasMoreTokens()) {
            throw new InvalidCommandFormatException("Command is empty");
        }
        String commandString = tokenizer.nextToken();
        if (!CommandString.isValidCommandString(commandString)) {
            throw new InvalidCommandFormatException("Unrecognised command");
        }

        CommandString validCommandString = CommandString.valueOfCommandString(commandString);

        switch (validCommandString) {
        case LIST:
            return new ListCommand();
        case DELETE:
            // Fallthrough
        case MARK:
            // Fallthrough
        case UNMARK:
            if (!tokenizer.hasMoreTokens()) {
                throw new InvalidCommandFormatException("Missing task number");
            }
            int indexOfAffectedTask = Integer.parseInt(tokenizer.nextToken()) - 1;
            if (!taskList.isValidIndex(indexOfAffectedTask)) {
                throw new InvalidCommandFormatException("Provided task number could not be found");
            }
            if (validCommandString.equals(CommandString.MARK)) {
                return new MarkCommand(indexOfAffectedTask);
            } else if (validCommandString.equals(CommandString.UNMARK)) {
                return new UnmarkCommand(indexOfAffectedTask);
            } else {
                return new DeleteCommand(indexOfAffectedTask);
            }
        case TODO:
            // Fallthrough
        case DEADLINE:
            // Fallthrough
        case EVENT:
            if (!tokenizer.hasMoreTokens()) {
                throw new InvalidCommandFormatException("Missing task description");
            }

            StringBuilder taskDescription = new StringBuilder();

            boolean deadlineExists = false;
            boolean startExists = false;

            while (tokenizer.hasMoreTokens()) {
                String token = tokenizer.nextToken();

                if (validCommandString.equals(CommandString.DEADLINE)
                        && token.equals(Parser.DEADLINE_OPT_BY)) {
                    deadlineExists = true;
                    break;
                }

                if (validCommandString.equals(CommandString.EVENT)
                        && token.equals(Parser.EVENT_OPT_START)) {
                    startExists = true;
                    break;
                }

                taskDescription.append(token);
                taskDescription.append(' ');
            }
            taskDescription.deleteCharAt(taskDescription.length() - 1);

            if (validCommandString.equals(CommandString.TODO)) {
                return new TodoCommand(taskDescription.toString());
            }

            if (validCommandString.equals(CommandString.DEADLINE)
                    && !(deadlineExists && tokenizer.hasMoreTokens())) {
                throw new InvalidCommandFormatException("No deadline provided");
            } else if (validCommandString.equals(CommandString.EVENT)
                    && !(startExists && tokenizer.hasMoreTokens())) {
                throw new InvalidCommandFormatException("No start date and time provided for event");
            }

            if (validCommandString.equals(CommandString.DEADLINE)) {
                StringBuilder deadline = new StringBuilder();
                while (tokenizer.hasMoreTokens()) {
                    String token = tokenizer.nextToken();
                    deadline.append(token);
                    if (tokenizer.hasMoreTokens()) {
                        deadline.append(' ');
                    }
                }
                return new DeadlineCommand(
                        taskDescription.toString(), deadline.toString());
            }

            StringBuilder start = new StringBuilder();
            boolean endExists = false;
            while (tokenizer.hasMoreTokens()) {
                String token = tokenizer.nextToken();
                if (token.equals(Parser.EVENT_OPT_END)) {
                    endExists = true;
                    break;
                }
                start.append(token);
                start.append(' ');
            }
            start.deleteCharAt(start.length() - 1);

            if (!endExists || !tokenizer.hasMoreTokens()) {
                throw new InvalidCommandFormatException("No end date and time provided for event");
            }

            StringBuilder end = new StringBuilder();
            while (tokenizer.hasMoreTokens()) {
                String token = tokenizer.nextToken();
                end.append(token);
                if (tokenizer.hasMoreTokens()) {
                    end.append(' ');
                }
            }

            return new EventCommand(
                    taskDescription.toString(), start.toString(), end.toString());
        case SEARCH:
            if (!tokenizer.hasMoreTokens()) {
                throw new InvalidCommandFormatException("Missing search term");
            }

            StringBuilder searchTerm = new StringBuilder();
            while (tokenizer.hasMoreTokens()) {
                searchTerm.append(tokenizer.nextToken());
                if (tokenizer.hasMoreTokens()) {
                    searchTerm.append(' ');
                }
            }

            return new SearchCommand(searchTerm.toString());
        case FILTER:
            if (!tokenizer.hasMoreTokens()) {
                throw new InvalidCommandFormatException("Missing date and/or time option");
            }
            String filterOpt = tokenizer.nextToken();
            if (!tokenizer.hasMoreElements()) {
                throw new InvalidCommandFormatException("Missing date and/or time");
            }
            StringBuilder dateTime = new StringBuilder();
            while (tokenizer.hasMoreTokens()) {
                String token = tokenizer.nextToken();
                dateTime.append(token);
                if (tokenizer.hasMoreTokens()) {
                    dateTime.append(' ');
                }
            }
            return switch (filterOpt) {
            case Parser.FILTER_OPT_ON -> new FilterCommand(
                    dateTime.toString(), FilterOpt.DATE_ON);
            case Parser.FILTER_OPT_BEFORE -> new FilterCommand(
                    dateTime.toString(), FilterOpt.DATE_BEFORE);
            case Parser.FILTER_OPT_AFTER -> new FilterCommand(
                    dateTime.toString(), FilterOpt.DATE_AFTER);
            case Parser.FILTER_OPT_ON_TIME -> new FilterCommand(
                    dateTime.toString(), FilterOpt.TIME_ON);
            case Parser.FILTER_OPT_BEFORE_TIME -> new FilterCommand(
                    dateTime.toString(), FilterOpt.TIME_BEFORE);
            case Parser.FILTER_OPT_AFTER_TIME -> new FilterCommand(
                    dateTime.toString(), FilterOpt.TIME_AFTER);
            default -> throw new InvalidCommandFormatException("Unknown filter option");
            };
        case BYE:
            // Fallthrough
        case EXIT:
            return new ExitCommand();
        default:
            throw new InvalidCommandFormatException("Unrecognised command");
        }
    }
}
