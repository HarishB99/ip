package bhaymax.ui;

import java.io.IOException;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import bhaymax.exception.InvalidCommandFormatException;
import bhaymax.exception.InvalidFileFormatException;

/**
 * Provides the CLI-UI for Bhaymax Chatbot
 */
public class Ui {
    private final Scanner scanner;

    /**
     * Sets up the Scanner object to read from STDIN
     */
    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Returns the command read from STDIN
     *
     * @return The command read from STDIN, as a {@code String}
     */
    public String readCommand() {
        System.out.println();
        return this.scanner.nextLine();
    }

    /**
     * Shows an error message when
     * {@code InvalidCommandFormatException} occurs
     *
     * @param exception An {@code InvalidCommandFormatException} object
     */
    public void showError(InvalidCommandFormatException exception) {
        this.printWithIndent("[-] Invalid command syntax provided:", true);
        this.printWithIndent("    " + exception.getMessage(), true);
        this.printWithIndent("[-] Try again.", true);
    }

    /**
     * Shows an error message when
     * {@code InvalidFileFormatException} occurs
     *
     * @param exception An {@code InvalidFileFormatException} object
     */
    public void showError(InvalidFileFormatException exception) {
        this.printWithIndent("[-] Format of task file is incorrect:", true);
        this.printWithIndent("    " + exception.getMessage(), true);
        this.printWithIndent("[-] Please check your task file and try again", true);
    }

    /**
     * Shows an error message when
     * {@code NumberFormatException} occurs
     *
     * @param ignored An {@code NumberFormatException} object
     */
    public void showError(NumberFormatException ignored) {
        this.printWithIndent("[-] Invalid command syntax provided:", true);
        this.printWithIndent("    Task number should be numerical", true);
        this.printWithIndent("[-] Try again.", true);
    }

    /**
     * Shows an error message when
     * {@code IOException} occurs
     *
     * @param exception An {@code IOException} object
     */
    public void showError(IOException exception) {
        this.printWithIndent("[-] Unable to save task to file:", true);
        this.printWithIndent("    " + exception.getMessage(), true);
    }

    /**
     * Shows an error message when
     * {@code DateTimeParseException} occurs
     *
     * @param ignored An {@code DateTimeParseException} object
     */
    public void showError(DateTimeParseException ignored) {
        this.printWithIndent("[-] Task format is incorrect:", true);
        this.printWithIndent("    Wrong date/time format", true);
        this.printWithIndent("[-] Try again.", true);
    }

    /**
     * Prints the given message as an error message.
     * (meant to be used when a generic {@code Exception} occurs)
     *
     * @param message The error message to be printed
     */
    public void showError(String message) {
        this.printWithIndent("[-] An unknown error occurred:", true);
        this.printWithIndent("    " + message, true);
        this.printWithIndent("[-] Please try again later", true);
        this.printWithIndent("[-] Alternatively, you can try restarting the app", true);
    }

    /**
     * Prints a given {@code String} message with
     * single indent (4 spaces), with an optional
     * additional space if needed
     *
     * @param msg The message to be printed
     * @param includeAdditionalSpace Indicates whether to include an additional space in the indent (i.e., 5 spaces
     *                               instead of 4)
     */
    public void printWithIndent(String msg, boolean includeAdditionalSpace) {
        System.out.print("    ");
        if (includeAdditionalSpace) {
            System.out.print(" ");
        }
        System.out.println(msg);
    }

    /**
     * Prints a horizontal line, to be used
     * as a divider in the UI output
     */
    public void showLine() {
        this.printWithIndent(
                "____________________________________________________________",
                false);
    }

    /**
     * Prints a message that is meant to be
     * shown to the user at the start of the
     * app
     */
    public void showWelcome(String name) {
        this.printWithIndent("Hello! I'm " + name, true);
        this.printWithIndent("What can I do for you?", true);
    }

    /**
     * Prints a message that is meant to be
     * shown to the user before exiting the app
     */
    public void showFarewell() {
        this.printWithIndent("Bye. Hope to see you again soon!", true);
    }

    /**
     * Closes the Scanner object created in the
     * constructor method: {@code Ui()}
     */
    public void closeResources() {
        this.scanner.close();
    }

}
