package bhaymax;

import java.io.IOException;
import java.time.format.DateTimeParseException;
import bhaymax.ui.Ui;
import bhaymax.task.TaskList;
import bhaymax.parser.Parser;
import bhaymax.storage.Storage;
import bhaymax.command.Command;
import bhaymax.exception.InvalidFileFormatException;
import bhaymax.exception.InvalidCommandFormatException;

/**
 * The main entry point for {@link Bhaymax} Chatbot app
 */
public class Bhaymax {
    public static final String APP_NAME = "Bhaymax";

    private final Storage storage;
    private final Ui ui;
    private TaskList tasks;

    /**
     * Sets up the UI and loads the task
     * from a file, if it exists
     */
    public Bhaymax() {
        this.ui = new Ui();
        this.storage = new Storage();
        try {
            this.tasks = storage.loadTasks();
        } catch (InvalidFileFormatException e) {
            this.ui.showError(e);
        } catch (DateTimeParseException e) {
            this.ui.showError(e);
        } catch (Exception e) {
            this.ui.showError(e.getMessage());
            this.tasks = new TaskList();
        }
    }

    /**
     * Executes the chatbot app
     */
    public void run() {
        this.ui.showLine();
        this.ui.showWelcome(Bhaymax.APP_NAME);
        this.ui.showLine();
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = this.ui.readCommand();
                this.ui.showLine();
                Command c = Parser.parse(fullCommand, this.tasks);
                c.execute(this.tasks, this.ui, this.storage);
                isExit = c.isExit();
            } catch (InvalidCommandFormatException e) {
                this.ui.showError(e);
            } catch (DateTimeParseException e) {
                this.ui.showError(e);
            } catch (NumberFormatException e) {
                this.ui.showError(e);
            } catch (IOException e) {
                this.ui.showError(e);
            } catch (Exception e) {
                this.ui.showError(e.getMessage());
            } finally {
                this.ui.showLine();
            }
        }
        this.ui.closeResources();
    }

    public static void main(String[] args) {
        new Bhaymax().run();
    }
}
