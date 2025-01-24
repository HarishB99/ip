import java.util.LinkedList;
import java.util.Scanner;

public class Bhaymax {
    public static final String NAME = "Bhaymax";
    public static final String COMMAND_LIST = "list";
    public static final String COMMAND_MARK = "mark";
    public static final String COMMAND_UNMARK = "unmark";
    public static final String COMMAND_TODO = "todo";
    public static final String COMMAND_DEADLINE = "deadline";
    public static final String COMMAND_EVENT = "event";
    public static final String COMMAND_EXIT = "bye";
    public static final String COMMAND_SEP = " ";
    public static final String DEADLINE_OPT_BY = " /by ";
    public static final String EVENT_OPT_START = " /from ";
    public static final String EVENT_OPT_END = " /to ";

    public static void printHorizontalLine() {
        System.out.println("\t____________________________________________________________");
    }

    public static void greetHello(String name) {
        System.out.println("\t Hello! I'm " + name);
        System.out.println("\t What can I do for you?");
    }

    public static void sayFarewell() {
        System.out.println("\t Bye. Hope to see you again soon!");
    }

    public static void main(String[] args) {
        Bhaymax.printHorizontalLine();
        Bhaymax.greetHello(Bhaymax.NAME);
        Bhaymax.printHorizontalLine();

        LinkedList<Task> tasks = new LinkedList<Task>();
        Scanner sc = new Scanner(System.in);
        String command = "";

        while (!command.equals(Bhaymax.COMMAND_EXIT)) {
            System.out.println();
            command = sc.nextLine();
            if (command.equals(Bhaymax.COMMAND_EXIT)) {
                break;
            } else if (command.equals(Bhaymax.COMMAND_LIST)) {
                Bhaymax.printHorizontalLine();
                if (tasks.isEmpty()) {
                    System.out.println("\t There are no tasks available.");
                } else {
                    System.out.println("\t Here are the tasks in your list:");
                }
                for (int i = 0; i < tasks.size(); i++) {
                    System.out.println("\t " + (i + 1) + "." + tasks.get(i));
                }
                Bhaymax.printHorizontalLine();
                continue;
            } else if (command.startsWith(Bhaymax.COMMAND_MARK)) {
                Bhaymax.printHorizontalLine();
                String[] tokens = command.split(Bhaymax.COMMAND_SEP);
                if (tokens.length != 2) {
                    System.out.println("\t Invalid command syntax provided. Try again.");
                    Bhaymax.printHorizontalLine();
                    continue;
                }
                try {
                    int indexOfTaskToMark = Integer.parseInt(tokens[1]) - 1;
                    if (indexOfTaskToMark < 0 || indexOfTaskToMark >= tasks.size()) {
                        System.out.println("\t Provided Task Number could not be found.");
                        Bhaymax.printHorizontalLine();
                        continue;
                    }
                    Task taskToMark = tasks.get(indexOfTaskToMark);
                    taskToMark.markAsDone();
                    tasks.set(indexOfTaskToMark, taskToMark);
                    System.out.println("\t Nice! I've marked this task as done:");
                    System.out.println("\t   " + tasks.get(indexOfTaskToMark));
                } catch (NumberFormatException e) {
                    System.out.println("\t Invalid command syntax provided. Try again.");
                }
                Bhaymax.printHorizontalLine();
                continue;
            } else if (command.startsWith(Bhaymax.COMMAND_UNMARK)) {
                Bhaymax.printHorizontalLine();
                String[] tokens = command.split(Bhaymax.COMMAND_SEP);
                if (tokens.length != 2) {
                    System.out.println("\t Invalid command syntax provided. Try again.");
                    Bhaymax.printHorizontalLine();
                    continue;
                }
                try {
                    int indexOfTaskToMark = Integer.parseInt(tokens[1]) - 1;
                    if (indexOfTaskToMark < 0 || indexOfTaskToMark >= tasks.size()) {
                        System.out.println("\t Provided Task Number could not be found.");
                        Bhaymax.printHorizontalLine();
                        continue;
                    }
                    Task taskToMark = tasks.get(indexOfTaskToMark);
                    taskToMark.markAsUndone();
                    tasks.set(indexOfTaskToMark, taskToMark);
                    System.out.println("\t OK, I've marked this task as not done yet:");
                    System.out.println("\t   " + tasks.get(indexOfTaskToMark));
                } catch (NumberFormatException e) {
                    System.out.println("\t Invalid command syntax provided. Try again.");
                }
                Bhaymax.printHorizontalLine();
                continue;
            } else if (command.startsWith(Bhaymax.COMMAND_TODO)) {
                Bhaymax.printHorizontalLine();
                String[] tokens = command.split(Bhaymax.COMMAND_TODO + Bhaymax.COMMAND_SEP);
                if (tokens.length != 2) {
                    System.out.println("\t Invalid command syntax provided. Try again.");
                    Bhaymax.printHorizontalLine();
                    continue;
                }
                Task task = new Todo(tokens[1]);
                tasks.add(task);
                System.out.println("\t Got it. I've added this task:");
                System.out.println("\t   " + task);
                System.out.println("\t Now you have " + tasks.size() + " tasks in the list.");
                Bhaymax.printHorizontalLine();
                continue;
            } else if (command.startsWith(Bhaymax.COMMAND_DEADLINE)) {
                Bhaymax.printHorizontalLine();
                String[] tokens = command.split(Bhaymax.COMMAND_DEADLINE + Bhaymax.COMMAND_SEP);
                if (tokens.length != 2) {
                    System.out.println("\t Invalid command syntax provided. Try again.");
                    Bhaymax.printHorizontalLine();
                    continue;
                }
                tokens = tokens[1].split(Bhaymax.DEADLINE_OPT_BY);
                Task task = new Deadline(tokens[0], tokens[1]);
                tasks.add(task);
                System.out.println("\t Got it. I've added this task:");
                System.out.println("\t   " + task);
                System.out.println("\t Now you have " + tasks.size() + " tasks in the list.");
                Bhaymax.printHorizontalLine();
                continue;
            } else if (command.startsWith(Bhaymax.COMMAND_EVENT)) {
                Bhaymax.printHorizontalLine();
                String[] tokens = command.split(Bhaymax.COMMAND_EVENT + Bhaymax.COMMAND_SEP);
                if (tokens.length != 2) {
                    System.out.println("\t Invalid command syntax provided. Try again.");
                    Bhaymax.printHorizontalLine();
                    continue;
                }
                tokens = tokens[1].split(Bhaymax.EVENT_OPT_START);
                String description = tokens[0];
                tokens = tokens[1].split(Bhaymax.EVENT_OPT_END);
                Task task = new Event(description, tokens[0], tokens[1]);
                tasks.add(task);
                System.out.println("\t Got it. I've added this task:");
                System.out.println("\t   " + task);
                System.out.println("\t Now you have " + tasks.size() + " tasks in the list.");
                Bhaymax.printHorizontalLine();
                continue;
            }
            Bhaymax.printHorizontalLine();
            System.out.println("\t I don't recognise the command you entered. Please try again.");
            Bhaymax.printHorizontalLine();
        }

        sc.close();

        Bhaymax.printHorizontalLine();
        Bhaymax.sayFarewell();
        Bhaymax.printHorizontalLine();
    }
}
