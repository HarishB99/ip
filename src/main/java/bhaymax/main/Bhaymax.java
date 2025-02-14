package bhaymax.main;

import java.io.IOException;
import java.time.format.DateTimeParseException;

import bhaymax.controller.MainWindow;
import bhaymax.exception.InvalidFileFormatException;
import bhaymax.storage.Storage;
import bhaymax.task.TaskList;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Main class for JavaFX version of the Chatbot app
 */
public class Bhaymax extends Application {
    public static final String APP_NAME = "Bhaymax";

    private static final int PREFERRED_WIDTH = 420;
    private static final int PREFERRED_HEIGHT = 640;
    // Specified in milliseconds
    private static final int PAUSE_DURATION_FOR_TERMINATION = 5000;
    private static final String FXML_MAIN_WINDOW_PATH = "/view/MainWindow.fxml";
    private static final String ERROR_MESSAGE_TERMINATING_APP = "I will terminate in 5 seconds";

    private final Storage storage;
    private TaskList tasks;

    /**
     * Sets up the UI and loads the task
     * from a file, if it exists
     */
    public Bhaymax() {
        this.storage = new Storage();
    }

    @Override
    public void start(Stage stage) {
        try {
            boolean canOpenFile = false;
            FXMLLoader fxmlLoader = new FXMLLoader(
                    Bhaymax.class.getResource(Bhaymax.FXML_MAIN_WINDOW_PATH));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);

            // Set up properties for MainWindow controller
            MainWindow mainWindowController = fxmlLoader.<MainWindow>getController();
            mainWindowController.setAppName(Bhaymax.APP_NAME);
            mainWindowController.setStorage(this.storage);

            PauseTransition pauseTransition = new PauseTransition(
                    Duration.millis(Bhaymax.PAUSE_DURATION_FOR_TERMINATION));
            pauseTransition.setOnFinished(event -> Platform.exit());
            try {
                this.tasks = this.storage.loadTasks();
                canOpenFile = true;
            } catch (InvalidFileFormatException e) {
                mainWindowController.disableInputs();
                mainWindowController.clearChat(false);
                mainWindowController.showInvalidFileFormatDialogBox(e);
                mainWindowController.showSadResponse(Bhaymax.ERROR_MESSAGE_TERMINATING_APP);
                pauseTransition.play();
            } catch (DateTimeParseException e) {
                mainWindowController.disableInputs();
                mainWindowController.clearChat(false);
                mainWindowController.showDateTimeParseExceptionDialogBox(e);
                mainWindowController.showSadResponse(Bhaymax.ERROR_MESSAGE_TERMINATING_APP);
                pauseTransition.play();
            }

            if (canOpenFile) {
                mainWindowController.showWelcomeDialogBox();
                mainWindowController.setTasks(this.tasks);
            }

            stage.setMinHeight(Bhaymax.PREFERRED_HEIGHT);
            stage.setMinWidth(Bhaymax.PREFERRED_WIDTH);
            stage.setMaxHeight(Bhaymax.PREFERRED_HEIGHT);
            stage.setMaxWidth(Bhaymax.PREFERRED_WIDTH);

            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
