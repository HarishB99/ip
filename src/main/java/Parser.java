import java.util.StringTokenizer;

public class Parser {
    public static final String COMMAND_LIST             = "list";
    public static final String COMMAND_MARK             = "mark";
    public static final String COMMAND_UNMARK           = "unmark";
    public static final String COMMAND_TODO             = "todo";
    public static final String COMMAND_DEADLINE         = "deadline";
    public static final String COMMAND_FILTER           = "filter";
    public static final String COMMAND_EVENT            = "event";
    public static final String COMMAND_DELETE           = "delete";
    public static final String COMMAND_EXIT             = "bye";
    public static final String DATETIME_INPUT_FORMAT    = "dd-MM-yyyy HH:mm";
    public static final String DATE_FORMAT              = "dd-MM-yyyy";
    public static final String DATETIME_OUTPUT_FORMAT   = "dd MMM yyyy, EEE @ HH:mm";
    public static final String DEADLINE_OPT_BY          = "/by";
    public static final String EVENT_OPT_START          = "/from";
    public static final String EVENT_OPT_END            = "/to";
    public static final String FILTER_OPT_BEFORE        = "/before";
    public static final String FILTER_OPT_AFTER         = "/after";
    public static final String FILTER_OPT_ON            = "/on";
    public static final String FILTER_OPT_BEFORE_TIME   = "/before_time";
    public static final String FILTER_OPT_AFTER_TIME    = "/after_time";
    public static final String FILTER_OPT_ON_TIME       = "/on_time";

    public static Command parse(String fullCommandString, TaskList taskList) throws InvalidCommandFormatException {
        StringTokenizer tokenizer = new StringTokenizer(fullCommandString);
        if (!tokenizer.hasMoreTokens()) {
            throw new InvalidCommandFormatException("Command is empty");
        }
        String commandString = tokenizer.nextToken();
        switch (commandString) {
        case Parser.COMMAND_LIST:
            return new ListCommand();
        case Parser.COMMAND_DELETE:
            // Fallthrough
        case Parser.COMMAND_MARK:
            // Fallthrough
        case Parser.COMMAND_UNMARK:
            if (!tokenizer.hasMoreTokens()) {
                throw new InvalidCommandFormatException("Missing task number");
            }
            int indexOfAffectedTask = Integer.parseInt(tokenizer.nextToken()) - 1;
            if (!taskList.isValidIndex(indexOfAffectedTask)) {
                throw new InvalidCommandFormatException("Provided task number could not be found");
            }
            if (commandString.equals(COMMAND_MARK)) {
                return new MarkCommand(indexOfAffectedTask);
            } else if (commandString.equals(COMMAND_UNMARK)) {
                return new UnmarkCommand(indexOfAffectedTask);
            } else {
                return new DeleteCommand(indexOfAffectedTask);
            }
        case Parser.COMMAND_TODO:
            // Fallthrough
        case Parser.COMMAND_DEADLINE:
            // Fallthrough
        case Parser.COMMAND_EVENT:
            if (!tokenizer.hasMoreTokens()) {
                throw new InvalidCommandFormatException("Missing task description");
            }

            StringBuilder taskDescription = new StringBuilder();

            boolean deadlineExists = false;
            boolean startExists = false;

            while (tokenizer.hasMoreTokens()) {
                String token = tokenizer.nextToken();

                if (commandString.equals(Parser.COMMAND_DEADLINE)
                        && token.equals(Parser.DEADLINE_OPT_BY)) {
                    deadlineExists = true;
                    break;
                }

                if (commandString.equals(Parser.COMMAND_EVENT)
                        && token.equals(Parser.EVENT_OPT_START)) {
                    startExists = true;
                    break;
                }

                taskDescription.append(token);
                taskDescription.append(' ');
            }
            taskDescription.deleteCharAt(taskDescription.length() - 1);

            if (commandString.equals(Parser.COMMAND_TODO)) {
                return new TodoCommand(taskDescription.toString());
            }

            if (commandString.equals(Parser.COMMAND_DEADLINE)
                    && !(deadlineExists && tokenizer.hasMoreTokens())) {
                throw new InvalidCommandFormatException("No deadline provided");
            } else if (commandString.equals(Parser.COMMAND_EVENT)
                    && !(startExists && tokenizer.hasMoreTokens())) {
                throw new InvalidCommandFormatException("No start date and time provided for event");
            }

            if (commandString.equals(Parser.COMMAND_DEADLINE)) {
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
        case Parser.COMMAND_FILTER:
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
        case Parser.COMMAND_EXIT:
            return new ExitCommand();
        default:
            throw new InvalidCommandFormatException("Unrecognised command");
        }
    }
}
