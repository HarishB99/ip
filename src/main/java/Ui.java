import java.io.IOException;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Ui {
    private final Scanner scanner;

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    public String readCommand() {
        System.out.println();
        return this.scanner.nextLine();
    }

    public void showError(InvalidCommandFormatException e) {
        this.printWithIndent("[-] Invalid command syntax provided:", true);
        this.printWithIndent("    " + e.getMessage(), true);
        this.printWithIndent("[-] Try again.", true);
    }

    public void showError(InvalidFileFormatException e) {
        this.printWithIndent("[-] Format of task file is incorrect:", true);
        this.printWithIndent("    " + e.getMessage(), true);
        this.printWithIndent("[-] Please check your task file and try again", true);
    }

    public void showError(NumberFormatException ignored) {
        this.printWithIndent("[-] Invalid command syntax provided:", true);
        this.printWithIndent("    Task number should be numerical", true);
        this.printWithIndent("[-] Try again.", true);
    }

    public void showError(IOException e) {
        this.printWithIndent("[-] Unable to save task to file:", true);
        this.printWithIndent("    " + e.getMessage(), true);
    }

    public void showError(DateTimeParseException ignored) {
        this.printWithIndent("[-] Format of task file is incorrect:", true);
        this.printWithIndent("    Wrong date/time format", true);
        this.printWithIndent("[-] Please check your task file and try again", true);
    }

    public void showError(String message) {
        this.printWithIndent("[-] An unknown error occurred:", true);
        this.printWithIndent("    " + message, true);
        this.printWithIndent("[-] Please try again later", true);
        this.printWithIndent("[-] Alternatively, you can try restarting the app", true);
    }

    public void printWithIndent(String msg, boolean includeAdditionalSpace) {
        System.out.print("    ");
        if (includeAdditionalSpace) {
            System.out.print(" ");
        }
        System.out.println(msg);
    }

    public void showLine() {
        this.printWithIndent(
                "____________________________________________________________",
                false);
    }

    public void showWelcome(String name) {
        this.printWithIndent("Hello! I'm " + name, true);
        this.printWithIndent("What can I do for you?", true);
    }

    public void showFarewell() {
        this.printWithIndent("Bye. Hope to see you again soon!", true);
    }

    public void closeResources() {
        this.scanner.close();
    }

}
