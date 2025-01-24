import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Scanner;

public class Bhaymax {
    public static final String NAME = "Bhaymax";
    public static final String LIST_COMMAND = "list";
    public static final String EXIT_COMMAND = "bye";

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

        LinkedList<String> tasks = new LinkedList<String>();
        Scanner sc = new Scanner(System.in);
        String command = "";

        while (!command.equals(Bhaymax.EXIT_COMMAND)) {
            System.out.println();
            command = sc.nextLine();
            if (command.equals(Bhaymax.EXIT_COMMAND)) {
                break;
            } else if (command.equals(Bhaymax.LIST_COMMAND)) {
                Bhaymax.printHorizontalLine();
                if (tasks.isEmpty()) {
                    System.out.println("\t There are no tasks available.");
                }
                for (int i = 0; i < tasks.size(); i++) {
                    System.out.println("\t " + (i + 1) + ". " + tasks.get(i));
                }
                Bhaymax.printHorizontalLine();
                continue;
            }
            Bhaymax.printHorizontalLine();
            tasks.add(command);
            System.out.println("\t added: " + command);
            Bhaymax.printHorizontalLine();
        }

        sc.close();

        Bhaymax.printHorizontalLine();
        Bhaymax.sayFarewell();
        Bhaymax.printHorizontalLine();
    }
}
