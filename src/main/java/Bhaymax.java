import java.io.File;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Bhaymax {
    public static final String NAME             = "Bhaymax";
    public static final String COMMAND_LIST     = "list";
    public static final String COMMAND_MARK     = "mark";
    public static final String COMMAND_UNMARK   = "unmark";
    public static final String COMMAND_TODO     = "todo";
    public static final String COMMAND_DEADLINE = "deadline";
    public static final String COMMAND_EVENT    = "event";
    public static final String COMMAND_DELETE   = "delete";
    public static final String COMMAND_EXIT     = "bye";
    public static final String DEADLINE_OPT_BY  = "/by";
    public static final String EVENT_OPT_START  = "/from";
    public static final String EVENT_OPT_END    = "/to";
    public static final String TASKS_FILE_PATH  = "data/tasks.txt";

    public static boolean readTasksFromFile(String filePath, LinkedList<Task> tasks)
            throws InvalidFileFormatException {
        try {
            File file = new File(filePath);
            Scanner sc = new Scanner(file);
            int lineNumber = 1;
            while (sc.hasNextLine()) {
                String line = sc.nextLine().strip();
                StringTokenizer tokenizer = new StringTokenizer(line, "|");
                if (!tokenizer.hasMoreTokens()) {
                    // File exists but is empty. Should be acceptable.
                    return false;
                }
                String taskType = tokenizer.nextToken().strip();
                if (!tokenizer.hasMoreTokens()) {
                    throw new InvalidFileFormatException("Line " + lineNumber + ": Status of task is missing");
                }
                String taskStatus = tokenizer.nextToken().strip();
                boolean taskWasDone;
                try {
                    taskWasDone = Integer.parseInt(taskStatus) == 1;
                } catch (NumberFormatException e) {
                    throw new InvalidFileFormatException(
                            "Line " + lineNumber + ": Status of task is not a valid value");
                }
                if (!tokenizer.hasMoreTokens()) {
                    throw new InvalidFileFormatException("Line " + lineNumber + ": Task description is missing");
                }
                String taskDescription = tokenizer.nextToken().strip();
                Task task;
                switch (taskType) {
                case Todo.TYPE:
                    task = new Todo(taskDescription);
                    if (taskWasDone) {
                        task.markAsDone();
                    }
                    break;
                case Deadline.TYPE:
                    if (!tokenizer.hasMoreTokens()) {
                        throw new InvalidFileFormatException(
                                "Line " + lineNumber + ": Deadline Task doesn't have a deadline");
                    }
                    String deadline = tokenizer.nextToken().strip();
                    task = new Deadline(taskDescription, deadline);
                    if (taskWasDone) {
                        task.markAsDone();
                    }
                    break;
                case Event.TYPE:
                    if (!tokenizer.hasMoreTokens()) {
                        throw new InvalidFileFormatException(
                                "Line " + lineNumber + ": Event doesn't have a start date/time");
                    }
                    String start = tokenizer.nextToken().strip();
                    if (!tokenizer.hasMoreTokens()) {
                        throw new InvalidFileFormatException(
                                "Line " + lineNumber + ": Event doesn't have an end date/time");
                    }
                    String end = tokenizer.nextToken().strip();
                    task = new Event(taskDescription, start, end);
                    if (taskWasDone) {
                        task.markAsDone();
                    }
                    break;
                default:
                    throw new InvalidFileFormatException("Line " + lineNumber + ": Unrecognised task type");
                }
                tasks.add(task);
                lineNumber++;
            }
            sc.close();
            return true;
        } catch (FileNotFoundException e) {
            return false;
        }
    }

    public static boolean writeTasksToFile(String filePath, LinkedList<Task> tasks) {
        try {
            File file = new File(filePath);
            FileOutputStream fileOutputStream = new FileOutputStream(file, false);
            PrintWriter writer = new PrintWriter(fileOutputStream);
            for (Task task : tasks) {
                writer.println(task.serialise());
            }
            writer.close();
            fileOutputStream.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static void printWithIndent(String msg, boolean includeAdditionalSpace) {
        System.out.print("    ");
        if (includeAdditionalSpace) {
            System.out.print(" ");
        }
        System.out.println(msg);
    }

    public static void printHorizontalLine() {
        Bhaymax.printWithIndent(
                "____________________________________________________________",
                false);
    }

    public static void greetHello(String name) {
        Bhaymax.printWithIndent("Hello! I'm " + name, true);
        Bhaymax.printWithIndent("What can I do for you?", true);
    }

    public static void sayFarewell() {
        Bhaymax.printWithIndent("Bye. Hope to see you again soon!", true);
    }

    public static void main(String[] args) {
        Bhaymax.printHorizontalLine();
        Bhaymax.greetHello(Bhaymax.NAME);
        Bhaymax.printHorizontalLine();

        LinkedList<Task> tasks = new LinkedList<Task>();
        try {
            boolean wasAbleToReadFile = Bhaymax.readTasksFromFile(Bhaymax.TASKS_FILE_PATH, tasks);
            if (wasAbleToReadFile) {
                System.out.println();
                Bhaymax.printHorizontalLine();
                Bhaymax.printWithIndent("Task file was read successfully", true);
                Bhaymax.printHorizontalLine();
            }
        } catch (InvalidFileFormatException e) {
            System.out.println();
            Bhaymax.printHorizontalLine();
            Bhaymax.printWithIndent("[-] Format of task file is incorrect:", true);
            Bhaymax.printWithIndent("    " + e.getMessage(), true);
            Bhaymax.printWithIndent("[-] Please check your task file and try again", true);
            Bhaymax.printHorizontalLine();
        }
        Scanner sc = new Scanner(System.in);
        String userInput = "";

        while (!userInput.equals(Bhaymax.COMMAND_EXIT)) {
            System.out.println();
            userInput = sc.nextLine();
            StringTokenizer tokenizer = new StringTokenizer(userInput);
            if (!tokenizer.hasMoreTokens()) {
                Bhaymax.printHorizontalLine();
                Bhaymax.printWithIndent("Please type something.", true);
                Bhaymax.printHorizontalLine();
                continue;
            }
            String command = tokenizer.nextToken();
            Bhaymax.printHorizontalLine();
            switch (command) {
            case Bhaymax.COMMAND_LIST:
                if (tasks.isEmpty()) {
                    Bhaymax.printWithIndent(
                            "You're all caught up! You have no pending tasks!", true);
                } else {
                    Bhaymax.printWithIndent("Here are the tasks in your list:", true);
                }
                for (int i = 0; i < tasks.size(); i++) {
                    Bhaymax.printWithIndent((i + 1) + "." + tasks.get(i), true);
                }
                break;
            case Bhaymax.COMMAND_DELETE:
                try {
                    if (!tokenizer.hasMoreTokens()) {
                        throw new InvalidCommandFormatException("Not enough arguments");
                    }
                    int indexOfTaskToDelete = Integer.parseInt(tokenizer.nextToken()) - 1;
                    if (indexOfTaskToDelete < 0 || indexOfTaskToDelete >= tasks.size()) {
                        throw new InvalidCommandFormatException("Provided task number could not be found");
                    }
                    Task taskToDelete = tasks.get(indexOfTaskToDelete);
                    tasks.remove(taskToDelete);
                    Bhaymax.printWithIndent(
                            "Noted. I've removed this task:", true);
                    Bhaymax.printWithIndent(
                            "  " + taskToDelete, true);
                    Bhaymax.printWithIndent(
                            "Now you have " + tasks.size() + " tasks in the list.", true);
                } catch (InvalidCommandFormatException e) {
                    Bhaymax.printWithIndent("[-] Invalid command syntax provided:", true);
                    Bhaymax.printWithIndent("    " + e.getMessage(), true);
                    Bhaymax.printWithIndent("[-] Try again.", true);
                } catch (NumberFormatException e) {
                    Bhaymax.printWithIndent("[-] Invalid command syntax provided:", true);
                    Bhaymax.printWithIndent("    Task number should be numerical", true);
                    Bhaymax.printWithIndent("[-] Try again.", true);
                }
                break;
            case Bhaymax.COMMAND_MARK:
                // Fallthrough
            case Bhaymax.COMMAND_UNMARK:
                try {
                    if (!tokenizer.hasMoreTokens()) {
                        throw new InvalidCommandFormatException("Not enough arguments");
                    }
                    int indexOfTaskToMark = Integer.parseInt(tokenizer.nextToken()) - 1;
                    if (indexOfTaskToMark < 0 || indexOfTaskToMark >= tasks.size()) {
                        throw new InvalidCommandFormatException("Provided task number could not be found");
                    }
                    Task taskToMark = tasks.get(indexOfTaskToMark);
                    if (command.equals(Bhaymax.COMMAND_MARK)) {
                        taskToMark.markAsDone();
                        Bhaymax.printWithIndent(
                                "Nice! I've marked this task as done:", true);
                    } else {
                        taskToMark.markAsUndone();
                        Bhaymax.printWithIndent(
                                "OK, I've marked this task as not done yet:", true);
                    }
                    tasks.set(indexOfTaskToMark, taskToMark);
                    Bhaymax.printWithIndent(
                            "  " + tasks.get(indexOfTaskToMark), true);
                } catch (InvalidCommandFormatException e) {
                    Bhaymax.printWithIndent("[-] Invalid command syntax provided:", true);
                    Bhaymax.printWithIndent("    " + e.getMessage(), true);
                    Bhaymax.printWithIndent("[-] Try again.", true);
                } catch (NumberFormatException e) {
                    Bhaymax.printWithIndent("[-] Invalid command syntax provided:", true);
                    Bhaymax.printWithIndent("    Task number should be numerical", true);
                    Bhaymax.printWithIndent("[-] Try again.", true);
                }
                break;
            case Bhaymax.COMMAND_TODO:
                try {
                    if (!tokenizer.hasMoreTokens()) {
                        throw new InvalidCommandFormatException("Not enough arguments");
                    }
                    StringBuilder taskDescription = new StringBuilder();
                    while (tokenizer.hasMoreTokens()) {
                        taskDescription.append(tokenizer.nextToken());
                        if (tokenizer.hasMoreTokens()) {
                            taskDescription.append(' ');
                        }
                    }
                    Task task = new Todo(taskDescription.toString());
                    tasks.add(task);
                    Bhaymax.printWithIndent("Got it. I've added this task:", true);
                    Bhaymax.printWithIndent("  " + task, true);
                    Bhaymax.printWithIndent(
                            "Now you have " + tasks.size() + " tasks in the list.", true);
                } catch (InvalidCommandFormatException e) {
                    Bhaymax.printWithIndent("[-] Invalid command syntax provided:", true);
                    Bhaymax.printWithIndent("    " + e.getMessage(), true);
                    Bhaymax.printWithIndent("[-] Try again.", true);
                }
                break;
            case Bhaymax.COMMAND_DEADLINE:
                try {
                    if (!tokenizer.hasMoreElements()) {
                        throw new InvalidCommandFormatException("Not enough arguments");
                    }
                    StringBuilder taskDescription = new StringBuilder();
                    StringBuilder deadline = new StringBuilder();
                    boolean deadlineExists = false;
                    while (tokenizer.hasMoreTokens()) {
                        String token = tokenizer.nextToken();
                        if (token.equals(Bhaymax.DEADLINE_OPT_BY)) {
                            deadlineExists = true;
                            break;
                        }
                        taskDescription.append(token);
                        taskDescription.append(' ');
                    }
                    taskDescription.deleteCharAt(taskDescription.length() - 1);
                    if (!deadlineExists || !tokenizer.hasMoreTokens()) {
                        throw new InvalidCommandFormatException("No deadline provided");
                    }
                    while (tokenizer.hasMoreTokens()) {
                        String token = tokenizer.nextToken();
                        deadline.append(token);
                        if (tokenizer.hasMoreTokens()) {
                            deadline.append(' ');
                        }
                    }
                    Task task = new Deadline(
                            taskDescription.toString(), deadline.toString());
                    tasks.add(task);
                    Bhaymax.printWithIndent("Got it. I've added this task:", true);
                    Bhaymax.printWithIndent("  " + task, true);
                    Bhaymax.printWithIndent(
                            "Now you have " + tasks.size() + " tasks in the list.", true);
                } catch (InvalidCommandFormatException e) {
                    Bhaymax.printWithIndent("[-] Invalid command syntax provided:", true);
                    Bhaymax.printWithIndent("    " + e.getMessage(), true);
                    Bhaymax.printWithIndent("[-] Try again.", true);
                }
                break;
            case Bhaymax.COMMAND_EVENT:
                try {
                    if (!tokenizer.hasMoreElements()) {
                        throw new InvalidCommandFormatException("Not enough arguments");
                    }
                    StringBuilder taskDescription = new StringBuilder();
                    StringBuilder start = new StringBuilder();
                    StringBuilder end = new StringBuilder();
                    boolean startExists = false;
                    boolean endExists = false;
                    while (tokenizer.hasMoreTokens()) {
                        String token = tokenizer.nextToken();
                        if (token.equals(Bhaymax.EVENT_OPT_START)) {
                            startExists = true;
                            break;
                        }
                        taskDescription.append(token);
                        taskDescription.append(' ');
                    }
                    taskDescription.deleteCharAt(taskDescription.length() - 1);
                    if (!startExists || !tokenizer.hasMoreTokens()) {
                        throw new InvalidCommandFormatException("No start date/time provided for event");
                    }
                    while (tokenizer.hasMoreTokens()) {
                        String token = tokenizer.nextToken();
                        if (token.equals(Bhaymax.EVENT_OPT_END)) {
                            endExists = true;
                            break;
                        }
                        start.append(token);
                        start.append(' ');
                    }
                    start.deleteCharAt(start.length() - 1);
                    if (!endExists || !tokenizer.hasMoreTokens()) {
                        throw new InvalidCommandFormatException("No end date/time provided for event");
                    }
                    while (tokenizer.hasMoreTokens()) {
                        String token = tokenizer.nextToken();
                        end.append(token);
                        if (tokenizer.hasMoreTokens()) {
                            end.append(' ');
                        }
                    }
                    Task task = new Event(taskDescription.toString(), start.toString(), end.toString());
                    tasks.add(task);
                    Bhaymax.printWithIndent("Got it. I've added this task:", true);
                    Bhaymax.printWithIndent("  " + task, true);
                    Bhaymax.printWithIndent(
                            "Now you have " + tasks.size() + " tasks in the list.", true);
                } catch (InvalidCommandFormatException e) {
                    Bhaymax.printWithIndent("[-] Invalid command syntax provided:", true);
                    Bhaymax.printWithIndent("    " + e.getMessage(), true);
                    Bhaymax.printWithIndent("[-] Try again.", true);
                }
                break;
            case Bhaymax.COMMAND_EXIT:
                Bhaymax.sayFarewell();
                break;
            default:
                Bhaymax.printWithIndent(
                        "[-] I don't recognise the command you entered. Please try again.",
                        true);
                break;
            }
            Bhaymax.printHorizontalLine();
        }

        sc.close();
    }
}
