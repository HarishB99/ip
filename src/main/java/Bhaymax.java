import java.util.Scanner;

public class Bhaymax {
    public static final String NAME = "Bhaymax";
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

        Scanner sc = new Scanner(System.in);
        String command = "";

        while (!command.equals(Bhaymax.EXIT_COMMAND)) {
            System.out.println();
            command = sc.nextLine();
            if (command.equals(Bhaymax.EXIT_COMMAND)) {
                break;
            }
            Bhaymax.printHorizontalLine();
            System.out.println("\t " + command);
            Bhaymax.printHorizontalLine();
        }

        sc.close();

        Bhaymax.printHorizontalLine();
        Bhaymax.sayFarewell();
        Bhaymax.printHorizontalLine();
    }
}
