import java.io.IOException;
import java.time.format.DateTimeParseException;

public class Bhaymax {
    public static final String NAME             = "Bhaymax";
    public static final String TASKS_FILE_PATH  = "data/tasks.txt";

    private final Storage storage;
    private final Ui ui;
    private TaskList tasks;

    public Bhaymax(String filePath) {
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

    public void run() {
        this.ui.showLine();
        this.ui.showWelcome(Bhaymax.NAME);
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
        new Bhaymax(Bhaymax.TASKS_FILE_PATH).run();
    }
}
